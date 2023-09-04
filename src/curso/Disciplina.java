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

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import error.DisciplinaCompletaException;

import usuarios.Aluno;
import usuarios.Professor;

/**
 * Classe que representa uma disciplina
 */
public class Disciplina implements java.io.Serializable {

    /** disciplina é opcional */
    private Boolean opcional;

    /** disciplina está ativa */
    private Boolean estaAtiva;

    /** professor da disciplina */
    private Optional<Professor> professor;

    /** creditos da disciplina */
    private Integer creditos;

    /** alunos na disciplina */
    private List<Aluno> alunos;

    /** nome da disciplina */
    private final String NOME;

    /** numero maximo de alunos na disciplina */
    private static final Integer MAX_ALUNOS = 60;

    /** numero minimo de alunos na disciplina */
    private static final Integer MIN_ALUNOS = 3;

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
        this.professor = Optional.empty();
        this.estaAtiva = Boolean.FALSE;
        this.alunos = new LinkedList<Aluno>();
    };

    /**
     * Adiciona um aluno na disciplina.
     * 
     * @throws DisciplinaCompletaException se a disciplina já estiver cheia.
     * @param novoAluno aluno a ser adicionado
     * @see Aluno#matricular()
     */
    public void addAluno(Aluno novoAluno) throws DisciplinaCompletaException {
        if (this.alunos.size() >= MAX_ALUNOS)
            throw new DisciplinaCompletaException("Numero maximo de alunos atingido na disciplina " + this.NOME + ".");
        this.alunos.add(novoAluno);
        if (!this.estaAtiva && this.alunos.size() >= MIN_ALUNOS)
            this.estaAtiva = Boolean.TRUE;
    };

    /**
     * Adiciona um professor na disciplina.
     * 
     * @param professor professor a ser adicionado
     */
    public void addProfessor(Professor professor) {
        this.professor = Optional.of(professor);
    };

    /**
     * Remove um aluno na disciplina.
     * 
     * @param aluno aluno a ser removido
     * @see Aluno#matricular()
     * @return {@code TRUE} se o aluno foi removido, {@code FALSE} caso contrário.
     */
    public Boolean removeAluno(Aluno aluno) {
        if (this.alunos.remove(aluno)) {
            if (this.alunos.size() < MIN_ALUNOS)
                this.estaAtiva = Boolean.FALSE;
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    };

    /**
     * Lista todos os alunos da disciplina.
     * 
     * @return alunos da disciplina em string
     */
    public String listarAlunos() {
        StringBuilder alunos = new StringBuilder();
        this.alunos.forEach(aluno -> alunos.append(aluno.getInfo() + "\n"));
        return new String(alunos);
    };

    @Override
    public String toString() {
        return "Disciplina " + this.NOME + ", " + (this.opcional ? "opcional" : "obrigatoria") + " e "
                + (this.estaAtiva ? "ativa" : "inativa") + " de " + this.creditos + " creditos.\n Ha "
                + this.alunos.size()
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

    /** Retorna se há um professor na disciplina
     * @return se há um professor na disciplina */
    public boolean temProfessor() { return this.professor.isPresent(); };

}
