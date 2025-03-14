package com.example.uspForum.subject;

import com.example.uspForum.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceUnitTests {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    void findByIdReturnsSubject() {
        long subjectId = 1L;
        Subject subject = new Subject();

        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        assertEquals(subject, subjectService.findById(subjectId));

        verify(subjectRepository, times(1)).findById(subjectId);
        verifyNoMoreInteractions(subjectRepository);
    }

    @Test
    void findByNonExistentIdThrowsNotFoundException() {
        long subjectId = 1L;
        Subject subject = new Subject();

        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> subjectService.findById(subjectId));

        verify(subjectRepository, times(1)).findById(subjectId);
        verifyNoMoreInteractions(subjectRepository);
    }

}
