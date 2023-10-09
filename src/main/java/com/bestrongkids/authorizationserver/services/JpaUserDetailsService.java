package com.bestrongkids.authorizationserver.services;

import com.bestrongkids.authorizationserver.entities.User;
import com.bestrongkids.authorizationserver.model.SecurityUserDetails;
import com.bestrongkids.authorizationserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public SecurityUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("User Not Found!");

        User u = userRepository.findUserByEmail(email).orElseThrow(s);
        return new SecurityUserDetails(u);
    }
}
