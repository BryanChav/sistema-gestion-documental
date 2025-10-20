package utpl.gestiondocumental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import utpl.gestiondocumental.model.Historial;
import utpl.gestiondocumental.service.HistorialService;

@RestController
@RequestMapping("/api/historial")
public class HistorialController {

    @Autowired
    private HistorialService historialService;

    @PostMapping
    public ResponseEntity<Historial> registrarAccion(
            @RequestParam Long documentoId,
            @RequestParam Long usuarioId,
            @RequestParam String accion
    ) {
        Historial historial = historialService.registrarAccion(documentoId, usuarioId, accion);
        return ResponseEntity.ok(historial);
    }

    @GetMapping
    public List<Historial> listarHistorial() {
        return historialService.listarHistorial();
    }

    @GetMapping("/documento/{documentoId}")
    public List<Historial> listarPorDocumento(@PathVariable Long documentoId) {
        return historialService.listarPorDocumento(documentoId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Long id) {
        historialService.eliminarHistorial(id);
        return ResponseEntity.noContent().build();
    }
}
