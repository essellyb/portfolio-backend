package com.essel.portfolio.service;

import com.essel.portfolio.dto.ContactRequest;

public interface EmailService {

    void sendContactEmail(ContactRequest request);
}
