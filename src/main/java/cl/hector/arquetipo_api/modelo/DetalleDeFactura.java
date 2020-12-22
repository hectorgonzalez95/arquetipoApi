package cl.hector.arquetipo_api.modelo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import cl.hector.arquetipo_api.validators.ValidacionDeForma;

public class DetalleDeFactura {
	
	@NotNull(groups=ValidacionDeForma.class)
	private Integer monto;
	
	@NotEmpty(groups=ValidacionDeForma.class)
	private String glosa;

	public Integer getMonto() {
		return monto;
	}

	public void setMonto(Integer monto) {
		this.monto = monto;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
}
