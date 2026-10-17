package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Funcionario;
import service.FuncionarioService;
import util.Navegador;

import java.io.IOException;
import java.sql.SQLException;

public class ListaFuncionariosController {

    @FXML
    private ListView<Funcionario> listaFuncionarios;

    @FXML
    private Label labelMensagem;

    private FuncionarioService funcionarioService = new FuncionarioService();

    @FXML
    public void initialize() {
        listaFuncionarios.setCellFactory(lv -> new ListCell<Funcionario>() {
            @Override
            protected void updateItem(Funcionario f, boolean vazio) {
                super.updateItem(f, vazio);
                setText(vazio || f == null ? "" : f.getNome() + " - " + f.getCargo());
            }
        });

        listaFuncionarios.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Funcionario selecionado = listaFuncionarios.getSelectionModel().getSelectedItem();
                if (selecionado != null) {
                    abrirDetalhes(selecionado);
                }
            }
        });

        carregarLista();
    }

    private void carregarLista() {
        try {
            listaFuncionarios.setItems(FXCollections.observableArrayList(funcionarioService.listarFuncionarios()));
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao carregar funcionários.");
        }
    }

    private void abrirDetalhes(Funcionario funcionario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DetalheFuncionario.fxml"));
            Parent raiz = loader.load();

            DetalheFuncionarioController controller = loader.getController();
            controller.carregarFuncionario(funcionario);

            Scene cena = new Scene(raiz, 380, 360);
            cena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());

            Stage modal = new Stage();
            modal.setTitle("Dados do Funcionário");
            modal.setScene(cena);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.showAndWait();

            carregarLista();

        } catch (IOException e) {
            labelMensagem.setText("Erro ao abrir a janela do funcionário.");
        }
    }

    @FXML
    public void voltar() {
        Navegador.irPara(listaFuncionarios, "/views/MenuFuncionarios.fxml");
    }
}