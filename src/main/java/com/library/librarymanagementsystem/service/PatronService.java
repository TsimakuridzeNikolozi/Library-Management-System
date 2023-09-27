package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Patron;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface PatronService {
    List<Patron> getAllPatrons();

    List<Patron> searchPatrons(String keyword);

    Patron getPatronById(UUID id);

    void savePatron(Patron patron);

    void updatePatron(Patron patron);

    void deletePatron(UUID id);

    Page<Patron> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Page<Patron> findPaginatedPlusSearch(int pageNo, int pageSize, String sortField, String sortDirection, String searchKeyword);
}
