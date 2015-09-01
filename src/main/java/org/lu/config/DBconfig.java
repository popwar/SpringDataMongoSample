package org.lu.config;

import java.util.Collections;

import org.lu.entities.Post;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
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

	@Override
	public Mongo mongo() throws Exception {
		MongoClient mongo = new MongoClient("localhost");
		mongo.setWriteConcern(WriteConcern.SAFE);
		return mongo;
	}
	
//	protected String getMappingBasePackage() {
//		return "org.lu.repositories";
//	}

	// @Bean
	@Override
	public CustomConversions customConversions() {

		return new CustomConversions(Collections.emptyList());

		// List<Converter<?, ?>> converterList = new ArrayList<>();
		// converterList.add(new
		// org.springframework.data.mongodb.test.PersonReadConverter());
		// converterList.add(new
		// org.springframework.data.mongodb.test.PersonWriteConverter());
		// return new CustomConversions(converterList);
	}

	@ReadingConverter
	public static class PostsReadConverter implements
			Converter<DBObject, Post> {
		@Override
		public Post convert(DBObject source) {
			return new Post();
		}
	}
}
