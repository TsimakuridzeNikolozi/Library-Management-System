package com.library.librarymanagementsystem.service.impl;

import com.library.librarymanagementsystem.entity.Genre;
import com.library.librarymanagementsystem.repository.GenreRepository;
import com.library.librarymanagementsystem.service.GenreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Genre> searchGenres(String keyword) {
        if (keyword == null)
            return genreRepository.findAll();
        return genreRepository.search(keyword);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Genre getGenreById(UUID id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre with ID " + id + " couldn't be found"));
    }

    @Override
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void updateGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(UUID id) {
        var genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre with ID " + id + " couldn't be found"));

        genreRepository.deleteById(id);
    }

    @Override
    public List<String> getAllGenreNames() {
        return genreRepository.findAll().stream().map(Genre::getName).toList();
    }

    @Override
    public Page<Genre> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return genreRepository.findAll(pageable);
    }

    @Override
    public Page<Genre> findPaginatedPlusSearch(int pageNo, int pageSize, String sortField, String sortDirection, String searchKeyword) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        if (searchKeyword == null || searchKeyword.isEmpty())
            return genreRepository.findAll(pageable);
        return genreRepository.findAllByKeyword(searchKeyword, pageable);
    }
}
