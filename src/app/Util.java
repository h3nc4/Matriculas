package app;

import java.io.IOException;
import java.time.LocalTime;

public class Util {
    
    /**
     * Método que lê uma string do console através do {@link System#console()}.
     * 
     * @param mensagem a ser exibida ao usuário.
     * @return string lida do console.
     */
    public static String lerStr(String mensagem) {
        return System.console().readLine(mensagem).trim();
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
