//package tw.eeit1462.springmvcproject.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import tw.eeit1462.springmvcproject.model.Attendance;
//import tw.eeit1462.springmvcproject.model.Employee;
//import tw.eeit1462.springmvcproject.repository.AttendanceLogsRepository;
//import tw.eeit1462.springmvcproject.repository.AttendanceRepository;
//import tw.eeit1462.springmvcproject.repository.EmployeeRepository;
//
//import java.time.LocalDateTime;
//
//@Controller
//public class ClockController { 
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private AttendanceLogsRepository attendanceLogsRepository;
//
//    @GetMapping("/clock")
//    public String clockPage(Model model) {
//        model.addAttribute("currentTime", LocalDateTime.now().toString());
//        return "clock";
//    }
//
//    @PostMapping("/clock-in")
//    public String clockIn(@RequestParam int employeeId, Model model) {
//        Employee employee = employeeRepository.findById(employeeId).orElse(null);
//        if (employee != null) {
//            Attendance attendance = new Attendance();
//            attendance.setEmployee(employee);
//            attendance.setCreatedAt(LocalDateTime.now());
//            attendanceLogsRepository.save(attendance);
//            model.addAttribute("message", "Clocked in successfully");
//        } else {
//            model.addAttribute("error", "Employee not found");
//        }
//        return "clock";
//    }
//}
