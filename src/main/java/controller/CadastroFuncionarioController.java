package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Funcionario;
import service.FuncionarioService;

import java.sql.SQLException;

public class CadastroFuncionarioController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoCargo;

    @FXML
    private Label labelMensagem;

    @FXML
    private ListView<Funcionario> listaFuncionarios;

    private FuncionarioService funcionarioService = new FuncionarioService();

    @FXML
    public void initialize() {
        carregarLista();

        listaFuncionarios.setCellFactory(lv -> new javafx.scene.control.ListCell<Funcionario>() {
            @Override
            protected void updateItem(Funcionario funcionario, boolean vazio) {
                super.updateItem(funcionario, vazio);
                setText(vazio || funcionario == null ? "" : funcionario.getNome() + " - " + funcionario.getCargo());
            }
        });
    }

    private void carregarLista() {
        try {
            ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList(funcionarioService.listarFuncionarios());
            listaFuncionarios.setItems(funcionarios);
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao carregar funcionários.");
        }
    }

    @FXML
    public void salvar() {
        String nome = campoNome.getText();
        String cargo = campoCargo.getText();

        try {
            funcionarioService.cadastrarFuncionario(nome, cargo);
            labelMensagem.setText("Funcionário cadastrado com sucesso!");
            campoNome.clear();
            campoCargo.clear();
            carregarLista();

        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }
}