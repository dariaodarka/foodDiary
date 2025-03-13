package com.project.foodDiary.service;

import com.project.foodDiary.model.dto.DishDTO;
import com.project.foodDiary.entity.Dish;
import com.project.foodDiary.model.reportObjects.DishReportObject;
import com.project.foodDiary.repository.DishRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {
    private final DishRepository dishRepository;
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish createDish(DishDTO dishDTO) {
        if(dishRepository.findByName(dishDTO.getName()) != null) {
            throw new IllegalArgumentException("Блюдо с названием " + dishDTO.getName() + " уже добавлено. " +
                    "Пожалуйста добавьте блюдо с другим названием.");
        }
        Dish dish = new Dish();
        dish.setName(dishDTO.getName().toLowerCase());
        dish.setCalories(dishDTO.getCalories());
        dish.setCarbs(dishDTO.getCarbs());
        dish.setProtein(dishDTO.getProtein());
        dish.setFats(dishDTO.getFats());
        return dishRepository.save(dish);
    }

    public List<Dish> findAll() {
        List<Dish> dishes = dishRepository.findAll();

        return dishes;
    }

}
