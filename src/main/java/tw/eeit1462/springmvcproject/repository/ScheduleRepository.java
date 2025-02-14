package tw.eeit1462.springmvcproject.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tw.eeit1462.springmvcproject.model.Schedule;
import tw.eeit1462.springmvcproject.model.ShiftType;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query("SELECT s.shiftType FROM Schedule s WHERE s.employee.employeeId = :employeeId AND s.scheduleDate = :date")
    Optional<ShiftType> findShiftTypeByEmployeeIdAndDate(@Param("employeeId") int employeeId, @Param("date") LocalDate date);
}
