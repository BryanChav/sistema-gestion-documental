package utpl.gestiondocumental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import utpl.gestiondocumental.model.Auditoria;
import utpl.gestiondocumental.service.AuditoriaService;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

	  @Autowired
	    private AuditoriaService auditoriaService;

	    // 🔹 Listar toda la auditoría
	    @GetMapping
	    public List<Auditoria> listar() {
	        return auditoriaService.listar();
	    }

	   
	}