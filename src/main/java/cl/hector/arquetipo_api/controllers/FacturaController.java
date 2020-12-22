package cl.hector.arquetipo_api.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cl.hector.arquetipo_api.validators.ValidacionDeFondo;
import cl.hector.arquetipo_api.validators.ValidacionDeForma;
import cl.hector.arquetipo_api.modelo.Factura;


@RestController
@RequestMapping("facturas")
public class FacturaController {

	@RequestMapping(
			value="/",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Factura> guardarFactura(
			@Validated({ValidacionDeForma.class,ValidacionDeFondo.class})
			@RequestBody
			Factura factura) {
		
		return ResponseEntity.ok(factura);
	}
}