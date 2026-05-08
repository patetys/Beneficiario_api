// consistenciasMSCad.js

	
var processando = false;
function ValidarCamposEnviar(Form) {
	//CotacaoBean.codModalidade != '02' || (CotacaoBean.codRamo != 113 && CotacaoBean.codRamo != 112)
	
	if(document.frm1.flagMod.value != "02" || (document.frm1.flagRamo.value != "113" && document.frm1.flagRamo.value != "112")){
		if(document.frm1.flagCorretorEmpresarial.value == "false" && document.frm1.flagRamo.value == "112" && document.frm1.lmiUnico.checked == false){
			alert("Favor solicitar sua cotação via SYASweb. Em caso de risco restrito, entre em contato com seu executivo de contas.");
			return false;
		}
	}

	if(document.frm1.nomeProp.value==""){
       alert("O campo 'Nome Proponente' é obrigatório");
       document.frm1.nomeProp.focus();
	   return false;
	}
	
	if(document.frm1.cnpjCpf.value==""){
       alert("O campo 'CNPJ/CPF' é obrigatório");
       document.frm1.cnpjCpf.focus();
 	   return false; 

	 } else if (document.frm1.tipoPessoa[0].checked) {

         document.frm1.cnpjCpf.value = AddZeros(document.frm1.cnpjCpf.value,11); 
		if (!valida_CPF(document.frm1.cnpjCpf)) {
			alert("Número de CPF inválido");
	        document.frm1.cnpjCpf.focus();
			return false;	
		}
	} else if (document.frm1.tipoPessoa[1].checked) {
	    document.frm1.cnpjCpf.value = AddZeros(document.frm1.cnpjCpf.value,14); 
		if (!valida_CGC(document.frm1.cnpjCpf)) {
			alert("Número de CNPJ inválido");
	        document.frm1.cnpjCpf.focus();
			return false;
		}
	}	
	
	if (document.frm1.dataVigenciaInicio.value==""){
		alert("Data de início de vigência é obrigatório");
	    document.frm1.dataVigenciaInicio.focus();
   	    return false;
	}

	if (document.frm1.dataVigenciaFim.value==""){
		alert("Data de término de vigência é obrigatório");
	    document.frm1.dataVigenciaFim.focus();
   	    return false;
	}
	
	if (dtMaior(document.frm1.dataVigenciaInicio.value,document.frm1.dataVigenciaFim.value )==false)
	{
		alert("Data de início de vigência deve ser menor que a data de término da vigência.");
	    document.frm1.dataVigenciaInicio.focus();
   	    return false;
	}	
	
	// Renovação Yasuda Marítima: o início de vigência poderá ser de 3 dias corridos antes da data atual 
	if (document.frm1.tipEmis.value == '02' && (dtTresDiasMenor(document.frm1.dataVigenciaInicio.value))==false){
		alert("Data de início de vigência deve ser maior ou igual a 3 dias anteriores da data atual.");
	    document.frm1.dataVigenciaInicio.focus();
   	    return false;
	}
	
	// Seguro novo e renovação de congênere: o início de  vigência deverá ser a partir da data atual
	if ((document.frm1.tipEmis.value == '00' 
		|| document.frm1.tipEmis.value == '01') 
			&& (dtMaiorOuIgual(dataAtualDDMMAAAA(),document.frm1.dataVigenciaInicio.value)==false)){
		alert("Data de início de vigência deve ser maior ou igual a data atual.");
	    document.frm1.dataVigenciaInicio.focus();
   	    return false;
	}

	// Endosso não possui validação, mas o restante precisam dessa validacao (data de vigencia deve ser <= 90 dias).
	if(document.frm1.tipEmis.value != '03' && (diasEntreDatas(dataAtualDDMMAAAA(),document.frm1.dataVigenciaInicio.value) > 90)){
		alert("A cotação não poderá ser cadastrada, pois o início de vigência é superior a 90 dias da data atual.");
		document.frm1.dataVigenciaInicio.focus();
	   	return false;
	}

		 
	if (document.frm1.QtdLocRisc.checked==false){
		 	
		 document.frm1.QtdLocRisc.value=1;
		 	
   	     if(document.frm1.Cep.value==""){
   	       alert("O campo 'Cep' é obrigatório");
	       document.frm1.Cep.focus();
   	       return false;
   	     }   
   	     	 	     	       	       		
   	     if(document.frm1.End.value==""){
   	       alert("O campo 'Endereço' é obrigatório");
	       document.frm1.End.focus();
   	       return false;
   	     }      	     
         		 					
   	     if(document.frm1.Numero.value==""){
   	       alert("O campo 'Numero' é obrigatório");
	       document.frm1.Numero.focus();
   	       return false;
   	     }   	       		 						
//   	     if(document.frm1.Complemento.value==""){
//   	       alert("O campo 'Complemento' � obrigat�rio");
//   	       return true;
//   	     }
  	
   	     if(document.frm1.Bairro.value==""){
   	       alert("O campo 'Bairro' é obrigatório");
	       document.frm1.Bairro.focus();
   	       return false;
   	     }
   	     if(document.frm1.Cid.value==""){
   	       alert("O campo 'Cidade' é obrigatório");
	       document.frm1.Cid.focus();
   	       return false;
   	     }
 
   	     if(document.frm1.cobUF.value==""){	
   	       alert("O campo 'UF' é obrigatório");
	       document.frm1.cobUF.focus();
   	       return false;
   	     }

   	     if(document.frm1.VlRisco.value==""){
   	       alert("O campo 'Valor em Risco' é obrigatório");
	       document.frm1.VlRisco.focus();
   	       return false;
   	     }
   	     
   	     if(parseFloat(document.frm1.VlRisco.value.replace(/,/g, '')) <= 0){
   	       alert("O campo 'Valor em Risco' deve ser maior que zero");
  	       document.frm1.VlRisco.focus();
   	       return false;
   	     }
   	     
   	 }else{
   	     
		 document.frm1.QtdLocRisc.value=0;
		if((document.frm1.codRamo.value==112) || (document.frm1.codRamo.value==113) || (document.frm1.codRamo.value==114)){
	   	     if(document.frm1.VlRisco.value==""){
		   	       alert("O campo 'Valor em Risco' é obrigatório. Caso tenha mais de um local de risco, preencher com o maior Valor em Risco.");
			       document.frm1.VlRisco.focus();
		   	       return false;
	   	     }
	   	     
	   	     if(parseFloat(document.frm1.VlRisco.value.replace(/,/g, '')) <= 0){
     	       alert("O campo 'Valor em Risco' deve ser maior que zero");
    	       document.frm1.VlRisco.focus();
     	       return false;
     	     }
		}

	  }

		

         if(document.frm1.EmailContato.value==""){
   	        alert("O campo 'Email de Contato' é obrigatório");
	        document.frm1.EmailContato.focus();
   	       return false;
   	     }     	   
         
         var email = document.frm1.EmailContato.value;
         
         for(var i = 0; i < email.length; i++){
             if(email.charAt(i) == "," || email.charAt(i) == "/" || email.charAt(i) == "\\"){
                 alert("Email inválido!");
                 return false;
             } 
             if(email.charAt(i) == " "){
                 var iProx = i + 1;
                 if(iProx < email.length){
                     var x = iProx;
                     while( x< email.length){
                         if(email.charAt(x) != " "){
                             alert("Email inválido!");
                             return false;
                         } 
                         x++;
                     }
                 }    
             }    
         }
         
         function trim(str) {
             var str = str.replace(/^\s\s*/, ''),
                 ws = /\s/,
                 i = str.length;
             while (ws.test(str.charAt(--i)));
             return str.slice(0, i + 1);
         }
         
         email = trim(email);   	 
         
         finalEmailTres = email.substring(email.length-3,email.length);
         finalEmailQuatro = email.substring(email.length-4,email.length);
         
         var er = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/); 
         if(!er.test(email)){ 
             alert("Email inválido!");
             return false; 
         } else if(finalEmailTres != ".br" && finalEmailQuatro != ".com" && finalEmailQuatro != ".net"){
             alert("Email inválido!");
             return false;
         }    
   	     
   	     if(document.frm1.NomeContato.value==""){
   	       alert("O campo 'Nome de Contato' é obrigatório");
	       document.frm1.NomeContato.focus();
   	       return false;
   	     }   	     
   	     
   	     if(document.frm1.TelefContato.value==""){
   	       alert("O campo 'Telefone de Contato' é obrigatório");
	       document.frm1.TelefContato.focus();
	       return false;
   	     }       
		 

   	  document.frm1.submit();
}

