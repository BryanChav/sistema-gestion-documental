package utpl.gestiondocumental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import utpl.gestiondocumental.model.Documento;

@Repository
public interface DocumentoRepository
extends JpaRepository<Documento, Long>,
        JpaSpecificationExecutor<Documento> {

    List<Documento> findByUsuarioId(Long usuarioId);
    List<Documento> findByCategoriaId(Long categoriaId);
}