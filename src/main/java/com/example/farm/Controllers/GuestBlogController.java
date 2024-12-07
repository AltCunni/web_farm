package com.example.farm.Controllers;

import com.example.farm.Models.BlogPost;
import com.example.farm.Services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Контроллер для обработки запросов к блогу для гостей.
 * Позволяет гостям просматривать записи блога.
 */
@Controller
@RequestMapping("/guest/blog") // Указывает, что все методы в этом контроллере будут обрабатывать запросы по пути /guest/blog
@PreAuthorize("hasRole('GUEST') || hasRole('ADMIN') || hasRole('MANAGER')") // Ограничивает доступ к методам контроллера только для пользователей с ролью GUEST или ADMIN
public class GuestBlogController {

    // Внедрение зависимости сервиса для работы с записями блога
    @Autowired
    private BlogPostService service;

    /**
     * Метод для отображения главной страницы блога для гостей.
     *
     * @param model Модель, используемая для передачи данных в представление
     * @return Имя шаблона, который будет отображен (guest_blog)
     */
    @GetMapping("/") // Указывает, что этот метод будет обрабатывать GET-запросы по пути /guest/blog/
    public String viewGuestBlogPage(Model model) {
        // Получаем все записи блога из сервиса
        List<BlogPost> listPosts = service.listAll(null); // Передаем null, чтобы получить все записи без фильтрации по ключевому слову
        // Добавляем список записей в модель, чтобы он был доступен в представлении
        model.addAttribute("listPosts", listPosts);
        // Возвращаем имя шаблона, который будет отображен
        return "guest_blog";
    }
}
