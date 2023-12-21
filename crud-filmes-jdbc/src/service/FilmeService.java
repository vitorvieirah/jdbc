package service;

import domains.Filme;
import dtos.DadosFilmes;
import repository.ConnectionFactory;
import repository.FilmeDAO;

import java.sql.Connection;
import java.util.List;


public class FilmeService {

    private final ConnectionFactory connection;

    public FilmeService(){
        this.connection = new ConnectionFactory();
    }

    public List<Filme> listarTodos() {
        Connection conn = connection.getConnectionDataBase();
        return new FilmeDAO(conn).listar();
    }

    public Filme buscarPorId(int id) {
        Connection conn = connection.getConnectionDataBase();
        return new FilmeDAO(conn).buscar(id);
    }

    public void alterarFilme(DadosFilmes dadosAlteracaoFilme) {
        Connection conn = connection.getConnectionDataBase();
        new FilmeDAO(conn).alterar(dadosAlteracaoFilme);
    }

    public void cadastrar(DadosFilmes dadosFilmes) {
        Connection conn = connection.getConnectionDataBase();
        new FilmeDAO(conn).cadastrar(dadosFilmes);
    }

    public void deletar(int id) {
        Connection conn = connection.getConnectionDataBase();
        new FilmeDAO(conn).deletar(id);
    }
}
