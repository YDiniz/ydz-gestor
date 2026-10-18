package service;

import dao.PagamentoDAO;
import model.CategoriaGasto;
import model.DirecaoPagamento;
import model.Pagamento;
import model.TipoPagamento;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PagamentoService {

    private PagamentoDAO pagamentoDAO = new PagamentoDAO();

    public void lancarPagamento(LocalDate data, double valor, TipoPagamento tipo,
                                DirecaoPagamento direcao, CategoriaGasto categoria,
                                String descricao, int servicoId) throws SQLException {
        validarDados(data, valor, tipo, direcao, categoria);
        if (servicoId <= 0) {
            throw new IllegalArgumentException("Selecione o serviço do pagamento.");
        }

        Pagamento pagamento = new Pagamento(0, data, valor, tipo, direcao,
                categoriaFinal(direcao, categoria), descricao);
        pagamentoDAO.salvar(pagamento, servicoId);
    }

    public void atualizarPagamento(int id, LocalDate data, double valor, TipoPagamento tipo,
                                   DirecaoPagamento direcao, CategoriaGasto categoria,
                                   String descricao) throws SQLException {
        validarDados(data, valor, tipo, direcao, categoria);

        Pagamento pagamento = new Pagamento(id, data, valor, tipo, direcao,
                categoriaFinal(direcao, categoria), descricao);
        pagamentoDAO.atualizar(pagamento);
    }

    private void validarDados(LocalDate data, double valor, TipoPagamento tipo,
                              DirecaoPagamento direcao, CategoriaGasto categoria) {
        if (data == null) {
            throw new IllegalArgumentException("A data do pagamento é obrigatória.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser maior que zero.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("O tipo do pagamento é obrigatório.");
        }
        if (direcao == null) {
            throw new IllegalArgumentException("Informe se o pagamento foi recebido ou realizado.");
        }
        if (direcao == DirecaoPagamento.REALIZADO && categoria == null) {
            throw new IllegalArgumentException("Informe se o gasto foi com funcionário ou material.");
        }
    }

    private CategoriaGasto categoriaFinal(DirecaoPagamento direcao, CategoriaGasto categoria) {
        return direcao == DirecaoPagamento.REALIZADO ? categoria : null;
    }

    public List<Pagamento> listarPagamentosDoServico(int servicoId) throws SQLException {
        return pagamentoDAO.listarPorServico(servicoId);
    }

    public double calcularTotalPago(int servicoId) throws SQLException {
        double total = 0;
        for (Pagamento p : pagamentoDAO.listarPorServico(servicoId)) {
            total += p.getValor();
        }
        return total;
    }

    public double calcularTotal(int servicoId, DirecaoPagamento direcao) throws SQLException {
        double total = 0;
        for (Pagamento p : pagamentoDAO.listarPorServico(servicoId)) {
            if (p.getDirecao() == direcao) {
                total += p.getValor();
            }
        }
        return total;
    }

    public void removerPagamento(int pagamentoId) throws SQLException {
        pagamentoDAO.remover(pagamentoId);
    }
}