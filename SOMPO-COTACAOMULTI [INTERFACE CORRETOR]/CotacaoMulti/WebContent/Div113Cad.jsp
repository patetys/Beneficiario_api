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
			<TD class="boxTitulo" width="437">Ramo 113 - Multi-Seguro Residêncial</TD>
		</TR>
		<TR valign="top">
			<TD height="128" width="437">

			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="18" width="176">Construção Alvenaria?</TD>
						<TD class="boxTexto" height="18" width="260">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="ConstAlv" value="S"
									<c:if test="${bean.constAlv =='S' || bean.constAlv ==null }"> checked </c:if>>Sim</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="ConstAlv" value="N"
									<c:if test="${bean.constAlv =='N'}"> checked </c:if>>Não</TD>
							</TR>
						</TABLE>
						</TD>
						<TD class="boxTexto" height="22" width="10"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" width="176">Tipo de Moradia</TD>
						<TD class="boxTexto" height="18" width="260">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="TpMoradia" value="1"
									<c:if test="${bean.tpMoradia==1 || bean.tpMoradia==null}"> checked </c:if>>Casa</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="TpMoradia" value="2"
									<c:if test="${bean.tpMoradia ==2 }"> checked </c:if>>Apartamento</TD>
							</TR>
						</TABLE>
						</TD>
						<TD class="boxTexto" height="22" width="10"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" width="176">Modalidade da Moradia</TD>
						<TD class="boxTexto" height="22" width="260">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="TpUtilizacao" value="1"
									<c:if test="${bean.tpUtilizacao ==1 || bean.tpMoradia==null}"> checked </c:if>>Habitual</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="TpUtilizacao" value="2"
									<c:if test="${bean.tpUtilizacao ==2 }"> checked </c:if>>Eventual</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="TpUtilizacao" value="3"
									<c:if test="${bean.tpUtilizacao ==3 }"> checked </c:if>>Desocupada</TD>
							</TR>
						</TABLE>
						</TD>
						<TD class="boxTexto" height="22" width="10"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="176" height="42">Sinistralidade dos
						últimos 5 anos</TD>
						<TD class="boxTexto" height="42" width="260"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" height="42" width="10"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="51" width="212">
			<A href="javascript:voltaPrincipal(document.frm1);" class="cbutton">VOLTAR</A>
		</TD>
		<TD class="boxTexto" align="center" height="51" width="219">
			<a name="btnEnviar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">PRÓXIMO</a>
		</TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div113Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
