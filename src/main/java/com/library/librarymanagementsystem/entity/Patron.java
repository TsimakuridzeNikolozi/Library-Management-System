package com.library.librarymanagementsystem.entity;

import com.library.librarymanagementsystem.enumeration.Membership;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "membership", length = 50, nullable = false)
    private Membership membership;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "patron")
    private List<BookLoan> bookLoans;
}
