/*
 *  Copyright 2023 Henrique Almeida, Gabriel Dolabela, João Pauletti
 * 
 * This file is part of Sistema de matriculas PUC.
 * 
 * Sistema de matriculas PUC is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Sistema de matriculas PUC is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU
 * General Public License along with Sistema de matriculas PUC. If not, see
 * <https://www.gnu.org/licenses/>.
*/

package app;

import java.io.File;
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

    /** Instância única desta classe. */
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
        } finally {
            Util.pause();
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
            File pasta = new File("./data");
            if (!pasta.exists()) 
                pasta.mkdirs();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./data/" + nomeArquivo));
            out.writeObject(obj);
            out.close();
        } catch (IOException e) {
            System.out.println("Erro ao escrever o arquivo " + nomeArquivo + ": " + e.getMessage() + ".\n");
            e.printStackTrace();
            Util.pause();
        }
    };

}
