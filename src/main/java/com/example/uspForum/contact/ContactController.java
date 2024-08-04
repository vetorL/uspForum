package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.util.ModelMapper;
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
                         ContactDTO contactDTO,
                         Model model) {

        contactService.save(modelMapper.toContact(contactDTO, sender));
        model.addAttribute("contactRegistered", true);
        model.addAttribute("contactAttempts", contactService.getPreviousContactAttempts(sender));

        return "contact";
    }

    @GetMapping
    public String read(@AuthenticationPrincipal CustomUser sender,
                       Model model) {

        model.addAttribute("contactDTO", new ContactDTO());
        model.addAttribute("contactAttempts", contactService.getPreviousContactAttempts(sender));

        return "contact";
    }

}
