package utpl.gestiondocumental.dto;

import java.time.LocalDateTime;
import java.util.List;

import utpl.gestiondocumental.model.Categoria;
import utpl.gestiondocumental.model.EstadoDocumento;

public class DocumentoSearchResponse {

    private Long id;

    // ✅ nombre lógico
    private String titulo;

    // ✅ nombre físico del archivo
    private String nombreArchivo;

    private String descripcion;
    private String tipo;
    private Long tamaño;

    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaModificacion;

    // ✅ mejor tipado que Object
    private Categoria categoria;
    private EstadoDocumento estadoDocumento;

    private List<MetadataDTO> metadatos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getNombreArchivo() { return nombreArchivo; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Long getTamaño() { return tamaño; }
    public void setTamaño(Long tamaño) { this.tamaño = tamaño; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime fechaModificacion) { this.fechaModificacion = fechaModificacion; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public EstadoDocumento getEstadoDocumento() { return estadoDocumento; }
    public void setEstadoDocumento(EstadoDocumento estadoDocumento) { this.estadoDocumento = estadoDocumento; }

    public List<MetadataDTO> getMetadatos() { return metadatos; }
    public void setMetadatos(List<MetadataDTO> metadatos) { this.metadatos = metadatos; }
}