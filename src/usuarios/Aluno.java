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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import error.DisciplinaCompletaException;

import curso.Curso;
import curso.Disciplina;
import app.App;
import app.Util;

/**
 * Classe que representa um aluno
 * 
 * @see Usuario
 */
public class Aluno extends Usuario {

    /** nome do aluno */
    private final String NOME;

    /** curso do aluno */
    private Curso curso;

    /** disciplinas do aluno */
    private Disciplina[] disciplinas;

    /** serial version UID */
    private static final long serialVersionUID = 1L;

    /**
     * Construtor da classe Aluno, alunos recém matriculaos não possuem disciplinas
     * escolhidas
     * 
     * @param nome      nome do aluno
     * @param matricula matricula do aluno
     * @param passwd    senha do aluno
     * @param curso     curso do aluno
     */
    public Aluno(String nome, Integer matricula, String passwd, Curso curso) {
        super(matricula, passwd);
        this.NOME = nome;
        this.curso = curso;
        this.disciplinas = this.curso.getDisciplinasIniciais();

        Stream.of(this.disciplinas).forEach(d -> {
            try {
                d.adcAluno(this);
            } catch (DisciplinaCompletaException e) {
                System.out.println("Nao foi possivel matricular-se em " + d.getNome() + ": " + e.getMessage());
            }
        });
    };

    /**
     * Matricula o aluno em disciplinas e checa se as disciplinas são válidas
     * 
     * @param disciplinasAdc disciplinas a serem matriculadas
     */
    public void efetuarMatricula(List<Disciplina> disciplinasAdc) {

        // concatenar as disciplinas atuais com as novas e unir em uma coleção
        List<Disciplina> disciplinas = Stream.concat(Stream.of(this.disciplinas), disciplinasAdc.stream()).toList();

        if (disciplinas.stream().filter(d -> d.eObrigatria()).count() == 0 // verificar se o aluno está matriculado em
                                                                           // pelo menos uma disciplina
                || disciplinas.stream().filter(d -> d.eObrigatria()).count() > 4 // verificar se o aluno está
                                                                                 // matriculado em no máximo 4
                                                                                 // disciplinas obrigatórias
                || disciplinas.stream().filter(d -> !d.eObrigatria()).count() > 2) // verificar se o aluno está
                                                                                   // matriculado em no máximo 2
                                                                                   // disciplinas opcionais
        {
            System.out.println( // caso alguma das condições seja verdadeira, o aluno não pode se matricular
                    "Erro ao realizar matricula, verifique se as disciplinas sao validas e se o total de cada disciplina esta correto");
            return;
        }

        disciplinasAdc.forEach(d -> {
            try {
                d.adcAluno(this);
            } catch (DisciplinaCompletaException e) {
                System.out.println("Nao foi possivel matricular-se em " + d.getNome() + ": " + e.getMessage());
            }
        }); // adicionar o aluno em cada disciplina nova
        this.disciplinas = disciplinas.toArray(Disciplina[]::new);
    };

    /**
     * Menu para o aluno se matricular
     * 
     * @return {@code TRUE} para manter o aluno no menu
     */
    public Boolean matricular() {
        Integer q = 0;
        System.out.println(
                "Para matricular-se em uma disciplina, digite o nome desta, lembrando que sao permitidas no maximo 4 disciplinas obrigatorias e 2 disciplinas opcionais." //
        );
        List<Disciplina> disciplinasRem = new LinkedList<>();
        while (q <= 6) {
            String nomeDisciplina = Util.lerStr(
                    "Digite o nome de uma disciplina (digite 0 para sair): " //
            );
            if (nomeDisciplina.equals("0"))
                break;
            Disciplina disciplina = App.getApp().getDisciplina(nomeDisciplina);
            if (disciplina == null && !this.curso.estaNoCurso(nomeDisciplina)) {
                System.out.println("Disciplina invalida");
                continue;
            }
            System.out.println("Disciplina adicionada");
            disciplinasRem.add(disciplina);
            q++;
        }
        this.efetuarMatricula(
                disciplinasRem //
        );
        return true;
    };

