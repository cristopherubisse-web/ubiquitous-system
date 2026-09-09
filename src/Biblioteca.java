import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe principal de gestão do sistema da biblioteca.
 * Manipula os dados em memória através de arrays (Livro[], Utilizador[])
 * e de uma matriz (String[][]) para o histórico de empréstimos.
 */
public class Biblioteca {

    // Capacidades máximas da base de dados simulada em memória
    private static final int CAPACIDADE_LIVROS = 100;
    private static final int CAPACIDADE_UTILIZADORES = 100;
    private static final int CAPACIDADE_EMPRESTIMOS = 500;

    // Colunas da matriz de histórico de empréstimos
    private static final int COL_ID_LIVRO = 0;
    private static final int COL_ID_UTILIZADOR = 1;
    private static final int COL_DATA_EMPRESTIMO = 2;
    private static final int COL_DATA_DEVOLUCAO = 3;
    private static final int NUM_COLUNAS_EMPRESTIMO = 4;

    private Livro[] livros;
    private int totalLivros;

    private Utilizador[] utilizadores;
    private int totalUtilizadores;

    // Matriz que guarda o histórico de empréstimos (linhas = empréstimos, colunas = dados do empréstimo)
    private String[][] historicoEmprestimos;
    private int totalEmprestimos;

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Biblioteca() {
        livros = new Livro[CAPACIDADE_LIVROS];
        totalLivros = 0;

        utilizadores = new Utilizador[CAPACIDADE_UTILIZADORES];
        totalUtilizadores = 0;

        historicoEmprestimos = new String[CAPACIDADE_EMPRESTIMOS][NUM_COLUNAS_EMPRESTIMO];
        totalEmprestimos = 0;
    }

    // ============================================================
    // 1. REGISTO DE LIVROS
    // ============================================================

    public boolean registarLivro(int id, String titulo, String autor, int anoPublicacao, int quantidade) {
        if (totalLivros >= CAPACIDADE_LIVROS) {
            System.out.println("Erro: catálogo cheio. Não é possível registar mais livros.");
            return false;
        }

        if (buscarLivroPorId(id) != null) {
            System.out.println("Erro: já existe um livro registado com o ID " + id + ".");
            return false;
        }

        if (titulo == null || titulo.trim().isEmpty() || autor == null || autor.trim().isEmpty()) {
            System.out.println("Erro: título e autor não podem estar vazios.");
            return false;
        }

        if (anoPublicacao < 0 || quantidade < 0) {
            System.out.println("Erro: ano de publicação e quantidade devem ser valores positivos.");
            return false;
        }

        livros[totalLivros] = new Livro(id, titulo, autor, anoPublicacao, quantidade);
        totalLivros++;
        System.out.println("Livro \"" + titulo + "\" registado com sucesso.");
        return true;
    }

    // ============================================================
    // 2. CONSULTA DE CATÁLOGO
    // ============================================================

    public void listarTodosLivros() {
        if (totalLivros == 0) {
            System.out.println("O catálogo está vazio.");
            return;
        }
        System.out.println("\n--- Catálogo de Livros (" + totalLivros + ") ---");
        for (int i = 0; i < totalLivros; i++) {
            System.out.println(livros[i]);
        }
    }

