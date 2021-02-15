package br.com.felipe.pessoal.sistema.ordem_servico.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/home")
    @ResponseBody
    public String home(){
        return "HOME PAGE";
    }

}
