package model;

public class Escola {
    private int id;
    private String nome;
    private String endereco;
    private String contato;
    private String nomeContato;

    public Escola(int id, String nome, String endereco, String contato, String nomeContato) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.contato = contato;
        this.nomeContato = nomeContato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }
}