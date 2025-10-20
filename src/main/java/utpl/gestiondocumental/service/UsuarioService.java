package utpl.gestiondocumental.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import utpl.gestiondocumental.model.Usuario;
import utpl.gestiondocumental.repository.UsuarioRepository;
@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(usuario.getUsername(), usuario.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol())));
    }

    // Guardar usuario (ya lo tenías)
    public Usuario saveUsuario(Usuario usuario) {
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
        usuario.setRol(usuarioActualizado.getRol());
        if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().isEmpty()) {
            usuario.setPassword(usuarioActualizado.getPassword());
        }
        return repo.save(usuario);
    }

    // ✅ Eliminar usuario
    public void eliminarUsuario(Long id) {
        repo.deleteById(id);
    }
}
