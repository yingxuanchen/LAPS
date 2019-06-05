package sg.edu.nus.laps.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.laps.javabean.UserSession;
import sg.edu.nus.laps.model.Employee;
import sg.edu.nus.laps.model.LeaveRecord;
import sg.edu.nus.laps.service.LeaveServiceIF;
import sg.edu.nus.laps.validator.LeaveRecordValidator;

@Controller
public class PersonalController {

	private LeaveServiceIF lService;
	private LeaveRecordValidator lValidator;
	
	@Autowired
	public void setlService(LeaveServiceIF lService) {
		this.lService = lService;
	}
	@Autowired
	public void setlValidator(LeaveRecordValidator lValidator) {
		this.lValidator = lValidator;
	}

	@InitBinder("leaveRecord")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(lValidator);
	}
	
	@RequestMapping(path = "/balance", method = RequestMethod.GET)
	public ModelAndView viewBalance(HttpSession session) {
		ModelAndView mav = new ModelAndView("Balance");
		UserSession us = (UserSession)session.getAttribute("us");
		mav.addObject("empLeaves", us.getUser().getEmpLeaves());
		return mav;
	}
	
	@RequestMapping(path = "/apply", method = RequestMethod.GET)
	public ModelAndView viewLeaveForm(@ModelAttribute LeaveRecord leaveRecord) {
		//get all leave types (for dropdown)
		ModelAndView mav = new ModelAndView("LeaveForm");
		mav.addObject("leaveTypes", lService.findAllLeaveTypes());
		
		// get new empty leave if not existing validated leave
		if (leaveRecord == null)
			leaveRecord = new LeaveRecord();
		
		mav.addObject("leaveRecord", leaveRecord);
		return mav;
	}
	
	@RequestMapping(path = "/apply", method = RequestMethod.POST)
	public ModelAndView applyLeave(@Valid LeaveRecord leaveRecord, 
			BindingResult bindingResult, HttpSession session) {
		
		// redirect back to leave application page if start or end date is not valid
		if (bindingResult.hasErrors()) {
			return viewLeaveForm(leaveRecord);
		}
		
		UserSession us = (UserSession)session.getAttribute("us");
		leaveRecord.setEmployee(us.getUser());
		
		leaveRecord.setNumOfDays(lService.numOfWorkingDays(leaveRecord.getStartDate(), leaveRecord.getEndDate()));
		leaveRecord.setLeaveStatus("Applied");
		
		if(lService.applyLeave(leaveRecord, bindingResult))
			return new ModelAndView("Success");
		else
			return viewLeaveForm(leaveRecord);
	}
	
	@RequestMapping(path = "/history", method = RequestMethod.GET)
	public ModelAndView viewLeaveHistory(HttpSession session) {
		UserSession us = (UserSession)session.getAttribute("us");
		Employee emp = us.getUser();
		Set<LeaveRecord> leaves = emp.getLeaveSet();
		
		ModelAndView mav = new ModelAndView("History");
		mav.addObject("leaves", leaves);
		return mav;
	}
	
	@RequestMapping(path = "/leave/{id}", method = RequestMethod.GET)
    public ModelAndView viewLeave(@PathVariable int id) {
		LeaveRecord leave = lService.findLeaveById(id);			
		return new ModelAndView("ViewLeave", "leave", leave);
    }

}
