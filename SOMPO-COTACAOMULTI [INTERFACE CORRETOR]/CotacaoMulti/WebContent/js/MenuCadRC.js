//
// Consistencias usadas nas p�ginas de cadastro
//
const oneMonth 	= 2628000000;
const oneYear 	= 31536000000;
const fiveYear 	= 157766400000;

$(document).ready(
    function() {
        // componentes
        // $("#corretorSel").chosen();

        // Mascaras
        $("#cCotacao").mask("999999999999", {placeholder : ""})
        $("#numApol").mask("9?999999999", {placeholder : ""})
        $("#numCEP").mask("99999-999", {placeholder : " "});
        $("#VlRisco").maskMoney({prefix: "",decimal:",", thousands:".", precision:2, affixesStay:true});
        $("#TelefContato").mask("(99)9999-9999?9").focusout(function (event) {
            var target, phone, element;
            target = (event.currentTarget) ? event.currentTarget : event.srcElement;
            phone = target.value.replace(/\D/g, '');
            element = $(target);
            element.unmask();
            if(phone.length > 10) {
                element.mask("(99)9-9999-999?9");
            } else {
                element.mask("(99)9999-9999?9");
            }
        });

        // Nova máscara para CPF/CNPJ
        $('#cpfCnpj').unmask(); // Limpa qualquer máscara antiga

        var CpfCnpjMaskBehavior = function (val) {
                return val.replace(/[^a-zA-Z0-9]/g, '').length > 11 ? 'AA.AAA.AAA/AAAA-00' : '000.000.000-009';
        },
        options = {
            onKeyPress: function(val, e, field, options) {
                field.mask(CpfCnpjMaskBehavior.apply({}, arguments), options);
            }
        };

        $('#cpfCnpj').mask(CpfCnpjMaskBehavior, options);
        $('#lbCnpj').mask(CpfCnpjMaskBehavior, options);

        // Datapicker
        $.datepicker.regional['pt-BR'] = {
            closeText : 'Fechar',
            prevText : '&#x3c;Anterior',
            nextText : 'Pr&oacute;ximo&#x3e;',
            currentText : 'Hoje',
            monthNames : [ 'Janeiro', 'Fevereiro', 'Mar&ccedil;o', 'Abril',
                    'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro',
                    'Outubro', 'Novembro', 'Dezembro' ],
            monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
                    'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
            dayNames : [ 'Domingo', 'Segunda-feira', 'Ter&ccedil;a-feira',
                    'Quarta-feira', 'Quinta-feira', 'Sexta-feira',
                    'S&aacute;bado' ],
            dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex',
                    'S&aacute;b' ],
            dayNamesMin : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex',
                    'S&aacute;b' ],
            weekHeader : 'Sm',
            dateFormat : 'dd/mm/yy',
            firstDay : 0,
            isRTL : false,
            showMonthAfterYear : false,
            yearSuffix : ''
        };
        $.datepicker.setDefaults($.datepicker.regional['pt-BR']);
        $(".datepicker").datepicker();
        $(".datepicker").mask("99/99/9999", {
            placeholder : "dd/mm/aaaa"
        });

        $(".jquery-selectbox-currentItem").click(function() {
            $(this).parent().find(".jquery-selectbox-moreButton").click();
        });

    }
);


function showLoadingAlign() {
	
	/*showLoading();
	
	var w = window,
	d = document,
	e = d.documentElement,
	g = d.getElementsByTagName('body')[0],
	x = w.innerWidth || e.clientWidth || g.clientWidth,
	y = w.innerHeight || e.clientHeight|| g.clientHeight;
	
	var scrollTop = w.parent.document.screenTop;	
	var div = $('div[tabindex=-1]');
	
	div.css("top", "");
			
	div.css("position","absolute");
	div.css("left", "0%");
	div.css("top", (115 + scrollTop) - ((y/2) - 60) +"px");
	div.css("width", "100%");
	div.css("height", "100%");
	
	alert(scrollTop);
	alert((y/2) - 60);*/
}

