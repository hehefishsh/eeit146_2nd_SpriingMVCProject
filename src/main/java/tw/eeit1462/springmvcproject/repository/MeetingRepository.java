package tw.eeit1462.springmvcproject.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.model.Meeting;
import tw.eeit1462.springmvcproject.model.Room;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

	// 查員工
	List<Meeting> findByEmployee(Employee employee);

	// 查會議室
	List<Meeting> findByRoom(Room room);

	// 查時間
	List<Meeting> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

	@Query("SELECT m FROM Meeting m JOIN FETCH m.employee JOIN FETCH m.room")
	List<Meeting> findAllWithDetails();
}
