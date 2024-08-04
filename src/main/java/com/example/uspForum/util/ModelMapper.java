package com.example.uspForum.util;

import com.example.uspForum.contact.Contact;
import com.example.uspForum.contact.ContactDTO;
import com.example.uspForum.course.Course;
import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.professor.Professor;
import com.example.uspForum.subject.Subject;
import com.example.uspForum.subject.SubjectCreationDTO;
import com.example.uspForum.subjectReview.SubjectReview;
import com.example.uspForum.subjectReview.SubjectReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public Contact toContact(ContactDTO contactDTO, CustomUser sender) {
        return new Contact(contactDTO.getSubjectMatter(), contactDTO.getContent(), sender);
    }

    public Subject toSubject(SubjectCreationDTO subjectCreationDTO, Course course, Professor professor) {
        return new Subject(
                subjectCreationDTO.getName(),
                subjectCreationDTO.getAbbreviation(),
                subjectCreationDTO.getCode(),
                course,
                professor
        );
    }

    public SubjectReview toSubjectReview(SubjectReviewDTO subjectReviewDTO, CustomUser author, Subject subject) {
        return new SubjectReview(
                author,
                subject,
                subjectReviewDTO.getTitle(),
                subjectReviewDTO.getContent(),
                subjectReviewDTO.getRecommendation()
        );
    }

}
