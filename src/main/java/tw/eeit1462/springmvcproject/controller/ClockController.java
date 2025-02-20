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
import tw.eeit1462.springmvcproject.service.ClockService;

@Controller
public class ClockController {

    @Autowired
    private ClockService clockService;

    @GetMapping("/clock")
    public String showClockPage(Model model) {
        // 取得當前登入的 Employee
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee currentUser = (Employee) authentication.getPrincipal();
        Integer employeeId = currentUser.getEmployeeId();

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        model.addAttribute("currentTime", currentTime);
        model.addAttribute("employeeId", employeeId);
        
        // 將之前的打卡異常消息顯示在前端
        if (model.containsAttribute("errorMessage")) {
            model.addAttribute("errorMessage", model.getAttribute("errorMessage"));
        }

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

        // 執行打卡邏輯，並捕獲異常
        try {
            String exceptionMessage = clockService.clockInOrOut(employeeId, clockTypeId);
            if(exceptionMessage=="重複打卡") {
            	redirectAttributes.addFlashAttribute("errorMessage", exceptionMessage);
            }else if(exceptionMessage!=null){
            	redirectAttributes.addFlashAttribute("errorMessage", exceptionMessage);
            }else {
            redirectAttributes.addFlashAttribute("message", "打卡成功！");   
            }     	
        } catch (RuntimeException e) {
            // 捕獲異常並顯示錯誤消息
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        // 避免重複提交，重新導向到 GET /clock
        return "redirect:/clock";
    }
}
