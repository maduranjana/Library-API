package com.collabera.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class BookRequest {

    @NotBlank(message = "isbn is required")
    @Size(min = 2, max = 50, message = "isbn must be between 2 and 50 characters")
    private String isbn;

    @NotBlank(message = "title is required")
    @Size(min = 2, max = 50, message = "title must be between 2 and 50 characters")
    private String title;

    @NotBlank(message = "author is required")
    @Size(min = 2, max = 50, message = "author must be between 2 and 50 characters")
    private String author;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
