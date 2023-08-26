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

/**
 * Classe que representa uma secretaria
 * 
 * @see Usuario
 */
public class Secretaria implements Usuario {

    /** matricula da secretaria */
    private Integer matricula;

    /** senha da secretaria */
    private String passwd;

    /**
     * Construtor da classe Secretaria
     * 
     * @param matricula matricula da secretaria
     * @param passwd    senha da secretaria
     */
    public Secretaria(Integer matricula, String passwd) {
        this.matricula = matricula;
        this.passwd = passwd;
    }

    @Override
    public Usuario login(String passwd) {
        return this.passwd.equals(passwd) // verificar se a matricula e a senha são iguais as da secretaria
                ? this
                : null;
    }

    @Override
    public Boolean menu() {
        return true;
    }

}
