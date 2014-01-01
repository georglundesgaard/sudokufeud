package no.lundesgaard.sudokufeud.api.serilizer;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

public class ISO8601DateTimeSerializer extends SerializerBase<DateTime> {

    private DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    public ISO8601DateTimeSerializer() {
        super(DateTime.class);
    }

    @Override
    public void serialize(
            DateTime dateTime,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {

        jsonGenerator.writeString(dateTimeFormatter.print(dateTime));
    }
}
