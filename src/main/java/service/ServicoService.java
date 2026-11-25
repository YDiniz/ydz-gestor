package service;

import dao.ServicoDAO;
import model.Escola;
import model.Servico;
import model.StatusServico;
import java.util.ArrayList;

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

    public List<Servico> listarPorStatus(StatusServico status) throws SQLException {
        return servicoDAO.listarPorStatus(status);
    }

    public void atualizarStatus(int servicoId, StatusServico status) throws SQLException {
        if (status == null) {
            throw new IllegalArgumentException("Selecione o status do serviço.");
        }
        servicoDAO.atualizarStatus(servicoId, status);
    }

    public void concluirServico(int servicoId) throws SQLException {
        servicoDAO.atualizarStatus(servicoId, StatusServico.CONCLUIDO);
    }

    public void reabrirServico(int servicoId) throws SQLException {
        servicoDAO.atualizarStatus(servicoId, StatusServico.EM_ANDAMENTO);
    }

    public List<Servico> listarEmAberto() throws SQLException {
        List<Servico> resultado = new ArrayList<>();
        for (Servico s : servicoDAO.listarTodos()) {
            if (s.getStatus() != StatusServico.CONCLUIDO) {
                resultado.add(s);
            }
        }
        return resultado;
    }

    public List<Servico> listarConcluidos() throws SQLException {
        return servicoDAO.listarPorStatus(StatusServico.CONCLUIDO);
    }

}