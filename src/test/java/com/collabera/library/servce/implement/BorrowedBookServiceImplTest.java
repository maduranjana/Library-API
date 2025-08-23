package com.collabera.library.servce.implement;

import com.collabera.library.dto.BorrowBookRequest;
import com.collabera.library.dto.LibraryResponse;
import com.collabera.library.exception.BusinessValidationException;
import com.collabera.library.model.Book;
import com.collabera.library.model.BorrowedBookLog;
import com.collabera.library.model.Borrower;
import com.collabera.library.repository.BookRepository;
import com.collabera.library.repository.BorrowedBookLogRepository;
import com.collabera.library.repository.BorrowerRepository;
import com.collabera.library.service.implement.BorrowedBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BorrowedBookServiceImplTest {

    @Mock
    private BorrowedBookLogRepository borrowedBookRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowedBookServiceImpl borrowedBookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowBook_ShouldSaveAndReturnResponse() {
        BorrowBookRequest request = new BorrowBookRequest();
        request.setBookID(1L);
        request.setBorrowerId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAvailability(true);

        Borrower borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("John Doe");

        when(borrowedBookRepository.findByBookidAndBorrowerId(1L, 1L))
                .thenReturn(Optional.empty());
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        when(borrowedBookRepository.save(any(BorrowedBookLog.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        LibraryResponse response = borrowedBookService.borrowBook(request);

        assertNotNull(response);
        assertEquals("Clean Code", response.getBookTitle());
        assertEquals("John Doe", response.getBorrowerName());
        assertFalse(book.isAvailability());
        verify(borrowedBookRepository, times(1)).save(any(BorrowedBookLog.class));
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void borrowBook_AlreadyBorrowed_ShouldThrowException() {
        BorrowBookRequest request = new BorrowBookRequest();
        request.setBookID(1L);
        request.setBorrowerId(1L);

        BorrowedBookLog log = new BorrowedBookLog();
        when(borrowedBookRepository.findByBookidAndBorrowerId(1L, 1L))
                .thenReturn(Optional.of(log));

        BusinessValidationException exception = assertThrows(BusinessValidationException.class,
                () -> borrowedBookService.borrowBook(request));

        assertEquals("This book is already borrowed.", exception.getMessage());
    }

    @Test
    void returnBook_ShouldMarkReturnedAndUpdateBook() {
        BorrowBookRequest request = new BorrowBookRequest();
        request.setBookID(1L);
        request.setBorrowerId(1L);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");
        book.setAvailability(false);

        Borrower borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("John Doe");

        BorrowedBookLog log = new BorrowedBookLog();
        log.setBook(book);
        log.setBorrower(borrower);
        log.setBorrowDate(LocalDate.now());
        log.setReturned(false);

        when(borrowedBookRepository.findByBookidAndBorrowerId(1L, 1L))
                .thenReturn(Optional.of(log));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowedBookRepository.save(any(BorrowedBookLog.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        LibraryResponse response = borrowedBookService.returnBook(request);

        assertNotNull(response);
        assertTrue(log.isReturned());
        assertTrue(book.isAvailability());
        verify(borrowedBookRepository, times(1)).save(log);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void returnBook_NotBorrowed_ShouldThrowException() {
        BorrowBookRequest request = new BorrowBookRequest();
        request.setBookID(1L);
        request.setBorrowerId(1L);

        when(borrowedBookRepository.findByBookidAndBorrowerId(1L, 1L))
                .thenReturn(Optional.empty());

        BusinessValidationException exception = assertThrows(BusinessValidationException.class,
                () -> borrowedBookService.returnBook(request));

        assertEquals("This book is Not borrowed by borrower", exception.getMessage());
    }
}
