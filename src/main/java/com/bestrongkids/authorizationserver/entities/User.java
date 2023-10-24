package com.bestrongkids.authorizationserver.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
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
    @Column(name= "users_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Setter
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private int loginCount;

    @JsonIgnore
    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private List<Authority> authorities;

    private LocalDateTime lastLoginAt;

    @CreatedDate
    private LocalDateTime createAt;

    private boolean nonExpired;

    private boolean nonBlocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    public User() {

    }

}