function VoltaMenuCad(Form) {
	 document.frm1.action.value = "menucad";
	 document.frm1.vMenuCad.value = "1";
	 document.frm1.submit();
}

function txtQtdlocRiscChanged(){
	
	if (document.frm1.QtdLocRisc.checked){
		 
		 //alert('Relacionar os Locais de Risco com VR/Ocupa��o/Coberturas/LMG');
		 document.frm1.QtdLocRisc.value=1;	
   	     document.frm1.Cep.value="";
	     document.frm1.Cep.disabled=true;	     
		 document.frm1.End.value="";
		 document.frm1.End.disabled=true;		
		 document.frm1.Numero.value="";
		 document.frm1.Numero.disabled=true;			
		 document.frm1.Complemento.value="";
		 document.frm1.Complemento.disabled=true;		
		 document.frm1.Bairro.value="";
		 document.frm1.Bairro.disabled=true;				
		 document.frm1.Cid.value="";
		 document.frm1.Cid.disabled=true;		
		 document.frm1.cobUF.value="";			 		 
		 document.frm1.cobUF.disabled=true;
 		 if((document.frm1.codRamo.value==112) || (document.frm1.codRamo.value==113) || (document.frm1.codRamo.value==114)){
			//document.frm1.DescVlRisco.value="Maior valor em Risco R$:";
		 	alert("Favor preencher o campo indicado com o Maior Valor em Risco e relacionar, em arquivo anexo, os locais de risco com VR/Ocupação/Coberturas/LMG. (utilizar a rotina de anexação de arquivo no final desta tela).");
			document.frm1.VlRisco.style.background='E1EFFF';
			document.frm1.VlRisco.focus();
		 }else{
			//document.frm1.DescVlRisco.value="Valor em Risco R$:";
			document.frm1.VlRisco.value="";
		 	alert("Favor relacionar, em arquivo anexo, os locais de risco com VR/Ocupação/Coberturas/LMG. (utilizar a rotina de anexação de arquivo no final desta tela).");
			document.frm1.VlRisco.disabled=true;
		 }
	 }else{
  		 document.frm1.QtdLocRisc.value=0;
   	     document.frm1.Cep.value="";
	     document.frm1.Cep.disabled=false;	     
  		 document.frm1.End.value="";
		 document.frm1.End.disabled=false;	
		 document.frm1.Numero.value="";
		 document.frm1.Numero.disabled=false;			
		 document.frm1.Complemento.value="";
		 document.frm1.Complemento.disabled=false;
		 document.frm1.Bairro.value="";
		 document.frm1.Bairro.disabled=false;				
		 document.frm1.Cid.value="";
		 document.frm1.Cid.disabled=false;		
		 document.frm1.cobUF.value="";
		 document.frm1.cobUF.disabled=false;	 		 						
		 //document.frm1.DescVlRisco.value="Valor em Risco R$:";
		 document.frm1.VlRisco.style.background='FFFFFF';
		 document.frm1.VlRisco.value="";
		 document.frm1.VlRisco.disabled=false;	 
		 document.frm1.Cep.focus();     
	 }        
	
		return(true);
}

