package com.project.foodDiary.controller;


import com.project.foodDiary.entity.User;
import com.project.foodDiary.entity.enums.ResponseMessage;
import com.project.foodDiary.model.util.ApiResponse;
import com.project.foodDiary.model.auth.AuthRequest;
import com.project.foodDiary.entity.CustomUserDetails;
import com.project.foodDiary.model.jwt.JwtService;
import com.project.foodDiary.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        ApiResponse response = new ApiResponse();

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (authRequest.getEmail(), authRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            Optional<User> optionalUser = userService.findOptionalUser(authRequest.getEmail());
            User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
            List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));

            CustomUserDetails userDetails = new CustomUserDetails(
                    user.getId(),
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );

            String token = jwtService.generateToken(userDetails.getId());
            response.setStatus(ResponseMessage.SUCCESS.getMessage());
            response.setData(token);
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            response.setData("Ошибка аунтефикации: Неправильный логин или пароль.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}