    /**
     * Desmatricula o aluno de disciplinas e o avisa quais foram desmatriculadas
     * 
     * @param disciplinas disciplinas a serem desmatriculadas
     */
    public void efetuarDesmatricula(List<Disciplina> disciplinas) {
        disciplinas.forEach(d -> System.out.println(d.removeAluno(this) ? " Desmatriculado de " + d.getNome() : ""));
    };

    /**
     * Menu para o aluno se desmatricular
     * 
     * @return {@code TRUE} para manter o aluno no menu
     */
    public Boolean desmatricular() {
        Integer q = 0;
        System.out.println(
                "Para desmatricular-se de uma disciplina, digite o nome desta, lembrando que sao permitidas no maximo 4 disciplinas obrigatorias e 2 disciplinas opcionais." //
        );
        List<Disciplina> disciplinasRem = new LinkedList<>();
        while (q <= 6) {
            String nomeDisciplina = Util.lerStr(
                    "Digite o nome de uma disciplina (digite 0 para sair): " //
            );
            if (nomeDisciplina.equals("0"))
                break;
            Disciplina disciplina = App.getApp().getDisciplina(nomeDisciplina);
            if (disciplina == null || Stream.of(this.disciplinas).filter(d -> d.equals(disciplina)).count() == 0) {
                System.out.println("Voce nao esta matriculado em " + nomeDisciplina);
                continue;
            } else {
                System.out.println("Disciplina removida");
                this.disciplinas = Stream.of(this.disciplinas).filter(d -> !d.equals(disciplina))
                        .toArray(Disciplina[]::new);
                disciplinasRem.add(disciplina);
                q++;
            }

        }
        this.efetuarDesmatricula(
                disciplinasRem //
        );
        return true;
    };

    /**
     * Permitir que o aluno veja as disciplinas que ele está matriculado
     */
    public void verDisciplinas() {
        System.out.println("Disciplinas matriculadas:");
        Stream.of(this.disciplinas).forEach(d -> System.out.println("" + d.getNome()));
    };

    @Override
    public Boolean menu() {
        Integer escolha = Util.lerInt("\n\nBem vindo aluno " + this.matricula
                + "\n1- Matricular\n2- Desmatricular\n3- Ver suas disciplinas\n0- Sair\n");
        switch (escolha) {
            case 1 -> {
                if (App.getApp().matriculasAbertas())
                    System.out.println(
                            "Nao e possivel matricular-se agora, as matriculas estao fechadas, tente novamente mais tarde.");
                return this.matricular();
            }
            case 2 -> {
                if (App.getApp().matriculasAbertas())
                    System.out.println(
                            "Nao e possivel desmatricular-se agora, as matriculas estao fechadas, tente novamente mais tarde.");
                return this.desmatricular();
            }
            case 3 -> {
                this.verDisciplinas();
                return true;
            }
            default -> {
                return false;
            }
        }
    };

    @Override
    public String toString() {
        return "Aluno " + this.NOME + " do curso " + this.curso.getNome() + " matriculado em "
                + this.disciplinas.length + " disciplinas: "
                + Stream.of(this.disciplinas).map(d -> d.getNome()).collect(Collectors.joining(", "));
    };

    /**
     * Retorna o nome do aluno e sua matricula
     * 
     * @return nome do aluno e sua matricula
     */
    public String getInfo() {
        return "Aluno " + this.NOME + " matricula " + this.matricula;
    }

    /**
     * Remove uma disciplina do aluno.
     * APENAS PARA USO INTERNO
     * 
     * @param d disciplina a ser removida
     * @see App#fecharMatriculas()
     */
    public void removeDisciplina(Disciplina d) {
        this.disciplinas = Stream.of(this.disciplinas).filter(disc -> !disc.equals(d)).toArray(Disciplina[]::new);
    };

}
