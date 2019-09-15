package com.fdmgroup.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.dao.IQuestionDao;
import com.fdmgroup.dao.JpaQuestionDao;
import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Choice;
import com.fdmgroup.model.Question;
import com.fdmgroup.model.User;

@Controller
public class TraineeController {
	private static Logger logger = LogManager.getLogger(AuthenticationController.class);
	
	//Socket Routes
	@RequestMapping("/game")
	public String goToGame(Model model) {
		return "/game_views/trainee_pin";
	}
	
	@RequestMapping(value ="/getUsername", method = RequestMethod.POST)
	public void pullUserName(HttpServletRequest req, Model model, HttpServletResponse resp) throws IOException {
		
		User loggedUser = (User) req.getSession().getAttribute("loggedUser");
		PrintWriter out = resp.getWriter();
		out.print(loggedUser.getEmail());
	}
	
//	From Game Pin Page to create NickName page
	@RequestMapping(value = "/nickName", method = RequestMethod.POST)
	public String goToNickName(){
		
		logger.trace("Trainee moving to NickName Page");
		return "/trainee_views/trainee_nickname";
	}
	
//	From NickName page to Waiting page for all users
	@RequestMapping(value = "/waitPage", method = RequestMethod.POST)
	public String goToWaitPage(){
		logger.trace("Trainee moving to Waiting Page");
		return "/trainee_views/trainee_loading_game";
	}
	
//	From Waiting page to Question page
	@RequestMapping(value = "/playGame")
	public String goToQuestion(){
		return "/trainee_views/trainee_question";
	}
	
//	When an answer option is chosen
	@RequestMapping(value = "/choice")
	public String redirectDependOnChoiceA(HttpRequest req, Model model, 
											@RequestParam int choiceId,
											@RequestParam int questionId) throws NoSuchDatabaseEntryException{
		IQuestionDao questionDao = new JpaQuestionDao();
		Question question = questionDao.findById(questionId);
		Choice choice = question.getChoices().get(choiceId);
		if(choice.getIsCorrect()){
			return "/trainee_views/trainee_question_correct";
		}
		else{
			return "/trainee_views/trainee_question_incorrect";
		}
	}
	
//	From last question in Game page to Final Result for top 5 Trainee page
	@RequestMapping(value = "/finalResultForPlacedTrainee")
	public String goToPlacedResultForTrainee(){
		
		return "/trainee_views/trainee_placed";
	}
	
//	From last question in Game page to Final Result for non top 5 Trainee page
	@RequestMapping(value = "/finalResultForNotPlacedTrainee")
	public String goToNotPlacedResultForTrainee(){
		
		return "/trainee_views/trainee_not_placed";
	}
}
