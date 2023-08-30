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

package app;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import error.ChaveInvalidaException;
import error.OperacaoNaoSuportadaException;
import usuarios.Aluno;
import usuarios.Professor;
import usuarios.Secretaria;
import usuarios.Usuario;
import curso.Curso;
import curso.Disciplina;

/**
 * Classe principal do programa.
 */
public class App {

    /** Matrícula atual */
    private static Integer proxMatricula = 1;

    /** Usuário atual */
    private static Usuario usuarioAtual = null;

    /** Mapa de usuários */
    private static HashMap<Integer, Usuario> usuarios = new HashMap<>();

    /** Mapa de cursos */
    private static HashMap<String, Curso> cursos = new HashMap<>();

    /** Mapa de disciplinas */ //@formatter:off
    private static HashMap<String, Disciplina> disciplinas = new HashMap<>();

    /** Retorna um curso
     * @param nome nome do curso
     * @return curso*/
    public static Curso getCurso(String nome) { return cursos.get(nome); }

    /** Busca uma disciplina
     * @param nome nome da disciplina
     * @return disciplina*/ 
    public static Disciplina getDisciplina(String nome) { return disciplinas.get(nome); }

    /** classe não instanciável */
    private App() { throw new InstantiationError("Classe nao instanciavel"); } // @formatter:on

    /**
     * Método que lê uma string do console através do {@link System#console()}.
     * 
     * @param mensagem a ser exibida ao usuário.
     * @return string lida do console.
     */
    public static String lerStr(String mensagem) {
        return System.console().readLine(mensagem).trim();
    }

    /**
     * Método que lê um inteiro do console.
     * 
     * @param mensagem a ser exibida ao usuário.
     * @return inteiro lido do console.
     */
    public static int lerInt(String mensagem) {
        try {
            return Integer.parseInt(App.lerStr(mensagem));
        } catch (NumberFormatException e) {
            return lerInt(" ERRO: Valor invalido. Digite um numero inteiro: ");
        }
    }

    /**
     * Limpa o console.
     */
    public static void clearConsole() {
        try {
            new ProcessBuilder(System.getProperty("os.name").toLowerCase().contains("win") ? "cls" : "clear")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
    }

    /**
     * Retorna a hora do dia em que o programa está sendo executado.
     * 
     * @return hora do dia.
     */
    public static String saudacao() {
        int hour = LocalTime.now().getHour();
        return (hour >= 4 && hour < 12 ? "Bom dia"
                : hour < 18 ? "Boa tarde"
                        : "Boa Noite")
                + ", bem vindo ao sistema de matriculas da PUCMG. (-1 para sair)\n Matricula: ";
    }

    /**
     * Realiza o login do usuário.
     */
    public static Boolean login() {
        App.clearConsole();
        try {
            int user = App.lerInt(" " + App.saudacao());
            if (user == -1)
                return Boolean.FALSE;
            App.usuarioAtual = usuarios.get(user).login(App.lerStr(" Senha: "));

        } catch (NullPointerException e) {
            System.out.println(" ERRO: Usuario nao existente.");
        }
        if (usuarioAtual == null)
            System.out.println(" ERRO: Senha incorreta.");
        return Boolean.TRUE;
    }

    /**
     * Cria e insere um novo aluno no mapa de usuários.
     */
    public static void novoAluno() {
        String senha = App.lerStr(" Matricula: " + App.proxMatricula + "\n Senha: ");
        Curso curso = null;
        while (curso == null) {
            curso = App.cursos.get(App.lerStr(" Curso: ").toLowerCase());
            if (curso == null)
                System.out.println(" ERRO: Curso invalido.");
        }
        App.usuarios.put(
                App.proxMatricula,
                new Aluno(
                        App.proxMatricula++,
                        senha,
                        curso //
                ) //
        );
    }

    /**
     * Cria e insere um novo professor no mapa de usuários.
     */
    public static void novoProfessor() {
        App.usuarios.put(
                App.proxMatricula,
                new Professor(
                        App.proxMatricula++,
                        App.lerStr(" Matricula: " + App.proxMatricula + "\n Senha: ") //
                ) //
        );
    }

    /**
     * Busca disciplinas digitadas pelo usuário no mapa de disciplinas.
     * 
     * @param mensagem a ser exibida ao usuário.
     * @return stream de disciplinas de acordo com o que o usuário digitou.
     */
    private static Stream<Disciplina> buscaDisciplinas(String mensagem) {
        return Stream.of(App.lerStr(mensagem).split(",")) // lê as disciplinas do usuário
                .map(d -> {
                    Disciplina add = App.disciplinas.get(d.toLowerCase()); // busca a disciplina no mapa de disciplinas
                    if (add == null)
                        throw new ChaveInvalidaException(d); // caso a disciplina não exista, lança uma exceção passando
                                                             // o nome da disciplina para avisar o usuário
                    return add;
                });
    }

    /**
     * Cria e insere um novo curso no mapa de cursos.
     */
    public static void novoCurso() throws ChaveInvalidaException, OperacaoNaoSuportadaException {
        String nome = App.lerStr(" Nome: ").toLowerCase(); // lê o nome do curso do usuário

        HashMap<String, Disciplina> disciplinasC = (HashMap<String, Disciplina>) App
                .buscaDisciplinas(" Digite as disciplinas do curso separadas por virgula: ")
                .collect(
                        Collectors.toMap(d -> d.getNome(), d -> d) //
                ); // transforma o stream em um mapa e o retorna para a variável disciplinasC

        Disciplina[] disciplinasIni = App
                .buscaDisciplinas(" Digite as 4 disciplinas iniciais do curso separadas por virgula: ")
                .collect(Collectors.toList()) // transforma o stream em uma lista
                .toArray(Disciplina[]::new); // transforma a lista em um array

        if (disciplinasIni.length != 4) // caso não tenham sido adicionadas 4 disciplinas, lança uma exceção
            throw new OperacaoNaoSuportadaException();

        App.cursos.put(
                nome,
                new Curso(
                        nome,
                        disciplinasC,
                        App.lerInt(" Creditos: "),
                        disciplinasIni //
                ) //
        );
    }

    /**
     * Cria e insere uma nova disciplina no mapa de disciplinas.
     */
    public static void novaDisciplina() {
        String nome = App.lerStr(" Nome: ").toLowerCase();
        App.disciplinas.put(
                nome,
                new Disciplina(
                        nome,
                        App.lerInt(" Creditos: "),
                        App.lerStr(" E opcional? (s/n): ").equalsIgnoreCase("s") //
                ) //
        );
    }

    /**
     * Método principal do programa.
     * 
     * @param args argumentos da linha de comando.
     * @throws Throwable caso ocorra algum erro.
     */
    public static void main(String[] args) throws Throwable {
        App.usuarios.put(0, new Secretaria(0, "admin123")); // secretaria padrão

        while (App.login()) { // Neste loop, o usuário pode ser deslogado mas não pode sair do programa
            while (App.usuarioAtual.menu()) // Neste loop, o usuário é apresentado ao seu menu e caso ele retorne
                                            // TRUE, o menu é exibido novamente, caso contrário, o usuário é
                                            // deslogado e enviado para o loop externo
                ;
        }
    }

}
