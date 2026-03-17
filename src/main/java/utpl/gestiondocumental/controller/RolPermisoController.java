package utpl.gestiondocumental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utpl.gestiondocumental.model.RolPermiso;
import utpl.gestiondocumental.service.RolPermisoService;

@RestController
@RequestMapping("/api/rol-permisos")
public class RolPermisoController {

    @Autowired
    private RolPermisoService rolPermisoService;

    // POST /api/rol-permisos/asignar?idRol=1&idPermiso=2
    @PostMapping("/asignar")
    public RolPermiso asignar(
            @RequestParam Long idRol,
            @RequestParam Long idPermiso) {
        return rolPermisoService.asignarPermisoARol(idRol, idPermiso);
    }
}