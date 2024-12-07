package com.example.farm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурационный класс для настройки безопасности приложения.
 * Использует Spring Security для управления аутентификацией и авторизацией.
 */
@Configuration // Указывает, что этот класс содержит конфигурацию Spring
@EnableWebSecurity // Включает функциональность безопасности Spring
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService; // Сервис для загрузки пользовательских данных

    @Autowired
    private PasswordEncoder passwordEncoder; // Инжектируем PasswordEncoder для кодирования паролей

    /**
     * Метод для настройки цепочки фильтров безопасности.
     *
     * @param http Объект HttpSecurity для настройки безопасности
     * @return Настроенная цепочка фильтров безопасности
     * @throws Exception Если возникла ошибка при настройке
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/register", "/login").permitAll() // Разрешаем доступ к страницам регистрации и входа
                        .anyRequest().authenticated()) // Все остальные запросы требуют аутентификации
                .formLogin(form -> form
                        .loginPage("/login") // Указываем пользовательскую страницу входа
                        .defaultSuccessUrl("/menu", true) // Указываем страницу, на которую перенаправлять после успешного входа
                        .permitAll()) // Разрешаем доступ к странице входа для всех
                .logout(logout -> logout.permitAll()); // Разрешаем доступ к выходу для всех

        return http.build(); // Возвращаем настроенную цепочку фильтров
    }

    /**
     * Метод для глобальной настройки аутентификации.
     *
     * @param auth Объект AuthenticationManagerBuilder для настройки аутентификации
     * @throws Exception Если возникла ошибка при настройке
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService) // Указываем сервис для загрузки пользовательских данных
                .passwordEncoder(passwordEncoder); // Указываем кодировщик паролей
    }
}