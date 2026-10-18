package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.CategoriaGasto;
import model.DirecaoPagamento;
import model.Pagamento;
import model.TipoPagamento;
import service.PagamentoService;

import java.sql.SQLException;

public class EditarPagamentoController {

    @FXML
    private RadioButton radioRecebido;

    @FXML
    private RadioButton radioRealizado;

    @FXML
    private ComboBox<TipoPagamento> comboTipo;

    @FXML
    private Label labelCategoria;

    @FXML
    private ComboBox<CategoriaGasto> comboCategoria;

    @FXML
    private TextField campoDescricao;

    @FXML
    private TextField campoValor;

    @FXML
    private DatePicker campoData;

    @FXML
    private Label labelMensagem;

    private PagamentoService pagamentoService = new PagamentoService();
    private Pagamento pagamento;

    @FXML
    public void initialize() {
        comboTipo.setItems(FXCollections.observableArrayList(TipoPagamento.values()));
        comboCategoria.setItems(FXCollections.observableArrayList(CategoriaGasto.values()));

        radioRealizado.selectedProperty().addListener((obs, antigo, novo) -> mostrarCategoria(novo));
    }

    public void carregarPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;

        if (pagamento.getDirecao() == DirecaoPagamento.REALIZADO) {
            radioRealizado.setSelected(true);
        } else {
            radioRecebido.setSelected(true);
        }
        mostrarCategoria(radioRealizado.isSelected());

        comboTipo.setValue(pagamento.getTipo());
        comboCategoria.setValue(pagamento.getCategoria());
        campoDescricao.setText(pagamento.getDescricao());
        campoValor.setText(String.format("%.2f", pagamento.getValor()).replace('.', ','));
        campoData.setValue(pagamento.getData());
    }

    private void mostrarCategoria(boolean mostrar) {
        labelCategoria.setVisible(mostrar);
        labelCategoria.setManaged(mostrar);
        comboCategoria.setVisible(mostrar);
        comboCategoria.setManaged(mostrar);
        if (!mostrar) {
            comboCategoria.setValue(null);
        }
    }

    @FXML
    public void salvar() {
        DirecaoPagamento direcao = radioRealizado.isSelected()
                ? DirecaoPagamento.REALIZADO : DirecaoPagamento.RECEBIDO;

        try {
            double valor = Double.parseDouble(campoValor.getText().trim().replace(",", "."));

            pagamentoService.atualizarPagamento(pagamento.getId(), campoData.getValue(), valor,
                    comboTipo.getValue(), direcao, comboCategoria.getValue(), campoDescricao.getText());

            fechar();

        } catch (NumberFormatException e) {
            labelMensagem.setText("Informe um valor válido, por exemplo 500,00.");
        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void fechar() {
        Stage stage = (Stage) campoValor.getScene().getWindow();
        stage.close();
    }
}