package ro.ubb.tst5.core.service;

import ro.ubb.tst5.core.model.Book;

import java.util.List;

public interface BookService {

    Book createBook(String title, Integer year, String authorName);

    List<Book> findAll();
}
