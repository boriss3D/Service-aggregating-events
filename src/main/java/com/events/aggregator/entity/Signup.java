package com.events.aggregator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(Signup.class)
@Table(name = "signups")
public class Signup implements Serializable {

    @Id
    @Column(nullable = false)
    private Long eventId;

    @Id
    @Column(nullable = false)
    private String userEmail;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
