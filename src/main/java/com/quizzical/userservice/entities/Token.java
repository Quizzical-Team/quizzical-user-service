package com.quizzical.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tokens")
@Getter // * intentional no setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token extends BaseEntity {
    private String tokenValue;

}
