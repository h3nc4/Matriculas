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

import java.util.List;

import curso.Disciplina;

/**
 * Classe que representa um professor
 * 
 * @see Usuario
 */
public class Professor extends Usuario {

    /** disciplinas do professor */
    private List<Disciplina> disciplinas;

    /**
     * Construtor da classe Professor
     * 
     * @param matricula matricula do professor
     * @param passwd    senha do professor
     */
    public Professor(Integer matricula, String passwd) {
        super(matricula, passwd);
    };

    /**
     * Adiciona uma disciplina ao professor
     * 
     * @param disciplina disciplina a ser adicionada
     */
    public void addDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    };

    @Override
    public Boolean menu(){
        return true;
    };

    @Override
    public String toString() {
        return "Professor " + this.matricula + " lecionando " + this.disciplinas.size() + " disciplinas.";
    };

}
