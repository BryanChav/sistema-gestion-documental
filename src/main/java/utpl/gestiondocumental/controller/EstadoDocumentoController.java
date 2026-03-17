package utpl.gestiondocumental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utpl.gestiondocumental.model.EstadoDocumento;
import utpl.gestiondocumental.service.EstadoDocumentoService;

@RestController
@RequestMapping("/api/estado-documento")

public class EstadoDocumentoController {

    @Autowired
    private EstadoDocumentoService estadoDocumentoService;

    @GetMapping
    public List<EstadoDocumento> listar() {
        return estadoDocumentoService.listar();
    }

    @PostMapping
    public EstadoDocumento crear(@RequestBody EstadoDocumento estado) {
        return estadoDocumentoService.guardar(estado);
    }
}
