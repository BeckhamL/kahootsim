package com.fdmgroup.dao;

import com.fdmgroup.model.Question;

public interface IQuestionDao extends IStorage<Question>, IEditable<Question>, IRemovable<Question> {
}
