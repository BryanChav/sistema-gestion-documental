package utpl.gestiondocumental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utpl.gestiondocumental.model.Historial;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Long> {
    List<Historial> findByDocumentoId(Long documentoId);
}