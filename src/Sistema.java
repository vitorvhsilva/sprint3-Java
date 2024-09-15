import dao.*;
import model.DescricaoProblema;
import model.Usuario;
import service.*;

import java.util.Scanner;

public class Sistema {
    private UsuarioService usuarioService;
    private VeiculoService veiculoService;
    private DescricaoProblemaService descricaoProblemaService;
    private DiagnosticoService diagnosticoService;
    private OrcamentoService orcamentoService;
    private Scanner scanner;
    private Usuario usuario;

    public Sistema() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        DescricaoProblemaDAO descricaoProblemaDAO = new DescricaoProblemaDAO();
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
        OrcamentoDAO orcamentoDAO = new OrcamentoDAO();

        this.usuarioService = new UsuarioService(usuarioDAO);
        this.veiculoService = new VeiculoService(veiculoDAO, usuarioDAO);
        this.descricaoProblemaService = new DescricaoProblemaService(descricaoProblemaDAO, veiculoDAO, usuarioDAO);
        this.diagnosticoService = new DiagnosticoService(diagnosticoDAO, descricaoProblemaDAO, veiculoDAO, usuarioDAO);
        this.orcamentoService = new OrcamentoService(diagnosticoDAO, descricaoProblemaDAO, veiculoDAO, usuarioDAO, orcamentoDAO);
        this.scanner = new Scanner(System.in);
    }

    public void iniciarSistema() {
        loginOuCadastro();

        exibirMenu();
    }

    public Usuario loginOuCadastro() {
        System.out.println("Olá, seja bem vindo! \nVocê deseja fazer login ou cadastro? \n1 - Login, 2 - Cadastro");
        int opcao = scanner.nextInt();scanner.nextLine();

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
                2 - Ver seus veículos
                3 - Fazer descrição do problema ocorrido ao veículo
                4 - Mostrar descrição já feitas a um veículo
                5 - Mostrar diagnósticos já feitos a um veículo
                6 - Fazer orçamento do veículo
                7 - Mostrar orçamentos já feito a um veículo
                8 - Encerrar o sistema""");
        int opcao = scanner.nextInt();scanner.nextLine();

        switch (opcao) {
            case 1 -> {
                veiculoService.persistirVeiculo(usuario);
                exibirMenu();
            }
            case 2 -> {
                verificarSeOUsuarioTemVeiculos();
                veiculoService.mostrarVeiculos(usuario);
                exibirMenu();
            }
            case 3 -> {
                verificarSeOUsuarioTemVeiculos();
                DescricaoProblema dp = descricaoProblemaService.persistirDescricao(usuario);
                diagnosticoService.persistirDiagnostico(dp);
                exibirMenu();
            }
            case 4 -> {
                verificarSeOUsuarioTemVeiculos();
                descricaoProblemaService.mostrarDescricoesJaFeita(usuario);
                exibirMenu();
            }
            case 5 -> {
                verificarSeOUsuarioTemVeiculos();
                diagnosticoService.mostrarDescricoesJaFeita(usuario);
                exibirMenu();
            }
            case 6 -> {
                verificarSeOUsuarioTemVeiculos();
                orcamentoService.fazerOrcamento(usuario);
                exibirMenu();
            }
            case 7 -> {
                verificarSeOUsuarioTemVeiculos();
                orcamentoService.mostrarOrcamentos(usuario);
                exibirMenu();
            }
            case 8 -> encerrarSistema();
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
            encerrarSistema();
        }
    }

    public void encerrarSistema() {
        usuarioService.fecharConexoes();
        veiculoService.fecharConexoes();
        descricaoProblemaService.fecharConexoes();
        diagnosticoService.fecharConexoes();
        orcamentoService.fecharConexoes();

        System.exit(0);
    }

    public void verificarSeOUsuarioTemVeiculos() {
        boolean possuiVeiculos = veiculoService.verificarSeOUsuarioTemVeiculos(usuario);
        if (!possuiVeiculos) {
            System.out.println("\n\nNão existe nenhum veículo cadastrado!\n");
            exibirMenu();
        }
    }
}
