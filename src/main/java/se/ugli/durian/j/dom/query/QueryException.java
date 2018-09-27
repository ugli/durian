package se.ugli.durian.j.dom.query;

public class QueryException extends RuntimeException {

	private static final long serialVersionUID = -1166841568205590490L;

	public QueryException(final Exception cause) {
		super(cause);
	}

	public QueryException(final String msg) {
		super(msg);
	}

}
