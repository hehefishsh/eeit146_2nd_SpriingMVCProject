package tw.eeit1462.springmvcproject.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

	Optional<Attendance> findByEmployeeAndCreatedAtBetween(Employee employee, LocalDateTime start, LocalDateTime end);
	

}
