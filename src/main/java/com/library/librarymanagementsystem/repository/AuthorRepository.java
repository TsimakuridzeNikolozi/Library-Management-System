package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    @Query("SELECT a FROM Author a WHERE a.firstName LIKE %?1%" + " OR a.lastName LIKE %?1%")
    List<Author> search(String keyword);

    @Query(value = "SELECT a FROM Author a WHERE a.firstName LIKE %:keyword%" + " OR a.lastName LIKE %:keyword%",
            countQuery = "SELECT COUNT(a) FROM Author a WHERE a.firstName LIKE %:keyword%" + " OR a.lastName LIKE %:keyword%")
    Page<Author> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
