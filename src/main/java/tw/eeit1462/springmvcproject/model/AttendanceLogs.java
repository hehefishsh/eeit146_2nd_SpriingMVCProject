package tw.eeit1462.springmvcproject.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance_logs")
public class AttendanceLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parent_attendance_id", nullable = false)
    private Attendance attendance;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private LocalDateTime clockTime;

    @ManyToOne
    @JoinColumn(name = "clock_type_id", nullable = false)
    private Type clockType;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Attendance getAttendance() {
		return attendance;
	}

	public void setAttendance(Attendance attendance) {
		this.attendance = attendance;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDateTime getClockTime() {
		return clockTime;
	}

	public void setClockTime(LocalDateTime clockTime) {
		this.clockTime = clockTime;
	}

	public Type getClockType() {
		return clockType;
	}

	public void setClockType(Type clockType) {
		this.clockType = clockType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	private LocalDateTime createdAt = LocalDateTime.now();
}

