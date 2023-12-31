package com.bestrongkids.authorizationserver.controller;

import com.bestrongkids.authorizationserver.dto.UserDto;
import com.bestrongkids.authorizationserver.entities.User;
import com.bestrongkids.authorizationserver.repositories.UserRepository;
import com.bestrongkids.authorizationserver.utils.exceptions.GeneralException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @MockBean
    private final UserRepository userRepository;

    @Autowired
    UserTest(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Order(1)
    @DisplayName("1. 유저 등록 테스트")
    @Test
    public void givenUser_whenRequestingRegisterUser_thenReturnSuccessMessage() throws Exception{
        // Given

        UserDto userDto = new UserDto("test1", "test1@test.com", "12345");
        String body = mapper.writeValueAsString(userDto);

        // When
        MvcResult result = mvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = mapper.readValue(responseContent, new TypeReference<Map<String, Object>>(){});

        assertTrue((Boolean) responseMap.get("success"));

        Map<String, String> userMap = (Map<String, String>) responseMap.get("response");
        assertEquals("test1", userMap.get("username"));

        assertNull(responseMap.get("error"));


        // 데이터베이스에서 사용자를 조회하여 검증

        var registeredUser = GeneralException.throwIfNotFound(userRepository.findUserByEmail(userDto.getEmail()),userDto.getEmail() + "는 존재 하지않는 유저 입니다.");
//        assertTrue(registeredUser.isPresent());
        assertEquals(userDto.getName(), registeredUser.getName());
        // 암호화된 패스워드가 데이터베이스에 저장되었는지 확인
        assertNotEquals(passwordEncoder.encode(userDto.getPassword()), registeredUser.getPassword());
    }

    @Order(2)
    @Test
    public void testFormLogin() throws Exception {
        mvc.perform(formLogin().user("test1@test.com").password("12345"))
                .andExpect(status().isFound())  // 로그인 성공 시 redirect되므로 302 상태 코드를 기대합니다.
                .andExpect(authenticated());
    }
    @Order(3)
    @WithMockUser(username = "test1@test.com", password = "12345")
    @DisplayName("2. 유저 비밀번호 Update 테스트")
    @Test
    public void givenUser_whenRequestingUpdateUser_thenReturnSuccessMessage() throws Exception{
        // Given
        UserDto userDto = new UserDto("test1", "test1@test.com", "12345","test123");
        String body = mapper.writeValueAsString(userDto);

        // When
//        mvc.perform(formLogin().user(userDto.getEmail()).password(userDto.getPassword()))
//                .andExpect(status().isFound())  // 로그인 성공 시 redirect되므로 302 상태 코드를 기대합니다.
//                .andExpect(authenticated());

        MvcResult result = mvc.perform(
                        put("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        // Then
        String responseContent = result.getResponse().getContentAsString();
        Map<String, Object> responseMap = mapper.readValue(responseContent, new TypeReference<Map<String, Object>>(){});

        assertTrue((Boolean) responseMap.get("success"));

        Map<String, String> userMap = (Map<String, String>) responseMap.get("response");
        assertEquals("test1", userMap.get("username"));

        assertNull(responseMap.get("error"));


        // 데이터베이스에서 사용자를 조회하여 검증
        Optional<User> registeredUser = userRepository.findUserByEmail(userDto.getEmail());
        assertTrue(registeredUser.isPresent());
        assertEquals(userDto.getName(), registeredUser.get().getName());
        // 암호화된 패스워드가 데이터베이스에 저장되었는지 확인
        assertNotEquals(userDto.getNewPassword(), registeredUser.get().getPassword());

    }



    @Order(4)
    @Test
    public void testFormLoginFailure() throws Exception {
        mvc.perform(formLogin().user("test1").password("wrongpassword"))
                .andExpect(status().isFound())  // 로그인 실패 시에도 redirect되므로 302 상태 코드를 기대합니다.
                .andExpect(redirectedUrl("/login?error"))  // 로그인 실패 시 로그인 페이지에 "error" 쿼리 파라미터와 함께 리다이렉트됩니다.
                .andExpect(unauthenticated());  // 사용자가 인증되지 않았음을 확인합니다.
    }

}