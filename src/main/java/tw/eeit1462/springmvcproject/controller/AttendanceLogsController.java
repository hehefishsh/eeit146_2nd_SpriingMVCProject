package tw.eeit1462.springmvcproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceLogsController {

    @GetMapping("/attendancelogs")
    public String showEmployeeManagement() {
        return "attendancelogs"; 
    }
}