function alteraTpCotacao() {
	var radioSelTpEmissao = $("input[name='tpCotacao']:checked").val();
	var cpNumApol = $("#cCotacao");

	if (radioSelTpEmissao == "2") {
		$("#divNumCotacao").removeClass("invisivel");
		$("#cCotacao").removeAttr("disabled");
	} else {
		$("#divNumCotacao").addClass("invisivel");
		$("#cCotacao").attr("disabled","disabled");
		cpNumApol.val("");
	}

}


function pesquisaCep() {

	var numCEP = $("#numCEP").val().trim();
	numCEP = numCEP.replace("-", "");
	var endereco = $("#endereco");
	var complEnd = $("#complEnd");
	var bairro = $("#bairro");
	var cidade = $("#cidade");
	var UF = $("#UF");

	endereco.val("");
	bairro.val("");
	cidade.val("");
	UF.val("");
	complEnd.val("");
	
	endereco.attr("readonly", "readonly");
	bairro.attr("readonly", "readonly");

	if (numCEP != "" && numCEP != null && numCEP.length == 8) {
		
		$.ajax(
				{
					type : "GET",
					url : "Controller?action=cepAjax",
					dataType : "json",
					data : {
						numCEP : numCEP
					},
					success : function(json) {
						if (json.tpLogradouro != undefined) {
					
							var jsonEnderecoRet = json.tpLogradouro + " " + json.nomeLogradouro;
							
							endereco.val(jsonEnderecoRet.trim());
							bairro.val(json.nomeBairro);
							cidade.val(json.nomeCidade);
							UF.val(json.nomeEstado);
							complEnd.val(json.nomeComplemento);
							$("#numeroEnd").focus();
							if(json.nomeCidade == "" || json.nomeBairro == ""){
								endereco.removeAttr("readonly");
								bairro.removeAttr("readonly");
								endereco.focus();
							}
						}  
						else {
							$("#numCEP").val("");
						}
					}
				});
	}
}

function changeRamo() {

	alteraModalidade();
	alteraRamoLmiLmg();
}

function alteraCorretor() {
	
	var codCorretorSel = $("#corretorSel").val();
	var unidadeSelection = $("#unidadeSel");
	unidadeSelection.find('option').remove();
		
	$.ajax({
		type : "GET",
		url : "Controller?action=buscaUnidadeAjax",
		async: false,
		dataType : "json",
		data : {
			cCorretor : codCorretorSel
		},
		success : function(json) {
			unidadeSelection.append(new Option("Selecione", ""));
			$.each(json, function(key, data) {
				var txtSel = data.unidNegocio + " - " + data.nomDepto;
				var txtCod = data.unidNegocio + "-" + data.codProdutor;
				unidadeSelection.append(new Option(txtSel, txtCod));
			})
		}
	});

}

function alteraTipoPessoa() {
    var lbNome = $("#lbNome");
    var lbRazao = $("#lbRazaoSocial");
    var lbFisica = $("#lbCpf");
    var lbJuridica = $("#lbCnpj");
    var tpPessoaRadio = $("input[name='tipoPessoa']:checked").val();
    var tpPessoa = $("#cpfCnpj");
    var lbAtivPrincipal = $("#lbAtivPrincipal");
    var lbProfissao = $("#lbProfissao");
    tpPessoa.removeAttr("readonly");

    tpPessoa.val("");
    tpPessoa.unmask();

    if (tpPessoaRadio == "1") { // Pessoa Jurídica
        lbJuridica.removeClass("removeComp");
        lbFisica.addClass("removeComp");
        lbRazao.removeClass("removeComp");
        lbNome.addClass("removeComp");
        lbAtivPrincipal.removeClass("removeComp");
        lbProfissao.addClass("removeComp");
    } else { // Pessoa Física
        lbJuridica.addClass("removeComp");
        lbRazao.addClass("removeComp");
        lbFisica.removeClass("removeComp");
        lbNome.removeClass("removeComp");
        lbProfissao.removeClass("removeComp");
        lbAtivPrincipal.addClass("removeComp");
    }

    // Ação 3: Coloque o foco no campo para o usuário digitar.
    tpPessoa.focus();

    alteraTipoPessoaProfissaoAtividadeAjax();
}

