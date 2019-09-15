package com.fdmgroup.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.dao.IAcademyAdminDao;
import com.fdmgroup.dao.IBatchDao;
import com.fdmgroup.dao.IQuestionDao;
import com.fdmgroup.dao.IQuizDao;
import com.fdmgroup.dao.ITraineeDao;
import com.fdmgroup.dao.ITrainerDao;
import com.fdmgroup.dao.IChoiceDao;
import com.fdmgroup.dao.JpaAcademyAdminDao;
import com.fdmgroup.dao.JpaBatchDao;
import com.fdmgroup.dao.JpaQuestionDao;
import com.fdmgroup.dao.JpaQuizDao;
import com.fdmgroup.dao.JpaTraineeDao;
import com.fdmgroup.dao.JpaTrainerDao;
import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.game.Answer;
import com.fdmgroup.game.Person;
import com.fdmgroup.game.PlayerDetails;
import com.fdmgroup.game.Report;
import com.fdmgroup.game.SocketQuestion;
import com.fdmgroup.game.SocketQuiz;
import com.fdmgroup.model.Choice;
import com.fdmgroup.model.Module;
import com.fdmgroup.model.Question;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.model.Result;
import com.fdmgroup.model.ResultAnswer;
import com.fdmgroup.model.Trainee;
import com.fdmgroup.model.User;
import com.fdmgroup.util.Log;
import com.fdmgroup.util.PinGenerator;
import com.google.gson.Gson;

@Controller
public class TrainerController {

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
	@Qualifier("choiceDao")
	private IChoiceDao choiceDao;
	@Autowired
	@Qualifier("traineeDao")
	private ITraineeDao traineeDao;

	// Socket Routes
	@RequestMapping("/admin")
	public String goToRegister(Model model) {
		return "/game_views/admin"; // return the name of the view/name of the
									// jsp
	}

	private List<SocketQuestion> convertQuestions(List<Question> questions) {
		List<SocketQuestion> socketQuestions = new ArrayList<>();
		for (Question question : questions) {
			SocketQuestion sq = new SocketQuestion();
			sq.setQuestion(question.getQuestionContent());
			sq.setTimeLimit((int) question.getTimeLimit());
			sq.setId(question.getId());
			List<Answer> answers = new ArrayList<>();
			for (Choice choice : question.getChoices()) {
				Answer answer = new Answer();
				answer.setAnswer(choice.getChoiceContent());
				answer.setCorrect(choice.getIsCorrect());
				answers.add(answer);
			}
			sq.setChoices(answers);
			socketQuestions.add(sq);
		}
		return socketQuestions;

	}

	@RequestMapping(value = "/generateReport", method = RequestMethod.POST)
	public void generateReport(HttpServletRequest req, HttpServletResponse resp, Model model)
			throws IOException, NoSuchDatabaseEntryException {
		Log.debug("generateReport");
		Log.debug(req.getContentType());
		Log.debug(req.getHeader("report"));
		Gson gson = new Gson();

		// Get the JSON
		BufferedReader reader = req.getReader();
		Report report = gson.fromJson(reader, Report.class);

		for (PlayerDetails player : report.getPlayers()) {
			Result result = new Result(null, 0, 0, new HashMap<Integer, ResultAnswer>());
			result.setQuizId(report.getQuizId());
			result.setScore(player.getScore());
			List<ResultAnswer> resultAnswers = new ArrayList<>();
			int questionCounter = 0;
			for (Answer answer : player.getAnswers()) {
				ResultAnswer ra = new ResultAnswer(answer.isCorrect(), answer.getTimeTaken(), answer.getOption(), null,
						report.getQuestions().get(questionCounter).getId());
				resultAnswers.add(ra);
				questionCounter++;
			}
			for (int i = 0; i < report.getQuestions().size(); i++) {

				System.out.println(report.getQuestions().get(i).getQuestion());
				System.out.println(report.getQuestions().get(i).getId());

				result.addAnswer(report.getQuestions().get(i).getId(), resultAnswers.get(i));
			}

			System.out.println("Result answers");
			System.out.println(result.getAnswers());

			Trainee trainee = traineeDao.findByEmail(player.getUsername());
			Log.debug("finishing result for trainee..." + trainee.getEmail());
			trainee.addResult(result);
			traineeDao.update(trainee);

			System.out.println("TRAINEE RESULT");
			Trainee res = traineeDao.findById(trainee.getId());
			res.getResults().forEach(resResult -> System.out.println(resResult.getAnswers().keySet()));

			Log.debug("updatedtrainee");

		}
		PrintWriter out = resp.getWriter();
		out.print(gson.toJson(report));
		out.flush();
		out.close();
	}

