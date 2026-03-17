package utpl.gestiondocumental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import utpl.gestiondocumental.model.EstadoDocumento;

public interface EstadoDocumentoRepository extends JpaRepository<EstadoDocumento, Long> {
	
	    Optional<EstadoDocumento> findByNombreEstadoIgnoreCase(String nombreEstado);


}