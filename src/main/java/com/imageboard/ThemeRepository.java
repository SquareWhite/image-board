package com.imageboard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Long>{
    Theme findByName(String name);
    Collection<Theme> findAll();
}
