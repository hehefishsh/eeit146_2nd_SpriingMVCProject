package tw.eeit1462.springmvcproject.model;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "hiredate")
    private String hireDate;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

	@ManyToMany(mappedBy = "employee")
	private List<Roles> roles = new LinkedList<Roles>();

	public Employee() {
		super();
	}

	public Employee(String employeeName, String password, Position position, Department department, String hireDate,
			Status status) {
		this.employeeName = employeeName;
		this.password = password;
		this.position = position;
		this.department = department;
		this.hireDate = hireDate;
		this.status = status;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList(); // 暫時不需要角色權限
    }

//	@Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//		List<GrantedAuthority> authorities = new ArrayList<>();
//
//		// 假設 employee 是當前用戶的 Employee 物件，並且有 roles 集合
//		for (Roles role : employee.getRoles()) {
//			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())); // 角色名稱加上 "ROLE_" 前綴
//		}
//
//		return authorities;
//    }

    @Override
    public String getUsername() {
        return employeeName; // 使用員工姓名作為用戶名
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 帳號未過期
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 帳號未鎖定
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 憑證未過期
    }

    @Override
    public boolean isEnabled() {
        return true; // 帳號啟用
    }

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", password=" + password
				+ ", position=" + position + ", department=" + department + ", hireDate=" + hireDate + ", status="
				+ status + "]";
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	
}