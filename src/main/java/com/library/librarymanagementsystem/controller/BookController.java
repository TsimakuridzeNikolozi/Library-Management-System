package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Book;
import com.library.librarymanagementsystem.service.AuthorService;
import com.library.librarymanagementsystem.service.BookService;
import com.library.librarymanagementsystem.service.GenreService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
public class BookController {
    private static final int DEFAULT_PAGE = 1;
    private static final String DEFAULT_SORT_FIELD = "title";
    private static final String DEFAULT_SORT_DIR = "asc";
    private static final String DEFAULT_SEARCH_KEYWORD = "";
    private static final int DEFAULT_PAGE_SIZE = 5;

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @RequestMapping("/books")
    public String searchAndPagination(@RequestParam(name = "pageNo", required = false) String pageNo,
                                      @RequestParam(name = "sortField", required = false) String sortField,
                                      @RequestParam(name = "sortDir", required = false) String sortDir,
                                      @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                                      Model model) {

        if (pageNo == null || pageNo.isEmpty()) pageNo = String.valueOf(DEFAULT_PAGE);
        if (sortField == null || sortField.isEmpty()) sortField = DEFAULT_SORT_FIELD;
        if (sortDir == null || sortDir.isEmpty()) sortDir = DEFAULT_SORT_DIR;
        if (searchKeyword == null || searchKeyword.isEmpty()) searchKeyword = DEFAULT_SEARCH_KEYWORD;

        Page <Book> page = bookService.findPaginatedPlusSearch(Integer.parseInt(pageNo), DEFAULT_PAGE_SIZE, sortField, sortDir, searchKeyword);
        List <Book> listBooks = page.getContent();

        model.addAttribute("currentPage", Integer.parseInt(pageNo));
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("searchKeyword", searchKeyword);

        model.addAttribute("books", listBooks);
        return "list-books";
    }

    @GetMapping("/add-book")
    public String addBook(Book book, Model model) {
        model.addAttribute("book", book);
        model.addAttribute("allAuthors", authorService.getAllAuthors());
        model.addAttribute("allGenres", genreService.getAllGenres());
        return "add-book";
    }

    @RequestMapping("/save-book")
    public String saveBook(Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("allAuthors", authorService.getAllAuthors());
            model.addAttribute("allGenres", genreService.getAllGenres());
            return "add-book";
        }

        bookService.saveBook(book);
        model.addAttribute("books", bookService.getAllBooks());
        return "redirect:/books";
    }

    @GetMapping("/edit-book/{id}")
    public String editBook(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("book", bookService.getBookById(id));
        model.addAttribute("allAuthors", authorService.getAllAuthors());
        model.addAttribute("allGenres", genreService.getAllGenres());
        return "edit-book";
    }

    @RequestMapping("/update-book/{id}")
    public String updateBook(Model model, @PathVariable("id") UUID id, Book book, BindingResult result) {
        if (result.hasErrors()) {
            book.setId(id);
            model.addAttribute("book", book);
            model.addAttribute("allAuthors", authorService.getAllAuthors());
            model.addAttribute("allGenres", genreService.getAllGenres());
            return "edit-book";
        }

        bookService.updateBook(book);
        model.addAttribute("books", bookService.getAllBooks());
        return "redirect:/books";
    }

    @RequestMapping("/delete-book/{id}")
    public String deleteBook(Model model, @PathVariable("id") UUID id) {
        bookService.deleteBook(id);
        model.addAttribute("books", bookService.getAllBooks());
        return "redirect:/books";
    }
}
