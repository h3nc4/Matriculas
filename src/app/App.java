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
public class App implements java.io.Serializable {

    /** Instância única da classe */
    private static App instancia = null;

    /** Matriculas abertas */
    public Boolean matriculasAbertas;

    /** Matrícula atual */
    private Integer proxMatricula;

    /** Usuário atual */
    private Optional<Usuario> usuarioAtual;

    /** Mapa de usuários */
    private HashMap<Integer, Usuario> usuarios;

    /** Mapa de cursos */
    private HashMap<String, Curso> cursos;

    /** Mapa de disciplinas */
    private HashMap<String, Disciplina> disciplinas;

    /** Padrão Singleton */
    private App() {
        this.matriculasAbertas = Boolean.FALSE;
        this.proxMatricula = 1;
        this.usuarioAtual = null;
        this.usuarios = new HashMap<>();
        this.cursos = new HashMap<>();
        this.disciplinas = new HashMap<>();
    }; // @formatter:off

    /** Padrão Singleton 
     * @return instância única da classe */
    public static App getApp() { if (App.instancia == null) App.instancia = new App(); return App.instancia; };

    /** Retorna o estado das matrículas
     * @return {@code TRUE} se as matrículas estão abertas ou {@code FALSE} caso estejam fechadas */
    public Boolean matriculasAbertas() { return this.matriculasAbertas; };

    /** Abre as matrículas */
    public void abrirMatriculas() { this.matriculasAbertas = Boolean.TRUE; };

    /** Fecha as matrículas */
    public void fecharMatriculas() { this.matriculasAbertas = Boolean.FALSE; };

    /** Busca uma disciplina
     * @param nome nome da disciplina
     * @return disciplina*/ 
    public Disciplina getDisciplina(String nome) { return disciplinas.get(nome); }; // @formatter:on

