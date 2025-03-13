package com.project.foodDiary.repository;

import com.project.foodDiary.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Dish findByName(String name);
    Dish findById(long id);

    @Query ("select d from Dish d")
    List<Dish> findAll();
}
