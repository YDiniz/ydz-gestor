package dao;

import db.ConexaoBanco;
import model.Servico;
import model.Escola;
import model.StatusServico;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServicoDAO {

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private DocumentoDAO documentoDAO = new DocumentoDAO();
    private PagamentoDAO pagamentoDAO = new PagamentoDAO();

    public int salvar(Servico servico) throws SQLException {
        String sql = "INSERT INTO servico (nome, tipo_obra, status, escola_id) VALUES (?, ?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getTipoObra());
            stmt.setString(3, servico.getStatus().name());
            stmt.setInt(4, servico.getEscola().getId());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        throw new SQLException("Falha ao obter o ID gerado para o serviço.");
    }


    public List<Servico> listarTodos() throws SQLException {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT s.id, s.nome, s.tipo_obra, s.status, " +
                "e.id AS escola_id, e.nome AS escola_nome, e.endereco AS escola_endereco, e.contato AS escola_contato " +
                "FROM servico s " +
                "JOIN escola e ON s.escola_id = e.id";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Escola escola = new Escola(
                        rs.getInt("escola_id"),
                        rs.getString("escola_nome"),
                        rs.getString("escola_endereco"),
                        rs.getString("escola_contato")
                );

                Servico servico = new Servico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tipo_obra"),
                        escola
                );

                int servicoId = servico.getId();

                for (var funcionario : funcionarioDAO.listarPorServico(servicoId)) {
                    servico.adicionarFuncionario(funcionario);
                }

                for (var documento : documentoDAO.listarPorServico(servicoId)) {
                    servico.adicionarDocumento(documento);
                }

                for (var pagamento : pagamentoDAO.listarPorServico(servicoId)) {
                    servico.adicionarPagamento(pagamento);
                }

                servicos.add(servico);
            }
        }
        return servicos;
    }

    public List<Servico> listarPorStatus(StatusServico status) throws SQLException {
        List<Servico> todos = listarTodos();
        List<Servico> filtrados = new ArrayList<>();

        for (Servico s : todos) {
            if (s.getStatus() == status) {
                filtrados.add(s);
            }
        }

        return filtrados;
    }
}