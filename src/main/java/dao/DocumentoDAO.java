package dao;

import db.ConexaoBanco;
import model.Documento;
import model.TipoDocumento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDAO {

    public void salvar(Documento documento, int servicoId) throws SQLException {
        String sql = "INSERT INTO documento (tipo, caminho_arquivo, servico_id) VALUES (?, ?, ?)";

        try (Connection conexao = ConexaoBanco.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, documento.getTipo().name());
            stmt.setString(2, documento.getCaminhoArquivo());
            stmt.setInt(3, servicoId);

            stmt.executeUpdate();

        }

    }

    public List<Documento> listarPorServico(int servicoId) throws SQLException {
        List<Documento> documentos = new ArrayList<>();
        String sql = "SELECT * FROM documento WHERE servico_id = ?";

        try (Connection conexao = ConexaoBanco.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, servicoId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Documento documento = new Documento(
                            rs.getInt("id"),
                            TipoDocumento.valueOf(rs.getString("tipo")),
                            rs.getString("caminho_arquivo")
                    );
                    documentos.add(documento);
                }
            }
        }

        return documentos;
    }
}