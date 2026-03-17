package utpl.gestiondocumental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import utpl.gestiondocumental.model.DocumentoVersion;

public interface DocumentoVersionRepository extends JpaRepository<DocumentoVersion, Long> {

    List<DocumentoVersion> findByDocumentoIdOrderByNumeroVersionDesc(Long documentoId);

    DocumentoVersion findTopByDocumentoIdOrderByNumeroVersionDesc(Long documentoId);

    long countByDocumentoId(Long documentoId);
}