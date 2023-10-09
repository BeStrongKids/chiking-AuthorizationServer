package com.bestrongkids.authorizationserver.config;

import com.bestrongkids.authorizationserver.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DefaultConfig {
//    private final UserRepository userRepository;
//    @Bean
//    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//
//    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager();
//        userDetailsManager.setDataSource(dataSource);
//        return userDetailsManager;
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
}
