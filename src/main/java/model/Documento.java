package model;

public class Documento {
    private int id;
    private TipoDocumento tipo;
    private String caminhoArquivo;

    public Documento(int id, TipoDocumento tipo, String caminhoArquivo) {
        this.id = id;
        this.tipo = tipo;
        this.caminhoArquivo = caminhoArquivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
}