package utpl.gestiondocumental.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import utpl.gestiondocumental.dto.DocumentoSearchRequest;
import utpl.gestiondocumental.dto.DocumentoSearchResponse;
import utpl.gestiondocumental.dto.MetadataDTO;
import utpl.gestiondocumental.model.Documento;
import utpl.gestiondocumental.model.DocumentoVersion;
import utpl.gestiondocumental.repository.DocumentoVersionRepository;
import utpl.gestiondocumental.service.DocumentoService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/documentos")
public class DocumentosController {

    @Autowired
    private DocumentoVersionRepository versionRepo;

    @Autowired
    private DocumentoService documentoService;

    private final ObjectMapper mapper = new ObjectMapper();

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
            @RequestParam Long idCategoria,
            @RequestParam Long idEstado,
            @RequestParam Long idUsuario,
            @RequestParam(required = false) String metadatos
    ) throws IOException {

        List<MetadataDTO> listaMetadatos = null;
        if (metadatos != null && !metadatos.isBlank()) {
            listaMetadatos = mapper.readValue(
                    metadatos,
                    new TypeReference<List<MetadataDTO>>() {}
            );
        }

        Documento doc = documentoService.subirDocumento(
                archivo, titulo, descripcion,
                idCategoria, idEstado, idUsuario,
                listaMetadatos
        );

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

    // ✅ Listar versiones de un documento
    @GetMapping("/{id}/versiones")
    public List<DocumentoVersion> listarVersiones(@PathVariable Long id) {
        return versionRepo.findByDocumentoIdOrderByNumeroVersionDesc(id);
    }

    // ✅ Descargar documento actual
    @GetMapping("/descargar/{id}")
    public ResponseEntity<Resource> descargarDocumento(@PathVariable Long id) throws Exception {
        Documento doc = documentoService.obtenerPorId(id);
        Path rutaArchivo = Paths.get(doc.getRutaArchivo());
        Resource resource = new UrlResource(rutaArchivo.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getTipo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getNombreArchivo() + "\"")
                .body(resource);
    }

    // ✅ Descargar una versión específica
    @GetMapping("/versiones/{idVersion}/descargar")
    public ResponseEntity<Resource> descargarVersion(@PathVariable Long idVersion) throws Exception {
        DocumentoVersion v = versionRepo.findById(idVersion)
                .orElseThrow(() -> new RuntimeException("Versión no encontrada"));

        Path rutaArchivo = Paths.get(v.getRutaArchivo());
        Resource resource = new UrlResource(rutaArchivo.toUri());

        // ✅ Detectar el tipo real (PDF, imagen, etc.)
        String contentType = java.nio.file.Files.probeContentType(rutaArchivo);
        if (contentType == null) contentType = "application/octet-stream";

        // ✅ Nombre real del archivo desde el path
        String nombre = rutaArchivo.getFileName().toString();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                // ✅ inline para previsualizar (iframe/img)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + nombre + "\"")
                .body(resource);
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
    @PutMapping("/{id}/archivar")
    public ResponseEntity<Documento> archivar(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.archivarDocumento(id));
    }

    @PutMapping("/{id}/restaurar")
    public ResponseEntity<Documento> restaurar(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.restaurarDocumento(id));
    }


    @PostMapping("/search")
    public Page<DocumentoSearchResponse> search(
            @RequestBody DocumentoSearchRequest req,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return documentoService.search(req, pageable);
    }
    @PutMapping(value = "/{id}/archivo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Documento> actualizarArchivo(
            @PathVariable Long id,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam Long idUsuario
    ) throws IOException {
        return ResponseEntity.ok(documentoService.actualizarArchivoDocumento(id, archivo, idUsuario));
    }

}
