package utpl.gestiondocumental.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Documento {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String tipo;
	    private String titulo;
	    private String nombreArchivo;              // PDF, Word, Imagen
	    private String descripcion;
	    private Long tamaño;          // activo, inactivo, borrador
	    private LocalDateTime fechaRegistro;
	    private LocalDateTime fechaModificacion;
	    private String rutaArchivo;
	    private Boolean activo = true;    // para soft delete
	 // FK hacia Categoria
	    @ManyToOne
	    @JoinColumn(name = "categoria_id")
	    private Categoria categoria;
	    
	 // FK -> estado_documento
	    @ManyToOne
	    @JoinColumn(name = "id_estado")
	    private EstadoDocumento estadoDocumento;
	    
	    // FK -> usuarios
	    @ManyToOne
	    @JoinColumn(name = "usuario_id")
	    private Usuario usuario;
	    
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
		
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		public String getNombreArchivo() {
			return nombreArchivo;
		}
		public void setNombreArchivo(String nombreArchivo) {
			this.nombreArchivo = nombreArchivo;
		}
		public LocalDateTime getFechaRegistro() {
			return fechaRegistro;
		}
		public void setFechaRegistro(LocalDateTime fechaRegistro) {
			this.fechaRegistro = fechaRegistro;
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
		
	
		
		public String getTipo() {
			return tipo;
		}
		public void setTipo(String tipo) {
			this.tipo = tipo;
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
		
		
		public Boolean getActivo() {
			return activo;
		}
		public void setActivo(Boolean activo) {
			this.activo = activo;
		}
		public Categoria getCategoria() {
			return categoria;
		}
		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}
		public EstadoDocumento getEstadoDocumento() {
			return estadoDocumento;
		}
		public void setEstadoDocumento(EstadoDocumento estadoDocumento) {
			this.estadoDocumento = estadoDocumento;
		}
		public Usuario getUsuario() {
			return usuario;
		}
		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}
		
	
	   
	}


