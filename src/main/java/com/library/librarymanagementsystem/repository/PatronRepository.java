package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Book;
import com.library.librarymanagementsystem.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PatronRepository extends JpaRepository<Patron, UUID> {
    @Query("SELECT p FROM Patron p WHERE p.firstName LIKE %?1%" + " OR p.lastName LIKE %?1%" + " OR p.email LIKE %?1%")
    List<Patron> search(String keyword);
}