function txtQtdlocRiscChangedOpen(){
	
	if (document.frm1.QtdLocRisc.checked){
		 
		 document.frm1.QtdLocRisc.value=1;	

   	     document.frm1.Cep.value="";
	     document.frm1.Cep.disabled=true;	     
		 document.frm1.End.value="";
		 document.frm1.End.disabled=true;		
		 document.frm1.Numero.value="";
		 document.frm1.Numero.disabled=true;			
		 document.frm1.Complemento.value="";
		 document.frm1.Complemento.disabled=true;		
		 document.frm1.Bairro.value="";
		 document.frm1.Bairro.disabled=true;				
		 document.frm1.Cid.value="";
		 document.frm1.Cid.disabled=true;		
		 document.frm1.cobUF.value="";			 		 
		 document.frm1.cobUF.disabled=true;
 	 	 //document.frm1.DescVlRisco.value="Valor em Risco R$:";			 
		 if((document.frm1.codRamo.value==112) || (document.frm1.codRamo.value==113) || (document.frm1.codRamo.value==114)){
			 if(document.frm1.QtdLocRisc.value==1){
			 	//document.frm1.DescVlRisco.value="Maior valor em Risco R$:";
			 }
		 }else{
		 	document.frm1.VlRisco.value="";		 
		 	document.frm1.VlRisco.disabled=true;	
		 }
	 }
	
		return(true);
}

function limpaCampos(){

//	alert('n�o faz nada por enquanto');
		 document.frm1.nomeProp.value="";
		 document.frm1.tipoPessoa.value=0;
		 document.frm1.tipoPessoa[0].checked=true;
		 document.frm1.cnpjCpf.value="";
		 document.frm1.QtdLocRisc.value=0;	
   	     document.frm1.QtdLocRisc.checked=false;
   	     document.frm1.Cep.value="";
		 document.frm1.End.value="";
		 document.frm1.Numero.value="";
		 document.frm1.Complemento.value="";
		 document.frm1.Bairro.value="";
		 document.frm1.Cid.value="";
		 document.frm1.cobUF.value="";			 		 
		 document.frm1.VlRisco.value="";
		 document.frm1.EmailContato.value="";
		 document.frm1.NomeContato.value="";
		 document.frm1.TelefContato.value="";
		 document.frm1.observ.value="";
	
    return false;
	
}


