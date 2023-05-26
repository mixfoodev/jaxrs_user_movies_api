package gr.aueb.cf.usermovies.dao;


import gr.aueb.cf.usermovies.model.User;

/**
 * Provides CRUD operations for {@link User}
 */
public interface IUserDAO {

    /**
     * Inserts a {@link User} into datasource.
     *
     * @param user the {@link User} to be inserted.
     * @return the inserted {@link User}.
     */
    User insert(User user);

    /**
     * Updates a {@link User} in datasource.
     *
     * @param user the {@link User} to be updated.
     * @return the updated {@link User}.
     */
    User update(User user);

    /**
     * Deletes a {@link User} from datasource.
     *
     * @param id the {@link User#id} needed to be deleted.
     * @return the deleted {@link User}.
     */
    User delete(Long id);

    /**
     * Retrieves a {@link User} from datasource
     * based on {@link User#id}.
     *
     * @param id the {@link User#id} to be retrieved.
     * @return the retrieved {@link User}.
     */
    User getById(Long id);

    /**
     * Retrieves a {@link User} from datasource
     * based on {@link User#username}.
     *
     * @param username the {@link User#username} to be retrieved.
     * @return the retrieved {@link User}.
     */
    User getByUsername(String username);

}
