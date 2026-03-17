package utpl.gestiondocumental.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utpl.gestiondocumental.model.DocumentoVersion;
import utpl.gestiondocumental.service.DocumentoVersionService;

@RestController
@RequestMapping("/api/documento-versiones")

public class DocumentoVersionController {

    @Autowired
    private DocumentoVersionService documentoVersionService;

    // Listar todas las versiones
    @GetMapping
    public List<DocumentoVersion> listar() {
        return documentoVersionService.listar();
    }

    // Obtener una versión por ID
    @GetMapping("/{id}")
    public DocumentoVersion obtener(@PathVariable Long id) {
        return documentoVersionService.obtenerPorId(id);
    }

    // Crear una versión (POST)
    @PostMapping
    public DocumentoVersion crear(@RequestBody DocumentoVersion version) {
        return documentoVersionService.guardar(version);
    }
}
