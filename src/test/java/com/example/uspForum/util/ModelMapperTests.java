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
import com.example.uspForum.subjectReview.reviewReport.ReviewReport;
import com.example.uspForum.subjectReview.reviewReport.ReviewReportDTO;
import com.example.uspForum.vote.Vote;
import com.example.uspForum.vote.VoteDTO;
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

    @Test
    @DisplayName("toSubject method works")
    void toSubjectWorks() {
        ModelMapper modelMapper = new ModelMapper();

        SubjectCreationDTO subjectCreationDTO = new SubjectCreationDTO("name", "abbreviation",
                "code", "relatedCourseName", "relatedCampusAbbreviation",
                "professorEmail");

        Course course = new Course();
        Professor professor = new Professor();

        Subject subject = modelMapper.toSubject(subjectCreationDTO, course, professor);

        assertEquals(subjectCreationDTO.getName(), subject.getName());
        assertEquals(subjectCreationDTO.getAbbreviation(), subject.getAbbreviation());
        assertEquals(subjectCreationDTO.getCode(), subject.getCode());
        assertEquals(course, subject.getCourse());
        assertEquals(professor, subject.getProfessor());
    }

    @Test
    @DisplayName("toSubjectReview method works")
    void toSubjectReviewWorks() {
        ModelMapper modelMapper = new ModelMapper();

        SubjectReviewDTO subjectReviewDTO =
                new SubjectReviewDTO("title", "content", "recommendation");

        CustomUser author = new CustomUser();
        Subject subject = new Subject();

        SubjectReview subjectReview = modelMapper.toSubjectReview(subjectReviewDTO, author, subject);

        assertEquals(subjectReviewDTO.getTitle(), subjectReview.getTitle());
        assertEquals(subjectReviewDTO.getContent(), subjectReview.getContent());
        assertEquals(subjectReviewDTO.getRecommendation(), subjectReview.getRecommendation());
        assertEquals(author, subjectReview.getAuthor());
        assertEquals(subject, subjectReview.getSubject());
    }

    @Test
    @DisplayName("toVote method works when voting up")
    void toVoteWorksWhenVotingUp() {
        ModelMapper modelMapper = new ModelMapper();

        VoteDTO voteDTO = new VoteDTO(1L, "up");

        CustomUser voter = new CustomUser();
        SubjectReview subjectReview = new SubjectReview();

        Vote vote = modelMapper.toVote(voteDTO, voter, subjectReview);

        assertEquals(1, vote.getVote());
        assertEquals(voter, vote.getVoter());
        assertEquals(subjectReview, vote.getSubjectReview());
    }

    @Test
    @DisplayName("toVote method works when voting down")
    void toVoteWorksWhenVotingDown() {
        ModelMapper modelMapper = new ModelMapper();

        VoteDTO voteDTO = new VoteDTO(1L, "down");

        CustomUser voter = new CustomUser();
        SubjectReview subjectReview = new SubjectReview();

        Vote vote = modelMapper.toVote(voteDTO, voter, subjectReview);

        assertEquals(-1, vote.getVote());
        assertEquals(voter, vote.getVoter());
        assertEquals(subjectReview, vote.getSubjectReview());
    }

    @Test
    @DisplayName("toReviewReport works")
    void toReviewReportWorks() {
        // # Given
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO("reason");
        CustomUser accuser = new CustomUser("email", "username", "password");
        SubjectReview subjectReview = new SubjectReview();

        // # Call method to be tested
        ModelMapper modelMapper = new ModelMapper();
        ReviewReport reviewReport = modelMapper.toReviewReport(reviewReportDTO, accuser, subjectReview);

        assertEquals(reviewReportDTO.getReason(), reviewReport.getReason());
        assertEquals(accuser, reviewReport.getAccuser());
        assertEquals(subjectReview, reviewReport.getSubjectReview());
    }

}
