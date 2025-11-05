package utpl.gestiondocumental.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import utpl.gestiondocumental.model.Documento;
import utpl.gestiondocumental.service.DocumentoService;

@RestController
@RequestMapping("/api/documentos")
public class DocumentosController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Subir un documento",
        description = "Permite subir un archivo con su título, descripción y otros metadatos."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento subido correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en la subida del documento")
    })
    public ResponseEntity<Documento> subirDocumento(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("titulo") String titulo,
            @RequestParam("descripcion") String descripcion,
            @RequestParam(value = "usuarioId", required = false) Long usuarioId,
            @RequestParam(value = "categoriaId", required = false) Long categoriaId,
            @RequestParam(value = "origen", required = false) String origen
    ) throws IOException {
        Documento doc = documentoService.subirDocumento(archivo, titulo, descripcion, usuarioId, categoriaId, origen);
        return ResponseEntity.ok(doc);
    }

    @GetMapping
    public List<Documento> listarDocumentos() {
        return documentoService.listarDocumentos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> obtenerDocumento(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> actualizarDocumento(@PathVariable Long id, @RequestBody Documento doc) {
        return ResponseEntity.ok(documentoService.actualizarDocumento(id, doc));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDocumento(@PathVariable Long id) {
        documentoService.eliminarDocumento(id);
        return ResponseEntity.noContent().build();
    }
}
