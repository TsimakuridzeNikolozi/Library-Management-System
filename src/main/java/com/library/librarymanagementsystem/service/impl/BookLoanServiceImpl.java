package com.library.librarymanagementsystem.service.impl;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.BookLoan;
import com.library.librarymanagementsystem.repository.BookLoanRepository;
import com.library.librarymanagementsystem.service.BookLoanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BookLoanServiceImpl implements BookLoanService {

    private final BookLoanRepository bookLoanRepository;

    public BookLoanServiceImpl(BookLoanRepository bookLoanRepository) {
        this.bookLoanRepository = bookLoanRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BookLoan> getAllBookLoans() {
        return bookLoanRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public BookLoan getBookLoanById(UUID id) {
        return bookLoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookLoan with ID " + id + " couldn't be found"));
    }

    @Override
    public void saveBookLoan(BookLoan bookLoan) {
        bookLoanRepository.save(bookLoan);
    }

    @Override
    public void updateBookLoan(BookLoan bookLoan) {
        bookLoanRepository.save(bookLoan);
    }

    @Override
    public void deleteBookLoan(UUID id) {
        var bookLoan = bookLoanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BookLoan with ID " + id + " couldn't be found"));

        bookLoanRepository.deleteById(id);
    }

    @Override
    public List<BookLoan> getOverdueBookLoans() {
        return bookLoanRepository.findAll().stream()
                .filter(bookLoan -> bookLoan.getDueDate().before(Date.valueOf(LocalDate.now()))).toList();
    }

    @Override
    public List<BookLoan> getAllBookLoansSortedByLoanDateDesc() {
        return bookLoanRepository.findAll(Sort.by(Sort.Direction.DESC, "loanDate"));
    }

    @Override
    public List<Integer> getNumOfBookLoansForPastDays(int numberOfDays) {
        List<BookLoan> bookLoans = bookLoanRepository.findAll().stream()
                .filter(bookLoan -> bookLoan.getLoanDate().after(Date.valueOf(LocalDate.now().minusDays(numberOfDays)))).toList();

        List<Integer> numOfBookLoansPerDays = new ArrayList<>();
        for(int i = 0; i < numberOfDays; i++) {
            numOfBookLoansPerDays.add(0);
        }

        for(BookLoan bookLoan: bookLoans) {
            int numDaysBetween = Math.abs(Math.toIntExact(ChronoUnit.DAYS.between(LocalDate.now(), bookLoan.getLoanDate().toLocalDate())));
            int index = numberOfDays - numDaysBetween - 1;
            numOfBookLoansPerDays.set(index, numOfBookLoansPerDays.get(index) + 1);
        }

        return numOfBookLoansPerDays;
    }

    @Override
    public Page<BookLoan> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return bookLoanRepository.findAll(pageable);
    }

    @Override
    public Page<BookLoan> findPaginatedPlusSearch(int pageNo, int pageSize, String sortField, String sortDirection, String searchKeyword) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        if (searchKeyword == null || searchKeyword.isEmpty())
            return bookLoanRepository.findAll(pageable);
        return bookLoanRepository.findAllByKeyword(searchKeyword, pageable);
    }
}
