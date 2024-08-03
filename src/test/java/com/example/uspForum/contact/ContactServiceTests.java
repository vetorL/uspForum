package com.example.uspForum.contact;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTests {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    void saveWorks() {
        Contact contact = new Contact();

        contactService.save(contact);

        verify(contactRepository, times(1)).save(contact);
        verifyNoMoreInteractions(contactRepository);
    }

}
