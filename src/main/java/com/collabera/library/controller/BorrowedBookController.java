package com.collabera.library.controller;

import com.collabera.library.dto.BorrowBookRequest;
import com.collabera.library.dto.LibraryResponse;
import com.collabera.library.service.BorrowedBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/library")
public class BorrowedBookController {

    @Autowired
    private BorrowedBookService borrowedBookService;

    @Tag(name = "Borrow a book") // add tag name to swagger doc
    @Operation(summary = "Create a borrow book") // add operation section with summary
    @ApiResponses(value = { // set custom api responses with http status codes and sample outputs
            @ApiResponse(responseCode = "201", description = "Successfully borrowed a book",
                    content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = com.collabera.library.dto.LibraryResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "*/*", schema = @Schema(type = "string", example = "Internal server error")))
    })
    @PostMapping("/borrow")
    public ResponseEntity<LibraryResponse> borrowBook(@Valid @RequestBody BorrowBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowedBookService.borrowBook(request));
    }

    @Tag(name = "Return a book") // add tag name to swagger doc
    @Operation(summary = "Create a return book") // add operation section with summary
    @ApiResponses(value = { // set custom api responses with http status codes and sample outputs
            @ApiResponse(responseCode = "201", description = "Successfully borrowed a book",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.collabera.library.dto.LibraryResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "*/*", schema = @Schema(type = "string", example = "Internal server error")))
    })
    @PostMapping("/return")
    public ResponseEntity<LibraryResponse> returnBook(@Valid @RequestBody BorrowBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowedBookService.returnBook(request));
    }
}
