package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Funcionario;
import service.FuncionarioService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class DetalheFuncionarioController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoCargo;

    @FXML
    private TextField campoTelefone;

    @FXML
    private Label labelMensagem;

    private FuncionarioService funcionarioService = new FuncionarioService();
    private Funcionario funcionario;

    public void carregarFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        campoNome.setText(funcionario.getNome());
        campoCargo.setText(funcionario.getCargo());
        campoTelefone.setText(funcionario.getTelefone());
    }

    @FXML
    public void salvar() {
        try {
            Funcionario alterado = new Funcionario(funcionario.getId(), campoNome.getText(),
                    campoCargo.getText(), campoTelefone.getText());
            funcionarioService.atualizarFuncionario(alterado);
            fechar();

        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void excluir() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION,
                "Excluir o funcionário " + funcionario.getNome() + "?", ButtonType.YES, ButtonType.NO);

        alerta.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.YES) {
                executarExclusao();
            }
        });
    }

    private void executarExclusao() {
        try {
            funcionarioService.removerFuncionario(funcionario.getId());
            fechar();

        } catch (SQLIntegrityConstraintViolationException e) {
            labelMensagem.setText("Este funcionário está vinculado a serviços e não pode ser excluído.");
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao excluir no banco.");
        }
    }

    @FXML
    public void fechar() {
        Stage stage = (Stage) campoNome.getScene().getWindow();
        stage.close();
    }
}