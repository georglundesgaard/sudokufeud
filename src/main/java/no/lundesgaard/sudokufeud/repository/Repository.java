package no.lundesgaard.sudokufeud.repository;

import no.lundesgaard.sudokufeud.model.Identifiable;

public interface Repository<T extends Identifiable> {
	T create(T t);

	T read(String id);

	T update(T t);

	void delete(String id);
}
