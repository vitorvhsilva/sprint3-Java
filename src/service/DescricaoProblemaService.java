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

    public DescricaoProblemaService(DescricaoProblemaDAO descricaoProblemaDAO, VeiculoDAO veiculoDAO, UsuarioDAO usuarioDAO) {
        this.descricaoProblemaDAO = descricaoProblemaDAO;
        this.veiculoDAO = veiculoDAO;
        this.usuarioDAO = usuarioDAO;
        this.scanner = new Scanner(System.in);
    }

    public DescricaoProblema persistirDescricao(Usuario usuario) {
        VeiculoService veiculoService = new VeiculoService(veiculoDAO, usuarioDAO);
        String placa = veiculoService.pegarPlacaDoVeiculoEscolhido(usuario);
        veiculoService.fecharConexoes();

        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        System.out.println("O que aconteceu com o seu veículo?");
        String descricao = scanner.nextLine();

        LocalDateTime data = LocalDateTime.now();

        DescricaoProblema descricaoProblema = new DescricaoProblema(descricao, data, idVeiculo);
        descricaoProblemaDAO.persistirDescricao(descricaoProblema);
        return descricaoProblema;
    }

    public void mostrarDescricoesJaFeita(Usuario usuario) {
        VeiculoService veiculoService = new VeiculoService(veiculoDAO, usuarioDAO);
        String placa = veiculoService.pegarPlacaDoVeiculoEscolhido(usuario);
        veiculoService.fecharConexoes();

        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        List<DescricaoProblema> descricoes = descricaoProblemaDAO.pegarDescricoes(idVeiculo);

        if (descricoes.isEmpty()) {
            System.out.println("Nenhuma descrição foi feita para esse veículo!");
            return;
        }

        descricoes.forEach(System.out::println);
    }

    public void fecharConexoes() {
        descricaoProblemaDAO.fecharConexao();
        usuarioDAO.fecharConexao();
        veiculoDAO.fecharConexao();
    }
}
