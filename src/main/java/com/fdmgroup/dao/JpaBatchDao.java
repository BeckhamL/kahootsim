package com.fdmgroup.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.Batch;
import com.fdmgroup.util.JpaUtil;

public class JpaBatchDao implements IBatchDao {
	@Qualifier("traineeDao")
	@Autowired
	private ITraineeDao traineeDao;

	@Override
	public boolean create(Batch batch) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		if (em.find(Batch.class, batch.getId()) != null)
			return false;
		em.getTransaction().begin();
		em.persist(batch);

		// update all trainees in this batch
		if (batch.getTrainees() != null) {
			batch.getTrainees().forEach(trainee -> {
				trainee.setBatch(batch);
				traineeDao.noTransactionUpdate(em, trainee);
			});
		}

		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public Batch findById(int id) throws NoSuchDatabaseEntryException {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Batch res = em.find(Batch.class, id);
		em.close();
		if (res == null)
			throw new NoSuchDatabaseEntryException("No Batch with given ID found");
		return res;
	}

	@Override
	public void noTransactionUpdate(EntityManager em, Batch batch) {
		Batch res = em.find(Batch.class, batch.getId());
		if (res == null) {
			em.persist(batch);
		} else {
			em.merge(batch);
		}

		// update all the trainees contained in this batch
		if (batch.getTrainees() != null) {
			batch.getTrainees().forEach(trainee -> {
				trainee.setBatch(batch);
				traineeDao.noTransactionUpdate(em, trainee);
			});
		}
	}

	@Override
	public boolean remove(Batch batch) {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Batch res = em.find(Batch.class, batch.getId());
		if (res == null) {
			em.close();
			return false;
		}

		// first remove all trainees
		if (res.getTrainees() != null) {
			res.getTrainees().forEach(trainee -> traineeDao.remove(trainee));
		}

		em.getTransaction().begin();
		em.remove(res);
		em.getTransaction().commit();
		em.close();
		return true;
	}
	
	@Override
	public long numberOfBatches() {
		EntityManager em = JpaUtil.getInstance().getEntityManager();
		Query query = em.createNamedQuery("Batch.countAll");
		long res = (long) query.getSingleResult();
		em.close();
		return res;
	}

	public ITraineeDao getTraineeDao() {
		return traineeDao;
	}

	public void setTraineeDao(ITraineeDao traineeDao) {
		this.traineeDao = traineeDao;
	}

}