function alteraTipoPessoa() {

	var lbNome = $("#lbNome");
	var lbRazao = $("#lbRazaoSocial");
	var lbFisica = $("#lbCpf");
	var lbJuridica = $("#lbCnpj");
	var tpPessoaRadio = $("input[name='tipoPessoa']:checked").val();
	var tpPessoa = $("#cpfCnpj");
	var lbAtivPrincipal = $("#lbAtivPrincipal");
	var lbProfissao = $("#lbProfissao");
	tpPessoa.removeAttr("readonly");
	
	tpPessoa.val(" ");
	tpPessoa.unmask();

	if (tpPessoaRadio == "1") {
		lbJuridica.removeClass("removeComp");
		lbFisica.addClass("removeComp");
		lbRazao.removeClass("removeComp");
		lbNome.addClass("removeComp");
		lbAtivPrincipal.removeClass("removeComp");
		lbProfissao.addClass("removeComp");
		tpPessoa.mask("AA.AAA.AAA/AAAA-00");
	} else {
		lbJuridica.addClass("removeComp");
		lbRazao.addClass("removeComp");
		lbFisica.removeClass("removeComp");
		lbNome.removeClass("removeComp");
		lbProfissao.removeClass("removeComp");
		lbAtivPrincipal.addClass("removeComp");
		tpPessoa.mask("999.999.999-99");
	}

	alteraTipoPessoaProfissaoAtividadeAjax();

}

function alteraRamoLmiLmg() {
	var divLmiLmg = $("#divLmgLmi");
	var divLmg = $("#lbLmg");
	var divLmi = $("#lbLmi");
	var ramoSel = $("#ramoSel").val().trim();

	if (ramoSel == "510" || ramoSel == "780") {
		divLmi.addClass("removeComp");
		divLmg.removeClass("removeComp");
		divLmiLmg.removeClass("invisivel");
	} else if (ramoSel == "670" || ramoSel == "750" || ramoSel == "760") {
		divLmg.addClass("removeComp");
		divLmi.removeClass("removeComp");
		divLmiLmg.removeClass("invisivel");
	} 		
	 else {
		divLmiLmg.addClass("invisivel");
	}

}

function alteraModalidade() {

	var ramoSel = $("#ramoSel").val();
	var modalidadeSel = $("#modalidadeSel");
	modalidadeSel.find('option').remove();

	if (ramoSel != "" && ramoSel != null && ramoSel != "760" && ramoSel != "750") {
		
		$("#divModalidade").removeClass("invisivel");
		$("#modalidadeSel").removeAttr("disabled");
		
		$.ajax({
			type : "GET",
			url : "Controller?action=buscaModalidadeAjax",
			async: false,
			dataType : "json",
			data : {
				ramo : ramoSel
			},
			success : function(json) {
				modalidadeSel.append(new Option("Selecione", ""));
				$.each(json, function(key, data) {
					modalidadeSel.append(new Option(data.nome, data.id));
				})
			}
		});
	}
	else{
		$("#divModalidade").addClass("invisivel");
		$("#modalidadeSel").attr("disabled","true");
	}
}

