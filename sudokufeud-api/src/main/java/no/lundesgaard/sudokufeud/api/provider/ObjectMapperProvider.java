package no.lundesgaard.sudokufeud.api.provider;

//@Provider
//@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperProvider /*implements ContextResolver<ObjectMapper>*/ {/*
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
*/}
