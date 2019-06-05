package sg.edu.nus.laps.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.nus.laps.model.Employee;

@Component
public class EmployeeValidator implements Validator{
	
	/** This Validator validates *just* Employee instances **/
	@Override
	public boolean supports(Class<?> clazz) {
		return Employee.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "empId", "error.employee.empid.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "pwd", "error.employee.pwd.empty");
	}

}
