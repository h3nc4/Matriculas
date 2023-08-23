import java.util.List;

/**
 * Classe que representa um professor
 * 
 * @see Usuario
 */
public class Professor implements Usuario {

    /** matricula do professor */
    private String matricula;

    /** senha do professor */
    private String passwd;

    /** disciplinas do professor */
    private List<Disciplina> disciplinas;

    /**
     * Construtor da classe Professor
     * 
     * @param matricula matricula do professor
     * @param passwd    senha do professor
     */
    public Professor(String matricula, String passwd) {
        this.matricula = matricula;
        this.passwd = passwd;
    }

    /**
     * Adiciona uma disciplina ao professor
     * 
     * @param disciplina disciplina a ser adicionada
     */
    public void addDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    @Override
    public Boolean login(String matricula, String passwd) {
        return this.matricula.equals(matricula) && this.passwd.equals(passwd);
    }
}