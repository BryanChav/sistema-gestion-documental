package utpl.gestiondocumental.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import utpl.gestiondocumental.model.DocumentoMetadata;
import utpl.gestiondocumental.repository.DocumentoMetadataRepository;
import utpl.gestiondocumental.service.DocumentoMetadataService;

@RestController
@RequestMapping("/api/documento-metadata")

public class DocumentoMetadataController {
	 @Autowired
	private  DocumentoMetadataRepository repo;
    @Autowired
    private DocumentoMetadataService documentoMetadataService;

    // 🔹 Listar todos los metadatos de un documento
    @GetMapping("/documento/{idDocumento}")
    public List<DocumentoMetadata> listarPorDocumento(@PathVariable Long idDocumento) {
        return documentoMetadataService.listarPorDocumento(idDocumento);
    }

    // 🔹 Buscar documentos por metadata (clave/valor)
    // Ejemplo: /api/documento-metadata/buscar?clave=origen&valor=Interno
    @GetMapping("/buscar")
    public List<DocumentoMetadata> buscar(
            @RequestParam String clave,
            @RequestParam String valor) {
        return documentoMetadataService.buscarPorClaveValor(clave, valor);
    }
    @GetMapping("/keys")
    public List<String> getKeys() {
      return repo.findDistinctClaves();
    }
    
}
