package no.lundesgaard.sudokufeud.repository.exception;

import java.util.Formatter;

public class EntityNotFoundException extends RepositoryException {
    private static final long serialVersionUID = -7872466126206893005L;

    private final Class<?> entityType;
    private final String id;

    public EntityNotFoundException(Class<?> entityType, String id) {
        this.entityType = entityType;
        this.id = id;
    }

    public Class<?> getEntityType() {
        return entityType;
    }

    public String getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return new Formatter().format("%s with id <%s> not found", entityType.getSimpleName(), id).toString();
    }
}
