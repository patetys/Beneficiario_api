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
			<TD class="boxTitulo" width="354">Ramo 710 - Riscos Diversos -
			Anúncios Luminosos/Antenas</TD>
		</TR>
		<TR valign="top">
			<TD width="354" height="137">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="22" width="171">Modalidade</TD><TD class="boxTexto" height="22" width="238">
						<TABLE>
							<TBODY><TR>
								<TD class="boxTexto" height="12" width="60"><INPUT type="radio" name="Modalidade" value="0" <c:if test="${bean.modalidade =='0' || bean.modalidade ==null }"> checked </c:if>="">Anúncio</TD>
								<TD class="boxTexto" height="12" width="60"><INPUT type="radio" name="Modalidade" value="1" <c:if test="${bean.modalidade =='1'}"> checked </c:if>="">Antena</TD>
							</TR>
						</TBODY></TABLE>						
						</TD>
						
						<TD class="boxTexto" height="22" width="1"></TD>
					</TR>

					<TR>
						<TD class="boxTexto" height="25" width="171">Altura</TD><TD class="boxTexto" height="25" width="238"><INPUT type="text" class="boxInput" name="Altura" size="14" maxlength="15" style="width: 150px" onkeyup="javascript:soNumeroMoeda('Altura')" value='<c:out value="${bean.altura}" />'></TD>
						
						<TD class="boxTexto" height="22" width="1"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="25" width="171">Área M2 </TD><TD class="boxTexto" height="25" width="238"><INPUT type="text" class="boxInput" name="AreaM2" size="14" maxlength="15" style="width: 150px" onkeyup="javascript:soNumeroMoeda('AreaM2')" value='<c:out value="${bean.areaM2}" />'></TD>
						
						<TD class="boxTexto" height="22" width="1"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="25" width="171">Luminosos ?</TD><TD class="boxTexto" height="25" width="238">
						<TABLE>
							<TBODY><TR>
								<TD class="boxTexto" height="12" width="60"><INPUT type="radio" name="Luminoso" value="S" <c:if test="${bean.luminoso =='S' || bean.luminoso ==null }"> checked </c:if>="">Sim</TD>
								<TD class="boxTexto" height="12" width="60"><INPUT type="radio" name="Luminoso" value="N" <c:if test="${bean.luminoso =='N'}"> checked </c:if>="">Não</TD>
							</TR>
						</TBODY></TABLE>
						
						</TD>
						
						<TD class="boxTexto" height="22" width="1"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="40" width="173">Sinistralidade dos
						últimos 5 anos</TD><TD class="boxTexto" height="40" width="257"><INPUT type="text" class="boxInput" name="NumSin5anos" size="14" maxlength="5" style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')" value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						
						<TD class="boxTexto" height="40" width="1"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="51" width="170">
			<A href="javascript:voltaPrincipal(document.frm1);" class="cbutton">VOLTAR</A>
		</TD>
		<TD class="boxTexto" align="center" height="51" width="178">
			<a name="btnEnviar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">PRÓXIMO</a>
		</TD>
	</TR>
</TABLE>


</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div7108Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
