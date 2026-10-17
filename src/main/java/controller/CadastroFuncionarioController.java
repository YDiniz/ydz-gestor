package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.FuncionarioService;
import util.Navegador;

import java.sql.SQLException;

public class CadastroFuncionarioController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoCargo;

    @FXML
    private Label labelMensagem;

    private FuncionarioService funcionarioService = new FuncionarioService();

    @FXML
    public void salvar() {
        try {
            funcionarioService.cadastrarFuncionario(campoNome.getText(), campoCargo.getText());

            labelMensagem.setText("Funcionário cadastrado com sucesso!");
            campoNome.clear();
            campoCargo.clear();

        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void voltar() {
        Navegador.irPara(campoNome, "/views/MenuFuncionarios.fxml");
    }
}