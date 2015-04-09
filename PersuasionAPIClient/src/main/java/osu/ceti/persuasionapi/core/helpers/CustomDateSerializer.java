package osu.ceti.persuasionapi.core.helpers;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {    
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2) throws 
        IOException, JsonProcessingException {      
    	String formattedDate = StringHelper.dateToString(value);
        gen.writeString(formattedDate);
    }
}