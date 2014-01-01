package no.lundesgaard.sudokufeud.repository.hazelcast;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import no.lundesgaard.sudokufeud.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class ProfileEntryListener implements EntryListener<String, Profile> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileEntryListener.class);

    @Override
    public void entryAdded(EntryEvent<String, Profile> stringProfileEntryEvent) {
        LOGGER.debug("profile added: {}", stringProfileEntryEvent.getKey());
        URL resource = getClass().getResource("/");
        LOGGER.debug("url: {}", resource);
    }

    @Override
    public void entryRemoved(EntryEvent<String, Profile> stringProfileEntryEvent) {
        LOGGER.debug("profile removed: {}", stringProfileEntryEvent.getKey());
    }

    @Override
    public void entryUpdated(EntryEvent<String, Profile> stringProfileEntryEvent) {
        LOGGER.debug("profile updated: {}", stringProfileEntryEvent.getKey());
    }

    @Override
    public void entryEvicted(EntryEvent<String, Profile> stringProfileEntryEvent) {
        LOGGER.debug("profile evicted: {}", stringProfileEntryEvent.getKey());
    }
}
