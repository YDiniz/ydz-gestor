package db;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoBanco {

    private static final Path ARQUIVO_EXTERNO =
            Path.of(System.getProperty("user.home"), "YDZGestor", "config.properties");

    private static final Properties propriedades = new Properties();

    static {
        try {
            if (Files.exists(ARQUIVO_EXTERNO)) {
                try (InputStream input = Files.newInputStream(ARQUIVO_EXTERNO)) {
                    propriedades.load(input);
                }
            } else {
                try (InputStream input = ConexaoBanco.class.getResourceAsStream("/config.properties")) {
                    if (input == null) {
                        throw new RuntimeException("Arquivo de configuração não encontrado: " + ARQUIVO_EXTERNO);
                    }
                    propriedades.load(input);
                }
            }
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