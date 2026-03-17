package utpl.gestiondocumental.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import utpl.gestiondocumental.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
	 Optional<Rol> findByNombreRol(String nombre);
}