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
import java.util.Optional;
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
    private static Optional<Usuario> usuarioAtual = null;

    /** Mapa de usuários */
    private static HashMap<Integer, Usuario> usuarios = new HashMap<>();

    /** Mapa de cursos */
    private static HashMap<String, Curso> cursos = new HashMap<>();

    /** Mapa de disciplinas */ //@formatter:off
    private static HashMap<String, Disciplina> disciplinas = new HashMap<>();

    /** Busca uma disciplina
     * @param nome nome da disciplina
     * @return disciplina*/ 
    public static Disciplina getDisciplina(String nome) { return disciplinas.get(nome); };

    /** classe não instanciável */
    private App() { throw new InstantiationError("Classe nao instanciavel"); }; // @formatter:on

    /**
     * Realiza o login do usuário.
     * 
     * @return TRUE caso o login tenha sido mal sucedido, FALSE caso o usuário
     *         deseje sair do programa.
     */
    public static Boolean login() {
        Util.limparTerminal();
        try {
            Integer user = Util.lerInt(" " + Util.saudacao());
            if (user == -1)
                return Boolean.FALSE;
            App.usuarioAtual = Optional.ofNullable(usuarios.get(user).login(Util.lerStr(" Senha: ")));

        } catch (NullPointerException e) {
            System.out.println(" ERRO: Usuario nao existente.");
        }
        if (usuarioAtual.isEmpty())
            System.out.println(" ERRO: Senha incorreta.");
        return Boolean.TRUE;
    };

    /**
     * Cria e insere um novo aluno no mapa de usuários.
     */
    public static void novoAluno() {
        String senha = Util.lerStr(" Matricula: " + App.proxMatricula + "\n Senha: ");
        Curso curso = null;
        while (curso == null) {
            curso = App.cursos.get(Util.lerStr(" Curso: ").toLowerCase());
            if (curso == null) {
                System.out.println(" ERRO: Curso invalido, deseja adicionar o curso antes? (s/n)");
                if (Util.lerStr(" ").equalsIgnoreCase("s"))
                    App.novoCurso();
            }
        }
        App.usuarios.put(
                App.proxMatricula,
                new Aluno(
                        App.proxMatricula++,
                        senha,
                        curso //
                ) //
        );
    };

    /**
     * Cria e insere um novo professor no mapa de usuários.
     */
    public static void novoProfessor() {
        App.usuarios.put(
                App.proxMatricula,
                new Professor(
                        App.proxMatricula++,
                        Util.lerStr(" Matricula: " + App.proxMatricula + "\n Senha: ") //
                ) //
        );
    };

    /**
     * Cria e insere uma nova secretaria no mapa de usuários.
     */
    public static void novaSecretaria() {
        App.usuarios.put(
                App.proxMatricula,
                new Secretaria(
                        App.proxMatricula++,
                        Util.lerStr(" Matricula: " + App.proxMatricula + "\n Senha: ") //
                ) //
        );
    };

    /**
     * Busca disciplinas digitadas pelo usuário no mapa de disciplinas.
     * 
     * @param mensagem a ser exibida ao usuário.
     * @return stream de disciplinas de acordo com o que o usuário digitou.
     */
    private static Stream<Disciplina> buscaDisciplinas(String mensagem) {
        return Stream.of(Util.lerStr(mensagem).split(",")) // lê as disciplinas do usuário
                .map(d -> {
                    Disciplina add = App.disciplinas.get(d.toLowerCase()); // busca a disciplina no mapa de disciplinas
                    if (add == null)
                        throw new ChaveInvalidaException(d); // caso a disciplina não exista, lança uma exceção passando
                                                             // o nome da disciplina para avisar o usuário
                    return add;
                });
    };

    /**
     * Cria e insere um novo curso no mapa de cursos.
     */
    public static void novoCurso() throws ChaveInvalidaException, OperacaoNaoSuportadaException {
        String nome = Util.lerStr(" Nome: ").toLowerCase(); // lê o nome do curso do usuário

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
                        Util.lerInt(" Creditos: "),
                        disciplinasIni //
                ) //
        );
    };

    /**
     * Cria e insere uma nova disciplina no mapa de disciplinas.
     */
    public static void novaDisciplina() {
        String nome = Util.lerStr(" Nome: ").toLowerCase();
        App.disciplinas.put(
                nome,
                new Disciplina(
                        nome,
                        Util.lerInt(" Creditos: "),
                        Util.lerStr(" E opcional? (s/n): ").equalsIgnoreCase("s") //
                ) //
        );
    };

    /**
     * Imprime os alunos do mapa de usuários.
     */
    public static void printAlunos() {
        App.usuarios.values().stream() // transforma o mapa de usuários em um stream
                .filter(u -> u instanceof Aluno) // filtra os alunos
                .forEach(System.out::println); // imprime os alunos
    };

    /**
     * Imprime os professores do mapa de usuários.
     */
    public static void printProfessores() {
        App.usuarios.values().stream() // transforma o mapa de usuários em um stream
                .filter(u -> u instanceof Professor) // filtra os professores
                .forEach(System.out::println); // imprime os professores
    };

    /**
     * Imprime as disciplinas do mapa de disciplinas.
     */
    public static void printDisciplinas() {
        App.disciplinas.values() // transforma o mapa de disciplinas em uma coleção
                .forEach(System.out::println); // imprime as disciplinas
    };

    /**
     * Imprime os cursos do mapa de cursos.
     */
    public static void printCursos() {
        App.cursos.values() // transforma o mapa de cursos em uma coleção
                .forEach(System.out::println); // imprime os cursos
    };

    /**
     * Altera os dados de uma disciplina no mapa de disciplinas.
     */
    public static void alterarDisciplina() {
        Disciplina disciplina = App.disciplinas.get(Util.lerStr(" Digite o nome da disciplina: ").toLowerCase());
        if (disciplina == null) {
            System.out.println(" ERRO: Disciplina nao existente.");
            return;
        }
        disciplina.setOpcional(Util.lerStr(" E opcional? (s/n): ").equalsIgnoreCase("s"));
        disciplina.setCreditos(Util.lerInt(" Creditos: "));
    };

    /**
     * Método principal do programa.
     * 
     * @param args argumentos da linha de comando.
     * @throws Throwable caso ocorra algum erro.
     */
    public static void main(String[] args) throws Throwable {
        App.usuarios.put(0, new Secretaria(0, "admin123")); // secretaria padrão

        while (App.login()) { // Neste loop, o usuário pode ser deslogado mas não pode sair do programa
            while (App.usuarioAtual.get().menu()) // Neste loop, o usuário é apresentado ao seu menu e caso ele retorne
                                                  // TRUE, o menu é exibido novamente, caso contrário, o usuário é
                                                  // deslogado e enviado para o loop externo
                ;
        }
    };

    /**
     * Escreve usuários, cursos e disciplinas em arquivos.
     */
    public static void escrever() {
        Fabrica fabrica = Fabrica.getInstancia();

        fabrica.escreverObjeto("usuarios.ser", usuarios);
        fabrica.escreverObjeto("cursos.ser", cursos);
        fabrica.escreverObjeto("disciplinas.ser", disciplinas);
        fabrica.escreverObjeto("proxMatricula.ser", proxMatricula);
    };

    /**
     * Lê usuários, cursos e disciplinas de arquivos.
     */
    @SuppressWarnings(value = "unchecked")
    public static void ler() {
        Fabrica fabrica = Fabrica.getInstancia();

        App.usuarios = (HashMap<Integer, Usuario>) fabrica.lerObjeto("usuarios.ser");
        App.cursos = (HashMap<String, Curso>) fabrica.lerObjeto("cursos.ser");
        App.disciplinas = (HashMap<String, Disciplina>) fabrica.lerObjeto("disciplinas.ser");
        App.proxMatricula = (Integer) fabrica.lerObjeto("proxMatricula.ser");
    };

}
