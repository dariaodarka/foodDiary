package com.project.foodDiary.service;

import com.project.foodDiary.entity.User;
import com.project.foodDiary.entity.enums.Activity;
import com.project.foodDiary.entity.enums.Gender;
import com.project.foodDiary.model.dto.UserDTO;
import com.project.foodDiary.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private CalorieService calorieService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("maria@gmail.com");
        userDTO.setName("Maria");
        userDTO.setAge(25);
        userDTO.setWeight(70);
        userDTO.setHeight(175);
        userDTO.setAim("maintenance");
        userDTO.setPassword("password");

        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(calorieService.calculateDailyCalories(any(UserDTO.class))).thenReturn(2000);

        User savedUser = new User();
        savedUser.setEmail(userDTO.getEmail());
        savedUser.setName(userDTO.getName());
        savedUser.setAge(userDTO.getAge());
        savedUser.setWeight(userDTO.getWeight());
        savedUser.setHeight(userDTO.getHeight());
        savedUser.setAim(userDTO.getAim());
        savedUser.setDailyCalories(2000);
        savedUser.setPassword("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getEmail(), result.getEmail());
        assertEquals(userDTO.getName(), result.getName());
        assertEquals(userDTO.getAge(), result.getAge());
        assertEquals(userDTO.getWeight(), result.getWeight());
        assertEquals(userDTO.getHeight(), result.getHeight());
        assertEquals(userDTO.getAim(), result.getAim());
        assertEquals(2000, result.getDailyCalories());
        assertEquals("encodedPassword", result.getPassword());

        verify(userRepository, times(1)).findByEmail(any(String.class));
        verify(bCryptPasswordEncoder, times(1)).encode(any(String.class));
        verify(calorieService, times(1)).calculateDailyCalories(any(UserDTO.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_EmailAlreadyExists() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("maria@gmail.com");

        when(userRepository.findByEmail(any(String.class))).thenReturn(new User());

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(userDTO));

        verify(userRepository, times(1)).findByEmail(any(String.class));
        verify(bCryptPasswordEncoder, never()).encode(any(String.class));
        verify(calorieService, never()).calculateDailyCalories(any(UserDTO.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void findOptionalUser_Success() {
        User user = new User();
        user.setEmail("maria@gmail.com");

        when(userRepository.findOptionalByEmail(any(String.class))).thenReturn(Optional.of(user));

        Optional<User> result = userService.findOptionalUser("maria@gmail.com");

        assertTrue(result.isPresent());
        assertEquals(user.getEmail(), result.get().getEmail());

        verify(userRepository, times(1)).findOptionalByEmail(any(String.class));
    }

    @Test
    void findOptionalUser_UserNotFound() {
        when(userRepository.findOptionalByEmail(any(String.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findOptionalUser("maria@gmail.com"));

        verify(userRepository, times(1)).findOptionalByEmail(any(String.class));
    }


    @Test
    void getCalorieConsumption_Success() {
        User user = new User();
        user.setId(1);
        user.setDailyCalories(2000);

        when(userRepository.findUserById(any(Long.class))).thenReturn(user);

        int result = userService.getCalorieConsumption(1);

        assertEquals(user.getDailyCalories(), result);

        verify(userRepository, times(1)).findUserById(any(Long.class));
    }
}
