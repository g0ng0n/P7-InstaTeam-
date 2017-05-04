package com.g0ng0n.instateam.dao;

import java.util.List;

/**
 * Created by g0ng0n.
 */
public interface GenericDao<T> {
    List<T> findAll();
    T findById(Long id);
    void save(T type);
    void delete(T type);
}
