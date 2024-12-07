package com.example.farm.Controllers;

import com.example.farm.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Контроллер для управления аутентификацией пользователей.
 * Обеспечивает регистрацию и вход пользователей в систему.
 */
@Controller // Указывает, что этот класс является контроллером Spring MVC
public class AuthController {

    @Autowired // Внедрение зависимости сервиса для работы с пользователями
    private UserService userService;

    @Autowired // Внедрение зависимости для шифрования паролей
    private PasswordEncoder passwordEncoder;

    @Value("${isAdminLaunch:false}") // Чтение значения из конфигурации, определяет, запущен ли админский режим
    private boolean isAdminLaunch;

    /**
     * Метод для отображения формы регистрации пользователя.
     *
     * @param model Модель, используемая для передачи данных в представление
     * @return Имя шаблона для отображения формы регистрации
     */
    @GetMapping("/register") // Указывает, что этот метод будет обрабатывать GET-запросы по пути /register
    public String showRegistrationForm(Model model) {
        model.addAttribute("isAdminLaunch", isAdminLaunch); // Добавляем информацию о том, запущен ли админский режим
        return "register"; // Возвращаем имя шаблона для отображения формы регистрации
    }

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param username Имя пользователя
     * @param password Пароль пользователя
     * @param role Роль пользователя
     * @param model Модель, используемая для передачи данных в представление
     * @return Имя шаблона для перенаправления на страницу входа
     */
    @PostMapping("/register") // Указывает, что этот метод будет обрабатывать POST-запросы по пути /register
    public String registerUser (String username, String password, String role, Model model) {
        // Если роль не указана, устанавливаем значение по умолчанию
        if (role == null || role.isEmpty()) {
            role = "GUEST"; // Устанавливаем роль по умолчанию для новых пользователей
        }

        // Регистрируем пользователя с указанными данными
        userService.registerUser (username, password, role, passwordEncoder);
        model.addAttribute("message", "Регистрация прошла успешно! Вы можете войти."); // Добавляем сообщение об успешной регистрации
        return "login"; // Перенаправление на страницу входа
    }

    /**
     * Метод для отображения формы входа пользователя.
     *
     * @return Имя шаблона для отображения формы входа
     */
    @GetMapping("/login") // Указывает, что этот метод будет обрабатывать GET-запросы по пути /login
    public String showLoginForm() {
        return "login"; // Возвращаем имя шаблона для отображения формы входа
    }

    /**
     * Метод для отображения меню пользователя.
     *
     * @return Имя шаблона для отображения меню
     */
    @GetMapping("/menu") // Указывает, что этот метод будет обрабатывать GET-запросы по пути /menu
    public String showMenu() {
        // Здесь можно добавить логику для отображения интерфейса в зависимости от роли пользователя
        return "menu"; // Возвращаем имя шаблона для отображения меню
    }
}