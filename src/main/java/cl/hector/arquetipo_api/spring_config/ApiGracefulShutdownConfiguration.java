package cl.hector.arquetipo_api.spring_config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cl.hector.arquetipo_api.tomcat8.MineducGracefulShutdown;

@Configuration
public class ApiGracefulShutdownConfiguration {

	@Bean
	public MineducGracefulShutdown gracefulShutdown() {
		return new MineducGracefulShutdown();
	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory(final MineducGracefulShutdown gracefulShutdown) {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(gracefulShutdown);
		return factory;
	}
}
