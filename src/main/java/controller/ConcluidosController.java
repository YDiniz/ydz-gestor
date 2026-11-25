package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Servico;
import service.ServicoService;
import util.Navegador;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ConcluidosController {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    private ListView<Servico> listaConcluidos;

    @FXML
    private Label labelMensagem;

    private ServicoService servicoService = new ServicoService();

    @FXML
    public void initialize() {
        listaConcluidos.setCellFactory(lv -> new ListCell<Servico>() {
            @Override
            protected void updateItem(Servico servico, boolean vazio) {
                super.updateItem(servico, vazio);
                if (vazio || servico == null) {
                    setText("");
                    return;
                }
                String data = servico.getDataConclusao() != null
                        ? servico.getDataConclusao().format(FORMATO) : "sem data";
                setText(servico.getNome() + " - " + servico.getEscola().getNome()
                        + " (concluído em " + data + ")");
            }
        });

        listaConcluidos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Servico selecionado = listaConcluidos.getSelectionModel().getSelectedItem();
                if (selecionado != null) {
                    abrirDetalhes(selecionado);
                }
            }
        });

        carregarLista();
    }

    private void carregarLista() {
        try {
            listaConcluidos.setItems(FXCollections.observableArrayList(servicoService.listarConcluidos()));
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao carregar os serviços concluídos.");
        }
    }

    private void abrirDetalhes(Servico servico) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DetalheServico.fxml"));
            Parent raiz = loader.load();

            DetalheServicoController controller = loader.getController();
            controller.carregarServico(servico);

            Scene cena = new Scene(raiz, 400, 680);
            cena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());

            Stage modal = new Stage();
            modal.setTitle("Detalhes do Serviço");
            modal.setScene(cena);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.show();

        } catch (IOException e) {
            labelMensagem.setText("Erro ao abrir os detalhes.");
        }
    }

    @FXML
    public void reabrirSelecionado() {
        Servico selecionado = listaConcluidos.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            labelMensagem.setText("Selecione um serviço na lista.");
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION,
                "Reabrir o serviço \"" + selecionado.getNome()
                        + "\"? Ele volta para Acompanhar Serviços como Em andamento.",
                ButtonType.YES, ButtonType.NO);

        if (confirmacao.showAndWait().filter(resposta -> resposta == ButtonType.YES).isEmpty()) {
            return;
        }

        try {
            servicoService.reabrirServico(selecionado.getId());
            carregarLista();
            labelMensagem.setText("Serviço reaberto.");

        } catch (SQLException e) {
            labelMensagem.setText("Erro ao reabrir o serviço.");
        }
    }

    @FXML
    public void voltarAoMenu() {
        Navegador.irPara(listaConcluidos, "/views/MenuPrincipal.fxml");
    }
}