package ro.ubb.crud.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ro.ubb.crud.model.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * Created by radu.
 */

@Repository
public class PersonRepositortImpl implements PersonRepository {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void save(Person newPerson) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        entityManager.persist(newPerson);

        entityTransaction.commit();

        entityManager.close();
    }

    @Override
    public Person findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

//        Person found = entityManager.find(Person.class, id);
        Person found2 = entityManager.getReference(Person.class, id);

//        System.out.println(found);
        System.out.println(found2);

        entityTransaction.commit();
        entityManager.close();

        System.out.println("after context");
//        System.out.println(found);
//        System.out.println(found2);

        return found2;
    }

    @Override
    public void update(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Person found = entityManager.find(Person.class, id);
        found.setName("Misu");

        entityTransaction.commit();
        entityManager.close();

    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Person toDelete = entityManager.getReference(Person.class, id);
        entityManager.remove(toDelete);

        entityTransaction.commit();
        entityManager.close();
    }

    @Override
    public void merge(Long id, Person person) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Person found = entityManager.find(Person.class, id);

        entityTransaction.commit();
        entityManager.close();

        found.setName(person.getName());

        System.out.println("merge detached");

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction2 = entityManager2.getTransaction();
        entityTransaction2.begin();

        entityManager2.merge(found);

        entityTransaction2.commit();
        entityManager2.close();

        System.out.println("merge new");
        EntityManager entityManager3 = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction3 = entityManager3.getTransaction();
        entityTransaction3.begin();

        entityManager3.merge(person);

        entityTransaction3.commit();
        entityManager3.close();

    }
}
