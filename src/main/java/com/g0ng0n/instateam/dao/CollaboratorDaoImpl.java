package com.g0ng0n.instateam.dao;

import com.g0ng0n.instateam.model.Collaborator;
import com.g0ng0n.instateam.model.Role;
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
        return null;
    }

    @Override
    public void save(Collaborator collaborator) {

    }

    @Override
    public void delete(Collaborator collaborator) {

    }
}
