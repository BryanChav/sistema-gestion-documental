package utpl.gestiondocumental.service;

import org.springframework.stereotype.Service;

import utpl.gestiondocumental.model.Permiso;
import utpl.gestiondocumental.model.Rol;
import utpl.gestiondocumental.model.RolPermiso;
import utpl.gestiondocumental.repository.RolPermisoRepository;

@Service
public class RolPermisoService {

    private final RolPermisoRepository rolPermisoRepository;
    private final RolService rolService;
    private final PermisoService permisoService;

    public RolPermisoService(RolPermisoRepository rolPermisoRepository, RolService rolService, PermisoService permisoService) {
        this.rolPermisoRepository = rolPermisoRepository;
        this.rolService = rolService;
        this.permisoService = permisoService;
    }

    public RolPermiso asignarPermisoARol(Long idRol, Long idPermiso) {
        Rol rol = rolService.obtenerPorId(idRol);
        Permiso permiso = permisoService.obtenerPorId(idPermiso);

        RolPermiso rp = new RolPermiso();
        rp.setRol(rol);
        rp.setPermiso(permiso);

        return rolPermisoRepository.save(rp);
    }
}