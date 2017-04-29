package com.g0ng0n.instateam.dao;

import com.g0ng0n.instateam.model.Collaborator;
import com.g0ng0n.instateam.model.Project;
import com.g0ng0n.instateam.model.Role;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by g0ng0n.
 */
public class CollaboratorDaoImpl implements CollaboratorDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Collaborator> findAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Get All categories with a Hibernate criteria
        List<Collaborator> collaborators = session.createCriteria(Collaborator.class).list();

        session.close();
        return collaborators;
    }

    @Override
    public Collaborator findById(Long id) {

        Session session = sessionFactory.openSession();

        Collaborator collaborator = session.get(Collaborator.class, id);
        // make sure that the collection of gifs is initialized before the session is close
        Hibernate.initialize(collaborator.getRole());

        session.close();
        return collaborator;
    }

    @Override
    public void save(Collaborator collaborator) {
        //Open session
        Session session = sessionFactory.openSession();

        // Begin trasanction
        session.beginTransaction();

        // save the project
        session.saveOrUpdate(collaborator);

        // commit the transaction
        session.getTransaction().commit();

        // close session
        session.close();
    }

    @Override
    public void delete(Collaborator collaborator) {
        //Open session
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.delete(collaborator);

        session.getTransaction().commit();
        // close session
        session.close();
    }
}
