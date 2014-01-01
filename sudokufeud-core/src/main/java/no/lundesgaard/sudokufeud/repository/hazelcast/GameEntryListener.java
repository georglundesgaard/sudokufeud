package no.lundesgaard.sudokufeud.repository.hazelcast;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import no.lundesgaard.sudokufeud.model.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class GameEntryListener implements EntryListener<String, Game> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameEntryListener.class);

    @Override
    public void entryAdded(EntryEvent<String, Game> entryEvent) {
        LOGGER.debug("gameMap added: {}", entryEvent.getKey());
        URL resource = getClass().getResource("/");
        LOGGER.debug("url: {}", resource);
    }

    @Override
    public void entryRemoved(EntryEvent<String, Game> entryEvent) {
        LOGGER.debug("gameMap removed: {}", entryEvent.getKey());
    }

    @Override
    public void entryUpdated(EntryEvent<String, Game> entryEvent) {
        LOGGER.debug("gameMap updated: {}", entryEvent.getKey());
    }

    @Override
    public void entryEvicted(EntryEvent<String, Game> entryEvent) {
        LOGGER.debug("gameMap evicted: {}", entryEvent.getKey());
    }
}
