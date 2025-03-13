package com.project.foodDiary.service;

import com.project.foodDiary.entity.Dish;
import com.project.foodDiary.entity.Meal;
import com.project.foodDiary.model.dto.UserDTO;
import com.project.foodDiary.model.reportObjects.DailyReportObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalorieServiceTest {

    @InjectMocks
    private CalorieService calorieService;

    @Mock
    private DailyReportObject dailyReportObject;

    @Mock
    private Meal meal1;

    @Mock
    private Meal meal2;

    @Mock
    private Dish dish1;

    @Mock
    private Dish dish2;

    @Mock
    private UserDTO userDTO;

    @Test
    void getCalorieConsumption() {

        int baseCaloriesConsumption = 1000;

        when(dish1.getCalories()).thenReturn(500);
        when(dish1.getCarbs()).thenReturn(50);
        when(dish1.getProtein()).thenReturn(30);
        when(dish1.getFats()).thenReturn(20);

        when(dish2.getCalories()).thenReturn(600);
        when(dish2.getCarbs()).thenReturn(60);
        when(dish2.getProtein()).thenReturn(40);
        when(dish2.getFats()).thenReturn(25);

        when(meal1.getDishes()).thenReturn(Arrays.asList(dish1));
        when(meal2.getDishes()).thenReturn(Arrays.asList(dish2));

        List<Meal> meals = Arrays.asList(meal1, meal2);

        calorieService.setKBJU(dailyReportObject, meals, baseCaloriesConsumption);

        verify(dailyReportObject).setTotalCarbs(110);
        verify(dailyReportObject).setTotalProtein(70);
        verify(dailyReportObject).setTotalFats(45);
        verify(dailyReportObject).setTotalCalories(1100);

        verify(dailyReportObject).setCalorieConsumption("Норма калорий превышена на 100.");
    }

    @Test
    void testCheckCalorieConsumption_Exceeded() {

        int baseCalories = 2500;
        int dailyCalories = 2700;
        String result = calorieService.checkCalorieConsumption(baseCalories, dailyCalories);
        assertEquals("Норма калорий превышена на " + (dailyCalories - baseCalories) + ".", result);
    }

    @Test
    void testCheckCalorieConsumption_NotExceeded() {

        int baseCalories = 2500;
        int dailyCalories = 2200;
        String result = calorieService.checkCalorieConsumption(baseCalories, dailyCalories);
        assertEquals("Норма калорий не превышена.", result);
    }

    @Test
    void calculateDailyCalories_whenValidInput() {
        when(userDTO.getGender()).thenReturn("female");
        when(userDTO.getActivity()).thenReturn("high");
        when(userDTO.getHeight()).thenReturn(173);
        when(userDTO.getWeight()).thenReturn(59);
        when(userDTO.getAge()).thenReturn(25);

        int result = calorieService.calculateDailyCalories(userDTO);
        assertEquals(2451, result);

    }
}
