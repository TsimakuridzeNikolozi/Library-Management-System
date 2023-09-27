package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("SELECT b FROM Book b WHERE b.title LIKE %?1%" + " OR b.isbn LIKE %?1%" + " OR b.description LIKE %?1%")
    List<Book> search(String keyword);

    @Query(value = "SELECT b FROM Book b WHERE b.title LIKE %:keyword%" + " OR b.description LIKE %:keyword% OR b.isbn LIKE %:keyword%",
            countQuery = "SELECT COUNT(b) FROM Book b WHERE b.title LIKE %:keyword%" + " OR b.description LIKE %:keyword% OR b.isbn LIKE %:keyword%")
    Page<Book> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
