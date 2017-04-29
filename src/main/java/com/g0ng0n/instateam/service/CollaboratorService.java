package com.g0ng0n.instateam.service;

import com.g0ng0n.instateam.model.Collaborator;

import java.util.List;

/**
 * Created by g0ng0n.
 */
public interface CollaboratorService {

    List<Collaborator> findAll();

    Collaborator findById(Long id);

    void save(Collaborator collaborator);

    void delete(Collaborator collaborator);
}
