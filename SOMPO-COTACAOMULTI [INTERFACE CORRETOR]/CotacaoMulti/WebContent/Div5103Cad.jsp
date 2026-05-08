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
	campoNumerico[0] = "NrFuncTerc";
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
			<TD class="boxTitulo" width="392">Ramo 510 - Resposanbilidade Civil -
			Prestação de Serviços</TD>
		</TR>
		<TR valign="top">
			<TD height="121" width="392">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="40" width="184">Nº de func. que
						prestam serviço em locais de terceiros:</TD>
						<TD class="boxTexto" height="40" width="200"><INPUT type="text"
							class="boxInput" name="NrFuncTerc" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NrFuncTerc')"
							value="<c:if test='${bean.nrFuncTerc!=null}'><c:out value='${bean.nrFuncTerc}'/></c:if>"></TD>
						<TD class="boxTexto" height="40" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="25" width="184">Tipo de Serviço :</TD>
						<TD class="boxTexto" height="25" width="200">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="90"><INPUT type="radio"
									name="TpServico" value="1"
									<c:if test="${bean.tpServico=='1' || bean.tpServico==null }"> checked </c:if>>Manutenção</TD>
								<TD class="boxTexto" height="18" width="80"><INPUT type="radio"
									name="TpServico" value="2"
									<c:if test="${bean.tpServico=='2' }"> checked </c:if>>Limpeza</TD>
							</TR>
						</TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="25" width="184">RC Empregados:</TD>
						<TD class="boxTexto" height="25" width="200">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="91"><INPUT type="radio"
									name="RcEmpregados" value="S"
									<c:if test="${bean.rcEmpregados =='S' || bean.rcEmpregados ==null }"> checked </c:if>>
								Sim</TD>
								<TD class="boxTexto" height="18" width="80"><INPUT type="radio"
									name="RcEmpregados" value="N"
									<c:if test="${bean.rcEmpregados =='N'}"> checked </c:if>> Não</TD>
							</TR>
						</TABLE>						
						
						</TD>
						<TD width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="40" width="184">Sinistralidade dos
						últimos 5 anos</TD>
						<TD class="boxTexto" height="40" width="200"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" height="40" width="12"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="43" width="186"><A
			href="javascript:voltaPrincipal(document.frm1);"> <IMG
			name="consiste" src="images/botaoVoltar.gif" border="0"></A></TD>
		<TD class="boxTexto" align="center" height="43" width="200"><INPUT
			type="image" name="btnEnviar" src="images/botaoProximo.gif"
			onclick="javascript:return ValidarCamposEnviar(this);"></TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div5103Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
