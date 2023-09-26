package com.library.librarymanagementsystem.service.impl;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Patron;
import com.library.librarymanagementsystem.repository.PatronRepository;
import com.library.librarymanagementsystem.service.PatronService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Controller
public class PatronServiceImpl implements PatronService {
    private final PatronRepository patronRepository;

    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Patron> searchPatrons(String keyword) {
        if (keyword == null)
            return patronRepository.findAll();
        return patronRepository.search(keyword);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Patron getPatronById(UUID id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patron with ID " + id + " couldn't be found"));
    }

    @Override
    public void savePatron(Patron patron) {
        patronRepository.save(patron);
    }

    @Override
    public void updatePatron(Patron patron) {
        patronRepository.save(patron);
    }

    @Override
    public void deletePatron(UUID id) {
        var patron = patronRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patron with ID " + id + " couldn't be found"));

        patronRepository.deleteById(id);
    }

    @Override
    public Page<Patron> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return patronRepository.findAll(pageable);
    }
}
