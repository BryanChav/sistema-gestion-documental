package utpl.gestiondocumental.dto;

public class MetadataDTO {
    private String clave;
    private String valor;
    public MetadataDTO() {}

    public MetadataDTO(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
