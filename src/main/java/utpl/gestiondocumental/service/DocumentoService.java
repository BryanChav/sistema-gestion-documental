package utpl.gestiondocumental.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import utpl.gestiondocumental.model.Documento;
import utpl.gestiondocumental.repository.DocumentoRepository;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    // Carpeta donde se guardarán los archivos
    private final String UPLOAD_DIR = "uploads/";

    // Subir documento
    public Documento subirDocumento(MultipartFile archivo, String titulo, String descripcion,
                                    Long usuarioId, Long categoriaId, String origen) throws IOException {
        Documento doc = new Documento();
        doc.setNombre(archivo.getOriginalFilename());
        doc.setTipo(archivo.getContentType());
        doc.setDescripcion(descripcion);
        doc.setUsuarioId(usuarioId);
        doc.setCategoriaId(categoriaId);
        doc.setOrigen(origen);
        doc.setTamaño(archivo.getSize());
        doc.setExtension(getExtension(archivo.getOriginalFilename()));

        // Crear carpeta si no existe
        File folder = new File(UPLOAD_DIR);
        if (!folder.exists()) folder.mkdirs();

        // Guardar archivo en disco
        String ruta = UPLOAD_DIR + archivo.getOriginalFilename();
        archivo.transferTo(new File(ruta));
        doc.setRutaArchivo(ruta);

        doc.setFecha(LocalDateTime.now());
        doc.setEstado("activo");
        doc.setActivo(true);

        return documentoRepository.save(doc);
    }

    // Listar todos los documentos
    public List<Documento> listarDocumentos() {
        return documentoRepository.findAll();
    }

    // Obtener documento por ID
    public Documento obtenerPorId(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con ID: " + id));
    }

    // Actualizar documento
    public Documento actualizarDocumento(Long id, Documento documentoActualizado) {
        Documento doc = obtenerPorId(id);
        doc.setNombre(documentoActualizado.getNombre());
        doc.setTipo(documentoActualizado.getTipo());
        doc.setDescripcion(documentoActualizado.getDescripcion());
        doc.setCategoriaId(documentoActualizado.getCategoriaId());
        doc.setOrigen(documentoActualizado.getOrigen());
        doc.setEstado(documentoActualizado.getEstado());
        doc.setActivo(documentoActualizado.getActivo());
        doc.setFechaModificacion(LocalDateTime.now());
        return documentoRepository.save(doc);
    }

    // Eliminar documento
    public void eliminarDocumento(Long id) {
        documentoRepository.deleteById(id);
    }

    // Listar documentos por usuario
    public List<Documento> listarPorUsuario(Long usuarioId) {
        return documentoRepository.findByUsuarioId(usuarioId);
    }

    // Listar documentos por categoría
    public List<Documento> listarPorCategoria(Long categoriaId) {
        return documentoRepository.findByCategoriaId(categoriaId);
    }

    // Método auxiliar para obtener extensión del archivo
    private String getExtension(String nombreArchivo) {
        int i = nombreArchivo.lastIndexOf('.');
        return i > 0 ? nombreArchivo.substring(i + 1) : "";
    }
}
