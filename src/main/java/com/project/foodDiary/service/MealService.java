package com.project.foodDiary.service;

import com.project.foodDiary.model.dto.MealDTO;
import com.project.foodDiary.entity.Dish;
import com.project.foodDiary.entity.Meal;
import com.project.foodDiary.entity.User;
import com.project.foodDiary.repository.DishRepository;
import com.project.foodDiary.repository.MealRespository;
import com.project.foodDiary.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {
    private final UserRepository userRepository;
    private final MealRespository mealRepository;
    private final DishRepository dishRepository;
    public MealService(MealRespository mealRepository, UserRepository userRepository, DishRepository dishRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.dishRepository = dishRepository;
    }

    public Meal createMeal(MealDTO mealDTO) {

        User user = userRepository.findUserById(mealDTO.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Пользователя с ID " + mealDTO.getUserId() + " не существует");
        }

        List<Dish> dishes = new ArrayList<>();
        List<Long> dishIds = mealDTO.getDishIds();

        for(Long id : dishIds) {

            Optional<Dish> OptionalDish = dishRepository.findById(id);
            Dish dish = OptionalDish.orElseThrow(() -> new IllegalArgumentException("Блюдо с id: " + id + " не найдено"));
            dishes.add(dish);
        }

        Meal meal = new Meal();
        meal.setName(mealDTO.getName());
        meal.setDate(mealDTO.getDate());
        meal.setUser(user);
        meal.setDishes(dishes);

        return mealRepository.save(meal);
    }
}
