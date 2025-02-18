package tw.eeit1462.springmvcproject.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.Employee;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
	
	@EntityGraph(attributePaths = {"attendanceLogs"})  // 只先抓 logs
    Optional<Attendance> findByEmployeeAndCreatedAtBetween(Employee employee, LocalDateTime start, LocalDateTime end);

	
	@Query("SELECT a FROM Attendance a LEFT JOIN FETCH a.attendanceLogs LEFT JOIN FETCH a.attendanceViolations WHERE a.employee.id = :employeeId")
	List<Attendance> findByEmployeeIdWithDetails(@Param("employeeId") int employeeId);


	List<Attendance> findAllAttendancesByEmployeeAndCreatedAtBetween(Employee employee, LocalDateTime startOfYear,
			LocalDateTime endOfYear);

}
