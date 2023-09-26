package com.library.librarymanagementsystem.service;
import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Book;
import com.library.librarymanagementsystem.entity.Genre;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BookService {
    List<Book> getAllBooks();
    List<Book> getAllBooksSortedBy(String keyword);

    List<Book> searchBooks(String keyword);

    Book getBookById(UUID id);

    void saveBook(Book book);

    void updateBook(Book book);

    void deleteBook(UUID id);

    List<Book> getAllAvailable();

    void loanBook(Book book);

    void receiveBook(Book book);

    List<Integer> getNumberOfBooksByGenre(List<String> genreNames);

    Page<Book> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
