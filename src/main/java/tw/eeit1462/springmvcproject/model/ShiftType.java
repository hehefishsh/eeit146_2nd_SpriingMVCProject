package tw.eeit1462.springmvcproject.model;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "shift_type")
public class ShiftType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shiftTypeId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "shift_name", nullable = false, length = 50)
    private String shiftName;

    @Column(name = "shift_category", nullable = false, length = 10)
    private String shiftCategory;

    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Column(name = "estimated_hours", nullable = false)
    private Integer estimatedHours;

    @OneToMany(mappedBy = "shiftType", cascade = CascadeType.ALL)
    private List<Schedule> schedules;

	public Integer getShiftTypeId() {
		return shiftTypeId;
	}

	public void setShiftTypeId(Integer shiftTypeId) {
		this.shiftTypeId = shiftTypeId;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	public String getShiftCategory() {
		return shiftCategory;
	}

	public void setShiftCategory(String shiftCategory) {
		this.shiftCategory = shiftCategory;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(Integer estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
}