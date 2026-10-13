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
import model.Escola;
import service.EscolaService;
import util.Navegador;

import java.io.IOException;
import java.sql.SQLException;

public class ListaEscolasController {

    @FXML
    private ListView<Escola> listaEscolas;

    @FXML
    private Label labelMensagem;

    private EscolaService escolaService = new EscolaService();

    @FXML
    public void initialize() {
        listaEscolas.setCellFactory(lv -> new ListCell<Escola>() {
            @Override
            protected void updateItem(Escola escola, boolean vazio) {
                super.updateItem(escola, vazio);
                setText(vazio || escola == null ? "" : escola.getNome());
            }
        });

        listaEscolas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Escola selecionada = listaEscolas.getSelectionModel().getSelectedItem();
                if (selecionada != null) {
                    abrirDetalhes(selecionada);
                }
            }
        });

        carregarLista();
    }

    private void carregarLista() {
        try {
            listaEscolas.setItems(FXCollections.observableArrayList(escolaService.listarEscolas()));
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao carregar escolas.");
        }
    }

    private void abrirDetalhes(Escola escola) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DetalheEscola.fxml"));
            Parent raiz = loader.load();

            DetalheEscolaController controller = loader.getController();
            controller.carregarEscola(escola);

            Scene cena = new Scene(raiz, 380, 430);
            cena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());

            Stage modal = new Stage();
            modal.setTitle("Dados da Escola");
            modal.setScene(cena);
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.showAndWait();

            carregarLista();

        } catch (IOException e) {
            labelMensagem.setText("Erro ao abrir a janela da escola.");
        }
    }

    @FXML
    public void voltar() {
        Navegador.irPara(listaEscolas, "/views/MenuEscolas.fxml");
    }
}