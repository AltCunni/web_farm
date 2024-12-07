package com.example.farm.Controllers;

import com.example.farm.Models.BlogPost;
import com.example.farm.Services.BlogPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Контроллер для управления записями блога в административной панели.
 * Обеспечивает CRUD операции для записей блога.
 */
@Slf4j // Логирование
@Controller // Указывает, что этот класс является контроллером Spring MVC
@RequestMapping("/admin/blog") // Определяет базовый путь для всех методов в этом контроллере
@PreAuthorize("hasRole('ADMIN')") // Ограничивает доступ к методам контроллера только для пользователей с ролью ADMIN
public class BlogPostController {

    @Autowired // Внедрение зависимости сервиса для работы с записями блога
    private BlogPostService service;

    /**
     * Метод для отображения главной страницы блога в административной панели.
     *
     * @param model   Модель, используемая для передачи данных в представление
     * @param keyword Ключевое слово для поиска записей блога
     * @return Имя шаблона, который будет отображен (blogs-admin)
     */
    @GetMapping("/") // Указывает, что этот метод будет обрабатывать GET-запросы по пути /admin/blog/
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        // Получаем все записи блога с возможностью фильтрации по ключевому слову и сортировки по дате публикации (по убыванию)
        List<BlogPost> listPosts = service.listAll(keyword, Sort.by(Sort.Direction.DESC, "datePublisher"));
        model.addAttribute("listPosts", listPosts); // Добавляем список записей в модель
        model.addAttribute("keyword", keyword); // Добавляем ключевое слово в модель для отображения в представлении
        return "blogs-admin"; // Возвращаем имя шаблона для отображения
    }

    /**
     * Метод для отображения формы создания новой записи блога.
     *
     * @param model Модель, используемая для передачи данных в представление
     * @return Имя шаблона для создания новой записи блога
     */
    @GetMapping("/new") // Указывает, что этот метод будет обрабатывать GET-запросы по пути /admin/blog/new
    public String showNewBlogPostForm(Model model) {
        BlogPost blogPost = new BlogPost(); // Создаем новый объект BlogPost
        model.addAttribute("blogPost", blogPost); // Добавляем объект в модель
        return "new_blog_post"; // Возвращаем имя шаблона для отображения формы создания новой записи
    }

    /**
     * Метод для сохранения новой записи блога.
     *
     * @param blogPost Объект BlogPost, который нужно сохранить
     * @return Редирект на главную страницу блога
     */
    @PostMapping("/save") // Указывает, что этот метод будет обрабатывать POST-запросы по пути /admin/blog/save
    public String saveBlogPost(@ModelAttribute("blogPost") BlogPost blogPost) {
        service.save(blogPost); // Сохраняем запись блога через сервис
        return "redirect:/admin/blog/"; // Редирект на главную страницу блога
    }

    /**
     * Метод для отображения формы редактирования существующей записи блога.
     *
     * @param id Идентификатор записи блога, которую нужно отредактировать
     * @return ModelAndView для отображения формы редактирования
     */
    @GetMapping("/edit/{id}") // Указывает, что этот метод будет обрабатывать GET-запросы по пути /admin/blog/edit/{id}
    public ModelAndView showEditBlogPostForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_blog_post"); // Создаем объект ModelAndView с указанием шаблона
        BlogPost blogPost = service.get(id); // Получаем запись блога по идентификатору
        mav.addObject("blogPost", blogPost); // Добавляем запись блога в ModelAndView

        return mav; // Возвращаем ModelAndView для отображения формы редактирования
    }

    /**
     * Метод для удаления записи блога.
     *
     * @param id Идентификатор записи блога, которую нужно удалить
     * @return Редирект на главную страницу блога
     */
    @GetMapping("/delete/{id}")
    // Указывает, что этот метод будет обрабатывать GET-запросы по пути /admin/blog/delete/{id}
    public String deleteBlogPost(@PathVariable(name = "id") Long id) {
        service.delete(id); // Удаляем запись блога через сервис
        return "redirect:/admin/blog/"; // Редирект на главную страницу блога
    }

    /**
     * Метод для сортировки записей блога по дате публикации.
     *
     * @param model Модель, используемая для передачи данных в представление
     * @return Имя шаблона, который будет отображен (blogs-admin)
     */
    @GetMapping("/sort") // Указывает, что этот метод будет обрабатывать GET-запросы по пути /admin/blog/sort
    public String sortBlogPosts(Model model) {
        // Получаем все записи блога, отсортированные по дате публикации (по возрастанию)
        List<BlogPost> sortedList = service.listAll(null, Sort.by(Sort.Direction.ASC, "datePublisher"));
        model.addAttribute("listPosts", sortedList); // Добавляем отсортированный список в модель
        return "blogs-admin"; // Возвращаем имя шаблона для отображения
    }
}