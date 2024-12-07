package com.example.farm.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Data // Генерирует геттеры, сеттеры, toString, equals и hashCode для всех полей
@Entity // Указывает, что этот класс является сущностью JPA
@Getter // Генерирует геттеры для всех полей
@Setter // Генерирует сеттеры для всех полей
@Table(name = "blog") // Указывает, что сущность будет сопоставлена с таблицей "blog" в базе данных
public class BlogPost {

    @Id // Указывает, что это поле является первичным ключом
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Указывает, что значение будет генерироваться автоматически (например, автоинкремент)
    private Long id; // Уникальный идентификатор для каждого поста блога

    private String title; // Заголовок поста блога

    @Lob // Используйте аннотацию @Lob для указания, что поле может содержать большие объемы данных
    private String content; // Содержимое поста блога

    private String author; // Автор поста блога

    @Column(name = "date_publisher") // Указывает, что это поле будет сопоставлено с колонкой "date_publisher" в базе данных
    private String datePublisher; // Дата публикации поста блога
}