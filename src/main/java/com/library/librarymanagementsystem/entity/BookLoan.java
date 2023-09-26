package com.library.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Entity
@Table(name="book_loan")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@SuperBuilder
public class BookLoan extends BaseEntity {
    @Column(name="loan_date", nullable = false)
    private Date loanDate;

    @Column(name="due_date", nullable = false)
    private Date dueDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Patron patron;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Book book;
}
