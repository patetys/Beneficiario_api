Passos para execução do projeto no Eclipse/STS com WildFly

Para compilação:

	1-) Caso não tenha sido instalado o Wildfly local no Eclipse, realizar a instalação. 
	    Project -> Targered Runtimes -> New. 
	    - Selecionar servidor desejado e concluir a instalação local. 

	2-) Botão direito no projeto
	    2.1-) Selecionar Build Path -> "Configure Build Path".
	    
	          Em Build Path, aba Libraries, Clicar em "Add Library": 
	          - Selecionar "JRE System Library" -> selecionar pasta com JDK 1.8.X 
	          - Selecionar "Server Runtime" -> selecionar TomCat instalado local e confirmar. 
	          
	          Em Build Path, aba Libraries, Clicar em "Add JAR":
	          - Selecionar todos os JAR's da pasta CotacaoMulti\WebContent\Web-INF\lib e importar todas. 
	        
		2.2-) Project Facets
			  - Convert to faceted form(Caso nao esteja configurado)
			  - Selecionar Dynamic Web Module(2.3)
			  - Selecionar Java(1.8+)
			  - Selecionar Javascript(1.0)
		
Para execução no WildFly

	1-) Esse projeto tem dependência do projeto YasudaUtils, portanto baixe-o e gere build do mesmo. 
	
	2-) Botão direito no projeto -> "Build Path" -> "Configure Build Path"
	    - Projects  -> Adicione o projeto YasudaUtils
			
	3-) IMPORTANTE: Esses ajustes não devem ser comitados.
	    O Wildfly utiliza url de conexao com o Banco de Dados diferente. Portanto, pesquise por "sqlProducao" em toda a workspace		
		- No arquivo DBAccess.java, altere a constante SQL_PROD para a sua url de conexao mapeada no WildFly(standalone.xml)
		- No arquivo ConnectionFactory.java do projeto YasudaUtils, altere a constante SQL_PROD para a sua url de conexao mapeada no WildFly(standalone.xml)


	4-) Adicionar esse projeto dentro do WildFly local e subir o mesmo.				

Para gerar o pacote para QA/PROD (Eclipse) - WAR File

	1-) Após os ajustes no Build Path do projeto, gerar um Build da Aplicação.
	    Botão direito sobre o projeto, acessar "Project" -> "Build Project". 
	    
	2-) Ajustar os apontamentos dos arquivos base.properties e config.properties de acordo com cada ambiente (QA/PROD). 
	
	3-) Exportar o pacote WAR da aplicação. 
	    Botão direito sobre o projeto, acessar "Export" -> "WAR file".
	    No primeiro campo, digitar "sompo-cotacaomulti", selecionar a pasta desejada e nomear o arquivo war como CotacaoMulti e marcar a opção "Export Source Files".  
		Verifique se após gerado o arquivo CotacaoMulti.war ao clicar nele, se na pasta principal estão os arquivos .jsp
		
URL para execução local: 

http://localhost:8080/CotacaoMulti/teste.jsp



