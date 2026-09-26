package model;

import java.time.LocalDate;

public class Pagamento {
    private int id;
    private LocalDate data;
    private double valor;
    private TipoPagamento tipo;

    public Pagamento(int id, LocalDate data, double valor, TipoPagamento tipo) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
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