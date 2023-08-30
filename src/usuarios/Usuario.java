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

package usuarios;

/**
 * Interface que define o comportamento de um usuário do sistema
 * 
 * @see Aluno
 * @see Professor
 * @see Secretaria
 */
abstract public class Usuario implements java.io.Serializable {

    /** matricula */
    Integer matricula;

    /** senha */
    String passwd;

    /**
     * Construtor da classe Usuario
     * 
     * @param matricula matricula do usuário
     * @param passwd    senha do usuário
     */
    Usuario(Integer matricula, String passwd) {
        this.matricula = matricula;
        this.passwd = passwd;
    }

    /**
     * Realiza o login do usuário
     * 
     * @param passwd    senha do usuário
     * @return {@code TRUE} se o login foi realizado com sucesso, {@code FALSE} caso
     *         contrário
     */
    public Usuario login(String passwd) {
        return this.passwd.equals(passwd) // verificar se a matricula e a senha são iguais as do usuário
                ? this
                : null;
    }

    /**
     * Menu de opções do usuário
     * 
     * @return {@code TRUE} se o usuário deseja continuar no sistema e {@code FALSE}
     */
    abstract public Boolean menu();

}
