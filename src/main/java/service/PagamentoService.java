package service;

import dao.PagamentoDAO;
import model.Pagamento;
import model.TipoPagamento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PagamentoService {

    private PagamentoDAO pagamentoDAO = new PagamentoDAO();

    public void lancarPagamento(LocalDate data, double valor, TipoPagamento tipo, int servicoId) throws SQLException {
        if (data == null) {
            throw new IllegalArgumentException("A data do pagamento é obrigatória.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo do pagamento é obrigatório.");
        }

        Pagamento pagamento = new Pagamento(0, data, valor, tipo);
        pagamentoDAO.salvar(pagamento, servicoId);
    }

    public List<Pagamento> listarPagamentosDoServico(int servicoId) throws SQLException {
        return pagamentoDAO.listarPorServico(servicoId);
    }

    public double calcularTotalPago(int servicoId) throws SQLException {
        List<Pagamento> pagamentos = pagamentoDAO.listarPorServico(servicoId);
        double total = 0;

        for (Pagamento p : pagamentos) {
            total += p.getValor();
        }

        return total;
    }
}