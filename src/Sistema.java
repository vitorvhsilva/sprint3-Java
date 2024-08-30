import model.Usuario;
import service.UsuarioService;

import java.util.Scanner;

public class Sistema {
    private UsuarioService usuarioService;
    private Scanner scanner;

    public Sistema() {
        this.usuarioService = new UsuarioService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciarSistema() {
        Usuario usuario = loginOuCadastro();
        System.out.println(usuario);
    }

    public Usuario loginOuCadastro() {
        System.out.println("Olá, seja bem vindo! \nVocê deseja fazer login ou cadastro? \n1 - Login, 2 - Cadastro");
        int opcao = scanner.nextInt();scanner.nextLine();

        Usuario usuario;

        if (opcao == 1) {
            usuario = usuarioService.fazerLogin();
        } else if (opcao == 2) {
            usuario = usuarioService.persistirUsuario();
        } else {
            System.out.println("Opção inválida!");
            usuario = null;
        }


        if (usuario == null) {
            System.out.println("Deseja reiniciar ou encerrar o sistema? \n1 - Login, 2 - Encerrar");
            opcao = scanner.nextInt();scanner.nextLine();

            if (opcao != 1) {
                System.exit(0);
            }

            loginOuCadastro();
        }

        return usuario;
    }
}
