package br.com.alura.bytebank.domain.conta;

import br.com.alura.bytebank.domain.cliente.Cliente;
import br.com.alura.bytebank.domain.cliente.DadosCadastroCliente;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ContaDAO {

    private Connection conn;

    ContaDAO(Connection connection){
        this.conn = connection;
    }

    public void salvar(DadosAberturaConta dadosDaConta){
        var cliente = new Cliente(dadosDaConta.dadosCliente());
        var conta = new Conta(dadosDaConta.numero(), cliente, BigDecimal.ZERO, false);

        String sql = "INSERT INTO conta (numero, saldo, cliente_nome, cliente_cpf, cliente_email) VALUES (?, ?, ?, ?, ?)";

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, conta.getNumero());
            preparedStatement.setBigDecimal(2, BigDecimal.ZERO);
            preparedStatement.setString(3, dadosDaConta.dadosCliente().nome());
            preparedStatement.setString(4, dadosDaConta.dadosCliente().cpf());
            preparedStatement.setString(5, dadosDaConta.dadosCliente().email());

            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Set<Conta> listar(){
        PreparedStatement ps;
        ResultSet resultSet;
        Set<Conta> contas = new HashSet<>();

        String sql = "SELECT * FROM conta WHERE inativo = FALSE";
        try{
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int numero = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                Boolean inativo = resultSet.getBoolean(6);

                Cliente cliente = new Cliente(new DadosCadastroCliente(nome, cpf, email));
                contas.add(new Conta(numero, cliente, saldo, inativo));
            }

            ps.close();
            resultSet.close();
            conn.close();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
        return contas;
    }

    public Conta buscarPorNumero(int numeroConta){
        String sql = "SELECT * FROM conta WHERE numero = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        Conta conta = null;

        try {
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, numeroConta);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int numero = resultSet.getInt(1);
                BigDecimal saldo = resultSet.getBigDecimal(2);
                String nome = resultSet.getString(3);
                String cpf = resultSet.getString(4);
                String email = resultSet.getString(5);
                Boolean inativo = resultSet.getBoolean(6);

                Cliente cliente = new Cliente(new DadosCadastroCliente(nome, cpf, email));
                conta = new Conta(numero, cliente, saldo, inativo);
            }

            preparedStatement.close();
            resultSet.close();
            conn.close();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }

        if(conta != null){
            return conta;
        }else {
            throw new RuntimeException("Conta não foi encontrada");
        }
    }

    public void altera(int numero, BigDecimal valor){
        PreparedStatement ps;

        String sql = "UPDATE conta SET saldo = ? WHERE numero = ?";

        try {
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sql);


            ps.setBigDecimal(1, valor);
            ps.setInt(2, numero);

            ps.execute();
            conn.commit();
            ps.close();
            conn.close();

        }catch (SQLException ex){
            try{
                conn.rollback();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
            throw new RuntimeException(ex);
        }
    }

    public void exclusaoLogica(int numero){
        PreparedStatement ps;

        String sql = "UPDATE conta SET inativo = true WHERE numero = ?";

        try{
            ps = conn.prepareStatement(sql);

            ps.setInt(1, numero);

            ps.execute();
            ps.close();
            conn.close();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }
}
