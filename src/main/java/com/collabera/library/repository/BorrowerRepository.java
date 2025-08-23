package com.collabera.library.repository;

import com.collabera.library.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

    boolean existsByEmail(String email);


}
