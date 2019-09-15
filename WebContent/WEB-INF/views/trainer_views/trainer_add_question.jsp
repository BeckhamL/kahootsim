<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<meta charset="UTF-8">
<title>Edit Quiz</title>
<link
	href="https://fonts.googleapis.com/css?family=Rubik:700&display=swap"
	rel="stylesheet">
<link rel='stylesheet'
	href='http://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css'>
<link rel="stylesheet"
	href="resources/style/trainer_styles/trainer_add_question_style.css">

</head>
<body>

	<div class="test">
		<h2 class="title">
			${quiz.title} <span class="controls"> <a
				class="question-add btn btn-default" id="new_button"> <span
					class="glyphicon glyphicon-plus" id="glyph"></span> NEW <!--  add new question -->
			</a> <a class="test-config btn btn-warning" id="configure_button"> <span
					class="glyphicon glyphicon-wrench" id="glyph"></span> CONFIGURE
			</a> <a class="btn btn-primary" id="save_button" href="trainerHome"
				style="font-size: 20px;"> <span
					class="glyphicon glyphicon-floppy-disk" id="glyph"></span> SAVE  <!-- save edits to the quiz -->
			</a>
			</span>
		</h2>

		<!-- display block for each question -->
		<%
			int i = 1;
		%>
		<c:forEach items="${quiz.questions}" var="question">
			<div class="question-list">
				<div class="question" draggable="true">
					<div class="title">
						<b><%=i%>)</b>&nbsp; <span class="text">${question.questionContent}</span>
						<!-- Pass ids to modal -->
						<span class="controls"> <a class="answer-add"
							title="New Answer"
							onclick="showAddChoiceModal(${quiz.id}, ${question.id})"><span
								class="glyphicon glyphicon-plus"></span></a> <a
							class="question-edit" title="Edit Question"><span
								class="glyphicon glyphicon-pencil"></span></a> <input type="hidden"
							value="${question.id}" name="deletedQuestionId" /> <a
							class="question-delete" title="Delete Question"
							onclick="setQuizQuestionId(${quiz.id}, ${question.id})"><span
								class="glyphicon glyphicon-remove"></span></a>
						</span>
					</div>
					<!-- display answers for each question  -->
					<ol class="answer-list">
						<c:forEach items="${question.choices}" var="choice">
							<li class="answer" draggable="true"><span class="text">${choice.choiceContent}</span>
								<span class="controls"> <input type="hidden"
									value="${question.id}" name="currentQuestionId" /> <input
									type="hidden" value="${choice.id}" name="choiceId" /> <input
									type="hidden" value="${quiz.id}" name="currentQuizId" /> <a
									class="answer-edit" title="Edit Answer"
									onclick="choiceFunction(${choice.id}, ${question.id}, ${quiz.id})"><span
										class="glyphicon glyphicon-pencil"></span></a> <a
									class="answer-delete" title="Delete Choice"
									onclick="choiceFunction(${choice.id}, ${question.id}, ${quiz.id})"><span
										class="glyphicon glyphicon-remove"></span></a>
							</span></li>
						</c:forEach>
					</ol>
				</div>
			</div>
			<%
				i++;
			%>
		</c:forEach>

	</div>

	<div class="overlay">
		<div class="screen"></div>
		<div class="dialog small" id="dialog-question-delete">
			<div class="dialog-body">
				<h4>Delete</h4>
				<p>Please confirm deletion of question. Be careful as once
					deleted, the question can't be recovered.</p>
			</div>
			<div class="dialog-controls">
				<a class="dismiss">CANCEL</a>
				<button onclick="removeQuestion()">Delete Question</button>

			</div>
		</div>


		<div class="dialog small" id="dialog-answer-delete">
			<div class="dialog-body">
				<h4>Delete</h4>
				<p>Please confirm deletion of answer. Be careful as once
					deleted, the answer can't be recovered.</p>
			</div>
			<div class="dialog-controls">
				<a class="dismiss">CANCEL</a>
				<button onclick="removeChoice()">Delete Choice</button>
			</div>
		</div>


		<!-- spring form for adding new question to the quiz -->
		<sf:form action="addQuestion" method="POST"
			modelAttribute="newQuestion">
			<div class="dialog medium" id="dialog-question-edit">
				<div class="dialog-body">
					<h4>Edit Question</h4>
					<label for="answer-text">Text</label> <input type="hidden"
						value="${quiz.id}" name="currentQuizId" />
					<sf:input type="text" id="question-text" path="questionContent" />
					<sf:select path="timeLimit">
						<sf:option value="20.0"></sf:option>
						<sf:option value="30.0"></sf:option>
						<sf:option value="40.0"></sf:option>
						<sf:option value="50.0"></sf:option>
						<sf:option value="60.0"></sf:option>
						<sf:option value="70.0"></sf:option>
						<sf:option value="80.0"></sf:option>
						<sf:option value="90.0"></sf:option>
					</sf:select>
				</div>
				<div class="dialog-controls">
					<a class="dismiss">CANCEL</a> <input type="submit" class="save"
						value="SAVE" />
				</div>
			</div>
		</sf:form>


		<!-- spring form for adding answers to each question -->
		<sf:form action="addChoices" method="POST" modelAttribute="newChoice">
			<div class="dialog medium" id="dialog-answer-edit">
				<div class="dialog-body">
					<h4>Edit Answer</h4>
					<label for="answer-text">Text</label>

					<sf:input type="text" id="answer-text" path="choiceContent" />

					<label for="answer-correct">Correct / Incorrect</label>
					<sf:select id="answer-correct" path="isCorrect">
						<sf:option value="true">Correct</sf:option>
						<sf:option value="false">Incorrect</sf:option>
					</sf:select>

					<input id="addChoiceQuizId" type="hidden" name="currentQuizId">
					<input id="addChoiceQuestionId" type="hidden"
						name="currentQuestionId">
					
					<p>Maximum of 4 choices are allowed</p>

				</div>
				<div class="dialog-controls">
					<a class="dismiss">CANCEL</a>
					<button id="addChoiceButton" type="submit" class="save">SAVE</button>
				</div>
			</div>
		</sf:form>

		<div class="dialog large" id="dialog-test-config">
			<div class="dialog-body">
				<h4>Quiz Test</h4>
				<div class="column">
					<label>Quiz Title</label> <input type="text" id="test-name"
						value="Example Test" /> <label>Module</label> <select
						id="quiz-module">
						<option>Finance</option>
						<option>JDBC</option>
						<option>JPA</option>
						<option>JSP</option>
						<option>OOD</option>
						<option>SDLC</option>
						<option>Spring</option>
						<option>SQL</option>
						<option>TDD</option>
						<option>UNIX</option>
						<option>Web</option>
						<option>Other</option>
					</select>

				</div>

				<div class="column">
					<label>Owner</label> <input type="text" id="owner" value="Shihao" />

					<label>Availability</label> <select id="test-status">
						<option>Public</option>
						<option>Private</option>
					</select>
				</div>

				<div class="row">
					<label>Description</label> <input type="text" id="test-desc"
						value="Some Descriptions" />
				</div>

			</div>
			<div class="dialog-controls">
				<a class="dismiss">Cancel</a> <a class="save">Save</a>
			</div>

		</div>
	</div>

	<script
		src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
	<script
		src='http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js'></script>
	<script
		src="/fastdailymocks/resources/js/trainer_scripts/trainer_add_question_script.js"></script>

</body>
</html>
