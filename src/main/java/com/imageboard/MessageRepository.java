package com.imageboard;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import static javafx.scene.input.KeyCode.T;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
}
