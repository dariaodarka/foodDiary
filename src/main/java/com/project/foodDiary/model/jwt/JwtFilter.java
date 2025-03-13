package com.project.foodDiary.model.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.foodDiary.entity.enums.ResponseMessage;
import com.project.foodDiary.model.util.ApiResponse;
import com.project.foodDiary.service.UserService;
import com.project.foodDiary.service.CustomUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserService userService;


    public JwtFilter(JwtService jwtService, CustomUserDetailsService customUserDetailsService, UserService userService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = getJWTFromRequest(request);

            if (StringUtils.hasText(token)) {
                if (!jwtService.validateToken(token)) {
                    throw new JwtException("Невалидный токен!");
                }

                String userId = jwtService.extractUserId(token);
                String email = userService.findEmailById(Integer.parseInt(userId));
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);
        } catch (JwtException e) {

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            apiResponse.setData(e.getMessage());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8");


            response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
        }
    }



    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
