package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Documento;
import model.Funcionario;
import model.Pagamento;
import model.Servico;
import model.StatusServico;
import model.TipoDocumento;
import service.DocumentoService;
import service.FuncionarioService;
import service.PagamentoService;
import service.ServicoService;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EditarServicoController {

    @FXML
    private Label labelNome;

    @FXML
    private Label labelTipo;

    @FXML
    private Label labelEscola;

    @FXML
    private ComboBox<StatusServico> comboStatus;

    @FXML
    private ListView<Funcionario> listaFuncionarios;

    @FXML
    private ComboBox<Funcionario> comboNovoFuncionario;

    @FXML
    private ListView<Documento> listaDocumentos;

    @FXML
    private ComboBox<TipoDocumento> comboTipoDocumento;

    @FXML
    private ListView<Pagamento> listaPagamentos;

    @FXML
    private Label labelMensagem;

    private ServicoService servicoService = new ServicoService();
    private FuncionarioService funcionarioService = new FuncionarioService();
    private DocumentoService documentoService = new DocumentoService();
    private PagamentoService pagamentoService = new PagamentoService();

    private Servico servico;

    @FXML
    public void initialize() {
        comboStatus.setItems(FXCollections.observableArrayList(StatusServico.values()));
        comboTipoDocumento.setItems(FXCollections.observableArrayList(TipoDocumento.values()));

        comboNovoFuncionario.setConverter(new StringConverter<Funcionario>() {
            @Override
            public String toString(Funcionario f) {
                return f != null ? f.getNome() : "";
            }
            @Override
            public Funcionario fromString(String string) {
                return null;
            }
        });

        listaFuncionarios.setCellFactory(lv -> new ListCell<Funcionario>() {
            @Override
            protected void updateItem(Funcionario f, boolean vazio) {
                super.updateItem(f, vazio);
                setText(vazio || f == null ? "" : f.getNome() + " - " + f.getCargo());
            }
        });

        listaDocumentos.setCellFactory(lv -> new ListCell<Documento>() {
            @Override
            protected void updateItem(Documento d, boolean vazio) {
                super.updateItem(d, vazio);
                setText(vazio || d == null ? "" : d.getTipo() + " - " + new File(d.getCaminhoArquivo()).getName());
            }
        });

        listaPagamentos.setCellFactory(lv -> new ListCell<Pagamento>() {
            @Override
            protected void updateItem(Pagamento p, boolean vazio) {
                super.updateItem(p, vazio);
                setText(vazio || p == null ? "" : p.getData() + " - " + p.getDirecao()
                                                  + " - R$ " + String.format("%.2f", p.getValor()) + " (" + p.getTipo() + ")");
            }
        });
    }

    public void carregarServico(Servico servico) {
        this.servico = servico;

        labelNome.setText("Serviço: " + servico.getNome());
        labelTipo.setText("Tipo da obra: " + servico.getTipoObra());
        labelEscola.setText("Escola: " + servico.getEscola().getNome());
        comboStatus.setValue(servico.getStatus());

        recarregar();
    }

    private void recarregar() {
        try {
            List<Funcionario> vinculados = funcionarioService.listarDoServico(servico.getId());
            listaFuncionarios.setItems(FXCollections.observableArrayList(vinculados));

            List<Funcionario> disponiveis = new ArrayList<>(funcionarioService.listarFuncionarios());
            disponiveis.removeIf(f -> vinculados.stream().anyMatch(v -> v.getId() == f.getId()));
            comboNovoFuncionario.setItems(FXCollections.observableArrayList(disponiveis));
            comboNovoFuncionario.setValue(null);

            listaDocumentos.setItems(FXCollections.observableArrayList(
                    documentoService.listarDocumentosDoServico(servico.getId())));
            listaPagamentos.setItems(FXCollections.observableArrayList(
                    pagamentoService.listarPagamentosDoServico(servico.getId())));

        } catch (SQLException e) {
            labelMensagem.setText("Erro ao carregar os dados do serviço.");
        }
    }

    private boolean confirmar(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, mensagem, ButtonType.YES, ButtonType.NO);
        return alerta.showAndWait().filter(resposta -> resposta == ButtonType.YES).isPresent();
    }

    @FXML
    public void salvarStatus() {
        try {
            servicoService.atualizarStatus(servico.getId(), comboStatus.getValue());
            labelMensagem.setText("Status atualizado.");

        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void adicionarFuncionario() {
        Funcionario funcionario = comboNovoFuncionario.getValue();
        if (funcionario == null) {
            labelMensagem.setText("Escolha um funcionário para adicionar.");
            return;
        }

        try {
            funcionarioService.vincularAoServico(funcionario.getId(), servico.getId());
            recarregar();
            labelMensagem.setText("Funcionário adicionado.");

        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void removerFuncionario() {
        Funcionario funcionario = listaFuncionarios.getSelectionModel().getSelectedItem();
        if (funcionario == null) {
            labelMensagem.setText("Selecione um funcionário na lista.");
            return;
        }

        if (!confirmar("Remover " + funcionario.getNome() + " deste serviço?")) {
            return;
        }

        try {
            funcionarioService.desvincularDoServico(funcionario.getId(), servico.getId());
            recarregar();
            labelMensagem.setText("Funcionário removido do serviço.");

        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void anexarDocumento() {
        TipoDocumento tipo = comboTipoDocumento.getValue();
        if (tipo == null) {
            labelMensagem.setText("Escolha o tipo do documento antes de anexar.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar documento");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF ou Imagens", "*.pdf", "*.jpg", "*.png"));

        File arquivo = fileChooser.showOpenDialog((Stage) labelNome.getScene().getWindow());
        if (arquivo == null) {
            return;
        }

        try {
            documentoService.anexarDocumento(tipo, arquivo.getAbsolutePath(), servico.getId());
            comboTipoDocumento.setValue(null);
            recarregar();
            labelMensagem.setText("Documento anexado.");

        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void removerDocumento() {
        Documento documento = listaDocumentos.getSelectionModel().getSelectedItem();
        if (documento == null) {
            labelMensagem.setText("Selecione um documento na lista.");
            return;
        }

        if (!confirmar("Remover este documento do serviço? O arquivo no computador não será apagado.")) {
            return;
        }

        try {
            documentoService.removerDocumento(documento.getId());
            recarregar();
            labelMensagem.setText("Documento removido do serviço.");

        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }

    @FXML
    public void fechar() {
        Stage stage = (Stage) labelNome.getScene().getWindow();
        stage.close();
    }
}