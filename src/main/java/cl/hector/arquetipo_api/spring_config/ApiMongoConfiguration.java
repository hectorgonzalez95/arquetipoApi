package cl.hector.arquetipo_api.spring_config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"cl.hector.arquetipo_api.repo"})
@EnableMongoRepositories(basePackages = "cl.hector.arquetipo_api.repo", mongoTemplateRef = "primaryMongoTemplate")
@EnableCaching
/**
 * no funciona en springboot > 2.1.3 
 * @EnableMongoAuditing**/
public class ApiMongoConfiguration {
	
    @Value("${spring.data.mongodb.host}")
    private String mongoHost;

    @Value("${spring.data.mongodb.port}")
    private String mongoPort;

    @Value("${spring.data.mongodb.database}")
    private String mongoDB;

	@Primary
	@Bean(name = "primaryMongoTemplate")
	public MongoTemplate mongoTemplate(@Qualifier("mongoFactory") MongoDbFactory mongoFactory) {
		return new MongoTemplate(mongoFactory, getDefaultMongoConverter(mongoFactory));
	}
	
	@Bean 
	public MappingMongoConverter getDefaultMongoConverter(@Qualifier("mongoFactory") MongoDbFactory mongoFactory) {
		return new MappingMongoConverter(new DefaultDbRefResolver(mongoFactory),new MongoMappingContext());
	}

	@Primary
	@Bean("mongoFactory")
	public MongoDbFactory mongoFactory() {
		return new SimpleMongoDbFactory(new MongoClient(mongoHost + ":" + mongoPort), mongoDB);
	}

	@Bean(name = "mongoClient")
    public MongoClient mongoClient() {
        return new MongoClient(mongoHost, Integer.parseInt(mongoPort));
        
    }
	
}