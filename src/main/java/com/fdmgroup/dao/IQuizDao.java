package com.fdmgroup.dao;

import java.util.List;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Module;
import com.fdmgroup.model.Quiz;
import com.fdmgroup.model.User;

public interface IQuizDao extends IStorage<Quiz>, IEditable<Quiz>, IRemovable<Quiz> {
	public List<Quiz> findUserQuizzesInModule(User owner, Module moduleName) throws NoSuchDatabaseEntryException;
	
	
	public List<Quiz> findQuizzesbyModule(Module moduleName) throws NoSuchDatabaseEntryException;
	
	public List<Quiz> findAllQuiz() throws NoSuchDatabaseEntryException;
}
