import java.util.List;

/**
 * Classe que representa um curso
 */
public class Curso {

    /** nome do curso */
    private final String nome;

    /** creditos do curso */
    private Integer creditos;

    /** disciplinas do curso */
    private List<Disciplina> disciplinas;

    /** disciplinas iniciais do curso */
    private Disciplina[] disciplinasIni;

    /**
     * Construtor da classe Curso
     * 
     * @param nome           nome do curso
     * @param disciplinas    disciplinas do curso
     * @param creditos       creditos do curso
     * @param disciplinasIni disciplinas iniciais do curso
     */
    public Curso(String nome, List<Disciplina> disciplinas, int creditos, Disciplina[] disciplinasIni) {
        this.nome = nome;
        this.disciplinas = disciplinas;
        this.creditos = creditos;
        this.disciplinasIni = disciplinasIni;
    }

    public Disciplina[] getDisciplinasIniciais() {
        return this.disciplinasIni;
    }

}
