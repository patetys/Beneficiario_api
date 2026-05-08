<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="bean.Project"%>
<%@ page import="bean.Ramo"%>
<%@ page import="bean.Emissao"%>
<%@ page import="bean.UnidadeBean"%>

<%
Random random = new Random(); 
String valorRan = "" + random.nextInt();
valorRan = valorRan.trim();
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<meta content="chrome=1; IE=9; IE=7; IE=8; IE=10; IE=11; IE=EDGE" http-equiv="X-UA-Compatible" />
<TITLE>Cotação</TITLE>
<c:set var="sompo" value="${sessionScope['sompo']}" />
<!-- CSS -->
<link rel="stylesheet" type="text/css" href="css/jquery.selectbox.css" /> 
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.11.4.css" />
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
<c:if test="${sompo == 'Sompo'}">
	<link rel="stylesheet" type="text/css" href="css/sompo_calendario.css" />
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<link rel="stylesheet" type="text/css" href="css/calendario.css" />
</c:if>

<link rel="stylesheet" type="text/css" href="css/chosen.css" /> 
<link rel="stylesheet" type="text/css" href="css/ymModal.css?<%=Project.version%>" />
 
<c:if test="${sompo == 'Sompo'}">
	<link rel="stylesheet" type="text/css" href="css/sompo_stInterno.css?<%=Project.version%>" />
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<link rel="stylesheet" type="text/css" href="css/stInterno.css?<%=Project.version%>" />
</c:if>

<style>
.col1 {width: 424px;}
.col2 {width: 320px;}
.col3 {width: 600px;}

.margin-col1 {margin-left: 10px;}
.caixa_select_custom .txtComp {width: 1055px;}
 .style-select select {
	overflow: hidden;
	background: url(./images/botoes/seta-select.png) no-repeat right #fafafa;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
} 

 .style-select select::-ms-expand {
 display: none !important; 
}  
</style>
<!-- JS -->
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
 <script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery.maskedinput-1.2.2-co.min.js"></script>
<script type="text/javascript" src="js/jquery.selectbox-0.6.1.js"></script> 
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<script type="text/javascript" src="js/highlight.js"></script>
<script type="text/javascript" src="js/ymModal.js?param=<%=valorRan%>"></script>
<SCRIPT type="text/javascript" src="js/consistencias.js?param=<%=valorRan%>" charset="UTF-8"></SCRIPT>
<SCRIPT type="text/javascript" src="js/consistenciasMSCad.js?param=<%=valorRan%>" charset="UTF-8"></SCRIPT>
<SCRIPT type="text/javascript" src="js/chosen.jquery.js" charset="UTF-8"></SCRIPT> 
<script type="text/javascript" src="Components/resources/js/mascaras.js" charset="UTF-8"></script>
<SCRIPT type="text/javascript" src="js/MenuCadRC.js" charset="UTF-8"></SCRIPT>
<script language="javascript" src="js/jquery.maskMoney.min.js"	charset="UTF-8"></script>
<!-- <script>
		$(function(){
    	      $("#includedContent").load("/YMPopup/popup.html");
	    });
</script> -->

