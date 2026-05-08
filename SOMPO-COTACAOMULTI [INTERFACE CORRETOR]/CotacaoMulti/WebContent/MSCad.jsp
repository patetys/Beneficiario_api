<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>

<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%@ page import="java.util.*" %>

<%
Random random = new Random(); 
String valorRan = "" + random.nextInt();
valorRan = valorRan.trim();
%>

<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">

<c:set var="sompo" value="${sessionScope['sompo']}" />
<c:if test="${sompo == 'Sompo'}">
	<link rel="stylesheet" href="css/sompo_yasuda.css" type="text/css">
	<SCRIPT language="JavaScript" src="js/sompo_datetimepicker.js" charset="UTF-8"></SCRIPT>
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<link rel="stylesheet" href="css/yasuda.css" type="text/css">
	<SCRIPT language="JavaScript" src="js/datetimepicker.js"></SCRIPT>
</c:if>

<TITLE></TITLE>
</HEAD>

<BODY onload="javascript:txtQtdlocRiscChangedOpen();">
<script language="javascript" src="js/jquery-1.4.2.js" charset="UTF-8"></script>
<SCRIPT language="JavaScript" src="js/consistencias.js?param=<%=valorRan%>" charset="UTF-8"></SCRIPT>
<SCRIPT language="JavaScript" src="js/consistenciasMSCad.js?param=<%=valorRan%>" charset="UTF-8"></SCRIPT>

<script language="JavaScript">

function calcHeight(){

	try{
		  //find the height of the internal page
		  var the_height=
		    document.getElementById('frameFile').contentWindow.document.body.scrollHeight;
		
		  //change the height of the iframe
		  document.getElementById('frameFile').height=the_height;
	
  	}catch(e){
  	}

}

	
</script>

<jsp:useBean id="CotacaoBean" scope="session" class="bean.CotacaoBean">
	<jsp:setProperty name="CotacaoBean" property="property" param="param"/>
</jsp:useBean>

<FORM NAME="frmCEP" ACTION="Controller" target="cep" METHOD="POST"><INPUT
	TYPE=HIDDEN NAME="action" VALUE="cep"> <INPUT TYPE=HIDDEN NAME="numCEP"
	VALUE=""></FORM>

<form NAME=frm1 ACTION="Controller" METHOD="POST">
	<INPUT TYPE="HIDDEN" NAME="action" VALUE="cadramo">
	<INPUT TYPE="HIDDEN" NAME="vMenuCad" VALUE="1">
	<INPUT TYPE="HIDDEN" NAME="codRamo" VALUE='<c:out value="${CotacaoBean.codRamo}" />'>
	<INPUT TYPE="HIDDEN" NAME="tipEmis" VALUE="<c:out value='${CotacaoBean.tipEmissao}' />" />
	<INPUT TYPE="HIDDEN" NAME="flagCorretorEmpresarial" ID="flagCorretorEmpresarial" VALUE="<c:out value='${flagCorretorEmpresarial}' />" /> 
	<INPUT TYPE="HIDDEN" NAME="flagRamo" ID="flagRamo" VALUE="<c:out value='${CotacaoBean.codRamo}' />" /> 
	<INPUT TYPE="HIDDEN" NAME="flagMod" ID="flagMod" VALUE="<c:out value='${CotacaoBean.codModalidade}' />" /> 
	<INPUT TYPE="hidden" name="codUnidadeCorp" id="codUnidadeCorp" VALUE="<c:out value='${CotacaoBean.codDepto}'/>">
	<INPUT TYPE="hidden" name="codCorr" id="codCorr" VALUE="<c:out value='${CotacaoBean.codCorr}'/>">
	
	<input type="hidden" name="cod_hierarquico" value="<c:out value="${CotacaoBean.codigoHierarquico}" />" />
	<input type="hidden" name="cod_filial" value="<c:out value="${CotacaoBean.codigoFilial}" />" />
	
