package ru.maltseva.jdbcspringexample.repository;

import ru.maltseva.jdbcspringexample.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAllBooks();
    Book getBookById(Long id);
}
