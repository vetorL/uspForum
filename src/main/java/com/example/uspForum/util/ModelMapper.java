package com.example.uspForum.util;

import com.example.uspForum.contact.Contact;
import com.example.uspForum.contact.ContactDTO;
import com.example.uspForum.customUser.CustomUser;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public Contact toContact(ContactDTO contactDTO, CustomUser sender) {
        return new Contact(contactDTO.getSubjectMatter(), contactDTO.getContent(), sender);
    }

}
