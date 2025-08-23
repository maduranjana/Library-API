package com.collabera.library.service;

import com.collabera.library.dto.BookRequest;
import com.collabera.library.dto.BookResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    BookResponse createBook(@Valid BookRequest request);

    List<BookResponse> getAllBooks();
}
