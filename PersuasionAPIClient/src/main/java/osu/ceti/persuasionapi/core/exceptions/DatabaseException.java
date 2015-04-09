package osu.ceti.persuasionapi.core.exceptions;

import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;

public class DatabaseException extends PersuasionAPIException {

	private static final long serialVersionUID = 1L;

	public DatabaseException(String message) {
		super(InternalErrorCodes.DATABASE_EXCEPTION, message);
	}

	public DatabaseException(String message, Throwable cause) {
		super(InternalErrorCodes.DATABASE_EXCEPTION, message, cause);
	}

}
