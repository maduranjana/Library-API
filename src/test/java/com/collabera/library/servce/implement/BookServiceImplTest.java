package com.collabera.library.servce.implement;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.collabera.library.dto.BookRequest;
import com.collabera.library.dto.BookResponse;
import com.collabera.library.model.Book;
import com.collabera.library.repository.BookRepository;
import com.collabera.library.service.implement.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook_NewISBN_ShouldSaveBook() {
        BookRequest request = new BookRequest();
        request.setIsbn("123-ABC");
        request.setTitle("Clean Code");
        request.setAuthor("Robert C. Martin");

        when(bookRepository.findByIsbn("123-ABC")).thenReturn(Optional.of(Collections.emptyList()));
        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setIsbn("123-ABC");
        savedBook.setTitle("Clean Code");
        savedBook.setAuthor("Robert C. Martin");
        savedBook.setAvailability(true);

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        BookResponse response = bookService.createBook(request);

        assertNotNull(response);
        assertEquals("123-ABC", response.getIsbn());
        assertEquals("Clean Code", response.getTitle());
        assertEquals("Robert C. Martin", response.getAuthor());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testCreateBook_SameISBNDifferentTitle_ShouldThrowException() {
        BookRequest request = new BookRequest();
        request.setIsbn("123-ABC");
        request.setTitle("Different Title");
        request.setAuthor("Someone Else");

        Book existingBook = new Book();
        existingBook.setIsbn("123-ABC");
        existingBook.setTitle("Clean Code");
        existingBook.setAuthor("Robert C. Martin");

        when(bookRepository.findByIsbn("123-ABC")).thenReturn(Optional.of(Arrays.asList(existingBook)));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> bookService.createBook(request));

        assertEquals("The title and author must be the same for the same ISBN.", exception.getMessage());
    }

    @Test
    void testGetAllBooks_ShouldReturnBookResponseList() {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setIsbn("123-ABC");
        book1.setTitle("Clean Code");
        book1.setAuthor("Robert C. Martin");
        book1.setAvailability(true);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setIsbn("456-DEF");
        book2.setTitle("Effective Java");
        book2.setAuthor("Joshua Bloch");
        book2.setAvailability(false);

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<BookResponse> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        assertEquals("Clean Code", books.get(0).getTitle());
        assertEquals("Effective Java", books.get(1).getTitle());
    }
}
