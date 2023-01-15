package com.javacource.se.receiptapp.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping
    public String start (){
        return "Приложение запущено";
    }
    String nameStudent = "Юлия";
    String nameProject = "Книга рецептов";
    String data = "15.01.2023";
    String description = "Самые популярные и вкусные рецепты на каждый день";

    @GetMapping("path/to/secondTask")
    public String secondTask(@RequestParam String nameStudent,String nameProject,String data,String description) {
        return "Имя ученика :" + nameStudent +".Название вашего пректа :" + nameProject + ".Дата создания проекта :"
                + data + ".Описание проекта :" + description ;

    }
}
