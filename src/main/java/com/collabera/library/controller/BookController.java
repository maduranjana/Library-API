package com.collabera.library.controller;

import com.collabera.library.dto.BookRequest;
import com.collabera.library.dto.BookResponse;
import com.collabera.library.dto.BorrowerRequest;
import com.collabera.library.dto.BorrowerResponse;
import com.collabera.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Tag(name = "Register Book") // add tag name to swagger doc
    @Operation(summary = "Register a Book") // add operation section with summary
    @ApiResponses(value = { // set custom api responses with http status codes and sample outputs
            @ApiResponse(responseCode = "201", description = "Successfully created Book",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.collabera.library.dto.BookResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "*/*", schema = @Schema(type = "string", example = "Internal server error")))
    })
    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @Tag(name = "Get All Books")
    @Operation(summary = "Retrieve All of Books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BookResponse.class))))
    })
    @GetMapping
    public List<BookResponse> getAll() {
        return bookService.getAllBooks();
    }

}
