package service;

import dao.DocumentoDAO;
import model.Documento;
import model.TipoDocumento;

import java.sql.SQLException;
import java.util.List;

public class DocumentoService {

    private DocumentoDAO documentoDAO = new DocumentoDAO();

    public void anexarDocumento(TipoDocumento tipo, String caminhoArquivo, int servicoId) throws SQLException {
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo do documento é obrigatório.");
        }
        if (caminhoArquivo == null || caminhoArquivo.isBlank()) {
            throw new IllegalArgumentException("É necessário selecionar um arquivo.");
        }

        Documento documento = new Documento(0, tipo, caminhoArquivo);
        documentoDAO.salvar(documento, servicoId);
    }

    public List<Documento> listarDocumentosDoServico(int servicoId) throws SQLException {
        return documentoDAO.listarPorServico(servicoId);
    }
}