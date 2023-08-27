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

/**
 * Classe principal do programa.
 */
public class App {

    /** Matrícula atual */
    private static Integer matriculaAtual = 1;

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
        return System.console().readLine(mensagem);
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

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();
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
                        :  "Boa Noite";
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
                            " " + App.saudacao() + ", bem vindo ao sistema de matrículas da PUCMG.\n Login: " //
                    )).login(
                            App.lerStr(" Senha: ") //
                    );
            lock = usuarioAtual == null;
            if (lock)
                System.out.println(" ERRO: Usuário ou senha inválidos.");
        }
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
