package com.example.farm.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Farm {
    private Long id;
    private String name_zerno;
    private String fio;
    private String data_posev;
    private String data_sbor;
    private String kolvo;

    public Farm() {};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public String toString(){
        return "farm [id=" + id + " name_zerno=" + name_zerno + " fio=" + fio + " data_posev=" + data_posev + " data_sbor=" + data_sbor + " kolvo=" + kolvo + "]";
    }
}
