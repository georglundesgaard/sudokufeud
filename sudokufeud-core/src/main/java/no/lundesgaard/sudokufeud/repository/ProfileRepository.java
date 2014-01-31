package no.lundesgaard.sudokufeud.repository;

import no.lundesgaard.sudokufeud.model.Profile;

public interface ProfileRepository extends Repository<Profile> {
	Profile findByUserId(String userId);
}
