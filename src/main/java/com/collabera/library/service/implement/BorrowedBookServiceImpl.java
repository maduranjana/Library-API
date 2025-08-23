package com.collabera.library.service.implement;

import com.collabera.library.dto.BorrowBookRequest;
import com.collabera.library.dto.LibraryResponse;
import com.collabera.library.exception.BusinessValidationException;
import com.collabera.library.model.Book;
import com.collabera.library.model.BorrowedBookLog;
import com.collabera.library.model.Borrower;
import com.collabera.library.repository.BookRepository;
import com.collabera.library.repository.BorrowedBookLogRepository;
import com.collabera.library.repository.BorrowerRepository;
import com.collabera.library.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {

    @Autowired
    private BorrowedBookLogRepository borrowedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Override
    @Transactional
    public LibraryResponse borrowBook(BorrowBookRequest request) {


        // Check if the book is already borrowed
        Optional<BorrowedBookLog> borrowedBook = borrowedBookRepository.
                findByBookidAndBorrowerId(request.getBookID(), request.getBorrowerId());
        if (borrowedBook.isPresent()) {
            throw new BusinessValidationException("This book is already borrowed.");
        }

        // Find the borrower
        Borrower borrower = borrowerRepository.findById(request.getBorrowerId())
                .orElseThrow(() -> new IllegalArgumentException("Borrower not found"));

        // Find the book
        Book book = bookRepository.findById(request.getBookID())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        BorrowedBookLog bookLog = new BorrowedBookLog();

        bookLog.setBook(book);
        bookLog.setBorrower(borrower);
        bookLog.setBorrowDate(LocalDate.now());

        borrowedBookRepository.save(bookLog);

        book.setAvailability(false);

        bookRepository.save(book);

        LibraryResponse response = new LibraryResponse();

        response.setBookTitle(book.getTitle());
        response.setBorrowerName(borrower.getName());
        response.setLocalDate(bookLog.getBorrowDate());
        response.setMessage(book.getTitle() + " is borrowed to " + borrower.getName() + ", Success");

        return response;
    }

    @Override
    @Transactional
    public LibraryResponse returnBook(BorrowBookRequest request) {

        // Check if the book is already borrowed
        Optional<BorrowedBookLog> borrowedBook = borrowedBookRepository.
                findByBookidAndBorrowerId(request.getBookID(), request.getBorrowerId());

        if (borrowedBook.isPresent()) {

            BorrowedBookLog bookLog = borrowedBook.get();
            bookLog.setReturned(true);
            bookLog.setReturnDate(LocalDate.now());

            borrowedBookRepository.save(bookLog);

            // Find the book
            Book book = bookRepository.findById(request.getBookID())
                    .orElseThrow(() -> new IllegalArgumentException("Book not found"));

            book.setAvailability(true);

            bookRepository.save(book);

            LibraryResponse response = new LibraryResponse();

            response.setBookTitle(book.getTitle());
            response.setBorrowerName(borrowedBook.get().getBorrower().getName());
            response.setLocalDate(bookLog.getBorrowDate());
            response.setMessage(borrowedBook.get().getBorrower().getName() + " is " + book.getTitle() + " returned to Library, Success");

            return response;

        } else {
            throw new BusinessValidationException("This book is Not borrowed by borrower");

        }

    }
}
