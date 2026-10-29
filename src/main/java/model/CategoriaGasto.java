package model;

public enum CategoriaGasto {
    FUNCIONARIO("Funcionário"),
    MATERIAL("Material");

    private final String descricao;

    CategoriaGasto(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}