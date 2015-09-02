package org.lu.config;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.lu.entities.Comments;
import org.lu.entities.Post;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

@Configuration
@EnableMongoRepositories(basePackages = { "org.lu.repositories" })
public class DBconfig extends AbstractMongoConfiguration {

	@Override
	protected String getDatabaseName() {
		return "blog";
	}

	@Bean
	@Override
	public Mongo mongo() throws Exception {
		MongoClient mongo = new MongoClient("localhost");
		mongo.setWriteConcern(WriteConcern.SAFE);
		return mongo;
	}

	@Bean
	@Override
	public CustomConversions customConversions() {
		List<Converter<?, ?>> converterList = new LinkedList<>();
		converterList.add(new PostReadConverter());
		return new CustomConversions(converterList);
	}

	@ReadingConverter
	protected static class PostReadConverter implements
			Converter<DBObject, Post> {

		@SuppressWarnings("unchecked")
		@Override
		public Post convert(DBObject source) {
			Post post = new Post();
			post.setId((ObjectId) source.get("_id"));
			if (((String) source.get("author")) == "add") {
				post.setAuthor("Tom");
			} else {
				post.setAuthor("add");
			}

			post.setBody("content: " + (String) source.get("body"));
			post.setPermalink((String) source.get("permalink"));
			post.setTitle("title: " + (String) source.get("title"));
			post.setDate((Date) source.get("date"));
			post.setTags(new HashSet<String>((List<String>) source.get("tags")));
			post.setComments((List<Comments>) source.get("comments"));

			return post;
		}
	}
}
