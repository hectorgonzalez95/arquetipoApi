package cl.hector.arquetipo_api.controllers;

import javax.servlet.http.HttpServletResponse;

import io.micrometer.core.instrument.Metrics;

import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.hector.arquetipo_api.modelo.ApiMessage;
import cl.hector.arquetipo_api.services.ApiService;

@RestController
@RequestMapping("/")
public class ApiController {
	
	@Autowired ApiService service;
	
	@GetMapping(value = "mensaje/{idMensaje}", produces = {MediaType.APPLICATION_JSON_VALUE})
	@ApiOperation(value = "Obtiene Un Mensaje de saludo de ejemplo")
	public ApiMessage obtieneMensaje(@PathVariable Integer idMensaje,HttpServletResponse response){
		
		//Metrica de cuantas invocaciones se han realizado a este controlador MVC		
		Metrics.counter("api.api-test-dos.obtieneMensaje").increment();
		
		ApiMessage mensaje = service.obtieneMensaje(idMensaje);
		if(null!=mensaje){
			return mensaje;
		}else{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}