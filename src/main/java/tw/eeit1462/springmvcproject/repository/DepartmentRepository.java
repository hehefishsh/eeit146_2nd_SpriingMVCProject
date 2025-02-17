package tw.eeit1462.springmvcproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tw.eeit1462.springmvcproject.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
