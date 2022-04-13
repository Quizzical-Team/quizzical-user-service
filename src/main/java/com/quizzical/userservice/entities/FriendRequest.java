package com.quizzical.userservice.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = "friend_requests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"sender", "receiver"}),
        indexes = @Index(columnList = "sender, receiver", unique = true)
        // todo: make uniqueness order independent
        // QT-48
)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FriendRequest extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "sender")
    private Player sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private Player receiver;

    @Column(name = "accepted")
    private Boolean accepted = false;

    // todo: write a trigger to prevent duplicate friend requests
}
