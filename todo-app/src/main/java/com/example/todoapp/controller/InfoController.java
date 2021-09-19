package com.example.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    //Mapowanie propertisów
    //@Value("${spring.datasource.url}")
    @Autowired
    private DataSourceProperties dataSource;
    @Value("${my.property}")
    private String myProp;

    @GetMapping("/info/url")
    String url() {
        return dataSource.getUrl();
    }

    @GetMapping("/info/prop")
    String myPro() {
        return myProp;
    }

}
