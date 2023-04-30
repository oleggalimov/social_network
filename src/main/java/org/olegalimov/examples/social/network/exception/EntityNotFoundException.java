package org.olegalimov.examples.social.network.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String id) {
        super("Сущность с id = " + id + " не найдена!");
    }
}
