package utpl.gestiondocumental.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utpl.gestiondocumental.model.Historial;
import utpl.gestiondocumental.repository.HistorialRepository;

@Service
public class HistorialService {

    @Autowired
    private HistorialRepository historialRepository;

    // ➕ Registrar una acción en el historial
    public Historial registrarAccion(Long documentoId, Long usuarioId, String accion) {
        Historial historial = new Historial();
        historial.setDocumentoId(documentoId);
        historial.setUsuarioId(usuarioId);
        historial.setAccion(accion);
        historial.setFecha(LocalDateTime.now());

        return historialRepository.save(historial);
    }

    // 📋 Listar todo el historial
    public List<Historial> listarHistorial() {
        return historialRepository.findAll();
    }

    // 🔍 Listar historial por documento
    public List<Historial> listarPorDocumento(Long documentoId) {
        return historialRepository.findByDocumentoId(documentoId);
    }

    // 🗑️ Eliminar un registro específico
    public void eliminarHistorial(Long id) {
        historialRepository.deleteById(id);
    }
}
