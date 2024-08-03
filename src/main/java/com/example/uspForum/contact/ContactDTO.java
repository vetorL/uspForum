package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import lombok.Data;

@Data
public class ContactDTO {

    private String subjectMatter;
    private String content;

    public Contact toContact(CustomUser sender) {
        return new Contact(subjectMatter, content, sender);
    }

}
