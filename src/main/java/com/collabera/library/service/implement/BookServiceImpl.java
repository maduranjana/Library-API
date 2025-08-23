package com.collabera.library.service.implement;

import com.collabera.library.dto.BookRequest;
import com.collabera.library.dto.BookResponse;
import com.collabera.library.dto.BorrowerResponse;
import com.collabera.library.exception.DuplicateResourceException;
import com.collabera.library.model.Book;
import com.collabera.library.model.Borrower;
import com.collabera.library.repository.BookRepository;
import com.collabera.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional
    public BookResponse createBook(BookRequest request) {

        Optional<List<Book>> existingBooks = bookRepository.findByIsbn(request.getIsbn());// Check book exists with ISBN

        for (Book existingBook : existingBooks.get()) {

            Book existing = existingBook;
            if (!existing.getTitle().equals(request.getTitle()) || !existing.getAuthor().equals(request.getAuthor())) {
                throw new IllegalArgumentException("The title and author must be the same for the same ISBN.");
            }
        }

        Book book = new Book(); // Save the new book

        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setTitle(request.getTitle());

        Book savedBook = bookRepository.save(book);

        return mapToResponse(savedBook);
    }

    @Override
    public List<BookResponse> getAllBooks() {

        return bookRepository.findAll()// map with BookResponse obj using Stream map
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BookResponse mapToResponse(Book book) { // policy obj map with response dto
        BookResponse response = new BookResponse();

        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setIsbn(book.getIsbn());
        response.setAuthor(book.getAuthor());
        response.setAvailability(book.isAvailability());

        return response;
    }
}
