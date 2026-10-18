package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Servico;
import model.StatusServico;
import service.ServicoService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import java.io.IOException;
import javafx.scene.control.Alert;

import java.io.IOException;
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

        listaServicos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Servico selecionado = listaServicos.getSelectionModel().getSelectedItem();
                if (selecionado != null) {
                    abrirDetalhes(selecionado);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuPrincipal.fxml"));
            Parent raiz = loader.load();

            Scene cena = new Scene(raiz, 700, 750);
            cena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());

            Stage stage = (Stage) listaServicos.getScene().getWindow();
            stage.setScene(cena);

        } catch (IOException e) {
            System.out.println("Erro ao voltar ao menu: " + e.getMessage());
        }
    }

    private void abrirDetalhes(Servico servico) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DetalheServico.fxml"));
            Parent raiz = loader.load();

            DetalheServicoController controller = loader.getController();
            controller.carregarServico(servico);

            Stage modal = new Stage();
            modal.setTitle("Detalhes do Serviço");
            Scene cena = new Scene(raiz, 400, 680);
            cena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());
            modal.setScene(cena);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.show();

        } catch (IOException e) {
            System.out.println("Erro ao abrir detalhes: " + e.getMessage());
        }
    }

    @FXML
    public void editarSelecionado() {
        Servico selecionado = listaServicos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            new Alert(Alert.AlertType.INFORMATION, "Selecione um serviço na lista.").showAndWait();
            return;
        }

        abrirEdicao(selecionado);
    }

    private void abrirEdicao(Servico servico) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditarServico.fxml"));
            Parent raiz = loader.load();

            EditarServicoController controller = loader.getController();
            controller.carregarServico(servico);

            Scene cena = new Scene(raiz, 520, 700);
            cena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());

            Stage modal = new Stage();
            modal.setTitle("Editar Serviço");
            modal.setScene(cena);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.showAndWait();

            aplicarFiltro();

        } catch (IOException e) {
            System.out.println("Erro ao abrir edição: " + e.getMessage());
        }
    }
}