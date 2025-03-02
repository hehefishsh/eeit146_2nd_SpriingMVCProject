package tw.eeit1462.springmvcproject.service;

import java.time.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.AttendanceLogs;
import tw.eeit1462.springmvcproject.model.AttendanceViolations;
import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.model.ShiftType;
import tw.eeit1462.springmvcproject.model.Status;
import tw.eeit1462.springmvcproject.model.Type;
import tw.eeit1462.springmvcproject.repository.AttendanceLogsRepository;
import tw.eeit1462.springmvcproject.repository.AttendanceRepository;
import tw.eeit1462.springmvcproject.repository.AttendanceViolationsRepository;
import tw.eeit1462.springmvcproject.repository.EmployeeRepository;
import tw.eeit1462.springmvcproject.repository.ScheduleRepository;
import tw.eeit1462.springmvcproject.repository.StatusRepository;
import tw.eeit1462.springmvcproject.repository.TypeRepository;

@Service
@Transactional
public class ClockService {

    @Autowired
    private AttendanceLogsRepository attendanceLogsRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private AttendanceViolationsRepository attendanceViolationsRepository;

    public List<AttendanceLogs> getLogsByEmployeeId(int employeeId) {
        return attendanceLogsRepository.findByEmployee_EmployeeId(employeeId);
    }

    public String clockInOrOut(int employeeId, int clockTypeId) {
        String exceptionMessage = null; // 初始化異常訊息

        try {
            // 取得 Employee 實體
            Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("員工不存在"));
            
            // 取得 ClockType 實體
            Type clockType = typeRepository.findById(clockTypeId)
                .orElseThrow(() -> new RuntimeException("打卡類型不存在"));

            // 以今日起始與結束時間作為 Attendance 查詢範圍
            LocalDateTime todayStart = LocalDate.now().atStartOfDay();
            LocalDateTime todayEnd = todayStart.plusDays(1);

            // 取得當天的 ShiftType（根據 Schedule 表），以今日日期查詢
            ShiftType shiftType = scheduleRepository.findShiftTypeByEmployeeIdAndDate(employeeId, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("沒有找到今日班別"));

            // 取得出勤狀態 (例如：出勤中)
            Status status = statusRepository.findByStatusName("在職")
                .orElseThrow(() -> new RuntimeException("出勤狀態不存在"));

            // 取得當天的 Attendance，如果沒有則新建
            Attendance attendance = attendanceRepository
                .findByEmployeeAndCreatedAtBetween(employee, todayStart, todayEnd)
                .orElseGet(() -> attendanceRepository.save(createNewAttendance(employee, shiftType, status)));

            // 檢查異常（上班、下班、外出打卡、外出結束）
            exceptionMessage = checkForExceptions(employeeId, clockType, shiftType);

            if (exceptionMessage != null) {
                long violationMinutes = 0;
                LocalDateTime now = LocalDateTime.now();
                // 修正班別開始時間，讓它變成今天的日期
                LocalDateTime shiftStartTime = LocalDate.now().atTime(shiftType.getStartTime().toLocalTime());
                LocalDateTime shiftEndTime = LocalDate.now().atTime(shiftType.getEndTime().toLocalTime());

                // 依據異常類型計算違規分鐘數
                if ("遲到".equals(exceptionMessage)) {
                    LocalDateTime expectedClockInTime = shiftStartTime.plusMinutes(10);
                    System.out.println("班別開始時間：" + shiftStartTime);
                    
                    // 確保 `now` 晚於 `expectedClockInTime`，才計算違規分鐘數
                    if (now.isAfter(expectedClockInTime)) {
                        violationMinutes = Duration.between(expectedClockInTime, now).toMinutes();
                        System.out.println(violationMinutes);
                    } else {
                        violationMinutes = 0;
                    }
                } else if ("早退".equals(exceptionMessage)) {
                    violationMinutes = Duration.between(now, shiftEndTime.minusMinutes(10)).toMinutes();
                } else if ("非上班時間".equals(exceptionMessage)) {
                    // 表定上班前1小時與實際打卡時間之差
                    violationMinutes = Math.abs(Duration.between(now, shiftStartTime.minusHours(1)).toMinutes());
                } else {
                    // 重複打卡、缺外出結束、非外出打卡時間、缺外出打卡---預設違規分鐘數
                    violationMinutes = 0;
                }

                // 注意：此處使用 exceptionMessage 作為查詢條件，要確保資料中該欄位值唯一
                Type violationType = typeRepository.findByTypeName(exceptionMessage)
                    .orElseThrow(() -> new RuntimeException("異常類型不存在"));

                AttendanceViolations violation = new AttendanceViolations();
                violation.setEmployee(employee);
                violation.setAttendance(attendance);
                violation.setViolationType(violationType);
                violation.setViolationMinutes((int) violationMinutes);
                violation.setCreatedAt(now);

                attendanceViolationsRepository.save(violation);
            }

            // 建立 AttendanceLogs 實體，記錄打卡事件
            AttendanceLogs log = new AttendanceLogs();
            log.setAttendance(attendance);
            log.setEmployee(employee);
            log.setClockType(clockType);
            log.setClockTime(LocalDateTime.now());

            attendanceLogsRepository.save(log);

        } catch (RuntimeException e) {
            exceptionMessage = e.getMessage(); // 捕捉異常訊息
        }

