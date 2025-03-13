package com.project.foodDiary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "meals")
@Getter
@Setter
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private int id;
    @Column(name = "meal_name")
    private String name;
    @Column(name = "meal_date")
    private LocalDate date;
    @ManyToOne (cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany (cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "meal_dishes",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes = new ArrayList<>();

    public Meal(String name, LocalDate date, User user, List<Dish> dishes) {
        this.name = name;
        this.date = date;
        this.user = user;
        this.dishes = dishes;
    }

    public Meal() {
    }
}
