package com.imageboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {
    Collection<Thread> findByThemeName(String themeName);
}
