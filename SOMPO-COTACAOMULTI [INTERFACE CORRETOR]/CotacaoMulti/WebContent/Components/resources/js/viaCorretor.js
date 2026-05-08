var erroTitulo 				 = "Alguns campos são obrigatórios!";
var erroMsgCorretor 		 = "Selecione um corretor ou todos os corretores.";
var erroMsgTipoRamo			 = "Selecione um ramo.";
var erroMsgPeriodoInicial 	 = "Selecione o periodo inicial.";
var erroMsgPeriodoFinal 	 = "Selecione o periodo final.";
var erroMsgSegurado 		 = "O nome do segurado deve ter no minimo 5 caracteres.";
var erroMsgCpfCnpj 			 = "Digite o CPF ou CNPJ corretamente.";
var erroMsgApolice 			 = "Digite a apólice.";
var erroMsgEndosso 			 = "Digite o endosso.";
var erroMsgItem				 = "Digite o item.";
var erroMsgTipoEstado        = "Campo 'estado' obrigatório";
var erroMsgTipoCidade        = "Campo 'cidade' obrigatório";
var erroMsgTipoEspecialidade = "Campo 'especialidade' obrigatório";
var erroMsgTipoOficina       = "Campo 'Tipo Oficina' obrigatório";
var table 					 = null;	
var dadosTabela 			 = null;
var mensagemResultado 		 = null;
var requisicao 				 = null;
var trOpened 				 = null;
var hide 					 = true;
var varCodigoCorretor 		 = null;
var msgDetalheApolice		 = "";
var dadosRepetidos 			 = "";
var igual 					 = false;
var temDados 				 = false;
var qtdeAnterior 			 = 0;
var loadingAtivo 			 = false;
var quantidadeLinha 		 = 0;

function mostraBuscaAvancada(){
	if(hide){
		$( "#bntMostraBuscaDetalhada" ).removeClass("fa fa-plus");
		$( "#bntMostraBuscaDetalhada" ).addClass("fa fa-minus");
		$( "#camposDetalhados" ).slideDown("slow");
		hide=false;
	}else{
		hide=true;
		$( "#bntMostraBuscaDetalhada" ).removeClass("fa fa-minus");
		$( "#bntMostraBuscaDetalhada" ).addClass("fa fa-plus");
		$( "#camposDetalhados" ).slideUp("slow");
	}
	
	setTimeout(function() {
		if (!temDados) {
			if (hide){
				ajustaFrame(25);
			}else{ 
				if(document.getElementById("listApolices").style.display == 'block'){
					ajustaFrame(20);
				}else{
					ajustaFrame(200);
				}
			}
		} else {
			if (hide){ 
				ajustaFrame(25);
			}else{
				ajustaFrame(45);
			}
		}
	}, 250);
	
	limparCampos();
}

