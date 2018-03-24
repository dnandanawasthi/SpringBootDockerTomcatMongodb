package com.mvpjava.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@ComponentScan("com.mvpjava.util")
public class JsonDateTimeSerializer extends JsonSerializer<Date> {
  private static final SimpleDateFormat dateFormat = 
    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSZ");
   
  @Override
  public void serialize(Date date, JsonGenerator gen, 
    SerializerProvider provider)
      throws IOException, JsonProcessingException {
    String formattedDate = dateFormat.format(date);
    gen.writeString(formattedDate);
  }
}