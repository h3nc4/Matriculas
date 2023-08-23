import java.util.stream.Stream;

/**
 * Classe que representa um aluno
 * 
 * @see Usuario
 */
public class Aluno implements Usuario {

    /** matricula do aluno */
    private String matricula;

    /** senha do aluno */
    private String passwd;

    /** curso do aluno */
    private Curso curso;

    /** disciplinas do aluno */
    private Disciplina[] disciplinas;

    /**
     * Construtor da classe Aluno, alunos recém matriculaos não possuem disciplinas
     * escolhidas
     * 
     * @param matricula matricula do aluno
     * @param passwd    senha do aluno
     * @param curso     curso do aluno
     */
    public Aluno(String matricula, String passwd, Curso curso) {
        this.matricula = matricula;
        this.passwd = passwd;
        this.curso = curso;
        this.disciplinas = this.curso.getDisciplinasIniciais();
    }

    @Override
    public Boolean login(String matricula, String passwd) {
        return this.matricula.equals(matricula) && this.passwd.equals(passwd); // verificar se a matricula e a senha são
                                                                               // iguais as do aluno
    }

    /**
     * Matricula o aluno em disciplinas
     * 
     * @param disciplinasObg disciplinas obrigatórias, podem ser no máximo 4
     * @param disciplinasOpt disciplinas opcionais, podem ser no máximo 2
     * @return {@code TRUE} se o aluno foi matriculado em pelo menos uma disciplina
     *         e {@code FALSE} caso contrário
     */
    public Boolean matricular(Disciplina[] disciplinasObg, Disciplina[] disciplinasOpt) {
        if (disciplinasObg.length <= 4 && disciplinasOpt.length <= 2) {
            this.disciplinas = Stream.concat(
                    Stream.of(new Disciplina[] {}, disciplinasObg), Stream.of(disciplinasOpt))
                    .toArray(Disciplina[]::new);

            return this.disciplinas.length > 0; // vericar se o aluno foi matriculado em pelo menos uma disciplina
        }

        return false; // não foi possível matricular o aluno
    }

}