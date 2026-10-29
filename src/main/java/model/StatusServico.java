package model;

public enum StatusServico {
    ORCAMENTO("Orçamento"),
    EM_ANDAMENTO("Em andamento"),
    CONCLUIDO("Concluído");

    private final String descricao;

    StatusServico(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}