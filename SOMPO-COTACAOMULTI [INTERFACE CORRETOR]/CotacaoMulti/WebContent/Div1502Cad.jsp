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

//Valida Sistema de Proteção
 	var c = 0;
	if(document.frm1.Alarme.checked==true){
		document.frm1.Alarme.value="S";
		c++;
	}else{
		document.frm1.Alarme.value="N";			
	}
	if(document.frm1.Vigia24hs.checked==true){
		document.frm1.Vigia24hs.value="S";
		c++;
	}else{
		document.frm1.Vigia24hs.value="N";			
	}
	if(c==0){
		alert("No mínimo 1(um) 'Sistema de Proteção' deve ser selecionado!!");
		return (false);
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
<TABLE border="0" cellpadding="5" cellspacing="0" class="tabelaAction">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="497">Ramo 710 - Riscos Diversos - Roubo
			Comercial/Industrial</TD>
		</TR>
		<TR valign="top">
			<TD width="497" height="190">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="18" colspan="2">Descrição da Atividade</TD>
						<TD class="boxTexto" height="22" width="150"><INPUT type="text"
							class="boxInput" name="DscAtiv" size="14" maxlength="59"
							style="width: 250px" value='<c:out value="${bean.dscAtiv}" />'></TD>
						<TD class="boxTexto" height="22" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" colspan="2"><INPUT type="checkbox" name="TpMercMat" value="N" onclick="javascript:habilitaCampo('TpMercMat','VlrMercMat')" <c:if test="${bean.tpMercMat == 'S'}"> checked </c:if>="">Mercadorias
						e matérias primas inerentes ao ramo de negócios: R$</TD>
						<TD class="boxTexto" height="22" width="150"><INPUT type="text" class="boxInput" name="VlrMercMat" size="14" maxlength="15" style="width: 150px" onkeyup="javascript:soNumeroMoeda('VlrMercMat')" <c:if test="${bean.tpMercMat == null || bean.tpMercMat == 'N'}"> disabled </c:if>="" value="<c:if test='${bean.vlrMercMat!=null && bean.vlrMercMat!="0,00" }'><c:out value='${bean.vlrMercMat}'/></c:if>"> (Ex.: 9.999,99)</TD>
						
						<TD class="boxTexto" height="22" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" colspan="2"><INPUT
							type="checkbox" name="TpMaqEquip" value="N"
							onclick="javascript:habilitaCampo('TpMaqEquip','VlrMaqEquip')"
							<c:if test="${bean.tpMaqEquip == 'S'}"> checked </c:if>>Maquinas
						e equipamentos inerrentes ao ramo de negócios: R$</TD>
						<TD class="boxTexto" height="22" width="150"><INPUT type="text"
							class="boxInput" name="VlrMaqEquip" size="14" maxlength="15"
							style="width: 150px"
							onkeyup="javascript:soNumeroMoeda('VlrMaqEquip')"
							<c:if test="${bean.tpMaqEquip == null || bean.tpMaqEquip == 'N' }"> disabled </c:if>
							value="<c:if test='${bean.vlrMaqEquip!=null && bean.vlrMaqEquip!="0,00"}'><c:out value='${bean.vlrMaqEquip}'/></c:if>"> (Ex.: 9.999,99)</TD>
						<TD class="boxTexto" height="22" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" colspan="2"><INPUT
							type="checkbox" name="TpMobiliario" value="N"
							onclick="javascript:habilitaCampo('TpMobiliario','VlrMobiliario')"
							<c:if test="${bean.tpMobiliario == 'S'}"> checked </c:if>>Mobilíario:
						R$</TD>
						<TD class="boxTexto" height="22" width="150"><INPUT type="text"
							class="boxInput" name="VlrMobiliario" size="14" maxlength="15"
							style="width: 150px"
							onkeyup="javascript:soNumeroMoeda('VlrMobiliario')"
							<c:if test="${bean.tpMobiliario == null || bean.tpMobiliario == 'N'}"> disabled </c:if>
							value="<c:if test='${bean.vlrMobiliario!=null && bean.vlrMobiliario!="0,00"}'><c:out value='${bean.vlrMobiliario}'/></c:if>"> (Ex.: 9.999,99)</TD>
						<TD class="boxTexto" height="22" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" colspan="2"><INPUT
							type="checkbox" name="TpCobertDanos" value="N"
							onclick="javascript:habilitaCampo('TpCobertDanos','VlrCobertDanos')"
							<c:if test="${bean.tpCobertDanos == 'S'}"> checked </c:if>>Cobertura
						para danos causados a portas, janelas, etc: R$</TD>
						<TD class="boxTexto" height="22" width="150"><INPUT type="text"
							class="boxInput" name="VlrCobertDanos" size="14" maxlength="15"
							style="width: 150px"
							onkeyup="javascript:soNumeroMoeda('VlrCobertDanos')"
							<c:if test="${bean.tpCobertDanos == null || bean.tpCobertDanos == 'N'}"> disabled </c:if>
							value="<c:if test='${bean.vlrCobertDanos!=null && bean.vlrCobertDanos!="0,00"}'><c:out value='${bean.vlrCobertDanos}'/></c:if>"> (Ex.: 9.999,99)</TD>
						<TD class="boxTexto" height="22" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" colspan="2">Sinistralidade dos
						últimos 5 anos</TD>
						<TD class="boxTexto" height="22" width="150"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null && bean.numSin5anos!="0,00"}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
						<TD class="boxTexto" height="22" width="5"></TD>
					</TR>
					<TR>
						<TD class="boxTitulo tabelaAction" colspan="4"  height="21">Sistemas
						de Proteção</TD>
					</TR>
				
					<TR>
						<TD class="boxTexto" colspan="2" height="22"><INPUT
							type="checkbox" name="Alarme" value="" 
							<c:if test="${bean.alarme!=null && bean.alarme =='S'}"> checked </c:if>="">Alarme</TD>
						<TD class="boxTexto" width="150" height="22"><INPUT	
							type="checkbox" name="Vigia24hs" value="" 
							<c:if test="${bean.vigia24hs!=null && bean.vigia24hs =='S'}"> checked </c:if>="">Vigia 24hs</TD>
						<TD class="boxTexto" width="5" height="22"></TD>
					</TR>
				
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD class="boxTexto" align="center" height="39" width="231">
			<A href="javascript:voltaPrincipal(document.frm1);" class="cbutton">VOLTAR</A>
		</TD>
		<TD class="boxTexto" align="center" height="39" width="258">
			<a name="btnEnviar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">PRÓXIMO</a>
		</TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div1502Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
