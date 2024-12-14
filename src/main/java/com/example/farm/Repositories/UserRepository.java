package com.example.farm.Repositories;

import com.example.farm.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

//Репозиторий для работы с сущностью User.
// * Этот интерфейс предоставляет методы для выполнения операций CRUD и поиска пользователей.
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}