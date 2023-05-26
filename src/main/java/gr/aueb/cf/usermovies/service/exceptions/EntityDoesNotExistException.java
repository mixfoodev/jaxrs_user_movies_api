package gr.aueb.cf.usermovies.service.exceptions;

public class EntityDoesNotExistException extends Exception {

    public EntityDoesNotExistException(Class<?> entityClass) {
        super("Entity " + entityClass.getSimpleName() + " does not exist.");
    }
}
