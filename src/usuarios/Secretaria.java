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

import error.ChaveInvalidaException;
import error.OperacaoNaoSuportadaException;

import app.App;
import app.Util;

/**
 * Classe que representa uma secretaria
 * 
 * @see Usuario
 */
public class Secretaria extends Usuario {

    /**
     * Construtor da classe Secretaria
     * 
     * @param matricula matrícula da secretaria
     * @param passwd    senha da secretaria
     */
    public Secretaria(Integer matricula, String passwd) {
        super(matricula, passwd);
    };

    /**
     * Menu de cadastro dos itens no sistema
     */
    public void menuCadastro() {
        Integer escolha = Util.lerInt(
                "\n\n1- Cadastrar Aluno\n2- Cadastrar Professor\n3- Cadastrar Disciplina\n4- Cadastrar Curso\n0- Voltar\n");
        try {
            switch (escolha) {
                case 1 -> App.getApp().novoAluno();
                case 2 -> App.getApp().novoProfessor();
                case 3 -> App.getApp().novaDisciplina();
                case 4 -> App.getApp().novoCurso();
            }
        } catch (ChaveInvalidaException e) {
            System.out.println("ERRO: Disciplina nao existente: \"" + e.getMessage()
                    + "\", adicione esta disciplina ao sistema antes de criar um curso.");
        } catch (OperacaoNaoSuportadaException e) {
            System.out.println("ERRO: Nao foram adicionadas 4 disciplinas.");
        }
    };

    /**
     * Menu de impressão dos itens no sistema
     */
    public void menuLeitura() {
        Integer escolha = Util
                .lerInt("\n\n1- Imprimir Alunos\n2- Imprimir Professores\n3- Imprimir Disciplinas\n4- Imprimir Cursos\n0- Voltar\n");
        switch (escolha) {
            case 1 -> App.getApp().printAlunos();
            case 2 -> App.getApp().printProfessores();
            case 3 -> App.getApp().printDisciplinas();
            case 4 -> App.getApp().printCursos();
        }
    };

    @Override
    public Boolean menu() {
        Integer escolha = Util.lerInt(
                "\n\n1- Cadastrar no sistema\n2- Imprimir curriculos\n3- Alterar Disciplina\n4- Abrir/Fechar matriculas\n0- Logoff\n");
        switch (escolha) {
            case 1 -> this.menuCadastro();
            case 2 -> this.menuLeitura();
            case 3 -> App.getApp().alterarDisciplina();
            case 4 -> {
                if (Util.lerStr("Deseja abrir ou fechar as matriculas? (abrir/fechar)").equalsIgnoreCase("abrir"))
                    App.getApp().abrirMatriculas();
                else
                    App.getApp().fecharMatriculas();
            }
            case 0 -> {
                return false;
            }
        }
        return true;
    };

    @Override
    public String toString() {
        return "Secretaria " + this.matricula;
    };

}
