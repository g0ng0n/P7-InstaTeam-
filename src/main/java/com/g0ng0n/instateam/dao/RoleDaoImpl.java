package com.g0ng0n.instateam.dao;

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
public class RoleDaoImpl implements RoleDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> findAll() {

        // Open a session
        Session session = sessionFactory.openSession();

        // Get All categories with a Hibernate criteria
        List<Role> roles = session.createCriteria(Role.class).list();

        session.close();
        return roles;
    }

    @Override
    public Role findById(Long id) {
        Session session = sessionFactory.openSession();

        Role role = session.get(Role.class, id);
        // make sure that the collection of gifs is initialized before the session is close
        Hibernate.initialize(role.getCollaborator());

        session.close();
        return role;
    }

    @Override
    public void save(Role role) {
        //Open session
        Session session = sessionFactory.openSession();

        // Begin trasanction
        session.beginTransaction();

        // save the project
        session.saveOrUpdate(role);

        // commit the transaction
        session.getTransaction().commit();

        // close session
        session.close();
    }

    @Override
    public void delete(Role role) {
        //Open session
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.delete(role);

        session.getTransaction().commit();
        // close session
        session.close();
    }
}
