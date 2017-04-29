package com.g0ng0n.instateam.service;

import com.g0ng0n.instateam.model.Role;

import java.util.List;

/**
 * Created by g0ng0n.
 */
public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);

    void save(Role role);

    void delete(Role role);
}
