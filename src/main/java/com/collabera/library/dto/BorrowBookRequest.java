package com.collabera.library.dto;

import jakarta.validation.constraints.NotNull;

public class BorrowBookRequest {

    @NotNull(message = "bookID is required")
    private Long bookID;

    @NotNull(message = "BorrowerId is required")
    private Long borrowerId;

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }
}
