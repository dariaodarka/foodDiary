package com.project.foodDiary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private int age;
    @Column (nullable = false)
    private int weight;
    @Column(nullable = false)
    private int height;
    @Column(nullable = false)
    private String aim;
    @Column(nullable = false)
    private int dailyCalories;
    @Column (nullable = false)
    private String password;

    public User(long id, String name, String email, int age, int weight, int height, String aim, int dailyCalories, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.aim = aim;
        this.dailyCalories = dailyCalories;
        this.password = password;
    }
    public User() {}
}
