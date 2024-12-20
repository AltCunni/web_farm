package com.example.farm.Services;

import java.awt.*;
import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.IOException;

import java.util.Date;
import java.util.List;


import com.example.farm.Models.Farm;
import com.example.farm.Repositories.FarmRepository;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Сервисный класс для управления фермерскими данными.
 * Этот класс содержит бизнес-логику для работы с сущностью Farm.
 */

@Service
public class FarmService {
    @Autowired
    private FarmRepository repo;
/**
 * Возвращает список всех ферм или список, отфильтрованный по ключевому слову.*/
    public List<Farm> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }
/**
 * Сохраняет или обновляет информацию о ферме.*/
    public void save(Farm farm) {
        repo.save(farm);
    }

    public Farm get(Long Id) {
        return repo.findById(Id).get();
    }


    public void delete(Long Id) {
        repo.deleteById(Id);
    }

    public List<Farm> sortDate() {
        return repo.sort();
    }
    /**
     * Генерирует изображение гистограммы на основе данных о фермах.
     *
     * @param filePath путь к файлу, где будет сохранено изображение
     * @return массив байтов, представляющий изображение гистограммы
     * @throws IOException если произошла ошибка ввода-вывода
     */
    public byte[] generateHistogramImage(String filePath) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Object[]> list = repo.forHist();
        for (Object[] row : list) {
            String key = (String) row[0];
            Long value = (Long) row[1];
            dataset.addValue(value, "Количество", key);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Гистограмма количества зерна по дате",
                "Дата",
                "Количество",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        chart.setBackgroundPaint(Color.decode("#8cbab8"));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, Color.decode("#f6c"));
        plot.setBackgroundAlpha(0.0f);


        CategoryPlot categoryPlot = chart.getCategoryPlot();
        NumberAxis rangeAxis = (NumberAxis) categoryPlot.getRangeAxis();
        rangeAxis.setTickUnit(new NumberTickUnit(1.0));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ChartUtils.writeChartAsPNG(bos, chart, 800, 600);
        return bos.toByteArray();
    }
    /**
     * Возвращает данные для таблицы фермерских хозяйств.
     *
     * @return список объектов, представляющих данные для таблицы
     */

    public List<Object[]> tableFarm () {
        return repo.forTable();
    }

}