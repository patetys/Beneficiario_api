<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="bean" value="${sessionScope['CotacaoBean']}" />

<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<link rel="stylesheet" href="css/yasuda.css" type="text/css">
<TITLE></TITLE>
</HEAD>

<BODY>
<script language="JavaScript" type="text/javascript"
	src="js/consistencias.js" charset="UTF-8"></script>
<script language="JavaScript">

function ValidarCamposEnviar(Form) {

//Verifica os Campos não preenchidos
	if(!Validanaopreenchido(document.frm1)){
		return false;
	}

//Valida os campos de valores
	campoNumericoMoeda = new CriaArray(1);
	campoNumericoMoeda[0] = "FatUltim12";
	if(!numMoedaCampos(document.frm1,campoNumericoMoeda)){
		return false;
	}

	campoNumerico = new CriaArray(1);
	campoNumerico[0] = "NumSin5anos";
	if(!numCampos(document.frm1,campoNumerico)){
		return false;
	}

 	document.frm1.submit();
	return(true);
}

function soNumero(n){
	validaCampoNumerico(n);
}
function soNumeroMoeda(n){
	validaCampoNumericoMoeda(n);
}

</script>

<form NAME=frm1 ACTION="Controller" METHOD="POST"><INPUT TYPE="HIDDEN"
	NAME="action" VALUE="msdetalhe">
<P></P>
<TABLE border="0" cellpadding="5" cellspacing="0" bgcolor="#8FBCE4">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="482">Ramo 510 - Resposanbilidade Civil -
			Operações</TD>
		</TR>
		<TR valign="top">
			<TD height="77" width="482">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="25" valign="bottom" width="200">Faturamento
						últimos 12 meses : R$</TD>
						<TD class="boxTexto" height="25" valign="bottom" width="267"><INPUT
							type="text" class="boxInput" name="FatUltim12" size="14"
							maxlength="23" style="width: 150px"
							onkeyup="javascript:soNumeroMoeda('FatUltim12')"
							value="<c:if test='${bean.fatUltim12!=null && bean.fatUltim12!="0,00"}'><c:out value='${bean.fatUltim12}'/></c:if>"> (Ex.: 9.999,99)</TD>
						<TD class="boxTexto" height="25" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="24" width="200">Ocupação/Atividade :</TD>
						<TD class="boxTexto" height="24" width="267"><INPUT type="text"
							class="boxInput" name="DscOcupacao" size="14" maxlength="59"
							style="width: 250px"
							value="<c:if test='${bean.dscOcupacao!=null}'><c:out value='${bean.dscOcupacao}'/></c:if>"></TD>
						<TD class="boxTexto" height="24" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="24" width="200">RC Empregador ?</TD>
						<TD class="boxTexto" height="24" width="267">
						 <TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="RcEmpregador" value="S"
									<c:if test="${bean.rcEmpregador =='S' || bean.rcEmpregador ==null }"> checked </c:if>>
								Sim</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="RcEmpregador" value="N"
									<c:if test="${bean.rcEmpregador =='N'}"> checked </c:if>> Não</TD>
							</TR>
						 </TABLE>						
						</TD>
						<TD class="boxTexto" height="24" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="24" width="200">RC Riscos Contingentes de Veiculos ?</TD>
						<TD class="boxTexto" height="24" width="267">
						 <TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="RcRiscoVeic" value="S"
									<c:if test="${bean.rcRiscoVeic =='S' || bean.rcRiscoVeic ==null }"> checked </c:if>>
								Sim</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="RcRiscoVeic" value="N"
									<c:if test="${bean.rcRiscoVeic =='N'}"> checked </c:if>> Não</TD>
							</TR>
						 </TABLE>					
						
						</TD>
						<TD class="boxTexto" height="24" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="24" width="200">RC Produtos?</TD>
						<TD class="boxTexto" height="24" width="267">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="RcProdutos" value="S"
									<c:if test="${bean.rcProdutos =='S' || bean.rcProdutos ==null }"> checked </c:if>>
								Sim</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="RcProdutos" value="N"
									<c:if test="${bean.rcProdutos =='N'}"> checked </c:if>> Não</TD>
							</TR>
						</TABLE>						
						</TD>
						<TD class="boxTexto" height="24" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="24" valign="top" width="200">Sinistralidade
						dos últimos 5 anos :</TD>
						<TD class="boxTexto" height="24" valign="top" width="267"><INPUT
							type="text" class="boxInput" name="NumSin5anos" size="14"
							maxlength="5" style="width: 50px"
							onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" height="24" width="5"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="38" width="235"><A
			href="javascript:voltaPrincipal(document.frm1);"><IMG name="consiste"
			src="images/botaoVoltar.gif" border="0"></A></TD>
		<TD class="boxTexto" align="center" height="38" width="241"><INPUT
			type="image" name="btnEnviar" src="images/botaoProximo.gif"
			onclick="javascript:return ValidarCamposEnviar(this);"></TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div5102Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
