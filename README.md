# Sistema de Gestão da Biblioteca Municipal

Sistema informático em consola, desenvolvido em Java, para apoiar a Biblioteca Municipal na gestão do inventário de livros e no histórico de empréstimos aos utilizadores.

Trabalho de Campo — Disciplina de **Introdução a Algoritmos e Programação**
Curso de Licenciatura em Engenharia Informática — Faculdade de Engenharia e Agricultura
**Universidade Aberta ISCED — UnISCED**

## Funcionalidades

- **Registo de Livros** — inserção de novos títulos no catálogo (ID único, título, autor, ano de publicação, quantidade disponível).
- **Consulta de Catálogo** — listagem de todos os livros disponíveis, ou pesquisa por autor ou por título.
- **Gestão de Empréstimos** — empréstimo de um livro a um utilizador registado (com diminuição automática da quantidade disponível) e registo da respetiva devolução.
- **Estatísticas** — livro mais emprestado e número total de livros requisitados.

## Estrutura do projeto

```
biblioteca-municipal/
├── src/
│   ├── Main.java          # Menu interativo e ponto de entrada do programa
│   ├── Biblioteca.java     # Lógica de negócio e manipulação da base de dados em memória
│   ├── Livro.java          # Classe-modelo Livro
│   └── Utilizador.java     # Classe-modelo Utilizador
└── README.md
```

## Estruturas de dados utilizadas

O sistema simula uma base de dados em memória, sem recurso a ficheiros externos ou bases de dados reais, utilizando:

- `Livro[]` — array de objetos `Livro`, representando o catálogo.
- `Utilizador[]` — array de objetos `Utilizador`, representando os utilizadores registados.
- `String[][] historicoEmprestimos` — **matriz** que regista cada empréstimo (ID do livro, ID do utilizador, data do empréstimo, data de devolução ou `"pendente"`).

## Requisitos

- **Java JDK 17 ou superior** (testado com OpenJDK 21).
- Terminal com suporte a **UTF-8** (para exibir corretamente os caracteres acentuados).

Verificar a instalação:

```bash
java -version
javac -version
```

## Como compilar e executar

Clonar o repositório e entrar na pasta do projeto:

```bash
git clone <URL-do-repositório>
cd biblioteca-municipal
```

Compilar todos os ficheiros-fonte:

```bash
cd src
javac *.java -d ../bin
```

Executar o programa (recomenda-se forçar UTF-8 para evitar problemas de acentuação em alguns terminais, nomeadamente no Windows):

```bash
cd ../bin
java -Dfile.encoding=UTF-8 Main
```

No **Windows (cmd/PowerShell)**, recomenda-se ainda configurar a página de código antes de executar:

```cmd
chcp 65001
java -Dfile.encoding=UTF-8 Main
```

## Dados de demonstração

Ao iniciar, o programa carrega automaticamente 3 livros e 2 utilizadores de exemplo, para facilitar os testes imediatos de todas as funcionalidades (empréstimo, devolução, estatísticas).

## Tratamento de erros implementado

- Catálogo, lista de utilizadores ou histórico de empréstimos cheios (limite de capacidade).
- IDs duplicados no registo de livros ou utilizadores.
- Livro ou utilizador inexistente (ID inválido) em operações de empréstimo/devolução.
- Empréstimo recusado quando não há exemplares disponíveis.
- Validação de campos obrigatórios (título, autor, nome não vazios).
- Validação de entradas não numéricas no menu (`InputMismatchException` tratada via `try/catch`).

## Autor

Desenvolvido por **idurcio dos santos Vasco Alfredo cumbane**, estudante de Licenciatura em Engenharia Informática, no âmbito do Trabalho de Campo de Introdução a Algoritmos e Programação, UnISCED.
