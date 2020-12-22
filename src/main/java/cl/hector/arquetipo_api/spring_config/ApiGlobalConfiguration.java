package cl.hector.arquetipo_api.spring_config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.ibatis.session.SqlSessionFactory;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import cl.hector.arquetipo_api.mappers.MessageMapper;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cl.hector.arquetipo_api.interceptors.MDCInterceptor;


/**
 * @author Rodrigo Alvarez, Alvaro Tellez
 *
 */
@Configuration
@SpringBootApplication
@EnableAutoConfiguration(exclude={XADataSourceAutoConfiguration.class})
@ComponentScan(basePackages = { "cl.hector.arquetipo_api" } )
public class ApiGlobalConfiguration implements WebMvcConfigurer{
	
	@Autowired 
	private Environment env;
	
	/** web mvc mineduc framework conf **/
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		WebMvcConfigurer.super.addViewControllers(registry);
	}

	@Bean
	public HandlerInterceptor addMDCInterceptor() {
		return new MDCInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {		
		registry.addInterceptor(addMDCInterceptor()).addPathPatterns("/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}	
	/** web mvc framework conf **/	

	
	@Bean
	@Primary
	public DataSource dataSource(){
		HikariConfig config = new HikariConfig();
		
		config.setDriverClassName(env.getProperty("dbtest.datasource.driver-class-name"));
		config.setJdbcUrl(env.getProperty("dbtest.datasource.url"));
		config.setUsername(env.getProperty("dbtest.datasource.username"));
		config.setPassword(env.getProperty("dbtest.datasource.password"));
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		config.setMinimumIdle(1);
		config.setMaximumPoolSize(20);

		return new HikariDataSource(config);
	}
	
	@Bean
	@Primary
	public SqlSessionFactory sqlSessionFactory() throws Exception {
	    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	    sessionFactory.setDataSource(dataSource());
	    Resource[] arrResource = new PathMatchingResourcePatternResolver().getResources("classpath:maps/postgresql/*.xml");
	    sessionFactory.setMapperLocations(arrResource);	    
	    return sessionFactory.getObject();
	}
	
	@Bean(name="MapperFactoryDbTest")
	public MapperFactoryBean<MessageMapper> mapperFactorydbTest() throws Exception{
		MapperFactoryBean<MessageMapper> mapperFactory = new MapperFactoryBean<MessageMapper>();
		/** binding de la interface con sqlsession **/
		mapperFactory.setMapperInterface(cl.hector.arquetipo_api.mappers.MessageMapper.class);
		mapperFactory.setSqlSessionFactory(sqlSessionFactory());
		return mapperFactory;
	  }
	
	/**
	 * RestTemplate beans for API consumption
	 */
	@Bean
	public Credentials credentials(){
		return new UsernamePasswordCredentials("", ""); // en caso de usar basic http auth
	}
	
	@Bean
	public CredentialsProvider credentialsProvider(){
		BasicCredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY,credentials());
		return provider;
	}
	
	@Bean
	public HttpComponentsClientHttpRequestFactory httpFactory(){
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider());
		CloseableHttpClient httpClient = httpClientBuilder.build();
		return new HttpComponentsClientHttpRequestFactory(httpClient);		
	}
	
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate(httpFactory());
	}	
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setDefaultEncoding("UTF-8");
		
		Properties fileEncodings = new Properties();
		fileEncodings.setProperty("application-arquetipo-api-messages", "UTF-8");
		bundle.setFileEncodings(fileEncodings);
		bundle.setFallbackToSystemLocale(true);
		
		bundle.setBasename("classpath:application-arquetipo-api-messages");
		return bundle;
	}
}
