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
     * @return {@code TRUE} se o login foi realizado com sucesso, {@code FALSE} caso
     *         contrario
     */
    public Usuario login(String passwd);

    /**
     * Menu de opções do usuário
     * 
     * @return {@code TRUE} se o usuário deseja continuar no sistema e {@code FALSE}
     */
    public Boolean menu();

}
