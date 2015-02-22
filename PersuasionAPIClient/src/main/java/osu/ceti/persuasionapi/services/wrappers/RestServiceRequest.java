package osu.ceti.persuasionapi.services.wrappers;

import com.fasterxml.jackson.annotation.JsonInclude;

public class RestServiceRequest<T> {

	//TODO: Add user auth key or appropriate auth variables
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
