package com.example.farm.Repositories;

import java.util.List;

import com.example.farm.Models.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Репозиторий для управления сущностями Farm.
 * Этот интерфейс расширяет JpaRepository, предоставляя CRUD-операции
 * и пользовательские методы запросов для сущности Farm.
 */
public interface FarmRepository extends JpaRepository<Farm, Long> {
        //Ищет сущности {@link Farm} на основе предоставленного ключевого слова.
        @Query("SELECT p FROM Farm p WHERE CONCAT(p.name_zerno, '', p.fio, '', p.data_posev, '', p.data_sbor, '', p.kolvo) LIKE %?1%")
        List<Farm> search(String keyword);
        //Извлекает все сущности Farm, отсортированные по дате сбора урожая (data_sbor).
        @Query("SELECT p FROM Farm p ORDER BY p.data_sbor")
        List<Farm> sort();
        //Извлекает список дат сбора урожая (data_sbor) вместе с количеством уникальных сущностей Farm для каждой даты сбора.
        @Query("SELECT p.data_sbor, count(distinct p.id) from Farm p group by p.data_sbor")
        List<Object[]> forTable();
        //Этот метод аналогичен forTable() и может использоваться для анализа исторических данных.
        @Query("SELECT p.data_sbor, count(distinct p.id) from Farm p group by p.data_sbor")
        List<Object[]> forHist();
}

