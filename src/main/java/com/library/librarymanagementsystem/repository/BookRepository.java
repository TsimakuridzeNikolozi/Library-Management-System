package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

}
