package com.example.farm.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity // Указывает, что этот класс является сущностью JPA
@Getter // Генерирует геттеры для всех полей
@Setter // Генерирует сеттеры для всех полей
@NoArgsConstructor // Генерирует конструктор без параметров
public class User {

    @Id // Указывает, что это поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Указывает, что значение будет генерироваться автоматически (например, автоинкремент)
    private Long id; // Уникальный идентификатор пользователя

    private String username; // Имя пользователя

    private String password; // Пароль пользователя

    @ManyToMany(fetch = FetchType.EAGER) // Указывает, что связь с ролями является многие-ко-многим
    @JoinTable(
            name = "user_roles", // Имя таблицы, которая будет использоваться для хранения связей между пользователями и ролями
            joinColumns = @JoinColumn(name = "user_id"), // Колонка для идентификатора пользователя
            inverseJoinColumns = @JoinColumn(name = "role_id") // Колонка для идентификатора роли
    )
    private Set<Role> roles; // Набор ролей, связанных с пользователем

    // Конструктор для создания пользователя с указанием имени, пароля и ролей
    public User(String username, String password, Set<Role> roles) {
        this.username = username; // Устанавливаем имя пользователя
        this.password = password; // Устанавливаем пароль
        this.roles = roles; // Устанавливаем роли пользователя
    }

    // Геттеры и сеттеры автоматически генерируются Lombok
}