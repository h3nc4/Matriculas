
/**
 * Classe que representa uma secretaria
 * 
 * @see Usuario
 */
public class Secretaria implements Usuario {

    /** matricula da secretaria */
    private String matricula;

    /** senha da secretaria */
    private String passwd;

    /**
     * Construtor da classe Secretaria
     * 
     * @param matricula matricula da secretaria
     * @param passwd    senha da secretaria
     */
    public Secretaria(String matricula, String passwd) {
        this.matricula = matricula;
        this.passwd = passwd;
    }

    @Override
    public Boolean login(String matricula, String passwd) {
        return this.matricula.equals(matricula) && this.passwd.equals(passwd);
    }
}