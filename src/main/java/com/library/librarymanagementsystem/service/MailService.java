package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.entity.BookLoan;

public interface MailService {
    void sendMailFor(BookLoan bookLoan);
}
