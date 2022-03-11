package com.tuzgen.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "moderator_permission")
@Getter // * intentional no setter
@NoArgsConstructor
@AllArgsConstructor
public class ModeratorPermission extends BaseEntity {
    private String permissionLevel;
}