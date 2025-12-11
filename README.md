Sistema de Gerenciamento de Partidas do Brasileirão
Desenvolvido para a Situação Desafiadora – API REST com Regras Temporais
Curso: Superior de Tecnologia em Análise e Desenvolvimento de Sistemas
Faculdade de Tecnologia SENAI Antônio Adolpho Lobbe
Professor: Me. André Roberto da Silva

1. Contextualização
   Este projeto foi desenvolvido para atender ao desafio proposto na unidade de Desenvolvimento Back-End. O objetivo é simular a atuação de um desenvolvedor responsável por criar um MVP funcional que gerencie recursos, utilize persistência relacional e implemente regras de negócio que envolvam datas e horários.
   O sistema escolhido consiste em um gerenciador de partidas do Campeonato Brasileiro, permitindo cadastrar jogos, garantir consistência entre times, verificar conflitos de horário e expor todas as operações via API REST.

2. Objetivo Geral
   Construir uma API REST utilizando Java 17, Spark Java, JDBC puro e MySQL, aplicando o padrão DAO e manipulando dados temporais com a API java.time.
   O sistema deve permitir operações completas de CRUD envolvendo times e partidas, com validações e regras temporais corretas.

3. Tecnologias Utilizadas
   Java 17
   Spark Java (rotas e respostas HTTP)
   JDBC Puro com PreparedStatement
   Padrão DAO
   Gson (JSON)
   MySQL 8
   API java.time (LocalDateTime)

4. Escopo do Sistema e Regras de Negócio

4.1 Entidades
Time

* id_time
* nome
* serie (A, B ou C)
* escudo

Partida

* id_partida
* id_time_casa (FK para time)
* id_time_fora (FK para time)
* dataHoraInicio
* dataHoraFim

O relacionamento principal é 1:N, onde um time participa de várias partidas.

4.2 Regras de Negócio Implementadas

1. Times de séries diferentes não podem se enfrentar.

2. Um time não pode jogar contra ele mesmo.

3. Regra temporal: o sistema detecta conflitos de horário. Um time não pode participar de duas partidas cujos horários se sobrepõem.

4. O horário de término de uma partida é calculado com base na duração informada.

5. Todas as manipulações temporais utilizam exclusivamente LocalDateTime.

6. Interface com o Usuário (Frontend)
   O projeto utiliza a opção A (Fullstack).
   Foi implementada uma interface simples em HTML, CSS e React com Fetch API para consumir as rotas.
   Funcionalidades incluídas:

* Cadastro de partidas
* Listagem de partidas
* Edição de partidas
* Exclusão de partidas
* Filtro por série
* Filtro por time
* Busca de partida por ID
* Modal de edição
  O frontend consome a API exclusivamente via Fetch, sem bibliotecas adicionais.

6. Entregáveis e Etapas

6.1 Modelagem
Foi criado o DER contendo as tabelas time e partida, com chaves estrangeiras e relacionamento 1:N validado.

6.2 Desenvolvimento
As pastas foram organizadas em modelo, DAO, util e api.
Foi implementado tratamento de erro e respostas HTTP adequadas (400, 404 e 500).
Todo o código segue o padrão DAO e usa PreparedStatement.

6.3 Pitch
A apresentação demonstra a dor, a solução, o fluxo completo e um resumo da arquitetura adotada.

7. Rotas da API REST

Times
GET /times – lista todos os times
GET /times/:id – busca um time específico
GET /times/serie/:serie – lista times de uma série

Partidas
GET /partidas/:id – busca uma partida por ID
GET /partidas/serie/:serie – lista partidas de uma série
POST /partidas – cria uma partida (com regras de negócio)
PUT /partidas/:id – atualiza uma partida
DELETE /partidas/:id – exclui uma partida

8. Como Executar o Projeto

Banco de Dados
Importar o arquivo brasileirao.sql, que contém apenas a estrutura necessária (sem informações pessoais).

Configurar ConnectionFactory.java
Ajustar URL, usuário e senha do MySQL.

Executar a API
Rodar a classe principal (ApiBrasileirao).
O servidor Spark iniciará em [http://localhost:4567].


