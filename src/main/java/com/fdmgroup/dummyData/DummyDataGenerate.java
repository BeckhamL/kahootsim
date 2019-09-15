package com.fdmgroup.dummyData;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fdmgroup.dao.IAcademyAdminDao;
import com.fdmgroup.dao.IBatchDao;
import com.fdmgroup.dao.ITraineeDao;
import com.fdmgroup.dao.ITrainerDao;
import com.fdmgroup.dao.JpaAcademyAdminDao;
import com.fdmgroup.dao.JpaBatchDao;
import com.fdmgroup.dao.JpaTraineeDao;
import com.fdmgroup.dao.JpaTrainerDao;
import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.AcademyAdmin;
import com.fdmgroup.model.Batch;
import com.fdmgroup.model.Choice;
import com.fdmgroup.model.Module;
import com.fdmgroup.model.Question;
import com.fdmgroup.model.QuestionImage;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.model.Trainee;
import com.fdmgroup.model.TraineeStatus;
import com.fdmgroup.model.Trainer;
import com.fdmgroup.model.Result;
import com.fdmgroup.model.ResultAnswer;
import com.fdmgroup.model.Stream;

public class DummyDataGenerate {
	public static void main(String[] args) throws NoSuchDatabaseEntryException {
		ApplicationContext context = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");

		IAcademyAdminDao adminDao = (JpaAcademyAdminDao) context.getBean("academyAdminDao");
		IBatchDao batchDao = (JpaBatchDao) context.getBean("batchDao");
		ITrainerDao trainerDao = (JpaTrainerDao) context.getBean("trainerDao");
		ITraineeDao traineeDao = (JpaTraineeDao) context.getBean("traineeDao");

		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);

		AcademyAdmin adminGreg = new AcademyAdmin("greg.gamemaster@fdmgroup.com", "Greg", "Kelsie", "GG", "Scar-Bro",
				"Academy Admin", new ArrayList<Quiz>(), today);

		Trainer trainerBeckham = new Trainer("beckham.backlog@fdmgroup.com", "Beckham", "Lam", "BB", "R(IP) Hill",
				"Trainer", new ArrayList<Quiz>(), today);
		Trainer trainerNathan = new Trainer("nathan.negative@fdmgroup.com", "Nathan", "Chung", "NN", "Marked Ham",
				"Trainer", new ArrayList<Quiz>(), today);

