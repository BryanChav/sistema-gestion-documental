package utpl.gestiondocumental.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import utpl.gestiondocumental.dto.DocumentoSearchRequest;
import utpl.gestiondocumental.dto.DocumentoSearchResponse;
import utpl.gestiondocumental.dto.MetadataDTO;
import utpl.gestiondocumental.model.Auditoria;
import utpl.gestiondocumental.model.Categoria;
import utpl.gestiondocumental.model.Documento;
import utpl.gestiondocumental.model.DocumentoMetadata;
import utpl.gestiondocumental.model.EstadoDocumento;
import utpl.gestiondocumental.model.Usuario;
import utpl.gestiondocumental.repository.AuditoriaRepository;
import utpl.gestiondocumental.repository.CategoriaRepository;
import utpl.gestiondocumental.repository.DocumentoMetadataRepository;
import utpl.gestiondocumental.repository.DocumentoRepository;
import utpl.gestiondocumental.repository.EstadoDocumentoRepository;
import utpl.gestiondocumental.repository.UsuarioRepository;

@Service
public class DocumentoService {
	
	 @Autowired
	    private DocumentoMetadataRepository documentoMetadataRepository;
	
	@Autowired
	private AuditoriaRepository auditoriaRepository;


	 @Autowired
	    private EstadoDocumentoRepository estadoRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    public DocumentoService(DocumentoRepository documentoRepository,
            UsuarioRepository usuarioRepository) {
this.documentoRepository = documentoRepository;
this.usuarioRepository = usuarioRepository;
}
    @Autowired
    private DocumentoVersionService documentoVersionService;

  
    // 🔧 Ajusta si tus nombres cambian
    private static final String ESTADO_ARCHIVADO = "ARCHIVADO";
    private static final String ESTADO_ACTIVO = "ACTIVO";
    // Carpeta donde se guardarán los archivos
    private final String UPLOAD_DIR = "C:/DocumentosSistema/";

