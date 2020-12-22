package cl.hector.arquetipo_api.modelo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import cl.hector.arquetipo_api.validators.facturas.AsegurarEstadoDeAprobacionValido;
import cl.hector.arquetipo_api.validators.facturas.AsegurarMontoTotal;
import cl.hector.arquetipo_api.validators.ValidacionDeFondo;
import cl.hector.arquetipo_api.validators.ValidacionDeForma;

@AsegurarMontoTotal(groups=ValidacionDeFondo.class)
@AsegurarEstadoDeAprobacionValido(groups=ValidacionDeFondo.class)
public class Factura {
	
	@NotEmpty(groups=ValidacionDeForma.class)
	private String estadoDeAprobacion;	
	@NotNull(groups=ValidacionDeForma.class)
	private Integer rutCliente;
	@NotNull(groups=ValidacionDeForma.class)
	private Integer rut;
	@NotNull(groups=ValidacionDeForma.class)
	private Integer montoTotal;	
	
	@Valid
	@NotEmpty(groups=ValidacionDeForma.class)
	private List<DetalleDeFactura> detalles;

	public Integer getRutCliente() {
		return rutCliente;
	}

	public void setRutCliente(Integer rutCliente) {
		this.rutCliente = rutCliente;
	}

	public Integer getRut() {
		return rut;
	}

	public void setRut(Integer rut) {
		this.rut = rut;
	}

	public Integer getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Integer montoTotal) {
		this.montoTotal = montoTotal;
	}

	public List<DetalleDeFactura> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleDeFactura> detalles) {
		this.detalles = detalles;
	}

	public String getEstadoDeAprobacion() {
		return estadoDeAprobacion;
	}

	public void setEstadoDeAprobacion(String estadoDeAprobacion) {
		this.estadoDeAprobacion = estadoDeAprobacion;
	}

}