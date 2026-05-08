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
	campoNumerico[0] = "NrAlunos";
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
			<TD class="boxTitulo" width="366">Ramo 510 - Responsabilidade Civil -
			Estabelecimento de Ensino</TD>
		</TR>
		<TR valign="top">
			<TD height="147" width="366">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="30" width="181">N.º de alunos:</TD>
						<TD class="boxTexto" height="30" width="158"><INPUT type="text"
							class="boxInput" name="NrAlunos" size="14" maxlength="4"
							style="width: 50px" onkeyup="javascript:soNumero('NrAlunos')"
							value="<c:if test='${bean.nrAlunos!=null}'><c:out value='${bean.nrAlunos}'/></c:if>"></TD>
						<TD class="boxTexto" height="30" width="30"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" valign="bottom" height="26" width="181"><INPUT
							type="checkbox" name="Laboratorio" value="S"
							<c:if test="${bean.laboratorio=='S' }"> checked </c:if>>Laboratórios</TD>
						<TD class="boxTexto" valign="bottom" height="26" width="158"><INPUT
							type="checkbox" name="IntenExtern" value="S"
							<c:if test="${bean.intenExtern=='S' }"> checked </c:if>>Internato
						e/ou Externato</TD>
						<TD class="boxTexto" height="26" width="30"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="26" valign="top" width="181"><INPUT
							type="checkbox" name="Piscina" value="S"
							<c:if test="${bean.piscina=='S' }"> checked </c:if>>Piscina</TD>
						<TD class="boxTexto" height="26" valign="top" width="158"><INPUT
							type="checkbox" name="Elevador" value="S"
							<c:if test="${bean.elevador=='S' }"> checked </c:if>>Elevador</TD>
						<TD class="boxTexto" height="26" width="30"></TD>
					</TR>
					<TR align="left">
						<TD class="boxTexto" colspan="2" height="51">Atividade realizada
						fora do estabelecimento?
						<TABLE border="0" cellpadding="0" cellspacing="0"
							bgcolor="#FFFFFF">
							<TBODY>
								<TR align="left">
									<TD class="boxTexto" width="53" align="left"><INPUT
										type="radio" name="AtivForaEstab" value="S"
										<c:if test="${bean.ativForaEstab=='S' || bean.ativForaEstab==null }"> checked </c:if>>Sim</TD>
									<TD class="boxTexto" width="57" align="left"><INPUT
										type="radio" name="AtivForaEstab" value="N"
										<c:if test="${bean.ativForaEstab=='N' }"> checked </c:if>>Não</TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
						<TD class="boxTexto" height="51" width="30"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="27" width="181">Sinistralidade dos
						últimos 5 anos</TD>
						<TD class="boxTexto" height="27" width="158"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" height="27" width="30"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="49" width="177"><A
			href="javascript:voltaPrincipal(document.frm1);"> <IMG
			name="consiste" src="images/botaoVoltar.gif" border="0"></A></TD>
		<TD class="boxTexto" align="center" height="49" width="185"><INPUT
			type="image" name="btnEnviar" src="images/botaoProximo.gif"
			onclick="javascript:return ValidarCamposEnviar(this);"></TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div5107Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
