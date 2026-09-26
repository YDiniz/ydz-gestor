package dao;

import db.ConexaoBanco;
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
        String sql = "INSERT INTO pagamento (data, valor, tipo, servico_id) VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setObject(1, pagamento.getData());
            stmt.setDouble(2, pagamento.getValor());
            stmt.setString(3, pagamento.getTipo().name());
            stmt.setInt(4, servicoId);

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
                    Pagamento pagamento = new Pagamento(
                            rs.getInt("id"),
                            rs.getDate("data").toLocalDate(),
                            rs.getDouble("valor"),
                            TipoPagamento.valueOf(rs.getString("tipo"))
                    );
                    pagamentos.add(pagamento);
                }
            }
        }

        return pagamentos;
    }
}