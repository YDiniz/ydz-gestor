package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.CategoriaGasto;
import model.DirecaoPagamento;
import model.Servico;
import model.TipoPagamento;
import service.PagamentoService;
import service.ServicoService;
import util.Navegador;

import java.sql.SQLException;
import java.time.LocalDate;

public class PagamentosController {

    @FXML
    private RadioButton radioRealizado;

    @FXML
    private ComboBox<Servico> comboServico;

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

    private ServicoService servicoService = new ServicoService();
    private PagamentoService pagamentoService = new PagamentoService();

    @FXML
    public void initialize() {
        comboTipo.setItems(FXCollections.observableArrayList(TipoPagamento.values()));
        comboCategoria.setItems(FXCollections.observableArrayList(CategoriaGasto.values()));
        campoData.setValue(LocalDate.now());

        comboServico.setConverter(new StringConverter<Servico>() {
            @Override
            public String toString(Servico servico) {
                return servico != null ? servico.getNome() + " - " + servico.getEscola().getNome() : "";
            }
            @Override
            public Servico fromString(String string) {
                return null;
            }
        });

        try {
            comboServico.setItems(FXCollections.observableArrayList(servicoService.listarServicos()));
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao carregar serviços.");
        }

        radioRealizado.selectedProperty().addListener((obs, antigo, novo) -> mostrarCategoria(novo));
        mostrarCategoria(false);
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
        Servico servico = comboServico.getValue();
        DirecaoPagamento direcao = radioRealizado.isSelected()
                ? DirecaoPagamento.REALIZADO : DirecaoPagamento.RECEBIDO;

        try {
            double valor = Double.parseDouble(campoValor.getText().trim().replace(",", "."));

            pagamentoService.lancarPagamento(campoData.getValue(), valor, comboTipo.getValue(),
                    direcao, comboCategoria.getValue(), campoDescricao.getText(),
                    servico != null ? servico.getId() : 0);

            labelMensagem.setText("Pagamento lançado com sucesso!");
            campoValor.clear();
            campoDescricao.clear();
            comboTipo.setValue(null);
            comboCategoria.setValue(null);

        } catch (NumberFormatException e) {
            labelMensagem.setText("Informe um valor válido, por exemplo 500,00.");
        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void voltarAoMenu() {
        Navegador.irPara(campoValor, "/views/MenuPrincipal.fxml");
    }
}