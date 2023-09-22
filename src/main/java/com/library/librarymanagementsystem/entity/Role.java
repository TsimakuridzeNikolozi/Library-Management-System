package com.library.librarymanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name = "role")
public class Role extends BaseEntity {
    @Column(name = "name")
    private String name;
}