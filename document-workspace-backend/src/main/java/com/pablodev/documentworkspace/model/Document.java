package com.pablodev.documentworkspace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "documents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Document extends Item {

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

}
