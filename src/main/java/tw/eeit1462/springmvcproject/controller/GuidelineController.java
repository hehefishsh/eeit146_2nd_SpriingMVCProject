package tw.eeit1462.springmvcproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuidelineController {

    @GetMapping("/guideline")
    public String showGuileline() {
        return "guideline"; 
    }
}

