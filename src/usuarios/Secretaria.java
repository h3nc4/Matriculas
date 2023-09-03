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

/**
 * Classe que representa uma secretaria
 * 
 * @see Usuario
 */
public class Secretaria extends Usuario {

    /**
     * Construtor da classe Secretaria
     * 
     * @param matricula matricula da secretaria
     * @param passwd    senha da secretaria
     */
    public Secretaria(Integer matricula, String passwd) {
        super(matricula, passwd);
    };

    @Override
    public Boolean menu() {
        Integer escolha = App.lerInt(
                " 1- Cadastrar Aluno\n 2- Cadastrar Professor\n 3- Cadastrar Disciplina\n 4- Cadastrar Curso\n 5- Salvar\n 6- Carregar\n 0- Sair\n");
        switch (escolha) {
            case 1 -> App.novoAluno();
            case 2 -> App.novoProfessor();
            case 3 -> App.novaDisciplina();
            case 4 -> {
                try {
                    App.novoCurso();
                } catch (ChaveInvalidaException e) {
                    System.out.println(" ERRO: Disciplina nao existente: \"" + e.getMessage()
                            + "\", adicione esta disciplina ao sistema antes de criar um curso.");
                } catch (OperacaoNaoSuportadaException e) {
                    System.out.println(" ERRO: Nao foram adicionadas 4 disciplinas iniciais.");
                }
            }
            case 5 -> App.escrever();
            case 6 -> App.ler();
            case 0 -> {
                System.out.println("Saindo...");
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
