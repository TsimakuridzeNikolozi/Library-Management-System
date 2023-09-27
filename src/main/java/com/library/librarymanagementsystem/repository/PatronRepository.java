package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Book;
import com.library.librarymanagementsystem.entity.Patron;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PatronRepository extends JpaRepository<Patron, UUID> {
    @Query("SELECT p FROM Patron p WHERE p.firstName LIKE %?1%" + " OR p.lastName LIKE %?1%" + " OR p.email LIKE %?1%")
    List<Patron> search(String keyword);

    @Query(value = "SELECT p FROM Patron p WHERE p.firstName LIKE %:keyword%" + " OR p.lastName LIKE %:keyword% OR p.email LIKE %:keyword%",
            countQuery = "SELECT COUNT(p) FROM Patron p WHERE p.firstName LIKE %:keyword%" + " OR p.lastName LIKE %:keyword% OR p.email LIKE %:keyword%")
    Page<Patron> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
