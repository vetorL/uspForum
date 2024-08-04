package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void getPreviousContactAttemptsWorks() {
        CustomUser sender = new CustomUser();
        Contact contact = new Contact();

        List<Contact> previousContactAttempts = List.of(contact);

        when(contactRepository.findAllBySender(sender)).thenReturn(previousContactAttempts);

        assertEquals(previousContactAttempts, contactService.getPreviousContactAttempts(sender));

        verify(contactRepository, times(1)).findAllBySender(sender);
        verifyNoMoreInteractions(contactRepository);
    }

}