$(document).ready(function() {
	
	//Ajuste de tela
	setTimeout(function() {
		ajusteInicialTela();
	}, 1000);

	/* Esconde tabela resultado */
	$("#listApolices").css("display", "none");
	$(".divBntAction").css("display", "none");
	
	$(".closeVia").click(function (){
		$("#msgError span").html("");
		$("#msgError").css("display","none");
	});

	/* EFETUA A PESQUISA DAS APOLICES */
	$("#bntPesquisar").click(function (){
		
		if(!validaCampos()){
			buscarJsonPesquisa();
		}

	});

	/* MASK */
	$("#periodoInicial").removeAttr("readonly");
	$("#periodoFinal").removeAttr("readonly");
	$("#periodoInicial").focus(function() {$(this).select();});
	$("#periodoFinal").focus(function() {$(this).select();});
	$("#periodoFinal").keypress(function() { $("#msgError").css("display", "none");});
	
	$('#periodoInicial').mask('00/00/0000');
	$('#periodoFinal').mask('00/00/0000');
	$('#numeroApolice').mask('0000000000');
	$('#numeroEndosso').mask('000000');
	$('#numeroItem').mask('000');
	
	$("#periodoInicial").datepicker(
		{
			defaultDate: "+1w",
			changeMonth: true,
			changeYear: false,
			numberOfMonths: 1,
			autoclose: true,
			showAnim: "slideDown",
			dateFormat: 'dd/mm/yy',
			dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
			dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
			dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
			monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
			monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
			nextText: 'Próximo',
			prevText: 'Anterior',
			maxDate: "0D",
			minDate: "-5y",
			onClose: function(selected) {
				if ($(this).val().length < 10) {
					var d = $.datepicker.parseDate('dd/mm/yy', $("#periodoFinal").val());
					d.setDate(d.getDate() - 15);
					$('#periodoInicial').datepicker('setDate', d);
				}
				
				corrigirDataFinal(selected);
			},
			
			onSelect : function (selected){
				corrigirDataFinal(selected);
			}
		}
	).datepicker("setDate", subtrairDate(15));
	
	$("#periodoFinal").datepicker(
		{
		defaultDate: "+1w",
		changeMonth: true,
		changeYear: false,
		numberOfMonths: 1,
		autoclose: true,
		showAnim: "slideDown",
		dateFormat: 'dd/mm/yy',
		dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
		dayNamesMin: ['D','S','T','Q','Q','S','S','D'],
		dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb','Dom'],
		monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun','Jul','Ago','Set','Out','Nov','Dez'],
		maxDate: "0D",
		minDate: "-5y", 
		nextText: 'Próximo',
		prevText: 'Anterior',
		onClose: function(selected) {
			if ($(this).val().length < 10) {
				var d = $.datepicker.parseDate('dd/mm/yy', $("#periodoInicial").val());
				d.setDate(d.getDate() + 15);
				$('#periodoFinal').datepicker('setDate', d);
			}
			
			if ($("#msgError").css("display") != "block") {
				validaDataFinal(selected);
			}
		},
		onSelect : function (selected){
			validaDataFinal(selected);
		}
	}
	).datepicker("setDate", subtrairDate(0));
	
	function subtrairDate(dias){
		return new Date(new Date().getTime() - (dias * 24 * 60 * 60 * 1000));
	}
	
	function corrigirDataFinal(obj) {
		var d = $.datepicker.parseDate('dd/mm/yy', obj);
		d.setDate(d.getDate() + 15);
		$('#periodoFinal').datepicker('setDate', d);
	}
	
	function validaDataFinal(obj) {
		$("#msgError").css("display","none");
		$("#msgError span").html("");

		var dInicial = $.datepicker.parseDate('dd/mm/yy', $("#periodoInicial").val());
		var dFinal = $.datepicker.parseDate('dd/mm/yy', obj);
		var diferenca = Math.abs(dInicial - dFinal); //diferença em milésimos e positivo
		var dia = 1000*60*60*24; // milésimos de segundo correspondente a um dia
		var total = Math.round(diferenca/dia);

		if(dFinal > dInicial){
			if(total > 15){
				dInicial.setDate(dInicial.getDate() + 15);
				$('#periodoFinal').datepicker('setDate', dInicial);
				exibeMsgError("ALERTA", "O período de pesquisa deverá ser no máximo 15 dias.");
			}
		}else if(dFinal < dInicial){
			dInicial.setDate(dInicial.getDate() + 15);
			$('#periodoFinal').datepicker('setDate', dInicial);
			exibeMsgError("ALERTA", 
						"O período final escolhido (" + (dFinal.getDate() < 9 ? "0" : "") + dFinal.getDate() + '/' 
													  + ((dFinal.getMonth() + 1) < 9 ? "0" : "") + (dFinal.getMonth() + 1) + '/' + dFinal.getFullYear() + 
						") não pode ser menor que o período inicial. O período válido é de até 15 dias.");
		}
	}
	
	function validaCampos(){

		var msg 			= "";
		var err				= false;
		var estado 		    = $("#codEstado").val();
		var cidade 	        = $('#codCidade').val();
		var especialidade	= $('#codEspec').val();
		var bairro	        = $('#codBairro').val();
		var oficina	        = $('#codTpOficina').val();
		
		if (estado == ""){
			msg = "<br />" + erroMsgTipoEstado;
			err = true;
		}
		
		if (cidade == ""){
			msg = msg + "<br />" + erroMsgTipoCidade;
			err = true;
		}
		
		if (especialidade == ""){
			msg = msg + "<br />" + erroMsgTipoEspecialidade;
			err = true;
		}
		
		if (oficina == ""){
			msg = msg + "<br />" + erroMsgTipoOficina;
			err = true;
		}
		
		if(err == true){
			exibeMsgError("ALERTA", msg);
		}else{
			$("#msgError").css("display","none");
		}
		return err;
	}
} );

