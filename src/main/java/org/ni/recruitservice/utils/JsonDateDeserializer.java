package org.ni.recruitservice.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.ni.recruitservice.exception.DataFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by nazmul on 9/21/2018.
 */

public class JsonDateDeserializer extends JsonDeserializer<Date> {
    private Logger log = LoggerFactory.getLogger(JsonDateDeserializer.class);
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        try {
            String date = jsonParser.getText();
            LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
            return Date.from(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
        } catch (Exception e) {
            throw new DataFormatException(e);
        }

    }
}
