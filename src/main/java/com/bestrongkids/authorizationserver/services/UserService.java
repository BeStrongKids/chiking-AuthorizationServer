package com.bestrongkids.authorizationserver.services;

import com.bestrongkids.authorizationserver.dto.UserDto;
import com.bestrongkids.authorizationserver.entities.Authorities;
import com.bestrongkids.authorizationserver.entities.Authority;
import com.bestrongkids.authorizationserver.entities.User;
import com.bestrongkids.authorizationserver.repositories.AuthoritiesRepository;
import com.bestrongkids.authorizationserver.repositories.AuthorityRepository;
import com.bestrongkids.authorizationserver.repositories.UserRepository;
import com.bestrongkids.authorizationserver.result.user.RegisterUserResult;
import com.bestrongkids.authorizationserver.utils.exceptions.GeneralException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    @Transactional
    public String updateUser(@NonNull UserDto userDto) throws UsernameNotFoundException {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("User Not Found!");

        User user = findUserByEmailOrThrow(userDto.getEmail(),s);
        updateUserFields(user, userDto);

        userRepository.save(user);



        return "save";
    }

    private void updateUserFields(User user,UserDto userDto){
        if(!userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

    }

    private User findUserByEmailOrThrow(String username, Supplier<UsernameNotFoundException> usernameNotFoundExceptionSupplier) {
        return GeneralException.throwIfNotFound(userRepository.findUserByEmail(username),username + "는 존재 하지않는 유저 입니다.");
    }
}
