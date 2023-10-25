package com.bestrongkids.authorizationserver.model;

import com.bestrongkids.authorizationserver.entities.User;
import com.bestrongkids.authorizationserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User users = userRepository.findUserByEmail(username).orElseThrow(() -> new UsernameNotFoundException("사용자가 없습니다."));

        return new SecurityUserDetails(users);


    }
}
