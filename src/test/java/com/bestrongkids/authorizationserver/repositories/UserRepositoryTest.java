package com.bestrongkids.authorizationserver.repositories;

import com.bestrongkids.authorizationserver.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserRepositoryTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;


    private final UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DisplayName("1. 유저 이메일로 유저의 권한들을 가져오는 기능 테스트")
    @Test
    void givenUserEmail_whenFindUserAuthorities_thenUserAuthoritiesName() {
        // Given
        String userEmail = "chulsoo.kim@example.com";

        List<String> expectedRoleList = new ArrayList<>();
        expectedRoleList.add("ROLE_ADMIN");
        expectedRoleList.add("ROLE_USER");


        // When
        var user = userRepository.findAuthorityNamesByEmail(userEmail);

        //Then
        assertEquals((long) user.size(),2);
        assertEquals(expectedRoleList,user);

    }
}


