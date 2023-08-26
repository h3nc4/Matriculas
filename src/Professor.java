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

import java.util.List;

/**
 * Classe que representa um professor
 * 
 * @see Usuario
 */
public class Professor implements Usuario {

    /** matricula do professor */
    private Integer matricula;

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
    public Professor(Integer matricula, String passwd) {
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
    public Usuario login(String passwd) {
        return this.passwd.equals(passwd) // verificar se a matricula e a senha são iguais as do professor
                ? this
                : null;
    }

    @Override
    public Boolean menu(){
        return true;
    }

}
