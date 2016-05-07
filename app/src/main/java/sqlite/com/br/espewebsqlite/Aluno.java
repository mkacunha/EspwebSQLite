package sqlite.com.br.espewebsqlite;

/**
 * Created by maiko on 07/05/16.
 */
public class Aluno {

    private Integer id;
    private String curso;
    private String nome;
    private String cidade;

    public Aluno(Integer id, String curso, String nome, String cidade) {
        this.id = id;
        this.curso = curso;
        this.nome = nome;
        this.cidade = cidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.nome + " : " + this.curso + " (" + this.cidade + ")";
    }
}
