package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.util.DateHandler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final DateHandler dateHandler;

    public ContactService(ContactRepository contactRepository, DateHandler dateHandler) {
        this.contactRepository = contactRepository;
        this.dateHandler = dateHandler;
    }

    @PreAuthorize("authenticated")
    public boolean save(Contact contact) {

        // ### Saves the contact to the DB only if it has been more than 24 hours since the last attempt

        // # Find most recent contact of the user
        Contact lastContact = contactRepository.findFirstBySenderOrderByCreatedAtDesc(contact.getSender());

        if (lastContact != null) {
            // # Case where a contact attempt has already been made by the user

            // If more than one day has passed since the last contact attempt, allow it to be saved
            // Else reject it
            if (dateHandler.isOlderThanOneDay(lastContact.getCreatedAt())) {
                contactRepository.save(contact);
                return true;
            }

        } else {
            // # Case where the user has never attempted to initiate contact
            contactRepository.save(contact);
            return true;
        }

        return false;
    }

    public boolean canContact(CustomUser sender) {

        // ### Returns true if it has been more than 24 hours since last contact attempt, else returns false

        // # Find most recent contact of the user
        Contact lastContact = contactRepository.findFirstBySenderOrderByCreatedAtDesc(sender);

        // # Case where the user has never attempted to initiate contact
        if (lastContact == null) {
            return true;
        }

        // # Case where a contact attempt has already been made by the user
        return dateHandler.isOlderThanOneDay(lastContact.getCreatedAt());
    }

    public List<Contact> getPreviousContactAttempts(CustomUser sender) {
        return contactRepository.findAllBySenderOrderByCreatedAtDesc(sender);
    }

}
