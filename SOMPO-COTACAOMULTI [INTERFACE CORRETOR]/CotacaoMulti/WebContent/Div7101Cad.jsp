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
			<TD class="boxTitulo" width="421">Ramo 710 - Riscos Diversos -
			Exposição</TD>
		</TR>
		<TR valign="top">
			<TD width="421" height="287">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="18" colspan="2" valign="bottom">Descrição
						por Equipamento com Ano/Modelo, Nacionalidade, Utilização e Valor
						Atual:</TD>
						<TD class="boxTexto" height="22" width="1"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" colspan="2"><TEXTAREA onkeyup="javascript:maxCaracteres(document.frm1.DscObjeto,495)"
							name="DscObjeto" rows="3" cols="61" style="width: 425px"
							style="color: #4686BE"><c:if test='${bean.dscObjeto!=null}'><c:out value='${bean.dscObjeto}' /></c:if></TEXTAREA></TD>
						<TD class="boxTexto" height="22" width="1"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" valign="bottom" colspan="2" height="41">Local
						e período da Exposição</TD>
						<TD class="boxTexto" width="1" height="41"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" colspan="2" height="64"><TEXTAREA onkeyup="javascript:maxCaracteres(document.frm1.DscFeira,495)"
							name="DscFeira" rows="3" cols="61" style="width: 425px"
							style="color: #4686BE"><c:if test='${bean.dscFeira!=null}'><c:out value='${bean.dscFeira}' /></c:if></TEXTAREA></TD>
						<TD class="boxTexto" height="64" width="1"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="173" height="56">Com Transporte?</TD>
						<TD class="boxTexto" width="257" height="56">
						<TABLE border="0" cellpadding="0" cellspacing="0"
							bgcolor="#FFFFFF">
							<TBODY>
								<TR align="left">
									<TD class="boxTexto" width="50" align="left"><INPUT
										type="radio" name="ComTransp" value="01"
										<c:if test="${bean.comTransp=='01' || bean.comTransp==null }"> checked </c:if>>Sim</TD>
									<TD class="boxTexto" width="50" align="left"><INPUT
										type="radio" name="ComTransp" value="02"
										<c:if test="${bean.comTransp=='02' }"> checked </c:if>>Não</TD>
								</TR>
							</TBODY>
						</TABLE>
						<TD class="boxTexto" width="1" height="56"></TD>
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
		<TD align="right" width="166" height="56">
			<A href="javascript:voltaPrincipal(document.frm1);" class="cbutton">VOLTAR</A>
		</TD>
		<TD class="boxTexto" align="center" width="264" height="56">
			<a name="btnEnviar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">PRÓXIMO</a>
		</TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div7101Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
