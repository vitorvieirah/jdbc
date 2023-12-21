import com.sun.security.jgss.GSSUtil;
import domains.Filme;
import dtos.DadosFilmes;
import service.FilmeService;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class Menus {

    private static Scanner teclado = new Scanner(System.in);

    private static final FilmeService filmeService = new FilmeService();

    private static boolean encontrado;

    public static int menuInicial(){
        System.out.println("""
                FILMES
                1 - LISTAR TODOS OS FILMES
                2 - LISTAR UM FILME PELO IDENTIFICADOR
                3 - ALTERAR UM FILME
                4 - CADASTRAR UM FILME
                5 - DELETAR UM FILME
                6 - SAIR
                """);
        return teclado.nextInt();
    }

    public static void menuListarTodosFilmes(){
        List<Filme> filmes = filmeService.listarTodos();
        encontrado = menuListaDeFilmesNaoEncontrado(filmes);

        if(encontrado)
            filmes.forEach(System.out::println);

        menuVoltaParaInicio();
    }

    public static void menuListarFilmePorId(){
        System.out.println("Digite o código identificador do filme: ");
        var filme = filmeService.buscarPorId(teclado.nextInt());
        encontrado = menuFilmeNaoEncontrado(filme);

        if(encontrado)
            System.out.println(filme);

        menuVoltaParaInicio();
    }

    public static void menuAlterarFilme(){
        System.out.println("Digite o código identificador do filme: ");
        int idFilme = teclado.nextInt();
        System.out.println("""
                Digite o que deseja alterar:
                1 - NOME
                2 - DATA DE LANÇAMENTO
                3 - DURAÇÃO
                4 - CLASSIFICAÇÃO
                5 - ALTERAR TUDO
                """);
        int opcao = teclado.nextInt();
        switch (opcao) {
            case 1 -> {
                System.out.println("Digite o novo nome: ");
                filmeService.alterarFilme(new DadosFilmes(idFilme, teclado.next(), null, null, null));
            }
            case 2 -> {
                System.out.println("Digite a nova data de lançamento: ");
                filmeService.alterarFilme(new DadosFilmes(idFilme, null, teclado.next(), null, null));
            }
            case 3 -> {
                System.out.println("Digite a nova duração: ");
                filmeService.alterarFilme(new DadosFilmes(idFilme, null, null, teclado.next(), null));
            }
            case 4 -> {
                System.out.println("Digite a nova classificação: ");
                filmeService.alterarFilme(new DadosFilmes(idFilme, null, null, null, teclado.next()));
            }
            default -> {
                System.out.println("Digite o novo nome: ");
                String nome = teclado.next();
                System.out.println("Digite a nova data de lançamento: ");
                String dataLancamento = teclado.next();
                System.out.println("Digite a nova duração: ");
                String duracao = teclado.next();
                System.out.println("Digite a nova classificação: ");
                String classificao = teclado.next();
                filmeService.alterarFilme(new DadosFilmes(idFilme, nome, dataLancamento, duracao, classificao));
            }
        }
        menuOperacaoSucesso();
        menuVoltaParaInicio();
    }

    public static void menuCadastroFilme(){
        System.out.println("Digite o nome do filme: ");
        String nome = teclado.next();
        System.out.println("Digite a data de lançamento: ");
        String dataLancamento = teclado.next();
        System.out.println("Digite a duração do filme: ");
        String duracao = teclado.next();
        System.out.println("Digite a classificação do filme: ");
        String classificacao = teclado.next();
        filmeService.cadastrar(new DadosFilmes(null, nome, dataLancamento, duracao, classificacao));
        menuOperacaoSucesso();
        menuVoltaParaInicio();
    }

    public static void menuDeletarFilme(){
        System.out.println("Digite o código do filme que deseja deletar: ");
        filmeService.deletar(teclado.nextInt());
        menuOperacaoSucesso();
        menuVoltaParaInicio();
    }

    private static void menuVoltaParaInicio(){
        System.out.println("Aperte ENTER para voltar para o inicio");
        teclado.next();
    }

    private static void menuOperacaoSucesso(){
        System.out.println("Operação realizada com sucesso");
    }

    private static boolean menuFilmeNaoEncontrado(Filme filme){
        if(filme == null) {
            System.out.println("Filme não encontrado");
            return false;
        }
        return true;
    }

    private static boolean menuListaDeFilmesNaoEncontrado(List<Filme> filmes){
        if(filmes.size() == 0){
            System.out.println("Ainda não há filmes cadastrados");
            return false;
        }
        return true;
    }
}
