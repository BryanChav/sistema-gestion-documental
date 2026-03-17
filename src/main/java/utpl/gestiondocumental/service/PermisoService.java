package utpl.gestiondocumental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import utpl.gestiondocumental.model.Permiso;
import utpl.gestiondocumental.repository.PermisoRepository;

@Service
public class PermisoService {

    private final PermisoRepository permisoRepository;

    public PermisoService(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    public List<Permiso> listar() {
        return permisoRepository.findAll();
    }

    public Permiso guardar(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public Permiso obtenerPorId(Long id) {
        return permisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permiso no encontrado con id: " + id));
    }
}