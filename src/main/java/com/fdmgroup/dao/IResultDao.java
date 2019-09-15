package com.fdmgroup.dao;

import java.util.List;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Result;

public interface IResultDao extends IStorage<Result>, IEditable<Result>, IRemovable<Result> {
	public List<Result> findByQuizId(int quizId) throws NoSuchDatabaseEntryException;
}
