package service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class EscolaServiceTest {

    @Test
    public void naoDevePermitirCadastrarEscolaSemNome() {
        EscolaService escolaService = new EscolaService();

        assertThrows(IllegalArgumentException.class, () -> {
            escolaService.cadastrarEscola("", "Rua Teste", "11999999999");
        });
    }
}