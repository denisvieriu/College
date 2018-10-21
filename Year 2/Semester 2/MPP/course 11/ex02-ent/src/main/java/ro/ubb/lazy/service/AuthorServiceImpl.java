package ro.ubb.lazy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.lazy.model.Author;
import ro.ubb.lazy.repository.AuthorRepository;

import java.util.List;

/**
 * Created by radu.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
//        List<Author> all = authorRepository.findAll();
//        System.out.println("findAll");
//        all.forEach(a -> {
//            System.out.print("books ");
//            int n = a.getBooks().size();
//        });

//        List<Author> all = authorRepository.findAllWithBooks();

        List<Author> all = authorRepository.findAllWithBooksAndPublisher();

        return all;
    }
}
