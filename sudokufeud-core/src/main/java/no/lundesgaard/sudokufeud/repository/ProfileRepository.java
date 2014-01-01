package no.lundesgaard.sudokufeud.repository;

import no.lundesgaard.sudokufeud.model.Profile;

public interface ProfileRepository extends Repository<Profile> {
    String readIdByUserId(String userId);

    Profile readByUserId(String userId);
}