function montarTableByRequestAjax(responseTemp){
	
	//Dados guardados para montagem de planilha
	quantidadeLinha 	    = responseTemp.length;

	if(responseTemp.length > 0){
		mensagemResultado = responseTemp[0].mensagem;
	}
	
	try{
		
		if(mensagemResultado == null || mensagemResultado == ""){
			$(".divBntAction").css("display", "block");
			$("#listApolices").css("display", "block");
			$("#listApolices_wrapper").css("display", "block");
			$("#msgError").css("display","none");
			$("#msgError span").html("");
			if(table != null){
				table.clear().draw();
				table.destroy();
			}
			temDados = true;
		}else{
			
			//Define tamanho iframe
			
			temDados = false;
			loadingAtivo = true;
			exibeMsgError("ALERTA", mensagemResultado);
			return false;
		}
		
		table = $('#listApolices').DataTable( {
			"responsive": true,
			"autoFill": {columns: ':not(:first-child)'},
			"fnDrawCallback": function () {$('[data-toggle="tooltip"]').tooltip();},
			"fnFooterCallback": function(nRow, aaData, iStart, iEnd, aiDisplay){
				
				if (trOpened != null) closeRow(trOpened);
				
				var valorAtual = iEnd - iStart;
				
				if (table != null) {
					if (valorAtual == 10) {
						if (qtdeAnterior == 10) {
							ajustaFrame(30);
						} else {
							if (aiDisplay.length > valorAtual && qtdeAnterior > 0){
								ajustaFrame(hide ? 50 : 40);
							}else if (qtdeAnterior == 0){
								ajustaFrame(30);
							}else{
								ajustaFrame(20);
							}
						}
					} else {
						if (qtdeAnterior < valorAtual) {
							ajustaFrame(hide ? 30 : 20);
						} else {
							ajustaFrame(aiDisplay.length > 0 ? valorAtual * 2.5 : 15);
						}
					}
				}
				
				qtdeAnterior = valorAtual;

			},
			"fnClearTable" : true,
			"data": responseTemp,
	        "dataType": "json",
	        "contentType": "application/json; charset=utf-8",
			"type": "POST",
			"bLengthChange": false,
			"paging":   true,
	        "ordering": true,
	        "info":     true,
			"columns": [
			    {   "className":      'detalhes',
	                "orderable":      false,
	                "data":           null,
	                "targets": 1,
	                "defaultContent": ""
	            },
	            { "data": "bairro"},
				{ "data": "nome" },
				{ "data": "endereco" }],
				"fnDrawCallback": function( oSettings ) {
				      addEvents();
				},
				"order": [[ 2, "asc" ]],
				
				"language": {
				"search": "Busca na Lista:", 
				"paginate": {
					"first":      "Primeiro",
					"last":       "Último",
					"next":       "Próximo",
					"previous":   "Anterior"
				},
	            "lengthMenu": "Exibir _MENU_ linhas por página",
	            "sInfoFiltered":"", /* (filtered from _MAX_ total records) */
	            "zeroRecords": "Nenhuma informação encontrada. Por favor, altere os dados e refaça sua busca.",
	            "info": "Mostrando página _PAGE_ de _PAGES_",
	            "infoEmpty": "Nenhuma página disponível",
	            "infoFiltered": "(filtered from _MAX_ total records)"}
		} );
		
		$("#listApolices").css("display", "block");
		
		//Display none para quando a apólice estiver selecionada
		if(requisicao != null && requisicao != ""){
			$("#listApolices_filter").css("display", "none");
		}
		
		$("#gridCol03").css("min-width", "500px");
		$("#gridCol01").css("width", "150px");
		$("#gridCol02").css("width", "150px");
		
		var tipoBrowser = navigator.userAgent;
		if (tipoBrowser.toUpperCase().indexOf("MOZILLA") >= 0){
			$("#gridCol03").css("min-width", "500px");
		}

		if (temDados) {
			if(quantidadeLinha != 0 && quantidadeLinha <= 3){
				ajustaFrame(20);
			}else if(quantidadeLinha != 0 && quantidadeLinha <= 6){
				ajustaFrame(30);
			}else if(quantidadeLinha != 0 && quantidadeLinha > 6){
				ajustaFrame(50);
			}

		} else {
			$("#msgError span").html("");
			$(".divBntAction").css("display", "none");
			exibeMsgError(	"ALERTA",
					"Não foi possível encontrar dados com a pesquisa informada. Por favor, altere os dados e refaça sua busca.");
			
			if(document.getElementById("listApolices").style.display == 'block'){
				ajustaFrame(20);
			}
		}
		
	}finally{
		finalizarLoading();
	}
}

