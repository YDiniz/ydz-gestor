package dao;

import db.ConexaoBanco;
import model.CategoriaGasto;
import model.DirecaoPagamento;
import model.Pagamento;
import model.TipoPagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    public void salvar(Pagamento pagamento, int servicoId) throws SQLException {
        String sql = "INSERT INTO pagamento (data, valor, tipo, direcao, categoria, descricao, servico_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setObject(1, pagamento.getData());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setString(3, pagamento.getTipo().name());
            stmt.setString(4, pagamento.getDirecao().name());
            stmt.setString(5, pagamento.getCategoria() != null ? pagamento.getCategoria().name() : null);
            stmt.setString(6, pagamento.getDescricao());
            stmt.setInt(7, servicoId);

            stmt.executeUpdate();
        }
    }

    public List<Pagamento> listarPorServico(int servicoId) throws SQLException {
        List<Pagamento> pagamentos = new ArrayList<>();
        String sql = "SELECT * FROM pagamento WHERE servico_id = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, servicoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String categoria = rs.getString("categoria");

                    Pagamento pagamento = new Pagamento(
                            rs.getInt("id"),
                            rs.getDate("data").toLocalDate(),
                            rs.getDouble("valor"),
                            TipoPagamento.valueOf(rs.getString("tipo")),
                            DirecaoPagamento.valueOf(rs.getString("direcao")),
                            categoria != null ? CategoriaGasto.valueOf(categoria) : null,
                            rs.getString("descricao")
                    );
                    pagamentos.add(pagamento);
                }
            }
        }

        return pagamentos;
    }

    public void remover(int pagamentoId) throws SQLException {
        String sql = "DELETE FROM pagamento WHERE id = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pagamentoId);
            stmt.executeUpdate();
        }
    }

    public void atualizar(Pagamento pagamento) throws SQLException {
        String sql = "UPDATE pagamento SET data = ?, valor = ?, tipo = ?, direcao = ?, "
                + "categoria = ?, descricao = ? WHERE id = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setObject(1, pagamento.getData());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setString(3, pagamento.getTipo().name());
            stmt.setString(4, pagamento.getDirecao().name());
            stmt.setString(5, pagamento.getCategoria() != null ? pagamento.getCategoria().name() : null);
            stmt.setString(6, pagamento.getDescricao());
            stmt.setInt(7, pagamento.getId());

            stmt.executeUpdate();
        }
    }

}