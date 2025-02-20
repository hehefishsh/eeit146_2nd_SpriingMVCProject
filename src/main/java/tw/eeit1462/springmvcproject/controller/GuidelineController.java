package tw.eeit1462.springmvcproject.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tw.eeit1462.springmvcproject.model.Guideline;
import tw.eeit1462.springmvcproject.model.GuidelineContent;
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
    
	@GetMapping("/guideline/detail/{id}")
	public String showEmployeeDetail(@PathVariable("id") Integer guideId,Model model) {
		
		List<GuidelineContent> contentsById = guidelineService.findContentById(guideId);
		Guideline guideline = guidelineService.findGuidelineById(guideId);
		model.addAttribute("contents",contentsById);
		model.addAttribute("guideline",guideline);
		
		return "guidelineDetail";
	}
	
}

