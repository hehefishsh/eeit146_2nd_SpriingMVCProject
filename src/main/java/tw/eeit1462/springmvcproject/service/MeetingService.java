package tw.eeit1462.springmvcproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.eeit1462.springmvcproject.model.Meeting;
import tw.eeit1462.springmvcproject.repository.MeetingRepository;

@Service
public class MeetingService {
	@Autowired
	private MeetingRepository meetingRepository;

	
	// 查所有會議
	public List<Meeting> getAllMeetings() {
		return meetingRepository.findAllWithDetails();
	}

	// ID 查會議
	public Optional<Meeting> getMeetingById(Integer id) {
		return meetingRepository.findById(id);
	}

	// 新增會議
	public Meeting saveMeeting(Meeting meeting) {
		return meetingRepository.save(meeting);
	}

	// 刪除會議
	public void deleteMeeting(Integer id) {
		meetingRepository.deleteById(id);
	}

	
}
