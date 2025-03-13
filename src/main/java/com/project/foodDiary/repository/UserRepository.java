package com.project.foodDiary.repository;

import com.project.foodDiary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findUserById(long id);

    Optional<User> findOptionalById(long id);
    Optional<User> findOptionalByEmail(String email);
}
