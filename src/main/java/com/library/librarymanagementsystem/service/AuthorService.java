package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface AuthorService {
    List<Author> getAllAuthors();

    List<Author> searchAuthors(String keyword);

    Author getAuthorById(UUID id);

    void saveAuthor(Author author);

    void updateAuthor(Author author);

    void deleteAuthor(UUID id);

    Page<Author> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
