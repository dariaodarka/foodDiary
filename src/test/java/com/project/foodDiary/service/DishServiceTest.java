package com.project.foodDiary.service;

import com.project.foodDiary.entity.Dish;
import com.project.foodDiary.model.dto.DishDTO;
import com.project.foodDiary.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DishServiceTest {

    @InjectMocks
    private DishService dishService;

    @Mock
    private DishRepository dishRepository;


    @Test
    void createDish_ShouldReturnDish_WhenValidInput() {
        DishDTO dishDTO = new DishDTO("Пицца", 500, 20, 10, 60);
        Dish dish = new Dish("Пицца", 500, 20, 10, 60);

        when(dishRepository.save(any(Dish.class))).thenReturn(dish);

        Dish result = dishService.createDish(dishDTO);

        assertNotNull(result);
        assertEquals("Пицца", result.getName());
        verify(dishRepository, times(1)).save(any(Dish.class));
    }

}
