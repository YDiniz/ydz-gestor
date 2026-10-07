package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Documento;
import model.Funcionario;
import model.Pagamento;
import model.Servico;
import service.PagamentoService;

import java.awt.Desktop;
import java.io.File;
import java.sql.SQLException;

public class DetalheServicoController {

    @FXML
    private Label labelNome;

    @FXML
    private Label labelTipo;

    @FXML
    private Label labelStatus;

    @FXML
    private Label labelEscola;

    @FXML
    private ListView<Funcionario> listaFuncionarios;

    @FXML
    private ListView<Documento> listaDocumentos;

    @FXML
    private ListView<Pagamento> listaPagamentos;

    @FXML
    private Label labelTotalPago;

    private PagamentoService pagamentoService = new PagamentoService();

    public void carregarServico(Servico servico) {
        labelNome.setText("Nome: " + servico.getNome());
        labelTipo.setText("Tipo da obra: " + servico.getTipoObra());
        labelStatus.setText("Status: " + servico.getStatus());
        labelEscola.setText("Escola: " + servico.getEscola().getNome() + " - " + servico.getEscola().getEndereco());

        listaFuncionarios.setItems(FXCollections.observableArrayList(servico.getFuncionarios()));
        listaFuncionarios.setCellFactory(lv -> new javafx.scene.control.ListCell<Funcionario>() {
            @Override
            protected void updateItem(Funcionario f, boolean vazio) {
                super.updateItem(f, vazio);
                setText(vazio || f == null ? "" : f.getNome() + " - " + f.getCargo());
            }
        });

        listaDocumentos.setItems(FXCollections.observableArrayList(servico.getDocumentos()));
        listaDocumentos.setCellFactory(lv -> new javafx.scene.control.ListCell<Documento>() {
            @Override
            protected void updateItem(Documento d, boolean vazio) {
                super.updateItem(d, vazio);
                setText(vazio || d == null ? "" : d.getTipo() + " - " + new File(d.getCaminhoArquivo()).getName());
            }
        });
        listaDocumentos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Documento selecionado = listaDocumentos.getSelectionModel().getSelectedItem();
                if (selecionado != null) {
                    abrirArquivo(selecionado.getCaminhoArquivo());
                }
            }
        });

        listaPagamentos.setItems(FXCollections.observableArrayList(servico.getPagamentos()));
        listaPagamentos.setCellFactory(lv -> new javafx.scene.control.ListCell<Pagamento>() {
            @Override
            protected void updateItem(Pagamento p, boolean vazio) {
                super.updateItem(p, vazio);
                setText(vazio || p == null ? "" : p.getData() + " - R$ " + p.getValor() + " (" + p.getTipo() + ")");
            }
        });

        try {
            double total = pagamentoService.calcularTotalPago(servico.getId());
            labelTotalPago.setText("Total pago: R$ " + total);
        } catch (SQLException e) {
            labelTotalPago.setText("Erro ao calcular total pago.");
        }
    }

    private void abrirArquivo(String caminho) {
        try {
            Desktop.getDesktop().open(new File(caminho));
        } catch (Exception e) {
            System.out.println("Erro ao abrir arquivo: " + e.getMessage());
        }
    }

    @FXML
    public void fechar() {
        Stage stage = (Stage) labelNome.getScene().getWindow();
        stage.close();
    }
}