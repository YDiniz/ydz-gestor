package model;

public enum DirecaoPagamento {
    RECEBIDO("Recebido"),
    REALIZADO("Realizado");

    private final String descricao;

    DirecaoPagamento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}