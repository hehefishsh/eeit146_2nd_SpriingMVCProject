package tw.eeit1462.springmvcproject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.eeit1462.springmvcproject.model.Department;
import tw.eeit1462.springmvcproject.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public List<Department> findAllDepartment() {
		List<Department> departments = departmentRepository.findAll();
		return departments;
	}

	public Department findById(Integer id) {
		Optional<Department> department = departmentRepository.findById(id);
		if (department != null) {
			return department.get();
		} else {
			return null;
		}
	}

}
