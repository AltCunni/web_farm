package com.example.farm.Repositories;

import com.example.farm.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

// Репозиторий для работы с сущностью Role.
// * Интерфейс наследует JpaRepository, что позволяет использовать стандартные методы
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name); //Метод для поиска роли по имени.
}
