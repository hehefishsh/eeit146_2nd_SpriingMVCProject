package tw.eeit1462.springmvcproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tw.eeit1462.springmvcproject.model.Meeting;
import tw.eeit1462.springmvcproject.service.MeetingService;

@Controller
public class MeetingRoomController {

    @Autowired
    private MeetingService meetingService; 

    @GetMapping("/meetingroom")
    public String getAllMeetingRooms(Model model) {
        List<Meeting> meetings = meetingService.getAllMeetings(); 
        model.addAttribute("meetings", meetings); 
        return "meetingroom"; 
    }
} 


