package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.BookLoan;
import com.library.librarymanagementsystem.entity.Genre;
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
public class GenreController {
    private static final int DEFAULT_PAGE = 1;
    private static final String DEFAULT_SORT_FIELD = "name";
    private static final String DEFAULT_SORT_DIR = "asc";
    private static final String DEFAULT_SEARCH_KEYWORD = "";

    private final GenreService genreService;
    private final BookService bookService;
    GenreController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @RequestMapping("/genres")
    public String getAllGenres(Model model) {
        return findPaginated(DEFAULT_PAGE, DEFAULT_SORT_FIELD, DEFAULT_SORT_DIR, model);
    }

    @GetMapping("/add-genre")
    public String addGenre(Genre genre, Model model) {
        model.addAttribute("genre", genre);
        model.addAttribute("allBooks", bookService.getAllBooks());
        return "add-genre";
    }

    @RequestMapping("/save-genre")
    public String saveGenre(Genre genre, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("genre", genre);
            model.addAttribute("allBooks", bookService.getAllBooks());
            return "add-genre";
        }

        genreService.saveGenre(genre);
        model.addAttribute("genres", genreService.getAllGenres());
        return "redirect:/genres";
    }

    @GetMapping("/edit-genre/{id}")
    public String editGenre(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("genre", genreService.getGenreById(id));
        model.addAttribute("allBooks", bookService.getAllBooks());
        return "edit-genre";
    }

    @RequestMapping("/update-genre/{id}")
    public String updateGenre(Model model, @PathVariable("id") UUID id, Genre genre, BindingResult result) {
        if (result.hasErrors()) {
            genre.setId(id);
            model.addAttribute("genre", genre);
            model.addAttribute("allBooks", bookService.getAllBooks());
            return "edit-genre";
        }

        genreService.updateGenre(genre);
        model.addAttribute("genres", genreService.getAllGenres());
        return "redirect:/genres";
    }

    @RequestMapping("/delete-genre/{id}")
    public String deleteGenre(Model model, @PathVariable("id") UUID id) {
        genreService.deleteGenre(id);
        model.addAttribute("genres", genreService.getAllGenres());
        return "redirect:/genres";
    }

    @GetMapping("/genres/{pageNo}")
    public String findPaginated(@PathVariable(name = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page <Genre> page = genreService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List <Genre> listGenres = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("genres", listGenres);
        return "list-genres";
    }
}
