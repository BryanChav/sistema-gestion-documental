package utpl.gestiondocumental.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import utpl.gestiondocumental.model.Rol;
import utpl.gestiondocumental.model.Usuario;
import utpl.gestiondocumental.repository.RolRepository;
import utpl.gestiondocumental.repository.UsuarioRepository;
@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repo;
    
    @Autowired
    private RolRepository rolRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String roleName = (usuario.getRol() != null && usuario.getRol().getNombreRol() != null)
                ? usuario.getRol().getNombreRol()
                : "USER";

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + roleName))
        );
    }
    // Guardar usuario (ya lo tenías)
    public Usuario saveUsuario(Usuario usuario) {
    	usuario.setEstado(true);
    	// Rol por defecto (USER)
    	Rol rolUser = rolRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Rol USER no existe"));
        usuario.setRol(rolUser);
    	
        return repo.save(usuario);
    }

    // ✅ Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return repo.findAll();
    }

    // ✅ Obtener usuario por ID
    public Usuario obtenerUsuarioPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // ✅ Actualizar usuario
    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuario = obtenerUsuarioPorId(id);

        usuario.setUsername(usuarioActualizado.getUsername());
        usuario.setNombres(usuarioActualizado.getNombres());
        usuario.setApellidos(usuarioActualizado.getApellidos());
        usuario.setCorreo(usuarioActualizado.getCorreo());
        usuario.setTelefono(usuarioActualizado.getTelefono());
        usuario.setCedula(usuarioActualizado.getCedula());
        usuario.setCargo(usuarioActualizado.getCargo());
        usuario.setDepartamento(usuarioActualizado.getDepartamento());

        // ✅ estado
        if (usuarioActualizado.getEstado() != null) {
            usuario.setEstado(usuarioActualizado.getEstado());
        }

        // ✅ rol (no permitir null accidental)
        if (usuarioActualizado.getRol() != null && usuarioActualizado.getRol().getIdRol() != null) {
            Rol rol = rolRepository.findById(usuarioActualizado.getRol().getIdRol())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuario.setRol(rol);
        }

        // ✅ password opcional
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isBlank()) {
            usuario.setPassword(usuarioActualizado.getPassword());
        }

        return repo.save(usuario);
    }


    // ✅ Eliminar usuario
    public void eliminarUsuario(Long id) {
        repo.deleteById(id);
    }
}