<P></P>
<f:setLocale value="pt-BR"/> 
<TABLE border="0" cellpadding="5" cellspacing="0" class="tabelaAction">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="465">Dados Gerais</TD>
		</TR>
		<TR valign="top">
			<TD width="465" height="701">

			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>


					<TR>
						<TD class="boxTexto" width="176" height="26">Ramo :</TD>
						<TD class="boxTexto" width="265" height="26"><c:out
							value="${CotacaoBean.codRamo}" /> - <c:out
							value="${CotacaoBean.nomRamo}" /></TD>
						<TD class="boxTexto" width="05" height="26"></TD>
					</TR>

					<c:if test="${CotacaoBean.codModalidade>0}">
						<TR>
							<TD class="boxTexto" width="176" height="20">Modalidade :</TD>
							<TD class="boxTexto" width="265" height="20"><c:out	value="${CotacaoBean.nomModalidade}" /></TD>
							<TD class="boxTexto" width="05" height="20"></TD>
						</TR>
					</c:if>

					<TR>
						<TD class="boxTexto" width="176" height="20">Corretor :</TD>
						<TD class="boxTexto" width="265" height="20"><c:out
							value="${CotacaoBean.codCorr}" /> - <c:out
							value="${CotacaoBean.nomCorr}" /></TD>
						<TD class="boxTexto" width="15" height="20"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="176" height="20">Tipo de Cotação:</TD>
						<TD class="boxTexto" width="265" height="20"><c:if
							test="${CotacaoBean.tpCotacao=='1'}"> COTAÇÃO  </c:if> <c:if
							test="${CotacaoBean.tpCotacao=='2'}"> RECOTAÇÃO </c:if></TD>
						<TD class="boxTexto" width="15" height="20"></TD>
					</TR>


					<TR>
						<TD class="boxTexto" width="176" height="20">Tipo de Emissão:</TD>
						<TD class="boxTexto" width="265" height="20"><c:out
							value="${CotacaoBean.tipEmissao}" /> - <c:out
							value="${CotacaoBean.nomEmissao}" /></TD>
						<TD class="boxTexto" width="15" height="20"></TD>
					</TR>
					<c:if test="${CotacaoBean.codModalidade != '02' || (CotacaoBean.codRamo != 113 && CotacaoBean.codRamo != 112)}">
					<TR>
						<TD class="boxTexto" width="176" height="20">LMI único:</TD>
						<TD class="boxTexto" width="265" height="20">
							<input type="checkbox" name="lmiUnico" id="lmiUnico"></input>
						</TD>
						<TD class="boxTexto" width="15" height="20"></TD>
					</TR>
					</c:if>
					<c:if test="${CotacaoBean.tipEmissao=='04' || CotacaoBean.tipEmissao=='03'}">
						<TR>
							<TD class="boxTexto" width="176" height="20">Apólice :</TD>
							<TD class="boxTexto" width="265" height="20"><c:out
								value="${CotacaoBean.numApol}" /></TD>
							<TD class="boxTexto" width="05" height="20"></TD>
						</TR>
					</c:if>
					<c:if test="${requestScope['intranet']=='1'}">
					<TR>
						<TD class="boxTexto" height="20" width="176">Motivo:</TD>					
						<TD class="boxTexto" height="20" width="256">
    			            <select class="boxInput" id="TpMotivo" name="TpMotivo" size="1"  style="width: 205px" onchange="onChange(this)">    					    
    					    <c:forEach items="${TpMotivo}" var="tpmot" varStatus="status"  >
    					    	 <c:choose>
									<c:when test="${CotacaoBean.codMotivo==status.index}">
										<option selected="selected" value="<c:out value="${status.index}" />" > <c:out value="${tpmot}" /></option>
									</c:when>
									<c:otherwise>
										<option value="<c:out value="${status.index}" />" > <c:out value="${tpmot}" /></option>
									</c:otherwise>
								</c:choose> 
    					 	</c:forEach>
    						</select>
    					</TD>    					
					</TR>
					<TR id="campo-six" style="display:none">
						<TD class="boxTexto" height="20" width="176">Descrição:</TD>					
						<TD class="boxTexto" height="23" width="256">
    			            <input name="nomMotivo" id="nomMotivo" type="text" value="<c:out value="${CotacaoBean.nomMotivo}" />" size="30" maxlength="30" class="boxInput" style="width: 250px"/>
    					</TD>    					
					</TR>
					</c:if>
					<TR>
						<TD class="boxTitulo tabelaAction" height="22" colspan="2" >Dados
						do Proponente</TD>
						<TD class="boxTitulo tabelaAction" height="22"  width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="25" width="176">Nome:</TD>
						<TD class="boxTexto" height="25" width="265"><INPUT type="text"
							class="boxInput" name="nomeProp" size="14" 
							value="<c:out value="${CotacaoBean.nomeProp}" />" maxlength="59"
							style="width: 250px"></TD>
						<TD class="boxTexto" height="25" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="18" width="176">Tipo Pessoa:</TD>
						<TD class="boxTexto" height="18" width="265">
						<TABLE>
							<TR>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="tipoPessoa" value="0"
									<c:if test="${CotacaoBean.tipPessoa=='0' || CotacaoBean.tipPessoa==null || CotacaoBean.tipPessoa==''}">checked</c:if>>Física</TD>
								<TD class="boxTexto" height="18" width="60"><INPUT type="radio"
									name="tipoPessoa" value="1"
									<c:if test="${CotacaoBean.tipPessoa=='1'}">checked</c:if>>Jurídica</TD>
							</TR>
						</TABLE>
						</TD>

						<TD class="boxTexto" height="22" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" width="176">CNPJ/CPF:</TD>
						<TD class="boxTexto" height="22" width="265"><INPUT type="text"
                            class="boxInput" name="cnpjCpf" id="cnpjCpf" size="14"
                            onBlur="javascript:consultaContasInternacionais()"
                            value="<c:out value="${CotacaoBean.cnpjCpf}"/>" maxlength="14"
                            style="width: 100px"
                            oninput="javascript:formatarInputAlfanumerico(this);"> (Insira todos os caracteres.)
							<div id="msgDuplic" class="boxInputSemBorda"></div>
						</TD>
						<TD class="boxTexto" height="22" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" width="176">Vigência:</TD>
						<TD class="boxTexto" height="22" width="265"><INPUT type="text" readonly="true"
							class="boxInput" name="dataVigenciaInicio" id="dataVigenciaInicio" size="10"
							value="<c:out value="${CotacaoBean.dataVigenciaInicioFormatada}"/>"  maxlength="10"
							style="width: 100px"> 
							<A href="javascript:NewCal('dataVigenciaInicio','ddmmyyyy')"><IMG src="images/cal.gif" width="16" height="16" border="0" alt="Selecione a data"></A>
							até
							<INPUT type="text" readonly="true"
							class="boxInput" name="dataVigenciaFim" id="dataVigenciaFim" size="10"
							value="<c:out value="${CotacaoBean.dataVigenciaFimFormatada}"/>" maxlength="10"
							style="width: 100px"> 
							<c:if test="${dataLimteVigenciaEndosso == false}">
							<A href="javascript:NewCal('dataVigenciaFim','ddmmyyyy')"><IMG src="images/cal.gif" width="16" height="16" border="0" alt="Selecione a data"></A></TD>
							</c:if>
						<TD class="boxTexto" height="22" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTitulo tabelaAction" height="27" colspan="2" >Local
						de Risco</TD>
						<TD class="boxTitulo tabelaAction" height="27"  width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="19" width="176">Local de Risco:</TD>

						<TD class="boxTexto" height="20" width="265"><INPUT
							type="checkbox" name="QtdLocRisc" value="0"
							OnClick="txtQtdlocRiscChanged()"
							<c:if test="${CotacaoBean.qtdLocRisc=='1' }"> checked </c:if>="">Vários</TD>

						<TD class="boxTexto" height="14" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="21" width="176" valign="top">Cep:</TD>
						<TD class="boxTexto" height="14" width="265"><INPUT type="text"
							onkeyup="javascript:soNumero('Cep')" class="boxInput" name="Cep"
							size="14" value="<c:out value="${CotacaoBean.cep}" />"
							maxlength="8" style="width: 80px"
							onBlur="javascript: consultaCEP();"> (Digitar somente números.) <INPUT
							type="text" class="boxInputSemBorda" name="msgCEP" readonly
							value=""></TD>
						<TD class="boxTexto" height="14" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="19" width="176">Endereço :</TD>
						<TD class="boxTexto" height="18" width="265"><INPUT type="text"
							class="boxInput" name="End" size="14"
							value="<c:out value="${CotacaoBean.end}" />" maxlength="45"
							style="width: 250px" readonly="readonly"></TD>
						<TD class="boxTexto" height="18" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="19" width="176">Numero :</TD>
						<TD class="boxTexto" height="18" width="265"><INPUT type="text"
							class="boxInput" name="Numero" size="14"
							value="<c:out value="${CotacaoBean.numero}" />" maxlength="8"
							style="width: 90px"></TD>
						<TD class="boxTexto" height="18" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="19" width="176">Complemento:</TD>
						<TD class="boxTexto" height="14" width="265"><INPUT type="text"
							class="boxInput" name="Complemento" size="14"
							value="<c:out value="${CotacaoBean.complemento}" />"
							maxlength="19" style="width: 250px"></TD>
						<TD class="boxTexto" height="14" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="19" width="176">Bairro:</TD>
						<TD class="boxTexto" height="18" width="265"><INPUT type="text"
							class="boxInput" name="Bairro" size="14"
							value="<c:out value="${CotacaoBean.bairro}" />" maxlength="70"
							style="width: 200px" readonly="readonly"></TD>
						<TD class="boxTexto" height="18" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="19" width="176">Cidade:</TD>
						<TD class="boxTexto" height="18" width="265"><INPUT type="text"
							class="boxInput" name="Cid" size="14"
							value="<c:out value="${CotacaoBean.cid}" />" maxlength="70"
							style="width: 200px" readonly></TD>
						<TD class="boxTexto" height="18" width="15"></TD>
					</TR>


					<TR>
						<TD class="boxTexto" height="19" width="176">UF:</TD>
						<TD class="boxTexto" height="18" width="265"><INPUT type="text"
							class="boxInput" name="cobUF" size="14"
							value="<c:out value="${CotacaoBean.uf}" />" maxlength="70"
							style="width: 50px" readonly></TD>
						<TD class="boxTexto" height="18" width="15"></TD>
					</TR>

					<TR>
						
						<TD class="boxTexto" height="24" width="176">LMG R$: </TD>
													
						<TD class="boxTexto" height="18" width="265"><INPUT type="text"
							class="boxInput" name="VlRisco" size="14" onkeyup="javascript:soNumeroMoeda('VlRisco')" onBlur="javascript: document.frm1.VlRisco.style.background='FFFFFF';;"
							value="<c:if test="${CotacaoBean.vlRisco!=0}"><f:formatNumber value='${CotacaoBean.vlRisco}' 
							groupingUsed='true' minFractionDigits='2' /></c:if>" maxlength="13"
							style="width: 150px"> (Ex.: 9.999,99)</TD>	
							
						<TD class="boxTexto" height="18" width="15"></TD>
					</TR>

					<TR>
						<TD class="boxTitulo tabelaAction" colspan="2"  height="26">Dados
						do Solicitante</TD>
						<TD class="boxTitulo tabelaAction"  height="26" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" height="25" width="176">email: <font color="red">*</font></TD>
						<TD class="boxTexto" align="left" height="25" width="265"><INPUT
							type="text" class="boxInput" name="EmailContato" size="14"
							value="<c:out value="${CotacaoBean.emailContato}" />"
							maxlength="50" style="width: 250px"></TD>
						<TD class="boxTexto" align="left" height="25" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" height="12" width="176">Nome:</TD>
						<TD class="boxTexto" height="9" width="265"><INPUT type="text"
							class="boxInput" name="NomeContato" size="14"
							value="<c:out value="${CotacaoBean.nomeContato}" />"
							maxlength="30" style="width: 250px"></TD>
						<TD class="boxTexto" height="9" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" height="12" width="176">Telefone:</TD>
						<TD class="boxTexto" height="15" width="265"><INPUT type="text"
							class="boxInput" name="TelefContato" size="14"
							value="<c:out value="${CotacaoBean.telefContato}" />"
							maxlength="15" style="width: 100px"></TD>
						<TD class="boxTexto" height="15" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" height="12" width="500" colspan="2">
							<p style="margin-top: 10px"><b><font color="red">*</font></b> Para cadastrar mais de um email, deve-se usar ; (ponto e vírgula) como separador, sem espaços.</p>
						</TD>
					</TR>
					<TR>
						<TD class="boxTitulo tabelaAction" colspan="2"  height="26">Observações</TD>
						<TD class="boxTitulo tabelaAction"  height="26" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" height="12" width="500" 
						colspan="2">Demais dados necessários para a cotação (cobertura, LMG, bens a segurar, etc.): </TD>
						<TD class="boxTexto" align="left" height="12" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" height="12" width="176"
							colspan="2"><TEXTAREA name="observ" rows="3" cols="61" onkeyup="javascript:maxCaracteres(document.frm1.observ,500)"
							style="width: 436px" style="color: #4686BE"><c:out value="${CotacaoBean.comentario}" /></TEXTAREA></TD>
						<TD class="boxTexto" height="9" width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" height="12" width="176"></TD>
						<TD class="boxTexto" height="15" width="265"></TD>
						<TD class="boxTexto" height="15" width="15"></TD>
					</TR>

					<TR>
						<TD class="boxTitulo tabelaAction" height="27" colspan="2" >Anexos</TD>
						<TD class="boxTitulo tabelaAction" height="27"  width="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="183" height="15" colspan="3"><IFRAME
							id="frameFile" onLoad="javascript:calcHeight();" frameborder="0"
							name="uploadFile" src="UploadFile.jsp" width="416" height="1"
							marginheight="10" marginwidth="0" scrolling="no"> </IFRAME></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE width="464">
	<TR>

		<TD align="center" height="50" width="140">
			<a name="btnVoltar" class="cbutton" onclick="javascript:return VoltaMenuCad(this);">VOLTAR</a>
		</TD>

		<TD align="center" height="50" width="156">
			<a name="btnLimpar" class="cbutton" onClick="javascript:return limpaCampos();">LIMPAR</a>
		</TD>

		<TD class="boxTexto" align="center" height="50" width="161">
			<a name="btnEnviar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">PRÓXIMO</a>
		</TD>
	</TR>
</TABLE>

</form>
<IFRAME src="Cep.jsp" frameborder="0" name="cep" width="0"
	marginwidth="0" height="0" marginheight="0" id="cep" scrolling="no"
	style="position: relative; left: 0px; top: 0px"> </IFRAME>
<script>
//deixe esse js no final, acredite é melhor!..rs
function onChange(obj){
	document.getElementById('nomMotivo').value='';
	if(obj.options[obj.selectedIndex].value=='6'){
		document.getElementById('campo-six').style.display = 'block'; 		
	}else{
		document.getElementById('campo-six').style.display = 'none'; 		
	}
}
if(<c:out value="${CotacaoBean.codMotivo}" />=='6'){
	document.getElementById('campo-six').style.display = 'block';
}


</script>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/MSCad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
  
</script>

</BODY>
</HTML>
