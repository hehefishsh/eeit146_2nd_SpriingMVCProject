package tw.eeit1462.springmvcproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tw.eeit1462.springmvcproject.model.Guideline;
import tw.eeit1462.springmvcproject.service.GuidelineService;

@Controller
public class GuidelineController {

	@Autowired
	private GuidelineService guidelineService;
	
    @GetMapping("/guideline")
    public String showGuileline(Model model) {
    	List<Guideline> allGuideline = guidelineService.findAllGuideline();
        model.addAttribute("allGuideline",allGuideline);
    	return "guideline"; 
    }
}

