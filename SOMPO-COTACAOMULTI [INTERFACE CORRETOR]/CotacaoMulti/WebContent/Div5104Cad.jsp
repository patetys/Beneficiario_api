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
	campoNumerico = new CriaArray(2);
	campoNumerico[0] = "NrSocios";
	campoNumerico[1] = "NumSin5anos";
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
			<TD class="boxTitulo" width="377">Ramo 510 - Resposanbilidade Civil -
			Clubes</TD>
		</TR>
		<TR valign="top">
			<TD width="377" height="87">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="27" width="100">Nº de Sócios :</TD>
						<TD class="boxTexto" height="27" width="150"><INPUT type="text"
							class="boxInput" name="NrSocios" size="14" maxlength="3"
							style="width: 50px" onkeyup="javascript:soNumero('NrSocios')"
							value="<c:if test='${bean.nrSocios!=null}'><c:out value='${bean.nrSocios}'/></c:if>"></TD>
						<TD class="boxTexto" height="27" width="0"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="25" width="169"><INPUT
							type="checkbox" name="RestSimilares" value="1"
							<c:if test="${bean.restSimilares=='1' }"> checked </c:if>>Restaurantes
						ou Similares</TD>
						<TD class="boxTexto" height="25" width="233"><INPUT
							type="checkbox" name="InstacEsport" value="1"
							<c:if test="${bean.instacEsport=='1' }"> checked </c:if>>Instalações
						esportivas</TD>
						<TD class="boxTexto" height="25" width="0"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="30" width="169">Sinistralidade dos
						últimos 5 anos</TD>
						<TD class="boxTexto" height="30" width="233"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" height="30" width="0"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="44" width="188"><A
			href="javascript:voltaPrincipal(document.frm1);"> <IMG
			name="consiste" src="images/botaoVoltar.gif" border="0"></A></TD>
		<TD align="center" height="44" width="178"><INPUT type="image" name="btnEnviar"
			src="images/botaoProximo.gif"
			onclick="javascript:return ValidarCamposEnviar(this);"></TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div5104Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