        return exceptionMessage; // 返回異常訊息，顯示給員工
    }
    private String checkForExceptions(int employeeId, Type clockType, ShiftType shiftType) {
        // 使用當天起始與結束時間作為查詢範圍
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        List<AttendanceLogs> logsToday = attendanceLogsRepository
            .findByEmployee_EmployeeIdAndClockTimeBetween(employeeId, startOfDay, endOfDay);
        // 修正班別開始時間，讓它變成今天的日期
        LocalDateTime shiftStartTime = LocalDate.now().atTime(shiftType.getStartTime().toLocalTime());
        LocalDateTime shiftEndTime = LocalDate.now().atTime(shiftType.getEndTime().toLocalTime());

        // 檢查上班打卡：重複打卡、打卡過早、遲到
        if (clockType.getTypeName().equals("上班") &&
            logsToday.stream().anyMatch(log -> log.getClockType().getTypeName().equals("上班"))) {
            return "重複打卡";
        } else if (clockType.getTypeName().equals("上班")) {
            // 檢查上班打卡必須在表定上班前1小時內
            if (LocalDateTime.now().isBefore(shiftStartTime.minusHours(1))) {
                return "非上班時間";
            }
            // 檢查遲到：若打卡時間晚於表定開始時間+10分鐘寬限期
            if (LocalDateTime.now().isAfter(shiftStartTime.plusMinutes(10))) {
                return "遲到";
            }
        }
        // 檢查下班打卡：重複打卡、早退、外出未結束
        else if (clockType.getTypeName().equals("下班") &&
                 logsToday.stream().anyMatch(log -> log.getClockType().getTypeName().equals("下班"))) {
            return "重複打卡";
        } else if (clockType.getTypeName().equals("下班")) {
        	// 若打下班前有未結束的外出打卡
        	long outCount = logsToday.stream()
        			.filter(log -> log.getClockType().getTypeName().equals("外出打卡"))
        			.count();
        	long outFinishCount = logsToday.stream()
        			.filter(log -> log.getClockType().getTypeName().equals("外出結束"))
        			.count();
        	if (outCount > outFinishCount) {
        		return "缺外出結束";
        	}
            if (LocalDateTime.now().isBefore(shiftEndTime.minusMinutes(10))) {
                return "早退";
            }
        }
        // 檢查外出打卡：必須在上班後且下班前，且不能有未結束的外出打卡
        else if (clockType.getTypeName().equals("外出打卡")) {
            boolean hasClockIn = logsToday.stream()
                    .anyMatch(log -> log.getClockType().getTypeName().equals("上班"));
            boolean hasClockOut = logsToday.stream()
                    .anyMatch(log -> log.getClockType().getTypeName().equals("下班"));
            if (!hasClockIn || hasClockOut) {
                return "非外出打卡時間";
            }
            long outCount = logsToday.stream()
                    .filter(log -> log.getClockType().getTypeName().equals("外出打卡"))
                    .count();
            long outFinishCount = logsToday.stream()
                    .filter(log -> log.getClockType().getTypeName().equals("外出結束"))
                    .count();
            if (outCount > outFinishCount) {
                return "缺外出結束";
            }
        }
        // 檢查外出結束：必須在上班後且下班前，且必須有對應的外出打卡
        else if (clockType.getTypeName().equals("外出結束")) {
            boolean hasClockIn = logsToday.stream()
                    .anyMatch(log -> log.getClockType().getTypeName().equals("上班"));
            boolean hasClockOut = logsToday.stream()
                    .anyMatch(log -> log.getClockType().getTypeName().equals("下班"));
            if (!hasClockIn || hasClockOut) {
                return "非外出打卡時間";
            }
            long outCount = logsToday.stream()
                    .filter(log -> log.getClockType().getTypeName().equals("外出打卡"))
                    .count();
            long outFinishCount = logsToday.stream()
                    .filter(log -> log.getClockType().getTypeName().equals("外出結束"))
                    .count();
            if (outCount <= outFinishCount) {
                return "缺外出打卡";
            }
        }
        return null; // 沒有異常
    }

    private Attendance createNewAttendance(Employee employee, ShiftType shiftType, Status status) {
        Attendance newAttendance = new Attendance();
        newAttendance.setEmployee(employee);
        newAttendance.setShiftType(shiftType);
        newAttendance.setStatus(status);
        newAttendance.setTotalHours(0);
        newAttendance.setRegularHours(8);
        newAttendance.setOvertimeHours(0);
        newAttendance.setFieldWorkHours(0);
        newAttendance.setHasViolation(false);
        return newAttendance;
    }
}
