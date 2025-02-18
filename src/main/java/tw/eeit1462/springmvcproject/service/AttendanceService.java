package tw.eeit1462.springmvcproject.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.AttendanceViolations;
import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.repository.AttendanceRepository;
import tw.eeit1462.springmvcproject.repository.AttendanceViolationsRepository;
import tw.eeit1462.springmvcproject.repository.EmployeeRepository;

@Service
@Transactional
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AttendanceViolationsRepository attendanceViolationsRepository;

    public Attendance getAttendanceWithDetails(int employeeId, LocalDate date) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("員工不存在"));

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        Attendance attendance = attendanceRepository.findByEmployeeAndCreatedAtBetween(employee, startOfDay, endOfDay)
            .orElseThrow(() -> new RuntimeException("沒有找到出勤記錄"));

        // 獲取違規紀錄
        List<AttendanceViolations> violations = attendanceViolationsRepository.findByAttendance(attendance);
        attendance.setAttendanceViolation(violations);

        return attendance;
    }

    public List<Attendance> getAttendancesForMonth(int employeeId, LocalDate month) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("員工不存在"));

        LocalDateTime startOfMonth = month.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = month.plusMonths(1).withDayOfMonth(1).atStartOfDay();

        return attendanceRepository.findAllAttendancesByEmployeeAndCreatedAtBetween(employee, startOfMonth, endOfMonth);
    }

    public List<Attendance> getAttendancesForYear(int employeeId, LocalDate year) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("員工不存在"));

        LocalDateTime startOfYear = year.withDayOfYear(1).atStartOfDay();
        LocalDateTime endOfYear = year.plusYears(1).withDayOfYear(1).atStartOfDay();

        return attendanceRepository.findAllAttendancesByEmployeeAndCreatedAtBetween(employee, startOfYear, endOfYear);
    }
    // 新增的指定日期查詢方法
    public Attendance getAttendanceByDate(int employeeId, LocalDate date) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("員工不存在"));

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        Attendance attendance = attendanceRepository.findByEmployeeAndCreatedAtBetween(employee, startOfDay, endOfDay)
            .orElseThrow(() -> new RuntimeException("沒有找到該日期的出勤記錄"));

        // 獲取違規紀錄
        List<AttendanceViolations> violations = attendanceViolationsRepository.findByAttendance(attendance);
        attendance.setAttendanceViolation(violations);

        return attendance;
    }
}
