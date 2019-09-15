package com.fdmgroup.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.dao.IAcademyAdminDao;
import com.fdmgroup.dao.IBatchDao;
import com.fdmgroup.dao.IQuestionDao;
import com.fdmgroup.dao.IQuizDao;
import com.fdmgroup.dao.IResultDao;
import com.fdmgroup.dao.ITraineeDao;
import com.fdmgroup.dao.ITrainerDao;
import com.fdmgroup.dao.JpaAcademyAdminDao;
import com.fdmgroup.dao.JpaBatchDao;
import com.fdmgroup.dao.JpaQuestionDao;
import com.fdmgroup.dao.JpaQuizDao;
import com.fdmgroup.dao.JpaResultDao;
import com.fdmgroup.dao.JpaTraineeDao;
import com.fdmgroup.dao.JpaTrainerDao;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.AcademyAdmin;
import com.fdmgroup.model.Module;
import com.fdmgroup.model.Question;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.model.Result;
import com.fdmgroup.model.ResultAnswer;
import com.fdmgroup.model.Trainee;
import com.fdmgroup.model.TraineeStatus;
import com.fdmgroup.model.Trainer;
import com.fdmgroup.model.User;
import com.fdmgroup.util.TimeAccuracyPair;

@Controller
public class AcademyAdminController {

	@Autowired
	@Qualifier("batchDao")
	private IBatchDao batchDao;
	@Autowired
	@Qualifier("quizDao")
	private IQuizDao quizDao;
	@Autowired
	@Qualifier("questionDao")
	private IQuestionDao questionDao;
	@Autowired
	@Qualifier("trainerDao")
	private ITrainerDao trainerDao;
	@Autowired
	@Qualifier("traineeDao")
	private ITraineeDao traineeDao;
	@Autowired
	@Qualifier("resultDao")
	private IResultDao resultDao;
	@Qualifier("academyAdminDao")
	@Autowired
	private IAcademyAdminDao academyAdminDao;

	// From Login page to Academy Admin Home page
	@RequestMapping(value = "/academyAdminHome", method = RequestMethod.GET)
	public String goToAcademyAdminHome(HttpServletRequest req, Model model) throws NoSuchDatabaseEntryException {
		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		model.addAttribute("numberOfBatches", batchDao.numberOfBatches());
		model.addAttribute("numberofTrainers", trainerDao.numberOfTrainers());
		model.addAttribute("numberOfTrainees", traineeDao.numberOfTrainees());
		model.addAttribute("loggedUser", loggedUser);
		return "/admin_views/admin_index";
	}

	// From Academy Admin Home page to Add Trainee page
	@RequestMapping(value = "/addTrainee", method = RequestMethod.GET)
	public String goToAddTrainee() {
		return "/admin_views/admin_add_trainee";
	}

	// Register Trainee in database
	@RequestMapping(value = "/registerTrainee", method = RequestMethod.POST)
	public String addTrainee(HttpServletRequest req) {
		// Defining Parameters
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String location = req.getParameter("location");
		String statusMessage = req.getParameter("status");
		String start = req.getParameter("startTime");
		String end = req.getParameter("endTime");

		try {
			Trainee trainee = traineeDao.findByEmail(email);
			String error = "Username has been used.";
			req.setAttribute("error", error);
			return "/admin_views/admin_add_trainee";
		} catch (NoSuchDatabaseEntryException e1) {
			try {
				Trainer trainer = trainerDao.findByEmail(email);
				String error = "Username has been used.";
				req.setAttribute("errorMessage", error);
				return "/admin_views/admin_add_trainee";
			} catch (NoSuchDatabaseEntryException e2) {
				try {
					AcademyAdmin admin = academyAdminDao.findByEmail(email);
					String error = "Username has been used.";
					req.setAttribute("errorMessage", error);
					return "/admin_views/admin_add_trainee";
				} catch (NoSuchDatabaseEntryException e3) {

				}
			}
		}
		// Convert String to time
		LocalDate startdate = LocalDate.parse(start);
		LocalDate enddate = LocalDate.parse(end);

		// Default value
		TraineeStatus status = TraineeStatus.IN_TRAINING;
		// Set the enum type
		switch (statusMessage) {
		case "IN_TRAINING":
			status = TraineeStatus.IN_TRAINING;
			break;
		case "SIGNED_OFF":
			status = TraineeStatus.SIGNED_OFF;
			break;
		case "BEACHED":
			status = TraineeStatus.BEACHED;
			break;
		}
		// Create Trainee
		Trainee newTrainee = new Trainee(email, firstName, lastName, password, location, "Trainee",
				new ArrayList<Quiz>(), status, startdate, enddate, null, new ArrayList<Result>());
		// Write it into the database
		traineeDao.create(newTrainee);
		// Return to admin home
		return "redirect:/academyAdminHome";
	}

