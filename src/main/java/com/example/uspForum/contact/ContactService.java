package com.example.uspForum.contact;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PreAuthorize("authenticated")
    public void save(Contact contact) {

        // If user already made a contact attempt within the last 24 hours, reject it
        contactRepository.save(contact);
    }

}
