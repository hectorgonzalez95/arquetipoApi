/**
 * 
 */
package cl.hector.arquetipo_api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cl.hector.arquetipo_api.interceptors.MDCInterceptor;

import cl.hector.arquetipo_api.spring_config.ApiGlobalConfiguration; 
import cl.hector.arquetipo_api.spring_config.ApiSecurityConfiguration;
import cl.hector.arquetipo_api.spring_config.ApiSwagger2Configuration;
import cl.hector.arquetipo_api.spring_config.ApiMongoConfiguration;
import cl.hector.arquetipo_api.spring_config.ApiGracefulShutdownConfiguration;

/**
 * 
 * @author alvarotellez
 *
 */
@SpringBootApplication
public class Api {
	private static Logger logger = LogManager.getLogger(Api.class);
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) {
		MDCInterceptor.appStartup();
		Class<?>[] configClasses = {
				ApiGlobalConfiguration.class,
				ApiSecurityConfiguration.class,
				ApiSwagger2Configuration.class,
				ApiMongoConfiguration.class,
				ApiGracefulShutdownConfiguration.class
		};
		SpringApplication.run(configClasses,args);
		if(logger.isDebugEnabled())
			logger.debug("{} HAS DEBUG ENABLED : {}",Api.class.getCanonicalName(),logger.isDebugEnabled());
		logger.info("{} UP & RUNNING!!!",Api.class.getName());
	}
}