# Instruções da pasta src

## Organização de pacotes

    app -> O pacote "app" contém a classe principal App, que inicia a execução do sistema de matrícula.

    curso -> O pacote "curso" contém as classes relacionadas a cursos e disciplinas, incluindo as classes Curso e Disciplina.

    error -> O pacote "error" contém as classes de exceção personalizadas utilizadas no sistema, incluindo as classes ChaveInvalidaException e OperacaoNaoSuportadaException.

    usuarios -> O pacote "usuarios" contém as classes relacionadas aos diferentes tipos de usuários do sistema, incluindo as classes Aluno, Professor, Secretaria e a classe pai Usuario.

## Requisitos

- Java 20

## Execução

Para executar o programa, baixe o arquivo .jar no repositório e execute o comando:

``` bash
    java -jar ./Matriculas.jar
```
