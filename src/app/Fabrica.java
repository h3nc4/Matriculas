package app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
    private static Fabrica instancia = null;

    /**
     * Construtor privado para garantir que apenas uma instância desta classe seja
     * criada.
     */
    private Fabrica() {

    };

    /**
     * Retorna a instância única desta classe.
     * 
     * @return Instância única desta classe.
     */
    public static Fabrica getInstancia() {
        if (instancia == null)
            instancia = new Fabrica();
        return instancia;
    };

    /**
     * Lê um objeto de um arquivo.
     * 
     * @param nomeArquivo Nome do arquivo.
     * @return Objeto lido do arquivo.
     */
    public Object lerObjeto(String nomeArquivo) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("./data/" + nomeArquivo));
            Object obj = in.readObject();
            in.close();
            return obj;
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo " + nomeArquivo);
        } catch (ClassNotFoundException e) {
            System.out.println("Erro ao buscar a classe do objeto no arquivo " + nomeArquivo);
        }
        return null;
    };

    /**
     * Escreve um objeto em um arquivo.
     * 
     * @param nomeArquivo Nome do arquivo.
     * @param obj         Objeto a ser escrito no arquivo.
     */
    public void escreverObjeto(String nomeArquivo, Object obj) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./data/" + nomeArquivo));
            out.writeObject(obj);
            out.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo " + nomeArquivo);
        }
    };

}
