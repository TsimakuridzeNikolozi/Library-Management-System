package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%" + " OR b.isbn LIKE %?1%" + " OR b.description LIKE %?1%")
    List<Book> search(String keyword);
}
