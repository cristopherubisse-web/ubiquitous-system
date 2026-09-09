import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Ponto de entrada do sistema de gestão da Biblioteca Municipal.
 * Apresenta um menu interativo em consola.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Biblioteca biblioteca = new Biblioteca();

    public static void main(String[] args) {
        carregarDadosIniciais();

        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    menuRegistarLivro();
                    break;
                case 2:
                    menuConsultarCatalogo();
                    break;
                case 3:
                    menuRegistarUtilizador();
                    break;
                case 4:
                    menuEfetuarEmprestimo();
                    break;
                case 5:
                    menuRegistarDevolucao();
                    break;
                case 6:
                    menuEstatisticas();
                    break;
                case 0:
                    System.out.println("A encerrar o sistema. Até breve!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=========================================");
        System.out.println("   SISTEMA DE GESTÃO - BIBLIOTECA MUNICIPAL");
        System.out.println("=========================================");
        System.out.println("1. Registar Livro");
        System.out.println("2. Consultar Catálogo");
        System.out.println("3. Registar Utilizador");
        System.out.println("4. Efetuar Empréstimo");
        System.out.println("5. Registar Devolução");
        System.out.println("6. Estatísticas");
        System.out.println("0. Sair");
        System.out.println("=========================================");
    }

    private static void menuRegistarLivro() {
        System.out.println("\n--- Registo de Livro ---");
        int id = lerInteiro("ID do livro: ");
        String titulo = lerTexto("Título: ");
        String autor = lerTexto("Autor: ");
        int ano = lerInteiro("Ano de publicação: ");
        int quantidade = lerInteiro("Quantidade disponível: ");

        biblioteca.registarLivro(id, titulo, autor, ano, quantidade);
    }

    private static void menuConsultarCatalogo() {
        System.out.println("\n--- Consulta de Catálogo ---");
        System.out.println("1. Listar todos os livros");
        System.out.println("2. Pesquisar por autor");
        System.out.println("3. Pesquisar por título");
        int opcao = lerInteiro("Escolha uma opção: ");

        switch (opcao) {
            case 1:
                biblioteca.listarTodosLivros();
                break;
            case 2:
                String autor = lerTexto("Nome do autor: ");
                biblioteca.pesquisarPorAutor(autor);
                break;
            case 3:
                String titulo = lerTexto("Título (ou parte dele): ");
                biblioteca.pesquisarPorTitulo(titulo);
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private static void menuRegistarUtilizador() {
        System.out.println("\n--- Registo de Utilizador ---");
        int id = lerInteiro("ID do utilizador: ");
        String nome = lerTexto("Nome: ");
        biblioteca.registarUtilizador(id, nome);
    }

    private static void menuEfetuarEmprestimo() {
        System.out.println("\n--- Gestão de Empréstimos ---");
        System.out.println("1. Efetuar novo empréstimo");
        System.out.println("2. Listar empréstimos pendentes");
        int opcao = lerInteiro("Escolha uma opção: ");

        if (opcao == 1) {
            int idLivro = lerInteiro("ID do livro: ");
            int idUtilizador = lerInteiro("ID do utilizador: ");
            biblioteca.efetuarEmprestimo(idLivro, idUtilizador);
        } else if (opcao == 2) {
            biblioteca.listarEmprestimosPendentes();
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private static void menuRegistarDevolucao() {
        System.out.println("\n--- Registo de Devolução ---");
        int idLivro = lerInteiro("ID do livro: ");
        int idUtilizador = lerInteiro("ID do utilizador: ");
        biblioteca.registarDevolucao(idLivro, idUtilizador);
    }

    private static void menuEstatisticas() {
        System.out.println("\n--- Estatísticas ---");
        biblioteca.livroMaisEmprestado();
        biblioteca.totalLivrosRequisitados();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro: introduza um número inteiro válido.");
            }
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static void carregarDadosIniciais() {
        biblioteca.registarLivro(1, "Dom Casmurro", "Machado de Assis", 1899, 3);
        biblioteca.registarLivro(2, "O Alquimista", "Paulo Coelho", 1988, 2);
        biblioteca.registarLivro(3, "Terra Sonâmbula", "Mia Couto", 1992, 4);
        biblioteca.registarUtilizador(1, "Carlos Machava");
        biblioteca.registarUtilizador(2, "Ana Sitoe");
    }
}
