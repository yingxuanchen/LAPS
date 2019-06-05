package sg.edu.nus.laps.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Employee {
	@Id
	@Column(length = 10)
	private String empId;
	private String empName;
	private String pwd;
	private String role;
	private String designation;
	private boolean isAdmin;
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<LeaveRecord> leaveSet = new HashSet<LeaveRecord>();
	@ManyToOne
	@JoinColumn(name = "manager_empid")
	private Employee manager;
	@OneToMany(mappedBy = "manager", fetch = FetchType.EAGER)
	private Set<Employee> subordinates = new HashSet<Employee>();
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<EmpLeave> empLeaves = new HashSet<EmpLeave>();
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<LeaveRecord> getLeaveSet() {
		return leaveSet;
	}

	public void setLeaveSet(Set<LeaveRecord> leaveSet) {
		this.leaveSet = leaveSet;
	}
	
	public void addLeave(LeaveRecord l) {
		this.leaveSet.add(l);
	}
	
	public void removeLeave(LeaveRecord l) {
		this.leaveSet.remove(l);
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Set<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(Set<Employee> subordinates) {
		this.subordinates = subordinates;
	}

	
	public Set<EmpLeave> getEmpLeaves() {
		return empLeaves;
	}

	public void setEmpLeaves(Set<EmpLeave> empLeaves) {
		this.empLeaves = empLeaves;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", role=" + role + ", designation=" + designation
				+ ", manager=" + manager + "]";
	}
	
}
