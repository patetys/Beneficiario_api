// menupesq.js


function tipoCotacaoChanged() {
	if (document.frm1.tpCotacao[0].checked) {
	    document.frm1.cCotacao.value="";
		document.frm1.cCotacao.readOnly=true;
	} else {
		document.frm1.cCotacao.readOnly=false;
	}
}

function CmbTpEmissaoChanged(){
	var indTipEmissao = 0;
	var textoTipEmissao = "";	
	
	indTipEmissao = document.frm1.TipEmissao.selectedIndex;   		        	
    textoTipEmissao = document.getElementById("TipEmissao").options.item(indTipEmissao).text; 

	if (textoTipEmissao != "Endosso") {
        document.frm1.numApol.value="";
	}   	  	
  
 }
function removeOptions(selectElement) {
	   var i, L = selectElement.options.length - 1;
	   for(i = L; i >= 0; i--) {
	      selectElement.remove(i);
	   }
	}
function CmbRamoChanged(r,intranet){
	var i=1;
	var ate=0;
	var iPrioridade=0;
	var texto="";
	var ramo="";
	
	//Resetando TipEmissao
	removeOptions(document.getElementById("TipEmissao"));
	document.getElementById("TipEmissao").add(document.createElement("option"));
	document.getElementById("TipEmissao").add(document.createElement("option"));
	document.getElementById("TipEmissao").add(document.createElement("option"));
	document.getElementById("TipEmissao").add(document.createElement("option"));
	document.getElementById("TipEmissao").add(document.createElement("option"));
	document.getElementById("TipEmissao").options.item(0).text = " ";
	document.getElementById("TipEmissao").options.item(0).value = " ";
	document.getElementById("TipEmissao").options.item(1).text = "Seguro Novo";
	document.getElementById("TipEmissao").options.item(1).value = "Seguro Novo";
	document.getElementById("TipEmissao").options.item(2).text = "Renov Congênere";
	document.getElementById("TipEmissao").options.item(2).value = "Renov Congênere";
	document.getElementById("TipEmissao").options.item(3).text = "Renov Sompo";
	document.getElementById("TipEmissao").options.item(3).value = "Renov Sompo";
	document.getElementById("TipEmissao").options.item(4).text = "Endosso";
	document.getElementById("TipEmissao").options.item(4).value = "Endosso";
	//Fim-Resetando
	
	if(document.frm1.codRamo.value == "150 - Roubo"){
		alert("Atendendo a Circular Susep 535, para cotações do produto Roubo,\nSelecione o ramo 710 - Riscos Diversos e Modalidade Roubo.");
		document.frm1.codRamo.selectedIndex = 0;
		return false;
	}
	
	// Redirect para nova tela de cotação (ramo RC/Engenharia/Gantia)
	//Retirada chamada para tela nova
//	if(document.frm1.codRamo.value == "510 - Responsabilidade Civil" 
//			|| document.frm1.codRamo.value == "670 - Riscos de Engenharia"){
//		document.frmNew.submit();	
//		return false;
//	}
	
	if(r==null){
		r = 0;
	}	
     
	iPrioridade = document.frm1.codRamo.selectedIndex;
     
    texto = document.getElementById("codRamo").options.item(iPrioridade).text;
   
    if (iPrioridade > 0){
	    ramo= texto.substr(0,3);
	}   
	   
    document.frm1.codMod.disabled = true;
	
    document.frm1.codMod.selectedIndex = 0; 

	//JIRA-279947 && JIRA-292164
	if(ramo == 780 || ramo == 100) {
		document.getElementById("TipEmissao").remove(2);
		document.getElementById("TipEmissao").remove(1);   			
	} else {
		if(intranet != "1"){
			//JIRA-168467
			if(ramo == 750){
				if(document.getElementById("TipEmissao").length == 5){
					document.getElementById("TipEmissao").remove(2);
					document.getElementById("TipEmissao").remove(1);   			
				}
			}else{     		
				if(document.getElementById("TipEmissao").length == 3){
					document.getElementById("TipEmissao").add(document.createElement("option"));   
					document.getElementById("TipEmissao").add(document.createElement("option"));     			
					
					for(i = 4; i >= 1; i--){  
						if(i>2){
							document.getElementById("TipEmissao").options.item(i).text = document.getElementById("TipEmissao").options.item(i-2).text;
							document.getElementById("TipEmissao").options.item(i).value = document.getElementById("TipEmissao").options.item(i-2).text;
						}
						if(i == 1){
							document.getElementById("TipEmissao").options.item(i).text = "Seguro Novo";
							document.getElementById("TipEmissao").options.item(i).value = "Seguro Novo";
						}
						if(i == 2){
							document.getElementById("TipEmissao").options.item(i).text = "Renov Congênere";
							document.getElementById("TipEmissao").options.item(i).value = "Renov Congênere";
						}
					} 
					
				}
				else if(document.getElementById("TipEmissao").length == 2) {
					document.getElementById("TipEmissao").add(document.createElement("option"));
					document.getElementById("TipEmissao").add(document.createElement("option"));
					document.getElementById("TipEmissao").add(document.createElement("option"));

					for (i = 4; i >= 1; i--) {
						if (i > 3) {
							document.getElementById("TipEmissao").options.item(i).text = document.getElementById("TipEmissao").options.item(i - 3).text;
							document.getElementById("TipEmissao").options.item(i).value = document.getElementById("TipEmissao").options.item(i - 3).text;
						}
						if (i == 1) {
							document.getElementById("TipEmissao").options.item(i).text = "Seguro Novo";
							document.getElementById("TipEmissao").options.item(i).value = "Seguro Novo";
			}  
						if (i == 2) {
							document.getElementById("TipEmissao").options.item(i).text = "Renov Congênere";
							document.getElementById("TipEmissao").options.item(i).value = "Renov Congênere";
		}
						if (i == 3) {
							document.getElementById("TipEmissao").options.item(i).text = "Renov Sompo";
							document.getElementById("TipEmissao").options.item(i).value = "Renov Sompo";
						}
					}
		
				}
			}  
		}
	}
    
//    if (ramo=="150") {			
//    	ate=17;
//		modalidade = new CriaArray(17);
//	   //Ramo 150 - Diversos
//		modalidade[0]= ""
//		modalidade[1]= "01 - All Risk's";
//		modalidade[2]= "02 - Comercial/Industrial";
//		modalidade[3]= "03 - Residencial";
//		modalidade[4]= "";
//		modalidade[5]= "";
//		modalidade[6]= "";
//		modalidade[7]= "";
//		modalidade[8]= "";
//		modalidade[9]= "";		
//		modalidade[10]= "";	
//		modalidade[11]= "";	
//		modalidade[12]= "";	
//		modalidade[13]= "";	
//		modalidade[14]= "";	
//		modalidade[15]= "";	
//		modalidade[16]= "";	
//		modalidade[17]= "";	
//	} else 
    if (ramo=="112") {			
    	ate=17;
		modalidade = new CriaArray(17);
	   //Ramo 112 - Empresarial
		modalidade[0]= ""
		modalidade[1]= "01 - Empresarial";
		modalidade[2]= "02 - Imobiliário Empresarial";	
	} else if (ramo=="113") {			
    	ate=17;
		modalidade = new CriaArray(17);
	   //Ramo 113 - Residencial
		modalidade[0]= ""
		if(intranet == "1"){
			modalidade[1]= "01 - Residencial";
			modalidade[2]= "02 - Imobiliário Residencial";
		}
		else if(intranet != "1" && (document.frm1.CodCorrVoltaJs.value=="0908624" || document.frm1.CodCorrVoltaJs.value=="0944587")){
			modalidade[1]= "01 - Residencial";
		}
		else{
			modalidade[2]= "02 - Imobiliário Residencial";
		}
		
	}  else if (ramo=="670") {	
		//Ramo 670
		ate=17;
		modalidade = new CriaArray(17);		
		modalidade[0]= "";
		modalidade[1]= "03 - Instalação e montagem";
		modalidade[2]= "04 - Obras civis em construção";
		modalidade[3]= "05 - Obras civis em construção e Instalação e montagem";		
		modalidade[4]= "";
		modalidade[5]= "";
		modalidade[6]= "";
		modalidade[7]= "";
		modalidade[8]= "";
		modalidade[9]= "";		
		modalidade[10]= "";	
		modalidade[11]= "";	
		modalidade[12]= "";	
		modalidade[13]= "";	
		modalidade[14]= "";	
		modalidade[15]= "";	
		modalidade[16]= "";	
		modalidade[17]= "";	
	}else if (ramo=="710"){
		ate=17;
		modalidade = new CriaArray(17);
		//Ramo 710 - Diversos
		modalidade[0]= ""
		modalidade[1]= "01 - Exposição";
		modalidade[2]= "02 - Portáteis";
		modalidade[3]= "03 - Móveis";
		modalidade[4]= "04 - Instrumentos Musicais";
		modalidade[5]= "05 - Arrendados";
		modalidade[6]= "06 - Estacionários";
		modalidade[7]= "07 - Valores";
		modalidade[8]= "08 - Anúncios Luminosos/Antenas";
		modalidade[9]= "09 - Cinematográficos";
		modalidade[10]= "10- Operações sobre água";
		modalidade[11]= "11- Sompo Equipamentos";	
		modalidade[12]= "12- Compreensivo de Veículos";	
		modalidade[13]= "13- Tarifa (demais modalidades)";	
		modalidade[14]= "";	
		modalidade[15]= "15- Roubo All Risk's";	
		modalidade[16]= "16- Roubo Comercial/Industrial";	
		modalidade[17]= "17- Roubo Residencial";
		
		document.getElementById("codMod").options.item(11).style.display = 'none';
		document.getElementById("codMod").options.item(14).style.display = 'none';
				
	}else if (ramo=="510"){	
	    //Ramo 510 - Responsabilidade civil
	   	ate=17;
		modalidade = new CriaArray(ate);
		modalidade[0]= "";
		modalidade[1]= "18 - RC Anúncios e/ou Antenas";
		modalidade[2]= "19 - RC Armazéns Gerais e Similares";
		modalidade[3]= "20 - RC Condomínios Comerciais (''Shopping Centers'')";
		modalidade[4]= "21 - RC Condomínios, Proprietários e Locatários de Imóveis";
		modalidade[5]= "22 - RC Familiar";
		modalidade[6]= "23 - RC Guarda de Embarcações de Terceiros";		
		modalidade[7]= "24 - RC Guarda de Veículos de Terceiros";
		modalidade[8]= "25 - RC Obras Civis e/ou Prestação de Serviços de Montagem, Instalação e/ou Assistência Técnica e manutenção, de Máquinas, Equipamentos e Aparelhos em Geral";
		modalidade[9]= "26 - RC Operações - Estabelecimentos Comerciais e/ou Industriais";
		modalidade[10]= "27 - RC Participação em Exposições ou em Feiras de Amostras";
		modalidade[11]= "28 - RC Prestação de Serviços de Movimentação de Cargas";
		modalidade[12]= "29 - RC Prestação de Serviços em Locais de Terceiros, de Limpeza e Manutenção Geral de Imóveis";
		modalidade[13]= "30 - RC Promoção de Eventos Artísticos, Esportivos e Similares";
		modalidade[14]= "31 - RC Promoção de Exposições e Feiras de Amostras";
		modalidade[15]= "32 - RC Transporte de Passageiros em Embarcações";
		modalidade[16]= "";
		modalidade[17]= "";		
	}else if (ramo=="780"){	
	    //Ramo 780 - R.C. Profissional
	   	ate=17;
		modalidade = new CriaArray(ate);
		modalidade[0]= "";
		modalidade[1]= "01 - Profissional Contabilista";
		//JIRA-117284
		//modalidade[2]= "02 - Profissional Corretores de Seguros";
		modalidade[2]= "03 - Profissional Engenheiros e Arquitetos";
		modalidade[3]= "04 - Profissional Notórios e Registradores";
		modalidade[4]= "05 - Profissional de Agência de Turismo";
		modalidade[5]= "06 - Miscellaneous";
		modalidade[6]= "";
		modalidade[7]= "";
		modalidade[8]= "";
		modalidade[9]= "";		
		modalidade[10]= "";
		modalidade[11]= "";		
		modalidade[12]= "";
		modalidade[13]= "";		
		modalidade[14]= "";
		modalidade[15]= "";		
		modalidade[16]= "";
		modalidade[17]= "";		
	}
	else if (ramo=="300"){
	   	ate=17;
		modalidade = new CriaArray(17);
		modalidade[0]= "";
		modalidade[1]= "01 - Móveis";
		modalidade[2]= "02 - Estacionários";
		modalidade[3]= "";
		modalidade[4]= "";
		modalidade[5]= "";
		modalidade[6]= "";
		modalidade[7]= "";
		modalidade[8]= "";
		modalidade[9]= "";
		modalidade[10]= "";
		modalidade[11]= "";
		modalidade[12]= "";
		modalidade[13]= "";
		modalidade[14]= "";
		modalidade[15]= "";
		modalidade[16]= "";
		modalidade[17]= "";
	}
	else if (ramo=="620"){
		ate=17;
		modalidade = new CriaArray(17);
		modalidade[0]= "";
		modalidade[1]= "01 - Móveis";
		modalidade[2]= "02 - Estacionários";
		modalidade[3]= "";
		modalidade[4]= "";
		modalidade[5]= "";
		modalidade[6]= "";
		modalidade[7]= "";
		modalidade[8]= "";
		modalidade[9]= "";
		modalidade[10]= "";
		modalidade[11]= "";
		modalidade[12]= "";
		modalidade[13]= "";
		modalidade[14]= "";
		modalidade[15]= "";
		modalidade[16]= "";
		modalidade[17]= "";
	}
    
/*
	else {
		
		document.frm1.elements("codMod").disabled=true;
		alert("Passou aqui false");
   	}
*/  
	for (i; i <= ate ; i++) {		
		document.getElementById("codMod").options.item(i).text = modalidade[i];
	}
	if (ate>0) {
	   	if(r==0){
	   		alert("Para este ramo você deve selecionar uma modalidade");
	   	}
		document.frm1.codMod.selectedIndex = r;
	    document.frm1.codMod.disabled = false;
	}   
	
	// INICIO EXCECOES DE CORRETOR
	var codCorretor = document.frm1.cCorretor.value;
	var codRamo = document.frm1.codRamo.value;
	//alert (codCorretor);
	//alert (codRamo);
		
	$.ajax({
		type : "GET",
		url : "Controller?action=buscaTpEmissaoBloqueadasAjax",
		async: false,
		dataType : "json",
		data : {
			corretor : codCorretor,
			ramo : codRamo
		},
		success : function(json) {
			$.each(json, function(key, data) {
				for (i=0; i< document.getElementById("TipEmissao").length;i++) {
					if (document.getElementById("TipEmissao").options.item(i).value == data ) {
						document.getElementById("TipEmissao").remove(i);
						break;
					}
				}
			})
		}
	});

	// FIM EXCECOES DE CORRETOR
	
}
function CmbModalidadeChanged(intranet){
    var iPrioridadeRamo=0;
    var texto="";
    var iPrioridadeMod=0;
    var textoMod="";
    
    iPrioridadeRamo = document.frm1.codRamo.selectedIndex;
    texto = document.getElementById("codRamo").options.item(iPrioridadeRamo).text;
    var ramo= texto.substr(0,3);
    
    iPrioridadeMod = document.frm1.codMod.selectedIndex;
    texto = document.getElementById("codMod").options.item(iPrioridadeMod).text;
    var mod= texto.substr(0,2);

	if (intranet != "1") {

		if (ramo == 113 && document.frm1.CodCorrVoltaJs.value!="0908624" 
				&& document.frm1.CodCorrVoltaJs.value!="0944587" 
				&& mod == '02') {
			if (document.getElementById("TipEmissao").length == 5) {
				document.getElementById("TipEmissao").remove(3);
				document.getElementById("TipEmissao").remove(2);
				document.getElementById("TipEmissao").remove(1);
			}
		} else {
			if (document.getElementById("TipEmissao").length == 2) {
				document.getElementById("TipEmissao").add(document.createElement("option"));
				document.getElementById("TipEmissao").add(document.createElement("option"));
				document.getElementById("TipEmissao").add(document.createElement("option"));

				for (i = 4; i >= 1; i--) {
					if (i > 3) {
						document.getElementById("TipEmissao").options.item(i).text = document.getElementById("TipEmissao").options.item(i - 3).text;
						document.getElementById("TipEmissao").options.item(i).value = document.getElementById("TipEmissao").options.item(i - 3).text;
					}
					if (i == 1) {
						document.getElementById("TipEmissao").options.item(i).text = "Seguro Novo";
						document.getElementById("TipEmissao").options.item(i).value = "Seguro Novo";
					}
					if (i == 2) {
						document.getElementById("TipEmissao").options.item(i).text = "Renov Congênere";
						document.getElementById("TipEmissao").options.item(i).value = "Renov Congênere";
					}
					if (i == 3) {
						document.getElementById("TipEmissao").options.item(i).text = "Renov Sompo";
						document.getElementById("TipEmissao").options.item(i).value = "Renov Sompo";
					}
				}

			}
		}
	}

}		

function validaNumero(n) {
	if (n.length==0) return false;
	var numeros = '0123456789';
	for (var i=0; i < n.length; i++) {
		if (numeros.indexOf(n.charAt(i)) == -1) {
			return false;
		}
	}
	return true;
}

function limpaVar(){
  
	document.frm1.elements("codRamo").selectedIndex=0;
	document.frm1.elements("codMod").selectedIndex=0;
	document.frm1.elements("TipEmissao").selectedIndex=0;
	return true;

}


