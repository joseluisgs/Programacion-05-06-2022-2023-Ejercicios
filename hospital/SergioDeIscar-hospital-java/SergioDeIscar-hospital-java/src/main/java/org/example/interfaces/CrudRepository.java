package org.example.interfaces;

import java.util.List;

public interface CrudRepository <T, ID>{
    T save(T entity);
    T findById(ID id);
    T deleteById(ID id);
    List<T> findAll();
    void saveAll(List<T> entities);
}