function buscarDetalhesApolice(dadosPesquisa){
//
//	try{
//		dwrDetalheApoliceAjax.buscarDetalhesApolice(dadosPesquisa.dadosApolice.pesquisaEnum, 
//													dadosPesquisa.numeroSusep, 
//													dadosPesquisa.codigoSucursal,
//													dadosPesquisa.codigoCorretor,
//													dadosPesquisa.corretorMaritima,
//													dadosPesquisa.numeroApolice, 
//													dadosPesquisa.numeroEndosso, 
//													dadosPesquisa.codigoColaborador, {
//			callback:function(dadosApolice) {
//				callBackBuscarDetalheApolice(dadosApolice, 
//											dadosPesquisa.codigoCorretor2, 
//											dadosPesquisa.codigoCorretor3);
//			},
//			async:false,
//			timeout:65000,
//			errorHandler:function(msg, exc) {
//				exibeMsgError( "ERRO", msg);	
//				finalizarLoading();
//			}
//			
//		});
//	} catch(e){
//		finalizarLoading();	
//	} finally{
//		finalizarLoading();
//	}
	
	msgDetalheApolice = "<div style='min-width=100%; min-height=100%; border: 0px solid #cccccc !important;box-shadow: 0px 0px 5px #091b31;border-radius: 4px'> " +
			"<iframe src='/ConsultaOficinasRef/detalhe.action?codigo=1002551652'></iframe> " +
			"</div>";
}

function callBackBuscarDetalheApolice(dadosApolice, codigoCorretor2, codigoCorretor3){
	
	var obs = "";
	var temTexto = true;
	if(dadosApolice.coCorretagem){
		obs = "Esta apólice faz parte de uma co-corretagem";
	}else if((codigoCorretor2 != null && codigoCorretor2 != "0") 
		|| (codigoCorretor3 != null && codigoCorretor3 != "0")){
		obs = "Esta apólice faz parte de uma co-corretagem";
	}		    
	
	msgDetalheApolice = "<table id='detalheApoliceAjax' style='min-width=100%; min-height=100%; border: 0px solid #cccccc !important;box-shadow: 0px 0px 5px #091b31;border-radius: 4px'>";
	
	
	if(dadosApolice.numeroPlaca != null && dadosApolice.numeroPlaca != ""){
		msgDetalheApolice += "<col width='150px'>" +
	    "<col width='300px'>" +
	  	"<col width='150px'>" +
	    "<col width='300px'>" +
		"<tr><td style='text-align: initial;' colspan='2'><strong>Inicio de vigência: </strong>" +
			dadosApolice.dataVigenciaInicial +
		"</td><td style='text-align: left;' colspan='2'><strong>Final de vigência: </strong>" +
		dadosApolice.dataVigenciaFinal +
		"</td></tr>";
	}else{
		msgDetalheApolice += "<col width='350px'>" +
	    "<col width='600px'>" +
		"<tr><td style='text-align: initial;' colspan='1'><strong>Inicio de vigência: </strong>" +
			dadosApolice.dataVigenciaInicial +
		"</td><td style='text-align: left;' colspan='1'><strong>Final de vigência: </strong>" +
		dadosApolice.dataVigenciaFinal +
		"</td></tr>";
	}
	if(dadosApolice.dataVigenciaInicial == null || dadosApolice.dataVigenciaInicial == ''){
		temTexto = false;
	}
	
	if(dadosApolice.codigoUnidade == null || dadosApolice.codigoUnidade == ""){
		if(dadosApolice.numeroPlaca != null && dadosApolice.numeroPlaca != ""){
			msgDetalheApolice += '<tr><td style="text-align: initial;" colspan="2"><strong>Unidade: </strong>'+ dadosApolice.nomeUnidade;
		}else{
			msgDetalheApolice += '<tr><td style="text-align: initial;" colspan="1"><strong>Unidade: </strong>'+ dadosApolice.nomeUnidade;
		}
	}else{
		if(dadosApolice.numeroPlaca != null && dadosApolice.numeroPlaca != ""){
			msgDetalheApolice += '<tr><td style="text-align: initial;" colspan="2"><strong>Unidade: </strong>'+ dadosApolice.codigoUnidade + ' - ' + dadosApolice.nomeUnidade;	
		}else{
			msgDetalheApolice += '<tr><td style="text-align: initial;" colspan="1"><strong>Unidade: </strong>'+ dadosApolice.codigoUnidade + ' - ' + dadosApolice.nomeUnidade;
		}
	}

	msgDetalheApolice += "</td><td style='text-align: left;' colspan='2'><strong>Produtor: </strong>" +
		dadosApolice.codigoProdutor + ' - ' + dadosApolice.nomeProdutor +
		"</td></tr>";
	
	if(dadosApolice.numeroPlaca != null && dadosApolice.numeroPlaca != ""){
		msgDetalheApolice += "<tr><td style='text-align: left;' colspan='2'><strong>Marca/Modelo: </strong>" +
		dadosApolice.marca + ' - ' + dadosApolice.modelo + 	
		"</td><td style='text-align: left;' colspan='1'><strong>Placa: </strong>" +
		dadosApolice.numeroPlaca +
		"</td><td style='text-align: left;'><strong>Ano: </strong>" + dadosApolice.anoAutomovel + "</td></tr>";
	}

	if(obs != ""){
		 msgDetalheApolice += "<tr><td style='text-align: initial;' colspan='5'><strong>Observação: </strong>" +
							 	obs +
							 	"</td></tr>";
	 }
	
	 msgDetalheApolice += "</table>";	
	 
	 if(!temTexto){
		 msgDetalheApolice = "<b>Não foi possível exibir os detalhes da apólice, favor tentar novamente.</b>"
	 }
}


