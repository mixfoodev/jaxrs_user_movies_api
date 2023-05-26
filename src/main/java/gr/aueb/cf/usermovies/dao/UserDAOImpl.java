package gr.aueb.cf.usermovies.dao;

import gr.aueb.cf.usermovies.model.User;
import gr.aueb.cf.usermovies.service.util.JPAHelper;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.ext.Provider;

@Provider
@RequestScoped
@Named("userDAO")
public class UserDAOImpl implements IUserDAO {
    @Override
    public User insert(User user) {
        JPAHelper.getEntityManager().persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        JPAHelper.getEntityManager().merge(user);
        return user;
    }

    @Override
    public User delete(Long id) {
        User user = JPAHelper.getEntityManager().find(User.class, id);
        JPAHelper.getEntityManager().remove(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        return JPAHelper.getEntityManager().find(User.class, id);
    }

    @Override
    public User getByUsername(String username) {
        CriteriaBuilder builder = JPAHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> selectQuery = builder.createQuery(User.class);
        Root<User> root = selectQuery.from(User.class);

        ParameterExpression<String> paramUsername = builder.parameter(String.class);
        selectQuery.select(root).where(builder.equal(root.get("username"), paramUsername));

        TypedQuery<User> query = JPAHelper.getEntityManager().createQuery(selectQuery);
        query.setParameter(paramUsername, username);

        try{
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }
}
