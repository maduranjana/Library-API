package com.collabera.library.service;

import com.collabera.library.dto.BorrowerRequest;
import com.collabera.library.dto.BorrowerResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public interface BorrowerService {
    BorrowerResponse createBorrower(@Valid BorrowerRequest request);

}
