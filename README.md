# Banco - Quarkus

Este é um projeto que gerencia contas correntes usando o framework Quarkus. Abaixo estão as instruções sobre como executar o projeto e as rotas disponíveis.

Se você deseja aprender mais sobre o Quarkus, visite o site oficial: https://quarkus.io/ .

## Executando a aplicação em modo de desenvolvimento
Você pode executar sua aplicação em modo de desenvolvimento, que permite codificação ao vivo, utilizando:
```shell script
./mvnw compile quarkus:dev
```
ou 
```shell script
quarkus dev
```


> **_NOTA:_**  O Quarkus agora inclui uma interface de usuário de desenvolvimento (Dev UI), disponível apenas no modo de desenvolvimento em http://localhost:8080/q/dev/.

## Endpoints

Endpoint principal: localhost:8080/api/v1/contacorrente/

Depois de iniciar o servidor, você pode acessar os seguintes Endpoint:

- Criar Conta:

[localhost:8080/api/v1/contacorrente/criarconta](localhost:8080/api/v1/contacorrente/criarconta): **POST**.

- Contas

[localhost:8080/api/v1/contacorrente](localhost:8080/api/v1/contacorrente): **GET**.

- Ver Saldo:

[localhost:8080/contacorrente/saldo/{numConta}](localhost:8080/contacorrente/saldo/{numConta}): **GET**.

- Depositar:

[localhost:8080/api/v1/contacorrente/saldo/{id}](localhost:8080/api/v1/contacorrente/saldo/{id}): **POST**.

- Sacar:

[localhost:8080/api/v1/contacorrente/sacar) (localhost:8080/api/v1/contacorrente/sacar): **POST**.

- Transferir:

[localhost:8080/api/v1/contacorrente/transferir](localhost:8080/api/v1/contacorrente/transferir): **PATCH**.

- Deletar:

[localhost:8080/api/v1/contacorrente/excluir/{id}](localhost:8080/api/v1/contacorrente/excluir/{id}): **DELETE**.
