package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.Navegador;

public class MenuFuncionariosController {

    @FXML
    private Button botaoCadastrar;

    @FXML
    public void abrirCadastro() {
        Navegador.irPara(botaoCadastrar, "/views/CadastroFuncionario.fxml");
    }

    @FXML
    public void abrirLista() {
        Navegador.irPara(botaoCadastrar, "/views/ListaFuncionarios.fxml");
    }

    @FXML
    public void voltarAoMenu() {
        Navegador.irPara(botaoCadastrar, "/views/MenuPrincipal.fxml");
    }
}