package com.pablodev.documentworkspace.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "extensions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Extension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private Type type;

    @ElementCollection
    private List<String> actions;

    public Extension(String name, Type type, List<String> actions) {
        this.name = name;
        this.type = type;
        this.actions = actions;
    }

}
