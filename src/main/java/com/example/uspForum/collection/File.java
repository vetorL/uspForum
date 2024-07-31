package com.example.uspForum.collection;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subject.Subject;
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
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String name;
    private final String url;
    private final String type;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private final Subject subject;

    @ManyToOne
    @JoinColumn(name = "uploader_id")
    private final CustomUser uploader;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

}
