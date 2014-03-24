package no.lundesgaard.sudokufeud.repository.exception;

import no.lundesgaard.sudokufeud.model.Profile;

public class ProfileNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = -4921582884517832695L;

    public ProfileNotFoundException(String id) {
        super(Profile.class, id);
    }
}
