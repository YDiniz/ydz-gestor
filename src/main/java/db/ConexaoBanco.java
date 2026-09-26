package db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco {

    private static final Properties propriedades = new Properties();

    static {
        try (InputStream input = ConexaoBanco.class.getResourceAsStream("/config.properties")) {
            propriedades.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar config.properties", e);
        }
    }

    public static Connection conectar() throws SQLException {
        String url = propriedades.getProperty("db.url");
        String usuario = propriedades.getProperty("db.usuario");
        String senha = propriedades.getProperty("db.senha");

        return DriverManager.getConnection(url, usuario, senha);
    }
}