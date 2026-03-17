package utpl.gestiondocumental.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DocumentoMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_metadata")
    private Long idMetadata;

    private String clave;
    private String valor;

    @ManyToOne
    @JoinColumn(name = "idDocumento")
    private Documento documento;

    public Long getIdMetadata() { return idMetadata; }
    public void setIdMetadata(Long idMetadata) { this.idMetadata = idMetadata; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public Documento getDocumento() { return documento; }
    public void setDocumento(Documento documento) { this.documento = documento; }
}