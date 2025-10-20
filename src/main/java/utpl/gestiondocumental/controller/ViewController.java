package utpl.gestiondocumental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("layout")
    public String layout() {
        return "layout"; // templates/login.html
    }
}
