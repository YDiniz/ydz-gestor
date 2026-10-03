package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import model.Servico;
import model.StatusServico;
import service.ServicoService;

import java.sql.SQLException;

public class AcompanharServicosController {

    @FXML
    private ComboBox<StatusServico> comboFiltroStatus;

    @FXML
    private ListView<Servico> listaServicos;

    private ServicoService servicoService = new ServicoService();

    @FXML
    public void initialize() {
        comboFiltroStatus.setItems(FXCollections.observableArrayList(StatusServico.values()));

        comboFiltroStatus.setOnAction(event -> aplicarFiltro());

        listaServicos.setCellFactory(lv -> new javafx.scene.control.ListCell<Servico>() {
            @Override
            protected void updateItem(Servico servico, boolean vazio) {
                super.updateItem(servico, vazio);
                if (vazio || servico == null) {
                    setText("");
                } else {
                    setText(servico.getNome() + " - " + servico.getEscola().getNome() + " [" + servico.getStatus() + "]");
                }
            }
        });

        carregarTodos();
    }

    private void carregarTodos() {
        try {
            ObservableList<Servico> servicos = FXCollections.observableArrayList(servicoService.listarServicos());
            listaServicos.setItems(servicos);
        } catch (SQLException e) {
            listaServicos.setItems(FXCollections.observableArrayList());
        }
    }

    private void aplicarFiltro() {
        StatusServico statusSelecionado = comboFiltroStatus.getValue();

        try {
            if (statusSelecionado == null) {
                carregarTodos();
            } else {
                ObservableList<Servico> servicos = FXCollections.observableArrayList(servicoService.listarPorStatus(statusSelecionado));
                listaServicos.setItems(servicos);
            }
        } catch (SQLException e) {
            listaServicos.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    public void voltarAoMenu() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/MenuPrincipal.fxml"));
            javafx.scene.Parent raiz = loader.load();

            javafx.stage.Stage stage = (javafx.stage.Stage) listaServicos.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(raiz));

        } catch (java.io.IOException e) {
            System.out.println("Erro ao voltar ao menu: " + e.getMessage());
        }

    }
}