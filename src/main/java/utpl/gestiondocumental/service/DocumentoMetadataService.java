package utpl.gestiondocumental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import utpl.gestiondocumental.model.Documento;
import utpl.gestiondocumental.model.DocumentoMetadata;
import utpl.gestiondocumental.repository.DocumentoMetadataRepository;

@Service
public class DocumentoMetadataService {

    private final DocumentoMetadataRepository documentoMetadataRepository;

    public DocumentoMetadataService(DocumentoMetadataRepository documentoMetadataRepository) {
        this.documentoMetadataRepository = documentoMetadataRepository;
    }

    public DocumentoMetadata guardar(DocumentoMetadata metadata) {
        return documentoMetadataRepository.save(metadata);
    }

    public List<DocumentoMetadata> listarPorDocumento(Long idDocumento) {
        return documentoMetadataRepository.findByDocumentoId(idDocumento);
    }

    public List<DocumentoMetadata> buscarPorClaveValor(String clave, String valor) {
        return documentoMetadataRepository.findByClaveAndValor(clave, valor);
    }

    public DocumentoMetadata crearMetadata(Documento documento, String clave, String valor) {
        DocumentoMetadata md = new DocumentoMetadata();
        md.setDocumento(documento);
        md.setClave(clave);
        md.setValor(valor);
        return documentoMetadataRepository.save(md);
    }
}