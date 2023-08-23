
/**
 * Classe que representa uma disciplina
 */
public class Disciplina {

    /** disciplina é opcional */
    private Boolean opcional;

    /** creditos da disciplina */
    private Integer creditos;
    /** quantidade de alunos na disciplina */

    private Integer qtdAlunos;

    /** nome da disciplina */
    private final String NOME;

    /** numero maximo de alunos na disciplina */
    public static final Integer MAX_ALUNOS = 60;

    /** numero minimo de alunos na disciplina */
    public static final Integer MIN_ALUNOS = 3;

    /**
     * Construtor da classe Disciplina
     * 
     * @param nome     nome da disciplina
     * @param creditos creditos da disciplina
     * @param opcional disciplina é opcional
     */
    public Disciplina(String nome, Integer creditos, Boolean opcional) {
        this.NOME = nome;
        this.creditos = creditos;
        this.opcional = opcional;
        this.qtdAlunos = 0;
    }

}
