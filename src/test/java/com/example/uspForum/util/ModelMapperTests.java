package com.example.uspForum.util;

import com.example.uspForum.contact.Contact;
import com.example.uspForum.contact.ContactDTO;
import com.example.uspForum.customUser.CustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ModelMapperTests {

    @Test
    @DisplayName("toContact method works")
    void toContactWorks() {
        ModelMapper modelMapper = new ModelMapper();

        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setSubjectMatter("subjectMatter");
        contactDTO.setContent("content");

        CustomUser sender = new CustomUser();

        Contact contact = modelMapper.toContact(contactDTO, sender);

        assertEquals(contactDTO.getSubjectMatter(), contact.getSubjectMatter());
        assertEquals(contactDTO.getContent(), contact.getContent());
        assertEquals(sender, contact.getSender());
    }

}
