package com.fdmgroup.dao;

import javax.persistence.EntityManager;

import com.fdmgroup.model.IStorable;
import com.fdmgroup.util.JpaUtil;

public interface IEditable<T extends IStorable> {
	/**
	 * Updates the fields of the object in the database with the same primary
	 * key as the object given. If the given object does not exist it will be
	 * created.
	 * 
	 * @param t
	 *            The object to update
	 */
	default public void update(T t) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		em.getTransaction().begin();
		noTransactionUpdate(em, t);
		em.getTransaction().commit();
		em.close();
	}

	/**
	 * Updates the fields of the object in the database with the same primary
	 * key as the object given, uses the entity manager provided to ensure all
	 * changes can be made in one commit.If the given object does not exist it
	 * will be created.
	 * 
	 * @param t
	 *            The object to update
	 * @param em
	 *            The entity manager object to use, a commit should have already
	 *            been started
	 */
	public void noTransactionUpdate(EntityManager em, T t);
}
