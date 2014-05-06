package no.lundesgaard.sudokufeud.repository;

import java.util.List;

import no.lundesgaard.sudokufeud.model.Game;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository<Game, Long>, GameRepositoryCustom {
	List<Game> findByPlayer2IsNull();
}
