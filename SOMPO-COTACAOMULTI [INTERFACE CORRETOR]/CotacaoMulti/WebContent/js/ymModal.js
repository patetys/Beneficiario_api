// JavaScript Document

var ymmDialogDefault = {titulo:'Atenção', width:330, height: 70, textalign: 'left', fadingtime:100, opacity: 0.3};
var ymmFecharHook = null;
$(document).ready(function() {
	ymmEstruturaModal();
});

function ymmEstruturaModal() {
	var estModal = "<div id='ymmMoldura'>"
			+ "<div id='ymmTituloDiv'><h2 id='ymmTitulo'></h2></div>"
			+ "<div id='ymmFecharDiv' class='ymFechar'></div><div style='clear:both'/>"
			+ "<div id='ymmConteudo'></div>" + "</div>"
			+ "<div id='ymmOriginalPosition' style='display:none'></div>"
			+ "<div id='ymmMascara'></div>";
	$('body').append(estModal);
	$('#ymmFecharDiv').click(
			function(ev) {
				ev.preventDefault();
				var test = true;
				if (typeof ymmFecharHook === "function") {
					test = ymmFecharHook();
				}
				if (test == null || test === "" || test == "true" || test) {
					ymmFecharModal();
				}
			});
}

function ymmGetScrollTop() {
	var scrollTop;
	if (typeof (window.pageYOffset) == 'number') {
		// DOM compliant, IE9+
		scrollTop = window.pageYOffset;
	} else {
		// IE6-8 workaround
		if (document.body && document.body.scrollTop) {
			// IE quirks mode
			scrollTop = document.body.scrollTop;
		} else if (document.documentElement
				&& document.documentElement.scrollTop) {
			// IE6+ standards compliant mode
			scrollTop = document.documentElement.scrollTop;
		}
	}
	return scrollTop;
}

function ymmExibirModal(div, configuracao) {
	if(configuracao == null) configuracao={};
	var id = "#ymmMoldura";
	var alturaDoc = $(document).height();
	var larguraDoc = $(document).width();
	var alturaTela = $(window).height();
	var larguraTela = $(window).width();
	var larguraPop = configuracao.width;
	if(larguraPop==null || larguraPop > larguraTela)larguraPop=larguraTela-100;
	var larguraGap = (larguraTela - larguraPop)/2;
	$("#ymmTitulo").html("");
	var alturaPop = configuracao.height;
	if(alturaPop==null || alturaPop > alturaTela)alturaPop=alturaTela-100;

	if (configuracao != null) {
		if (typeof configuracao.validacao == "function") {
			ymmFecharHook = configuracao.validacao;
		}
		if(configuracao.titulo != null)
			$("#ymmTitulo").html(configuracao.titulo);
	}

//	// colocando o fundo preto
//	$('#ymmMascara').css({
//		'width' : larguraDoc,
//		'height' : alturaDoc
//	});
//	$('#ymmMascara').fadeIn(750);
//	$('#ymmMascara').fadeTo("normal", 0.6);

	ymBloquearTela(750, 0.5);

	var left = larguraGap;
	var top = 50 + ymmGetScrollTop();

	$(id).css({
		'top' : top,
		'left' : left,
		'width' : larguraPop,
		'height' : alturaPop
	});
	$("#ymmOriginalPosition").insertBefore(div);
	div.appendTo($("#ymmConteudo"));
	div.show();
	$("#ymmConteudo").css({
		'height' : alturaPop - 30
	});

	$('body').addClass('ymmTravarScroll');

	$(id).fadeIn(750);
}

function ymmFecharModal(){
	$("#ymmMascara").hide();
	$("#ymmMoldura").hide();
	var div = $("#ymmConteudo div");
	var html = div.html();
	div.hide().insertAfter($("#ymmOriginalPosition")).html(html);
	$('body').removeClass('ymmTravarScroll');
	ymmFecharHook = null;
}

$.fn.extend({
	ymModal : function(configuracao) {
		ymmExibirModal($(this), configuracao)
		return this;
	}
});

function ymmExibirDialog(configuracao){
	
	 configuracao = $.extend(true,{},ymmDialogDefault, configuracao);
	
	var id = "#ymmMoldura";
	var alturaTela = $(window).height();
	var larguraTela = $(window).width();
	var left = (larguraTela / 2) - (configuracao.width / 2);
	var top = (alturaTela / 2) - (configuracao.height / 2) + ymmGetScrollTop();
	
	
	ymBloquearTela(configuracao.fadingtime, configuracao.opacity);
	
	$(id).addClass("ymmDialog").css({
		'top' : top,
		'left' : left
	});
  
	$("#ymmTitulo").html(configuracao.titulo);
	$("#ymmConteudo").html(
			"<div id='ymmDialogConteudo'>" + configuracao.mensagem + "</div>" +
			"<div id='ymmBotoesDialog'></div>");
	$("#ymmDialogConteudo").css({
		'width' : configuracao.width,
		'min-height' : configuracao.height,
		'text-align' : configuracao.textalign
 })
	if(configuracao.tpModal == 'confirmaSimNao'){
		$("#ymmBotoesDialog").html("<input type='button' value='Sim'/><input type='button' value='Não'/>");
	}
	else{
		$("#ymmBotoesDialog").html("<input type='button' value='OK'/>");
	}

	var retorno = configuracao.retornoHook;
	$("#ymmBotoesDialog :input[type='button']").click(function(){
		var valor = $(this).val();
		ymmFecharModal();
		if(typeof retorno == 'function') retorno(valor);
	});
	
	ymmFecharHook = function(){
		if(typeof retorno == 'function') setTimeout(function(){retorno('fechar')},50);
	}

	$(id).show();
	
}


function ymBloquearTela(tempo, opacity){

	var alturaDoc = $(document).height();
	var larguraDoc = $(document).width();
	
	// colocando o fundo preto
	$('#ymmMascara').css({
		'width' : larguraDoc,
		'height' : alturaDoc
	});
	$('#ymmMascara').fadeIn(tempo/2);
	$('#ymmMascara').fadeTo(tempo/2, opacity);
	
	$('body').addClass('ymmTravarScroll');
}
