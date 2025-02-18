package tw.eeit1462.springmvcproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.service.AttendanceService;

import java.time.LocalDate;

@Controller
public class AttendanceLogsController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/attendancelogs")
    public String showAttendanceLogs(Model model) {
        // 取得當前登入的 Employee
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee currentUser = (Employee) authentication.getPrincipal();
        Integer employeeId = currentUser.getEmployeeId();
        // 查詢當天的 Attendance，包含 logs 和 violations
        Attendance attendance = attendanceService.getAttendanceWithDetails(employeeId, LocalDate.now());

        model.addAttribute("attendance", attendance);
        return "attendancelogs"; // Thymeleaf 頁面名稱
    }
}
