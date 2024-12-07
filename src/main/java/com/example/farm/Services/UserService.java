package com.example.farm.Services;

import com.example.farm.Models.Role;
import com.example.farm.Models.User;
import com.example.farm.Repositories.RoleRepository;
import com.example.farm.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Сервис для управления пользователями.
 * Предоставляет методы для регистрации пользователей и работы с ролями.
 */
@Service // Указывает, что этот класс является сервисом Spring
public class UserService {

    @Autowired // Внедрение зависимости репозитория для работы с пользователями
    private UserRepository userRepository;

    @Autowired // Внедрение зависимости репозитория для работы с ролями
    private RoleRepository roleRepository; // Предполагается, что у вас есть репозиторий для ролей

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param username Имя пользователя для регистрации
     * @param password Пароль для пользователя
     * @param roleName Имя роли, которую будет иметь пользователь
     * @param passwordEncoder Объект для кодирования паролей
     */
    public void registerUser (String username, String password, String roleName, PasswordEncoder passwordEncoder) {
        // Кодируем пароль перед сохранением
        String encodedPassword = passwordEncoder.encode(password);

        // Получаем роль из базы данных
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            // Если роль не найдена, создаем новую роль
            role = new Role(roleName);
            roleRepository.save(role); // Сохраняем новую роль в базе данных
        }

        // Создаем набор ролей для пользователя
        Set<Role> roles = new HashSet<>();
        roles.add(role); // Добавляем найденную или созданную роль

        // Создаем нового пользователя с заданными данными
        User user = new User(username, encodedPassword, roles);
        userRepository.save(user); // Сохраняем пользователя в базе данных
    }
}