function alteraTipoPessoaProfissaoAtividadeAjax() {

	var tipoPessoa = $("input[name='tipoPessoa']:checked").val();
	var profissaoRamoAtividadeSel = $("#profissaoRamoAtividade");
	var codigo;

	profissaoRamoAtividadeSel.find('option').remove();

	$.ajax(
			{
				type : "GET",
				url : "Controller?action=buscaProfissaoRamoAtividadeAjax",
				async: false,
				dataType : "json",
				data : {
					tipoPessoa : tipoPessoa
				},
				success : function(json) {
					profissaoRamoAtividadeSel.append(new Option("Selecione", ""));
					$.each(json, function(key, data) {
						if(tipoPessoa == '0'){
							codigo = data.id + data.subId;
						}
						else{
							codigo = data.id;
						}
						profissaoRamoAtividadeSel.append(new Option(data.nome, codigo));
					})
				}
			});
}

// Upload

var processando = false;

function uploadFile() {

	if (processando) {
		alert("Aguarde, já está sendo processado!");
	}

	$("#action").val("uploadEmailFileNewVisual");
	$("#arqAnexoFake").val($("#arqAnexo").val());
	processando = true;
	document.frm.submit();

}

function downloadFile(file) {
	if (processando) {
		alert("Aguarde, já está sendo processado!");
	}
	window
			.open(
					'/CotacaoMulti/VisualizaDoc.jsp?file=' + file,
					'Documento',
					'fullscreen=no,border=yes,toolbar=no,location=no,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes');

}

function delFile(file) {

	if (processando) {
		alert("Aguarde, já está sendo processado!");
	}

	document.frmDel.nameTempFile.value = file;
	document.frmDel.action.value = "delEmailFileNewVisual";
	processando = true;
	document.frmDel.submit();
}

function classificaFile(arquivo, c) {

	if (processando) {
		alert("Aguarde, já está sendo processado!");
	}

	document.frmClassifica.nome.value = arquivo;
	document.frmClassifica.classifica.value = c;
	document.frmClassifica.action.value = "ArquivoClassifica";
	processando = true;
	document.frmClassifica.submit();
}

function calcHeight() {
	try {
		// find the height of the internal page
		var the_height = document.getElementById('frameFile').contentWindow.document.body.scrollHeight;

		// change the height of the iframe
		document.getElementById('frameFile').height = the_height;

		// find the height of the internal page
		var the_height1 = parent.document.getElementById('Hats').contentWindow.document.body.scrollHeight;

		// change the height of the iframe
		parent.document.getElementById('Hats').height = the_height1;
	} catch (e) {
	}
}

document.getElementById('frameFile').onload = function() {
	calcHeight();
}

function alteraTipoEmissao() {
	var tpEmissao = $("#TipEmissao").val();
	var divNumApol = $("#divNumApol");
	
	if (tpEmissao == "02" || tpEmissao == "03" || tpEmissao == " ") {
		divNumApol.removeClass("invisivel");
		$("#numApol").removeAttr("disabled");
	} else {
		$("#numApol").val("");
		divNumApol.addClass("invisivel");
		$("#numApol").attr("disabled","disabled");
	}

}

function AddZeros(objCampo, intSize) {
	var slinha = new String();
	var sretlinha = new String();
	slinha = objCampo.toString();
	var tam = slinha.length;

	if ((tam < intSize) && (tam > 0)) {
		for (i = 0; i < (intSize - tam); i++) {
			sretlinha += "0";
		}
	}
	return (sretlinha + slinha);
}

