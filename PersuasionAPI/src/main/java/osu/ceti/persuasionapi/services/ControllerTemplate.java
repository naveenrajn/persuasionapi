package osu.ceti.persuasionapi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;

public class ControllerTemplate {

	/**
	 * Generates a success response
	 * @return success response
	 */
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

	/**
	 * Generates a success response with the given response data
	 * @param data
	 * @return success resposne with data
	 */
	public RestServiceResponse successResponse(Object data) {
		Log log = LogFactory.getLog(getClass());
		try {
			RestServiceResponse response = successResponse();
			response.setData(data);
			return response;
		} catch(PersuasionAPIException e) {
			log.error("Service call complete. Failed to create RestServiceResponse Object");
			throw new RuntimeException("Service processing succesful"
					+ ". Failed to create response");
		}
	}

	/**
	 * Generates a failure response
	 * @param errorCode
	 * @param errorMessage
	 * @return failure response
	 */
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
