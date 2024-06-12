package com.hutech.furniturestore.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String  name;

    @Column(name="description")
    private String description;

    @Column(name = "is_removed")
    private Boolean isRemoved = false;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
