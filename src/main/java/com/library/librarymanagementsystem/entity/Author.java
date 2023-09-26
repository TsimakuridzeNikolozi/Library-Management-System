package com.library.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="author")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuperBuilder
public class Author extends Person {
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> books = new ArrayList<>();
}
