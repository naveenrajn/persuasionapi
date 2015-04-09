package osu.ceti.persuasionapi.core.helpers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		String date = arg0.getText();
		try {
			return StringHelper.toDateFromCharString(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
