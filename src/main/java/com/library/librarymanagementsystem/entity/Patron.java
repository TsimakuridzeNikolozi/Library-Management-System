package com.library.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name="patron")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuperBuilder
public class Patron extends Person {
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "book_patron",
            joinColumns = @JoinColumn(name = "patron_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> borrowedBooks;
}
