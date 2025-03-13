package com.project.foodDiary.service;

import com.project.foodDiary.entity.Dish;
import com.project.foodDiary.entity.Meal;
import com.project.foodDiary.entity.User;
import com.project.foodDiary.model.dto.MealDTO;
import com.project.foodDiary.repository.DishRepository;
import com.project.foodDiary.repository.MealRespository;
import com.project.foodDiary.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {

    @InjectMocks
    private MealService mealService;

    @Mock
    private MealRespository mealRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DishRepository dishRepository;

    @Test
    void createMeal_whenValidInput() {

        Long userId = 1L;
        Long dishId1 = 1L;
        Long dishId2 = 2L;

        MealDTO mealDTO = new MealDTO();
        mealDTO.setUserId(userId);
        mealDTO.setName("Meal_Name");
        mealDTO.setDate(LocalDate.parse("2025-03-09"));
        mealDTO.setDishIds(Arrays.asList(dishId1, dishId2));

        User user = new User();
        user.setId(userId);

        Dish dish1 = new Dish();
        dish1.setId(dishId1.intValue());

        Dish dish2 = new Dish();
        dish2.setId(dishId2.intValue());

        Meal meal = new Meal();
        meal.setName(mealDTO.getName());
        meal.setDate(mealDTO.getDate());
        meal.setUser(user);
        meal.setDishes(Arrays.asList(dish1, dish2));

        when(userRepository.findUserById(userId)).thenReturn(user);
        when(dishRepository.findById(dishId1)).thenReturn(Optional.of(dish1));
        when(dishRepository.findById(dishId2)).thenReturn(Optional.of(dish2));
        when(mealRepository.save(any(Meal.class))).thenReturn(meal);

        Meal result = mealService.createMeal(mealDTO);

        assertNotNull(result);
        assertEquals("Meal_Name", result.getName());
        assertEquals(user, result.getUser());
        assertEquals(2, result.getDishes().size());

        verify(userRepository).findUserById(userId);
        verify(dishRepository).findById(dishId1);
        verify(dishRepository).findById(dishId2);
        verify(mealRepository).save(any(Meal.class));
    }
}
