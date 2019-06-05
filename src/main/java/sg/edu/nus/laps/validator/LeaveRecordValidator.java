package sg.edu.nus.laps.validator;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.edu.nus.laps.model.LeaveRecord;
import sg.edu.nus.laps.service.LeaveServiceIF;

@Component
public class LeaveRecordValidator implements Validator{
	
	private LeaveServiceIF lService;
	@Autowired
	public void setlService(LeaveServiceIF lService) {
		this.lService = lService;
	}
	
	/** This Validator validates *just* LeaveRecord instances **/
	@Override
	public boolean supports(Class<?> clazz) {
		return LeaveRecord.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		LeaveRecord leave = (LeaveRecord) obj;
		Date start = leave.getStartDate();
		Date end = leave.getEndDate();
		
		// start and end date cannot be empty
		if (start == null || end == null) {
			e.reject("error.leaverecord.date.empty");
			return;
		}
			
		// start and end date cannot be on weekends on public holiday
		if ( !lService.isWorkingDay(start) || !lService.isWorkingDay(end) )
			e.reject("error.leaverecord.date.notworkingday");
		
	}

}
