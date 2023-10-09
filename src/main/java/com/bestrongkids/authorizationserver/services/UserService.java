package com.bestrongkids.authorizationserver.services;

import com.bestrongkids.authorizationserver.dto.UserDto;
import com.bestrongkids.authorizationserver.entities.User;
import com.bestrongkids.authorizationserver.repositories.UserRepository;
import com.bestrongkids.authorizationserver.result.user.RegisterUserResult;
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

//        return "save";
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

    private User findUserByEmailOrThrow(String email, Supplier<UsernameNotFoundException> usernameNotFoundExceptionSupplier) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(usernameNotFoundExceptionSupplier);
    }
}
