package com.collabera.library.service;

import com.collabera.library.dto.BorrowBookRequest;
import com.collabera.library.dto.LibraryResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface BorrowedBookService {
    LibraryResponse borrowBook(@Valid BorrowBookRequest request);

    LibraryResponse returnBook(@Valid BorrowBookRequest request);
}
