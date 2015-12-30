package com.taskmanager.service.impl;


import com.taskmanager.service.IGenericService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;


@Repository
public class GenericService<T, PK extends Serializable>
        implements IGenericService<T, PK> {

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public GenericService() {

    }

    public GenericService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Transactional
    @Override
    public T create(T t) {
        this.entityManager.persist(t);
        return t;
    }

    @Transactional
    @Override
    public T read(PK id) {
        return this.entityManager.find(entityClass, id);
    }

    @Transactional
    @Override
    public T update(T t) {
        return this.entityManager.merge(t);
    }

    @Transactional
    @Override
    public void delete(T t) {
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
    }

    @Transactional
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t").getResultList();
    }
}
