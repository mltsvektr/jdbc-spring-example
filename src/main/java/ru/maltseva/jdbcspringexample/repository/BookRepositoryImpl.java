package ru.maltseva.jdbcspringexample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.maltseva.jdbcspringexample.model.Book;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @Autowired
    private DataSource dataSource;

    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> result = new ArrayList<>();
        String SQL_findAllBooks = "Select * from books;";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_findAllBooks)) {
            while (resultSet.next()) {
                Book book = convertRowToBook(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    public Book getBookById(Long id){
        Book result;
        String SQL_findBookById = "Select * from books where id = " + id + ";";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_findBookById)) {
            resultSet.next();
            result = convertRowToBook(resultSet);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    private Book convertRowToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Book(id, name);
    }
}
