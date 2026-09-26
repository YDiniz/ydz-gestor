package dao;

import db.ConexaoBanco;
import model.Escola;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EscolaDAO {

    public void salvar (Escola escola) throws SQLException {
        String sql = "INSERT INTO escola (nome, endereco, contato) VALUES (?, ?, ?,)";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, escola.getNome());
            stmt.setString(2, escola.getEndereco());
            stmt.setString(3, escola.getContato());

            stmt.executeUpdate();
        }
    }

    public List<Escola> listarTodas() throws SQLException {
        List<Escola> escolas = new ArrayList<>();
        String sql = "SELECT * FROM escola";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Escola escola = new Escola(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("contato")
                );
                escolas.add(escola);
            }
        }

        return escolas;
    }

    public void atualizar(Escola escola) throws SQLException {
        String sql = "UPDATE escola SET nome = ?, endereco = ?, contato = ? WHERE id = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, escola.getNome());
            stmt.setString(2, escola.getEndereco());
            stmt.setString(3, escola.getContato());
            stmt.setInt(4, escola.getId());

            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM escola WHERE id = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}
