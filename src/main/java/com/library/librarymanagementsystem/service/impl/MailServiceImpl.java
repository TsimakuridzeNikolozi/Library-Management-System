package com.library.librarymanagementsystem.service.impl;

import com.library.librarymanagementsystem.entity.BookLoan;
import com.library.librarymanagementsystem.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private static final String MESSAGE_SUBJECT = "Overdue loan for a book: ${title}";
    private static final String MESSAGE_BODY =
            """
                    Dear ${patron},

                    Your book, ${title} is overdue. Please return it immediately to avoid membership revocation.

                    Thank you,
                    """;

    private static final String SIGNATURE = "Library Management Team";

    private JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendMailFor(BookLoan bookLoan) {
        String bookTitle = bookLoan.getBook().getTitle();
        String patronName = bookLoan.getPatron().getFirstName() + " " + bookLoan.getPatron().getLastName();

        String to = bookLoan.getPatron().getEmail();
        String subject = MESSAGE_SUBJECT.replace("${title}", bookTitle);
        String body = MESSAGE_BODY
                .replace("${title}", bookTitle)
                .replace("${patron}", patronName) + SIGNATURE;

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