/**
 * Função que define qual a forma de pesquisa iniciará dependendo da requisição: pesquisa por período
 * ou pesquisa por apólice selecionada.
 * 
 */
function buscarDadosIniciaisTela(tipoRequisicao){

	requisicao = tipoRequisicao;
	try{
		
		if(tipoRequisicao == null || tipoRequisicao == ""){
			//Acesso via link do YMViaCorretor
	
			dwrDadosIniciaisAjax.buscarDadosIniciais({
				callback:function(response){
					callBackBuscarDadosIniciais(response);
				},
				timeout:45000,
				errorHandler:function(msg, exc) {
					exibeMsgError( "ERRO", msg );
					finalizarLoading();
				}
			});
		}else{
			
			loadingAtivo = true;
			try{
				showLoading();
			}catch(e){
				if(requisicao == 'ViaCorretor' || requisicao == 'ViaSegurado'){
					self.parent.doIframe();
				}
			}
			//Acesso via Apólice selecionada ao YMViaCorretor
			$(".columnAlignRight").css("display","none");
			$(".bntHabilitaBuscaAvancada").css("display","none");
			$(".column_full").css("display","none");
			$("#fieldsetViaCorretor").css("display","none");
			$("#camposDetalhados").css("display","none");
			
			var defineRequisicao = "";
			if(tipoRequisicao == 'ViaCorretor'){ 
				defineRequisicao = "CORRETOR";
			}else if(tipoRequisicao == 'ViaSegurado'){
				defineRequisicao = "SEGURADO";
			}else if(tipoRequisicao == 'ViaPrestador'){
				defineRequisicao = "PRESTADOR";
			}

			dwrPesquisaViaCorretorAjax.buscarResultadoApoliceSelecionada(defineRequisicao, {
					callback:function(response){
						montarTableByRequestAjax(response);
					},
					errorHandler:function(msg, exc) {
						exibeMsgError( "ERRO", msg );
						finalizarLoading();
					}
				});
		}
	}catch(e){
		finalizarLoading();
	}
}

function callBackBuscarDadosIniciais(response){
	
	try{
		
		document.getElementById("fieldsetViaCorretor").style.display = 'block';
		var responseTemp = response;
		$("#codCorretor").empty(); 
		
		if(responseTemp.listaCorretores == null || responseTemp.listaCorretores.length == 0){
			$("#codCorretor").append('<option class="boxInputDate" value="0" selected="selected">Corretor(a) não encontrado!</option>'); 
		}else if(responseTemp.listaCorretores.length == 1){
	
			//Colocar o Corretor que está logado, já selecionado
			$("#codCorretor").append("<option class='boxInputDate' selected='selected' value='" 
					+ responseTemp.listaCorretores[0].valueCorretor + "'>" 
					+ responseTemp.listaCorretores[0].textCorretor +"</option>"); 
		}else{
			$("#codCorretor").append('<option class="boxInputDate" value="0" selected="selected">Selecione a corretora</option>'); 
			$("#codCorretor").append('<option class="boxInputDate" value="T">TODOS</option>'); 
			for(var i = 0; i < responseTemp.listaCorretores.length; i++){
				$("#codCorretor").append("<option class='boxInputDate' value='" 
						+ responseTemp.listaCorretores[i].valueCorretor + "'>" 
						+ responseTemp.listaCorretores[i].textCorretor +"</option>");  
			}
		}
		$("#tipoRamo").empty();
		
		if(responseTemp.listaRamos != null){
			for(var j = 0; j < responseTemp.listaRamos.length; j++){
				if(responseTemp.listaRamos[j].valueRamo == 'AUTOMOVEL'){
					$("#tipoRamo").append("<option class='boxInputDate' selected='selected' value='" 
							+ responseTemp.listaRamos[j].valueRamo + "'>" 
							+ responseTemp.listaRamos[j].textRamo +"</option>"); 
				}else{
					$("#tipoRamo").append("<option class='boxInputDate' value='" 
							+ responseTemp.listaRamos[j].valueRamo + "'>" 
							+ responseTemp.listaRamos[j].textRamo +"</option>");
				}
			}
		}
		
		$("#codCorretor").chosen();
		$("#tipoRamo").chosen();
		
	}finally{
		finalizarLoading();
	}
}

