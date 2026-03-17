package utpl.gestiondocumental.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import utpl.gestiondocumental.model.Permiso;
import utpl.gestiondocumental.model.Rol;
import utpl.gestiondocumental.model.RolPermiso;
import utpl.gestiondocumental.repository.PermisoRepository;
import utpl.gestiondocumental.repository.RolPermisoRepository;
import utpl.gestiondocumental.repository.RolRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RolRepository rolRepo,
            PermisoRepository permisoRepo,
            RolPermisoRepository rolPermisoRepo
    ) {
        return args -> {

        	// 1) Crear permisos si no existen
            Permiso ver = permisoRepo.findByNombrePermiso("VER").orElseGet(() -> {
                Permiso p = new Permiso();
                p.setNombrePermiso("VER");
                p.setDescripcion("Ver información");
                return permisoRepo.save(p);
            });

            Permiso crear = permisoRepo.findByNombrePermiso("CREAR").orElseGet(() -> {
                Permiso p = new Permiso();
                p.setNombrePermiso("CREAR");
                p.setDescripcion("Crear registros");
                return permisoRepo.save(p);
            });

            Permiso editar = permisoRepo.findByNombrePermiso("EDITAR").orElseGet(() -> {
                Permiso p = new Permiso();
                p.setNombrePermiso("EDITAR");
                p.setDescripcion("Editar registros");
                return permisoRepo.save(p);
            });

            Permiso eliminar = permisoRepo.findByNombrePermiso("ELIMINAR").orElseGet(() -> {
                Permiso p = new Permiso();
                p.setNombrePermiso("ELIMINAR");
                p.setDescripcion("Eliminar registros");
                return permisoRepo.save(p);
            });

            // 2) Crear roles si no existen
            Rol user = rolRepo.findByNombreRol("USER").orElseGet(() -> {
                Rol r = new Rol();
                r.setNombreRol("USER"); // ajusta si tu campo en Rol tiene otro nombre
                return rolRepo.save(r);
            });

            Rol admin = rolRepo.findByNombreRol("ADMIN").orElseGet(() -> {
                Rol r = new Rol();
                r.setNombreRol("ADMIN"); // ajusta si tu campo en Rol tiene otro nombre
                return rolRepo.save(r);
            });

            // 3) Relacionar Rol-Permiso
            if (!rolPermisoRepo.existsByRolAndPermiso(user, ver)) {
                RolPermiso rp = new RolPermiso();
                rp.setRol(user);
                rp.setPermiso(ver);
                rolPermisoRepo.save(rp);
            }

            // Admin: todos
            if (!rolPermisoRepo.existsByRolAndPermiso(admin, ver)) {
                RolPermiso rp1 = new RolPermiso(); rp1.setRol(admin); rp1.setPermiso(ver);
                RolPermiso rp2 = new RolPermiso(); rp2.setRol(admin); rp2.setPermiso(crear);
                RolPermiso rp3 = new RolPermiso(); rp3.setRol(admin); rp3.setPermiso(editar);
                RolPermiso rp4 = new RolPermiso(); rp4.setRol(admin); rp4.setPermiso(eliminar);

                rolPermisoRepo.save(rp1);
                rolPermisoRepo.save(rp2);
                rolPermisoRepo.save(rp3);
                rolPermisoRepo.save(rp4);
            }
        };
    }
}


        
    
