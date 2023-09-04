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
import app.App;
import app.Util;

/**
 * Classe que representa um professor
 * 
 * @see Usuario
 */
public class Professor extends Usuario {

    /** disciplinas do professor */
    private List<Disciplina> disciplinas;

    /** serial version UID */
    private static final long serialVersionUID = 1L;

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
    public void inserirDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    };

    /**
     * Retorna as disciplinas do professor em string
     * 
     * @return disciplinas do professor
     */
    public String buscarDisciplinas() {
        StringBuilder disciplinas = new StringBuilder();
        this.disciplinas.forEach(disciplina -> disciplinas.append(disciplina.toString() + "\n"));
        return new String(disciplinas);
    };

    /**
     * Lista as disciplinas disponíveis para o professor
     */
    public void listarDisciplinas() {
        System.out.println(App.getApp().listarDisciplinas());
    };

    /**
     * Lista os alunos de uma disciplina
     */
    public void listarAlunos() {
        Disciplina disciplina = App.getApp().getDisciplina(Util.lerStr(" Digite o nome da disciplina: "));
        if (disciplina != null)
            System.out.println(disciplina.listarAlunos());
    };

    /**
     * Inscreve o professor em uma disciplina
     */
    public void inscreverMateria() {
        Disciplina disciplina = App.getApp().getDisciplina(Util.lerStr(" Digite o nome da disciplina: "));
        if (disciplina != null)
            this.disciplinas.add(disciplina);
        else
            System.out.println(" Disciplina não encontrada");
    };

    @Override
    public Boolean menu() {
        Integer escolha = Util.lerInt("\n\n 1- Buscar suas disciplinas\n 2- Ver todas as disciplinas\n 3- Ver alunos em uma disciplina\n 4- Se cadastrar em disciplina 0- Voltar\n");
        switch (escolha) {
            case 1 -> System.out.println(this.buscarDisciplinas());
            case 2 -> this.listarAlunos();
            case 3 -> this.inscreverMateria();
            case 0 -> {
                return false;
            }
        }
        return true;
    };

    @Override
    public String toString() {
        return " Professor " + this.matricula + " lecionando " + this.disciplinas.size() + " disciplinas.";
    };

}
