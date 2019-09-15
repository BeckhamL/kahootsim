package com.fdmgroup.dao;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Trainer;

public interface ITrainerDao extends IStorage<Trainer>, IEditable<Trainer>, IRemovable<Trainer> {
	public Trainer findByEmail(String email) throws NoSuchDatabaseEntryException;
	
	public long numberOfTrainers();
}
