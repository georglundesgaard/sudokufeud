package no.lundesgaard.sudokufeud.api.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class LoggingConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingConfig.class);

    public LoggingConfig() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        LOGGER.debug("SLF4J bridge installed");
    }
}
