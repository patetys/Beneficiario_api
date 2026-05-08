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
			<TD class="boxTitulo" width="413" height="22">Ramo 710 - Riscos Diversos - Roubo All
			Risk's</TD>
		</TR>
		<TR valign="top">
			<TD width="413" height="137">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="18" width="400" colspan="2">Descrição
						por Objeto com Ano/Modelo, Nacionalidade, Ultilização e Valor
						Atual</TD>
						<TD class="boxTexto" height="22" width="3"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" width="60" colspan="2"><TEXTAREA onkeyup="javascript:maxCaracteres(document.frm1.DscObjeto,495)"
							name="DscObjeto" rows="3" cols="61" style="width: 436px"
							style="color: #4686BE"><c:out value="${bean.dscObjeto}" /></TEXTAREA></TD>
						<TD class="boxTexto" height="22" width="3"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="60" height="34">Cobertura :</TD>
						<TD class="boxTexto" width="250" height="34">
						<TABLE>
							<TR>
								<TD class="boxTexto" width="120" height="27"><INPUT type="radio"
									name="CodCobertura" value="1"
									<c:if test="${bean.codCobertura==1 || bean.codCobertura==null}"> checked </c:if>>Território
								Nacional</TD>
								<TD class="boxTexto" width="120" height="27"><INPUT type="radio"
									name="CodCobertura" value="2"
									<c:if test="${bean.codCobertura==2}"> checked </c:if>>Todo o
								Mundo</TD>
							</TR>
						</TABLE>
						</TD>
						<TD class="boxTexto" width="3" height="34"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="176" height="36">Sinistralidade dos
						últimos 5 anos</TD>
						<TD class="boxTexto" width="265" height="36"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" width="3" height="36"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="46" width="219">
			<A href="javascript:voltaPrincipal(document.frm1);" class="cbutton">VOLTAR</A>
		</TD>
		<TD class="boxTexto" align="center" height="46" width="229">
			<a name="btnEnviar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">PRÓXIMO</a>
		</TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div1501Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
