package sg.edu.nus.laps.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import sg.edu.nus.laps.model.EmpLeave;
import sg.edu.nus.laps.model.Employee;
import sg.edu.nus.laps.model.LeaveRecord;
import sg.edu.nus.laps.repo.EmpLeaveRepository;
import sg.edu.nus.laps.repo.EmployeeRepository;
import sg.edu.nus.laps.repo.LeaveRecordRepository;
import sg.edu.nus.laps.repo.LeaveTypeRepository;
import sg.edu.nus.laps.repo.PublicHolidayRepository;

@Service
public class LeaveService implements LeaveServiceIF {

	private EmpLeaveRepository elRepo;
	private EmployeeRepository eRepo;
	private LeaveRecordRepository lRepo;
	private LeaveTypeRepository ltRepo;
	private PublicHolidayRepository phRepo;
	
	@Autowired
	public void setElRepo(EmpLeaveRepository elRepo) {
		this.elRepo = elRepo;
	}
	@Autowired
	public void seteRepo(EmployeeRepository eRepo) {
		this.eRepo = eRepo;
	}
	@Autowired
	public void setlRepo(LeaveRecordRepository lRepo) {
		this.lRepo = lRepo;
	}
	@Autowired
	public void setLtRepo(LeaveTypeRepository ltRepo) {
		this.ltRepo = ltRepo;
	}
	@Autowired
	public void setPhRepo(PublicHolidayRepository phRepo) {
		this.phRepo = phRepo;
	}

	@Transactional
	@Override
	public boolean applyLeave(LeaveRecord l, BindingResult bindingResult) {
		Employee emp = l.getEmployee();
		EmpLeave empLeave = elRepo.findByEmployeeAndLeaveType(emp, l.getLeaveType());
		double leaveBal = empLeave.getLeaveBal();
		double numOfDays = l.getNumOfDays();
		
		// cannot apply leave if num of days is more than balance
		if (numOfDays > leaveBal) {
			String[] codes = {"error.leaverecord.balance"};
			Object[] args = {String.format ("%.1f", numOfDays), String.format ("%.1f", leaveBal), l.getLeaveType()};
			bindingResult.addError(new ObjectError("l", codes, args, "default msg"));
			return false;
		}
		
		// cannot apply leave if there are other applied/updated/approved leaves in same period
		if (!hasNoConflictingLeave(emp, l.getStartDate(), l.getEndDate())) {
			String[] codes = {"error.leaverecord.conflict"};
			bindingResult.addError(new ObjectError("l", codes, null, "default msg"));
			return false;
		}
		
		// reduce leave balance
		empLeave.setLeaveBal(leaveBal - numOfDays);
		
		// save leave application and balance to database
		elRepo.save(empLeave);
		lRepo.save(l);
		
		// fetch updated set of leave applications and balance for employee
		emp.setEmpLeaves(elRepo.findByEmployee(emp));
		emp.setLeaveSet(lRepo.findByEmployee(emp));
		
		return true;
	}
	
	@Transactional
	@Override
	public Employee authenticate(String empId, String pwd) {
		return eRepo.findByEmpIdAndPwd(empId, pwd);
	}
	
	// convert date from Date to LocalDate
	@Override
	public LocalDate convertDateToLocalDate(Date date) {
	    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	@Override
	public Set<String> findAllLeaveTypes() {
		return ltRepo.findAllLeaveTypes();
	}
	
	@Override
	public EmpLeave findEmpLeaveByEmployeeAndLeaveType(Employee emp, String leaveType) {
		return elRepo.findByEmployeeAndLeaveType(emp, leaveType);
	}
	
	@Override
	public LeaveRecord findLeaveById(int id) {
		return lRepo.findById(id).orElse(null);
	}
	
	@Override
	public Set<Employee> findSubordinatesByManager(Employee manager) {
		return eRepo.findSubordinatesByManager(manager);
	}
	
	@Override
	public boolean hasNoConflictingLeave(Employee emp, Date start, Date end) {
		Set<LeaveRecord> leaves = lRepo.findValidLeavesByEmployee(emp);
		for (LeaveRecord l : leaves)
			if (l.getStartDate().compareTo(end) <= 0)
				if (l.getEndDate().compareTo(start) >= 0)
					return false;
		
		return true;
	}
	
	@Override
	public boolean isWorkingDay(Date date) {
		LocalDate lDate = convertDateToLocalDate(date);
		if(lDate.getDayOfWeek() == DayOfWeek.SATURDAY)
			return false;
		if(lDate.getDayOfWeek() == DayOfWeek.SUNDAY)
			return false;
		
		// get public holiday dates
		Set<Date> phDates = phRepo.findAllPublicHolidayDates();
		if(phDates.contains(date))
			return false;
		
		return true;
	}
	
	// calculate number of working days between 2 dates
	@Override
	public int numOfWorkingDays(Date start, Date end) {
		
		int count = 0;
		
		// get public holiday dates
		Set<Date> phDates = phRepo.findAllPublicHolidayDates();
		
		// convert all Date to LocalDate because plusDays method only exists for LocalDate
		LocalDate startDate = convertDateToLocalDate(start);
		LocalDate endDate = convertDateToLocalDate(end);
		Set<LocalDate> phLocalDates = new HashSet<LocalDate>();
		for(Date phDate : phDates) {
			phLocalDates.add(convertDateToLocalDate(phDate));
		}
		
		// iterate through each date to check if the date is weekend or public holiday
		for(LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
			if(date.getDayOfWeek() == DayOfWeek.SATURDAY)
				continue;
			if(date.getDayOfWeek() == DayOfWeek.SUNDAY)
				continue;
			if(phLocalDates.contains(date))
				continue;
			count++;
		}
		
		return count;
	}
	
	@Transactional
	@Override
	public boolean updateEmpLeave(EmpLeave empLeave) {
		elRepo.save(empLeave);				
		return true;
	}
	
	@Transactional
	@Override
	public boolean updateLeave(LeaveRecord l) {
		lRepo.save(l);				
		return true;
	}
}
