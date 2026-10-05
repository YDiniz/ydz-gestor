import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login.fxml"));
        Parent raiz = loader.load();

        Scene cena = new Scene(raiz, 700, 750);
        stage.setScene(cena);
        stage.centerOnScreen();
        stage.show();

        cena.getStylesheets().add(getClass().getResource("/css/estilo-azul.css").toExternalForm());
    }

    public static void main(String[] args) {
        launch(args);
    }

}