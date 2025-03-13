package com.project.foodDiary.service;

import com.project.foodDiary.entity.Dish;
import com.project.foodDiary.entity.Meal;
import com.project.foodDiary.entity.enums.Activity;
import com.project.foodDiary.entity.enums.Gender;
import com.project.foodDiary.model.dto.UserDTO;
import com.project.foodDiary.model.reportObjects.DailyReportObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalorieService {

    public void setKBJU(DailyReportObject dailyReportObject, List<Meal> meals, int baseCalorieConsumption) {
        int totalCalories = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(Dish::getCalories)
                .sum();

        int totalCarbs = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(dish -> dish.getCarbs())
                .sum();

        int totalProtein = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(dish -> dish.getProtein())
                .sum();

        int totalFats = meals.stream()
                .flatMap(meal -> meal.getDishes().stream())
                .mapToInt(dish -> dish.getFats())
                .sum();

        dailyReportObject.setTotalCalories(totalCalories);
        dailyReportObject.setTotalCarbs(totalCarbs);
        dailyReportObject.setTotalProtein(totalProtein);
        dailyReportObject.setTotalFats(totalFats);
        dailyReportObject.setCalorieConsumption(checkCalorieConsumption(baseCalorieConsumption, totalCalories));
    }

    public String checkCalorieConsumption (int baseCaloriesConsumption, int dailyCaloriesConsumption){
        String calorieConsumptionComment;
        if(baseCaloriesConsumption >= dailyCaloriesConsumption){
            calorieConsumptionComment = "Норма калорий не превышена.";
        }else {
            calorieConsumptionComment = "Норма калорий превышена на " + (dailyCaloriesConsumption - baseCaloriesConsumption) + ".";
        }
        return calorieConsumptionComment;
    }

    public int calculateDailyCalories(UserDTO userDTO) {
        int bmr = 0;

        if(userDTO.getGender().toUpperCase().equals(Gender.MALE.toString())) {
            bmr = (int) Math.round(88.362 + (13.397 * userDTO.getWeight()) + (4.799 * userDTO.getHeight())
                    - (5.677 * userDTO.getAge()));
        }if(userDTO.getGender().toUpperCase().equals(Gender.FEMALE.toString())) {
            bmr = (int) Math.round(447.593 + (9.247 * userDTO.getWeight()) + (3.098 * userDTO.getHeight())
                    - (4.330 * userDTO.getAge()));
        }

        return switch (Activity.valueOf(userDTO.getActivity().toUpperCase())){
            case LOW -> (int) Math.round(bmr * 1.2);
            case MEDIUM -> (int) Math.round(bmr * 1.55);
            case HIGH -> (int) Math.round(bmr * 1.725);
            case VERY_HIGH -> (int) Math.round(bmr * 1.9);
        };
    }
}
