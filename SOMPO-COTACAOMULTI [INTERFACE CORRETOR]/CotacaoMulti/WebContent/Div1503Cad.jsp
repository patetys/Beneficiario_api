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

function habilitaCampo(n,m){
	camposHabilita(document.frm1,n,m);
}

</script>

<form NAME=frm1 ACTION="Controller" METHOD="POST"><INPUT TYPE="HIDDEN"
	NAME="action" VALUE="msdetalhe">

<P></P>
<TABLE border="0" cellpadding="5" cellspacing="0" class="tabelaAction"
	width="554">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="587">Ramo 710 - Riscos Diversos - Roubo Residêncial</TD>
		</TR>
		<TR valign="top">
			<TD width="587" height="25">

			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"
				width="573">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="24">Construção Alvenaria :</TD>
						<TD class="boxTexto" colspan="2" height="24">
						<TABLE>
							<TBODY>
								<TR>
									<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
										name="TpConstrucao" value="S"
										<c:if test="${bean.tpConstrucao=='S' || bean.tpConstrucao==null }"> checked </c:if>>Sim</TD>
									<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
										name="TpConstrucao" value="N"
										<c:if test="${bean.tpConstrucao=='N' }"> checked </c:if>>Não</TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="14">Tipo de Moradia</TD>
						<TD class="boxTexto" colspan="2" height="14">
						<TABLE>
							<TBODY>
								<TR>
									<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
										name="TpMoradia" value="1"
										<c:if test="${bean.tpMoradia==1 || bean.tpMoradia==null }"> checked </c:if>>Casa</TD>
									<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
										name="TpMoradia" value="2"
										<c:if test="${bean.tpMoradia==2 }"> checked </c:if>>Apartamento</TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="24">Modalidade da Moradia</TD>
						<TD class="boxTexto" valign="top" colspan="2" height="24">
						<TABLE>
							<TBODY>
								<TR>
									<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
										name="TpUsoMoradia" value="1"
										<c:if test="${bean.tpUsoMoradia==1 || bean.tpUsoMoradia==null }"> checked </c:if>>Habitual</TD>
									<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
										name="TpUsoMoradia" value="2"
										<c:if test="${bean.tpUsoMoradia==2 }"> checked </c:if>>Eventual</TD>
									<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
										name="TpUsoMoradia" value="3"
										<c:if test="${bean.tpUsoMoradia==3 }"> checked </c:if>>Desocupada</TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="boxTitulo tabelaAction" colspan="2"  height="14" width="315">Cobertura:</TD>
						<TD class="boxTitulo tabelaAction" colspan="4"  height="14"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" valign="bottom" colspan="2" height="10" width="315"><INPUT type="checkbox" name="TpMobiliario" value="N"
							onclick="javascript:habilitaCampo('TpMobiliario','VlrMobiliario')"
							<c:if test="${bean.tpMobiliario == 'S'}"> checked </c:if>="">Mobiliário:
						R$</TD>
						<TD class="boxTexto" height="10" width="228"><INPUT type="text"
							class="boxInput" name="VlrMobiliario" size="14" maxlength="15"
							style="width: 150px"
							onkeyup="javascript:soNumeroMoeda('VlrMobiliario')"
							<c:if test="${bean.tpMobiliario == null || bean.tpMobiliario == 'N'}"> disabled </c:if>=""
							value="<c:if test='${bean.vlrMobiliario!=null && bean.vlrMobiliario!="0,00"}'><c:out value='${bean.vlrMobiliario}'/></c:if>"> (Ex.: 9.999,99)</TD>
					</TR>
					<TR>
						<TD class="boxTexto" colspan="2" height="15" width="315"><INPUT
							type="checkbox" name="TpArtCouro" value="N"
							onclick="javascript:habilitaCampo('TpArtCouro','VlrArtCouro')"
							<c:if test="${bean.tpArtCouro == 'S'}"> checked </c:if>="">Artigos
						de ouro, prata, relógios e jóias em geral: R$</TD>
						<TD class="boxTexto" height="15" width="228"><INPUT type="text"
							class="boxInput" name="VlrArtCouro" size="14" maxlength="15"
							style="width: 150px"
							onkeyup="javascript:soNumeroMoeda('VlrArtCouro')"
							<c:if test="${bean.tpArtCouro == null || bean.tpArtCouro == 'N'}"> disabled </c:if>=""
							value="<c:if test='${bean.vlrArtCouro!=null && bean.vlrArtCouro!="0,00" }'><c:out value='${bean.vlrArtCouro}'/></c:if>"> (Ex.: 9.999,99)</TD>
					</TR>
					<TR>
						<TD class="boxTexto" colspan="2" height="13" width="315"><INPUT
							type="checkbox" name="TpCobertDanos" value="N"
							onclick="javascript:habilitaCampo('TpCobertDanos','VlrCobertDanos')"
							<c:if test="${bean.tpCobertDanos == 'S'}"> checked </c:if>="">Cobertura
						para danos causados a portas, janelas, etc: R$</TD>
						<TD class="boxTexto" height="13" width="228"><INPUT type="text"
							class="boxInput" name="VlrCobertDanos" size="14" maxlength="15"
							style="width: 150px"
							onkeyup="javascript:soNumeroMoeda('VlrCobertDanos')"
							<c:if test="${bean.tpCobertDanos == null || bean.tpCobertDanos == 'N'}"> disabled </c:if>=""
							value="<c:if test='${bean.vlrCobertDanos!=null && bean.vlrCobertDanos!="0,00" }'><c:out value='${bean.vlrCobertDanos}'/></c:if>"> (Ex.: 9.999,99)</TD>
					</TR>
					<TR>
						<TD class="boxTexto" colspan="2" height="10" width="315">Sinistralidade
						dos últimos 5 anos</TD>
						<TD class="boxTexto" height="10" width="228"><INPUT type="text"
							class="boxInput" name="NumSin5anos" size="14" maxlength="5"
							style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')"
							value="<c:if test='${bean.numSin5anos!=null}'><c:out value='${bean.numSin5anos}'/></c:if>"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" colspan="2" height="38" width="293">
			<A href="javascript:voltaPrincipal(document.frm1);" class="cbutton">VOLTAR</A>
		</TD>
		<TD class="boxTexto" align="center" height="38" width="285">
			<a name="btnEnviar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">PRÓXIMO</a>
		</TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div1503Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
