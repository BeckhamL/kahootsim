package com.fdmgroup.dao;

import com.fdmgroup.model.Batch;

public interface IBatchDao extends IStorage<Batch>, IEditable<Batch>, IRemovable<Batch> {
	public long numberOfBatches();
}
