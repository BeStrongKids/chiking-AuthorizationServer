package com.bestrongkids.authorizationserver.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Entity
@Getter
@AllArgsConstructor
@Builder
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="authority_id")
    private Integer id;

    @JoinColumn(name="authorities_id")
    @ManyToOne
    private Authorities authoritiesId;

    @JoinColumn(name = "users_id")
    @ManyToOne
    private User userId;


    public Authority() {

    }
}
