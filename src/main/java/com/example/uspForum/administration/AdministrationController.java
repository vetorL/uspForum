package com.example.uspForum.administration;

import com.example.uspForum.contact.ContactService;
import com.example.uspForum.subjectReview.reviewReport.ReviewReport;
import com.example.uspForum.subjectReview.reviewReport.ReviewReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdministrationController {

    private final ReviewReportService reviewReportService;
    private final ContactService contactService;

    public AdministrationController(ReviewReportService reviewReportService, ContactService contactService) {
        this.reviewReportService = reviewReportService;
        this.contactService = contactService;
    }

    @GetMapping
    public String index() {
        return "administration";
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {

        List<ReviewReport> reviewReports = reviewReportService.findAll();

        model.addAttribute("tab", "reviews");
        model.addAttribute("reviewReports", reviewReports);

        return "administration";
    }

    @GetMapping("/contatos")
    public String contacts(Model model) {

        model.addAttribute("tab", "contacts");
        model.addAttribute("contactAttempts", contactService.findAll());

        return "administration";
    }

}
