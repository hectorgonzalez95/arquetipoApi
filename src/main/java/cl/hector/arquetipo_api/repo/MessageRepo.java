package cl.hector.arquetipo_api.repo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import cl.mineduc.framework2.exceptions.MineducException;
import cl.hector.arquetipo_api.mappers.MessageMapper;
import cl.hector.arquetipo_api.modelo.ApiMessage;

@Repository("messagesRepo")
public class MessageRepo {
	private static Logger logger = LogManager.getLogger(MessageRepo.class);

		@Autowired
		private MessageMapper messageMapper;

		public ApiMessage obtieneMensaje(Integer idMensaje){
			if (idMensaje == null){
				throw new MineducException("id mensaje no puede ser nulo");
			}
			try {
				return messageMapper.getMessage(idMensaje);
			} catch (DataAccessException e) {
				logger.error("Error de base de datos al consultar mensaje", e);
				throw new MineducException("Error de base de datos al consultar mensaje", e);
			}
		}
}