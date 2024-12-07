package com.example.farm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурационный класс для настройки кодировщика паролей.
 * Использует BCrypt для безопасного кодирования паролей пользователей.
 */
@Configuration // Указывает, что этот класс содержит конфигурацию Spring
public class PasswordConfig {

    /**
     * Метод для создания бина PasswordEncoder.
     *
     * @return Объект PasswordEncoder, который будет использоваться для кодирования паролей
     */
    @Bean // Указывает, что метод возвращает бин, который будет управляться контекстом Spring
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Возвращает экземпляр BCryptPasswordEncoder для кодирования паролей
    }
}