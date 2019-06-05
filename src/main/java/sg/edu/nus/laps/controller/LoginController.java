package sg.edu.nus.laps.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.laps.javabean.UserSession;
import sg.edu.nus.laps.model.Employee;
import sg.edu.nus.laps.service.LeaveServiceIF;
import sg.edu.nus.laps.validator.EmployeeValidator;

@Controller
//@SessionAttributes("session")
public class LoginController {

	private LeaveServiceIF lService;
	private EmployeeValidator eValidator;
	
	@Autowired
	public void setlService(LeaveServiceIF lService) {
		this.lService = lService;
	}
	@Autowired
	public void seteValidator(EmployeeValidator eValidator) {
		this.eValidator = eValidator;
	}

	@InitBinder("employee")
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(eValidator);
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("Login");
		mav.addObject("employee", new Employee());
		return mav;
	}
	
	@RequestMapping(path = "/authenticate", method = RequestMethod.POST)
	public ModelAndView authenticate(@Valid @ModelAttribute Employee employee, 
			BindingResult bindingResult, HttpSession session) {
		
		// redirect back to login page if empId or password is empty
		if (bindingResult.hasErrors()) {
			return new ModelAndView("Login", "employee", employee);
		}
		
		// find employee in database
		Employee emp = lService.authenticate(employee.getEmpId(), employee.getPwd());
		
		// redirect back to login page if authentication fails
		if (emp == null) {
			bindingResult.addError(new ObjectError("employee", "Employee ID or Password is wrong"));
			return new ModelAndView("Login", "employee", employee);
		}
		
		// register user session
		UserSession us = new UserSession();
		us.setUser(emp);
		session.setAttribute("us", us);
		
		// redirect to leave balance page if login succeeds
		return new ModelAndView("redirect:/balance");
	}
	
	@RequestMapping(path = "/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
}
