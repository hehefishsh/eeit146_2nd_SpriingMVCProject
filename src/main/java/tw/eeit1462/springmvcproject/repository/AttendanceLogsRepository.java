package tw.eeit1462.springmvcproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.AttendanceLogs;

@Repository
public interface AttendanceLogsRepository extends JpaRepository<AttendanceLogs, Integer> {
    List<AttendanceLogs> findByEmployee_EmployeeId(int employeeId);
}