</HEAD>
<BODY id="bdMenuCadRC">
	<!-- <div id="includedContent" ></div> -->
	<FORM NAME="frm1" ACTION="Controller" METHOD="POST">
	
		<INPUT TYPE="HIDDEN" NAME="action" VALUE="enviacotacaonew"/>
		
		<c:if test="${intranet != 1}">
		<div class="fLeft col1">

				<IFRAME id="garantia" onLoad="javascript:calcHeight();" frameborder="0" name="garantia"
					src="email-calculo-risco-engenharia-rc-garantia.jsp" width="800px" height="55px"	marginheight="10" 
					marginwidth="0" scrolling="no"> </IFRAME>

			</div>
		</c:if>
		<div id="divFormTotal" style="width: 900px; max-width: 900px;" >
		
		<div class="secao style-select">
			<div class="fLeft col1">
				<h1>Dados Básicos</h1>
			</div>

			<br clear="all" /> <br clear="all" />

			<div class="fLeft col1">

				<label onclick="javascript:showLoadingAlign();" for="corretorSel" class="w120">Corretor:*</label>
				<div class="caixa_select_custom chosen-select"
					style="width: 255px">
				
					<select style="width: 282px" name="corretorSel" id="corretorSel" 
						onchange="javascript:alteraCorretor();">
						<option value="">Selecione</option>
						<c:forEach var="corretor" items="${corretores}">
							<option value="<c:out value='${corretor.codCorretor}'/>"
								<c:if test="${CotacaoBean.codCorr == corretor.codCorretor}">
									<c:out value=" selected " /></c:if>>
								<c:out value="${corretor.corretor}" />
							</option>

						</c:forEach>
					</select>
				
					
				</div>
			</div>

			<div class="fLeft margin-col1 ">

				<label>Unidade:*</label>
				<div class="caixa_select_custom">
					
						<select style="width: 271px" name="unidadeSel" id="unidadeSel" >
						<option value="">Selecione</option>
						<c:forEach var="unidade" items="${unidades}">
							<option <c:if test="${CotacaoBean.unidadeNegocioCodigoProdutor == unidade.unidadeNegocioCodigoProdutor}"><c:out value=" selected " /></c:if>
							 value="<c:out value='${unidade.unidadeNegocioCodigoProdutor}'/>">
								<c:out value="${unidade.unidNegocio}-" /><c:out	value="${unidade.nomDepto}" />
							</option>
						</c:forEach>
					</select>
					
				</div>
			</div>


			<br clear="all" />

			<div class="fLeft">

				<label class="w121">E-mail do Solicitante:*</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 210px;" name="EmailContato" maxlength="60" id="EmailContato" />
				</div>
			</div>

			<div class="fLeft margin-col1">

				<label>Telefone:*</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 111px" name="TelefContato" maxlength="15" id="TelefContato" />
				</div>
			</div>

			<div class="fLeft margin-col1">

				<label>Contato:*</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 135px;" name="NomeContato" maxlength="30" id="NomeContato" />
				</div>
			</div>

			<br clear="all" />

			<div class="fLeft col1">

				<label class="w120">Tipo de Cotação:*</label>
				<div class="caixa_select_custom">
					<input type="radio" value="1" name="tpCotacao"
						class="fLeft radioButton" onchange="javascript:alteraTpCotacao();" /><label>Cotação</label>
					<input type="radio" value="2" name="tpCotacao"
						class="fLeft radioButton" onchange="javascript:alteraTpCotacao();" /><label>Recotação</label>
				</div>
			</div>

			<div class="fLeft margin-col1 invisivel" id="divNumCotacao">

				<label class="w97 txtAlignLeft">Nº Cotação:*</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 244px;" name="cCotacao" maxlength="12" id="cCotacao" onblur="javascript:recotacaoAjax();" disabled/>
				</div>
			</div>

			<br clear="all" />

			<div class="fLeft col1">

				<label class="w120">Ramo:*</label>
				<div class="caixa_select_custom">
					
					<select style="width: 290px" name="ramoSel" id="ramoSel" onchange="javascript:changeRamo();">
						<option value="">Selecione</option>
						<c:forEach var="ramo" items="${ramos}">
							<option value="<c:out value='${ramo.codigo}'/>">
								<c:out value="${ramo.nome}" />
							</option>
						</c:forEach>
					</select>
					
				</div>
			</div>

			<div class="fLeft margin-col1 invisivel" id="divModalidade">

				<label class="w97 txtAlignLeft">Modalidade:*</label>
				<div class="caixa_select_custom">
				
					<select style="width: 255px;" name="modalidadeSel" id="modalidadeSel" disabled>
						<option>Selecione</option>
					</select>
					
				</div>
			</div>

			<br clear="all" />

			<div class="fLeft col1">

				<label class="w120">Tipo de Emissão:*</label>
				<div>
				
					<select style="width: 290px" name="TipEmissao" id="TipEmissao" onchange="javascript:alteraTipoEmissao();">
						<option value="">Selecione</option>
						<c:forEach var="emissao" items="${emissoes}">
							<option value="<c:out value='${emissao.codigo}'/>">
								<c:out value='${emissao.nome}' />
							</option>
						</c:forEach>
					</select>
					
				</div>
			</div>

			<div class="fLeft margin-col1 invisivel" id="divNumApol">

				<label class="w97 txtAlignLeft">Nº Apólice:*</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 244px;" name="numApol" id="numApol" maxlength="10" disabled/>
				</div>
			</div>

		</div>

		<div class="secao style-select">
	
			<div class="fLeft col1">
				<h1>
					Dados do Proponente
					</h1>
			</div>

			<br clear="all" /> <br clear="all" />

			<div class="fLeft col1">

				<label class="w120">Tipo de Pessoa:*</label>
				<div class="caixa_select_custom">
					<input type="radio" value="0" name="tipoPessoa"
						class="fLeft radioButton" onchange="javascript:alteraTipoPessoa();" />
						<label>Física</label>
					<input type="radio" value="1" name="tipoPessoa" class="fLeft radioButton"
						onchange="javascript:alteraTipoPessoa();" />
						<label>Jurídica</label>
				</div>
			</div>

			<br clear="all" /> <br clear="all" />

			<div class="fLeft col1">

				<label class="w120" id="lbCpf">CPF:*</label> <label
					class="w120 removeComp" id="lbCnpj">CNPJ:*</label>
				<div class="caixa_select_custom">
					<input type="text" id="cpfCnpj" name="cnpjCpf" readonly="readonly"
						style="width: 170px;" onblur="javascript:verificaCpfCnpj();" oninput="javascript:chama_mascara(this);"/>
				</div>

			</div>

			<div class="fLeft margin-col1">

				<label id="lbNome">Nome:*</label>
				<label id="lbRazaoSocial" class="removeComp" style="margin-left: -37px;">Razão Social:*</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 267px;" name="nomeProp" maxlength="100" id="nomeProp" />
				</div>

			</div>

			<br clear="all" />

			<div class="fLeft col1">

				<label id="lbProfissao" class="w120">Profissão:</label>
				<label id="lbAtivPrincipal" class="w120 removeComp">Atividade Principal:</label>
				<div class="caixa_select_custom">
				
					<select style="width: 290px" id="profissaoRamoAtividade" name="profissaoRamoAtividade">
						<option value="">Selecione</option>
					</select>
					
				</div>

			</div>

			<br clear="all" />

			<div class="fLeft col2">
				<label class="w120">CEP:</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 80px;" name="Cep" id="numCEP"
						onblur="javascript:pesquisaCep();" />
				</div>

			</div>

			<div class="fLeft margin-col1">
				<label class="w83">Endereço:</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 331px;" name="End"
					id="endereco" maxlength="50" readonly="readonly" />
				</div>

			</div>

			<br clear="all" />

			<div class="fLeft col2">
				<label class="w120">Número:</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 80px;" name="Numero" maxlength="10"
						id="numeroEnd" />
				</div>

			</div>

			<div class="fLeft margin-col1">
				<label class="w83">Complemento:</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 331px;" name="Complemento" maxlength="20"
						id="complEnd" />
				</div>

			</div>

			<br clear="all" />
			<div class="fLeft col1">
				<label class="w120">Bairro:</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 282px;" name="Bairro" id="bairro"
						readonly="readonly" maxlength="30"/>
				</div>

			</div>

			<br clear="all" />
			<div class="fLeft col1">

				<label class="w120">Cidade:</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 282px;" name="Cid" id="cidade"
						readonly="readonly" />
				</div>

			</div>

			<div class="fLeft margin-col1">
				<label>UF:</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 50px;" name="cobUF" id="UF"
						readonly="readonly" />
				</div>
			</div>


		</div>


		<div class="secao">

			<div class="fLeft col1">
				<h1>
					Dados da Cotação
					</h1>
			</div>

			<br clear="all" /> <br clear="all" />

			<div class="fLeft margin-col1">

				<label class="w120">Vigência:*</label>
				<div class="caixa_select_custom">
					<input type="text" class="datepicker" style="width: 136px;"
						name="dataVigenciaInicio" id="inicioVig" />
				</div>
			</div>

			<div class="fLeft margin-col1">

				<label>&nbsp;&nbsp;Até: </label>
				<div class="caixa_select_custom">
					<input type="text" class="datepicker" style="width: 136px;"
						name="dataVigenciaFim" id="terminoVig" />
				</div>
			</div>

			<br clear="all" />

			<div class="fLeft margin-col1 invisivel" id="divLmgLmi">

				<label class="w120" id="lbLmg">LMG (R$):*</label> <label class="w120"
					id="lbLmi">LMI (R$):*</label>
				<div class="caixa_select_custom">
					<input type="text" style="width: 136px;" value="" name="VlRisco"
						id="VlRisco" />
				</div>
			</div>

		</div>


		<div class="secao">

			<div class="fLeft col1">
				<h1>Informações adicionais</h1>
			</div>

			<br clear="all" />

			<div class="fLeft col1">
				<div class="caixa_select_custom">
					<label class="w400">Preencher no campo abaixo as
						informações adicionais para a cotação.</label>
				</div>

			</div>

			<br clear="all" />

			<div class="fLeft col3">
				<label class="w120">&nbsp;</label>
				<div class="box_conteiner" style="width:95%;">
					<textarea rows="8" style="width:130%;" name="observ" ></textarea>
				</div>
			</div>

			<br clear="all" /> <label class="w400" style="text-align: left;">*Campos
				de preenchimento obrigatório</label>


		</div>

		<div class="secao" style="border-bottom:0px solid;">

			<div class="fLeft col1">
				<h1>Anexo Gerais</h1>
			</div>

			<br clear="all" />

			<div class="fLeft col1">

				<IFRAME id="frameFile" onLoad="javascript:calcHeight();" style="background:transparent;"
					frameborder="0" name="uploadFile"
					src="UploadFileNovaIdentidadeVisual.jsp" width="800px" height="1px"
					marginheight="10" marginwidth="0" scrolling="no"> </IFRAME>

			</div>

			<br clear="all" /> <label class="w500">É imprescindível
				anexar o questionário para efetivação da cotação e todas as
				documentações pertinentes ao processo.</label> <br clear="all" /> <br
				clear="all" />

			<div class="fLeft col1">
				<label class="w120">&nbsp;</label>
				<div class="caixa_select_custom">
					<input type="button" class="botao-azul" value="Nova cotação" onclick="javascript:novaCotacao();" />
				</div>
			</div>

			<div class="fLeft">
				<div class="caixa_select_custom">
					<input type="button" class="botao-azul" value="Enviar" style="margin-left: 100px;" onclick="javascript:cadastraCotacao();" />
				</div>
			</div>

		</div>
		
		</div>
				
	</FORM>
	<form action="Controller" method="POST" NAME="frm2">
		<input type="HIDDEN" name="action" value="menucadrc" /> <input
			type="HIDDEN" name="numSusep"
			value="<c:out value="${CotacaoBean.numSusep}"/>" /> <input
			type="HIDDEN" name="intranet" value="<c:out value="${intranet}"/>" />
	</form>
	<script type="text/javascript">
		var _gaq = _gaq || [];
		_gaq.push([ '_setAccount', 'UA-30723727-1' ]);
		_gaq.push([ '_trackPageview', '/APP/CotacaoMulti/MenuCadRC.jsp' ]);

		(function() {
			var ga = document.createElement('script');
			ga.type = 'text/javascript';
			ga.async = true;
			ga.src = ('https:' == document.location.protocol ? 'https://ssl'
					: 'http://www')
					+ '.google-analytics.com/ga.js';
			var s = document.getElementsByTagName('script')[0];
			s.parentNode.insertBefore(ga, s);
		})();
		
		
	</script>
	
	<br clear="all" />
	<br clear="all" />
	<br clear="all" />
	
</BODY>

</HTML>
