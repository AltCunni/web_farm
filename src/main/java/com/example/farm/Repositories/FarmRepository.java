package com.example.farm.Repositories;

import java.util.List;

import com.example.farm.Models.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FarmRepository extends JpaRepository<Farm, Long> {
        @Query("SELECT p FROM Farm p WHERE CONCAT(p.name_zerno, '', p.fio, '', p.data_posev, '', p.data_sbor, '', p.kolvo) LIKE %?1%")
        List<Farm> search(String keyword);

        @Query("SELECT p FROM Farm p ORDER BY p.data_sbor")
        List<Farm> sort();

        @Query("SELECT p.data_sbor, count(distinct p.id) from Farm p group by p.data_sbor")
        List<Object[]> forTable();

        @Query("SELECT p.data_sbor, count(distinct p.id) from Farm p group by p.data_sbor")
        List<Object[]> forHist();
}

