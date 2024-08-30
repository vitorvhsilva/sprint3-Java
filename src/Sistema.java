import model.Usuario;
import service.UsuarioService;
import service.VeiculoService;

import java.util.Scanner;

public class Sistema {
    private UsuarioService usuarioService;
    private VeiculoService veiculoService;
    private Scanner scanner;
    private Usuario usuario;

    public Sistema() {
        this.usuarioService = new UsuarioService();
        this.veiculoService = new VeiculoService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciarSistema() {
        this.usuario = loginOuCadastro();
        System.out.println(usuario);

        exibirMenu();
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
            encerrarOuReiniciarSistema();

            loginOuCadastro();
        }

        return usuario;
    }

    public void exibirMenu() {
        System.out.println("Olá " + usuario.getNome() + ", o que você deseja fazer?");
        System.out.println("""
                1 - Cadastrar veículo
                2 - Ver seus veículos""");
        int opcao = scanner.nextInt();scanner.nextLine();

        switch (opcao) {
            case 1 -> {
                veiculoService.persistirVeiculo(usuario);
                exibirMenu();
            }
            case 2 -> {
                veiculoService.mostrarVeiculos(usuario);
                exibirMenu();
            }
            default -> {
                System.out.println("Opção inválida!");
                encerrarOuReiniciarSistema();
                exibirMenu();
            }
        }
    }

    public void encerrarOuReiniciarSistema() {
        System.out.println("Deseja reiniciar ou encerrar o sistema? \n1 - Reiniciar, 2 - Encerrar");
        int opcao = scanner.nextInt();scanner.nextLine();

        if (opcao != 1) {
            System.exit(0);
        }
    }
}
