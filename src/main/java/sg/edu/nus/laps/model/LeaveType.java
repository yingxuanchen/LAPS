package sg.edu.nus.laps.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LeaveType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String leaveType;
	private String designation;
	private int defaultNumOfDays;
	
	public LeaveType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getDefaultNumOfDays() {
		return defaultNumOfDays;
	}

	public void setDefaultNumOfDays(int defaultNumOfDays) {
		this.defaultNumOfDays = defaultNumOfDays;
	}

	@Override
	public String toString() {
		return "LeaveType [leaveType=" + leaveType + ", designation=" + designation + ", defaultNumOfDays="
				+ defaultNumOfDays + "]";
	}

}
