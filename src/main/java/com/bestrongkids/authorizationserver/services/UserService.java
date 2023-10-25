package com.bestrongkids.authorizationserver.services;

import com.bestrongkids.authorizationserver.dto.UserDto;
import com.bestrongkids.authorizationserver.entities.Authorities;
import com.bestrongkids.authorizationserver.entities.Authority;
import com.bestrongkids.authorizationserver.entities.User;
import com.bestrongkids.authorizationserver.repositories.AuthoritiesRepository;
import com.bestrongkids.authorizationserver.repositories.AuthorityRepository;
import com.bestrongkids.authorizationserver.repositories.UserRepository;
import com.bestrongkids.authorizationserver.result.user.RegisterUserResult;
import com.bestrongkids.authorizationserver.result.user.UpdateUserResult;
import com.bestrongkids.authorizationserver.utils.exceptions.GeneralException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.function.Supplier;

import static com.bestrongkids.authorizationserver.utils.ApiUtils.ApiResult;
import static com.bestrongkids.authorizationserver.utils.ApiUtils.success;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final AuthoritiesRepository authoritiesRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ApiResult<RegisterUserResult> registerUser(@NonNull UserDto userDto){
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nonExpired(true)
                .nonBlocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
        User registeredUser = userRepository.save(user);

        Authorities authorities = authoritiesRepository.findAuthoritiesByName("ROLE_USER").orElseThrow();

        Authority authority = Authority.builder()
                .userId(registeredUser)
                .authoritiesId(authorities)
                .build();
        authorityRepository.save(authority);
//        user.getAuthorities().add(authority);

        return success(new RegisterUserResult(registeredUser.getName()));
    }


    // TODO : 로그인된 멤버 또는 권한을 가진 사람에게만 허락되는 메소드 추가가 필요함.
    @Transactional
    public ApiResult<UpdateUserResult> updateUser(@NonNull UserDto userDto) throws UsernameNotFoundException {
        User user = GeneralException.throwIfNotFound(userRepository.findUserByEmail(userDto.getEmail()),userDto.getEmail() + "는 존재 하지않는 유저 입니다.");
        String encodedUserPw = passwordEncoder.encode(userDto.getPassword());
        if(!passwordEncoder.matches(userDto.getPassword(), encodedUserPw))
        {
            throw new BadCredentialsException("Email 또는 Password를 확인해주세요.");
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User updatedUser =  userRepository.save(user);


        return success(new UpdateUserResult(updatedUser.getName()));

    }


}
