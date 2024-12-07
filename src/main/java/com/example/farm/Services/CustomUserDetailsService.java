package com.example.farm.Services;

import com.example.farm.Models.Role;
import com.example.farm.Models.User; // Импорт модели пользователя
import com.example.farm.Repositories.UserRepository; // Импорт репозитория пользователя
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Сервис для загрузки пользовательских данных для аутентификации.
 * Реализует интерфейс UserDetailsService от Spring Security.
 */
@Service // Указывает, что этот класс является сервисом Spring
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired // Внедрение зависимости репозитория для работы с пользователями
    private UserRepository userRepository;

    /**
     * Метод для загрузки пользовательских данных по имени пользователя.
     *
     * @param username Имя пользователя, по которому нужно найти пользователя
     * @return Объект UserDetails, содержащий информацию о пользователе и его ролях
     * @throws UsernameNotFoundException Если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username); // Поиск пользователя по имени
        if (user == null) {
            throw new UsernameNotFoundException("User  not found"); // Исключение, если пользователь не найден
        }

        // Создание набора авторизаций для пользователя на основе его ролей
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName())); // Добавление роли в набор авторизаций
        }

        // Возвращение объекта UserDetails с информацией о пользователе и его авторизациях
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities // Набор авторизаций
        );
    }
}