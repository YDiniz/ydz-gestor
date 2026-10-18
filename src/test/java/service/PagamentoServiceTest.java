package service;

import model.DirecaoPagamento;
import model.TipoPagamento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PagamentoServiceTest {

    private PagamentoService pagamentoService = new PagamentoService();

    @Test
    public void naoDevePermitirPagamentoComValorNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
                pagamentoService.lancarPagamento(LocalDate.now(), -100.0, TipoPagamento.ADIANTAMENTO,
                        DirecaoPagamento.RECEBIDO, null, null, 1));
    }

    @Test
    public void naoDevePermitirPagamentoComValorZero() {
        assertThrows(IllegalArgumentException.class, () ->
                pagamentoService.lancarPagamento(LocalDate.now(), 0.0, TipoPagamento.ADIANTAMENTO,
                        DirecaoPagamento.RECEBIDO, null, null, 1));
    }

    @Test
    public void naoDevePermitirGastoRealizadoSemCategoria() {
        assertThrows(IllegalArgumentException.class, () ->
                pagamentoService.lancarPagamento(LocalDate.now(), 100.0, TipoPagamento.PAGAMENTO,
                        DirecaoPagamento.REALIZADO, null, "Teste", 1));
    }

    @Test
    public void naoDevePermitirPagamentoSemServico() {
        assertThrows(IllegalArgumentException.class, () ->
                pagamentoService.lancarPagamento(LocalDate.now(), 100.0, TipoPagamento.PAGAMENTO,
                        DirecaoPagamento.RECEBIDO, null, null, 0));
    }

    @Test
    public void naoDevePermitirAtualizarPagamentoComValorZero() {
        assertThrows(IllegalArgumentException.class, () ->
                pagamentoService.atualizarPagamento(1, LocalDate.now(), 0.0, TipoPagamento.PAGAMENTO,
                        DirecaoPagamento.RECEBIDO, null, null));
    }

}