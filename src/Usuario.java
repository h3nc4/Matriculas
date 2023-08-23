
/**
 * Interface que define o comportamento de um usuario do sistema
 * 
 * @see Aluno
 * @see Professor
 * @see Secretaria
 */
public interface Usuario {
    /**
     * Realiza o login do usuario
     * 
     * @param matricula matricula do usuario
     * @param passwd    senha do usuario
     * @return true se o login foi realizado com sucesso, false caso contrario
     */
    public Boolean login(String matricula, String passwd);
}