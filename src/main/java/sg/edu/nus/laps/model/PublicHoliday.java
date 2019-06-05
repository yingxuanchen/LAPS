package sg.edu.nus.laps.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class PublicHoliday {
	
	@Id
	private Date phDate;
	@NotBlank
	private String phName;
	
	public PublicHoliday() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Date getPhDate() {
		return phDate;
	}

	public void setPhDate(Date phDate) {
		this.phDate = phDate;
	}

	public String getPhName() {
		return phName;
	}

	public void setPhName(String phName) {
		this.phName = phName;
	}

	@Override
	public String toString() {
		return "PublicHoliday [phDate=" + phDate + ", phName=" + phName + "]";
	}
	
}
