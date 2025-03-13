package com.project.foodDiary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "dishes")
@Getter
@Setter
public class Dish {

    @Id
    @Column (name = "dish_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column (name = "dish_name")
    private String name;
    @Column (name = "dish_calories")
    private int calories;
    @Column (name = "dish_protein")
    private int protein;
    @Column (name = "dish_carbs")
    private int carbs;
    @Column(name = "dish_fats")
    private int fats;

    public Dish(String name, int calories, int protein, int carbs, int fats) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }
    public Dish() {}

    @Override
    public String toString() {
        return "Блюдо: {" +
                "id = " + id +
                ", название = '" + name + '\'' +
                ", калории = " + calories +
                ", белки = " + protein +
                ", углеводы = " + carbs +
                ", жиры = " + fats +
                '}';
    }
}
