package service;

import model.Escola;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicoServiceTest {

    @Test
    public void deveListarServicosSemErro() {
        ServicoService servicoService = new ServicoService();

        List<model.Servico> servicos = assertDoesNotThrow(() -> servicoService.listarServicos());

        assertTrue(servicos.size() >= 0);
    }

    @Test
    public void naoDevePermitirStatusNulo() {
        ServicoService servicoService = new ServicoService();

        assertThrows(IllegalArgumentException.class, () -> servicoService.atualizarStatus(1, null));
    }
}