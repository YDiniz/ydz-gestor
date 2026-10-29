package model;

public enum TipoPagamento {
    ADIANTAMENTO("Adiantamento"),
    PAGAMENTO("Pagamento");

    private final String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}