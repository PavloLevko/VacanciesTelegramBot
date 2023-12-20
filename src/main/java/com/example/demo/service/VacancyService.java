package com.example.demo.service;

import com.example.demo.dto.VacancyDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VacancyService {
    @Autowired
    VacanciesReaderService vacanciesReaderService;
    private final Map<String, VacancyDto> vacancyDtoMap = new HashMap<>();

    @PostConstruct
    public void init(){
     List<VacancyDto> list = vacanciesReaderService.getVacanciesFromFile("vacancies.csv");
     for( VacancyDto vacancyDto : list) {
         vacancyDtoMap.put(vacancyDto.getId(), vacancyDto);
     }

    }
    public List<VacancyDto> getJuniorVacancies(){
        return vacancyDtoMap.values().stream().
                filter( v -> v.getTitle().toLowerCase().contains("junior")).toList();
    }
    public List<VacancyDto> getMiddleVacancies(){
        return vacancyDtoMap.values().stream().
                filter( v -> v.getTitle().toLowerCase().contains("middle")).toList();
    }
    public List<VacancyDto> getSeniorVacancies(){
        return vacancyDtoMap.values().stream().
                filter( v -> v.getTitle().toLowerCase().contains("senior")).toList();
    }

    public VacancyDto get(String id) {
        return vacancyDtoMap.get(id);
    }
}
