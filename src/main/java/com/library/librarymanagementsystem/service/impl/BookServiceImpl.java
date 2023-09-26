package com.library.librarymanagementsystem.service.impl;
import com.library.librarymanagementsystem.entity.Author;
import com.library.librarymanagementsystem.entity.Book;
import com.library.librarymanagementsystem.entity.Genre;
import com.library.librarymanagementsystem.repository.BookRepository;
import com.library.librarymanagementsystem.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Book> getAllBooksSortedBy(String keyword) {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, keyword));
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Book> searchBooks(String keyword) {
        if (keyword == null)
            return bookRepository.findAll();
        return bookRepository.search(keyword);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Book getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with ID " + id + " couldn't be found"));
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(UUID id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book with ID " + id + " couldn't be found"));

        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> getAllAvailable() {
        return bookRepository.findAll().stream().filter(book -> book.getQuantity() > 0).toList();
    }

    @Override
    public void loanBook(Book book) {
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
    }

    @Override
    public void receiveBook(Book book) {
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
    }

    @Override
    public List<Integer> getNumberOfBooksByGenre(List<String> genreNames) {
        List<Book> allBooks = bookRepository.findAll();
        Map<String, Integer> numBooksByGenre = new HashMap<>();

        for(Book book: allBooks) {
            for(Genre genre: book.getGenres()) {
                String genreName = genre.getName();
                numBooksByGenre.put(genreName, numBooksByGenre.getOrDefault(genreName, 0) + 1);
            }
        }

        List<Integer> numBooksByGenreOrdered = new ArrayList<>();
        for(String genreName: genreNames) {
            numBooksByGenreOrdered.add(numBooksByGenre.getOrDefault(genreName, 0));
        }
        return numBooksByGenreOrdered;
    }

    @Override
    public Page<Book> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return bookRepository.findAll(pageable);
    }
}
