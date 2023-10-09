package br.com.joseduarte.todolist.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firstroute")
public class MinhaPrimeiraController {
    @GetMapping("/firstmethod")
    public String firstMessage (){
        return "Worked";
    }
}
