package com.squarewhite.imageboard.repositories;

import com.squarewhite.imageboard.entities.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, Long> {
    @Secured("ROLE_ADMIN")
    @Override
    void delete(Long id);

    @Query("select t from Thread t where t.theme.themeId = ?1 order by t.dateUpdated desc")
    Page<Thread> findThreadsByThemeId(Long id, Pageable pageable);
}
