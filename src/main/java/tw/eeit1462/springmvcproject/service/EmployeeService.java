package tw.eeit1462.springmvcproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> findAllEmployee() {
		List<Employee> employees = employeeRepository.findAll();
		return employees;
	}

	public void createEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

}
