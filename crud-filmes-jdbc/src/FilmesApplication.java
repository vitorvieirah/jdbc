public class FilmesApplication {
    public static void main(String[] args) {
        int op =  Menus.menuInicial();
        while (op != 6){

            switch (op){
                case 1 -> Menus.menuListarTodosFilmes();
                case 2 -> Menus.menuListarFilmePorId();
                case 3 -> Menus.menuAlterarFilme();
                case 4 -> Menus.menuCadastroFilme();
                default -> Menus.menuDeletarFilme();
            }
            op = Menus.menuInicial();
        }
    }
}