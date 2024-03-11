EKAN
a API REST para manter o cadastro de beneficiários de um plano de saúde.

##Tecnologias Para o desenvolvimento da aplicação, foram utilizadas às tecnologias e libs abaixo:

Nome	Versão
lombok 1.18.30
Java SDK 17
modelmapper 3.1.1
swagger 1.5.0
h2database
springBoot 3.2.3
springframework 6.1.4
org.springdoc 1.6.14
maven 3.9.6
| ##Pré requisitos de tecnologia

Instalar o Java 17, por ventura se estiver a utilizar Linux ou Mac pode utilizar o
SDK Man para fazer a gestão de versões do Java;
Instalar o maven-3.9.6, por ventura se estiver a utilizar Linux ou Mac pode utilizar o
SDK Man para fazer a gestão de versões do maven;
##Build

Fazer o build da aplicação através do comando build gradle(Necessário ter o gradle instalado) ou./gradlew build(Busca a partir do arquivo gradle-wrapper.properties).
##Execução

Executar a aplicação através do comando java -jar build/libs/documents.jar ;
Pontos a serem observados no processo de execução são:
Execução na porta 8080;
Acesso através do swagger-ui = (http://localhost:8080/swagger-ui/index.html)