function recuperaListaOficinasRET(ldata) {
	montarTableByRequestAjax(ldata);
}

/* Function para preparação dos dados da tela para pesquisa */
function buscarJsonPesquisa() {

	try {
		loadingAtivo = true;
		// showLoading();
		var estado             = $("#codEstado").val();
		var cidade             = $("#codCidade").val();
		var especialidade      = $("#codEspec").val();
		var bairro             = $("#codBairro").val();
		var tipo_oo= "";
		var tipo_b="";

		AjaxController.recuperaListaOficinas(estado, cidade, especialidade, bairro, tipo_oo, tipo_b ,recuperaListaOficinasRET);
	} catch (e) {
		exibeMsgError("ERRO", msg);
		finalizarLoading();
	}
}

function visualizaPdfBMK(chave){

	var urlBMK = 'http://www.bmk.com.br/web-beebox/yasudaservice/getPDF';
	
	$("body #result").html('<form name="bmk" id="bmk" method="post" target="_blank" action='+ urlBMK +'>'
	+ '<input type="hidden" name="chave" id="chave" value="'+chave+'" /></form>');
	$("#bmk").submit();
	return false;
}

function visualizaPdfONBASE(retorno){
	
	try{
		var retornoTemp = retorno.replace("-", "");
		var urlOnbase 	= './obterDocOnbaseAction';
		
		if($.isNumeric(retornoTemp)){

			downloadArquivo(urlOnbase, retorno);
			
		}else{
			
			if(retorno.indexOf(".zip") != -1){
				exibeMsgError("ALERTA", "Foi realizado o download de todos os Extratos da Apólice em arquivo zipado.<br/>Não há Extratos da Apólice para Kit Segurado.");
				downloadArquivo(urlOnbase, retorno);
			}else{
				exibeMsgError("ALERTA", retorno);
			}
		}
	}catch(e){
		finalizarLoading();
	} finally{
		finalizarLoading();
	}
	return false;
}

function downloadArquivo(urlOnbase, retorno){
	$("body #result").html('<form name="onbase" id="onbase" method="get" action='+ urlOnbase +'>'
	+ '<input type="hidden" name="dadosArquivo" id="dadosArquivo" value="'+retorno+'" /></form>');
	$("#onbase").submit();
}

function gerarPdfOnBase(dados){
	try{
		loadingAtivo = true;
		showLoading();
		$("#msgError").css("display","none");
		$("#msgError span").html("");
		dwrOnbaseAjax.gerarDocOnbase(dados, {
			callback:function(response){
				visualizaPdfONBASE(response);
			},
			errorHandler:function(msg, exc) {
				exibeMsgError( "ERRO", msg );	
				finalizarLoading();
			}
		});
	}catch(e){
		finalizarLoading();
	}
}

function gerarRelatorioExcel(){
	try{
		loadingAtivo = true;
		showLoading();
		dwrGeraRelatorioAjax.gerarExcel(dadosTabela, {
			callback:function(response){
				downloadArquivoExcel(response);
			},
			errorHandler:function(msg, exc) {
				exibeMsgError( "ERRO", msg );	
				finalizarLoading();
			}
		});
	}catch(e){
		finalizarLoading();
	}
}

function downloadArquivoExcel(result){
	try{

		var urlExcel = './downloadRelatorioViaCorretor';
		
		$("body #result").html('<form name="relExcel" id="relExcel" method="get" action='+ urlExcel +'>'
		+ '<input type="hidden" name="dadosArquivo" id="dadosArquivo" value="'+varCodigoCorretor+'" /></form>');
		$("#relExcel").submit();

	}catch(e){
		exibeMsgError("ERRO", "Erro ao exibir relatório em Excel, favor entrar em contato com o suporte do corretor.");
	}finally{
		finalizarLoading();
	}
	return false;
}

