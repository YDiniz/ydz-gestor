package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.UsuarioService;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField campoLogin;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private Label labelErro;

    private UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void entrar() {
        String login = campoLogin.getText();
        String senha = campoSenha.getText();

        try {
            boolean autenticado = usuarioService.autenticar(login, senha);

            if (autenticado) {
                abrirMenuPrincipal();
            } else {
                labelErro.setText("Usuário ou senha inválidos.");
            }

        } catch (SQLException e) {
            labelErro.setText("Erro ao acessar o banco de dados.");
        }
    }

    private void abrirMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuPrincipal.fxml"));
            Parent raiz = loader.load();

            Stage stage = (Stage) campoLogin.getScene().getWindow();
            stage.setScene(new Scene(raiz));

        } catch (IOException e) {
            labelErro.setText("Erro ao abrir a próxima tela.");
        }
    }

}