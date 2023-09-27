package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.entity.BookLoan;
import com.library.librarymanagementsystem.service.BookLoanService;
import com.library.librarymanagementsystem.service.BookService;
import com.library.librarymanagementsystem.service.MailService;
import com.library.librarymanagementsystem.service.PatronService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class BookLoanController {
    private static final int DEFAULT_PAGE = 1;
    private static final String DEFAULT_SORT_FIELD = "loanDate";
    private static final String DEFAULT_SORT_DIR = "asc";
    private static final String DEFAULT_SEARCH_KEYWORD = "";
    private static final int DEFAULT_PAGE_SIZE = 5;

    private final BookLoanService bookLoanService;
    private final PatronService patronService;
    private final BookService bookService;

    private final MailService mailService;

    BookLoanController(BookLoanService bookLoanService,
                       PatronService patronService,
                       BookService bookService,
                       MailService mailService) {
        this.bookLoanService = bookLoanService;
        this.patronService = patronService;
        this.bookService = bookService;
        this.mailService = mailService;
    }

    @RequestMapping("/book-loans")
    public String searchAndPagination(@RequestParam(name = "pageNo", required = false) String pageNo,
                                      @RequestParam(name = "sortField", required = false) String sortField,
                                      @RequestParam(name = "sortDir", required = false) String sortDir,
                                      @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                                      Model model) {

        if (pageNo == null || pageNo.isEmpty()) pageNo = String.valueOf(DEFAULT_PAGE);
        if (sortField == null || sortField.isEmpty()) sortField = DEFAULT_SORT_FIELD;
        if (sortDir == null || sortDir.isEmpty()) sortDir = DEFAULT_SORT_DIR;
        if (searchKeyword == null || searchKeyword.isEmpty()) searchKeyword = DEFAULT_SEARCH_KEYWORD;

        Page<BookLoan> page = bookLoanService.findPaginatedPlusSearch(Integer.parseInt(pageNo), DEFAULT_PAGE_SIZE, sortField, sortDir, searchKeyword);
        List<BookLoan> listBookLoans = page.getContent();

        model.addAttribute("currentPage", Integer.parseInt(pageNo));
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("searchKeyword", searchKeyword);

        model.addAttribute("bookLoans", listBookLoans);
        return "list-book-loans";
    }


    @GetMapping("/add-book-loan")
    public String addBookLoan(BookLoan bookLoan, Model model) {
        model.addAttribute("bookLoan", bookLoan);
        model.addAttribute("allPatrons", patronService.getAllPatrons());
        model.addAttribute("allBooks", bookService.getAllAvailable());
        return "add-book-loan";
    }

    @RequestMapping("/save-book-loan")
    public String saveBookLoan(BookLoan bookLoan, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("bookLoan", bookLoan);
            model.addAttribute("allPatrons", patronService.getAllPatrons());
            model.addAttribute("allBooks", bookService.getAllAvailable());
            return "add-book-loan";
        }

        bookLoan.setLoanDate(Date.valueOf(LocalDate.now()));
        bookLoan.setDueDate(Date.valueOf(LocalDate.now().plusDays(bookLoan.getPatron().getMembership().getNumDays())));
        bookService.loanBook(bookLoan.getBook());
        bookLoanService.saveBookLoan(bookLoan);
        model.addAttribute("bookLoans", bookLoanService.getAllBookLoans());
        return "redirect:/book-loans";
    }

    @RequestMapping("/send-notification/{id}")
    public String sendNotificationToPatron(Model model, @PathVariable("id") UUID id) {
        String result = "Success";
        try {
            mailService.sendMailFor(bookLoanService.getBookLoanById(id));
        } catch (Exception e) {
            result = e.getMessage();
        }
        model.addAttribute("emailError", result);
        model.addAttribute("bookLoans", bookLoanService.getAllBookLoans());
        return "redirect:/book-loans";
    }

    @GetMapping("/edit-book-loan/{id}")
    public String editBookLoan(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("bookLoan", bookLoanService.getBookLoanById(id));
        model.addAttribute("allPatrons", patronService.getAllPatrons());
        model.addAttribute("allBooks", bookService.getAllAvailable());
        return "edit-book-loan";
    }

    @RequestMapping("/update-book-loan/{id}")
    public String updateBookLoan(Model model, @PathVariable("id") UUID id, BookLoan bookLoan, BindingResult result) {
        if (result.hasErrors()) {
            bookLoan.setId(id);
            model.addAttribute("bookLoan", bookLoan);
            model.addAttribute("allPatrons", patronService.getAllPatrons());
            model.addAttribute("allBooks", bookService.getAllAvailable());
            return "edit-book-loan";
        }

        bookLoan.setLoanDate(Date.valueOf(LocalDate.now()));
        bookLoan.setDueDate(Date.valueOf(LocalDate.now().plusDays(bookLoan.getPatron().getMembership().getNumDays())));
        bookLoanService.updateBookLoan(bookLoan);
        model.addAttribute("bookLoans", bookLoanService.getAllBookLoans());
        return "redirect:/book-loans";
    }

    @RequestMapping("/delete-book-loan/{id}")
    public String deleteBookLoan(Model model, @PathVariable("id") UUID id) {
        bookService.receiveBook(bookLoanService.getBookLoanById(id).getBook());
        bookLoanService.deleteBookLoan(id);
        model.addAttribute("bookLoans", bookLoanService.getAllBookLoans());
        return "redirect:/book-loans";
    }

    @RequestMapping("/generate-report")
    public String generateReport(Model model) {
        model.addAttribute("overdueBookLoans", bookLoanService.getOverdueBookLoans());
        model.addAttribute("transactionHistory", bookLoanService.getAllBookLoansSortedByLoanDateDesc());
        return "report";
    }
}
