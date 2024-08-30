package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsuarioService {
    private UsuarioDAO usuarioDAO;
    private Scanner scanner;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
        this.scanner = new Scanner(System.in);
    }

    public Usuario persistirUsuario() {
        System.out.println("Qual seu nome?");
        String nome = scanner.nextLine();
        nome = validarDado("nome", nome);

        System.out.println("Qual seu email?");
        String email = scanner.nextLine();
        email = validarDado("email", email);

        System.out.println("Qual será a sua senha?");
        String senha = scanner.nextLine();
        senha = validarDado("senha", senha);

        System.out.println("Qual é o seu gênero? (M - Masculino, F - Feminino, O - Outros)");
        String genero = scanner.nextLine().toUpperCase();
        genero = validarDado("genero", genero);

        System.out.println("Qual seu telefone?");
        String telefone = scanner.nextLine();
        telefone = validarDado("telefone", telefone);

        System.out.println("Qual seu cpf?");
        String cpf = scanner.nextLine();
        cpf = validarDado("cpf", cpf);

        if (usuarioDAO.usuarioExistePorCpf(cpf)) {
            System.out.println("Usuário já existe!");
            return null;
        }

        Usuario usuario = new Usuario(nome, email, senha, genero, telefone, cpf);
        usuarioDAO.persistirUsuario(usuario);
        return usuario;
    }

    public Usuario fazerLogin() {
        System.out.println("Qual seu email?");
        String email = scanner.nextLine();

        System.out.println("Qual a sua senha?");
        String senha = scanner.nextLine();

        return usuarioDAO.buscarPorLogin(email, senha);
    }

    private String validarDado(String caso, String dado) {
        switch (caso){
            case "nome" -> {
                if (dado.length() < 3) {
                    System.out.println("Nome muito pequeno! Digite novamente");
                    dado = scanner.nextLine();
                    return validarDado("nome", dado);
                }
                return dado;
            }
            case "email" -> {
                if (!validarEmail(dado)) {
                    System.out.println("Email inválido! Digite novamente");
                    dado = scanner.nextLine();
                    return validarDado("email", dado);
                }
                return dado;
            }
            case "senha" -> {
                if (dado.length() < 8) {
                    System.out.println("Senha inválida! Digite novamente");
                    dado = scanner.nextLine();
                    return validarDado("senha", dado);
                }
                return dado;
            }
            case "genero" -> {
                if (!dado.equalsIgnoreCase("O") && !dado.equalsIgnoreCase("F")
                        && !dado.equalsIgnoreCase("M")) {
                    System.out.println("Gênero inválido! Digite novamente (M - Masculino, F - Feminino, O - Outros)");
                    dado = scanner.nextLine().toUpperCase();
                    return validarDado("genero", dado);
                }
                return dado;
            }
            case "telefone" -> {
                if (dado.length() != 11) {
                    System.out.println("Telefone inválido! Digite novamente");
                    dado = scanner.nextLine();
                    return validarDado("telefone", dado);
                }
                return dado;
            }
            case "cpf" -> {
                if (dado.length() != 11) {
                    System.out.println("CPF inválido! Digite novamente");
                    dado = scanner.nextLine();
                    return validarDado("cpf", dado);
                }
                return dado;
            }
        }
        return null;
    }

    public boolean validarEmail(String email) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
