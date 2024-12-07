package com.example.farm.Controllers;

import java.io.IOException;
import java.util.List;

import com.example.farm.Models.Farm;
import com.example.farm.Services.FarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/manager/farms")
@PreAuthorize("hasRole('MANAGER') || hasRole('ADMIN')")
public class FarmController {

    @Autowired
    private FarmService service;

    @GetMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) throws IOException {
        try {
            log.debug("Вот мы получаем все культуры");
            List<Farm> listFarm = service.listAll(keyword);
            model.addAttribute("listFarm", listFarm);
            model.addAttribute("keyword", keyword);
            return "farms";
        } catch (Exception e) {
            e.printStackTrace(); // Логируем ошибку
            return "error"; // Вернуть страницу ошибки, если что-то пошло не так
        }
    }

    @GetMapping("/new")
    public String showNewFarmForm(Model model) {
        Farm farm = new Farm();
        model.addAttribute("farm", farm);
        return "new_farm"; // Убедитесь, что этот файл существует
    }

    @PostMapping("/save")
    public String saveFarm(@ModelAttribute("farm") Farm farm) {
        service.save(farm);
        return "redirect:/manager/farms/";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditFarmForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_farm");
        Farm farm = service.get(id);
        mav.addObject("farm", farm);
        return mav; // Убедитесь, что этот файл существует
    }

    @GetMapping("/delete/{id}")
    public String deleteFarm(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return "redirect:/manager/farms/";
    }

    @GetMapping("/sort")
    public String sortFarm(Model model) {
        List<Farm> sortedList = service.sortDate();
        model.addAttribute("listFarm", sortedList);
        return "farms"; // Убедитесь, что этот файл существует
    }

    @GetMapping("/hist")
    public ResponseEntity<byte[]> histogram() throws IOException {
        byte[] imageBytes = service.generateHistogramImage("src/main/resources/templates/histogram.png");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/table")
    public String showTable(Model model) {
        List<Object[]> table = service.tableFarm();
        model.addAttribute("listFarm", table);
        return "table_farm"; // Убедитесь, что этот файл существует
    }


}
