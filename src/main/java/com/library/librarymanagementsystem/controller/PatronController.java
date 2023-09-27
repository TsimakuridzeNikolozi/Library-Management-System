package com.library.librarymanagementsystem.controller;

import com.library.librarymanagementsystem.entity.Patron;
import com.library.librarymanagementsystem.service.PatronService;
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
public class PatronController {
    private static final int DEFAULT_PAGE = 1;
    private static final String DEFAULT_SORT_FIELD = "firstName";
    private static final String DEFAULT_SORT_DIR = "asc";
    private static final String DEFAULT_SEARCH_KEYWORD = "";
    private static final int DEFAULT_PAGE_SIZE = 5;

    private final PatronService patronService;

    PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @RequestMapping("/patrons")
    public String searchAndPagination(@RequestParam(name = "pageNo", required = false) String pageNo,
                                      @RequestParam(name = "sortField", required = false) String sortField,
                                      @RequestParam(name = "sortDir", required = false) String sortDir,
                                      @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                                      Model model) {

        if (pageNo == null || pageNo.isEmpty()) pageNo = String.valueOf(DEFAULT_PAGE);
        if (sortField == null || sortField.isEmpty()) sortField = DEFAULT_SORT_FIELD;
        if (sortDir == null || sortDir.isEmpty()) sortDir = DEFAULT_SORT_DIR;
        if (searchKeyword == null || searchKeyword.isEmpty()) searchKeyword = DEFAULT_SEARCH_KEYWORD;

        Page<Patron> page = patronService.findPaginatedPlusSearch(Integer.parseInt(pageNo), DEFAULT_PAGE_SIZE, sortField, sortDir, searchKeyword);
        List<Patron> listPatrons = page.getContent();

        model.addAttribute("currentPage", Integer.parseInt(pageNo));
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("searchKeyword", searchKeyword);

        model.addAttribute("patrons", listPatrons);
        return "list-patrons";
    }


    @GetMapping("/add-patron")
    public String addPatron(Patron patron, Model model) {
        model.addAttribute("patron", patron);
        return "add-patron";
    }

    @RequestMapping("/save-patron")
    public String savePatron(Patron patron, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("patron", patron);
            return "add-patron";
        }

        patronService.savePatron(patron);
        model.addAttribute("patrons", patronService.getAllPatrons());
        return "redirect:/patrons";
    }

    @GetMapping("/edit-patron/{id}")
    public String editPatron(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("patron", patronService.getPatronById(id));
        return "edit-patron";
    }

    @RequestMapping("/update-patron/{id}")
    public String updatePatron(Model model, @PathVariable("id") UUID id, Patron patron, BindingResult result) {
        if (result.hasErrors()) {
            patron.setId(id);
            model.addAttribute("patron", patron);
            return "edit-patron";
        }

        patronService.updatePatron(patron);
        model.addAttribute("patrons", patronService.getAllPatrons());
        return "redirect:/patrons";
    }

    @RequestMapping("/delete-patron/{id}")
    public String deletePatron(Model model, @PathVariable("id") UUID id) {
        patronService.deletePatron(id);
        model.addAttribute("patrons", patronService.getAllPatrons());
        return "redirect:/patrons";
    }
}
