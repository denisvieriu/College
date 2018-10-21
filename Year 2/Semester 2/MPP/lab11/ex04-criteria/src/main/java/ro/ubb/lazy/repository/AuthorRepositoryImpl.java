package ro.ubb.lazy.repository;

import ro.ubb.lazy.model.Author;
import ro.ubb.lazy.model.Author_;
import ro.ubb.lazy.model.Book;
import ro.ubb.lazy.model.Book_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Root;
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

    @Override
    public List<Author> findAllWithBooksAndPublisherCriteriaAPI() {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);
        query.distinct(Boolean.TRUE);
        Root<Author> root = query.from(Author.class);
        Fetch<Author, Book> authorBookFetch = root.fetch(Author_.books);
        authorBookFetch.fetch(Book_.publisher);

        List<Author> authors = entityManager.createQuery(query).getResultList();

        return authors;
    }
}
