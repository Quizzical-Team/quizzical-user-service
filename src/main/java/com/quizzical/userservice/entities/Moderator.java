package com.quizzical.userservice.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "moderators")
//@PrimaryKeyJoinColumn(name = "user")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Moderator extends User {
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "permission_level_id")
    private ModeratorPermission permissionLevel;
}
