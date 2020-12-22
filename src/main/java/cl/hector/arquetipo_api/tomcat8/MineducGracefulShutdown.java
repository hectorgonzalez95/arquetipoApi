package cl.hector.arquetipo_api.tomcat8;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class MineducGracefulShutdown implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(MineducGracefulShutdown.class);
	private volatile Connector connector;

	@Override
	public void customize(Connector connector) {
		this.connector = connector;		
	}
	private static final String errorMsg = "GracefulShutdown: No se pudo salir calmadamente del contenedor api-spring-boot...";

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		log.info("Comenzando Gracefulhutdown");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			log.error(errorMsg,e);
		}
		this.connector.pause();
		Executor executor = this.connector.getProtocolHandler().getExecutor();
		if (executor instanceof ThreadPoolExecutor) {
			try {
				ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
				threadPoolExecutor.shutdown();
				if (!threadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
					log.error(errorMsg);
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}
}