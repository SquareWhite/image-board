package com.imageboard;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, Long> {
}
