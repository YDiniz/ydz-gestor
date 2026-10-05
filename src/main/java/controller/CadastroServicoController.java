package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Escola;
import model.Funcionario;
import model.TipoDocumento;
import model.TipoPagamento;
import service.DocumentoService;
import service.EscolaService;
import service.FuncionarioService;
import service.PagamentoService;
import service.ServicoService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class CadastroServicoController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoTipoObra;

    @FXML
    private ComboBox<Escola> comboEscola;

    @FXML
    private ListView<Funcionario> listaFuncionarios;

    @FXML
    private Label labelMensagem;

    @FXML
    private Label labelArquivoSelecionado;

    @FXML
    private TextField campoValorPagamento;

    @FXML
    private ComboBox<TipoPagamento> comboTipoPagamento;

    private EscolaService escolaService = new EscolaService();
    private ServicoService servicoService = new ServicoService();
    private FuncionarioService funcionarioService = new FuncionarioService();
    private DocumentoService documentoService = new DocumentoService();
    private PagamentoService pagamentoService = new PagamentoService();

    private File arquivoSelecionado;

    @FXML
    public void initialize() {
        try {
            ObservableList<Escola> escolas = FXCollections.observableArrayList(escolaService.listarEscolas());
            comboEscola.setItems(escolas);
            comboTipoPagamento.setItems(FXCollections.observableArrayList(TipoPagamento.values()));
            comboEscola.setConverter(new StringConverter<Escola>() {
                @Override
                public String toString(Escola escola) {
                    return escola != null ? escola.getNome() : "";
                }
                @Override
                public Escola fromString(String string) {
                    return null;
                }
            });

            ObservableList<Funcionario> funcionarios = FXCollections.observableArrayList(funcionarioService.listarFuncionarios());
            listaFuncionarios.setItems(funcionarios);
            listaFuncionarios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            listaFuncionarios.setCellFactory(lv -> new javafx.scene.control.ListCell<Funcionario>() {
                @Override
                protected void updateItem(Funcionario funcionario, boolean vazio) {
                    super.updateItem(funcionario, vazio);
                    setText(vazio || funcionario == null ? "" : funcionario.getNome());
                }
            });

        } catch (SQLException e) {
            labelMensagem.setText("Erro ao carregar dados.");
        }
    }

    @FXML
    public void selecionarArquivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar orçamento");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF ou Imagens", "*.pdf", "*.jpg", "*.png")
        );

        Stage stage = (Stage) campoNome.getScene().getWindow();
        File arquivo = fileChooser.showOpenDialog(stage);

        if (arquivo != null) {
            arquivoSelecionado = arquivo;
            labelArquivoSelecionado.setText("Selecionado: " + arquivo.getName());
        }
    }

    @FXML
    public void salvar() {
        String nome = campoNome.getText();
        String tipoObra = campoTipoObra.getText();
        Escola escolaSelecionada = comboEscola.getValue();

        try {
            int servicoId = servicoService.cadastrarServico(nome, tipoObra, escolaSelecionada);

            for (Funcionario funcionario : listaFuncionarios.getSelectionModel().getSelectedItems()) {
                funcionarioService.vincularAoServico(funcionario.getId(), servicoId);
            }

            if (arquivoSelecionado != null) {
                documentoService.anexarDocumento(TipoDocumento.ORCAMENTO, arquivoSelecionado.getAbsolutePath(), servicoId);
            }

            String valorTexto = campoValorPagamento.getText();
            if (!valorTexto.isBlank()) {
                double valor = Double.parseDouble(valorTexto);
                TipoPagamento tipoPagamento = comboTipoPagamento.getValue();
                pagamentoService.lancarPagamento(LocalDate.now(), valor, tipoPagamento, servicoId);
            }

            labelMensagem.setText("Serviço cadastrado com sucesso!");
            campoNome.clear();
            campoTipoObra.clear();
            comboEscola.setValue(null);
            listaFuncionarios.getSelectionModel().clearSelection();
            arquivoSelecionado = null;
            labelArquivoSelecionado.setText("");
            campoValorPagamento.clear();
            comboTipoPagamento.setValue(null);

        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void voltarAoMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuPrincipal.fxml"));
            Parent raiz = loader.load();

            Scene cena = new Scene(raiz, 700, 750);
            cena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());

            Stage stage = (Stage) campoNome.getScene().getWindow();
            stage.setScene(cena);

        } catch (IOException e) {
            System.out.println("Erro ao voltar ao menu: " + e.getMessage());
        }
    }
}