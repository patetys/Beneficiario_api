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
	
	campoNumericoMoeda = new CriaArray(2);
	campoNumericoMoeda[0] = "VlrFat";
	campoNumericoMoeda[1] = "VltMaoObr";

	if(!numMoedaCampos(document.frm1,campoNumericoMoeda)){
		return false;
	}
	
	campoNumerico = new CriaArray(5);
	campoNumerico[0] = "MaiorPav";
	campoNumerico[1] = "QtdPav";
	campoNumerico[2] = "AreaLinFach";
	campoNumerico[3] = "AfastRelTerc";
	campoNumerico[4] = "NumSin5anos";
	
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

function habilitaCampos(v){
	if(v==1 || v==null){
		document.frm1.VltMaoObr.value="";
		document.frm1.VltMaoObr.disabled=true;
		document.frm1.MaiorPav.disabled=false;
		document.frm1.AreaLinFach.disabled=false;
		document.frm1.QtdPav.disabled=false;
		document.frm1.AfastRelTerc.disabled=false;
	}
	if(v==2){
		document.frm1.MaiorPav.value="";
		document.frm1.AreaLinFach.value="";
		document.frm1.QtdPav.value="";
		document.frm1.AfastRelTerc.value="";
		document.frm1.VltMaoObr.disabled=false;
		document.frm1.MaiorPav.disabled=true;
		document.frm1.AreaLinFach.disabled=true;
		document.frm1.QtdPav.disabled=true;
		document.frm1.AfastRelTerc.disabled=true;
	}
}


</script>

<form NAME=frm1 ACTION="Controller" METHOD="POST"><INPUT TYPE="HIDDEN"
	NAME="action" VALUE="msdetalhe">
<P></P>
<TABLE border="0" cellpadding="5" cellspacing="0" bgcolor="#8FBCE4">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="361">Ramo 510 - Responsabilidade Civil -
			Obras Civis</TD>
		</TR>
		<TR valign="top">
			<TD width="361" height="208">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="18" width="186"><INPUT type="radio"
							name="TpObra" value="1" onclick="javascript: habilitaCampos(1);"
							<c:if test="${bean.tpObra=='1' || bean.tpObra==null }"> checked </c:if>>Construção/Demolição</TD>
						<TD class="boxTexto" height="18" width="161"></TD>
						<TD class="boxTexto" height="22" width="18"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" width="186">Base maior pavimento:</TD>
						<TD class="boxTexto" height="18" width="161"><INPUT type="text" 
						class="boxInput" name="MaiorPav" size="17" maxlength="4" style="width: 50px" 
						onkeyup="javascript:soNumero('MaiorPav')" 
						value="<c:if test='${bean.maiorPav!=null}'><c:out value='${bean.maiorPav}'/></c:if>"> (m<FONT style='vertical-align: text-top;font-size: 6pt;'>2</FONT>)</TD>
						
						<TD class="boxTexto" height="22" width="18"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" width="186">Área linear da fachada:</TD>
						<TD class="boxTexto" height="22" width="161"><INPUT type="text" class="boxInput" 
						name="AreaLinFach" size="17" maxlength="5" style="width: 50px" 
						onkeyup="javascript:soNumero('AreaLinFach')" 
						value="<c:if test='${bean.areaLinFach!=null}'><c:out value='${bean.areaLinFach}'/></c:if>"> (m<FONT style='vertical-align: text-top;font-size: 6pt;'>2</FONT>)</TD>
						<TD class="boxTexto" height="22" width="18"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" width="186">Quantidade pavimentos:</TD>
						<TD class="boxTexto" height="18" width="161"><INPUT 
						type="text" class="boxInput" name="QtdPav" size="17" maxlength="3" 
						style="width: 50px" onkeyup="javascript:soNumero('QtdPav')" 
						value="<c:if test='${bean.qtdPav!=null}'><c:out value='${bean.qtdPav}'/></c:if>"></TD>
						<TD class="boxTexto" height="22" width="18"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" width="186">Afastamento em relação a terceiros:</TD>
						<TD class="boxTexto" height="22" width="161"><INPUT type="text" 
						class="boxInput" name="AfastRelTerc" size="17" maxlength="5" 
						style="width: 50px" onkeyup="javascript:soNumero('AfastRelTerc')" 
						value="<c:if test='${bean.afastRelTerc!=null}'><c:out value='${bean.afastRelTerc}'/></c:if>"> (m<FONT style='vertical-align: text-top;font-size: 6pt;'>2</FONT>)</TD>
						<TD class="boxTexto" height="22" width="18"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" width="186"><INPUT type="radio" name="TpObra" value="2"
						onclick="javascript: habilitaCampos(2);" <c:if test="${bean.tpObra=='2' }"> checked </c:if>> Outros</TD>
						<TD class="boxTexto" height="18" width="161"></TD>
						<TD class="boxTexto" height="22" width="18"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" width="186">Valor da Mão de Obra: R$</TD>
						<TD class="boxTexto" height="18" width="161"><INPUT type="text"
							class="boxInput" name="VltMaoObr" size="15" maxlength="15"
							style="width: 150px"
							onkeyup="javascript:soNumeroMoeda('VltMaoObr')"
							value="<c:if test='${bean.vltMaoObr!=null && bean.vltMaoObr!="0,00"}'><c:out value='${bean.vltMaoObr}'/></c:if>"></TD>
						
						<TD class="boxTexto" height="22" width="18"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" width="186"></TD>
						<TD class="boxTexto" height="22" width="161">(Ex.: 9.999,99)</TD>
						<TD class="boxTexto" height="22" width="18"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="186" height="40">Sinistralidade dos últimos 5 anos</TD>
						<TD class="boxTexto" width="161" height="40"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" width="18" height="40"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="46" width="178"><A
			href="javascript:voltaPrincipal(document.frm1);"><IMG name="consiste"
			src="images/botaoVoltar.gif" border="0"></A></TD>
		<TD class="boxTexto" align="center" height="46" width="176"><INPUT
			type="image" name="btnEnviar" src="images/botaoProximo.gif"
			onclick="javascript:return ValidarCamposEnviar(this);"></TD>
	</TR>
</TABLE>
<script>
habilitaCampos(<c:out value='${bean.tpObra}'/>);
</script>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div5108Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</HTML>
