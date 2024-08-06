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

    public Vote toVote(VoteDTO voteDTO, CustomUser voter, SubjectReview subjectReview) {
        int voteValue = 0;

        if(voteDTO.getVote().equals("up")) {
            voteValue = 1;
        } else if (voteDTO.getVote().equals("down")) {
            voteValue = -1;
        }

        return new Vote(
                voteValue,
                voter,
                subjectReview
        );
    }

    public ReviewReport toReviewReport(ReviewReportDTO reviewReportDTO, CustomUser accuser,
                                       SubjectReview subjectReview) {

        return new ReviewReport(reviewReportDTO.getReason(), accuser, subjectReview);
    }

}