	@RequestMapping(value = "/getPin", method = RequestMethod.POST)
	public void getPin(HttpServletRequest req, Model model, HttpServletResponse resp) throws IOException {
		Gson gson = new Gson();
		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		Quiz quiz = (Quiz) req.getSession().getAttribute("currentQuiz");

		SocketQuiz sq = new SocketQuiz();
		sq.setId(quiz.getId());
		sq.setTitle(quiz.getTitle());

		sq.setQuestions(convertQuestions(quiz.getQuestions()));

		int pin = PinGenerator.generatePin();
		Person p = new Person();
		p.setName(loggedUser.getFirstName());
		p.setPin(pin);
		p.setQuiz(sq);
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		String personString = gson.toJson(p);
		out.print(personString);
		out.flush();
	}
	
	@RequestMapping(value = "/game_menu", method = RequestMethod.POST)
	public void checkQuiz(HttpServletRequest req, Model model, HttpServletResponse resp) throws IOException {
		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		String quizId = req.getParameter("quizId");
		HttpSession session = req.getSession();
		PrintWriter out = resp.getWriter();
		for (Quiz q : loggedUser.getQuizzes()) {
			if (q.getId() == Integer.parseInt(quizId)) {
				if (q.getQuestions().size() != 0) {
					if (!invalidQuestion(q)) {
						session.setAttribute("currentQuiz", q);
						Log.debug("found quiz!!!!!");
						out.print("1");
					} else {
						out.print("2");
					}
					
				} else {
					out.print("0");
				}
				break;
			}
		}
		out.flush();
		out.close();
	}
	
	private boolean invalidQuestion(Quiz q) {
		for (Question question: q.getQuestions()) {
			if (question.getChoices().size() <= 1) {
				return true;
			}
		}
		return false;
	}

	@RequestMapping(value = "/game_menu")
	public String goToGameMenu(HttpServletRequest req, HttpServletResponse res) {
		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		String quizId = req.getParameter("quizId");
		HttpSession session = req.getSession();
		for (Quiz q : loggedUser.getQuizzes()) {
			if (q.getId() == Integer.parseInt(quizId)) {
				if (q.getQuestions().size() != 0) {
					session.setAttribute("currentQuiz", q);
					Log.debug("found quiz!!!!!");
					return "/trainer_views/game";
				} else {
					return "forward:/trainerHome";
				}
			}
		}
		if (loggedUser != null) {
			Log.debug("loggedUser is not null");
		} else {
			Log.debug("loggedUser is null");
		}

		return "forward:/trainerHome";
	}

	@RequestMapping(value = "/logout")
	public String logout(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession();
		session.invalidate();
		model.addAttribute("userModel", new User());
		return "/trainee_views/login";
	}

	// From login page to Trainer Home page
	@RequestMapping(value = "/trainerHome")
	public String goToTrainerHome(HttpServletRequest req, Model model) throws NoSuchDatabaseEntryException {
		Log.debug("trainer home route");
		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		List<Quiz> userQuizzes = loggedUser.getQuizzes();
		model.addAttribute("userQuizzes", userQuizzes);
		return "/trainer_views/trainer_menu";
	}

	// create quiz
	@RequestMapping(value = "/trainerCreateQuiz")
	public String goToCreateQuiz() {
		return "/trainer_views/trainer_create_quiz";
	}

	@RequestMapping(value = "/getUserName", method = RequestMethod.POST)
	public void pullUserName(HttpServletRequest req, Model model, HttpServletResponse resp) throws IOException {

		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		PrintWriter out = resp.getWriter();
		out.print(loggedUser.getFirstName());
	}

	// add question to quiz

	// load game

