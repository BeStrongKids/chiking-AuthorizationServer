package com.bestrongkids.authorizationserver.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Setter
    private String password;

    private int loginCount;

    private String algorithm;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<Authority> authorities;

    private LocalDateTime lastLoginAt;

    @CreatedDate
    private LocalDateTime createAt;

    private boolean nonExpired;

    private boolean nonBlocked;

    private boolean enabled;

    public User() {

    }


}
