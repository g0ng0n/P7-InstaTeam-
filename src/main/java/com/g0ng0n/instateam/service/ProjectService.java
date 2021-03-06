package com.g0ng0n.instateam.service;

import com.g0ng0n.instateam.model.Project;

import java.util.List;

/**
 * Created by g0ng0n.
 */
public interface ProjectService {

    List<Project> findAll();

    Project findById(Long id);

    void save(Project project);

    void delete(Project project);
}
