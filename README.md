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

Endpoint principal: /contacorrente

Depois de iniciar o servidor, você pode acessar os seguintes Endpoint:

- Criar Conta:

[http://localhost:8080/contacorrente](http://localhost:8080/contacorrente): POST.

- Ver Saldo:

[http://localhost:8080/contacorrente/saldo/{numConta}](http://localhost:8080/contacorrente/saldo/{numConta}): GET.

- Depositar:

[http://localhost:8080/contacorrente/depositar](http://localhost:8080/contacorrente/depositar): POST.

- Sacar:

[http://localhost:8080/contacorrente/sacar](http://localhost:8080/contacorrente/sacar): POST.

- Transferir:

[http://localhost:8080/contacorrente/transferir](http://localhost:8080/contacorrente/transferir): PATCH.
