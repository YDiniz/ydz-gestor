package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.Navegador;

public class MenuPrincipalController {

    @FXML
    private Button botaoNovoServico;

    @FXML
    public void abrirNovoServico() {
        Navegador.irPara(botaoNovoServico, "/views/CadastroServico.fxml");
    }

    @FXML
    public void abrirListaServicos() {
        Navegador.irPara(botaoNovoServico, "/views/AcompanharServicos.fxml");
    }

    @FXML
    public void abrirPagamentos() {
        Navegador.irPara(botaoNovoServico, "/views/Pagamentos.fxml");
    }

    @FXML
    public void abrirEscolas() {
        Navegador.irPara(botaoNovoServico, "/views/MenuEscolas.fxml");
    }

    @FXML
    public void abrirFuncionarios() {
        Navegador.irPara(botaoNovoServico, "/views/MenuFuncionarios.fxml");
    }

    @FXML
    public void sair() {
        Navegador.irPara(botaoNovoServico, "/views/Login.fxml");
    }
}