package app;

/**
 * Esta classe é responsável por ler e escrever objetos em arquivos.
 * 
 * Usamos o padrão de projeto Singleton para garantir que apenas uma instância
 * desta classe seja criada.
 * 
 * @see java.io.Serializable
 * @see java.io.ObjectOutputStream
 * @see java.io.ObjectInputStream
 */
public class Fabrica {
    private Fabrica instancia = null;

    /**
     * Construtor privado para garantir que apenas uma instância desta classe seja
     * criada.
     */
    private Fabrica() {

    }

    /**
     * Retorna a instância única desta classe.
     * 
     * @return Instância única desta classe.
     */
    public Fabrica getInstancia() {
        if (instancia == null)
            instancia = new Fabrica();
        return instancia;
    }
        
}
