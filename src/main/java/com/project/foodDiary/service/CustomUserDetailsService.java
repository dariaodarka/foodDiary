package com.project.foodDiary.service;

import com.project.foodDiary.entity.User;
import com.project.foodDiary.entity.CustomUserDetails;
import com.project.foodDiary.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOptionalByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден."));
        CustomUserDetails customUserDetails = new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), Arrays.asList());
        return customUserDetails;
    }
}
