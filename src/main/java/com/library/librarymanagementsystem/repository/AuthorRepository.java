package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
