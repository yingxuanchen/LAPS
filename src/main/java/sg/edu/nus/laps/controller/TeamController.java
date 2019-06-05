package sg.edu.nus.laps.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.laps.javabean.UserSession;
import sg.edu.nus.laps.model.EmpLeave;
import sg.edu.nus.laps.model.Employee;
import sg.edu.nus.laps.model.LeaveRecord;
import sg.edu.nus.laps.service.LeaveServiceIF;

@Controller
public class TeamController {

	private LeaveServiceIF lService;
	@Autowired
	public void setlService(LeaveServiceIF lService) {
		this.lService = lService;
	}
	
	@RequestMapping(path = "/applications", method = RequestMethod.GET)
	public ModelAndView viewApplications(HttpSession session) {
		UserSession us = (UserSession)session.getAttribute("us");
		Employee manager = us.getUser();
		Set<Employee> subordinates = manager.getSubordinates();
		Set<LeaveRecord> subLeaves = new HashSet<LeaveRecord>();
		
		// find leaves with status "Applied" and "Updated"
		for (Employee sub : subordinates) {
			Set<LeaveRecord> leaves = sub.getLeaveSet();
			for (LeaveRecord leave : leaves) {
				String status = leave.getLeaveStatus();
				if (status.equals("Applied") || status.equals("Updated"))
					subLeaves.add(leave);
			}
		}
		
		return new ModelAndView("Applications", "leaves", subLeaves);
	}
	
	@RequestMapping(path = "/application/{id}", method = RequestMethod.GET)
    public ModelAndView viewApplication(@PathVariable int id) {
		LeaveRecord leave = lService.findLeaveById(id);
		return new ModelAndView("Application", "leave", leave);
    }
	
	@RequestMapping(path = "/approve", method = RequestMethod.POST)
    public String approveLeave(@ModelAttribute("leave") LeaveRecord leave, HttpSession session) {
		LeaveRecord l = lService.findLeaveById(leave.getId());
		l.setManagerComments(leave.getManagerComments());
		l.setLeaveStatus("Approved");
		lService.updateLeave(l);
		
		// fetch updated leaves to process for manager
		UserSession us = (UserSession)session.getAttribute("us");
		Employee manager = us.getUser();
		manager.setSubordinates(lService.findSubordinatesByManager(manager));
		
		return "redirect:/applications";
    }
	
	@RequestMapping(path = "/reject", method = RequestMethod.POST)
    public String rejectLeave(@ModelAttribute("leave") LeaveRecord leave, HttpSession session) {
		LeaveRecord l = lService.findLeaveById(leave.getId());
		l.setManagerComments(leave.getManagerComments());
		l.setLeaveStatus("Rejected");
		lService.updateLeave(l);
		
		// update employee's leave balance
		EmpLeave empLeave = lService.findEmpLeaveByEmployeeAndLeaveType(l.getEmployee(), l.getLeaveType());
		empLeave.setLeaveBal(empLeave.getLeaveBal() + l.getNumOfDays());
		lService.updateEmpLeave(empLeave);
		
		// fetch updated leaves to process for manager
		UserSession us = (UserSession)session.getAttribute("us");
		Employee manager = us.getUser();
		manager.setSubordinates(lService.findSubordinatesByManager(manager));
		
		return "redirect:/applications";
    }

	@RequestMapping(path = "/team", method = RequestMethod.GET)
	public ModelAndView viewTeam(HttpSession session) {
		UserSession us = (UserSession)session.getAttribute("us");
		Employee manager = us.getUser();
		Set<Employee> subordinates = manager.getSubordinates();
		Set<LeaveRecord> subLeaves = new HashSet<LeaveRecord>();
		
		// combine all leaves in same team into 1 set
		subLeaves.addAll(manager.getLeaveSet());
		for (Employee sub : subordinates) {
			Set<LeaveRecord> leaves = sub.getLeaveSet();
			subLeaves.addAll(leaves);
		}
		
		return new ModelAndView("Team", "leaves", subLeaves);
	}
}
