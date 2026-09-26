package service;

import dao.UsuarioDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class UsuarioService {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean autenticar(String login, String senhaDigitada) throws SQLException {
        String hashArmazenado = usuarioDAO.buscarHashSenha(login);

        if (hashArmazenado == null) {
            return false; // login não existe
        }

        return BCrypt.checkpw(senhaDigitada, hashArmazenado);
    }
}