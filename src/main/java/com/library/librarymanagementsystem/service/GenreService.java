package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Book;
import com.library.librarymanagementsystem.entity.Genre;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface GenreService {
    List<Genre> getAllGenres();

    List<Genre> searchGenres(String keyword);

    Genre getGenreById(UUID id);

    void saveGenre(Genre genre);

    void updateGenre(Genre genre);

    void deleteGenre(UUID id);

    List<String> getAllGenreNames();

    Page<Genre> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Genre> findPaginatedPlusSearch(int pageNo, int pageSize, String sortField, String sortDirection, String searchKeyword);
}
