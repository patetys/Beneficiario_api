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
	campoNumerico = new CriaArray(3);
	campoNumerico[0] = "NrVisit";
	campoNumerico[1] = "NrExpositores";
	campoNumerico[2] = "NumSin5anos";
	
	if(!numCampos(document.frm1,campoNumerico)){
		return false;
	}

 	document.frm1.submit();
	return(true);
}

//Só permite a digitação de numeros nos campos
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
			<TD class="boxTitulo" width="381">Ramo 510 - Responsabilidade Civil -
			Exposição e Feira de Amostra</TD>
		</TR>
		<TR valign="top">
			<TD height="196" width="381">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="30" width="179">N.º de visitantes:</TD>
						<TD class="boxTexto" height="30" width="200"><INPUT type="text"
							class="boxInput" name="NrVisit" size="14" maxlength="4"
							style="width: 50px" onkeyup="javascript:soNumero('NrVisit')"
							value="<c:if test='${bean.nrVisit!=null}'><c:out value='${bean.nrVisit}'/></c:if>"></TD>
						<TD class="boxTexto" height="30" width="14"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="37" width="179"><INPUT type="radio"
							name="TpLocal" value="1"
							<c:if test="${bean.tpLocal=='1' || bean.tpLocal==null }"> checked </c:if>>Local
						fechado</TD>
						<TD class="boxTexto" height="37" width="200">
						<TABLE border="0" cellpadding="0" cellspacing="0"
							bgcolor="#FFFFFF">
							<TBODY>
								<TR align="left">
									<TD class="boxTexto" align="left" width="100"><INPUT
										type="radio" name="TpLocal" value="2"
										<c:if test="${bean.tpLocal=='2' }"> checked </c:if>> Local
									aberto</TD>
								</TR>
							</TBODY>
						</TABLE>
						<TD class="boxTexto" height="37" width="14"></TD>
					</TR>
					<TR align="left">
						<TD class="boxTexto" valign="bottom" height="22" width="179">Restaurante?</TD>
						<TD class="boxTexto" align="left" valign="bottom" height="22" width="200">
						<TABLE border="0" cellpadding="0" cellspacing="0"
							bgcolor="#FFFFFF">
							<TBODY>
								<TR align="left">
									<TD class="boxTexto" width="50" align="left"><INPUT
										type="radio" name="Restaurante" value="S"
										<c:if test="${bean.restaurante=='S' || bean.restaurante==null }"> checked </c:if>>Sim</TD>
									<TD class="boxTexto" width="50" align="left"><INPUT
										type="radio" name="Restaurante" value="N"
										<c:if test="${bean.restaurante=='N' }"> checked </c:if>>Não</TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
						<TD class="boxTexto" height="22" width="14"></TD>
					</TR>
					<TR align="left">
						<TD class="boxTexto" height="25" width="179">RC Cruzada?</TD>
						<TD class="boxTexto" align="left" height="25" width="200">
						<TABLE border="0" cellpadding="0" cellspacing="0"
							bgcolor="#FFFFFF">
							<TBODY>
								<TR align="left">
									<TD class="boxTexto" width="50" align="left"><INPUT
										type="radio" name="RcCruzada" value="S"
										<c:if test="${bean.rcCruzada=='S' || bean.rcCruzada==null }"> checked </c:if>>Sim</TD>
									<TD class="boxTexto" width="50" align="left"><INPUT
										type="radio" name="RcCruzada" value="N"
										<c:if test="${bean.rcCruzada=='N' }"> checked </c:if>>Não</TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
						<TD class="boxTexto" height="25" width="14"></TD>
					</TR>
					<TR align="left">
						<TD class="boxTexto" height="43" width="179">N.º de expositores:</TD>
						<TD class="boxTexto" align="left" height="43" width="200"><INPUT
							type="text" class="boxInput" name="NrExpositores" size="14"
							maxlength="4" style="width: 50px"
							onkeyup="javascript:soNumero('NrExpositores')"
							value="<c:if test='${bean.nrExpositores!=null}'><c:out value='${bean.nrExpositores}'/></c:if>"></TD>
						<TD class="boxTexto" height="43" width="14"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="32" width="179">Sinistralidade dos
						últimos 5 anos</TD>
						<TD class="boxTexto" height="32" width="200"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" height="32" width="14"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="41" width="177"><A
			href="javascript:voltaPrincipal(document.frm1);"> <IMG
			name="consiste" src="images/botaoVoltar.gif" border="0"></A></TD>
		<TD class="boxTexto" align="center" height="41" width="192"><INPUT
			type="image" name="btnEnviar" src="images/botaoProximo.gif"
			onclick="javascript:return ValidarCamposEnviar(this);"></TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div5109Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
