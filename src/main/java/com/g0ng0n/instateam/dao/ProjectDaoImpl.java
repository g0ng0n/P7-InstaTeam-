package com.g0ng0n.instateam.dao;

import com.g0ng0n.instateam.model.Project;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by g0ng0n.
 */
public class ProjectDaoImpl implements ProjectDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Project> findAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Get All categories with a Hibernate criteria
        List<Project> projects = session.createCriteria(Project.class).list();

        session.close();
        return projects;
    }

    @Override
    public Project findById(Long id) {
        Session session = sessionFactory.openSession();

        Project project = session.get(Project.class, id);
        // make sure that the collection of gifs is initialized before the session is close
        Hibernate.initialize(project.getCollaborators());

        session.close();
        return project;
    }

    @Override
    public void save(Project project) {
        //Open session
        Session session = sessionFactory.openSession();

        // Begin trasanction
        session.beginTransaction();

        // save the project
        session.saveOrUpdate(project);

        // commit the transaction
        session.getTransaction().commit();

        // close session
        session.close();
    }

    @Override
    public void delete(Project project) {
        //Open session
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.delete(project);

        session.getTransaction().commit();
        // close session
        session.close();
    }
}
