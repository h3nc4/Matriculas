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

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.management.openmbean.InvalidKeyException;

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
     * @return curso*/
    public static Curso getCurso(String nome) { return cursos.get(nome); }

    /** Busca uma disciplina
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
        return hour >= 4 && hour < 12 ? "Bom dia"
                : hour < 18 ? "Boa tarde"
                        : "Boa Noite";
    }

    /**
     * Realiza o login do usuário.
     */
    public static void login() {
        Boolean lock = true;
        while (lock) {
            App.clearConsole();
            App.usuarioAtual = usuarios
                    .get(App.lerInt(
                            " " + App.saudacao() + ", bem vindo ao sistema de matriculas da PUCMG.\n Matricula: " //
                    )).login(
                            App.lerStr(" Senha: ") //
                    );
            lock = usuarioAtual == null;
            if (lock)
                System.out.println(" ERRO: Usuário ou senha invalidos.");
        }
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
     * Cria e insere um novo curso no mapa de cursos.
     */
    public static void novoCurso() {
        String nome = App.lerStr(" Nome: ").toLowerCase();
        HashMap<String, Disciplina> disciplinasC = null;
        Disciplina[] disciplinasIni = null;
        try {
            disciplinasC = (HashMap<String, Disciplina>) Stream
                    .of(App.lerStr(" Digite as disciplinas do curso separadas por vírgula: ").split(","))
                    .map(d -> {
                        Disciplina add = App.disciplinas.get(d.trim().toLowerCase());
                        if (add == null)
                            throw new InvalidKeyException();
                        return add;
                    })
                    .collect(
                            Collectors.toMap(d -> d.getNome(), d -> d) //
                    );
            disciplinasIni = (Disciplina[]) Stream
                    .of(App.lerStr(" Digite as 4 disciplinas iniciais do curso separadas por vírgula: ").split(","))
                    .map(d -> {
                        Disciplina add = App.disciplinas.get(d.trim().toLowerCase());
                        if (add == null)
                            throw new InvalidKeyException();
                        return add;
                    })
                    .collect(Collectors.toList())
                    .toArray();
        } catch (InvalidKeyException e) {
            System.out.println(" ERRO: Disciplina nao existente. Adicione as disciplinas desejadas manualmente.");
            return;
        }

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
                        App.lerStr(" E opcional? (s/n): ").toLowerCase().equals("s") //
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

        while (true) { // Neste loop, o usuário pode ser deslogado mas não pode sair do programa
            App.login();
            while (App.usuarioAtual.menu()) // Neste loop, o usuário é apresentado ao seu menu e caso ele retorne
                                            // TRUE, o menu é exibido novamente, caso contrário, o usuário é
                                            // deslogado e enviado para o loop externo
                ;
        }
    }

}
