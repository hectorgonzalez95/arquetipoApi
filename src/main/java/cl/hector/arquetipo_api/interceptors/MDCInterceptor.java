package cl.hector.arquetipo_api.interceptors;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cl.mineduc.framework2.utils.StringUtils;

public class MDCInterceptor extends HandlerInterceptorAdapter {
	
	private static final String NOMBRE_SISTEMA = "api-spring-boot";
	private static final String MDC_X_FORWARDED_FOR = "mdcXForwardedFor";
	private static final String NULL = "NULL";
	private static final String MDC_NOMBRE_SISTEMA = "mdcSistema";
	private static final String MDC_NOMBRE_USUARIO = "mdcUsuario";
	private static final String MDC_J_SESSION_ID = "mdcJavaSessionId";

	@Override
	public boolean preHandle(HttpServletRequest requestParam, HttpServletResponse responseParam, Object handlerParam) throws Exception {		
		String ip = requestParam.getHeader("X-Forwarded-For");
		if (StringUtils.isEmpty(ip)) {
			ip = NULL;
		}
		MDC.put(MDC_X_FORWARDED_FOR, ip);
		MDC.put(MDC_J_SESSION_ID, UUID.randomUUID().toString());
		MDC.put(MDC_NOMBRE_USUARIO, NULL);
		MDC.put(MDC_NOMBRE_SISTEMA, NOMBRE_SISTEMA);
		return super.preHandle(requestParam, responseParam, handlerParam);
	}
	@Override
	public void afterCompletion(HttpServletRequest requestParam, HttpServletResponse responseParam, Object handlerParam, Exception exParam) throws Exception {		
		super.afterCompletion(requestParam, responseParam, handlerParam, exParam);
		MDC.clear();
	}

	static public void appStartup() {
		MDC.put(MDC_X_FORWARDED_FOR, NULL);
		MDC.put(MDC_J_SESSION_ID, NULL);
		MDC.put(MDC_NOMBRE_USUARIO, NULL);
		MDC.put(MDC_NOMBRE_SISTEMA, NOMBRE_SISTEMA);
	}
}