package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Escola;
import service.EscolaService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class DetalheEscolaController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoEndereco;

    @FXML
    private TextField campoNomeContato;

    @FXML
    private TextField campoContato;

    @FXML
    private Label labelMensagem;

    private EscolaService escolaService = new EscolaService();
    private Escola escola;

    public void carregarEscola(Escola escola) {
        this.escola = escola;
        campoNome.setText(escola.getNome());
        campoEndereco.setText(escola.getEndereco());
        campoContato.setText(escola.getContato());
        campoNomeContato.setText(escola.getNomeContato());
    }

    @FXML
    public void salvar() {
        try {
            Escola alterada = new Escola(escola.getId(), campoNome.getText(),
                    campoEndereco.getText(), campoContato.getText(), campoNomeContato.getText());
            escolaService.atualizarEscola(alterada);
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
                "Excluir a escola " + escola.getNome() + "?", ButtonType.YES, ButtonType.NO);

        alerta.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.YES) {
                executarExclusao();
            }
        });
    }

    private void executarExclusao() {
        try {
            escolaService.removerEscola(escola.getId());
            fechar();

        } catch (SQLIntegrityConstraintViolationException e) {
            labelMensagem.setText("Esta escola tem serviços vinculados e não pode ser excluída.");
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