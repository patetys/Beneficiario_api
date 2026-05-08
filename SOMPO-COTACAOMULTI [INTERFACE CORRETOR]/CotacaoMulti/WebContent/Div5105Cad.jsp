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
	campoNumericoMoeda = new CriaArray(1);
	campoNumericoMoeda[0] = "AreaConstrucao";
	if(!numMoedaCampos(document.frm1,campoNumericoMoeda)){
		return false;
	}
	
	campoNumerico = new CriaArray(4);
	campoNumerico[0] = "NrPavimentos";
	campoNumerico[1] = "ElevNrAndars";
	campoNumerico[2] = "ElevCapPessoa";
	campoNumerico[3] = "NumSin5anos";
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

function habilitaCampo(n,m){
	camposHabilita(document.frm1,n,m);
}
</script>

<form NAME=frm1 ACTION="Controller" METHOD="POST"><INPUT TYPE="HIDDEN"
	NAME="action" VALUE="msdetalhe">
<P></P>
<TABLE border="0" cellpadding="5" cellspacing="0" bgcolor="#8FBCE4">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="368">Ramo 510 - Resposanbilidade Civil -
			Condomínios, Proprietários e Locatários</TD>
		</TR>
		<TR valign="top">
			<TD height="275" width="368">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="18" valign="top" width="173"><INPUT
							type="radio" name="TpMoradia" value="1"
							<c:if test="${bean.tpMoradia=='1' || bean.tpMoradia==null }"> checked </c:if>>Comercial</TD>
						<TD class="boxTexto" height="18" valign="top" width="160"><INPUT
							type="radio" name="TpMoradia" value="2"
							<c:if test="${bean.tpMoradia=='2' }"> checked </c:if>>Residêncial</TD>
						<TD class="boxTexto" height="22" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" valign="top" width="173"><INPUT
							type="radio" name="TpMoradia" value="3"
							<c:if test="${bean.tpMoradia=='3' }"> checked </c:if>>Misto</TD>
						<TD class="boxTexto" height="18" valign="top" width="160"><INPUT
							type="radio" name="TpMoradia" value="4"
							<c:if test="${bean.tpMoradia=='4' }"> checked </c:if>>Escritório/Consultório

						
						<TD class="boxTexto" height="22" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" valign="top" height="35" colspan="2"><INPUT type="radio" name="TpMoradia" value="5"
							<c:if test="${bean.tpMoradia=='5' }"> checked </c:if>>Apart-Hotéis/Flats
						Residências</TD>
						<TD class="boxTexto" height="35" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="21" width="173">Área construída m<FONT style="vertical-align: text-top;font-size: 6pt;">2</FONT>:</TD>
						<TD class="boxTexto" height="21" width="160"><INPUT type="text"
							class="boxInput" name="AreaConstrucao" size="12" maxlength="5"
							style="width: 50px"
							onkeyup="javascript:soNumeroMoeda('AreaConstrucao')"
							value="<c:if test='${bean.areaConstrucao!=null}'><c:out value='${bean.areaConstrucao}'/></c:if>">
						(Ex: 999)<TD class="boxTexto" height="21" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="23" width="173">N.º pavimentos:</TD>
						<TD class="boxTexto" height="23" width="160"><INPUT type="text"
							class="boxInput" name="NrPavimentos" size="12" maxlength="2"
							style="width: 50px"
							onkeyup="javascript:soNumeroMoeda('NrPavimentos')"
							value="<c:if test='${bean.nrPavimentos!=null}'><c:out value='${bean.nrPavimentos}'/></c:if>"></TD>
						<TD class="boxTexto" height="23" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" valign="bottom" height="34" width="173"><INPUT
							type="checkbox" name="Saunas" value="S"
							<c:if test="${bean.saunas=='S' }"> checked </c:if>>Saunas</TD>
						<TD class="boxTexto" valign="bottom" height="34" width="160"><INPUT
							type="checkbox" name="EscadRol" value="S"
							<c:if test="${bean.escadRol=='S' }"> checked </c:if>>Escadas
						Rolantes</TD>
						<TD class="boxTexto" height="34" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" width="173"><INPUT
							type="checkbox" name="Anuncios" value="S"
							<c:if test="${bean.anuncios=='S' }"> checked </c:if>>Anúncios</TD>
						<TD class="boxTexto" height="22" width="160"><INPUT
							type="checkbox" name="Antenas" value="S"
							<c:if test="${bean.antenas=='S' }"> checked </c:if>>Antenas</TD>
						<TD class="boxTexto" height="22" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" valign="top" height="20" width="173"><INPUT
							type="checkbox" name="Piscinas" value="S"
							<c:if test="${bean.piscinas=='S' }"> checked </c:if>>Piscinas</TD>
						<TD class="boxTexto" valign="top" height="20" width="160"></TD>
						<TD class="boxTexto" height="20" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="24" width="173"><INPUT
							type="checkbox" name="Elevadores"
							onclick="javascript:habilitaCampo('Elevadores','ElevNrAndars') ; javascript:habilitaCampo('Elevadores','ElevCapPessoa') "
							value="S" <c:if test="${bean.elevadores=='S' }"> checked </c:if>>Elevadores
						</TD>
						<TD class="boxTexto" height="24" width="160">N.º andares: <INPUT
							type="text" class="boxInput" name="ElevNrAndars" size="7"
							maxlength="2" style="width: 40px"
							<c:if test="${bean.elevadores == null || bean.elevadores == 'N'}"> disabled </c:if>
							onkeyup="javascript:soNumeroMoeda('ElevNrAndars')"
							value="<c:if test='${bean.elevNrAndars!=null}'><c:out value='${bean.elevNrAndars}'/></c:if>"></TD>
						<TD class="boxTexto" height="24" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="26" width="173"></TD>
						<TD class="boxTexto" height="26" valign="top" width="160">Capacidade:
						<INPUT type="text" class="boxInput" name="ElevCapPessoa" size="7"
							maxlength="2" style="width: 40px"
							<c:if test="${bean.elevadores == null || bean.elevadores == 'N'}"> disabled </c:if>
							onkeyup="javascript:soNumeroMoeda('ElevCapPessoa')"
							value="<c:if test='${bean.elevCapPessoa!=null}'><c:out value='${bean.elevCapPessoa}'/></c:if>">
						pessoas</TD>
						<TD class="boxTexto" height="26" width="31"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="42" width="173">Sinistralidade dos
						últimos 5 anos:</TD>
						<TD class="boxTexto" height="42" width="160"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="12" maxlength="5"
							style="width: 50px"
							onkeyup="javascript:soNumeroMoeda('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" height="42" width="31"></TD>
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
		<TD class="boxTexto" align="center" height="41" width="187"><INPUT
			type="image" name="btnEnviar" src="images/botaoProximo.gif"
			onclick="javascript:return ValidarCamposEnviar(this);"></TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div5105Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
