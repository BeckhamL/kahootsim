package com.fdmgroup.dao;

import com.fdmgroup.model.IStorable;

public interface IRemovable<T extends IStorable> {
	/**
	 * Removes the object in the database with the same primary key as the
	 * object given.
	 * 
	 * @param t
	 *            The object to remove
	 * @return True if the object was removed successfully, false otherwise
	 */
	public boolean remove(T t);
}