function exibeMsgError(type, mensagemErro){
	var title = "";
	$("#msgError").removeAttr("class");
	
	if(type == "ERRO") {
		$("#msgError").attr("class", "alert alert-danger");
		title= "ERRO!";
	} else if (type == "ALERTA") {
		$("#msgError").attr("class", "alert alert-warning");
		title="Atenção!";
	} else if (type == "INFO") {
		$("#msgError").attr("class", "alert alert-info");
		title="Informativo!";
	} else if (type == "SUCESSO") {
		$("#msgError").attr("class", "alert alert-success");
		title="Sucesso!";
	}
			
	$("#msgError span").html("");
	$("#msgError strong").html(title);
	$("#msgError span").append("<br />" + mensagemErro);
	$("#msgError").css("display","block");

	if(temDados){
		if(quantidadeLinha != 0 && quantidadeLinha <= 3){
			ajustaFrame(20);
		}else if(quantidadeLinha != 0 && quantidadeLinha <= 6){
			ajustaFrame(30);
		}else if(quantidadeLinha != 0 && quantidadeLinha > 6){
			ajustaFrame(50);
		}	
	}else{
		if(document.getElementById("listApolices").style.display == 'block'){
			ajustaFrame(20);
		}else{
			ajustaFrame(200);
		}
	}

	finalizarLoading();
}

function dataExibicao(dataini, datafim){
	var ini = dataini.split("/");
	var fim = datafim.split("/");
	var d = new Date();
	if((d.getFullYear() == ini[2] && d.getMonth()+ 1 == ini[1] && d.getDate()>= ini[0]) ||
	   (d.getFullYear() == fim[2] && d.getMonth()+ 1 == fim[1] && d.getDate()<= fim[0])){
        return true;
	}
	return false;
}

function exibeMsgTemp(msg, dataini, datafim){
    if(dataExibicao(dataini, datafim)){
		var title = "MENSAGEM!";
		var mensagemErro = msg;
		$("#msgTemporaria").attr("class", "alert alert-warning");
		$("#msgTemporaria span").html("");
		$("#msgTemporaria strong").html(title);
		$("#msgTemporaria span").append("<br />" + mensagemErro);
		$("#msgTemporaria").css("display","block");
    }
}

function escondeTable(){
	$(".divBntAction").css("display", "none");
	$("#listApolices").css("display", "none");
	$("#listApolices_wrapper").css("display", "none");
}

function addEvents() {
	
	$('#listApolices tbody').off();
	$('#listApolices tbody').on('click', 'td.detalhes', function () {
		
		var tr 				= $(this).closest('tr');
		var row 			= table.row(tr);
		var trOpenedInfo 	= null;
		
		if(trOpened != null){
			trOpenedInfo 	= table.row(trOpened).data().numeroApolice + table.row(trOpened).data().tipoDocumento + table.row(trOpened).data().numeroEndosso;
		}
		
//		window.open("/ConsultaOficinasRef/detalhe.action?codigo=1002551652");
		
		var rowInfo 		= table.row(tr).data().numeroApolice + table.row(tr).data().tipoDocumento + table.row(tr).data().numeroEndosso;
		
		//Há linhas duplicadas apenas para separar Via Corretor e Kit Segurado, então para buscar
		//os detalhes da apólice é feito este if, onde busca apenas um deles e exibe para os dois o resultado.
		if(trOpenedInfo == dadosRepetidos){
			dadosRepetidos 	= rowInfo;
			igual 			= true;
		}else{
			igual 			= false;
		}
		
		if (trOpened != null && trOpenedInfo != rowInfo)
			closeRow(trOpened);
		   	
		if(trOpenedInfo == rowInfo && table.row( tr ).child.isShown()){
			closeRow(tr);
		} else {
			loadingAtivo = true;
//			showLoading();
			setTimeout(function(){ openRow(tr); }, 500);
		}
	});
}

function closeRow(tr) {
	var row = table.row( tr );
	row.child.hide();
	tr.removeClass('shown');
	trOpened = null;
	tr.removeAttr("style");
	tr.each(function(){
	   $(this).find('td').each(function(){
	       	$(this).removeAttr("style");
	   });
	});
	
}

function openRow(tr) {
	var row = table.row( tr );

	if(!igual){
		buscarDetalhesApolice(row.data());	
	}
	
	row.child(msgDetalheApolice).show();
    tr.addClass('shown');
    trOpened = tr;
    tr.css("background-color", "rgb(208, 207, 207) !important");
    tr.css("font-weight", "bold");

    tr.each(function(){
        $(this).find('td').each(function(){
        	$(this).css("border-top", "1px solid #203147");
        	$(this).css("border-bottom", "1px solid #203147");
        	$(this).css("color", "#203147");
        });
    });

    ajustaFrame(5);
    
    if(igual){
//    	finalizarLoading();
    }
	
}

