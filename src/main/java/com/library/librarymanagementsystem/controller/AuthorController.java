package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.service.AuthorService;
import com.library.librarymanagementsystem.service.BookService;
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
import java.util.logging.Logger;

@Controller
public class AuthorController {
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_PAGE_SIZE = 5;
    private static final String DEFAULT_SORT_FIELD = "firstName";
    private static final String DEFAULT_SORT_DIR = "asc";
    private static final String DEFAULT_SEARCH_KEYWORD = "";

    private final Logger logger;
    private final AuthorService authorService;
    private final BookService bookService;

    AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.logger = Logger.getLogger(getClass().getName());
    }

    @RequestMapping("/authors")
    public String searchAndPagination(@RequestParam(name = "pageNo", required = false) String pageNo,
                                      @RequestParam(name = "sortField", required = false) String sortField,
                                      @RequestParam(name = "sortDir", required = false) String sortDir,
                                      @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                                      Model model) {

        if (pageNo == null || pageNo.isEmpty()) pageNo = String.valueOf(DEFAULT_PAGE);
        if (sortField == null || sortField.isEmpty()) sortField = DEFAULT_SORT_FIELD;
        if (sortDir == null || sortDir.isEmpty()) sortDir = DEFAULT_SORT_DIR;
        if (searchKeyword == null || searchKeyword.isEmpty()) searchKeyword = DEFAULT_SEARCH_KEYWORD;

        Page<Author> page = authorService.findPaginatedPlusSearch(Integer.parseInt(pageNo), DEFAULT_PAGE_SIZE, sortField, sortDir, searchKeyword);
        List<Author> listAuthors = page.getContent();

        model.addAttribute("currentPage", Integer.parseInt(pageNo));
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("searchKeyword", searchKeyword);

        model.addAttribute("authors", listAuthors);
        return "list-authors";
    }

    @GetMapping("/add-author")
    public String addAuthor(Author author, Model model) {
        model.addAttribute("author", author);
        model.addAttribute("allBooks", bookService.getAllBooks());
        return "add-author";
    }

    @RequestMapping("/save-author")
    public String saveAuthor(Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("author", author);
            model.addAttribute("allBooks", bookService.getAllBooks());
            return "add-author";
        }

        try {
            authorService.saveAuthor(author);
            model.addAttribute("authors", authorService.getAllAuthors());
            logger.info("Successfully saved the author");
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return "redirect:/authors";
    }

    @GetMapping("/edit-author/{id}")
    public String editAuthor(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("author", authorService.getAuthorById(id));
        model.addAttribute("allBooks", bookService.getAllBooks());
        return "edit-author";
    }

    @RequestMapping("/update-author/{id}")
    public String updateAuthor(Model model, @PathVariable("id") UUID id, Author author, BindingResult result) {
        if (result.hasErrors()) {
            author.setId(id);
            model.addAttribute("author", author);
            model.addAttribute("allBooks", bookService.getAllBooks());
            return "edit-author";
        }

        authorService.updateAuthor(author);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "redirect:/authors";
    }

    @RequestMapping("/delete-author/{id}")
    public String deleteAuthor(Model model, @PathVariable("id") UUID id) {
        authorService.deleteAuthor(id);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "redirect:/authors";
    }
}
