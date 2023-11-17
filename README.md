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

Endpoint principal: api/v1/contacorrente/

Depois de iniciar o servidor, você pode acessar os seguintes Endpoint:

- Criar Conta:

[/api/v1/contacorrente/criarconta](api/v1/contacorrente/criarconta): **POST**.

- Contas

[/api/v1/contacorrente](api/v1/contacorrente): **GET**.

- Ver Saldo:

[/contacorrente/saldo/{numConta}](contacorrente/saldo/{numConta}): **GET**.

- Depositar:

[/api/v1/contacorrente/saldo/{id}](api/v1/contacorrente/saldo/{id}): **POST**.

- Sacar:

[/api/v1/contacorrente/sacar) (api/v1/contacorrente/sacar): **POST**.

- Transferir:

[/api/v1/contacorrente/transferir](api/v1/contacorrente/transferir): **PATCH**.

- Deletar:

[/api/v1/contacorrente/excluir/{id}](api/v1/contacorrente/excluir/{id}): **DELETE**.
