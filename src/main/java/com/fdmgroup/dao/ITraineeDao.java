package com.fdmgroup.dao;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Trainee;

public interface ITraineeDao extends IStorage<Trainee>, IEditable<Trainee>, IRemovable<Trainee> {
	public Trainee findByEmail(String email) throws NoSuchDatabaseEntryException;
	
	public long numberOfTrainees();
}
