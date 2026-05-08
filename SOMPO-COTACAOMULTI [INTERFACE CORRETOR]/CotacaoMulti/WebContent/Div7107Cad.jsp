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
<c:set var="sompo" value="${sessionScope['sompo']}" />
<c:if test="${sompo == 'Sompo'}">
	<link rel="stylesheet" href="css/sompo_yasuda.css" type="text/css">
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<link rel="stylesheet" href="css/yasuda.css" type="text/css">
</c:if>
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
<TABLE border="0" cellpadding="5" cellspacing="0" class="tabelaAction">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="431">Ramo 710 - Riscos Diversos -
			Valores</TD>
		</TR>
		<TR valign="top">
			<TD height="166" width="431">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"
				height="303">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="18" colspan="2" valign="bottom">Ocupação/Atividade:</TD>
						<TD class="boxTexto" height="22" width="1"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" colspan="2"><TEXTAREA onkeyup="javascript:maxCaracteres(document.frm1.DscOcup,495)"
							name="DscOcup" rows="3" cols="61" style="width: 425px"
							style="color: #4686BE"><c:if test='${bean.dscOcup!=null}'><c:out value='${bean.dscOcup}' /></c:if></TEXTAREA></TD>
						<TD class="boxTexto" height="22" width="1"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="173" valign="bottom" height="25">Valores:</TD>
						<TD class="boxTexto" width="257" height="25"></TD>
						<TD class="boxTexto" width="1" height="25"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="173" height="30"><INPUT type="radio"
							name="Valores" value="1"
							<c:if test="${bean.valores=='1' || bean.valores==null }"> checked </c:if>>Em trânsito</TD>
						<TD class="boxTexto" width="257" height="30"><INPUT type="radio"
							name="Valores" value="2"
							<c:if test="${bean.valores=='2' }"> checked </c:if>>Interior do estabelecimento</TD>
						<TD class="boxTexto" width="1" height="30"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="400" colspan="2" valign="bottom"
							height="32">Extensão da cobertura a pagamentos de salários?</TD>
						<TD class="boxTexto" width="1" height="32"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="173" valign="top" height="27">
						<TABLE border="0" cellpadding="0" cellspacing="0"
							bgcolor="#FFFFFF">
							<TBODY>
								<TR align="left">
									<TD class="boxTexto" width="50" align="left"><INPUT
										type="radio" name="PagtoSalar" value="S"
										<c:if test="${bean.pagtoSalar=='S' || bean.pagtoSalar==null }"> checked </c:if>>Sim</TD>
									<TD class="boxTexto" width="50" align="left"><INPUT
										type="radio" name="PagtoSalar" value="N"
										<c:if test="${bean.pagtoSalar=='N' }"> checked </c:if>>Não</TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
						<TD class="boxTexto" width="257" height="27"></TD>
						<TD class="boxTexto" width="1" height="27"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="173" height="39">Sinistralidade dos
						últimos 5 anos</TD>
						<TD class="boxTexto" width="257" height="39"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" width="1" height="39"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="right" width="173" height="50">
			<A href="javascript:voltaPrincipal(document.frm1);" class="cbutton">VOLTAR</A>
		</TD>
		<TD class="boxTexto" align="center" width="257" height="50">
			<a name="btnEnviar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">PRÓXIMO</a>
		</TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div7107Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
