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
//	if(!Validanaopreenchido(document.frm1)){
//		return false;
//	}

//Valida os campos de valores
	campoNumerico = new CriaArray(2);
	campoNumerico[0] = "AreaExclEstac";
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
			<TD class="boxTitulo" width="458">Ramo 510 - Resposanbilidade Civil -
			Guarda de Veículos</TD>
		</TR>
		<TR valign="top">
			<TD width="458" height="187">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"
				width="465" height="204">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="22" width="237"><INPUT
							type="checkbox" name="OficEquip" value="S"
							<c:if test="${bean.oficEquip=='S' }"> checked </c:if>>Oficina com
						equipamentos</TD>
						<TD class="boxTexto" height="22" width="219"><INPUT
							type="checkbox" name="Rotativo" value="S"
							<c:if test="${bean.rotativo=='S' }"> checked </c:if>>Rotativo
						<TD class="boxTexto" height="22" width="9"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" width="237"><INPUT
							type="checkbox" name="Condominio" value="S"
							<c:if test="${bean.condominio=='S' }"> checked </c:if>="">Condomínio</TD>
						<TD class="boxTexto" height="18" width="219"><INPUT
							type="checkbox" name="Elevador" value="S"
							<c:if test="${bean.elevador=='S' }"> checked </c:if>>Com elevador</TD>
						<TD class="boxTexto" height="22" width="9"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" valign="top" width="237" height="32"><INPUT
							type="checkbox" name="Mensalista" value="S"
							<c:if test="${bean.mensalista=='S' }"> checked </c:if>>Mensalista</TD>
						<TD class="boxTexto" valign="top" width="219" height="32"><INPUT
							type="checkbox" name="Posto" value="S"
							<c:if test="${bean.posto=='S' }"> checked </c:if>>Posto</TD>
						<TD class="boxTexto" width="9" height="32"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" valign="top" colspan="2" height="29">Área
						exclusiva para estacionamento de veículos: <INPUT type="text"
							class="boxInput" name="AreaExclEstac" size="14" maxlength="5"
							style="width: 50px"
							onkeyup="javascript:soNumero('AreaExclEstac')"
							value="<c:if test='${bean.areaExclEstac!=0}'><c:out value='${bean.areaExclEstac}'/></c:if>">
						m<FONT style='vertical-align: text-top; font-size: 6pt'>2</FONT></TD>
						<TD class="boxTexto" width="9" height="29"></TD>
					</TR>
					<TR>
						<TD class="boxTitulo" bgcolor="#8FBCE4" width="237" height="20">Tipo
						de Cobertura:</TD>
						<TD class="boxTitulo" bgcolor="#8FBCE4" width="219" height="20"></TD>
						<TD class="boxTitulo" bgcolor="#8FBCE4" width="9" height="20"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="237" height="36"><INPUT type="radio"
							name="TpCobertura" value="1"
							<c:if test="${bean.tpCobertura=='1' || bean.tpCobertura==null }"> checked </c:if>>Global
						(Colisão, Incêndio e Roubo)</TD>
						<TD class="boxTexto" width="219" height="36"><INPUT type="radio"
							name="TpCobertura" value="2"
							<c:if test="${bean.tpCobertura=='2' }"> checked </c:if>>Exclusivamente
						Incêndio e Roubo</TD>
						<TD class="boxTexto" width="9" height="36"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="237" height="34">Sinistralidade dos
						últimos 5 anos</TD>
						<TD class="boxTexto" width="219" height="34"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" width="9" height="34"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="43" width="237"><A
			href="javascript:voltaPrincipal(document.frm1);"> <IMG
			name="consiste" src="images/botaoVoltar.gif" border="0"></A></TD>
		<TD class="boxTexto" align="center" height="43" width="232"><INPUT
			type="image" name="btnEnviar" src="images/botaoProximo.gif"
			onclick="javascript:return ValidarCamposEnviar(this);"></TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div5106Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
