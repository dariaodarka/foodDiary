package com.project.foodDiary.service;

import com.project.foodDiary.model.dto.UserDTO;
import com.project.foodDiary.entity.User;
import com.project.foodDiary.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CalorieService calorieService ;

    public UserService(UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder,
                       CalorieService calorieService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.calorieService = calorieService;
    }

    public User registerUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Имейл " + userDTO.getEmail() + " уже используется.");
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setWeight(userDTO.getWeight());
        user.setHeight(userDTO.getHeight());
        user.setAim(userDTO.getAim());
        user.setDailyCalories(calorieService.calculateDailyCalories(userDTO));
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return user;
    }

    public Optional<User> findOptionalUser(String email) {
        Optional<User> OptionalUser = userRepository.findOptionalByEmail(email);
        User user = OptionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        return OptionalUser;
    }

    public String findEmailById(int id) {
        Optional<User> OptionalUser = userRepository.findOptionalById(id);
        User user = OptionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        return user.getEmail();
    }

    public int getCalorieConsumption(long id) {
        return userRepository.findUserById(id).getDailyCalories();
    }


}
