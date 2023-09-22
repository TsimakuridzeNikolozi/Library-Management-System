package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatronRepository extends JpaRepository<Patron, UUID> {
}
