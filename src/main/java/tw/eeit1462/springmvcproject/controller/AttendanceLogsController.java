package tw.eeit1462.springmvcproject.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.service.AttendanceLogsService;

@Controller
public class AttendanceLogsController {

    @Autowired
    private AttendanceLogsService attendanceLogsService;

    @GetMapping("/clock")
    public String showClockPage(Model model) {
        // 取得當前登入的 Employee
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee currentUser = (Employee) authentication.getPrincipal();
        Integer employeeId = currentUser.getEmployeeId();

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        model.addAttribute("currentTime", currentTime);
        model.addAttribute("employeeId", employeeId);  
        return "clock";
    }


    @PostMapping("/clock")
    public String clockInOrOut(
            @RequestParam("clockTypeId") int clockTypeId,
            RedirectAttributes redirectAttributes) {

        // 從 Spring Security 中取得 employeeId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee currentUser = (Employee) authentication.getPrincipal();
        Integer employeeId = currentUser.getEmployeeId();

        // 執行打卡邏輯
        attendanceLogsService.clockInOrOut(employeeId, clockTypeId);

        // 將成功訊息傳遞給 GET /clock
        redirectAttributes.addFlashAttribute("message", "打卡成功！");

        // 避免重複提交，重新導向到 GET /clock
        return "redirect:/clock";
    }
}
