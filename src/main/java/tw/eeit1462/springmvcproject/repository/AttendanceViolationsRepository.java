package tw.eeit1462.springmvcproject.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.AttendanceViolations;

public interface AttendanceViolationsRepository extends JpaRepository<AttendanceViolations, Integer> {

	List<AttendanceViolations> findByAttendance(Attendance attendance);
}

