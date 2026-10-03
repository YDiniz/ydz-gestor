package service;

import model.TipoPagamento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PagamentoServiceTest {

    @Test
    public void naoDevePermitirPagamentoComValorNegativo() {
        PagamentoService pagamentoService = new PagamentoService();

        assertThrows(IllegalArgumentException.class, () -> {
            pagamentoService.lancarPagamento(LocalDate.now(), -100.0, TipoPagamento.ADIANTAMENTO, 1);
        });
    }

    @Test
    public void naoDevePermitirPagamentoComValorZero() {
        PagamentoService pagamentoService = new PagamentoService();

        assertThrows(IllegalArgumentException.class, () -> {
            pagamentoService.lancarPagamento(LocalDate.now(), 0.0, TipoPagamento.ADIANTAMENTO, 1);
        });
    }
}