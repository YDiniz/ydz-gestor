package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navegador {

    private static final int LARGURA = 700;
    private static final int ALTURA = 750;

    public static void irPara(Node componente, String caminhoFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(Navegador.class.getResource(caminhoFxml));
            Parent raiz = loader.load();

            Scene cena = new Scene(raiz, LARGURA, ALTURA);
            cena.getStylesheets().add(Navegador.class.getResource("/css/estilo-azul.css").toExternalForm());

            Stage stage = (Stage) componente.getScene().getWindow();
            stage.setScene(cena);

        } catch (IOException e) {
            System.out.println("Erro ao abrir tela: " + e.getMessage());
        }
    }
}