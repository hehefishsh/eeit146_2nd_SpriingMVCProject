package tw.eeit1462.springmvcproject.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.AttendanceLogs;
import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.model.ShiftType;
import tw.eeit1462.springmvcproject.model.Status;
import tw.eeit1462.springmvcproject.model.Type;
import tw.eeit1462.springmvcproject.repository.AttendanceLogsRepository;
import tw.eeit1462.springmvcproject.repository.AttendanceRepository;
import tw.eeit1462.springmvcproject.repository.EmployeeRepository;
import tw.eeit1462.springmvcproject.repository.ScheduleRepository;
import tw.eeit1462.springmvcproject.repository.StatusRepository;
import tw.eeit1462.springmvcproject.repository.TypeRepository;

@Service
@Transactional
public class AttendanceLogsService {

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
    
    public List<AttendanceLogs> getLogsByEmployeeId(int employeeId) {
        return attendanceLogsRepository.findByEmployee_EmployeeId(employeeId);
    }

    public void clockInOrOut(int employeeId, int clockTypeId) {
    	 // 取得 Employee 實體
        Employee employee = employeeRepository.findById(employeeId)
                                .orElseThrow(() -> new RuntimeException("員工不存在"));

        // 取得 ClockType 實體
        Type clockType = typeRepository.findById(clockTypeId)
                                .orElseThrow(() -> new RuntimeException("打卡類型不存在"));

        // 取得今日日期
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime todayEnd = todayStart.plusDays(1);

        // 取得當天的 ShiftType（根據 Schedule 表）
        ShiftType shiftType = scheduleRepository.findShiftTypeByEmployeeIdAndDate(employeeId, LocalDateTime.now().toLocalDate())
                                .orElseThrow(() -> new RuntimeException("沒有找到今日班別"));

        // 取得出勤狀態 (例如：出勤中)
        Status status = statusRepository.findByStatusName("在職")
                                .orElseThrow(() -> new RuntimeException("出勤狀態不存在"));

        // 取得當天的 Attendance，如果沒有就新建
        Attendance attendance = attendanceRepository
                .findByEmployeeAndCreatedAtBetween(employee, todayStart, todayEnd)
                .orElseGet(() -> attendanceRepository.save(createNewAttendance(employee, shiftType, status)));

        // 建立 AttendanceLogs 實體
        AttendanceLogs log = new AttendanceLogs();
        log.setAttendance(attendance);
        log.setEmployee(employee);
        log.setClockType(clockType);
        log.setClockTime(LocalDateTime.now());

        // 儲存打卡紀錄
        attendanceLogsRepository.save(log);
    }
    private Attendance createNewAttendance(Employee employee, ShiftType shiftType, Status status) {
        Attendance newAttendance = new Attendance();
        newAttendance.setEmployee(employee);
        newAttendance.setShiftType(shiftType);
        newAttendance.setStatus(status);
        newAttendance.setTotalHours(0);
        newAttendance.setRegularHours(8); // 預設 8 小時
        newAttendance.setOvertimeHours(0);
        newAttendance.setFieldWorkHours(0);
        newAttendance.setHasViolation(false);
        return newAttendance;
    }
}

