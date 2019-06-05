package sg.edu.nus.laps.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

import org.springframework.validation.BindingResult;

import sg.edu.nus.laps.model.EmpLeave;
import sg.edu.nus.laps.model.Employee;
import sg.edu.nus.laps.model.LeaveRecord;

public interface LeaveServiceIF {

	boolean applyLeave(LeaveRecord l, BindingResult bindingResult);
	
	Employee authenticate(String empId, String pwd);
	
	LocalDate convertDateToLocalDate(Date date);
	
	Set<String> findAllLeaveTypes();
	
	EmpLeave findEmpLeaveByEmployeeAndLeaveType(Employee emp, String leaveType);
	
	LeaveRecord findLeaveById(int id);
	
	Set<Employee> findSubordinatesByManager(Employee manager);
	
	boolean hasNoConflictingLeave(Employee emp, Date start, Date end);
	
	boolean isWorkingDay(Date date);
	
	int numOfWorkingDays(Date start, Date end);
	
	boolean updateEmpLeave(EmpLeave empLeave);
	
	boolean updateLeave(LeaveRecord l);
}