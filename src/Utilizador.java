/**
 * Classe que representa um utilizador registado na biblioteca.
 */
public class Utilizador {

    private int id;
    private String nome;

    public Utilizador(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }

    @Override
    public String toString() {
        return String.format("ID: %-4d | Nome: %s", id, nome);
    }
}
