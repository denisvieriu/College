package ro.ubb.lazy.repository;

import ro.ubb.lazy.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by radu.
 */
public class AuthorRepositoryImpl extends CustomRepositorySupport implements AuthorRepositoryCustom {
    @Override
    public List<Author> findAllWithBooksAndPublisherJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct a from Author a " +
                "left join fetch a.books b " +
                "left join fetch b.publisher");
        List<Author> authors = query.getResultList();

        return authors;
    }
}
