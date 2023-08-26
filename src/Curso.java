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

import java.util.HashMap;

/**
 * Classe que representa um curso
 */
public class Curso {

    /** nome do curso */
    private final String nome;

    /** creditos do curso */
    private Integer creditos;

    /** disciplinas do curso */
    private HashMap<String, Disciplina> disciplinas;

    /** disciplinas iniciais do curso */
    private Disciplina[] disciplinasIni;

    /**
     * Construtor da classe Curso
     * 
     * @param nome           nome do curso
     * @param disciplinas    disciplinas do curso
     * @param creditos       creditos do curso
     * @param disciplinasIni disciplinas iniciais do curso
     */
    public Curso(String nome, HashMap<String, Disciplina> disciplinas, int creditos, Disciplina[] disciplinasIni) {
        this.nome = nome;
        this.disciplinas = disciplinas;
        this.creditos = creditos;
        this.disciplinasIni = disciplinasIni;
    }

    public Boolean estaNoCurso(String nome) {
        return this.disciplinas.containsKey(nome);
    }

    /** Retorna as disciplinas iniciais do curso
     *  @return disciplinas iniciais do curso */
    public Disciplina[] getDisciplinasIniciais() {
        return this.disciplinasIni;
    }

    /** Retorna o nome do curso
     *  @return nome do curso */
    public String getNome() {
        return this.nome;
    }

}
