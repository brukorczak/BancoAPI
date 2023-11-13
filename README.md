# code-with-quarkus

Este projeto utiliza o Quarkus, o Framework Java Supersônico Subatômico.

Se você deseja aprender mais sobre o Quarkus, visite o site oficial: https://quarkus.io/ .

## Executando a aplicação em modo de desenvolvimento

Você pode executar sua aplicação em modo de desenvolvimento, que permite codificação ao vivo, utilizando:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTA:_**  O Quarkus agora inclui uma interface de usuário de desenvolvimento (Dev UI), disponível apenas no modo de desenvolvimento em http://localhost:8080/q/dev/.

## Empacotando e executando a aplicação

A aplicação pode ser empacotada utilizando:
```shell script
./mvnw package
```
Isso produz o arquivo quarkus-run.jar no diretório target/quarkus-app/.
Este não é um über-jar, pois as dependências são copiadas para o diretório target/quarkus-app/lib/.

A aplicação pode ser executada usando java -jar target/quarkus-app/quarkus-run.jar.

Se você deseja construir um über-jar, execute o seguinte comando:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Criando um executável nativo

Você pode criar um executável nativo usando:
```shell script
./mvnw package -Dnative
```

Ou, se você não tiver o GraalVM instalado, pode executar a compilação do executável nativo em um contêiner usando:
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

Em seguida, você pode executar seu executável nativo com:  `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

Se desejar aprender mais sobre a criação de executáveis nativos, consulte  https://quarkus.io/guides/maven-tooling.

## Código Fornecido

### RESTEasy Reactive

Inicie facilmente seus Serviços Web Reativos RESTful.

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
