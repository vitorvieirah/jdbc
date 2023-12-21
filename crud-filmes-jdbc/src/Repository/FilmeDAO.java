package repository;

import domains.Filme;
import dtos.DadosFilmes;
import execptions.ExecptionDataBase;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
public class FilmeDAO {

    private Connection conn;

    public List<Filme> listar() {

        PreparedStatement ps;
        ResultSet resultSet;
        List<Filme> filmes = new ArrayList<>();
        String sql = "SELECT * FROM filmes";

        try {
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt(1);
                String nome  = resultSet.getString(2);
                String dataLancamento = resultSet.getString(3);
                String duracao = resultSet.getString(4);
                String classificao = resultSet.getString(5);

                filmes.add(new Filme(id, nome, dataLancamento, duracao, classificao));
            }

            ps.close();
            resultSet.close();
            conn.close();
        }catch (SQLException ex){
            throw new ExecptionDataBase(ex.getMessage());
        }
        return filmes;
    }

    public Filme buscar(int id) {

        Filme filme = null;
        PreparedStatement ps;
        ResultSet resultSet;
        String sql = "SELECT * FROM filmes WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            resultSet = ps.executeQuery();

            while (resultSet.next()){
                String nome  = resultSet.getString(2);
                String dataLancamento = resultSet.getString(3);
                String duracao = resultSet.getString(4);
                String classificao = resultSet.getString(5);

                filme = new Filme(id, nome, dataLancamento, duracao, classificao);
            }

            ps.close();
            conn.close();
            resultSet.close();

            return filme;
        }catch (SQLException ex){
            throw new ExecptionDataBase(ex.getMessage());
        }
    }

    public void alterar(DadosFilmes dadosAlteracaoFilme) {
        PreparedStatement ps;

        try{
            ps = manegerQuery(dadosAlteracaoFilme);

            ps.execute();
            ps.close();
            conn.close();
        }catch (SQLException ex){
            throw new ExecptionDataBase(ex.getMessage());
        }
    }

    public void cadastrar(DadosFilmes dadosFilmes) {
        PreparedStatement ps;

        String sql = "INSERT INTO filmes (id, nome, dataLancamento, duracao, classificao) VALUES (?,?,?,?,?)";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, geradorDeId());
            ps.setString(2, dadosFilmes.nome());
            ps.setString(3, dadosFilmes.dataLancamento());
            ps.setString(4, dadosFilmes.duracao());
            ps.setString(5, dadosFilmes.classificao());

            ps.execute();
            ps.close();
            conn.close();
        }catch (SQLException ex){
            throw new ExecptionDataBase(ex.getMessage());
        }
    }

    public void deletar(int id) {
        PreparedStatement ps;

        String sql = "DELETE FROM filmes WHERE id = ?";

        try{
            ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            ps.execute();
            ps.close();
            conn.close();
        }catch (SQLException ex){
            throw new ExecptionDataBase(ex.getMessage());
        }
    }

    private PreparedStatement manegerQuery(DadosFilmes dadosFilmes) throws SQLException {
        String sql;
        PreparedStatement ps;
        if(dadosFilmes.nome() != null && dadosFilmes.classificao() != null && dadosFilmes.duracao() != null && dadosFilmes.dataLancamento() != null){
            sql = "UPDATE filmes SET nome = ?, dataLancamento = ?, duracao = ?, classificao = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dadosFilmes.nome());
            ps.setString(2, dadosFilmes.dataLancamento());
            ps.setString(3, dadosFilmes.duracao());
            ps.setString(4, dadosFilmes.classificao());
            ps.setInt(5, dadosFilmes.id());
            return ps;
        }else if(dadosFilmes.dataLancamento() != null){
            sql = "UPDATE filmes SET dataLancamento = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dadosFilmes.dataLancamento());
            ps.setInt(2, dadosFilmes.id());
            return ps;
        }else if(dadosFilmes.duracao() != null){
            sql = "UPDATE filmes SET duracao = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dadosFilmes.duracao());
            ps.setInt(2, dadosFilmes.id());
            return ps;
        }else if(dadosFilmes.classificao() != null){
            sql = "UPDATE filmes SET classificao = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dadosFilmes.classificao());
            ps.setInt(2, dadosFilmes.id());
            return ps;
        }else {
            sql = "UPDATE filmes SET nome = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dadosFilmes.nome());
            ps.setInt(2, dadosFilmes.id());
            return ps;
        }
    }

    private static Integer geradorDeId() {
        Random random = new Random();
        return random.nextInt(200);
    }
}