	// Show Question
	@RequestMapping(value = "/trainerShowQuestion", method = RequestMethod.GET)
	public String startGame() {
		return "/trainer_views/trainer_show_question";
	}

	// Show Answer
	@RequestMapping(value = "/trainerFinishQuestion", method = RequestMethod.GET)
	public String loadQuestion() {
		return "/trainer_views/trainer_question_finished";
	}

	// show finish screen
	@RequestMapping(value = "/trainerGameFinished", method = RequestMethod.GET)
	public String finishGame() {
		return "/trainer_views/trainer_game_finished";
	}

	// show trainer report
	@RequestMapping(value = "/trainerViewReport", method = RequestMethod.GET)
	public String viewReport() {
		return "/trainer_views/trainer_view_report";
	}

	// From Trainer Home Page to Create Quiz Page
	@RequestMapping(value = "/createQuiz")
	public String goToCreateQuiz(Model model, HttpServletRequest req) {
		Quiz quiz = new Quiz();
		quiz.setQuestions(new ArrayList<Question>());
		model.addAttribute("quiz", quiz);
		return "/trainer_views/trainer_create_quiz";
	}

	// When Save option in create quiz page is clicked

	@RequestMapping(value = "/saveQuiz", method = RequestMethod.POST)
	public String saveQuiz(@ModelAttribute("quiz") Quiz newQuiz, HttpServletRequest req, HttpSession session)
			throws NoSuchDatabaseEntryException {
		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		newQuiz.setOwner(loggedUser);
		quizDao.create(newQuiz);
		session.setAttribute("loggedUser", trainerDao.findById(loggedUser.getId()));
		return "forward:/trainerHome";
	}

	// When Save is clicked on add question page
	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
	public String addQuestion(@ModelAttribute("newQuestion") Question newQuestion, HttpServletRequest request,
			Model model, HttpSession session) throws NumberFormatException, NoSuchDatabaseEntryException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		String currentQuizId = request.getParameter("currentQuizId");
		Quiz currentQuiz = quizDao.findById(Integer.parseInt(currentQuizId));
		newQuestion.setQuizzes(new ArrayList<Quiz>());
		newQuestion.setChoices(new ArrayList<Choice>());
		currentQuiz.addQuestion(newQuestion);
		quizDao.update(currentQuiz);

		model.addAttribute("quiz", currentQuiz);
		model.addAttribute("newQuestion", new Question());
		model.addAttribute("newChoice", new Choice());
		session.setAttribute("loggedUser", trainerDao.findById(loggedUser.getId()));

