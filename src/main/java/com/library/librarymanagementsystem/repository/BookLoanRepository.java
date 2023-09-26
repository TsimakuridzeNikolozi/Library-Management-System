package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookLoanRepository extends JpaRepository<BookLoan, UUID> {
}
