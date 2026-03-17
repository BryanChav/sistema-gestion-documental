package utpl.gestiondocumental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import utpl.gestiondocumental.model.Permiso;
import utpl.gestiondocumental.model.Rol;
import utpl.gestiondocumental.model.RolPermiso;

public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {
	boolean existsByRolAndPermiso(Rol rol, Permiso permiso);	
}
