package se.ugli.durian.j.validation;

public class ValidationError {

	public final ValidationErrorType type;
	public final String description;

	public ValidationError(final ValidationErrorType type, final String description) {
		this.type = type;
		this.description = description;
	}

	@Override
	public String toString() {
		return "ValidationError [type=" + type + ", description=" + description + "]";
	}

}
