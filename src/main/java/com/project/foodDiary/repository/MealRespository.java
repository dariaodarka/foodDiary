package com.project.foodDiary.repository;

import com.project.foodDiary.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealRespository extends JpaRepository<Meal, Long> {
    Meal findByName(String name);


    @Query("SELECT m FROM Meal m WHERE m.user.id = :userId AND m.date = :date")
    List<Meal> findMealsByUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT m FROM Meal m where m.user.id = :userId")
    List<Meal> findMealsByUser(@Param("userId") Long userId);

    @Query("SELECT m FROM Meal m where m.user.id = :userId AND m.date >= :startDate AND m.date <= :endDate")
    List<Meal> findMealsByUserAndStartEndDate(@Param("userId") Long userId, @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);
}