    /**
     * Realiza o login do usuário.
     * 
     * @return TRUE caso o login tenha sido mal sucedido, FALSE caso o usuário
     *         deseje sair do programa.
     */
    public Boolean login() {
        Util.limparTerminal();
        try {
            Integer user = Util.lerInt(" " + Util.saudacao());
            if (user == -1)
                return Boolean.FALSE;
            this.usuarioAtual = Optional.ofNullable(usuarios.get(user).login(Util.lerStr(" Senha: ")));
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
    public void novoAluno() {
        String senha = Util.lerStr(" Matricula: " + this.proxMatricula + "\n Senha: ");
        Curso curso = null;
        while (curso == null) {
            curso = this.cursos.get(Util.lerStr(" Curso: ").toLowerCase());
            if (curso == null) {
                System.out.println(" ERRO: Curso invalido, deseja adicionar o curso antes? (s/n)");
                if (Util.lerStr(" ").equalsIgnoreCase("s"))
                    this.novoCurso();
            }
        }
        this.usuarios.put(
                this.proxMatricula,
                new Aluno(
                        Util.lerStr(" Nome: "),
                        this.proxMatricula++,
                        senha,
                        curso //
                ) //
        );
    };

    /**
     * Cria e insere um novo professor no mapa de usuários.
     */
    public void novoProfessor() {
        this.usuarios.put(
                this.proxMatricula,
                new Professor(
                        this.proxMatricula++,
                        Util.lerStr(" Matricula: " + this.proxMatricula + "\n Senha: ") //
                ) //
        );
    };

    /**
     * Cria e insere uma nova secretaria no mapa de usuários.
     */
    public void novaSecretaria() {
        this.usuarios.put(
                this.proxMatricula,
                new Secretaria(
                        this.proxMatricula++,
                        Util.lerStr(" Matricula: " + this.proxMatricula + "\n Senha: ") //
                ) //
        );
    };

    /**
     * Busca disciplinas digitadas pelo usuário no mapa de disciplinas.
     * 
     * @param mensagem a ser exibida ao usuário.
     * @return stream de disciplinas de acordo com o que o usuário digitou.
     */
    private Stream<Disciplina> buscaDisciplinas(String mensagem) {
        return Stream.of(Util.lerStr(mensagem).split(",")) // lê as disciplinas do usuário
                .map(d -> {
                    Disciplina add = this.disciplinas.get(d.toLowerCase()); // busca a disciplina no mapa de disciplinas
                    if (add == null)
                        throw new ChaveInvalidaException(d); // caso a disciplina não exista, lança uma exceção passando
                                                             // o nome da disciplina para avisar o usuário
                    return add;
                });
    };

    /**
     * Lista as disciplinas disponíveis para professores lecionarem
     * 
     * @return disciplinas disponíveis
     */
    public String listarDisciplinas() {
        StringBuilder disciplinas = new StringBuilder();
        this.disciplinas.values().stream() // transforma o mapa de disciplinas em um stream
                .filter(d -> !d.temProfessor()) // filtra as disciplinas que não possuem professor
                .forEach(d -> disciplinas.append(d.getNome() + "\n")); // adiciona o nome das disciplinas ao
                                                                       // StringBuilder
        return new String(disciplinas);
    };

    /**
     * Cria e insere um novo curso no mapa de cursos.
     */
    public void novoCurso() throws ChaveInvalidaException, OperacaoNaoSuportadaException {
        String nome = Util.lerStr(" Nome: ").toLowerCase(); // lê o nome do curso do usuário

        HashMap<String, Disciplina> disciplinasC = (HashMap<String, Disciplina>) this
                .buscaDisciplinas(" Digite as disciplinas do curso separadas por virgula: ")
                .collect(
                        Collectors.toMap(d -> d.getNome(), d -> d) //
                ); // transforma o stream em um mapa e o retorna para a variável disciplinasC

        Disciplina[] disciplinasIni = this
                .buscaDisciplinas(" Digite as 4 disciplinas iniciais do curso separadas por virgula: ")
                .collect(Collectors.toList()) // transforma o stream em uma lista
                .toArray(Disciplina[]::new); // transforma a lista em um array

        if (disciplinasIni.length != 4) // caso não tenham sido adicionadas 4 disciplinas, lança uma exceção
            throw new OperacaoNaoSuportadaException();

        this.cursos.put(
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
    public void novaDisciplina() {
        String nome = Util.lerStr(" Nome: ").toLowerCase();
        this.disciplinas.put(
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
    public void printAlunos() {
        this.usuarios.values().stream() // transforma o mapa de usuários em um stream
                .filter(u -> u instanceof Aluno) // filtra os alunos
                .forEach(System.out::println); // imprime os alunos
    };

    /**
     * Imprime os professores do mapa de usuários.
     */
    public void printProfessores() {
        this.usuarios.values().stream() // transforma o mapa de usuários em um stream
                .filter(u -> u instanceof Professor) // filtra os professores
                .forEach(System.out::println); // imprime os professores
    };

    /**
     * \
     * Imprime as disciplinas do mapa de disciplinas.
     */
    public void printDisciplinas() {
        this.disciplinas.values() // transforma o mapa de disciplinas em uma coleção
                .forEach(System.out::println); // imprime as disciplinas
    };

    /**
     * Imprime os cursos do mapa de cursos.
     */
    public void printCursos() {
        this.cursos.values() // transforma o mapa de cursos em uma coleção
                .forEach(System.out::println); // imprime os cursos
    };

    /**
     * Altera os dados de uma disciplina no mapa de disciplinas.
     */
    public void alterarDisciplina() {
        Disciplina disciplina = this.disciplinas.get(Util.lerStr(" Digite o nome da disciplina: ").toLowerCase());
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
        App app = App.getApp();
        app.usuarios.put(0, new Secretaria(0, "admin123")); // secretaria padrão

        while (app.login()) { // Neste loop, o usuário pode ser deslogado mas não pode sair do programa
            while (app.usuarioAtual.get().menu()) // Neste loop, o usuário é apresentado ao seu menu e caso ele retorne
                                                  // TRUE, o menu é exibido novamente, caso contrário, o usuário é
                                                  // deslogado e enviado para o loop externo
                ;
        }
    };

    /**
     * Escreve usuários, cursos e disciplinas em arquivos.
     */
    public void escrever() {
        Fabrica fabrica = Fabrica.getInstancia();

        this.usuarioAtual = Optional.empty();
        fabrica.escreverObjeto("sistema.ser", this);
    };

    /**
     * Lê usuários, cursos e disciplinas de arquivos.
     */
    public static void ler() {
        Fabrica fabrica = Fabrica.getInstancia();

        App.instancia = (App) fabrica.lerObjeto("sistema.ser");
    };

}
