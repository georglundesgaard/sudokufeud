package no.lundesgaard.sudokufeud.api.deserializer;

public class ISO8601DateTimeDeserializer /*extends StdDeserializer<DateTime>*/ {/*

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
*/}
