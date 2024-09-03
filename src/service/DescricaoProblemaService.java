package service;

import dao.DescricaoProblemaDAO;
import dao.UsuarioDAO;
import dao.VeiculoDAO;
import dto.VeiculoDTO;
import model.DescricaoProblema;
import model.Usuario;

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
        Long idVeiculo = pegarIdDoVeiculoEscolhido(usuario);

        System.out.println("O que aconteceu com o seu veículo?");
        String descricao = scanner.nextLine();

        LocalDateTime data = LocalDateTime.now();

        DescricaoProblema descricaoProblema = new DescricaoProblema(descricao, data, idVeiculo);
        descricaoProblemaDAO.persistirDescricao(descricaoProblema);
        return descricaoProblema;
    }

    public Long pegarIdDoVeiculoEscolhido(Usuario usuario) {
        List<VeiculoDTO> veiculos = veiculoDAO.pegarVeiculosComId(usuarioDAO.retornarIdPorCpf(usuario.getCpf()));
        veiculos.forEach(System.out::println);
        System.out.println("Escolha um veículo");
        Long idVeiculo = scanner.nextLong();scanner.nextLine();

        for (VeiculoDTO veiculo : veiculos) {
            if (idVeiculo.equals(veiculo.getIdVeiculo())) {
                return idVeiculo;
            }
        }

        System.out.println("Opção inválida! Deseja tentar novamente ou encerrar o programa? \n1 - Tentar novamente, 2 - Encerrar");
        int opcao = scanner.nextInt();scanner.nextLine();
        if (opcao != 1) {
            System.exit(0);
        }

        return pegarIdDoVeiculoEscolhido(usuario);
    }

    public void mostrarDescricoesJaFeita(Usuario usuario) {
        Long idVeiculo = pegarIdDoVeiculoEscolhido(usuario);

        List<DescricaoProblema> descricoes = descricaoProblemaDAO.pegarDescricoes(idVeiculo);

        if (descricoes.isEmpty()) {
            System.out.println("Nenhuma descrição foi feita para esse veículo!");
            return;
        }

        descricoes.forEach(System.out::println);
    }
}
