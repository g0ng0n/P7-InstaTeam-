package com.g0ng0n.instateam.dao;

import com.g0ng0n.instateam.model.Role;

import java.util.List;

/**
 * Created by g0ng0n.
 */
public interface RoleDao {

    List<Role> findAll();

    Role findById(Long id);

    void save(Role role);

    void delete(Role role);
}