    // Subir documento
    public Documento subirDocumento(
            MultipartFile archivo,
            String titulo,
            String descripcion,
            Long idCategoria,
            Long idEstado,
            Long usuarioId,
            List<MetadataDTO> metadatos
    ) throws IOException {

        EstadoDocumento estadoDoc = estadoRepository.findById(idEstado)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Documento doc = new Documento();

        // ✅ Campo lógico del documento
        doc.setTitulo(titulo);

        // ✅ Nombre real del archivo subido
        doc.setNombreArchivo(archivo.getOriginalFilename());

        doc.setTipo(archivo.getContentType());
        doc.setDescripcion(descripcion);
        doc.setUsuario(usuario);
        doc.setCategoria(categoria);
        doc.setEstadoDocumento(estadoDoc);
        doc.setTamaño(archivo.getSize());

        // ✅ fechas correctas según tu modelo
        doc.setFechaRegistro(LocalDateTime.now());
        doc.setFechaModificacion(LocalDateTime.now());
        doc.setActivo(true);

        // Crear carpeta si no existe
        File folder = new File(UPLOAD_DIR);
        if (!folder.exists()) folder.mkdirs();

        // Guardar archivo en disco (mejor: evitar colisiones con timestamp)
        String nombreSeguro = System.currentTimeMillis() + "-" + archivo.getOriginalFilename();
        String ruta = UPLOAD_DIR + nombreSeguro;

        archivo.transferTo(new File(ruta));
        doc.setRutaArchivo(ruta);

        Documento guardado = documentoRepository.save(doc);

        // ✅ guardar metadatos solo si vienen
        if (metadatos != null && !metadatos.isEmpty()) {
            List<DocumentoMetadata> entities = new ArrayList<>();

            for (MetadataDTO m : metadatos) {
                if (m == null) continue;
                if (m.getClave() == null || m.getClave().isBlank()) continue;
                if (m.getValor() == null || m.getValor().isBlank()) continue;

                DocumentoMetadata md = new DocumentoMetadata();
                md.setDocumento(guardado);
                md.setClave(m.getClave().trim());
                md.setValor(m.getValor().trim());
                entities.add(md);
            }

            if (!entities.isEmpty()) {
                documentoMetadataRepository.saveAll(entities);
            }
        }

        // ✅ auditoría
        Auditoria au = new Auditoria();
        au.setAccion("CREAR");
        au.setFecha(LocalDateTime.now());
        au.setDetalle("Se creó el documento: " + guardado.getTitulo());
        au.setDocumento(guardado);
        au.setUsuario(usuario);
        auditoriaRepository.save(au);

        // ✅ versión 1 (si tu servicio lo requiere)
        documentoVersionService.crearVersion(guardado, usuario, guardado.getRutaArchivo());

        return guardado;
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

        doc.setTitulo(documentoActualizado.getTitulo());
        doc.setDescripcion(documentoActualizado.getDescripcion());
        doc.setActivo(documentoActualizado.getActivo());

        // si quieres permitir cambiar estado/categoria:
        if (documentoActualizado.getCategoria() != null) doc.setCategoria(documentoActualizado.getCategoria());
        if (documentoActualizado.getEstadoDocumento() != null) doc.setEstadoDocumento(documentoActualizado.getEstadoDocumento());

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
    
    public Page<DocumentoSearchResponse> search(DocumentoSearchRequest req, Pageable pageable) {
        Specification<Documento> spec = Specification.where(null);

        if (req.getQ() != null && !req.getQ().isBlank()) {
            String like = "%" + req.getQ().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("titulo")), like),          // ✅ CAMBIO
                    cb.like(cb.lower(root.get("nombreArchivo")), like),   // ✅ opcional (recomendado)
                    cb.like(cb.lower(root.get("descripcion")), like)
            ));
        }

        if (req.getCategoriaId() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("categoria").get("id"), req.getCategoriaId()));
        }

        if (req.getEstadoId() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("estadoDocumento").get("idEstado"), req.getEstadoId()));
        }

        if (req.getMetadata() != null && !req.getMetadata().isEmpty()) {
            boolean orMode = "OR".equalsIgnoreCase(req.getMetadataMode());
            Specification<Documento> metaSpec = null;

            for (MetadataDTO m : req.getMetadata()) {
                if (m == null || m.getClave() == null || m.getClave().isBlank() ||
                        m.getValor() == null || m.getValor().isBlank()) {
                    continue;
                }

                String clave = m.getClave().toLowerCase();
                String valor = m.getValor().toLowerCase();

                Specification<Documento> one = (root, query, cb) -> {
                    Subquery<Long> sq = query.subquery(Long.class);
                    Root<DocumentoMetadata> md = sq.from(DocumentoMetadata.class);
                    sq.select(cb.literal(1L));
                    sq.where(
                            cb.equal(md.get("documento"), root),
                            cb.equal(cb.lower(md.get("clave")), clave),
                            cb.like(cb.lower(md.get("valor")), "%" + valor + "%")
                    );
                    return cb.exists(sq);
                };

                metaSpec = (metaSpec == null) ? one : (orMode ? metaSpec.or(one) : metaSpec.and(one));
            }

            if (metaSpec != null) spec = spec.and(metaSpec);
        }

        Page<Documento> page = documentoRepository.findAll(spec, pageable);

        // ✅ MAPEAR Documento -> DocumentoSearchResponse + metadatos
        return page.map(doc -> {
        	 DocumentoSearchResponse dto = new DocumentoSearchResponse();
        	    dto.setId(doc.getId());
        	    dto.setTitulo(doc.getTitulo());                 // ✅ antes: getNombre()
        	    dto.setNombreArchivo(doc.getNombreArchivo());   // ✅ opcional si tu DTO lo tiene
        	    dto.setDescripcion(doc.getDescripcion());
        	    dto.setTipo(doc.getTipo());
        	    dto.setTamaño(doc.getTamaño());
        	    dto.setFechaRegistro(doc.getFechaRegistro());   // ✅ antes: getFecha()
        	    dto.setFechaModificacion(doc.getFechaModificacion());
        	    dto.setCategoria(doc.getCategoria());
        	    dto.setEstadoDocumento(doc.getEstadoDocumento());

        	    List<MetadataDTO> metas = documentoMetadataRepository
        	            .findByDocumentoId(doc.getId())
        	            .stream()
        	            .map(x -> new MetadataDTO(x.getClave(), x.getValor()))
        	            .toList();

        	    dto.setMetadatos(metas);
        	    return dto;
        	});
    }
    
    public Documento archivarDocumento(Long id) {
        Documento doc = documentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        EstadoDocumento estado = estadoRepository
            .findByNombreEstadoIgnoreCase(ESTADO_ARCHIVADO)
            .orElseThrow(() -> new RuntimeException("Estado ARCHIVADO no existe"));

        doc.setEstadoDocumento(estado);

        // ✅ OPCIONAL: si quieres ocultarlo completamente en listados
        // doc.setActivo(false);

        return documentoRepository.save(doc);
    }

    public Documento restaurarDocumento(Long id) {
        Documento doc = documentoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

        EstadoDocumento estado = estadoRepository
            .findByNombreEstadoIgnoreCase(ESTADO_ACTIVO)
            .orElseThrow(() -> new RuntimeException("Estado ACTIVO no existe"));

        doc.setEstadoDocumento(estado);

        // ✅ OPCIONAL: si usaste activo=false al archivar
        // doc.setActivo(true);

        return documentoRepository.save(doc);
    }
    public Documento actualizarArchivoDocumento(Long id, MultipartFile archivo, Long idUsuario) throws IOException {
        Documento doc = obtenerPorId(id);

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear carpeta si no existe
        File folder = new File(UPLOAD_DIR);
        if (!folder.exists()) folder.mkdirs();

        // Guardar archivo en disco
        String nombreSeguro = System.currentTimeMillis() + "-" + archivo.getOriginalFilename();
        String rutaNueva = UPLOAD_DIR + nombreSeguro;

        archivo.transferTo(new File(rutaNueva));

        // ✅ crear nueva versión
        documentoVersionService.crearVersion(doc, usuario, rutaNueva);

        // ✅ actualizar documento "actual"
        doc.setNombreArchivo(archivo.getOriginalFilename());
        doc.setTipo(archivo.getContentType());
        doc.setTamaño(archivo.getSize());
        doc.setRutaArchivo(rutaNueva);
        doc.setFechaModificacion(LocalDateTime.now());

        Documento actualizado = documentoRepository.save(doc);

        // auditoría
        Auditoria au = new Auditoria();
        au.setAccion("ACTUALIZAR_ARCHIVO");
        au.setFecha(LocalDateTime.now());
        au.setDetalle("Se actualizó el archivo del documento: " + actualizado.getTitulo());
        au.setDocumento(actualizado);
        au.setUsuario(usuario);
        auditoriaRepository.save(au);

        return actualizado;
    }

}
