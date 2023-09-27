package com.library.librarymanagementsystem.repository;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    @Query("SELECT g FROM Genre g WHERE g.name LIKE %?1%")
    List<Genre> search(String keyword);

    @Query(value = "SELECT g FROM Genre g WHERE g.name LIKE %:keyword%",
            countQuery = "SELECT COUNT(g) FROM Genre g WHERE g.name LIKE %:keyword%")
    Page<Genre> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
