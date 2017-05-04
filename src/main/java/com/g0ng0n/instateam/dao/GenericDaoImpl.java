package com.g0ng0n.instateam.dao;

import com.g0ng0n.instateam.model.Project;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by g0ng0n.
 */
@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    @Autowired
    SessionFactory sessionFactory;

    private Class<T> aClass;

    public GenericDaoImpl(){
        this.aClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public List<T> findAll(){
        Session session = sessionFactory.openSession();
        List<T> entities = session.createCriteria(aClass).list();
        session.close();
        return entities;
    }

    @Override
    public T findById(Long id) {
        Session session = sessionFactory.openSession();
        T entity = session.get(aClass, id);
        session.close();
        return entity;
    }

    @Override
    public void save(T type) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(type);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(T type) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(type);
        session.getTransaction().commit();
        session.close();
    }
}


