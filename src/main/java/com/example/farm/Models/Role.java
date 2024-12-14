package com.example.farm.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность, представляющая роль пользователя в системе.
 * Эта сущность сопоставляется с таблицей "roles" в базе данных.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    //Конструктор для создания роли с заданным именем.
    public Role(String name) {
        this.name = name;
    }
}
