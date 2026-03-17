package utpl.gestiondocumental.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import utpl.gestiondocumental.model.Auditoria;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long> {
	List<Auditoria> findByUsuario_Id(Long idUsuario);
	List<Auditoria> findByDocumento_Id(Long idDocumento);
}