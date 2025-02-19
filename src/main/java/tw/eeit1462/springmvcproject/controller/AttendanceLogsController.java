package tw.eeit1462.springmvcproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tw.eeit1462.springmvcproject.exception.AttendanceNotFoundException;
import tw.eeit1462.springmvcproject.exception.AttendanceTodayNotFoundException;
import tw.eeit1462.springmvcproject.exception.EmployeeNotFoundException;
import tw.eeit1462.springmvcproject.model.Attendance;
import tw.eeit1462.springmvcproject.model.Employee;
import tw.eeit1462.springmvcproject.service.AttendanceService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class AttendanceLogsController {

	@Autowired
	private AttendanceService attendanceService;

	@GetMapping("/attendancelogs")
	public String showAttendanceLogs(@RequestParam(required = false) String date, Model model) {
		// 取得當前登入的 Employee
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Employee currentUser = (Employee) authentication.getPrincipal();
		Integer employeeId = currentUser.getEmployeeId();

		try {// 查詢當天的 Attendance，包含 logs 和 violations
			Attendance todayAttendance = attendanceService.getAttendanceWithDetails(employeeId, LocalDate.now());
			model.addAttribute("todayAttendance", todayAttendance);

			// 查詢當月的 Attendance
			LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
			List<Attendance> monthlyAttendances = attendanceService.getAttendancesForMonth(employeeId, startOfMonth);
			model.addAttribute("monthlyAttendances", monthlyAttendances);

			// 查詢當年的 Attendance
			LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
			List<Attendance> yearlyAttendances = attendanceService.getAttendancesForYear(employeeId, startOfYear);
			model.addAttribute("yearlyAttendances", yearlyAttendances);

			// 如果提供了日期，查詢指定日期的考勤紀錄
			if (date != null && !date.isEmpty()) {
				LocalDate queryDate = LocalDate.parse(date);
				Attendance attendance = attendanceService.getAttendanceByDate(employeeId, queryDate);
				model.addAttribute("attendance", attendance);
				model.addAttribute("queryDate", queryDate); // 用於顯示查詢的日期
			}

			return "attendancelogs"; // Thymeleaf 頁面名稱
		} catch (EmployeeNotFoundException e) {
			model.addAttribute("error", e.getMessage());
			return "attendancelogs";
		} catch (AttendanceTodayNotFoundException ate) {
			model.addAttribute("atError", ate.getMessage());
			// 如果提供了日期，查詢指定日期的考勤紀錄
			try {
				if (date != null && !date.isEmpty()) {

					LocalDate queryDate = LocalDate.parse(date);
					Attendance attendance = attendanceService.getAttendanceByDate(employeeId, queryDate);
					model.addAttribute("attendance", attendance);
					model.addAttribute("queryDate", queryDate); // 用於顯示查詢的日期
				}
				return "attendancelogs";
			} catch (AttendanceNotFoundException ae) {
				model.addAttribute("aError", ae.getMessage());
				return "attendancelogs";
			}
		} catch (AttendanceNotFoundException ae) {
			model.addAttribute("aError", ae.getMessage());
			System.out.println(ae.getMessage());
			// 查詢當天的 Attendance，包含 logs 和 violations
			Attendance todayAttendance = attendanceService.getAttendanceWithDetails(employeeId, LocalDate.now());
			model.addAttribute("todayAttendance", todayAttendance);

			// 查詢當月的 Attendance
			LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
			List<Attendance> monthlyAttendances = attendanceService.getAttendancesForMonth(employeeId, startOfMonth);
			model.addAttribute("monthlyAttendances", monthlyAttendances);

			// 查詢當年的 Attendance
			LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
			List<Attendance> yearlyAttendances = attendanceService.getAttendancesForYear(employeeId, startOfYear);
			model.addAttribute("yearlyAttendances", yearlyAttendances);
			return "attendancelogs";
		}
	}
}
