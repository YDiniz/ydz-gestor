package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPrincipalController {

    @FXML
    private Button botaoNovoServico; // usaremos esse botão só pra pegar a referência da janela

    @FXML
    public void abrirNovoServico() {
        navegarPara("/views/CadastroServico.fxml");
    }

    @FXML
    public void abrirListaServicos() {
        navegarPara("/views/AcompanharServicos.fxml");
    }

    @FXML
    public void abrirEscolas() {
        navegarPara("/views/MenuEscolas.fxml");
    }

    @FXML
    public void abrirFuncionarios() {
        navegarPara("/views/MenuFuncionarios.fxml");
    }

    private void navegarPara(String caminhoFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFxml));
            Parent raiz = loader.load();

            Scene cena = new Scene(raiz, 700, 750);
            cena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());

            Stage stage = (Stage) botaoNovoServico.getScene().getWindow();
            stage.setScene(cena);

        } catch (IOException e) {
            System.out.println("Erro ao abrir tela: " + e.getMessage());
        }
    }
}