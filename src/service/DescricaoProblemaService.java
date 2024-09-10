package service;

import dao.DescricaoProblemaDAO;
import dao.UsuarioDAO;
import dao.VeiculoDAO;
import model.DescricaoProblema;
import model.Usuario;
import model.Veiculo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class DescricaoProblemaService {
    private DescricaoProblemaDAO descricaoProblemaDAO;
    private VeiculoDAO veiculoDAO;
    private UsuarioDAO usuarioDAO;
    private Scanner scanner;

    public DescricaoProblemaService() {
        this.descricaoProblemaDAO = new DescricaoProblemaDAO();
        this.veiculoDAO = new VeiculoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.scanner = new Scanner(System.in);
    }

    public DescricaoProblema persistirDescricao(Usuario usuario) {
        String placa = pegarPlacaDoVeiculoEscolhido(usuario);
        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        System.out.println("O que aconteceu com o seu veículo?");
        String descricao = scanner.nextLine();

        LocalDateTime data = LocalDateTime.now();

        DescricaoProblema descricaoProblema = new DescricaoProblema(descricao, data, idVeiculo);
        descricaoProblemaDAO.persistirDescricao(descricaoProblema);
        return descricaoProblema;
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

    public void mostrarDescricoesJaFeita(Usuario usuario) {
        String placa = pegarPlacaDoVeiculoEscolhido(usuario);
        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        List<DescricaoProblema> descricoes = descricaoProblemaDAO.pegarDescricoes(idVeiculo);

        if (descricoes.isEmpty()) {
            System.out.println("Nenhuma descrição foi feita para esse veículo!");
            return;
        }

        descricoes.forEach(System.out::println);
    }
}
