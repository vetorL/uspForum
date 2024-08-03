package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contato")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public String create(@AuthenticationPrincipal CustomUser sender,
                         ContactDTO contactDTO,
                         Model model) {

        contactService.save(contactDTO.toContact(sender));
        model.addAttribute("contactRegistered", true);

        return "contact";
    }

    @GetMapping
    public String read(Model model) {

        model.addAttribute("contactDTO", new ContactDTO());

        return "contact";
    }

}
