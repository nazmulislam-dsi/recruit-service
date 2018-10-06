package org.ni.recruitservice.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.ni.recruitservice.exception.DataFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * Created by nazmul on 9/21/2018.
 */

public class JsonDateDeserializer extends JsonDeserializer<Date> {
    private Logger log = LoggerFactory.getLogger(JsonDateDeserializer.class);
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            String date = jsonParser.getText();
            LocalDate ld = LocalDate.parse(date, formatter);
            LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.MIN.toLocalTime());
            return Date.from(ldt.toInstant(ZonedDateTime.now(ZoneId.of("UTC")).getOffset()));
        } catch (Exception e) {
            throw new DataFormatException(e);
        }

    }
}
