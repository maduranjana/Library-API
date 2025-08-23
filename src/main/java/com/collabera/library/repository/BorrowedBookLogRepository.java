package com.collabera.library.repository;


import com.collabera.library.model.BorrowedBookLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BorrowedBookLogRepository extends JpaRepository<BorrowedBookLog, Long> {

    @Query("SELECT b FROM BorrowedBookLog b WHERE b.book.id = :book_id AND b.isReturned = false ")
    Optional<BorrowedBookLog> findByBookidAndNotreturned(@Param("book_id") Long bookId);

    @Query("SELECT b FROM BorrowedBookLog b WHERE b.book.id = :book_id AND b.borrower.id= :borrower_id ")
    Optional<BorrowedBookLog> findByBookidAndBorrowerId(@Param("book_id") Long bookID, @Param("borrower_id") Long borrowerId);
}
