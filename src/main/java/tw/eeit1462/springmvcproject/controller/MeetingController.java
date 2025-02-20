package tw.eeit1462.springmvcproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.PostConstruct;
import tw.eeit1462.springmvcproject.model.Meeting;
import tw.eeit1462.springmvcproject.service.EmployeeService;
import tw.eeit1462.springmvcproject.service.MeetingService;
import tw.eeit1462.springmvcproject.service.RoomService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/meetings")
public class MeetingController {

	@Autowired
	private MeetingService meetingService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private RoomService roomService;

	// 進入meetings頁面時載入所有會議
	@GetMapping
	public String getAllMeetings(Model model) {
		List<Meeting> meetings = meetingService.getAllMeetings();
		model.addAttribute("meetings", meetings);
		return "meetingroom";
	}

	// 顯示新增會議表單
	@GetMapping("/new")
	public String newMeetingForm(Model model) {
		model.addAttribute("meeting", new Meeting());
		model.addAttribute("employees", employeeService.findAllEmployee());
		model.addAttribute("rooms", roomService.getAllRooms());
		return "meetingform";
	}

	// 檢查必要欄位
	@PostMapping("/save")
	public String saveMeeting(@ModelAttribute Meeting meeting, Model model) {
		if (meeting == null || meeting.getEmployee() == null || meeting.getRoom() == null
				|| meeting.getStartTime() == null || meeting.getEndTime() == null) {
			model.addAttribute("errorMessage", "請填寫完整的會議資訊");
			return "meetingform";
		}

		meetingService.saveMeeting(meeting);
		return "redirect:/meetingroom";
	}

	// 刪除會議
	@GetMapping("/delete/{id}")
	public String deleteMeeting(@PathVariable Integer id, Model model) {
		Optional<Meeting> optionalMeeting = meetingService.getMeetingById(id);

		if (optionalMeeting.isPresent()) {
			meetingService.deleteMeeting(id);
		} else {
			model.addAttribute("errorMessage", "找不到該會議");
			return "meetings";
		}
		return "redirect:/meetingroom"; 
	}

}
