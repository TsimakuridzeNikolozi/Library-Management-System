package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    @Query("SELECT g FROM Genre g WHERE g.name LIKE %?1%")
    List<Genre> search(String keyword);
}
