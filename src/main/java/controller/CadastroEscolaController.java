package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.EscolaService;
import util.Navegador;

import java.sql.SQLException;

public class CadastroEscolaController {

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

    @FXML
    public void salvar() {
        try {
            escolaService.cadastrarEscola(campoNome.getText(), campoEndereco.getText(), campoContato.getText(), campoNomeContato.getText());

            labelMensagem.setText("Escola cadastrada com sucesso!");
            campoNome.clear();
            campoEndereco.clear();
            campoContato.clear();
            campoNomeContato.clear();

        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void voltar() {
        Navegador.irPara(campoNome, "/views/MenuEscolas.fxml");
    }
}