package tw.eeit1462.springmvcproject.service;

import java.time.Duration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.AttendanceViolation;
import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.repository.AttendanceRepository;
import tw.eeit1462.springmvcproject.repository.AttendanceViolationRepository;
import tw.eeit1462.springmvcproject.repository.EmployeeRepository;

@Service
@Transactional
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Autowired
    private AttendanceViolationRepository attendanceViolationsRepository;  // 需要額外的 Repository

    public Attendance getAttendanceWithDetails(int employeeId, LocalDate date) {
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("員工不存在"));

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        Attendance attendance = attendanceRepository.findByEmployeeAndCreatedAtBetween(employee, startOfDay, endOfDay)
            .orElseThrow(() -> new RuntimeException("沒有找到出勤記錄"));

        // 第二次查詢，獲取違規紀錄
        List<AttendanceViolation> violations = attendanceViolationsRepository.findByAttendance(attendance);
        attendance.setAttendanceViolations(violations); // 手動塞入違規紀錄

        return attendance;
    }

}
