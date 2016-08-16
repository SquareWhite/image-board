package com.squarewhite.imageboard.repositories;

import com.squarewhite.imageboard.entities.Theme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Long>{
    Collection<Theme> findAll();
    Theme findByName(String name);

    @Secured("ROLE_ADMIN")
    @Override
    void delete(Long id);
}
