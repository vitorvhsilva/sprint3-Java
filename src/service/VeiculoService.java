package service;

import dao.UsuarioDAO;
import dao.VeiculoDAO;
import model.Usuario;
import model.Veiculo;

import java.util.List;
import java.util.Scanner;

public class VeiculoService {
    private VeiculoDAO veiculoDAO;
    private UsuarioDAO usuarioDAO;
    private Scanner scanner;

    public VeiculoService() {
        this.veiculoDAO = new VeiculoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.scanner = new Scanner(System.in);
    }

    public void persistirVeiculo(Usuario usuario) {

        System.out.println(usuarioDAO.retornarIdPorCpf(usuario.getCpf()));

        System.out.println("Qual a marca do seu veículo?");
        String marca = scanner.nextLine();
        marca = validarString("marca", marca);

        System.out.println("Qual o modelo do seu veículo?");
        String modelo = scanner.nextLine();
        modelo = validarString("modelo", modelo);

        System.out.println("Qual o ano do seu veículo?");
        Integer ano = scanner.nextInt();scanner.nextLine();
        ano = validarAno(ano);

        System.out.println("Qual o tipo do seu veículo? \nC - Carro, M - Moto, T - Caminhão");
        String tipo = scanner.nextLine().toUpperCase();
        tipo = validarString("tipo", tipo);

        System.out.println("Qual a placa do seu veículo?");
        String placa = scanner.nextLine();
        placa = validarString("placa", placa);

        Veiculo veiculo = new Veiculo(placa, marca, modelo, ano, tipo, usuarioDAO.retornarIdPorCpf(usuario.getCpf()));
        veiculoDAO.persistirVeiculo(veiculo);
    }

    public String validarString(String caso, String dado) {
        switch (caso) {
            case "marca" -> {
                if (dado.length() < 3) {
                    System.out.println("Marca inválida! Insira a marca novamente");
                    dado = scanner.nextLine();
                    return validarString("marca", dado);
                }
                return dado;
            }
            case "modelo" -> {
                if (dado.length() < 3) {
                    System.out.println("Modelo inválido! Insira o modelo novamente");
                    dado = scanner.nextLine();
                    return validarString("modelo", dado);
                }
                return dado;
            }
            case "tipo" -> {
                if (!dado.equalsIgnoreCase("C") && !dado.equalsIgnoreCase("T")
                        && !dado.equalsIgnoreCase("M")) {
                    System.out.println("Tipo inválido! Insira o tipo novamente \nC - Carro, M - Moto, T - Caminhão");
                    dado = scanner.nextLine();
                    return validarString("tipo", dado);
                }
                return dado;
            }
            case "placa" -> {
                if (dado.length() != 7) {
                    System.out.println("Placa inválida! Insira a placa novamente");
                    dado = scanner.nextLine();
                    return validarString("placa", dado);
                }
                return dado;
            }
        }
        return null;
    }

    public Integer validarAno(Integer ano) {
        if (ano < 1950 || ano >  2024) {
            System.out.println("Ano inválido! Insira o ano novamente");
            ano = scanner.nextInt();scanner.nextLine();
            return validarAno(ano);
        }
        return ano;
    }

    public void mostrarVeiculos(Usuario usuario) {
        Long idUsuario = usuarioDAO.retornarIdPorCpf(usuario.getCpf());
        List<Veiculo> veiculos = veiculoDAO.pegarVeiculos(idUsuario);

        veiculos.forEach(System.out::println);
        System.out.println();
    }

    public String pegarPlacaDoVeiculoEscolhido(Usuario usuario) {
        List<Veiculo> veiculos = veiculoDAO.pegarVeiculos(usuarioDAO.retornarIdPorCpf(usuario.getCpf()));
        veiculos.forEach(System.out::println);
        System.out.println("Escolha um veículo pela placa");
        String placa = scanner.nextLine();

        for (Veiculo veiculo : veiculos) {
            if (placa.equals(veiculo.getPlaca())) {
                return placa;
            }
        }

        System.out.println("Opção inválida! Deseja tentar novamente ou encerrar o programa? \n1 - Tentar novamente, 2 - Encerrar");
        int opcao = scanner.nextInt();scanner.nextLine();
        if (opcao != 1) {
            System.exit(0);
        }

        return pegarPlacaDoVeiculoEscolhido(usuario);
    }

    public boolean verificarSeOUsuarioTemVeiculos(Usuario usuario) {
        List<Veiculo> veiculos = veiculoDAO.pegarVeiculos(usuarioDAO.retornarIdPorCpf(usuario.getCpf()));

        return !veiculos.isEmpty();
    }
}
