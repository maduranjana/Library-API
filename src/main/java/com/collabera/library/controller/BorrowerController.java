package com.collabera.library.controller;

import com.collabera.library.dto.BorrowerRequest;
import com.collabera.library.dto.BorrowerResponse;


import com.collabera.library.service.BorrowerService;
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
@RequestMapping("api/v1/borrower")
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @Tag(name = "Create Borrower") // add tag name to swagger doc
    @Operation(summary = "Create a borrower") // add operation section with summary
    @ApiResponses(value = { // set custom api responses with http status codes and sample outputs
            @ApiResponse(responseCode = "201", description = "Successfully created borrower",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.collabera.library.dto.BorrowerResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "*/*", schema = @Schema(type = "string", example = "Internal server error")))
    })
    @PostMapping
    public ResponseEntity<BorrowerResponse> create(@Valid @RequestBody BorrowerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowerService.createBorrower(request));
    }
}
