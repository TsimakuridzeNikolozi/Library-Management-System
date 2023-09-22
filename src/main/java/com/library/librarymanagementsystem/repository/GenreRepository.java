package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {

}
