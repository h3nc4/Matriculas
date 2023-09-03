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

package curso;

import error.DisciplinaCompletaException;

import usuarios.Aluno;

/**
 * Classe que representa uma disciplina
 */
public class Disciplina implements java.io.Serializable {

    /** disciplina é opcional */
    private Boolean opcional;

    /** disciplina está ativa */
    private Boolean estaAtiva;

    /** creditos da disciplina */
    private Integer creditos;

    /** quantidade de alunos na disciplina */
    private Integer qtdAlunos;

    /** nome da disciplina */
    private final String NOME;

    /** numero maximo de alunos na disciplina */
    public static final Integer MAX_ALUNOS = 60;

    /** numero minimo de alunos na disciplina */
    public static final Integer MIN_ALUNOS = 3;

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da classe Disciplina
     * 
     * @param nome     nome da disciplina
     * @param creditos creditos da disciplina
     * @param opcional disciplina é opcional
     */
    public Disciplina(String nome, Integer creditos, Boolean opcional) {
        this.NOME = nome;
        this.creditos = creditos;
        this.opcional = opcional;
        this.qtdAlunos = 0;
        this.estaAtiva = Boolean.FALSE;
    };

    /**
     * Adiciona um aluno na disciplina.
     * 
     * @see Aluno#matricular(Disciplina[])
     */
    public void addAluno() throws DisciplinaCompletaException {
        if (++this.qtdAlunos > MAX_ALUNOS) {
            this.qtdAlunos--;
            throw new DisciplinaCompletaException("Numero maximo de alunos atingido na disciplina " + this.NOME + ".");
        }
    };

    /**
     * Remove um aluno na disciplina.
     * 
     * @see Aluno#matricular(Disciplina[])
     */
    public void removeAluno() {
        this.qtdAlunos--;
    };

    @Override
    public String toString() {
        return "Disciplina " + this.NOME + ", " + (this.opcional ? "opcional" : "obrigatoria") + " e "
                + (this.estaAtiva ? "ativa" : "inativa") + " de " + this.creditos + " creditos.\n Ha " + this.qtdAlunos
                + " alunos matriculados atualmente.";
    }; //@formatter:off

    /** Retorna o nome da disciplina
     * @return nome da disciplina */
    public String getNome() { return this.NOME;};

    /** Retorna se a disciplina é obrigatória
     * @return se a disciplina é obrigatória */
    public boolean eObrigatria() { return !this.opcional;};

    /** Altera o valor de opcional
     * @param opcional novo valor */
    public void setOpcional(boolean opcional) { this.opcional = opcional; };

    /** Altera o total de creditos
     * @param creditos novo valor */
    public void setCreditos(int creditos) { this.creditos = creditos; };

}
