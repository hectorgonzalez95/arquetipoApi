package cl.hector.arquetipo_api.mappers;


import org.apache.ibatis.annotations.Param;

import cl.hector.arquetipo_api.modelo.ApiMessage;

public interface MessageMapper {
	ApiMessage getMessage(@Param(value = "idMensaje") Integer idEntidad);
}