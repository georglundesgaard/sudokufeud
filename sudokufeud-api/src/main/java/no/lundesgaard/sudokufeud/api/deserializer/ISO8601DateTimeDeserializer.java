package no.lundesgaard.sudokufeud.api.deserializer;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

public class ISO8601DateTimeDeserializer extends StdDeserializer<DateTime> {

    private DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    public ISO8601DateTimeDeserializer() {
        super(DateTime.class);
    }

    @Override
    public DateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateTimeString = jp.getText();
        try {
            return dateTimeFormatter.parseDateTime(dateTimeString);
        } catch (IllegalArgumentException e) {
            throw ctxt.weirdStringException(_valueClass, "date has unexpted date format: " + dateTimeString);
        }
    }
}
