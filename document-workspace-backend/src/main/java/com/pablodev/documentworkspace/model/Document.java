package com.pablodev.documentworkspace.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String extension;

    @Column(nullable = false)
    private Long length;

    @Column(nullable = false)
    private boolean locked = false;

    @Column(nullable = false)
    private Long version = 0L;

    @Lob
    private byte[] content;

    @ManyToOne
    private Folder folder;

}
