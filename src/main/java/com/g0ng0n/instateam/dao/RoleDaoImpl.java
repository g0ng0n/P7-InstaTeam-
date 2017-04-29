package com.g0ng0n.instateam.dao;

import com.g0ng0n.instateam.model.Role;
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
        return null;
    }

    @Override
    public void save(Role role) {

    }

    @Override
    public void delete(Role role) {

    }
}
