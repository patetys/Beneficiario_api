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
<script language="JavaScript" type="text/javascript" src="js/consistencias.js" charset="UTF-8"></script>
<script language="JavaScript">

function ValidarCamposEnviar(form) {

var cont=0;

//Verifica os Campos não preenchidos
	if(!Validanaopreenchido(document.frm1)){
		return false;
	}       

	        
   

//Valida os campos de valores
	
	campoNumericoMoeda = new CriaArray(3);
	campoNumericoMoeda[0] = "VultoMaior";
	campoNumericoMoeda[1] = "VlrDMP";
	campoNumericoMoeda[2] = "VlrPMP";
	if(!numMoedaCampos(document.frm1,campoNumericoMoeda)){
		return false;
	}
	
	
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
			<TD class="boxTitulo" width="415">Ramo 112 - Multi-Seguro Empresarial</TD>
		</TR>
		<TR valign="top">
			<TD height="258" width="415">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" width="176" height="26">Tipo</TD><TD class="boxTexto" width="237" height="26">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="100"><INPUT type="radio"
									name="Tipo" value="1" <c:if test="${bean.tipo =='1' || bean.tipo ==null }"> checked </c:if>>Indústria</TD>
								<TD class="boxTexto" height="18" width="87"><INPUT type="radio" 
								name="Tipo" value="2" <c:if test="${bean.tipo =='2'}"> checked </c:if>> Comércio</TD>
							</TR>
						</TABLE>						
						</TD>
						<TD class="boxTexto" height="17" width="2"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="176" height="26">Descrição Atividade</TD>
						<TD class="boxTexto" width="237" height="26"><INPUT type="text" class="boxInput" name="DscAtividade" size="14" maxlength="59" style="width: 250px" value='<c:out value="${bean.dscAtividade}" />'></TD>
						
						<TD class="boxTexto" height="17" width="2"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="176" height="27">Processo á Quente :</TD>
						<TD class="boxTexto" width="237" height="27">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="ProcQuente" value="S"
									<c:if test="${bean.procQuente =='S' || bean.procQuente ==null }"> checked </c:if>>
								Sim</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="ProcQuente" value="N"
									<c:if test="${bean.procQuente =='N'}"> checked </c:if>> Não</TD>
							</TR>
						</TABLE>
						</TD>
						<TD class="boxTexto" height="27" width="2"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" width="176">Material Inflamável?:</TD>
						<TD class="boxTexto" height="18" width="237">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="MatInflam" value="S"
									<c:if test="${bean.matInflam =='S' || bean.matInflam ==null }"> checked </c:if>>Sim</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="MatInflam" value="N"
									<c:if test="${bean.matInflam =='N'}"> checked </c:if>>Não</TD>
							</TR>
						</TABLE>
						</TD>
						<TD class="boxTexto" height="22" width="2"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" width="176">Construção de Alvenaria ?</TD>
						<TD class="boxTexto" height="22" width="237">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="ConstAlvenaria" value="S" <c:if test="${bean.constAlvenaria =='S' || bean.constAlvenaria ==null }"> checked </c:if>>Sim</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="ConstAlvenaria" value="N" <c:if test="${bean.constAlvenaria =='N'}"> checked </c:if>>Não</TD>
							</TR>
						</TABLE>						
						</TD>
						<TD class="boxTexto" height="22" width="2"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="176" height="27">Sinistralidade dos
						últimos 5 anos</TD><TD class="boxTexto" width="237" height="27"><INPUT type="text" class="boxInput" name="NumSin5anos" size="14" maxlength="5" style="width: 50px" onkeyup="javascript:soNumero('NumSin5anos')" value='<c:out value="${bean.numSin5anos}" />'></TD>
						
						<TD class="boxTexto" height="27" width="2"></TD>
					</TR>
					<TR>
						<TD class="boxTitulo tabelaAction" colspan="2"  height="17">Bens cobertos</TD>
						<TD class="boxTitulo tabelaAction"  height="17" width="2"></TD>
					</TR>
					<TR>
						<TD height="1" colspan="3" align="left">
						<TABLE width="413" align="left">
							<TR>
								<TD class="boxTexto" height="18" width="117"><INPUT type="radio"
									name="BensCobertos" value="1"
									<c:if test="${bean.bensCobertos =='1' || bean.bensCobertos ==null }"> checked </c:if>>Prédio
								e conteúdo</TD>
								<TD class="boxTexto" height="18" width="137"><INPUT type="radio"
									name="BensCobertos" value="2"
									<c:if test="${bean.bensCobertos =='2'}"> checked </c:if>>Exclusivamente
								prédio</TD>
								<TD class="boxTexto" height="18" width="151"><INPUT type="radio"
									name="BensCobertos" value="3"
									<c:if test="${bean.bensCobertos =='3'}"> checked </c:if>>Exclusivamente
								contéudo</TD>
							</TR>
						</TABLE>
						</TD>
					</TR>
					<TR>
						<TD class="boxTitulo tabelaAction" colspan="2" height="17">Sistemas
						de Proteção</TD>
						<TD class="boxTitulo tabelaAction"  height="17" width="2"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" width="176" height="28"><INPUT
							type="checkbox" name="SistPrtcao01" value="01"
							<c:if test="${bean.sistPrtcao01 != null && bean.sistPrtcao01 !=0}"> checked </c:if>>Extintor</TD>
						<TD class="boxTexto" align="left" width="237" height="28"><INPUT
							type="checkbox" name="SistPrtcao03" value="01"
							<c:if test="${bean.sistPrtcao03 != null && bean.sistPrtcao03 !=0}"> checked </c:if>>Sprinkler</TD>
						<TD class="boxTexto" align="left" height="28" width="2"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" width="176" height="28"><INPUT
							type="checkbox" name="SistPrtcao02" value="01"
							<c:if test="${bean.sistPrtcao02 != null && bean.sistPrtcao02 !=0}"> checked </c:if>>Hidrantes</TD>
						<TD class="boxTexto" width="237" height="28"><INPUT
							type="checkbox" name="SistPrtcao04" value="01"
							<c:if test="${bean.sistPrtcao04 != null && bean.sistPrtcao04 !=0}"> checked </c:if>>Vigia 24hs</TD>
						<TD class="boxTexto" height="28" width="2"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" width="176" height="28"><INPUT
							type="checkbox" name="SistPrtcao05" value="01"
							<c:if test="${bean.sistPrtcao05 != null && bean.sistPrtcao05 !=0}"> checked </c:if>="">Alarme</TD>
						<TD class="boxTexto" width="237" height="28"></TD>
						<TD class="boxTexto" height="28" width="2"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE>
	<TR>
		<TD align="center" height="43" width="207">
			<A href="javascript: voltaPrincipal(document.frm1);" class="cbutton">VOLTAR</A>
		</TD>
		<TD class="boxTexto" align="center" height="43" width="206">
			<a name="btnEnviar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">PRÓXIMO</a>
		</TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Div112Cad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
