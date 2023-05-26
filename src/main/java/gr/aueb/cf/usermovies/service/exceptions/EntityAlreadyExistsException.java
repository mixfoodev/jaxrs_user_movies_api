package gr.aueb.cf.usermovies.service.exceptions;

public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException(Class<?> entityClass) {
        super("Entity " + entityClass.getSimpleName() + " already exists.");
    }
}