function consultaCEP() {
	var cep = document.frm1.Cep.value;
		
	if (cep.length==8 && seNumero(cep) && (document.frm1.QtdLocRisc.checked==false) ){
		document.frmCEP.numCEP.value=cep;
		document.frm1.msgCEP.value='Por favor, aguarde...';
		document.frmCEP.submit();
	}
	else {
		//if (cep.length==8 && seNumero(cep) && (document.frm1.QtdLocRisc.checked==false) ){	
			//document.frm1.cobTipoLogradouroIn.value='';	
			document.frm1.End.value='';	
			document.frm1.Complemento.value='';	
			document.frm1.Bairro.value='';	
			document.frm1.Cid.value='';	
			document.frm1.cobUF.value='';	
			alert('CEP inválido');
			document.frm1.Cep.focus();
		//}
	}
}

function soNumero(n){
	validaCampoNumerico(n);
}
function soNumeroMoeda(n){
	validaCampoNumericoMoeda(n);
}

function dtMaior(d1,d2)
{
	if ((d1.substring(6,10)+d1.substring(3,5)+d1.substring(0,2)) >= (d2.substring(6,10)+d2.substring(3,5)+d2.substring(0,2))){
		return false;
	}
		else{return true;}
}

function dtMaiorOuIgual(d1,d2)
{
	if ((d1.substring(6,10)+d1.substring(3,5)+d1.substring(0,2)) > (d2.substring(6,10)+d2.substring(3,5)+d2.substring(0,2))){
		return false;
	}
		else{return true;}
}

function dtTresDiasMenor(d1){
	
	var tresDiasMillis = (24*60*60*1000)*3;
	var limiteDias = new Date();
	limiteDias.setHours(0);
	limiteDias.setMinutes(0);
	limiteDias.setSeconds(0);
	limiteDias.setMilliseconds(0);
	limiteDias.setTime(limiteDias.getTime() - tresDiasMillis);
	
	var dt1 = new Date(d1.substring(6),d1.substring(3,5)-1,d1.substring(0,2));
	
	if (dt1.getTime() < limiteDias.getTime()){
		return false;
	}
	else{
		return true;
	}
}


function consultaContasInternacionais()
{
	if($("#cnpjCpf").val()!=""){
	
		$("#msgDuplic").html('Por favor, aguarde...');
		
		if (document.frm1.tipoPessoa[0].checked) {
	         document.frm1.cnpjCpf.value = AddZeros(document.frm1.cnpjCpf.value,11); 
			if (!valida_CPF(document.frm1.cnpjCpf)) {
				alert("Número de CPF inválido");
				$("#cnpjCpf").val('');
				$("#msgDuplic").html('');
				$("#cnpjCpf").focus();
				return true;	
			}
	
		} else if (document.frm1.tipoPessoa[1].checked) {
		    document.frm1.cnpjCpf.value = AddZeros(document.frm1.cnpjCpf.value,14); 
			if (!valida_CGC(document.frm1.cnpjCpf)) {
				alert("Número de CNPJ inválido");
				$("#cnpjCpf").val('');
				$("#msgDuplic").html('');
				$("#cnpjCpf").focus();
				return true;
			}	
		}	
		var tipPessoa = 1;
		if (document.frm1.tipoPessoa[0].checked) {
			tipPessoa = 0;
		}
				
		$("#msgDuplic").html('Por favor, aguarde...');
		
		$.ajax({
			type: "GET",
			url: "Controller?action=ContasInternacionais&cnpjCpf=" + $("#cnpjCpf").val()+"&codCorr=" + $("#codCorr").val()+"&tipPessoa=" + tipPessoa + "&codUnidadeCorp=" + $("#codUnidadeCorp").val(),
			dataType: "xml",
			success: function(xml) {
				$("#msgDuplic").html('');
				if (xml.children[0].innerHTML == "true") {
					//Libera acesso
					alert("O CNPJ " + $("#cnpjCpf").val() + " está atrelado à uma conta FM Global.\nFavor encaminhar o e-mail da cotação para a Unidade de Negócios Internacionais: negociosinternacionais@sompo.com.br.\n\nObrigado.");
					$("#cnpjCpf").val('');
					$("#cnpjCpf").focus();
				}	
			}
		});
				
	}
}



