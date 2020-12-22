package cl.hector.arquetipo_api.validators.facturas;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import cl.hector.arquetipo_api.validators.facturas.AsegurarEstadoDeAprobacionValido.AsegurarEstadoDeAprobacionValidoHelper;
import cl.hector.arquetipo_api.modelo.Factura;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Constraint(validatedBy = AsegurarEstadoDeAprobacionValidoHelper.class)
public @interface AsegurarEstadoDeAprobacionValido {
	String message() default "Debe ser Aprobado,Rechazado o Pendiente (case sensitive)";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	public class AsegurarEstadoDeAprobacionValidoHelper
			implements ConstraintValidator<AsegurarEstadoDeAprobacionValido, Factura> {
		private AsegurarEstadoDeAprobacionValido anotacion;

		@Override
		public void initialize(AsegurarEstadoDeAprobacionValido anotacion) {
			this.anotacion = anotacion;
		}

		@Override
		public boolean isValid(Factura factura, ConstraintValidatorContext ctx) {
			String escalar = factura.getEstadoDeAprobacion();
			if (escalar == null) {
				ctx.disableDefaultConstraintViolation();
				ctx.buildConstraintViolationWithTemplate(anotacion.message()).addPropertyNode("estadoDeAprobacion")
						.addConstraintViolation();
				return false;
			}
			switch (escalar) {
			case "Aprobado":
			case "Rechazado":
			case "Pendiente":
				return true;
			default:
				ctx.disableDefaultConstraintViolation();
				ctx.buildConstraintViolationWithTemplate(anotacion.message()).addPropertyNode("estadoDeAprobacion")
						.addConstraintViolation();
				return false;
			}
		}
	}
}