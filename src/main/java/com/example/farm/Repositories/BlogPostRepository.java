package com.example.farm.Repositories;

import com.example.farm.Models.BlogPost;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностями BlogPost.
 * Обеспечивает доступ к данным блога через интерфейс JpaRepository.
 */
@Repository // Указывает, что этот интерфейс является репозиторием Spring
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    /**
     * Метод для поиска записей блога по заголовку с учетом ключевого слова и сортировки.
     *
     * @param keyword Ключевое слово для поиска в заголовках
     * @param sort Объект Sort для указания порядка сортировки
     * @return Список записей блога, содержащих указанное ключевое слово в заголовке
     */
    List<BlogPost> findByTitleContaining(String keyword, Sort sort);

    /**
     * Метод для поиска записей блога по заголовку с учетом ключевого слова без сортировки.
     *
     * @param keyword Ключевое слово для поиска в заголовках
     * @return Список записей блога, содержащих указанное ключевое слово в заголовке
     */
    List<BlogPost> findByTitleContaining(String keyword);
}