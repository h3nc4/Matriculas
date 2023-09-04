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

/**
 * Classe utilitária para o sistema de matrículas.
 */
public class Util {

    /**
     * Construtor privado para evitar instanciação.
     */
    private Util() {};
    
    /**
     * Método que lê uma string do console através do {@link System#console()}.
     * 
     * @param mensagem a ser exibida ao usuário.
     * @return string lida do console.
     */
    public static String lerStr(String mensagem) {
        String out = System.console().readLine(mensagem).trim();
        return out.isEmpty() ? lerStr(" ERRO: Valor invalido. Digite algo: ") : out;
    };

    /**
     * Método que lê um inteiro do console.
     * 
     * @param mensagem a ser exibida ao usuário.
     * @return inteiro lido do console.
     */
    public static Integer lerInt(String mensagem) {
        try {
            return Integer.parseInt(Util.lerStr(mensagem));
        } catch (NumberFormatException e) {
            return lerInt(" ERRO: Valor invalido. Digite um numero inteiro: ");
        }
    };

    /**
     * Limpa o console.
     */
    public static void limparTerminal() {
        try {
            new ProcessBuilder(System.getProperty("os.name").toLowerCase().contains("win") ? "cls" : "clear")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
    };

    /**
     * Pausa a execução do programa até que o usuário pressione ENTER.
     */
    public static void pause() {
        System.console().readLine(" Pressione ENTER para continuar...");
    };

    /**
     * Retorna a hora do dia em que o programa está sendo executado.
     * 
     * @return hora do dia.
     */
    public static String saudacao() {
        Integer hour = LocalTime.now().getHour();
        return (hour >= 4 && hour < 12 ? "Bom dia"
                : hour < 18 ? "Boa tarde"
                        : "Boa Noite")
                + ", bem vindo ao sistema de matriculas da PUCMG. (-1 para sair)\n Matricula: ";
    };
}
