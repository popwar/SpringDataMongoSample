package org.lu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lu.config.DBconfig;
import org.lu.entities.Post;
import org.lu.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DBconfig.class)
public class AppTest {

	@Autowired
	PostRepository postRepository;

	@Test
	public void test() {
		Pageable pageable = new PageRequest(0, 10, Direction.ASC, "date");
		Page<Post> posts = postRepository.findByAuthorContaining("add",
				pageable);
		posts.iterator().forEachRemaining(
				post -> post.getTags().forEach(tag -> System.out.println(tag)));
	}

}