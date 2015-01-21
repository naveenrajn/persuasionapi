package osu.ceti.persuasionapi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.services.wrappers.RestServiceResponse;

public class ControllerTemplate {

	public RestServiceResponse successResponse() {
		//TODO: Verify if this creates a logger using the derived class' instance
		Log log = LogFactory.getLog(getClass());
		try {
			RestServiceResponse response = new RestServiceResponse(RestServiceResponse.SUCCESS);
			return response;
		} catch(PersuasionAPIException e) {
			log.error("Service call complete. Failed to create RestServiceResponse Object");
			throw new RuntimeException("Service processing succesful"
					+ ". Failed to create response");
		}
	}

	public RestServiceResponse successResponse(Object content) {
		Log log = LogFactory.getLog(getClass());
		try {
			RestServiceResponse response = successResponse();
			response.setContent(content);
			return response;
		} catch(PersuasionAPIException e) {
			log.error("Service call complete. Failed to create RestServiceResponse Object");
			throw new RuntimeException("Service processing succesful"
					+ ". Failed to create response");
		}
	}

	public RestServiceResponse failureResponse(String errorCode, String errorMessage) {
		Log log = LogFactory.getLog(getClass());
		try {
			RestServiceResponse response = new RestServiceResponse(RestServiceResponse.FAILURE);
			response.setErrorCode(errorCode);
			response.setErrorMessage(errorMessage);
			return response;
		} catch (PersuasionAPIException e) {
			log.error("Failed to create RestServiceResponse Object");
			throw new RuntimeException("Service processing unsuccesful"
					+ ". And failed to create a failure response");
		}
	}

	public RestServiceResponse failureResponse(String errorCode, Throwable e) {
		return failureResponse(errorCode, e.getMessage());
	}
}
