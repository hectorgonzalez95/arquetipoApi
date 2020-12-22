package cl.hector.arquetipo_api.validators.facturas;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.stream.Collectors;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import cl.hector.arquetipo_api.validators.facturas.AsegurarMontoTotal.AsegurarMontoTotalHelper;
import cl.hector.arquetipo_api.modelo.DetalleDeFactura;
import cl.hector.arquetipo_api.modelo.Factura;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Constraint(validatedBy = AsegurarMontoTotalHelper.class)
public @interface AsegurarMontoTotal {
	String message() default "el monto total debe coincidir con los montos detallados en la factura";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public class AsegurarMontoTotalHelper implements ConstraintValidator<AsegurarMontoTotal, Factura> {
		private AsegurarMontoTotal anotacion;

		@Override
		public void initialize(AsegurarMontoTotal anotacion) {
			this.anotacion = anotacion;
		}

		@Override
		public boolean isValid(Factura factura, ConstraintValidatorContext ctx) {
			if (factura.getDetalles() == null || factura.getMontoTotal() == null) {
				ctx.disableDefaultConstraintViolation();
				ctx.buildConstraintViolationWithTemplate(anotacion.message()).addPropertyNode("montoTotal").addConstraintViolation();
				return false;
			}
			boolean montosIguales = factura.getMontoTotal().equals(//
					factura.getDetalles().stream().collect(//
							Collectors.summingInt( detalleDeFactura -> detalleDeFactura.getMonto() == null ? 0 : detalleDeFactura.getMonto())//
					)//
			);
			if (montosIguales)
				return true;
			
			ctx.disableDefaultConstraintViolation();
			ctx.buildConstraintViolationWithTemplate(anotacion.message()).addPropertyNode("montoTotal").addConstraintViolation();
			return false;
		}
	}
}