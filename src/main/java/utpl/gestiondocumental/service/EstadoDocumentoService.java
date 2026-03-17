package utpl.gestiondocumental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import utpl.gestiondocumental.model.EstadoDocumento;
import utpl.gestiondocumental.repository.EstadoDocumentoRepository;

@Service
public class EstadoDocumentoService {

    private final EstadoDocumentoRepository estadoDocumentoRepository;

    public EstadoDocumentoService(EstadoDocumentoRepository estadoDocumentoRepository) {
        this.estadoDocumentoRepository = estadoDocumentoRepository;
    }

    public List<EstadoDocumento> listar() {
        return estadoDocumentoRepository.findAll();
    }

    public EstadoDocumento guardar(EstadoDocumento estado) {
        return estadoDocumentoRepository.save(estado);
    }

    public EstadoDocumento obtenerPorId(Long id) {
        return estadoDocumentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoDocumento no encontrado con id: " + id));
    }
}