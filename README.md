# EKAN - API REST para cadastro de beneficiários de um plano de saúde

### Do que se trata esse Repositório ###
EKAN é uma API REST desenvolvida para manter o cadastro de beneficiários de um plano de saúde.

## Arquitetura ##

A arquitetura utilizada é baseada na Layered Architecture, com alguns conceitos de
DDD, como a proposto por Eric Evans no projeto [DDD Sample](http://dddsample.sourceforge.net/architecture.html)
Arquitetura organiza o software de forma bem simples em três camadas horizontais 
suportadas por uma quarta camada vertical de infraestrutura. 

## Tecnologias Utilizadas

- Lombok 1.18.30
- Java SDK 17
- ModelMapper 3.1.1
- Swagger 1.5.0
- H2 Database
- Spring Boot 3.2.3
- Spring Framework 6.1.4
- org.springdoc 1.6.14
- Maven 3.9.6
- Spring data JPA para persistir num banco relacional (H2 em memória foi o escolhido)
- Tomcat Embarcado

### Banco de Dados ###
- Foi utilizado H2 em memória para Desenvolvimento e Testes. 

## Pré-requisitos de Tecnologia

Certifique-se de ter as seguintes tecnologias instaladas:

- Java 17
- Maven 3.9.6

### Executar localmente ###
> mvn package -DskipTests && java -jar target/beneficiario_api-0.1.0.jar

### Executar testes ###
> mvn test

### Cenários de Testes ###
Os cenários de testes no estilo BDD foram escritos em java utilizando 
[Spock](https://code.google.com/p/spock/) como framework.
O relatório de execução é gerado a cada execução dos testes no diretório build/spock-reports/index.html.
Um exemplo do relatório pode ser acessado clicando [aqui](http://rodrigormu.github.io/rest-api-endereco/).

A aplicação será executada na porta 8080.
O Swagger UI estará acessível em http://localhost:8080/swagger-ui/index.html.
Url para cessar o banco de dados local H2 em http://localhost:8080/h2-console

### CRUD de Beneficiario ###

#### Criar um novo Beneficiario ####

* http POST - http://localhost:8080/api/v1/beneficiarios
* json body:
```
#!json
{
    "nome": "João das colves ",
    "telefone": "11976471477",
    "dataNascimento": "2024-03-11T06:17:18.815Z",
    "documentos": [
        {
            "tipoDocumento": "CPF",
            "descricao": "CPF"
        },
        {
            "tipoDocumento": "RG",
            "descricao": "RG"
        },
          {
            "tipoDocumento": "CNH",
            "descricao": "CNH"
        }
    ]
}
```
* returna http 200 com o beneficiario e documentos criado:
```
* retorna http 400 se violar alguma regra de obrigatoriedade ou formato.

### Buscar todos os beneficiarios ###

* http GET -  http://localhost:8080/api/v1/beneficiarios

* retorna:
```
#!json
{
    "nome": "João das colves ",
    "telefone": "11976471477",
    "dataNascimento": "2024-03-11T06:17:18.815Z",
    "documentos": [
        {
            "tipoDocumento": "CPF",
            "descricao": "CPF"
        },
        {
            "tipoDocumento": "RG",
            "descricao": "RG"
        },
          {
            "tipoDocumento": "CNH",
            "descricao": "CNH"
        }
    ]
}
```
* retorna http 404 se não encontrar endereço do cliente

### Busca os documentos de um beneficiario ###

* http get - http://localhost:8080/api/v1/beneficiarios/2/documentos
* json body:
```
#!json
[
    {
        "id": 4,
        "tipoDocumento": "CPF",
        "descricao": "CPF"
    },
    {
        "id": 5,
        "tipoDocumento": "RG",
        "descricao": "RG"
    },
    {
        "id": 6,
        "tipoDocumento": "CNH",
        "descricao": "CNH"
    }
]
```
* retorna http 200 se retornar documetnos
* retorna http 400 se não encontrar.
```
### Atualiza dados do Beneficiario ###

* http get - http://localhost:8080/api/v1/beneficiarios/2
* json body:
```
#!json
{
    "nome": "Benjsamin Samuel do Anjos Lima2",
    "telefone": "11854745444",
    "dataNascimento": "1976-06-03"
}
```
* retorna http 200 se atualizar
* retorna http 400 se não encontrar.

* ### Deletar um Beneficiario e Documentos ###

* http get - http://localhost:8080/api/v1/beneficiarios/1
* json body:
```
#!json

```
* retorna http 204 sem conteudo
* retorna http 400 se não encontrar.

```

 
