package model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private LocalDate data;
    private double valor;
    private TipoPagamento tipo;
    private DirecaoPagamento direcao;
    private CategoriaGasto categoria;
    private String descricao;

    public DirecaoPagamento getDirecao() {
        return direcao;
    }

    public void setDirecao(DirecaoPagamento direcao) {
        this.direcao = direcao;
    }

    public CategoriaGasto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaGasto categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pagamento(int id, LocalDate data, double valor, TipoPagamento tipo,
                     DirecaoPagamento direcao, CategoriaGasto categoria, String descricao) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
        this.direcao = direcao;
        this.categoria = categoria;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoPagamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoPagamento tipo) {
        this.tipo = tipo;
    }
}