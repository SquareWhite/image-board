package com.squarewhite.imageboard.repositories;

import com.squarewhite.imageboard.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    @Secured("ROLE_ADMIN")
    @Override
    void delete(Long id);

    @Query("select m from Message m where m.thread.threadId = ?1")
    Page<Message> findMessagesByThreadId(Long id, Pageable pageable);
}
