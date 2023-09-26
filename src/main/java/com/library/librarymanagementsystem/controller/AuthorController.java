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

@Controller
public class AuthorController {
    private static final int DEFAULT_PAGE = 1;
    private static final String DEFAULT_SORT_FIELD = "firstName";
    private static final String DEFAULT_SORT_DIR = "asc";
    private static final String DEFAULT_SEARCH_KEYWORD = "";

    private final AuthorService authorService;
    private final BookService bookService;
    AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @RequestMapping("/authors")
    public String getAllAuthors(Model model) {
        return findPaginated(DEFAULT_PAGE, DEFAULT_SORT_FIELD, DEFAULT_SORT_DIR, model);
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

        authorService.saveAuthor(author);
        model.addAttribute("authors", authorService.getAllAuthors());
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

    @GetMapping("/authors/{pageNo}")
    public String findPaginated(@PathVariable(name = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page <Author> page = authorService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List <Author> listAuthors = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("authors", listAuthors);
        return "list-authors";
    }
}
