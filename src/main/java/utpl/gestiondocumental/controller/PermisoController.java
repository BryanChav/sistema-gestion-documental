package utpl.gestiondocumental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utpl.gestiondocumental.model.Permiso;
import utpl.gestiondocumental.service.PermisoService;

@RestController
@RequestMapping("/api/permisos")

public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @GetMapping
    public List<Permiso> listar() {
        return permisoService.listar();
    }

    @PostMapping
    public Permiso crear(@RequestBody Permiso permiso) {
        return permisoService.guardar(permiso);
    }
}