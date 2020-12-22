package cl.hector.arquetipo_api.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cl.hector.arquetipo_api.modelo.ApiMessage;
import cl.hector.arquetipo_api.repo.MessageRepo;

@Service
public class ApiService {
	private static Logger logger = LogManager.getLogger(ApiService.class);
	
	@Autowired @Qualifier("messagesRepo")
	MessageRepo messagesRepo;

	/**
	 * Metodo que consulta al repositorio por un mensaje
	 * @return ApiMessage mensaje
	 */
	public ApiMessage obtieneMensaje(Integer idMensaje) {
		ApiMessage mensaje = messagesRepo.obtieneMensaje(idMensaje);
		logger.debug(mensaje);
		return mensaje;
	}

}