package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.util.DateHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTests {

    @Mock
    private DateHandler dateHandler;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    @DisplayName("Case where the user has never attempted to initiate contact works correctly")
    void neverContactedWorksCorrectly() {
        // # Given
        Contact contact = new Contact();

        // # Mock method calls

        // Returning null means that no contact from this user is registered in the DB
        when(contactRepository.findFirstBySenderOrderByCreatedAtDesc(contact.getSender())).thenReturn(null);

        // # Call method that is to be tested
        assertTrue(contactService.save(contact));

        // # Verify interactions

        // Interactions with contactRepository
        verify(contactRepository, times(1))
                .findFirstBySenderOrderByCreatedAtDesc(contact.getSender());
        verify(contactRepository, times(1))
                .save(contact);
        verifyNoMoreInteractions(contactRepository);

        // Interactions with dateHandler
        verifyNoInteractions(dateHandler);
    }

    @Test
    @DisplayName("Case where the user has attempted to initiate contact within the last 24 hours works correctly")
    void hasContactedWithin24HoursWorksCorrectly() {
        // # Given
        Contact contact = new Contact();
        Contact lastContact = new Contact();

        // # Mock method calls

        // Return most recent contact from this user that is registered in the DB
        when(contactRepository.findFirstBySenderOrderByCreatedAtDesc(contact.getSender())).thenReturn(lastContact);

        // Determine that the last contact is not older than one day
        when(dateHandler.isOlderThanOneDay(lastContact.getCreatedAt())).thenReturn(false);

        // # Call method that is to be tested
        assertFalse(contactService.save(contact));

        // # Verify interactions

        // Interactions with contactRepository
        verify(contactRepository, times(1))
                .findFirstBySenderOrderByCreatedAtDesc(contact.getSender());
        verifyNoMoreInteractions(contactRepository);

        // Interactions with dateHandler
        verify(dateHandler, times(1))
                .isOlderThanOneDay(lastContact.getCreatedAt());
        verifyNoMoreInteractions(dateHandler);
    }

    @Test
    @DisplayName("Case where the user has contacted more than 24 hours ago works correctly")
    void hasContactedMoreThan24HoursAgoWorksCorrectly() {
        // # Given
        Contact contact = new Contact();
        Contact lastContact = new Contact();

        // # Mock method calls

        // Return most recent contact from this user that is registered in the DB
        when(contactRepository.findFirstBySenderOrderByCreatedAtDesc(contact.getSender())).thenReturn(lastContact);

        // Determine that the last contact is older than one day
        when(dateHandler.isOlderThanOneDay(lastContact.getCreatedAt())).thenReturn(true);

        // # Call method that is to be tested
        assertTrue(contactService.save(contact));

        // # Verify interactions

        // Interactions with contactRepository
        verify(contactRepository, times(1))
                .findFirstBySenderOrderByCreatedAtDesc(contact.getSender());
        verify(contactRepository, times(1))
                .save(contact);
        verifyNoMoreInteractions(contactRepository);

        // Interactions with dateHandler
        verify(dateHandler, times(1))
                .isOlderThanOneDay(lastContact.getCreatedAt());
        verifyNoMoreInteractions(dateHandler);
    }

    @Test
    @DisplayName("canContact works when user has never contacted")
    void canContactWorksWhenUserHasNotContacted() {
        CustomUser sender = new CustomUser();

        when(contactRepository.findFirstBySenderOrderByCreatedAtDesc(sender)).thenReturn(null);

        assertTrue(contactService.canContact(sender));

        verify(contactRepository, times(1)).findFirstBySenderOrderByCreatedAtDesc(sender);
        verifyNoMoreInteractions(contactRepository);
    }

    @Test
    @DisplayName("canContact works when user has contacted within the last 24 hours")
    void canContactWorksWhenUserHasContactedWithinLast24Hours() {
        // # Given
        Contact contact = new Contact();
        CustomUser sender = new CustomUser();

        // # Mock return of called methods
        when(contactRepository.findFirstBySenderOrderByCreatedAtDesc(sender)).thenReturn(contact);
        when(dateHandler.isOlderThanOneDay(contact.getCreatedAt())).thenReturn(false);

        // # Call service method being tested and assert its return value
        assertFalse(contactService.canContact(sender));

        // # Verify interactions

        // Interactions with contactRepository
        verify(contactRepository, times(1)).findFirstBySenderOrderByCreatedAtDesc(sender);
        verifyNoMoreInteractions(contactRepository);

        // Interactions with dateHandler
        verify(dateHandler, times(1)).isOlderThanOneDay(contact.getCreatedAt());
        verifyNoMoreInteractions(dateHandler);
    }

    @Test
    @DisplayName("canContact works when user has contacted more than 24 hours ago")
    void canContactWorksWhenUserHasContactedMoreThan24HoursAgo() {
        // # Given
        Contact contact = new Contact();
        CustomUser sender = new CustomUser();

        // # Mock return of called methods
        when(contactRepository.findFirstBySenderOrderByCreatedAtDesc(sender)).thenReturn(contact);
        when(dateHandler.isOlderThanOneDay(contact.getCreatedAt())).thenReturn(true);

        // # Call service method being tested and assert its return value
        assertTrue(contactService.canContact(sender));

        // # Verify interactions

        // Interactions with contactRepository
        verify(contactRepository, times(1)).findFirstBySenderOrderByCreatedAtDesc(sender);
        verifyNoMoreInteractions(contactRepository);

        // Interactions with dateHandler
        verify(dateHandler, times(1)).isOlderThanOneDay(contact.getCreatedAt());
        verifyNoMoreInteractions(dateHandler);
    }

    @Test
    void getPreviousContactAttemptsWorks() {
        CustomUser sender = new CustomUser();
        Contact contact = new Contact();

        List<Contact> previousContactAttempts = List.of(contact);

        when(contactRepository.findAllBySenderOrderByCreatedAtDesc(sender)).thenReturn(previousContactAttempts);

        assertEquals(previousContactAttempts, contactService.getPreviousContactAttempts(sender));

        verify(contactRepository, times(1)).findAllBySenderOrderByCreatedAtDesc(sender);
        verifyNoMoreInteractions(contactRepository);
    }

}
