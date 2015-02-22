package osu.ceti.persuasionapi.client.exceptions;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;

public class InvalidConfigurationException extends PersuasionAPIException {

	private static final long serialVersionUID = 1L;

	public InvalidConfigurationException() {
		super("INVALID_CONFIGURATION", "Client configuration invalid. Please initialize"
				+ " proper configuration");
	}

}
