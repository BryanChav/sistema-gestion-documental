package utpl.gestiondocumental.dto;

import java.time.LocalDate;
import java.util.List;

public class DocumentoSearchRequest {

    private String q;              // texto libre
    private Long categoriaId;
    private Long estadoId;

    private LocalDate fechaFrom;
    private LocalDate fechaTo;

    // filtros por metadatos
    private List<MetadataDTO> metadata;
    private String metadataMode; // AND / OR

    // getters y setters
    public String getQ() { return q; }
    public void setQ(String q) { this.q = q; }

    public Long getCategoriaId() { return categoriaId; }
    public void setCategoriaId(Long categoriaId) { this.categoriaId = categoriaId; }

    public Long getEstadoId() { return estadoId; }
    public void setEstadoId(Long estadoId) { this.estadoId = estadoId; }

    public LocalDate getFechaFrom() { return fechaFrom; }
    public void setFechaFrom(LocalDate fechaFrom) { this.fechaFrom = fechaFrom; }

    public LocalDate getFechaTo() { return fechaTo; }
    public void setFechaTo(LocalDate fechaTo) { this.fechaTo = fechaTo; }

    public List<MetadataDTO> getMetadata() { return metadata; }
    public void setMetadata(List<MetadataDTO> metadata) { this.metadata = metadata; }

    public String getMetadataMode() { return metadataMode; }
    public void setMetadataMode(String metadataMode) { this.metadataMode = metadataMode; }
}