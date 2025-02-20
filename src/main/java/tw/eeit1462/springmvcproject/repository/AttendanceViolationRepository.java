package tw.eeit1462.springmvcproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.eeit1462.springmvcproject.model.AttendanceViolation;

@Repository
public interface AttendanceViolationRepository extends JpaRepository<AttendanceViolation, Integer> {
}

