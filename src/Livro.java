/**
 * Classe que representa um livro no catálogo da biblioteca.
 */
public class Livro {

    private int id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private int quantidadeDisponivel;
    private int quantidadeTotal;

    public Livro(int id, String titulo, String autor, int anoPublicacao, int quantidadeDisponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.quantidadeTotal = quantidadeDisponivel;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public int getQuantidadeDisponivel() { return quantidadeDisponivel; }
    public int getQuantidadeTotal() { return quantidadeTotal; }

    public void diminuirQuantidade() {
        this.quantidadeDisponivel--;
    }

    public void aumentarQuantidade() {
        if (this.quantidadeDisponivel < this.quantidadeTotal) {
            this.quantidadeDisponivel++;
        }
    }

    @Override
    public String toString() {
        return String.format(
            "ID: %-4d | Título: %-30s | Autor: %-20s | Ano: %-6d | Disponíveis: %d/%d",
            id, titulo, autor, anoPublicacao, quantidadeDisponivel, quantidadeTotal
        );
    }
}
