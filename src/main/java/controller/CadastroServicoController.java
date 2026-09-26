package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.Escola;
import model.Funcionario;
import org.controlsfx.control.CheckComboBox;
import service.EscolaService;
import service.FuncionarioService;
import service.ServicoService;

import java.sql.SQLException;

public class CadastroServicoController {

    @FXML
    private TextField campoNome;

    @FXML
    private TextField campoTipoObra;

    @FXML
    private ComboBox<Escola> comboEscola;

    @FXML
    private VBox containerFuncionarios;

    @FXML
    private Label labelMensagem;

    private EscolaService escolaService = new EscolaService();
    private ServicoService servicoService = new ServicoService();
    private FuncionarioService funcionarioService = new FuncionarioService();

    private CheckComboBox<Funcionario> comboFuncionarios;

    @FXML
    public void initialize() {
        try {
            ObservableList<Escola> escolas = FXCollections.observableArrayList(escolaService.listarEscolas());
            comboEscola.setItems(escolas);
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
            comboFuncionarios = new CheckComboBox<>(funcionarios);
            comboFuncionarios.setPrefWidth(300);

            comboFuncionarios.setConverter(new StringConverter<Funcionario>() {
                @Override
                public String toString(Funcionario funcionario) {
                    return funcionario != null ? funcionario.getNome() : "";
                }
                @Override
                public Funcionario fromString(String string) {
                    return null;
                }
            });

            containerFuncionarios.getChildren().add(comboFuncionarios);

        } catch (SQLException e) {
            labelMensagem.setText("Erro ao carregar dados.");
        }
    }

    @FXML
    public void salvar() {
        String nome = campoNome.getText();
        String tipoObra = campoTipoObra.getText();
        Escola escolaSelecionada = comboEscola.getValue();

        try {
            int servicoId = servicoService.cadastrarServico(nome, tipoObra, escolaSelecionada);

            for (Funcionario funcionario : comboFuncionarios.getCheckModel().getCheckedItems()) {
                funcionarioService.vincularAoServico(funcionario.getId(), servicoId);
            }

            labelMensagem.setText("Serviço cadastrado com sucesso!");
            campoNome.clear();
            campoTipoObra.clear();
            comboEscola.setValue(null);
            comboFuncionarios.getCheckModel().clearChecks();

        } catch (IllegalArgumentException e) {
            labelMensagem.setText(e.getMessage());
        } catch (SQLException e) {
            labelMensagem.setText("Erro ao salvar no banco.");
        }
    }
}