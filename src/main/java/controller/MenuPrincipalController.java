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
        System.out.println("Abrir tela de lista de serviços (ainda não implementada)");
    }

    @FXML
    public void abrirFuncionarios() {
        System.out.println("Abrir tela de funcionários (ainda não implementada)");
    }

    private void navegarPara(String caminhoFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFxml));
            Parent raiz = loader.load();

            Stage stage = (Stage) botaoNovoServico.getScene().getWindow();
            stage.setScene(new Scene(raiz));

        } catch (IOException e) {
            System.out.println("Erro ao abrir tela: " + e.getMessage());
        }
    }
}