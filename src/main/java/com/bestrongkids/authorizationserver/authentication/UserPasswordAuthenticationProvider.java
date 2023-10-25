package com.bestrongkids.authorizationserver.authentication;

import com.bestrongkids.authorizationserver.entities.Authority;
import com.bestrongkids.authorizationserver.entities.User;
import com.bestrongkids.authorizationserver.repositories.UserRepository;
import com.bestrongkids.authorizationserver.utils.exceptions.GeneralException;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserPasswordAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        var user = GeneralException.throwIfNotFound(userRepository.findUserByEmail(username),username + "는 존재 하지않는 유저 입니다.");
        if(passwordEncoder.matches(pwd,user.getPassword())){
            List<GrantedAuthority> authorityList = userRepository.findAuthorityNamesByEmail(user.getEmail())
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword(),authorityList);
        }
        throw new BadCredentialsException("Email 또는 Password를 확인해주세요.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