function finalizarLoading(){
	if(loadingAtivo){
		try{
			hideLoading();
		}catch(e){
			if(requisicao == null || requisicao == 'ViaCorretor' || requisicao == 'ViaSegurado'){
				self.parent.doIframe();
			}
		}
		
		loadingAtivo = false;
	}
}

function limparCampos(){
	$("#nomeSegurado").val("");
	$('#numeroCpfCnpj').val("");
	$('#numeroApolice').val("");
	$('#numeroEndosso').val("");
	$('#numeroItem').val("");
}

function ajusteInicialTela(){
	var ua = window.navigator.userAgent;
	var msie = ua.indexOf("MSIE");
	
	/*Ajuste do iframe para exibição de tela adequadamente*/
	try{
		if($('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document) != undefined
			&& $('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).length == 1){
			
			$('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).height('650px');	
			
		}else if($('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document) != undefined
				&& $('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).length == 1){
			
			$('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).height('650px');
			
		}else{
			
			$('iframe[name="iframeApp"]', window.parent.document).height('350px');
		}
		
		if (msie > 0){      // If Internet Explorer, return version number
			if($('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document) != undefined
				&& $('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).length == 1){
				
				$('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).width('910px');
			
			}else if($('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document) != undefined
					&& $('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).length == 1){
				
				$('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).width('910px');
			}else{
				$('iframe[name="iframeApp"]', window.parent.document).width('910px');
			}
        }else{
        	// If another browser, return 0
        	if($('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document) != undefined
        		&& $('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).length == 1){
        		$('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).width('905px');
        		
        	}else if($('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document) != undefined
            		&& $('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).length == 1){
        		
        		$('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).width('905px');
        	}else{
        		$('iframe[name="iframeApp"]', window.parent.document).width('905px');
        	}
	 	}

	}catch(e){
		if(requisicao == null || requisicao == 'ViaCorretor' || requisicao == 'ViaSegurado'){
			self.parent.doIframe();
		}
	}
}

function ajustaFrame(porcentagem) {

	var x = ($(".divBox").height() * (1 + porcentagem/100)) + "px";

	if($('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document) != undefined
		&& $('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).length == 1){
		
		$('iframe[name="via-corretor-yasuda-gestao"]', window.parent.document).height(x);	
		
	}else if($('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document) != undefined
			&& $('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).length == 1){
		
		$('iframe[name="via-corretor-yasuda-via-corretor-yasuda"]', window.parent.document).height(x);
		
	}else{
		$('iframe[name="iframeApp"]', window.parent.document).height(x);
	}
}

function Dado() {
	this.cod;
	this.val;
};

function changeEstado() {
//	showLoading();
	AjaxController.recuperaCidades($("#codEstado").val(), changeEstadoRET);
}

function changeCidade() {
//	showLoading();
	AjaxController.recuperaEspecialidades($("#codEstado").val(), $("#codCidade").val(), changeCidadeRET);
}

function changeEspecialidade() {
//	showLoading();
	AjaxController.recuperaDetalheCidade($("#codEstado").val(), $("#codCidade").val(), $("#codEspec").val(), changeEspecialidadeRET);
}

function changeBairro() {
//	showLoading();
	AjaxController.recuperaTiposOfBairro($("#codEstado").val(), $("#codCidade").val(), $("#codEspec").val(), $("#codBairro").val(), changeBairroRET);
}

function changeEstadoRET(ldata) {
	loadChosen($("#codCidade"), ldata);
//	finalizarLoading();
}

function changeCidadeRET(ldata) {
	loadChosen($("#codEspec"), ldata);
//	finalizarLoading();
}

function changeEspecialidadeRET(ldata) {
	loadChosen($("#codBairro"), ldata.bairros);
//	finalizarLoading();
}

function changeBairroRET(ldata) {
	loadChosen($("#codTpOficina"), ldata);
//	finalizarLoading();
}

function loadChosen(component, json) {

	var comp;
	try {
		comp = $("#" + component);

	} catch (exception) {
		comp = component;
	}

	comp.find('option').remove();
	comp.append(new Option("Selecione", ""));

	for (var i = 0; i < json.length; i++) {
		comp.append(new Option(json[i].val, json[i].cod));
	}

	comp.chosen({
		width : '100%',
		no_results_text : "Oops, sem resultado para "
	});

	comp.trigger("chosen:updated");
}
