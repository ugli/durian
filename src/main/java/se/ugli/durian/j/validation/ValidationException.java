package se.ugli.durian.j.validation;

import java.util.List;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -6756417009011290626L;

	public ValidationException(final Exception e) {
		super(e);
	}

	public ValidationException(final List<ValidationError> errors) {
		super(errors.toString());
	}

}
