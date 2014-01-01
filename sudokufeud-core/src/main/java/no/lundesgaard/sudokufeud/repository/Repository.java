package no.lundesgaard.sudokufeud.repository;

import no.lundesgaard.sudokufeud.model.Identifiable;

public interface Repository<T extends Identifiable> {
    String create(T t);

    T read(String id);

    void update(T t);

    void delete(String id);
}
