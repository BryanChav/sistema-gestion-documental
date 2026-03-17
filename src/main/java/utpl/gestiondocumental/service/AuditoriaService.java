package utpl.gestiondocumental.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utpl.gestiondocumental.model.Auditoria;
import utpl.gestiondocumental.model.Documento;
import utpl.gestiondocumental.model.Usuario;
import utpl.gestiondocumental.repository.AuditoriaRepository;
import utpl.gestiondocumental.repository.DocumentoRepository;
import utpl.gestiondocumental.repository.UsuarioRepository;

@Service
public class AuditoriaService {
	@Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

   

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    public List<Auditoria> listar() {
        return auditoriaRepository.findAll();
    }

    public Auditoria registrar(
            String accion,
            String detalle,
            Long usuarioId,
            Long documentoId
    ) {
    	 Documento doc = documentoRepository.findById(documentoId)
                 .orElseThrow(() -> new RuntimeException("Documento no encontrado"));

         Usuario user = usuarioRepository.findById(usuarioId)
                 .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Auditoria a = new Auditoria();
        a.setAccion(accion);
        a.setDetalle(detalle);
        a.setFecha(LocalDateTime.now());
        a.setUsuario(user);
        a.setDocumento(doc);

        return auditoriaRepository.save(a);
    }
}