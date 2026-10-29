package model;

public enum TipoDocumento {
    ORCAMENTO("Orçamento"),
    COMPROVANTE_PAGAMENTO("Comprovante de pagamento");

    private final String descricao;

    TipoDocumento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}