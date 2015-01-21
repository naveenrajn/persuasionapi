package osu.ceti.persuasionapi.services.wrappers;

import osu.ceti.persuasionapi.core.exceptions.PersuasionAPIException;
import osu.ceti.persuasionapi.core.helpers.InternalErrorCodes;

public class RestServiceResponse {
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	
	private String responseType;
	private String errorCode;
	private String errorMessage;
	private String responseText;
	private Object content;
	
	public RestServiceResponse(String responseType) throws PersuasionAPIException {
		setResponseType(responseType);
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) throws PersuasionAPIException {
		if(SUCCESS.equals(responseType) || FAILURE.equals(responseType)) {
			this.responseType = responseType;
		} else {
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Response Type not recognized");
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) throws PersuasionAPIException {
		if(responseType.equals(FAILURE)) {
			this.errorCode = errorCode;
		} else {
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Setting error code not permitted for a success response");
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) throws PersuasionAPIException {
		if(responseType.equals(FAILURE)) {
			this.errorMessage = errorMessage;
		} else {
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Setting error message not permitted for a success response");
		}
	}
	
	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) throws PersuasionAPIException {
		if(responseType.equals(FAILURE)) {
			this.content = content;
		} else {
			throw new PersuasionAPIException(InternalErrorCodes.GENERATED_EXCEPTION
					, "Setting response content not permitted for a failure response");
		}
	}
	
}
