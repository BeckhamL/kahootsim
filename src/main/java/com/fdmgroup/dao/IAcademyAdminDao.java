package com.fdmgroup.dao;

import com.fdmgroup.exception.NoSuchDatabaseEntryException;
import com.fdmgroup.model.AcademyAdmin;

public interface IAcademyAdminDao extends IStorage<AcademyAdmin>, IEditable<AcademyAdmin>, IRemovable<AcademyAdmin> {
	public AcademyAdmin findByEmail(String email) throws NoSuchDatabaseEntryException;
}
