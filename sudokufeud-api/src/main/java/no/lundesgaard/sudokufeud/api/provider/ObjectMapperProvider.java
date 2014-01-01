package no.lundesgaard.sudokufeud.api.provider;

import no.lundesgaard.sudokufeud.api.deserializer.ISO8601DateTimeDeserializer;
import no.lundesgaard.sudokufeud.api.serilizer.ISO8601DateTimeSerializer;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.DateTime;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperProvider implements ContextResolver<ObjectMapper> {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectMapperProvider.class);

    @Override
    public ObjectMapper getContext(Class<?> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("DateTime (de)serializers", new Version(1, 0, 0, null));
        module.addSerializer(DateTime.class, new ISO8601DateTimeSerializer());
        module.addDeserializer(DateTime.class, new ISO8601DateTimeDeserializer());
        objectMapper.registerModule(module);
        objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
        return objectMapper;
    }
}
