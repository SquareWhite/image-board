package com.imageboard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    Collection<Message> findByThreadId(Long id);
}
