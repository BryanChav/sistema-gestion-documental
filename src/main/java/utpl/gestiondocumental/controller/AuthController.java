package utpl.gestiondocumental.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import utpl.gestiondocumental.dto.LoginRequest;
import utpl.gestiondocumental.model.Rol;
import utpl.gestiondocumental.model.Usuario;
import utpl.gestiondocumental.repository.RolRepository;
import utpl.gestiondocumental.repository.UsuarioRepository;
import utpl.gestiondocumental.security.CustomUserDetailsService;
import utpl.gestiondocumental.security.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository userRepository;
    
    @Autowired
    private RolRepository rolRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    // --------------------------
    // 1️⃣ Registro de usuario
    // --------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario user) {
    	if (userRepository.existsByUsername(user.getUsername())) {
            // Devolver JSON con la propiedad "message"
            Map<String, String> response = new HashMap<>();
            response.put("message", "El nombre de usuario ya existe");
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }
    	
    	Rol rolUser = rolRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Rol USER no existe"));

        user.setRol(rolUser);                 // ✅ evita NULL
        user.setEstado(true);                 // ✅ estado por defecto
    	
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Usuario nuevoUsuario = userRepository.save(user);
        nuevoUsuario.setPassword(null); // no devolver contraseña

        return ResponseEntity.ok(nuevoUsuario);
    }
       

    // --------------------------
    // 2️⃣ Login → devuelve JWT
    // --------------------------
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) throws Exception {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Usuario o contraseña incorrectos");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    // --------------------------
    // 3️⃣ Información del usuario autenticado
    // --------------------------
    @GetMapping("/me")
    public Usuario me(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName()).orElse(null);
    }
}