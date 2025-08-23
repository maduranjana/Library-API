package com.collabera.library.service.implement;

import com.collabera.library.dto.BorrowerRequest;
import com.collabera.library.dto.BorrowerResponse;
import com.collabera.library.exception.DuplicateResourceException;
import com.collabera.library.model.Borrower;
import com.collabera.library.repository.BorrowerRepository;
import com.collabera.library.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowerServiceImpl implements BorrowerService {

    @Autowired
    BorrowerRepository borrowerRepository;

    @Override
    public BorrowerResponse createBorrower(BorrowerRequest request) {

        if (borrowerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Borrower", "email", request.getEmail());
        }

        Borrower borrower = new Borrower();

        borrower.setName(request.getName());
        borrower.setEmail(request.getEmail());

        Borrower saved_borrower = borrowerRepository.save(borrower); //Save new borrower record

        return mapToResponse(saved_borrower); // return saved borrower record after mapping response obj
    }



    private BorrowerResponse mapToResponse(Borrower borrower) { // policy obj map with response dto
        BorrowerResponse response = new BorrowerResponse();

        response.setId(borrower.getId());
        response.setName(borrower.getName());
        response.setEmail(borrower.getEmail());

        return response;
    }


}
