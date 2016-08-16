package com.squarewhite.imageboard.repositories;

import com.squarewhite.imageboard.entities.Thread;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, Long> {
    @Secured("ROLE_ADMIN")
    @Override
    void delete(Long id);
}
