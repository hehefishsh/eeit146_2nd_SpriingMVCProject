package tw.eeit1462.springmvcproject.model;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionId;

    @Column(name = "position_name", nullable = false, length = 50)
    private String positionName;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<Employee> employees;

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
}
