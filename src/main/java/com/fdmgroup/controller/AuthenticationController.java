package com.fdmgroup.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.fdmgroup.validator.*;
import com.fdmgroup.dao.IAcademyAdminDao;
import com.fdmgroup.dao.ITraineeDao;
import com.fdmgroup.dao.ITrainerDao;
import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.AcademyAdmin;
import com.fdmgroup.model.Module;
import com.fdmgroup.model.Trainee;
import com.fdmgroup.model.Trainer;
import com.fdmgroup.model.User;

@Controller
public class AuthenticationController{
	@Autowired
	@Qualifier("loginValidator")
	private Validator validator;
	
	@Autowired
	@Qualifier("traineeDao")
	private ITraineeDao traineeDao;
	
	@Qualifier("trainerDao")
	@Autowired
	private ITrainerDao trainerDao;
	@Qualifier("academyAdminDao")
	@Autowired
	private IAcademyAdminDao academyAdminDao;

	private static Logger logger = LogManager.getLogger(AuthenticationController.class);
	
	@InitBinder
	private void initBinder(WebDataBinder binder){
		binder.setValidator(validator);
		logger.trace("Binding Validator");
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String toLogin(Model model, HttpServletRequest req){
		String userType = (String)req.getSession().getAttribute("userType");
		if (userType == null) {
			model.addAttribute("userModel", new User());
			logger.trace("Moving from Home Page To Login Page and creating user model");
			return "trainee_views/login";
		} else {
			//Redirect to the specific dashboard based on the user.
			if (userType.equals("trainee")) {
				return "/game_views/trainee_pin";
			} else if (userType.equals("trainer")) {
				return "forward:/trainerHome";
			} else if (userType.equals("admin")) {
				return "/admin_views/admin_index";
			} else {
			model.addAttribute("userModel", new User());
				return "/trainee_views/login";
			}
		}
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public String authentication(@ModelAttribute("userModel") User loggedUser, BindingResult bindingResult, Model model,
									HttpSession session, HttpServletRequest req) {
		new UserLoginValidator().validate(loggedUser, bindingResult);
		
		if (bindingResult.hasErrors()){
			logger.error("Empty or white space input for login");
			return "trainee_views/login";
		}
		
		// authenticate trainee
		try {
			Trainee trainee = traineeDao.findByEmail(loggedUser.getEmail());
			if (trainee.getPassword().equals(loggedUser.getPassword())) {
				session.setAttribute("loggedUser", trainee);
				session.setAttribute("userType", "trainee");
				logger.trace("Trainee: " + trainee.getEmail() + " logged in Successfully");
				System.out.println("CheckPoint");
				return "/game_views/trainee_pin";
			}
		} catch (NoSuchDatabaseEntryException e1) {
			// Trainer authentication
			try {
				Trainer trainer = trainerDao.findByEmail(loggedUser.getEmail());

				if (trainer.getPassword().equals(loggedUser.getPassword())) {
					List<Module> moduleList = new ArrayList<Module>(Arrays.asList(Module.values()));
					session.setAttribute("modules", moduleList); //list of modules needed for filter option
					session.setAttribute("loggedUser", trainer);				
					session.setAttribute("userType", "trainer");
					logger.trace("Trainer: " + trainer.getEmail() + " logged in Successfully");
					return "forward:/trainerHome";
				}
			} catch (NoSuchDatabaseEntryException e2) {
				// Admin authentication
				try {
					AcademyAdmin admin = academyAdminDao.findByEmail(loggedUser.getEmail());
					if (admin.getPassword().equals(loggedUser.getPassword())) {
						session.setAttribute("loggedUser", admin);
						session.setAttribute("userType", "admin");
						logger.trace("Academy Admin: " + admin.getEmail() + " logged in Successfully");
						return "redirect:/academyAdminHome";
					} else {
						throw new NoSuchDatabaseEntryException();
					}
				} catch (NoSuchDatabaseEntryException e3) {
					logger.trace("No User Found");
					String errorMessage = "Email or password is invalid";
					req.setAttribute("errorMessage", errorMessage);
					return "trainee_views/login";
				}
			}
		}
		return "/trainee_views/login";
	}
}
