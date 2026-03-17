package utpl.gestiondocumental.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import utpl.gestiondocumental.model.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
	 Optional<Permiso> findByNombrePermiso(String nombrePermiso);
}