package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import util.Navegador;

public class MenuEscolasController {

    @FXML
    private Button botaoCadastrar;

    @FXML
    public void abrirCadastro() {
        Navegador.irPara(botaoCadastrar, "/views/CadastroEscola.fxml");
    }

    @FXML
    public void abrirLista() {
        Navegador.irPara(botaoCadastrar, "/views/ListaEscolas.fxml");
    }

    @FXML
    public void voltarAoMenu() {
        Navegador.irPara(botaoCadastrar, "/views/MenuPrincipal.fxml");
    }
}