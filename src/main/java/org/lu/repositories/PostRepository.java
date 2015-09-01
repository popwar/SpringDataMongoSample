package org.lu.repositories;

import org.lu.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

	Page<Post> findByAuthorContaining(String author, Pageable pageable);

}
