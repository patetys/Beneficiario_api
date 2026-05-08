
function valida_CPF(obj) {
    let s = String(obj.valor || '').replace(/\D/g, '');

    while (s.length > 11 && s.startsWith('0')) {
        s = s.substring(1);
    }

	if (s.length > 11) {
		return false;
	} else if(s.length < 11) {
		s = AddZeros(s, 11);
	}

	var i;
	var c = s.substr(0, 9);
	var dv = s.substr(9, 2);
	var d1 = 0;
	for (i = 0; i < 9; i++) {
		d1 += c.charAt(i) * (10 - i);
	}
	if (d1 == 0) {
		return false;
	}
	d1 = 11 - (d1 % 11);
	if (d1 > 9)
		d1 = 0;
	if (dv.charAt(0) != d1) {
		return false;
	}
	d1 *= 2;
	for (i = 0; i < 9; i++) {
		d1 += c.charAt(i) * (11 - i);
	}
	d1 = 11 - (d1 % 11);
	if (d1 > 9)
		d1 = 0;
	if (dv.charAt(1) != d1) {
		return false;
	}
	return true;
}

function valida_CGC(obj) {
    let cnpjString = String(obj.value || '');
    if (!cnpjString) {
        return false;
    }

    if (cnpjString.length<14){
       return false;
    }else{
       cnpjString = AddZeros(cnpjString,14);
    }

    // --- Constantes e Funções Auxiliares ---
    const TAMANHO_CNPJ = 14;
    const TAMANHO_CNPJ_SEM_DV = 12;
    const CNPJ_PESOS = [6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2];

    const removeCaracteresFormatacao = (cnpj) => {
        return cnpj.replace(/[\.\-\/]/g, '');
    };

    const isCnpjFormacaoValidaComDV = (cnpj) => {
        const regexFormato = /^[A-Z\d]{12}\d{2}$/i;
        const regexZeros = /^0+$/;
        return regexFormato.test(cnpj) && !regexZeros.test(cnpj);
    };

    const calculaDigitoCnpj = (baseCnpj) => {
        let soma = 0;
        const VALOR_BASE = '0'.charCodeAt(0);

        for (let indice = baseCnpj.length - 1; indice >= 0; indice--) {
            const valorCaracter = baseCnpj.charCodeAt(indice) - VALOR_BASE;
            soma += valorCaracter * CNPJ_PESOS[CNPJ_PESOS.length - baseCnpj.length + indice];
        }
        const resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    };

    const calculaDVCnpj = (baseCnpj) => {
        const dv1 = calculaDigitoCnpj(baseCnpj);
        const dv2 = calculaDigitoCnpj(baseCnpj + dv1);
        return `${dv1}${dv2}`;
    };

    // --- Lógica Principal ---
    const cnpj = removeCaracteresFormatacao(cnpjString);

    if (cnpj.length !== TAMANHO_CNPJ) {
        return false;
    }

    if (!isCnpjFormacaoValidaComDV(cnpj)) {
        return false;
    }

    const baseCnpj = cnpj.substring(0, TAMANHO_CNPJ_SEM_DV);
    const dvInformado = cnpj.substring(TAMANHO_CNPJ_SEM_DV);
    const dvCalculado = calculaDVCnpj(baseCnpj);

    return dvCalculado === dvInformado;
}

