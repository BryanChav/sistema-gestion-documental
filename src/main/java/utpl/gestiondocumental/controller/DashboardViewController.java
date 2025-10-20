package utpl.gestiondocumental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardViewController {

	@GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Devuelve dashboard.html directamente
    }
}