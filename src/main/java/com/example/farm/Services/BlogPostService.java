package com.example.farm.Services;

import com.example.farm.Models.BlogPost;
import com.example.farm.Repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для управления записями блога.
 * Обеспечивает бизнес-логику и взаимодействие с репозиторием BlogPostRepository.
 */
@Service // Указывает, что этот класс является сервисом Spring
public class BlogPostService {

    @Autowired // Внедрение зависимости репозитория для работы с записями блога
    private BlogPostRepository repository;

    /**
     * Метод для получения списка записей блога с возможностью фильтрации по ключевому слову и сортировки.
     *
     * @param keyword Ключевое слово для поиска в заголовках записей
     * @param sort Объект Sort для указания порядка сортировки
     * @return Список записей блога, соответствующих критериям поиска
     */
    public List<BlogPost> listAll(String keyword, Sort sort) {
        if (keyword != null) {
            return repository.findByTitleContaining(keyword, sort); // Поиск по заголовкам с сортировкой
        }
        return repository.findAll(sort); // Возвращает все записи блога с сортировкой
    }

    /**
     * Метод для получения списка записей блога с возможностью фильтрации по ключевому слову.
     *
     * @param keyword Ключевое слово для поиска в заголовках записей
     * @return Список записей блога, соответствующих критериям поиска
     */
    public List<BlogPost> listAll(String keyword) {
        if (keyword != null) {
            return repository.findByTitleContaining(keyword); // Поиск по заголовкам без сортировки
        }
        return repository.findAll(); // Возвращает все записи блога без сортировки
    }

    /**
     * Метод для сохранения записи блога.
     *
     * @param blogPost Объект BlogPost, который нужно сохранить
     */
    public void save(BlogPost blogPost) {
        repository.save(blogPost); // Сохраняем запись блога через репозиторий
    }

    /**
     * Метод для получения записи блога по идентификатору.
     *
     * @param id Идентификатор записи блога
     * @return Объект BlogPost, если найден, иначе null
     */
    public BlogPost get(Long id) {
        return repository.findById(id).orElse(null); // Получаем запись блога по идентификатору
    }

    /**
     * Метод для удаления записи блога по идентификатору.
     *
     * @param id Идентификатор записи блога, которую нужно удалить
     */
    public void delete(Long id) {
        repository.deleteById(id); // Удаляем запись блога через репозиторий
    }
}