	// From Academy Admin Home page to Add Trainer page
	@RequestMapping(value = "/addTrainer", method = RequestMethod.GET)
	public String goToAddTrainer() {
		return "/admin_views/admin_add_trainer";
	}

	// From Academy Admin Home page to Add Trainer page
	@RequestMapping(value = "/registerTrainer", method = RequestMethod.POST)
	public String addTrainer(HttpServletRequest req) {
		// Defining Parameters
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String location = req.getParameter("location");
		String start = req.getParameter("startTime");
		
		try {
			Trainee trainee = traineeDao.findByEmail(email);
			String error = "Username has been used.";
			req.setAttribute("errorMessage", error);
			return "/admin_views/admin_add_trainee";
		} catch (NoSuchDatabaseEntryException e1) {
			try {
				Trainer trainer = trainerDao.findByEmail(email);
				String error = "Username has been used.";
				req.setAttribute("errorMessage", error);
				return "/admin_views/admin_add_trainee";
			} catch (NoSuchDatabaseEntryException e2) {
				try {
					AcademyAdmin admin = academyAdminDao.findByEmail(email);
					String error = "Username has been used.";
					req.setAttribute("errorMessage", error);
					return "/admin_views/admin_add_trainee";
				} catch (NoSuchDatabaseEntryException e3) {

				}
			}
		}
		
			
		// Convert String to time
		LocalDate startdate = LocalDate.parse(start);
		// Create Trainer
		Trainer newTrainer = new Trainer(email, firstName, lastName, password, location, "Trainer",
				new ArrayList<Quiz>(), startdate);
		// Write it into the database
		trainerDao.create(newTrainer);
		// Return to admin home
		return "redirect:/academyAdminHome";
	}

	// From Academy Admin Home page to View Report page
	@RequestMapping(value = "/viewReport", method = RequestMethod.GET)
	public String goToViewReport(HttpServletRequest req, Model model) {
		String moduleName = req.getParameter("moduleName");
		Module module = null;
		if (moduleName != null) {
			module = Module.valueOf(moduleName);
		}
		List<Quiz> quizzes = null;
		if (moduleName == null) {
			try {
				quizzes = quizDao.findAllQuiz();
			} catch (NoSuchDatabaseEntryException e) {
				e.printStackTrace();
			}
		} else {
			try {
				quizzes = quizDao.findQuizzesbyModule(module);
			} catch (NoSuchDatabaseEntryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		model.addAttribute("quizzes", quizzes);
		return "/admin_views/admin_view_report";
	}

	// Report
	@RequestMapping(value = "/showReport", method = RequestMethod.GET)
	public String showReport(HttpServletRequest req) {
		String id_String = req.getParameter("id");
		int quizId = Integer.parseInt(id_String);
		List<Result> results = null;
		Quiz quiz = null;
		try {
			quiz = quizDao.findById(quizId);
		} catch (NoSuchDatabaseEntryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Question> questions = quiz.getQuestions();

		//System.out.println("Quiz id:");
		//System.out.println(quiz.getId());

		try {
			results = resultDao.findByQuizId(quizId);
		} catch (NoSuchDatabaseEntryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (results == null) {
			return "redirect:/viewReport";
		}
		List<TimeAccuracyPair> pairList = new ArrayList<TimeAccuracyPair>();
		for (Question question : questions) {
			double totalTimeTaken = 0;
			double accuracy = 0;
			int size = 0;
			for (Result result : results) {

				//System.out.println(result.getAnswers());
				//System.out.println(question.getId());

				int questionId = question.getId();
				totalTimeTaken += result.getAnswer(questionId).getTimeTaken();
				if (result.getAnswer(questionId).getIsCorrect()) {
					accuracy++;
				}
				size++;
			}

			pairList.add(new TimeAccuracyPair(question.getQuestionIndex(), (totalTimeTaken / size),
					(accuracy / size) * 100));
			
		}
		req.setAttribute("quizName", quiz.getTitle());
		req.setAttribute("pairList", pairList);

		return "/admin_views/admin_report";
	}

	// View about
	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String goToAbout() {
		return "/admin_views/admin_about";
	}

}
