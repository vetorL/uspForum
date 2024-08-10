package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.util.ModelMapper;
import jakarta.validation.Valid;
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
    private final ModelMapper modelMapper;

    public ContactController(ContactService contactService, ModelMapper modelMapper) {
        this.contactService = contactService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public String create(@AuthenticationPrincipal CustomUser sender,
                         @Valid ContactDTO contactDTO,
                         Model model) {

        contactService.save(modelMapper.toContact(contactDTO, sender));
        model.addAttribute("canContact", contactService.canContact(sender));
        model.addAttribute("contactAttempts", contactService.getPreviousContactAttempts(sender));
        model.addAttribute("title", "Contato");

        return "contact";
    }

    @GetMapping
    public String read(@AuthenticationPrincipal CustomUser sender,
                       Model model) {

        model.addAttribute("canContact", contactService.canContact(sender));
        model.addAttribute("contactDTO", new ContactDTO());
        model.addAttribute("contactAttempts", contactService.getPreviousContactAttempts(sender));
        model.addAttribute("title", "Contato");

        return "contact";
    }

}
