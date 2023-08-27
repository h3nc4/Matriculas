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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Classe que representa um aluno
 * 
 * @see Usuario
 */
public class Aluno implements Usuario {

    /** matricula do aluno */
    private Integer matricula;

    /** senha do aluno */
    private String passwd;

    /** curso do aluno */
    private Curso curso;

    /** disciplinas do aluno */
    private Disciplina[] disciplinas;

    /**
     * Construtor da classe Aluno, alunos recém matriculaos não possuem disciplinas
     * escolhidas
     * 
     * @param matricula matricula do aluno
     * @param passwd    senha do aluno
     * @param curso     curso do aluno
     */
    public Aluno(Integer matricula, String passwd, Curso curso) {
        this.matricula = matricula;
        this.passwd = passwd;
        this.curso = curso;
        this.disciplinas = this.curso.getDisciplinasIniciais();
    }

    @Override
    public Usuario login(String passwd) {
        return this.passwd.equals(passwd) // verificar se a matricula e a senha são iguais as do aluno
                ? this
                : null;
    }

    /**
     * Matricula o aluno em disciplinas
     * 
     * @param disciplinasObg disciplinas obrigatórias, podem ser no máximo 4
     * @param disciplinasOpt disciplinas opcionais, podem ser no máximo 2
     * @return {@code TRUE} se o aluno foi matriculado em pelo menos uma disciplina
     *         e {@code FALSE} caso contrário
     */
    public Boolean matricular(Disciplina[] disciplinas) {
        if (Stream.of(disciplinas).filter(d -> d.eObrigatria()).count() == 0 // verificar se o aluno está matriculado em
                                                                             // pelo menos uma disciplina
                || Stream.of(disciplinas).filter(d -> d.eObrigatria()).count() > 4 // verificar se o aluno está
                                                                                   // matriculado em no máximo 4
                                                                                   // disciplinas obrigatórias
                || Stream.of(disciplinas).filter(d -> !d.eObrigatria()).count() > 2) { // verificar se o aluno está
                                                                                       // matriculado em no máximo 2
                                                                                       // disciplinas opcionais
            System.out.println(
                    " Erro ao realizar matricula, verifique se as disciplinas sao validas e se o total de cada disciplina esta correto" //
            );
            return false;
        }
        this.disciplinas = disciplinas;
        return true;
    }

    /**
     * Desmatricula o aluno de disciplinas e o avisa quais foram desmatriculadas
     * 
     * @param disciplinas disciplinas a serem desmatriculadas
     */
    public void desmatricular(String[] disciplinas) {
        disciplinas = Stream.of(disciplinas).filter(d -> {
            for (Disciplina disciplina : this.disciplinas)
                if (disciplina.getNome().equals(d)) {
                    System.out.println(" Desmatriculado de " + d);
                    return false;
                }
            return true;
        }).toArray(String[]::new);
    }

    @Override
    public Boolean menu() {
        Integer escolha = App
                .lerInt(" Bem vindo aluno " + this.matricula + "\n 1 - Matricular\n 2 - Desmatricular\n 0 - Sair\n");
        int q = 0;
        if (escolha == 1) {
            System.out.println(
                    " Para matricular-se em uma disciplina, digite o nome desta, lembrando que sao permitidas no maximo 4 disciplinas obrigatorias e 2 disciplinas opcionais." //
            );
            List<Disciplina> disciplinas = new LinkedList<>();
            while (q <= 6) {
                String nomeDisciplina = App.lerStr(
                        " Digite o nome de uma disciplina (digite 0 para sair): " //
                );
                if (nomeDisciplina.equals("0"))
                    break;
                Disciplina disciplina = App.getDisciplina(nomeDisciplina);
                if (disciplina != null && this.curso.estaNoCurso(nomeDisciplina)) {
                    System.out.println(" Disciplina adicionada");
                    disciplinas.add(disciplina);
                    q++;
                } else
                    System.out.println(" Disciplina invalida");
            }
            return this.matricular(
                    disciplinas.stream().filter(d -> d != null).toArray(Disciplina[]::new) //
            );
        } else if (escolha == 2) {
            System.out.println(
                    " Para desmatricular-se de uma disciplina, digite o nome desta, lembrando que sao permitidas no maximo 4 disciplinas obrigatorias e 2 disciplinas opcionais." //
            );
            List<String> disciplinas = new LinkedList<>();
            while (q <= 6) {
                String nomeDisciplina = App.lerStr(
                        " Digite o nome de uma disciplina (digite 0 para sair): " //
                );
                if (nomeDisciplina.equals("0"))
                    break;
                System.out.println(" Disciplina adicionada");
                disciplinas.add(nomeDisciplina);
                q++;
            }
            this.desmatricular(
                    disciplinas.stream().filter(d -> d != null).toArray(String[]::new) //
            );
            return true;
        } else
            return false;
    }

    @Override
    public String toString() {
        return "Aluno " + this.matricula + " do curso " + this.curso.getNome()+ " matriculado em " + this.disciplinas.length + " disciplinas: " + Stream.of(this.disciplinas).map(d -> d.getNome()).reduce("", (a, b) -> a + ", " + b);
    }

}
