package service;

import dao.DescricaoProblemaDAO;
import dao.UsuarioDAO;
import dao.VeiculoDAO;
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
        String placa = new VeiculoService().pegarPlacaDoVeiculoEscolhido(usuario);
        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        System.out.println("O que aconteceu com o seu veículo?");
        String descricao = scanner.nextLine();

        LocalDateTime data = LocalDateTime.now();

        DescricaoProblema descricaoProblema = new DescricaoProblema(descricao, data, idVeiculo);
        descricaoProblemaDAO.persistirDescricao(descricaoProblema);
        return descricaoProblema;
    }

    public void mostrarDescricoesJaFeita(Usuario usuario) {
        String placa = new VeiculoService().pegarPlacaDoVeiculoEscolhido(usuario);
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
