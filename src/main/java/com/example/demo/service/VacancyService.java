package com.example.demo.service;

import com.example.demo.dto.VacancyDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VacancyService {
    private final Map<String, VacancyDto> vacancyDtoMap = new HashMap<>();

    @PostConstruct
    public void init(){
        VacancyDto  juniorMaDeveloper = new VacancyDto();
        juniorMaDeveloper.setId("1");
        juniorMaDeveloper.setTitle(" Junior Dev at MA");
        juniorMaDeveloper.setShortDescription("Java Core is required");
        vacancyDtoMap.put("1",juniorMaDeveloper);

        VacancyDto  juniorNetflixDeveloper = new VacancyDto();
        juniorNetflixDeveloper.setId("2");
        juniorNetflixDeveloper.setTitle(" Junior Dev at Netflix");
        juniorNetflixDeveloper.setShortDescription("Java Core is required");
        vacancyDtoMap.put("2",juniorNetflixDeveloper);

        VacancyDto  middleMaDeveloper = new VacancyDto();
        middleMaDeveloper.setId("3");
        middleMaDeveloper.setTitle(" Middle Dev at MA");
        middleMaDeveloper.setShortDescription("Java Core is required");
        vacancyDtoMap.put("3",middleMaDeveloper);


    }
    public List<VacancyDto> getJuniorVacancies(){
        return vacancyDtoMap.values().stream().
                filter( v -> v.getTitle().toLowerCase().contains("junior")).toList();
    }

    public VacancyDto get(String id) {
        return vacancyDtoMap.get(id);
    }
}
