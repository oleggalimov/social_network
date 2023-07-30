package org.olegalimov.examples.social.network.core.exception;

public class TooManyResultException extends RuntimeException {
    public TooManyResultException(String id, int count) {
        super("Для сущности с id = " + id + " найдено больше одной записи (" + count + ")!");
    }
}
