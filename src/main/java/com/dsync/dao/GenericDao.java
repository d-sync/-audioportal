package com.dsync.dao;


public interface GenericDao<PK, T> {

	void save(T entity);

}
