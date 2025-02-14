package tw.eeit1462.springmvcproject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.repository.EmployeeRepository;

@Controller
public class LoginController {

//    @Autowired
//    private EmployeeRepository employeeRepository;

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "登入失敗，請檢查帳號和密碼。");
        }
        return "login";
    }

//    @PostMapping("/login")
//    public String login(@RequestParam String employeeName, @RequestParam String password, Model model) {
//        Employee employee = employeeRepository.findByEmployeeNameAndPassword(employeeName, password);
//        if (employee != null) {
//            return "redirect:/clock";
//        } else {
//            model.addAttribute("error", "Invalid credentials");
//            return "login";
//        }
//    }
}