function valida_CPF(valor) {
    let s = String(valor || '').replace(/\D/g, '');

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

function valida_CGC(value) {
    let cnpjString = String(value || '');
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

function verificaCpfCnpj() {
    var tpPessoa = $("input[name='tipoPessoa']:checked").val();
    var comp = $("#cpfCnpj");

    var cpfCnpj = String(comp.val()).replace(/[^a-z0-9]/ig, '').toUpperCase();

    if (tpPessoa == "1") {
        if (cpfCnpj != "" && !valida_CGC(cpfCnpj)) {
            $("#cpfCnpj").val("");
            alert("Número de CNPJ inválido");
            $("#cpfCnpj").focus();
            return false;
        }
    } else { // Pessoa Física (CPF)
        if (cpfCnpj != "" && !valida_CPF(cpfCnpj)) {
            $("#cpfCnpj").val("");
            alert("Número de CPF inválido");
            $("#cpfCnpj").focus();
            return false;
        }
    }
    return true;
}

function limparCamposRecotacao(){
	$("#corretorSel option").removeAttr('selected').filter("[value='']").attr('selected', true);
	$("#EmailContato").val("");			
	$("#TelefContato").val("");
	$("#NomeContato").val("");	
	$("#ramoSel option").removeAttr('selected').filter("[value='']").attr('selected', true);	
	$("#TipEmissao option").removeAttr('selected').filter("[value='']").attr('selected', true);	
	$("input[name=tipoPessoa]").prop("checked",false);	
	$("#nomeProp").val("");
	$("#numCEP").val("");
	$("#endereco").val("");
	$("#cidade").val("");
	$("#bairro").val("");
	$("#UF").val("");
	$("#complEnd").val("");
	$("#numeroEnd").val("");
	$("#unidadeSel option").removeAttr('selected').filter("[value='']").attr('selected', true);	
	$("#profissaoRamoAtividade option").removeAttr('selected').filter("[value='']").attr('selected', true);		
	$("#modalidadeSel option").removeAttr('selected').filter("[value='']").attr('selected', true);		
	$("#cpfCnpj").val("");
	$("#numApol").val("");

	alteraCorretor();						
	alteraTipoPessoa(); 
	changeRamo();
	alteraTipoEmissao();	
	
	$("#cpfCnpj").attr("readonly","readonly");
	$("#cpfCnpj").unmask();
}

function recotacaoAjax() {
	
	var codigoUnidade;
	var codigoModalidade;
	var profissaoRamoAtividade;
	var verificaRetorno = "";

	var cCotacao = $("#cCotacao").val();

	if (cCotacao != "" && cCotacao != null && cCotacao.length == 12) {
								
		$.ajax({
			type : "POST",
			url : "Controller?action=recotacaoRcAjax",
			async: false,
			dataType : "json",
			data : {
				cCotacao : cCotacao
			},
			success : function(json) {
				
				codigoUnidade = json.codDepto + "-" + json.codProdutor;
				codigoModalidade = json.codModalidade;
				profissaoRamoAtividade = json.profissaoRamoAtividade;
				
				$("#corretorSel option").removeAttr('selected').filter("[value=" + json.codCorr + "]").attr('selected', true);
				$("#EmailContato").val(json.emailContato);			
				$("#TelefContato").val(json.telefContato);
				$("#NomeContato").val(json.nomeContato);	
				$("#ramoSel option").removeAttr('selected').filter("[value=" + json.codRamo + "]").attr('selected', true);	
				$("#TipEmissao option").removeAttr('selected').filter("[value=" + json.tipEmissao + "]").attr('selected', true);	
				$("input[name=tipoPessoa][value='" + json.tipPessoa + "']").prop("checked",true);	
				$("#nomeProp").val(json.nomeProp);
				$("#numCEP").val(json.cep);
				$("#endereco").val(json.end);
				$("#cidade").val(json.cid);
				$("#bairro").val(json.bairro);
				$("#UF").val(json.uf);
				$("#complEnd").val(json.complemento);
				$("#numeroEnd").val(json.numero);
				if(json.codCorr != undefined){
					alteraCorretor();						
				}
				alteraTipoPessoa(); 
				changeRamo();
				alteraTipoEmissao();	
				$("#cpfCnpj").val(json.cnpjCpf);
				trataCamposRetornoRecotacaoAjax();	
				if(json.numApol != "0"){
					$("#numApol").val(json.numApol);						
				}
	
				if(json.flag != undefined){
					alert(json.descricao);
					limparCamposRecotacao();
				}
			}
		}).done(function(){	
				$("#unidadeSel option").removeAttr('selected').filter("[value=" + codigoUnidade + "]").attr('selected', true);	
				$("#modalidadeSel option").removeAttr('selected').filter("[value=" + codigoModalidade + "]").attr('selected', true);
				$("#profissaoRamoAtividade option").removeAttr('selected').filter("[value=" + profissaoRamoAtividade + "]").attr('selected', true);					
		});	
		
	}
	else{
		//limparCamposRecotacao();
	}
	
	
}

function trataCamposRetornoRecotacaoAjax(){
	var tpPessoa = $("#cpfCnpj");
	var tpPessoaRadio = $("input[name='tipoPessoa']:checked").val();
	tpPessoa.removeAttr("readonly");
	tpPessoa.unmask();

	if (tpPessoaRadio == "1") {
		tpPessoa.mask("99.999.999/9999-99");
	} else {
		tpPessoa.mask("999.999.999-99");
	}
	
	var telefone = $("#TelefContato"); 
    
	telefone.unmask(); 
    if(telefone.val().length > 10) {  
    	telefone.mask("(99)9-9999-999?9");  
    } else {  
    	telefone.mask("(99)9999-9999?9");  
    }  
    
    var numCEP = $("#numCEP");
    numCEP.unmask();
    numCEP.mask("99999-999");  
}

function cadastraCotacao() {
	
	if(!consistencias()){
		return false;
	}
		
	$("#cpfCnpj").val($("#cpfCnpj").val().replace(".", "").replace(".", "").replace("-","").replace("/", ""));
	if($("#cpfCnpj").val() != ""){
		var numCEP = $("#numCEP").val().trim();
		numCEP = numCEP.replace("-", "");
		$("#numCEP").val(numCEP);
		var telefone = $("#TelefContato");
	}
	
	telefone.val(telefone.val().replace("-","").replace("-","").replace("(","").replace(")",""));
	document.frm1.submit();
}

function novaCotacao(){
	document.frm2.submit();
}

function consistencias(){
	var corretor = $("#corretorSel");
	var unidade = $("#unidadeSel");
	var emailContato = $("#EmailContato");
	var telefContato = $("#TelefContato");
	var nomeContato = $("#NomeContato");
	var tpCotacao = $("input[name='tpCotacao']:checked");
	var cCotacao = $("#cCotacao");
	var ramoSel = $("#ramoSel");
	var modalidadeSel = $("#modalidadeSel");
	var TipEmissao = $("#TipEmissao");
	var numApol = $("#numApol");
	var tipoPessoa = $("input[name='tipoPessoa']:checked");
	var cpfCnpj = $("#cpfCnpj");
	var nomeProp = $("#nomeProp");
	var inicioVig = $("#inicioVig");
	var terminoVig = $("#terminoVig");
	var VlRisco = $("#VlRisco");
	var texto = "O preenchimento do campo ";
	var textoFinal = " é obrigatório.";
	var endereco = $("#endereco");
	var bairro = $("#bairro");
	
	if(corretor.val() == ""){
		alert("Selecione um corretor.");
		corretor.focus();
		return false;
	}
	
	if(unidade.val() == ""){
		alert("Selecione uma unidade.");
		unidade.focus();
		return false;
	}
	
	if(emailContato.val() == ""){
		alert(texto + "''E-mail do Solicitante''" + textoFinal);
		emailContato.focus();
		return false;
	}
	
	if(telefContato.val() == ""){
		alert(texto + "''Telefone''" + textoFinal);
		telefContato.focus();
		return false;
	}
	
	
	if(nomeContato.val() == ""){
		alert(texto + "''Contato''" + textoFinal);
		nomeContato.focus();
		return false;
	}
	
	
	if(tpCotacao.val() == undefined){
		alert("Selecione o tipo de cotação.");
		tpCotacao.focus();
		return false;
	}
	
	if(tpCotacao.val() == "2" && cCotacao.val() == ""){
		alert(texto + "''Nº Cotação''" + textoFinal);
		cCotacao.focus();
		return false;
	}
	
	if(ramoSel.val() == ""){
		alert("Selecione um ramo.");
		ramoSel.focus();
		return false;
	}
	
	if(modalidadeSel.val() == ""){
		alert("Selecione uma modalidade.");
		modalidadeSel.focus();
		return false;
	}
	
	if(TipEmissao.val() == ""){
		alert("Selecione um tipo de emissão.");
		TipEmissao.focus();
		return false;
	}
	
	if((TipEmissao.val() == "02" || TipEmissao.val() == "03") && numApol.val() == ""){
		alert(texto + "''Apólice''" + textoFinal);
		numApol.focus();
		return false;
	}
	
	if(tipoPessoa.val() == undefined){
		alert("Selecione o tipo de pessoa.");
		tipoPessoa.focus();
		return false;
	}
	
	if(cpfCnpj.val() == ""){
		if(tipoPessoa.val() == "1")
			alert(texto + "''CNPJ''" + textoFinal);
		else
			alert(texto + "''CPF''" + textoFinal);	
		cpfCnpj.focus();
		return false;
	}
	
	if(nomeProp.val() == ""){
		if(tipoPessoa.val() == "1")
			alert(texto + "''Razão Social''" + textoFinal);
		else
			alert(texto + "''Nome''" + textoFinal);
		nomeProp.focus();
		return false;
	}
		
	if(endereco.attr("readonly") != "readonly" && endereco.val() == ""){
		alert(texto + "''Endereço''" + textoFinal);
		return false;
	}
	
	if(bairro.attr("readonly") != "readonly" && bairro.val() == ""){
		alert(texto + "''Bairro''" + textoFinal);
		return false;
	}
	
	if(inicioVig.val() == ""){
		alert(texto + "''Início de vigência''" + textoFinal);
		inicioVig.focus();
		return false;
	}
	
	if(terminoVig.val() == ""){
		alert(texto + "''Término de vigência''" + textoFinal);
		terminoVig.focus();
		return false;
	}
	
	if (dtMaior(inicioVig.val(), terminoVig.val()) == false) {
		alert("Data de início de vigência deve ser menor que a data de término da vigência.");
		inicioVig.focus();
		return false;
	}
	//  Riscos de engenharia nao possuem 
	//	Para modalidade de RC Obras Civis e/ou Prestação...: vigência máxima de 5 anos
	if(ramoSel.val() == "510" && modalidadeSel.val() == "25"){ 
		if(isDiferencaAnosMaior(inicioVig.val(),terminoVig.val(), 5)){
			alert("A cotação não poderá ser transmitida, pois o término de vigência é superior a 5 anos da data de início da vigência.");
			terminoVig.focus();
			return false;
		}	
	}
	else if(ramoSel.val() == "510"){
		// Demais de RC: vigência máxima 12 meses
		if(isDiferencaAnosMaior(inicioVig.val(),terminoVig.val(), 1)){
			alert("A cotação não poderá ser transmitida, pois o término de vigência é superior a 1 ano da data de início da vigência.");
			terminoVig.focus();
			return false;
		}
	}
	else if(ramoSel.val() == "780"){
		// Demais de RC Profissional: vigência 1 a 5
		if(!isIntervalYears(inicioVig.val(),terminoVig.val(), oneMonth, fiveYear)){
			alert("A cotação não poderá ser transmitida, pois o período de vigência deverá ser entre 1 mês a 5 anos.");
			terminoVig.focus();
			return false;
		}
	}
	
	if(VlRisco.val() == ""){
		if(ramoSel.val() == "510" || ramoSel.val() == "780")
			alert(texto + "''LMG''" + textoFinal);
		else
			alert(texto + "''LMI''" + textoFinal);
		VlRisco.focus();
		return false;
	}
	
	return true;
	
}
