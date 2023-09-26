package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.service.BookLoanService;
import com.library.librarymanagementsystem.service.BookService;
import com.library.librarymanagementsystem.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StatisticsController {
    private final BookService bookService;
    private final BookLoanService bookLoanService;
    private final GenreService genreService;

    StatisticsController(BookService bookService, BookLoanService bookLoanService, GenreService genreService) {
        this.bookService = bookService;
        this.bookLoanService = bookLoanService;
        this.genreService = genreService;
    }

    @RequestMapping("/statistics")
    public String getStatistics(Model model) {
        model.addAttribute("genres", genreService.getAllGenreNames());
        model.addAttribute("numberOfBooksByGenre", bookService.getNumberOfBooksByGenre(genreService.getAllGenreNames()));
        model.addAttribute("bookLoans", bookLoanService.getNumOfBookLoansForPastDays(10));
        return "statistics";
    }
}