		return "/trainer_views/trainer_add_question";
	}

	@RequestMapping(value = "/checkAddedChoice", method = RequestMethod.POST)
	public void checkAddedChoice(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, NoSuchDatabaseEntryException {
		PrintWriter out = response.getWriter();
		String currentQuizId = request.getParameter("currentQuizId");
		Quiz currentQuiz = quizDao.findById(Integer.parseInt(currentQuizId));
		String currentQuestionId = request.getParameter("currentQuestionId");
		Question currentQuestion = questionDao.findById(Integer.parseInt(currentQuestionId));
		if (currentQuestion.getChoices().size() >= 4) {
			out.print("0");
		} else {
			out.print("1");
		}
		out.flush();
		out.close();
		
	}
	
	// when save is clicked on add choice modal
	@RequestMapping(value = "/addChoices", method = RequestMethod.POST)
	public String addChoices(@ModelAttribute("newChoice") Choice newChoice, HttpServletRequest request, Model model,
			HttpSession session) throws NumberFormatException, NoSuchDatabaseEntryException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		String currentQuizId = request.getParameter("currentQuizId");

		Quiz currentQuiz = quizDao.findById(Integer.parseInt(currentQuizId));
		String currentQuestionId = request.getParameter("currentQuestionId");
		Question currentQuestion = questionDao.findById(Integer.parseInt(currentQuestionId));

		if(currentQuestion.getChoices().size() < 4){
			int choiceIndex = currentQuestion.getNextChoiceIndex();
			newChoice.setChoiceIndex(choiceIndex);
			newChoice.setQuestion(currentQuestion);
			choiceDao.create(newChoice);
			currentQuestion.addChoice(newChoice);
	
			quizDao.update(currentQuiz);
		}
		
		
		model.addAttribute("quiz", currentQuiz);
		model.addAttribute("newQuestion", new Question());
		model.addAttribute("newChoice", new Choice());
		session.setAttribute("loggedUser", trainerDao.findById(loggedUser.getId()));
		return "/trainer_views/trainer_add_question";
	}

	// removing a question in quiz edit page
	@RequestMapping(value = "/removeQuestion", method = RequestMethod.GET)
	public String removeQuestion(HttpServletRequest request, Model model, HttpSession session)
			throws NumberFormatException, NoSuchDatabaseEntryException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");

		String currentQuizId = request.getParameter("currentQuizId");
		Quiz currentQuiz = quizDao.findById(Integer.parseInt(currentQuizId));

		String deletedQuestionId = request.getParameter("deletedQuestionId");


		Question deletedQuestion = questionDao.findById(Integer.parseInt(deletedQuestionId));
		questionDao.remove(deletedQuestion);

		model.addAttribute("quiz", currentQuiz);
		model.addAttribute("newQuestion", new Question());
		model.addAttribute("newChoice", new Choice());
		session.setAttribute("loggedUser", trainerDao.findById(loggedUser.getId()));

		return "/trainer_views/trainer_add_question";
	}

	// removing a question in quiz edit page
	@RequestMapping(value = "/removeChoice", method = RequestMethod.GET)
	public String removeChoice(HttpServletRequest request, Model model, HttpSession session)
			throws NumberFormatException, NoSuchDatabaseEntryException {
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");

		String currentQuizId = request.getParameter("currentQuizId");
		Quiz currentQuiz = quizDao.findById(Integer.parseInt(currentQuizId));

		String choiceId = request.getParameter("choiceId");

		Choice choice = choiceDao.findById(Integer.parseInt(choiceId));
		choiceDao.remove(choice);

		model.addAttribute("quiz", currentQuiz);
		model.addAttribute("newQuestion", new Question());
		model.addAttribute("newChoice", new Choice());
		session.setAttribute("loggedUser", trainerDao.findById(loggedUser.getId()));

		return "/trainer_views/trainer_add_question";
	}

	// when edit quiz is clicked
	@RequestMapping(value = "/goToAddQuestion", method = RequestMethod.POST)
	public String goToAddQuestion(HttpServletRequest req, Model model)
			throws NumberFormatException, NoSuchDatabaseEntryException {
		String quizId = req.getParameter("editQuiz");
		Quiz quiz = quizDao.findById(Integer.parseInt(quizId));
		model.addAttribute("quiz", quiz);
		model.addAttribute("newQuestion", new Question());
		model.addAttribute("newChoice", new Choice());
		return "trainer_views/trainer_add_question";

	}

	// From Trainer Home Page to Remove Quiz Page
	@RequestMapping(value = "/removeQuiz", method = RequestMethod.POST)
	public String removeQuiz(HttpServletRequest req, HttpSession session) throws NoSuchDatabaseEntryException {
		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		String quizId = req.getParameter("removeQuiz");
		Quiz quiz = quizDao.findById(Integer.parseInt(quizId));
		quizDao.remove(quiz);
		session.setAttribute("loggedUser", trainerDao.findById(loggedUser.getId()));
		return "forward:/trainerHome";
	}

	// In Trainer Home Page, display quiz questions
	@RequestMapping(value = "/displayQuizQuestion")
	public String goToDisplayQuizQuestion() {
		return "trainerHomePage";
	}

	// The trainer quizzes are filtered according to selected module
	@RequestMapping(value = "/filter")
	public String filterTrainerQuizzes(HttpServletRequest req, Model model) {
		String selectedModule = req.getParameter("selectedModule");
		Module module = Module.valueOf(selectedModule);

		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		List<Quiz> filteredQuizzes;
		try {
			filteredQuizzes = quizDao.findUserQuizzesInModule(loggedUser, module);
			model.addAttribute("userQuizzes", filteredQuizzes);
			return "/trainer_views/trainer_menu";
		} catch (NoSuchDatabaseEntryException e) {
			// code to handle exception
		}
		return "/trainer_views/trainer_menu";
	}

}
