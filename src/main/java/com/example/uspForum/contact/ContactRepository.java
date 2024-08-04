package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Long> {

    List<Contact> findAllBySender(CustomUser sender);

}
