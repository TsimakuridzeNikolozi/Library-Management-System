package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.entity.BookLoan;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BookLoanService {
    List<BookLoan> getAllBookLoans();

    BookLoan getBookLoanById(UUID id);

    void saveBookLoan(BookLoan bookLoan);

    void updateBookLoan(BookLoan bookLoan);

    void deleteBookLoan(UUID id);

    List<BookLoan> getOverdueBookLoans();

    List<BookLoan> getAllBookLoansSortedByLoanDateDesc();

    List<Integer> getNumOfBookLoansForPastDays(int numberOfDays);

    Page<BookLoan> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
