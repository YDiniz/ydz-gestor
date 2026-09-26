package service;

import dao.EscolaDAO;
import model.Escola;

import java.sql.SQLException;
import java.util.List;

public class EscolaService {

    private EscolaDAO escolaDAO = new EscolaDAO();

    public void cadastrarEscola(String nome, String endereco, String contato) throws SQLException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da escola é obrigatório.");
        }

        Escola escola = new Escola(0, nome, endereco, contato);
        escolaDAO.salvar(escola);
    }

    public List<Escola> listarEscolas() throws SQLException {
        return escolaDAO.listarTodas();
    }

    public void atualizarEscola(Escola escola) throws SQLException {
        if (escola.getNome() == null || escola.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome da escola é obrigatório.");
        }
        escolaDAO.atualizar(escola);
    }

    public void removerEscola(int id) throws SQLException {
        escolaDAO.deletar(id);
    }
}