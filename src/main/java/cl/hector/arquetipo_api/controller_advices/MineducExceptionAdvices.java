package cl.hector.arquetipo_api.controller_advices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import cl.hector.arquetipo_api.helpers.BadRequestBuilder;
import cl.hector.arquetipo_api.modelo.BadRequestBody;

@RestControllerAdvice
public class MineducExceptionAdvices {
	
	private static final Logger logger = LoggerFactory.getLogger(MineducExceptionAdvices.class);

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<BadRequestBody> handleNotReadlableException(HttpMessageNotReadableException ex) {
		BadRequestBuilder badRequestBuilder = new BadRequestBuilder();
		if (ex.getCause() instanceof InvalidFormatException) {
			InvalidFormatException iex = (InvalidFormatException) ex.getCause();						
			badRequestBuilder.addError(iex, String.format("valor inválido: %s", iex.getValue()));
			badRequestBuilder.addError(iex, iex.getMessage());
		} else if (ex.getCause() instanceof JsonMappingException) {			
			badRequestBuilder.addError((JsonMappingException) ex.getCause(), ex.getMessage());
		} else {
			badRequestBuilder.addError("body", ex.getMessage());
		}
		return badRequestBuilder.buildBadRequest();
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<BadRequestBody> handleGenericException(Exception ex) {
		logger.error("error no controlado ", ex);
		BadRequestBuilder badRequestBuilder = new BadRequestBuilder();
		badRequestBuilder.addError("body", ex.getMessage());
		return badRequestBuilder.buildBadRequest();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<BadRequestBody> handleDataBinderException(MethodArgumentNotValidException bindingException) {		
		return new BadRequestBuilder()// sigue
				.putAllErrors(bindingException.getBindingResult())// sigue
				.buildBadRequest()// sigue
		;
	}
}