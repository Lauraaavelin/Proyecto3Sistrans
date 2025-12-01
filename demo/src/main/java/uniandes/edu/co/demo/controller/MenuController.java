package uniandes.edu.co.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/Menu")
    public String mostrarMenu() {
        return "Menu";   // Se refiere al archivo Menu.html
    }
}
