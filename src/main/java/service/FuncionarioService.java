package service;

import dao.FuncionarioDAO;
import model.Funcionario;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {

    private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    public void cadastrarFuncionario(String nome, String cargo) throws SQLException {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do funcionário é obrigatório.");
        }

        Funcionario funcionario = new Funcionario(0, nome, cargo);
        funcionarioDAO.salvar(funcionario);
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        return funcionarioDAO.listarTodos();
    }

    public void atualizarFuncionario(Funcionario funcionario) throws SQLException {
        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do funcionário é obrigatório.");
        }
        funcionarioDAO.atualizar(funcionario);
    }

    public void removerFuncionario(int id) throws SQLException {
        funcionarioDAO.deletar(id);
    }

    public void vincularAoServico(int funcionarioId, int servicoId) throws SQLException {
        funcionarioDAO.vincularAoServico(funcionarioId, servicoId);
    }
}