function seNumero(string) {
    var numero=string;
    if (numero.length == 0) {
        return true
    }
    var valido = '0123456789';
    for (var i=0; i < numero.length; i++) {
            if (valido.indexOf(numero.charAt(i)) == -1) {
            return false
            }
    }
    return true
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

function formatarInputAlfanumerico(elemento) {
    var cursorPos = elemento.selectionStart;

    var valorLimpo = elemento.value.replace(/[^a-z0-9]/ig, '');

    var valorFinal = valorLimpo.toUpperCase();

    if (elemento.value !== valorFinal) {
        elemento.value = valorFinal;
        elemento.setSelectionRange(cursorPos, cursorPos);
    }
}

function validaNumeros(txtBoxId,qtde) {
	for (var i=0; i<qtde; i++) {
		var txtBox = document.getElementById(txtBoxId+i);
		if (txtBox.value=="") continue;
		if (validaNumero(txtBox.value)) continue;
		return false;
	}
	return true;
}

function AddZeros(objCampo,intSize)	{
	var slinha = new String();
	var sretlinha = new String();
	slinha = objCampo.toString();
	var tam = slinha.length;

	if ((tam < intSize) && (tam > 0))
	{
		for(i=0;i < (intSize - tam);i++)
		{
			sretlinha += "0";
		}
	}
	return(sretlinha + slinha);	
}
	
function validaMoeda(n) {
	if (n.length==0) return false;
	var numeros = '0123456789,.';
	for (var i=0; i < n.length; i++) {
		if (numeros.indexOf(n.charAt(i)) == -1) {
			return false;
		}
	}
	if (n.lastIndexOf(".")==(n.length-3)) return false;
	if (n.lastIndexOf(".")==(n.length-1)) return false;
	if (n.lastIndexOf(",")==(n.length-1)) return false;

	return true;
}

function validaMoedas(txtBoxId,qtde) {
	for (var i=0; i<qtde; i++) {
		var txtBox = document.getElementById(txtBoxId+i);
		if (txtBox.value=="") continue;
		if (validaMoeda(txtBox.value)) continue;
		return false;
	}
	return true;
}

function validaPercentuais(txtBoxId,qtde) {
	for (var i=0; i<qtde; i++) {
		var txtBox = document.getElementById(txtBoxId+i);
		if (txtBox.value=="") continue;
		if (validaPercentual(txtBox.value)) continue;
		return false;
	}
	return true;
}

function validaPercentual(n) {
	if (n.length==0) return false;
	var numeros = '0123456789,';
	for (var i=0; i < n.length; i++) {
		if (numeros.indexOf(n.charAt(i)) == -1) {
			return false;
		}
	}
	if (n<0 || n>100) return false;
	var idx=n.lastIndexOf(",");
	var str=n.substring(idx+1);
	if (idx==-1) return true;
	if ((idx==1 || idx==2) && (str.length<=4))
		return true;
	return false;
}

function  validaData(DateValue)
{
	var checkstr = "0123456789";
	var DateTemp = "";
	var seperator = ".";
	var day;
	var month;
	var year;
	var leap = 0;
	var err = 0;
	var i;
   err = 0;
   /* Delete all chars except 0..9 */
   for (i = 0; i < DateValue.length; i++) {
	  if (checkstr.indexOf(DateValue.substr(i,1)) >= 0) {
	     DateTemp = DateTemp + DateValue.substr(i,1);
	  }
	  else
	  {
	    if (DateValue.substr(i,1)!="/") return(false);
	  }
   }
   DateValue = DateTemp;
   /* Always change date to 8 digits - string*/
   if (DateValue.length != 8)  return(false);

   /* year is wrong if year = 0000 */
   year = DateValue.substr(4,4);
   if (year == 0) {
      return(false);
   }
   /* Validation of month*/
   month = DateValue.substr(2,2);
   if ((month < 1) || (month > 12)) {
      return(false);
   }
   /* Validation of day*/
   day = DateValue.substr(0,2);
   if (day < 1) {
     return(false);
   }
   /* Validation leap-year / february / day */
   if ((year % 4 == 0) || (year % 100 == 0) || (year % 400 == 0)) {
      leap = 1;
   }
   if ((month == 2) && (leap == 1) && (day > 29)) {
      return(false);
   }
   if ((month == 2) && (leap != 1) && (day > 28)) {
      return(false);
   }
   /* Validation of other months */
   if ((day > 31) && ((month == "01") || (month == "03") || (month == "05") || (month == "07") || (month == "08") || (month == "10") || (month == "12"))) {
      return(false);
   }
   if ((day > 30) && ((month == "04") || (month == "06") || (month == "09") || (month == "11"))) {
      return(false);
   }
   /* if 00 ist entered, no error, deleting the entry */
   if ((day == 0) && (month == 0) && (year == 00)) {
      return(false);
   }
   /* if no error, write the completed date to Input-Field (e.g. 13.12.2001) */
   return(true);
}

function gerRiscoChanged(idxHabTxt) {
	if (document.frm1.gerRisco[0].checked==true) {
		document.frm1.codGerRisco.disabled=false;
		if (document.frm1.codGerRisco.selectedIndex==idxHabTxt)
			document.frm1.descGerRisco.readOnly="";
	} else {
		document.frm1.codGerRisco.disabled=true;
		document.frm1.descGerRisco.readOnly="readonly";
	}
}

function codGerRiscoChanged(idxHabTxt) {
	var val = document.getElementById('codGerRisco').selectedIndex;
	var txtBox = document.getElementById('descGerRisco');
	
//Erialdo

if (val==idxHabTxt){
        txtBox.value="";
	    txtBox.disabled=false;
		txtBox.readOnly="";
	} else {
		txtBox.value="Desabilitado";
	    txtBox.disabled=true;
		txtBox.readOnly="readonly";
	}	
		
/*	
	if (val==idxHabTxt)
		txtBox.readOnly="";
	else
		txtBox.readOnly="readonly";
*/
		
		
}

function checkOneInListTxt(txtBoxId,qtde) {
	for (var i=0; i<qtde; i++) {
		var txtBox = document.getElementById(txtBoxId+i);
		if (txtBox.value!="") {
			return true;
		}
	}
	return false;
}

function checkOneInListCmb(txtBoxId,qtde) {
	for (var i=0; i<qtde; i++) {
		var txtBox = document.getElementById(txtBoxId+i);
		if (txtBox.selectedIndex!=0) {
			return true;
		}
	}
	return false;
}


function comboChanged(cmbId, txtId, habTxt) {
	var cmbBox = document.getElementById(cmbId);
	var txtBox = document.getElementById(txtId);

//Erialdo
	if (cmbBox.selectedIndex==habTxt){
	    txtBox.value="";
        txtBox.disabled=false;
		txtBox.readOnly="";
	 } else {
        txtBox.value="Desabilitado";
        txtBox.disabled=true;
		txtBox.readOnly="readonly";
	}
/*	
	if (cmbBox.selectedIndex==habTxt)
		txtBox.readOnly="";
	else
		txtBox.readOnly="readonly";
*/
		
}

function comboChanged(cmbId, txtId, habTxt, habTxt2) {
	var cmbBox = document.getElementById(cmbId);
	var txtBox = document.getElementById(txtId);	
	if (cmbBox.selectedIndex==habTxt ||
		cmbBox.selectedIndex==habTxt2) {
		txtBox.value="";
        txtBox.disabled=false;
		txtBox.readOnly="";
	} else {
		txtBox.value="Desabilitado";
        txtBox.disabled=true;
		txtBox.readOnly="readonly";
	}	
}
function comboChangedII(cmbId, txtId,habTxt0, habTxt, habTxt2) {
	var cmbBox = document.getElementById(cmbId);
	var txtBox = document.getElementById(txtId);

	if (cmbBox.selectedIndex==habTxt ||
		cmbBox.selectedIndex==habTxt2){
		document.frm1.codMeioTranspN.disabled=false;
	  }else{
	    document.frm1.codMeioTranspN.value=0;
		document.frm1.codMeioTranspN.disabled=true;		
	}
//Erialdo 
	 if (cmbBox.selectedIndex==habTxt0){
	      txtBox.value="";
	      txtBox.disabled=false;
	 	  txtBox.readOnly="";
	  }else {
	     txtBox.value="Desabilitado";
	     txtBox.disabled=true;
		 txtBox.readOnly="readonly";				
     }	

	  	
/*
	 if (cmbBox.selectedIndex==habTxt0)
	      txtBox.disabled=false;
	 	 txtBox.readOnly="";
	else
	     txtBox.disabled=true;
		 txtBox.readOnly="readonly";				
		
*/			  	
		
		
		
}

function simNaoComboChanged(cmbId, txtId) {
	var val = document.getElementById(cmbId).value;
	var txtBox = document.getElementById(txtId);
	if (val=='S'){
		txtBox.value="";
	    txtBox.disabled=false;
		txtBox.readOnly="";
	}else {
		txtBox.value="Desabilitado";
	    txtBox.disabled=true;
		txtBox.readOnly="readonly";
	}	
}

function limpaTxtLista(txtId, size) {
	for (var i=0; i<size; i++) {
		var txt = document.getElementById(txtId+i);
		txt.value="";
	}
}

function limpaCmbLista(cmbId, size) {
	for (var i=0; i<size; i++) {
		var cmb = document.getElementById(cmbId+i);
		cmb.selectedIndex=0;
	}
}

function setAction(str) {
	document.frm1.action.value=str;
	return;
}

function setMoeda(subramo,subramoExport) {
//	if (subramo==subramoExport) {
//		document.frm1.codMoeda.selectedIndex=2;
//		document.frm1.codMoeda.disabled=true;
//	}
	return false;
}
function downloadFile(cotacao,nomefile) {	
    window.open('about:blank','Documento','fullscreen=no,border=yes,toolbar=no,location=no,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes');
	document.frm2.cotacao.value=cotacao;
	document.frm2.dirArqOri.value=nomefile;
	document.frm2.action.value="downloadFile";
	document.frm2.submit();
}
var process=false;
function checkProcessando(){
	if (process) {
		alert("Aguarde, sua solicitação está sendo processada!");
		return false;
	}
	process=true;
	return true;
}


//RICARDO
//Verifica os Campos n�o preenchidos

function Validanaopreenchido(Form){

	for(var i=0;i<Form.length;i++){
		if(Form[i].disabled!=true){
			if(Form[i].type=="text" || Form[i].type=="textarea"){
				if ( Form[i].value.length == 0 || Form[i].value == '.' || Form[i].value == ' ') {
					alert("O campo que será indicado é de preenchimento obrigatório!!");
					Form[i].focus();
					Form[i].select();
					return false;
				}
			}
		}
	}
	return true;
}

//RICARDO
//Cria vetor
function CriaArray (n) { 
	this.length = n; 
	for (var i = 1 ; i <= n ; i++) {
		 this[i] = "" ;
	} 
} 

//RICARDO
//Verifica se os campos s�o numericos.
function numCampos(form,campos){
	for(var i=0;i<form.length;i++){
		if(form[i].disabled!=true){
			if(form[i].type=="text"){
				for(var j=0;j<campos.length;j++){
					if(campos[j]==form[i].name){
						if(!validaNumero(form[i].value)){
							alert("O campo indicado deve ser numérico!!! - Ex:(99)");
							form[i].focus();
							return(false);
						}
					}
				}
			}
		}
	}
	return true;
}

//RICARDO
//Verifica se os campos s�o numericos tipo moeda.
function numMoedaCampos(form,campos){
	for(var i=0;i<form.length;i++){
		if(form[i].disabled!=true){
			if(form[i].type=="text"){
				for(var j=0;j<campos.length;j++){
					if(campos[j]==form[i].name){
						if(!validaMoeda(form[i].value)){
							alert("O campo indicado deve ser numérico!!! - Ex:(99.999,99)");
							form[i].focus();
							return(false);
						}
					}
				}
			}
		}
	}
	return true;
}


//RICARDO
//S� deixa digitar numeros no campo.
function validaCampoNumerico(n) {
	var numeros = '0123456789';
	var resp='';
	var form = document.frm1;

	for(var i=0;i<form.length;i++){
		if(form[i].type=="text"){
			if(form[i].name==n){
				valor = form[i].value;
				for (var j=0; j < valor.length; j++) {
					if (numeros.indexOf(valor.charAt(j)) != -1) {
						resp += valor.charAt(j);
					}else{
						alert("Este campo só aceita números!!! - Ex:(999)");
						form[i].value=resp;
						form[i].focus();
						return false;
					}
				}
				form[i].value=resp;
				return true;
			}
		}
	}
}

//RICARDO
//S� deixa digitar numeros/moeda no campo .
function validaCampoNumericoMoeda(n) {
	var numeros = '0123456789.,';
	var resp='';
	var form = document.frm1;

	for(var i=0;i<form.length;i++){
		if(form[i].type=="text"){
			if(form[i].name==n){
				valor = form[i].value;
				for (var j=0; j < valor.length; j++) {
					if (numeros.indexOf(valor.charAt(j)) != -1) {
						resp += valor.charAt(j);
					}else{
						alert("Este campo só aceita números!!! - Ex:(99.999,99)");
						form[i].value=resp;
						form[i].focus();
						return false;
					}
				}
				form[i].value=resp;
				return true;
			}
		}
	}
}

//Ricardo

function voltaPrincipal(v){
	v.action.value="voltaCadPrincipal";
	v.submit();
}

function voltaEspecifico(v){
	v.action.value="voltaCadEspecifico";
	v.submit();
}


//Ricardo
//Faz a habilita��o e desabilita��o dos campos
function camposHabilita(x,n,m){
	form = x;
	habilita = "true";
	
	for(var i=0;i<form.length;i++){
		if(form[i].type=="checkbox"){
			if(n==form[i].name){
				if(form[i].checked){
					form[i].value = "S";				
					habilita = "false";
				}				
			}
		}	
	}

	for(var i=0;i<form.length;i++){
		if(form[i].type=="text"){
			if(m==form[i].name){
				if(habilita=="false"){
					form[i].value = "";
					form[i].disabled=false;
				}else{
					form[i].value = "N";
					form[i].value = "";
					form[i].disabled=true;			
				}
			}
		}
	}
}

//Ricardo
//Controle de tamanho do campo
//onkeyup="javascript:maxCaracteres(document.frm1.DscEquip,495)"
function maxCaracteres(campo,tamanho){
	if(campo.value.length>tamanho){
		campo.value = campo.value.substring(0, tamanho); 
		campo.focus();
	}
}

function dataAtualDDMMAAAA(){
	var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //Janeiro é 0!

    var yyyy = today.getFullYear();
    if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 
    today = dd+'/'+mm+'/'+yyyy;
    return today;
}

function diasEntreDatas(di,df) {
	
	
	// Declara��es de vari�veis
	var mes, dataInicio, arrDataInicio, novaDataInicio, dataFim, arrDataFim, novaDataFim, diasEntreDatas;
	// Lista dos meses em ingl�s
	mes = [];
	mes[0] = "January";
	mes[1] = "February";
	mes[2] = "March";
	mes[3] = "April";
	mes[4] = "May";
	mes[5] = "June";
	mes[6] = "July";
	mes[7] = "August";
	mes[8] = "September";
	mes[9] = "October";
	mes[10] = "November";
	mes[11] = "December";
	
	
	// Pega a data informada pelo usu�rio
	dataInicio = di;
	// Separa a data informada pelo usu�rio atrav�s da barra /
	arrDataInicio = dataInicio.split('/');
	// Formata a data para o seguinte formato: November 22 2006
	novaDataInicio = mes[(arrDataInicio[1] - 1)] + ' ' + arrDataInicio[0] + ' ' + arrDataInicio[2];
	// Saberemos o total de dias entre: a data informada pelo usu�rio e a data atual
	
	dataFim = df;
	// Separa a data informada pelo usu�rio atrav�s da barra /
	arrDataFim = dataFim.split('/');
	// Formata a data para o seguinte formato: November 22 2006
	novaDataFim = mes[(arrDataFim[1] - 1)] + ' ' + arrDataFim[0] + ' ' + arrDataFim[2];
	// Saberemos o total de dias entre: a data informada pelo usu�rio e a data atual
	
	return dateDif.dateDiff(novaDataInicio, novaDataFim);

}

var dateDif = {
		// Fonte: http://www.bigbold.com/snippets/posts/show/2501
		dateDiff: function(strDate1,strDate2){
		return (((Date.parse(strDate2))-(Date.parse(strDate1)))/(24*60*60*1000)).toFixed(0);
		}
}


function dtMaior(d1, d2) {
	if ((d1.substring(6, 10) + d1.substring(3, 5) + d1.substring(0, 2)) >= (d2.substring(6, 10)	+ d2.substring(3, 5) + d2.substring(0, 2))) {
		return false;
	} else {
		return true;
	}
}

function isDiferencaAnosMaior(dataInicial,dataFinal, anos){
	
	var inicial = new Date();
	var mesInicial = parseInt(dataInicial.substring(3,5)) - 1;

	inicial.setDate(dataInicial.substring(0,2));
	inicial.setMonth(mesInicial);
	inicial.setFullYear(dataInicial.substring(6,10));
	inicial.setHours(0);
	inicial.setMinutes(0);
	inicial.setSeconds(0);
	inicial.setMilliseconds(0);
	
	
	var dFinal = new Date();
	
	var mesTerm = parseInt(dataFinal.substring(3,5)) - 1;
	
	dFinal.setDate(dataFinal.substring(0,2));
	dFinal.setMonth(mesTerm);
	dFinal.setFullYear(dataFinal.substring(6,10));
	dFinal.setHours(0);
	dFinal.setMinutes(0);
	dFinal.setSeconds(0);
	dFinal.setMilliseconds(0);
	

	inicial.setFullYear(inicial.getFullYear() + anos);

	if(dFinal.getTime() > inicial.getTime()){
		return true;
	}
	
	return false;
	
}

function idIntervalDate(dataInicial,dataFinal, minMonth, maxMonth){
	
	var inicial = new Date();
	var mesInicial = parseInt(dataInicial.substring(3,5)) - 1;

	inicial.setDate(dataInicial.substring(0,2));
	inicial.setMonth(mesInicial);
	inicial.setFullYear(dataInicial.substring(6,10));
	inicial.setHours(0);
	inicial.setMinutes(0);
	inicial.setSeconds(0);
	inicial.setMilliseconds(0);
	
	var dFinal = new Date();
	var mesTerm = parseInt(dataFinal.substring(3,5)) - 1;
	
	dFinal.setDate(dataFinal.substring(0,2));
	dFinal.setMonth(mesTerm);
	dFinal.setFullYear(dataFinal.substring(6,10));
	dFinal.setHours(0);
	dFinal.setMinutes(0);
	dFinal.setSeconds(0);
	dFinal.setMilliseconds(0);
	
	var difYear = dFinal.getTime() - inicial.getTime(); 
	
	if(difYear >= oneYear && difYear <= fiveYear){
		return true;
	}
	
	return false;
	
}

function isIntervalYears(dataInicial,dataFinal, min, max){
	
	var inicial = new Date();
	var mesInicial = parseInt(dataInicial.substring(3,5)) - 1;

	inicial.setDate(dataInicial.substring(0,2));
	inicial.setMonth(mesInicial);
	inicial.setFullYear(dataInicial.substring(6,10));
	inicial.setHours(0);
	inicial.setMinutes(0);
	inicial.setSeconds(0);
	inicial.setMilliseconds(0);
	
	var dFinal = new Date();
	var mesTerm = parseInt(dataFinal.substring(3,5)) - 1;
	
	dFinal.setDate(dataFinal.substring(0,2));
	dFinal.setMonth(mesTerm);
	dFinal.setFullYear(dataFinal.substring(6,10));
	dFinal.setHours(0);
	dFinal.setMinutes(0);
	dFinal.setSeconds(0);
	dFinal.setMilliseconds(0);
	
	var difYear = dFinal.getTime() - inicial.getTime(); 
	
	if(difYear >= min && difYear <= max){
		return true;
	}
	
	return false;
}




