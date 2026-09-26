import service.UsuarioService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UsuarioService usuarioService = new UsuarioService();

        try {
            boolean senhaCorreta = usuarioService.autenticar("pai", "1234");
            System.out.println("Senha correta: " + senhaCorreta);

            boolean senhaErrada = usuarioService.autenticar("pai", "senhaerrada");
            System.out.println("Senha errada: " + senhaErrada);

            boolean loginInexistente = usuarioService.autenticar("naoexiste", "qualquer");
            System.out.println("Login inexistente: " + loginInexistente);

        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco: " + e.getMessage());
        }
    }
}