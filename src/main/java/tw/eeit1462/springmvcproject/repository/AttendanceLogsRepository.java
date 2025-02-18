package tw.eeit1462.springmvcproject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.AttendanceLogs;

@Repository
public interface AttendanceLogsRepository extends JpaRepository<AttendanceLogs, Integer> {
    List<AttendanceLogs> findByEmployee_EmployeeId(int employeeId);

	List<AttendanceLogs> findByAttendance(Attendance attendance);

	List<AttendanceLogs> findByEmployee_EmployeeIdAndClockTimeBetween(int employeeId, LocalDateTime start, LocalDateTime end);

}

