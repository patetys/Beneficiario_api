function dadosIniciaisPesquisaTO(){
	this.listaRamos = new Array();           
	this.listaCorretores = new Array();
	this.periodoInicial;                   
	this.periodoFinal;                     
	this.nomeSegurado;                     
	this.cpfCnpj;                                                         
	this.apolice;                          
	this.endosso;                          
	this.item;                             
	this.codRamo;                         
	this.codCorretor;                      
	this.codCliente;                       
	this.numSusep;                         
	this.dataInicio;                         
	this.dataFim;                            
};

function comboCorretoresTO(){
	this.textCorretor;
	this.valueCorretor;
};

function comboRamosTO(){
	this.textRamo;  
	this.valueRamo; 
};
function resultadoTO(){
	this.data = new Array();
};
function outrasInfoApoliceResultadoTO(){
	this.dataVigenciaInicial;      
	this.dataVigenciaFinal;        
	this.enderecoCorretor;         
	this.cidadeCorretor;           
	this.ufCorretor;               
	this.cepCorretor;              
	this.enderecoCorrespondencia;  
	this.cidadeCorrespondencia;    
	this.ufCorrespondencia;        
	this.cepCorrespondencia;       
	this.codigoProdutor;           
	this.nomeProdutor;             
	this.codigoUnidade;            
	this.nomeUnidade;              
};
function apoliceResultadoTO(){
	this.codigoCorretor;    
	this.codigoCorretor2;   
	this.codigoCorretor3;   
	this.nomeSegurado;      
	this.numeroApolice;     
	this.numeroEndosso;     
	this.tipoDocumento;     
	this.dataEmissaoApolice;
	this.item;
	this.mensagem;
	this.dadosApolice = new outrasInfoApoliceResultadoTO();
};
