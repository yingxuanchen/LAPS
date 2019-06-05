package sg.edu.nus.laps.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EmpLeave {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "employee_empid")
	private Employee employee;
	private String leaveType;
	private int leaveEntitlement;
	private double leaveBal;
	
	public EmpLeave() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public int getLeaveEntitlement() {
		return leaveEntitlement;
	}

	public void setLeaveEntitlement(int leaveEntitlement) {
		this.leaveEntitlement = leaveEntitlement;
	}

	public double getLeaveBal() {
		return leaveBal;
	}

	public void setLeaveBal(double leaveBal) {
		this.leaveBal = leaveBal;
	}

	@Override
	public String toString() {
		return "LeaveEmp [employee=" + employee + ", leaveType=" + leaveType + ", leaveEntitlement=" + leaveEntitlement
				+ ", leaveBal=" + leaveBal + "]";
	}
	
}
