package utpl.gestiondocumental.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Documento {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String nombre;
	    private String tipo;              // PDF, Word, Imagen
	    private String origen;            // INTERNO / EXTERNO
	    private String descripcion;
	    private Long tamaño;
	    private String extension;
	    private String estado;            // activo, inactivo, borrador
	    private LocalDateTime fecha;
	    private LocalDateTime fechaModificacion;
	    private String rutaArchivo;
	    private Long usuarioId;           // creador o recepcionista
	    private Long categoriaId;         // opcional
	    private Boolean activo = true;    // para soft delete
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
		}
		public String getOrigen() {
			return origen;
		}
		public void setOrigen(String origen) {
			this.origen = origen;
		}
		public String getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public Long getTamaño() {
			return tamaño;
		}
		public void setTamaño(Long tamaño) {
			this.tamaño = tamaño;
		}
		public String getExtension() {
			return extension;
		}
		public void setExtension(String extension) {
			this.extension = extension;
		}
		public String getEstado() {
			return estado;
		}
		public void setEstado(String estado) {
			this.estado = estado;
		}
		public LocalDateTime getFecha() {
			return fecha;
		}
		public void setFecha(LocalDateTime fecha) {
			this.fecha = fecha;
		}
		public LocalDateTime getFechaModificacion() {
			return fechaModificacion;
		}
		public void setFechaModificacion(LocalDateTime fechaModificacion) {
			this.fechaModificacion = fechaModificacion;
		}
		public String getRutaArchivo() {
			return rutaArchivo;
		}
		public void setRutaArchivo(String rutaArchivo) {
			this.rutaArchivo = rutaArchivo;
		}
		public Long getUsuarioId() {
			return usuarioId;
		}
		public void setUsuarioId(Long usuarioId) {
			this.usuarioId = usuarioId;
		}
		public Long getCategoriaId() {
			return categoriaId;
		}
		public void setCategoriaId(Long categoriaId) {
			this.categoriaId = categoriaId;
		}
		public Boolean getActivo() {
			return activo;
		}
		public void setActivo(Boolean activo) {
			this.activo = activo;
		}

	   
	}


