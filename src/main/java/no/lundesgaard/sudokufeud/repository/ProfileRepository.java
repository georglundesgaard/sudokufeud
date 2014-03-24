package no.lundesgaard.sudokufeud.repository;

import no.lundesgaard.sudokufeud.model.Profile;

import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
	Profile findByUserId(String userId);
}
