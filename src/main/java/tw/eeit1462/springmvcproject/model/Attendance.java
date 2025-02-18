package tw.eeit1462.springmvcproject.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "shift_type_id", nullable = false)
    private ShiftType shiftType;
    
    // 新增 OneToMany 關聯到 AttendanceLogs
    @OneToMany(mappedBy = "attendance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttendanceLogs> attendanceLogs;

    // 新增 OneToMany 關聯到 AttendanceViolations
    @OneToMany(mappedBy = "attendance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AttendanceViolations> attendanceViolations;

    private float totalHours;
    private float regularHours;
    private float overtimeHours;
    private float fieldWorkHours;
    private boolean hasViolation;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    private LocalDateTime createdAt = LocalDateTime.now();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public ShiftType getShiftType() {
		return shiftType;
	}

	public void setShiftType(ShiftType shiftType) {
		this.shiftType = shiftType;
	}

	public float getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(float totalHours) {
		this.totalHours = totalHours;
	}

	public float getRegularHours() {
		return regularHours;
	}

	public void setRegularHours(float regularHours) {
		this.regularHours = regularHours;
	}

	public float getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(float overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public float getFieldWorkHours() {
		return fieldWorkHours;
	}

	public void setFieldWorkHours(float fieldWorkHours) {
		this.fieldWorkHours = fieldWorkHours;
	}

	public boolean isHasViolation() {
		return hasViolation;
	}

	public void setHasViolation(boolean hasViolation) {
		this.hasViolation = hasViolation;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<AttendanceLogs> getAttendanceLogs() {
		return attendanceLogs;
	}

	public void setAttendanceLogs(List<AttendanceLogs> attendanceLogs) {
		this.attendanceLogs = attendanceLogs;
	}

	public List<AttendanceViolations> getAttendanceViolations() {
		return attendanceViolations;
	}

	public void setAttendanceViolation(List<AttendanceViolations> attendanceViolations) {
		this.attendanceViolations = attendanceViolations;
	}
	
}
