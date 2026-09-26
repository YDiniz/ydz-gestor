package service;

import dao.ServicoDAO;
import model.Escola;
import model.Servico;

import java.sql.SQLException;
import java.util.List;

public class ServicoService {

    private ServicoDAO servicoDAO = new ServicoDAO();

    public int cadastrarServico(String nome, String tipoObra, Escola escola) throws SQLException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do serviço é obrigatório.");
        }
        if (escola == null) {
            throw new IllegalArgumentException("O serviço precisa estar vinculado a uma escola.");
        }

        Servico servico = new Servico(0, nome, tipoObra, escola);
        return servicoDAO.salvar(servico);
    }

    public List<Servico> listarServicos() throws SQLException {
        return servicoDAO.listarTodos();
    }
}