package com.dsync.dao;

import java.util.List;

public interface GenericDao<PK, T> {

	void save(T entity);

	T getById(PK id);

	List<T> getAll();

}
