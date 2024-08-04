package com.example.uspForum.contact;

import com.example.uspForum.customUser.CustomUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String subjectMatter;
    private final String content;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private final CustomUser sender;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    private ContactStatus status = ContactStatus.PENDING;

    private String response;

}
