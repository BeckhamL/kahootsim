package com.fdmgroup.dao;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.IStorable;

public interface IStorage<T extends IStorable> {
	/**
	 * Creates a new object in the database with information provided by the
	 * given object.
	 * 
	 * @param t
	 *            The object to create
	 * @return True if the object was created successfully, false otherwise
	 */
	public boolean create(T t);

	/**
	 * Finds the T object with the given id
	 * 
	 * @param id
	 *            The id of the object
	 * @return The object if it was found
	 * @throws NoSuchDatabaseEntryException
	 *             Thrown if no object is found
	 */
	public T findById(int id) throws NoSuchDatabaseEntryException;
}