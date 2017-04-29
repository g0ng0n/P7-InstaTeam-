package com.g0ng0n.instateam.dao;

import com.g0ng0n.instateam.model.Collaborator;

import java.util.List;

/**
 * Created by g0ng0n.
 */
public interface CollaboratorDao {

    List<Collaborator> findAll();

    Collaborator findById(Long id);

    void save(Collaborator collaborator);

    void delete(Collaborator collaborator);
}
