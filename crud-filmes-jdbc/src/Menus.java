import Dominios.Filme;
import service.FilmeService;

import java.util.List;
import java.util.Scanner;

public abstract class Menus {

    private static Scanner teclado = new Scanner(System.in);

    public static int menuInicial(){
        System.out.println("""
                FILMES
                1 - LISTAR TODOS OS FILMES
                2 - LISTAR UM FILME PELO IDENTIFICADOR
                3 - ALTERAR UM FILME
                4 - CADASTRAR UM FILME
                5 - DELETAR UM FILME
                """);
        return teclado.nextInt();
    }

    public static void menuListarTodosFilmes(){
        List<Filme> filmes = FilmeService.listarTodos();
        filmes.forEach(System.out::println);
        System.out.println();
    }

    private static void menuVoltaParaInicio(){
        System.out.println("Aperte ENTER para voltar para o inicio");
        teclado.next();
    }

}
