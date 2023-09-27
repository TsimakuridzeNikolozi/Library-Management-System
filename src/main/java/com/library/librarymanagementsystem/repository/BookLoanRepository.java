package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.BookLoan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface BookLoanRepository extends JpaRepository<BookLoan, UUID> {
    @Query(value = "SELECT b FROM BookLoan b WHERE b.patron.firstName LIKE %:keyword%" +
            " OR b.patron.lastName LIKE %:keyword% OR b.book.title LIKE %:keyword%",
            countQuery = "SELECT COUNT(b) FROM BookLoan b WHERE b.patron.firstName LIKE %:keyword%" +
                    " OR b.patron.lastName LIKE %:keyword% OR b.book.title LIKE %:keyword%")
    Page<BookLoan> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
