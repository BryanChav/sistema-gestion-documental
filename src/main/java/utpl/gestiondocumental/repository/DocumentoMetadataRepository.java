package utpl.gestiondocumental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import utpl.gestiondocumental.model.DocumentoMetadata;

public interface DocumentoMetadataRepository extends JpaRepository<DocumentoMetadata, Long> {
    List<DocumentoMetadata> findByClaveAndValor(String clave, String valor);
    List<DocumentoMetadata> findByDocumentoId(Long idDocumento);
    @Query("select distinct lower(m.clave) from DocumentoMetadata m order by lower(m.clave)")
    List<String> findDistinctClaves();
}