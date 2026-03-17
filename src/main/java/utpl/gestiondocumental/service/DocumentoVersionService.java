package utpl.gestiondocumental.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utpl.gestiondocumental.model.Documento;
import utpl.gestiondocumental.model.DocumentoVersion;
import utpl.gestiondocumental.model.Usuario;
import utpl.gestiondocumental.repository.DocumentoVersionRepository;

@Service
public class DocumentoVersionService {

	 @Autowired
	 private DocumentoVersionRepository versionRepo;

	    public DocumentoVersion crearVersion(Documento doc, Usuario usuario, String rutaArchivo) {

	        long count = versionRepo.countByDocumentoId(doc.getId());
	        int next = (int) count + 1;

	        DocumentoVersion v = new DocumentoVersion();
	        v.setDocumento(doc);
	        v.setUsuario(usuario);
	        v.setNumeroVersion(next);
	        v.setRutaArchivo(rutaArchivo);
	        v.setFechaVersion(LocalDateTime.now());

	        return versionRepo.save(v);
	    }
    public DocumentoVersionService(DocumentoVersionRepository documentoVersionRepository) {
        this.versionRepo = documentoVersionRepository;
    }

    public List<DocumentoVersion> listar() {
        return versionRepo.findAll();
    }

    public DocumentoVersion guardar(DocumentoVersion version) {
        return versionRepo.save(version);
    }

    public DocumentoVersion obtenerPorId(Long id) {
        return versionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("DocumentoVersion no encontrada con id: " + id));
    }
}