package tw.eeit1462.springmvcproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import tw.eeit1462.springmvcproject.config.SecurityConfig;
import tw.eeit1462.springmvcproject.model.Department;
import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.model.Position;
import tw.eeit1462.springmvcproject.model.Status;
import tw.eeit1462.springmvcproject.service.DepartmentService;
import tw.eeit1462.springmvcproject.service.EmployeeService;
import tw.eeit1462.springmvcproject.service.PositionService;

@Controller
public class EmployeeManagementController {

	@Autowired
	private SecurityConfig security;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private PositionService positionService;

    @GetMapping("/employeemanagement")
	public String showEmployeeManagement(Model model) {
		List<Employee> employees = employeeService.findAllEmployee();
		model.addAttribute("employees", employees);
        return "employeemanagement"; 
    }

	@GetMapping("/employee/detail")
	public String showEmployeeDetail(Model model) {
		return "employeedetail";
	}

	@GetMapping("/employee/create")
	public String showEmployeeCreate(Model model) {
		List<Department> departments = departmentService.findAllDepartment();
		model.addAttribute("departments", departments);
		List<Position> positions = positionService.findAllPosition();
		model.addAttribute("positions", positions);
		return "employeecreate";
	}

	@PostMapping("/employeecreate")
	public String CreateEmployee(Model model, String employeeName, String password, String positionid,
			String departmentid, String startDate) {
		System.out.println(employeeName);
		System.out.println(password);
		System.out.println(departmentid);
		System.out.println(positionid);
		System.out.println(startDate);

		// 密碼加密處理
		String encode = security.passwordEncoder().encode(password);
		// 設置在職狀態
		Status status = new Status(1, "在職");

		Department department = departmentService.findById(Integer.valueOf(departmentid));
		Position position = positionService.findById(Integer.valueOf(positionid));
		Employee employee = new Employee(employeeName, encode, position, department, startDate, status);
		employeeService.createEmployee(employee);
		List<Employee> employees = employeeService.findAllEmployee();
		model.addAttribute("employees", employees);
		return "redirect:/employeemanagement";
	}
}
