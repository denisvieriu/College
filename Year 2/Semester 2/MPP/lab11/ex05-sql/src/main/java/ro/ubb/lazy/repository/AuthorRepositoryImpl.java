package ro.ubb.lazy.repository;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public List<Author> findAllWithBooksAndPublisherCriteriaAPI() {
//        EntityManager entityManager = getEntityManager();
//
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Author> query = criteriaBuilder.createQuery(Author.class);
//        query.distinct(Boolean.TRUE);
//        Root<Author> root = query.from(Author.class);
//        Fetch<Author, Book> authorBookFetch = root.fetch(Author_.books);
//        authorBookFetch.fetch(Book_.publisher);
//
//        List<Author> authors = entityManager.createQuery(query).getResultList();

        return null;
    }

    @Override
    @Transactional
    public List<Author> findAllWithBooksAndPublisherSQL() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {a.*},{b.*},{p.*} " +
                "from author a " +
                "left join book b on a.id=b.author_id " +
                "left join publisher p on b.publisher_id=p.id ")
                .addEntity("a",Author.class)
                .addJoin("b", "a.books")
                .addJoin("p", "b.publisher")
                .addEntity("a",Author.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Author> authors = query.getResultList();


        return authors;
    }
}