    public void pesquisarPorAutor(String autor) {
        boolean encontrado = false;
        System.out.println("\n--- Resultados da pesquisa por autor: \"" + autor + "\" ---");
        for (int i = 0; i < totalLivros; i++) {
            if (livros[i].getAutor().equalsIgnoreCase(autor.trim())) {
                System.out.println(livros[i]);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Nenhum livro encontrado para o autor indicado.");
        }
    }

    public void pesquisarPorTitulo(String titulo) {
        boolean encontrado = false;
        String termo = titulo.trim().toLowerCase();
        System.out.println("\n--- Resultados da pesquisa por título: \"" + titulo + "\" ---");
        for (int i = 0; i < totalLivros; i++) {
            if (livros[i].getTitulo().toLowerCase().contains(termo)) {
                System.out.println(livros[i]);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Nenhum livro encontrado com o título indicado.");
        }
    }

    // ============================================================
    // REGISTO DE UTILIZADORES (necessário para a Gestão de Empréstimos)
    // ============================================================

    public boolean registarUtilizador(int id, String nome) {
        if (totalUtilizadores >= CAPACIDADE_UTILIZADORES) {
            System.out.println("Erro: limite de utilizadores atingido.");
            return false;
        }

        if (buscarUtilizadorPorId(id) != null) {
            System.out.println("Erro: já existe um utilizador registado com o ID " + id + ".");
            return false;
        }

        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: nome do utilizador não pode estar vazio.");
            return false;
        }

        utilizadores[totalUtilizadores] = new Utilizador(id, nome);
        totalUtilizadores++;
        System.out.println("Utilizador \"" + nome + "\" registado com sucesso.");
        return true;
    }

    // ============================================================
    // 3. GESTÃO DE EMPRÉSTIMOS
    // ============================================================

    public boolean efetuarEmprestimo(int idLivro, int idUtilizador) {
        Livro livro = buscarLivroPorId(idLivro);
        if (livro == null) {
            System.out.println("Erro: livro com ID " + idLivro + " não encontrado.");
            return false;
        }

        Utilizador utilizador = buscarUtilizadorPorId(idUtilizador);
        if (utilizador == null) {
            System.out.println("Erro: utilizador com ID " + idUtilizador + " não encontrado.");
            return false;
        }

        if (livro.getQuantidadeDisponivel() <= 0) {
            System.out.println("Erro: não há exemplares disponíveis para \"" + livro.getTitulo() + "\".");
            return false;
        }

        if (totalEmprestimos >= CAPACIDADE_EMPRESTIMOS) {
            System.out.println("Erro: limite de registos de empréstimo atingido.");
            return false;
        }

        livro.diminuirQuantidade();

        historicoEmprestimos[totalEmprestimos][COL_ID_LIVRO] = String.valueOf(idLivro);
        historicoEmprestimos[totalEmprestimos][COL_ID_UTILIZADOR] = String.valueOf(idUtilizador);
        historicoEmprestimos[totalEmprestimos][COL_DATA_EMPRESTIMO] = LocalDate.now().format(FORMATO_DATA);
        historicoEmprestimos[totalEmprestimos][COL_DATA_DEVOLUCAO] = "pendente";
        totalEmprestimos++;

        System.out.println("Empréstimo registado: \"" + livro.getTitulo() + "\" para " + utilizador.getNome() + ".");
        return true;
    }

    public boolean registarDevolucao(int idLivro, int idUtilizador) {
        // Procura o empréstimo pendente mais antigo para este livro/utilizador
        for (int i = 0; i < totalEmprestimos; i++) {
            boolean mesmoLivro = historicoEmprestimos[i][COL_ID_LIVRO].equals(String.valueOf(idLivro));
            boolean mesmoUtilizador = historicoEmprestimos[i][COL_ID_UTILIZADOR].equals(String.valueOf(idUtilizador));
            boolean pendente = historicoEmprestimos[i][COL_DATA_DEVOLUCAO].equals("pendente");

            if (mesmoLivro && mesmoUtilizador && pendente) {
                historicoEmprestimos[i][COL_DATA_DEVOLUCAO] = LocalDate.now().format(FORMATO_DATA);
                Livro livro = buscarLivroPorId(idLivro);
                if (livro != null) {
                    livro.aumentarQuantidade();
                    System.out.println("Devolução registada com sucesso: \"" + livro.getTitulo() + "\".");
                }
                return true;
            }
        }

        System.out.println("Erro: não foi encontrado um empréstimo pendente para o livro " + idLivro
                + " e utilizador " + idUtilizador + ".");
        return false;
    }

    public void listarEmprestimosPendentes() {
        boolean encontrou = false;
        System.out.println("\n--- Empréstimos Pendentes ---");
        for (int i = 0; i < totalEmprestimos; i++) {
            if (historicoEmprestimos[i][COL_DATA_DEVOLUCAO].equals("pendente")) {
                Livro livro = buscarLivroPorId(Integer.parseInt(historicoEmprestimos[i][COL_ID_LIVRO]));
                Utilizador utilizador = buscarUtilizadorPorId(Integer.parseInt(historicoEmprestimos[i][COL_ID_UTILIZADOR]));
                System.out.println("Livro: " + (livro != null ? livro.getTitulo() : "?")
                        + " | Utilizador: " + (utilizador != null ? utilizador.getNome() : "?")
                        + " | Data do empréstimo: " + historicoEmprestimos[i][COL_DATA_EMPRESTIMO]);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Não existem empréstimos pendentes.");
        }
    }

    // ============================================================
    // 4. ESTATÍSTICAS
    // ============================================================

    public void livroMaisEmprestado() {
        if (totalEmprestimos == 0) {
            System.out.println("Ainda não foram registados empréstimos.");
            return;
        }

        int[] contagens = new int[totalLivros];

        for (int i = 0; i < totalEmprestimos; i++) {
            int idLivro = Integer.parseInt(historicoEmprestimos[i][COL_ID_LIVRO]);
            for (int j = 0; j < totalLivros; j++) {
                if (livros[j].getId() == idLivro) {
                    contagens[j]++;
                    break;
                }
            }
        }

        int indiceMax = 0;
        for (int j = 1; j < totalLivros; j++) {
            if (contagens[j] > contagens[indiceMax]) {
                indiceMax = j;
            }
        }

        if (contagens[indiceMax] == 0) {
            System.out.println("Ainda não foram registados empréstimos.");
            return;
        }

        System.out.println("Livro mais emprestado: \"" + livros[indiceMax].getTitulo() + "\" ("
                + contagens[indiceMax] + " empréstimo(s)).");
    }

    public void totalLivrosRequisitados() {
        System.out.println("Número total de empréstimos requisitados: " + totalEmprestimos);
    }

    // ============================================================
    // MÉTODOS AUXILIARES DE BUSCA
    // ============================================================

    public Livro buscarLivroPorId(int id) {
        for (int i = 0; i < totalLivros; i++) {
            if (livros[i].getId() == id) {
                return livros[i];
            }
        }
        return null;
    }

    public Utilizador buscarUtilizadorPorId(int id) {
        for (int i = 0; i < totalUtilizadores; i++) {
            if (utilizadores[i].getId() == id) {
                return utilizadores[i];
            }
        }
        return null;
    }

    public void listarUtilizadores() {
        if (totalUtilizadores == 0) {
            System.out.println("Não existem utilizadores registados.");
            return;
        }
        System.out.println("\n--- Utilizadores Registados (" + totalUtilizadores + ") ---");
        for (int i = 0; i < totalUtilizadores; i++) {
            System.out.println(utilizadores[i]);
        }
    }
}
