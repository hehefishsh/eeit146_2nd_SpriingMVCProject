package tw.eeit1462.springmvcproject.model;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;

	private String roleName;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "employee_roles", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "roleId") }, inverseJoinColumns = {
					@JoinColumn(name = "employee_id", referencedColumnName = "employee_id") })
	private List<Employee> employee = new LinkedList<Employee>();

	public Roles() {
		super();
	}

}