		Trainee traineeShihao = new Trainee("shihao.stylin@fdmgroup.com", "Shihao", "Miao", "SS", "Mt. Sauga",
				"Trainee", new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null,
				new ArrayList<Result>());
		Trainee traineeLamia = new Trainee("lamia.laggin@fdmgroup.com", "Lamia", "Siddiquee", "LL", "Nowhere",
				"Trainee", new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null,
				new ArrayList<Result>());
		Trainee traineeDaniel = new Trainee("daniel.ditchin@fdmgroup.com", "Daniel", "Kao", "DD", "Everywhere",
				"Trainee", new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null,
				new ArrayList<Result>());
		Trainee traineeSatinder = new Trainee("satinder.singedoff@fdmgroup.com", "Satinder", "Kaur", "SS", "Somewhere",
				"Trainee", new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null,
				new ArrayList<Result>());
		Trainee traineeDupe = new Trainee("daniel.duplicate@fdmgroup.com", "Daniel", "Kao", "DD", "Whereno", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee traineeHaoshi = new Trainee("haoshi.hoax@fdmgroup.com", "Haoshi", "Meow", "HH", "Wherewhere", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee traineeNathan = new Trainee("nathan.notatrainer@fdmgroup.com", "Not A.", "Trainer", "NN", "Therewhere",
				"Trainee", new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null,
				new ArrayList<Result>());
		Trainee traineeBeckham = new Trainee("beckham.buffissafe@fdmgroup.com", "String", "Buffer", "BB", "Herethere",
				"Trainee", new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null,
				new ArrayList<Result>());
		Trainee traineeGreg = new Trainee("greg.guidewire@fdmgroup.com", "Good", "Game", "GG", "Somethere", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());

		// Trainees for demo
		Trainee trainee1 = new Trainee("player1@fdmgroup.com", "Player", "One", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee trainee2 = new Trainee("player2@fdmgroup.com", "Player", "Two", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee trainee3 = new Trainee("player3@fdmgroup.com", "Player", "Three", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee trainee4 = new Trainee("player4@fdmgroup.com", "Player", "Four", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee trainee5 = new Trainee("player5@fdmgroup.com", "Player", "Five", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee trainee6 = new Trainee("player6@fdmgroup.com", "Player", "Six", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee trainee7 = new Trainee("player7@fdmgroup.com", "Player", "Seven", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee trainee8 = new Trainee("player8@fdmgroup.com", "Player", "Eight", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee trainee9 = new Trainee("player9@fdmgroup.com", "Player", "Nine", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());
		Trainee trainee10 = new Trainee("player10@fdmgroup.com", "Player", "Ten", "123", "Toronto", "Trainee",
				new ArrayList<Quiz>(), TraineeStatus.IN_TRAINING, today, tomorrow, null, new ArrayList<Result>());

		Batch java1Batch = new Batch(new ArrayList<Trainee>(), Stream.JAVA);
		Batch java2Batch = new Batch(new ArrayList<Trainee>(), Stream.JAVA);
		Batch testing1Batch = new Batch(new ArrayList<Trainee>(), Stream.TESTING);
		java1Batch.addTrainee(traineeShihao);
		java2Batch.addTrainee(traineeLamia);
		testing1Batch.addTrainee(traineeDaniel);
		testing1Batch.addTrainee(traineeSatinder);
		java1Batch.addTrainee(traineeDupe);
		java2Batch.addTrainee(traineeHaoshi);
		java1Batch.addTrainee(traineeNathan);
		java2Batch.addTrainee(traineeBeckham);
		testing1Batch.addTrainee(traineeGreg);
		
		java2Batch.addTrainee(trainee1);
		java2Batch.addTrainee(trainee2);
		java2Batch.addTrainee(trainee3);
		java2Batch.addTrainee(trainee4);
		java2Batch.addTrainee(trainee5);
		java2Batch.addTrainee(trainee6);
		java2Batch.addTrainee(trainee7);
		java2Batch.addTrainee(trainee8);
		java2Batch.addTrainee(trainee9);
		java2Batch.addTrainee(trainee10);

		// only 2 are real, the rest are filler
		Quiz jpa1Quiz = new Quiz("JPA Quiz", "A JPA quiz", true, Module.JPA, null, new ArrayList<Question>());

		Quiz tdd1Quiz = new Quiz("TDD Quiz", "A TDD quiz", true, Module.TDD, null, new ArrayList<Question>());

		Quiz canadaQuiz = new Quiz("Canada Trivia Quiz", "A series of trivia questions related to Canada!", true,
				Module.OTHER, null, new ArrayList<Question>());
		
		Quiz fdmQuiz = new Quiz("FDM Trivia Quiz", "A series of trivia questions related to FDM!", true,
				Module.OTHER, null, new ArrayList<Question>()); 

		trainerBeckham.addQuiz(jpa1Quiz);
		trainerBeckham.addQuiz(tdd1Quiz);
		trainerNathan.addQuiz(canadaQuiz);
		trainerNathan.addQuiz(fdmQuiz);

		Choice choice1, choice2, choice3, choice4;
		QuestionImage image;

		// Populate JPA quiz
		Question jpaQuestion1 = new Question("What does JPA stand for?", 1, 60, new ArrayList<Quiz>(), null,
				new ArrayList<Choice>());
		image = new QuestionImage("q1.png", "image".getBytes());
		choice1 = new Choice("Java Perusing Application", false, 1, null);
		choice2 = new Choice("Java Persistance Application", false, 2, null);
		choice3 = new Choice("Java Persistance API", true, 3, null);
		choice4 = new Choice("Java Perusing API", false, 4, null);
		jpaQuestion1.setImage(image);
		jpaQuestion1.addChoice(choice1);
		jpaQuestion1.addChoice(choice2);
		jpaQuestion1.addChoice(choice3);
		jpaQuestion1.addChoice(choice4);
		jpa1Quiz.addQuestion(jpaQuestion1);

		Question jpaQuestion2 = new Question("Which of these is NOT a valid value for hibernate.hbm2ddl.auto?", 2, 80,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("q2.png", "image".getBytes());
		choice1 = new Choice("Validate", false, 1, null);
		choice2 = new Choice("Drop", true, 2, null);
		choice3 = new Choice("Create-Drop", false, 3, null);
		choice4 = new Choice("Update", false, 4, null);
		jpaQuestion2.setImage(image);
		jpaQuestion2.addChoice(choice1);
		jpaQuestion2.addChoice(choice2);
		jpaQuestion2.addChoice(choice3);
		jpaQuestion2.addChoice(choice4);
		jpa1Quiz.addQuestion(jpaQuestion2);

		Question jpaQuestion3 = new Question("JPA has built in connection pooling. True or false?", 3, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("q3.png", "image".getBytes());
		choice1 = new Choice("True", false, 1, null);
		choice2 = new Choice("False", true, 2, null);
		jpaQuestion3.setImage(image);
		jpaQuestion3.addChoice(choice1);
		jpaQuestion3.addChoice(choice2);
		jpa1Quiz.addQuestion(jpaQuestion3);

		Question jpaQuestion4 = new Question(
				"JPA will automatically detect what driver to download and use. True or false??", 4, 40,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("q4.png", "image".getBytes());
		choice1 = new Choice("True", false, 1, null);
		choice2 = new Choice("False", true, 2, null);
		jpaQuestion4.setImage(image);
		jpaQuestion4.addChoice(choice1);
		jpaQuestion4.addChoice(choice2);
		jpa1Quiz.addQuestion(jpaQuestion4);

		// 2 right answers
		Question jpaQuestion5 = new Question("Which of these is NOT a valid JPA annotation", 5, 20,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("q5.png", "image".getBytes());
		choice1 = new Choice("@OneToOne", false, 1, null);
		choice2 = new Choice("@List", true, 2, null);
		choice3 = new Choice("@Id", false, 3, null);
		choice4 = new Choice("@SortBy", true, 4, null);
		jpaQuestion5.setImage(image);
		jpaQuestion5.addChoice(choice1);
		jpaQuestion5.addChoice(choice2);
		jpaQuestion5.addChoice(choice3);
		jpaQuestion5.addChoice(choice4);
		jpa1Quiz.addQuestion(jpaQuestion5);

		// Populate TDD Quiz
		Question tddQuestion1 = new Question("What does TDD stand for?", 1, 30, new ArrayList<Quiz>(), null,
				new ArrayList<Choice>());
		image = new QuestionImage("q6.png", "image".getBytes());
		choice1 = new Choice("Test Driven Development", true, 1, null);
		choice2 = new Choice("Test Derived Devleopment", false, 2, null);
		choice3 = new Choice("Test Deployment Descriptor", false, 3, null);
		choice4 = new Choice("Test Deployment Development", false, 4, null);
		tddQuestion1.setImage(image);
		tddQuestion1.addChoice(choice1);
		tddQuestion1.addChoice(choice2);
		tddQuestion1.addChoice(choice3);
		tddQuestion1.addChoice(choice4);
		tdd1Quiz.addQuestion(tddQuestion1);

		Question tddQuestion2 = new Question("Which of the following is a step of TDD", 2, 40, new ArrayList<Quiz>(),
				null, new ArrayList<Choice>());
		image = new QuestionImage("q7.png", "image".getBytes());
		choice1 = new Choice("Watch tests fail", true, 1, null);
		choice2 = new Choice("Trace flow of logic", false, 2, null);
		choice3 = new Choice("Spam compile until tests pass", false, 3, null);
		choice4 = new Choice("Write tests in response to errors", false, 4, null);
		tddQuestion2.setImage(image);
		tddQuestion2.addChoice(choice1);
		tddQuestion2.addChoice(choice2);
		tddQuestion2.addChoice(choice3);
		tddQuestion2.addChoice(choice4);
		tdd1Quiz.addQuestion(tddQuestion2);

		Question tddQuestion3 = new Question("Which is NOT a valid assert method?", 3, 60, new ArrayList<Quiz>(), null,
				new ArrayList<Choice>());
		image = new QuestionImage("q8.png", "image".getBytes());
		choice1 = new Choice("assertFalse", false, 1, null);
		choice2 = new Choice("assertFail", true, 2, null);
		choice3 = new Choice("assertThat", false, 3, null);
		choice4 = new Choice("assertNotSame", false, 4, null);
		tddQuestion3.setImage(image);
		tddQuestion3.addChoice(choice1);
		tddQuestion3.addChoice(choice2);
		tddQuestion3.addChoice(choice3);
		tddQuestion3.addChoice(choice4);
		tdd1Quiz.addQuestion(tddQuestion3);

		// Populate Canada quiz
		Question canadaQuestion1 = new Question("How many points does the maple leaf on the Canadian flag have?", 1, 20,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("7", false, 1, null);
		choice2 = new Choice("9", false, 2, null);
		choice3 = new Choice("11", true, 3, null);
		choice4 = new Choice("13", false, 4, null);
		canadaQuestion1.setImage(image);
		canadaQuestion1.addChoice(choice1);
		canadaQuestion1.addChoice(choice2);
		canadaQuestion1.addChoice(choice3);
		canadaQuestion1.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion1);

		Question canadaQuestion2 = new Question("What is the name of Canada's biggest mall?", 2, 40,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Metropolis", false, 1, null);
		choice2 = new Choice("Eaton Centre", false, 2, null);
		choice3 = new Choice("Square One", false, 3, null);
		choice4 = new Choice("West Edmonton Hall", true, 4, null);
		canadaQuestion2.setImage(image);
		canadaQuestion2.addChoice(choice1);
		canadaQuestion2.addChoice(choice2);
		canadaQuestion2.addChoice(choice3);
		canadaQuestion2.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion2);

		Question canadaQuestion3 = new Question("What is the capital city of Canada?", 3, 20, new ArrayList<Quiz>(),
				null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Ottawa", true, 1, null);
		choice2 = new Choice("Toronto", false, 2, null);
		choice3 = new Choice("Vancouver", false, 3, null);
		choice4 = new Choice("Canada City", false, 4, null);
		canadaQuestion3.setImage(image);
		canadaQuestion3.addChoice(choice1);
		canadaQuestion3.addChoice(choice2);
		canadaQuestion3.addChoice(choice3);
		canadaQuestion3.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion3);

		Question canadaQuestion4 = new Question("Which Canadian city has the most tourists?", 4, 50,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Toronto", true, 1, null);
		choice2 = new Choice("Vancouver", false, 2, null);
		choice3 = new Choice("Montreal", false, 3, null);
		choice4 = new Choice("Ottawa", false, 4, null);
		canadaQuestion4.setImage(image);
		canadaQuestion4.addChoice(choice1);
		canadaQuestion4.addChoice(choice2);
		canadaQuestion4.addChoice(choice3);
		canadaQuestion4.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion4);

		Question canadaQuestion5 = new Question("How many oceans border Canada?", 5, 30, new ArrayList<Quiz>(), null,
				new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("1", false, 1, null);
		choice2 = new Choice("2", false, 2, null);
		choice3 = new Choice("3", true, 3, null);
		choice4 = new Choice("4", false, 4, null);
		canadaQuestion5.setImage(image);
		canadaQuestion5.addChoice(choice1);
		canadaQuestion5.addChoice(choice2);
		canadaQuestion5.addChoice(choice3);
		canadaQuestion5.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion5);

		Question canadaQuestion6 = new Question("How many official sports does Canada have?", 6, 20,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("0", false, 1, null);
		choice2 = new Choice("1", false, 2, null);
		choice3 = new Choice("2", true, 3, null);
		choice4 = new Choice("3", false, 4, null);
		canadaQuestion6.setImage(image);
		canadaQuestion6.addChoice(choice1);
		canadaQuestion6.addChoice(choice2);
		canadaQuestion6.addChoice(choice3);
		canadaQuestion6.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion6);

		Question canadaQuestion7 = new Question("Canada is the _________ country in the world", 7, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("largest", false, 1, null);
		choice2 = new Choice("second largest", true, 2, null);
		choice3 = new Choice("third largest", false, 3, null);
		choice4 = new Choice("smallest", false, 4, null);
		canadaQuestion7.setImage(image);
		canadaQuestion7.addChoice(choice1);
		canadaQuestion7.addChoice(choice2);
		canadaQuestion7.addChoice(choice3);
		canadaQuestion7.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion7);

		Question canadaQuestion8 = new Question("The only official bilingual province in Canada is New Brunswick (T/F)",
				8, 30, new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("True", true, 1, null);
		choice2 = new Choice("False", false, 2, null);
		canadaQuestion8.setImage(image);
		canadaQuestion8.addChoice(choice1);
		canadaQuestion8.addChoice(choice2);
		canadaQuiz.addQuestion(canadaQuestion8);

		Question canadaQuestion9 = new Question("Canada is home to world's largest gold mine (T/F)", 9, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("True", false, 1, null);
		choice2 = new Choice("False", true, 2, null);
		canadaQuestion9.setImage(image);
		canadaQuestion9.addChoice(choice1);
		canadaQuestion9.addChoice(choice2);
		canadaQuiz.addQuestion(canadaQuestion9);

		Question canadaQuestion10 = new Question("How many Olympic Games have been held in Canada?", 10, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("2", false, 1, null);
		choice2 = new Choice("3", true, 2, null);
		choice3 = new Choice("4", false, 3, null);
		choice4 = new Choice("5", false, 4, null);
		canadaQuestion10.setImage(image);
		canadaQuestion10.addChoice(choice1);
		canadaQuestion10.addChoice(choice2);
		canadaQuestion10.addChoice(choice3);
		canadaQuestion10.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion10);

		Question canadaQuestion11 = new Question("Canada has never hosted a Summer Olympic Games (T/F)", 11, 40,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("True", false, 1, null);
		choice2 = new Choice("False", true, 2, null);
		canadaQuestion11.setImage(image);
		canadaQuestion11.addChoice(choice1);
		canadaQuestion11.addChoice(choice2);
		canadaQuiz.addQuestion(canadaQuestion11);

		Question canadaQuestion12 = new Question(
				"License plates in the Northwest Territories are shaped like polar bears (T/F)", 12, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("True", true, 1, null);
		choice2 = new Choice("False", false, 2, null);
		canadaQuestion12.setImage(image);
		canadaQuestion12.addChoice(choice1);
		canadaQuestion12.addChoice(choice2);
		canadaQuiz.addQuestion(canadaQuestion12);

		Question canadaQuestion13 = new Question("The $20 bill was the first polymer banknote to be issued (T/F)", 13,
				20, new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("True", false, 1, null);
		choice2 = new Choice("False", true, 2, null);
		canadaQuestion13.setImage(image);
		canadaQuestion13.addChoice(choice1);
		canadaQuestion13.addChoice(choice2);
		canadaQuiz.addQuestion(canadaQuestion13);

		Question canadaQuestion14 = new Question("What is the national anthem of Canada?", 14, 20,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Maple Leaf", false, 1, null);
		choice2 = new Choice("O No", false, 2, null);
		choice3 = new Choice("O Canadia", false, 3, null);
		choice4 = new Choice("O Canada", true, 4, null);
		canadaQuestion14.setImage(image);
		canadaQuestion14.addChoice(choice1);
		canadaQuestion14.addChoice(choice2);
		canadaQuestion14.addChoice(choice3);
		canadaQuestion14.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion14);

		Question canadaQuestion15 = new Question("Which of the following is a great lake of Canada?", 15, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Lake Inferior", false, 1, null);
		choice2 = new Choice("Lake Toronto", false, 2, null);
		choice3 = new Choice("Lake Eerie", false, 3, null);
		choice4 = new Choice("Lake Huron", true, 4, null);
		canadaQuestion15.setImage(image);
		canadaQuestion15.addChoice(choice1);
		canadaQuestion15.addChoice(choice2);
		canadaQuestion15.addChoice(choice3);
		canadaQuestion15.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion15);

		Question canadaQuestion16 = new Question("Who was the Prime Minister of Canada in 2018?", 16, 20,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Pierre Trudeau", false, 1, null);
		choice2 = new Choice("Ronald McDonald", false, 2, null);
		choice3 = new Choice("Justin Trudeau", true, 3, null);
		choice4 = new Choice("Donald Trump", false, 4, null);
		canadaQuestion16.setImage(image);
		canadaQuestion16.addChoice(choice1);
		canadaQuestion16.addChoice(choice2);
		canadaQuestion16.addChoice(choice3);
		canadaQuestion16.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion16);

		Question canadaQuestion17 = new Question("What is the national animal of Canada?", 17, 20,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Moose", false, 1, null);
		choice2 = new Choice("Polar Bear", false, 2, null);
		choice3 = new Choice("Gerbil", false, 3, null);
		choice4 = new Choice("Beaver", true, 4, null);
		canadaQuestion17.setImage(image);
		canadaQuestion17.addChoice(choice1);
		canadaQuestion17.addChoice(choice2);
		canadaQuestion17.addChoice(choice3);
		canadaQuestion17.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion17);

		Question canadaQuestion18 = new Question("Which was the last province to join Canada?", 18, 20,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Newfoundland", true, 1, null);
		choice2 = new Choice("Alberta", false, 2, null);
		choice3 = new Choice("USA", false, 3, null);
		choice4 = new Choice("British Columbia", false, 4, null);
		canadaQuestion18.setImage(image);
		canadaQuestion18.addChoice(choice1);
		canadaQuestion18.addChoice(choice2);
		canadaQuestion18.addChoice(choice3);
		canadaQuestion18.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion18);

		Question canadaQuestion19 = new Question("How many provinces are there in Canada?", 19, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("8", false, 1, null);
		choice2 = new Choice("9", false, 2, null);
		choice3 = new Choice("10", true, 3, null);
		choice4 = new Choice("11", false, 4, null);
		canadaQuestion19.setImage(image);
		canadaQuestion19.addChoice(choice1);
		canadaQuestion19.addChoice(choice2);
		canadaQuestion19.addChoice(choice3);
		canadaQuestion19.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion19);

		Question canadaQuestion20 = new Question(
				"How many 'o's are there across the names of all the provinces of Canada?", 20, 90,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("6", false, 1, null);
		choice2 = new Choice("7", false, 2, null);
		choice3 = new Choice("8", true, 3, null);
		choice4 = new Choice("9", false, 4, null);
		canadaQuestion20.setImage(image);
		canadaQuestion20.addChoice(choice1);
		canadaQuestion20.addChoice(choice2);
		canadaQuestion20.addChoice(choice3);
		canadaQuestion20.addChoice(choice4);
		canadaQuiz.addQuestion(canadaQuestion20);
		
		// Populate FDM Quiz
		Question fdmQuestion1 = new Question(
				"Which is not an FDM office location?", 1, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Austin", false, 1, null);
		choice2 = new Choice("Reston", false, 2, null);
		choice3 = new Choice("Charlotte", false, 3, null);
		choice4 = new Choice("Los Angeles", true, 4, null);
		fdmQuestion1.setImage(image);
		fdmQuestion1.addChoice(choice1);
		fdmQuestion1.addChoice(choice2);
		fdmQuestion1.addChoice(choice3);
		fdmQuestion1.addChoice(choice4);
		fdmQuiz.addQuestion(fdmQuestion1);

		Question fdmQuestion2 = new Question(
				"Where was the first FDM office located?", 2, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Brighton", true, 1, null);
		choice2 = new Choice("London", false, 2, null);
		choice3 = new Choice("New York", false, 3, null);
		choice4 = new Choice("Leeds", false, 4, null);
		fdmQuestion2.setImage(image);
		fdmQuestion2.addChoice(choice1);
		fdmQuestion2.addChoice(choice2);
		fdmQuestion2.addChoice(choice3);
		fdmQuestion2.addChoice(choice4);
		fdmQuiz.addQuestion(fdmQuestion2);

		Question fdmQuestion3 = new Question(
				"Which stock exchange is FDM listed on?", 3, 20,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("London Stock Exchange", true, 1, null);
		choice2 = new Choice("Tokyo Stock Exchange", false, 2, null);
		choice3 = new Choice("New York Stock Exchange", false, 3, null);
		choice4 = new Choice("Toronto Stock Exchange", false, 4, null);
		fdmQuestion3.setImage(image);
		fdmQuestion3.addChoice(choice1);
		fdmQuestion3.addChoice(choice2);
		fdmQuestion3.addChoice(choice3);
		fdmQuestion3.addChoice(choice4);
		fdmQuiz.addQuestion(fdmQuestion3);

		Question fdmQuestion4 = new Question(
				"How many rooms are there on the 20th floor?", 4, 60,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("18", false, 1, null);
		choice2 = new Choice("14", true, 2, null);
		choice3 = new Choice("10", false, 3, null);
		fdmQuestion4.setImage(image);
		fdmQuestion4.addChoice(choice1);
		fdmQuestion4.addChoice(choice2);
		fdmQuestion4.addChoice(choice3);
		fdmQuiz.addQuestion(fdmQuestion4);

		Question fdmQuestion5 = new Question(
				"How many computers are in the pond?", 5, 60,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("25", false, 1, null);
		choice2 = new Choice("20", true, 2, null);
		choice3 = new Choice("22", false, 3, null);
		choice4 = new Choice("18", false, 4, null);
		fdmQuestion5.setImage(image);
		fdmQuestion5.addChoice(choice1);
		fdmQuestion5.addChoice(choice2);
		fdmQuestion5.addChoice(choice3);
		fdmQuestion5.addChoice(choice4);
		fdmQuiz.addQuestion(fdmQuestion5);

		Question fdmQuestion6 = new Question(
				"Who is not an FDM trainer at the Toronto location?", 6, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Kemisha", false, 1, null);
		choice2 = new Choice("John", false, 2, null);
		choice3 = new Choice("Luca", false, 3, null);
		choice4 = new Choice("Allison", true, 4, null);
		fdmQuestion6.setImage(image);
		fdmQuestion6.addChoice(choice1);
		fdmQuestion6.addChoice(choice2);
		fdmQuestion6.addChoice(choice3);
		fdmQuestion6.addChoice(choice4);
		fdmQuiz.addQuestion(fdmQuestion6);

		Question fdmQuestion7 = new Question(
				"Which is not a room in the Toronto office?", 7, 30,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Scarborough", false, 1, null);
		choice2 = new Choice("Hamilton", true, 2, null);
		choice3 = new Choice("Mississauga", false, 3, null);
		choice4 = new Choice("Vaughan", false, 4, null);
		fdmQuestion7.setImage(image);
		fdmQuestion7.addChoice(choice1);
		fdmQuestion7.addChoice(choice2);
		fdmQuestion7.addChoice(choice3);
		fdmQuestion7.addChoice(choice4);
		fdmQuiz.addQuestion(fdmQuestion7);

		Question fdmQuestion8 = new Question(
				"When was FDM founded?", 8, 60,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("1991", true, 1, null);
		choice2 = new Choice("1988", false, 2, null);
		choice3 = new Choice("1993", false, 3, null);
		choice4 = new Choice("1990", false, 4, null);
		fdmQuestion8.setImage(image);
		fdmQuestion8.addChoice(choice1);
		fdmQuestion8.addChoice(choice2);
		fdmQuestion8.addChoice(choice3);
		fdmQuestion8.addChoice(choice4);
		fdmQuiz.addQuestion(fdmQuestion8);

		Question fdmQuestion9 = new Question(
				"What restaurant are the meet-and-greet pizzas from?", 9, 40,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Pizzaiolo", true, 1, null);
		choice2 = new Choice("Piazza Manna", false, 2, null);
		choice3 = new Choice("Pizza", false, 3, null);
		fdmQuestion9.setImage(image);
		fdmQuestion9.addChoice(choice1);
		fdmQuestion9.addChoice(choice2);
		fdmQuestion9.addChoice(choice3);
		fdmQuiz.addQuestion(fdmQuestion9);

		Question fdmQuestion10 = new Question(
				"Who is the Game Czar of FDM Toronto?", 10, 20,
				new ArrayList<Quiz>(), null, new ArrayList<Choice>());
		image = new QuestionImage("qX.png", "image".getBytes());
		choice1 = new Choice("Gregory", false, 1, null);
		choice2 = new Choice("Steve", false, 2, null);
		choice3 = new Choice("Anum", false, 3, null);
		choice4 = new Choice("Andrew", true, 4, null);
		fdmQuestion10.setImage(image);
		fdmQuestion10.addChoice(choice1);
		fdmQuestion10.addChoice(choice2);
		fdmQuestion10.addChoice(choice3);
		fdmQuestion10.addChoice(choice4);
		fdmQuiz.addQuestion(fdmQuestion10);
		

		Result result1 = new Result(null, 0, 0, new HashMap<Integer, ResultAnswer>());

		batchDao.create(java1Batch);
		batchDao.create(java2Batch);
		batchDao.create(testing1Batch);
		adminDao.create(adminGreg);
		trainerDao.create(trainerBeckham);
		trainerDao.create(trainerNathan);

		// anything requiring id requires it to be set by the database first
		ResultAnswer answer1 = new ResultAnswer(false, 5.5, 1, null, jpaQuestion1.getId());
		ResultAnswer answer2 = new ResultAnswer(true, 25.5, 1, null, jpaQuestion2.getId());
		ResultAnswer answer3 = new ResultAnswer(false, 35.5, 1, null, jpaQuestion3.getId());
		ResultAnswer answer4 = new ResultAnswer(true, 10.2, 1, null, jpaQuestion4.getId());
		ResultAnswer answer5 = new ResultAnswer(false, 0.8, 1, null, jpaQuestion5.getId());
		result1.addAnswer(jpaQuestion1.getId(), answer1);
		result1.addAnswer(jpaQuestion2.getId(), answer2);
		result1.addAnswer(jpaQuestion3.getId(), answer3);
		result1.addAnswer(jpaQuestion4.getId(), answer4);
		result1.addAnswer(jpaQuestion5.getId(), answer5);
		traineeShihao.addResult(result1);
		result1.setQuizId(jpa1Quiz.getId());
		batchDao.update(java1Batch);

		System.out.println("Batch Count: " + batchDao.numberOfBatches());
		System.out.println("Trainee Count: " + traineeDao.numberOfTrainees());
		System.out.println("Trainer Count: " + trainerDao.numberOfTrainers());

		((ClassPathXmlApplicationContext) context).close();

		System.out.println("Finished");
	}
}
