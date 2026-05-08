<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML xmlns:eba>
<HEAD>
<TITLE></TITLE>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">

  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="chosen/sompo_chosen.css">
  <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
  <script src="js/componentes.js"></script>
  <style>.chosen-container{font-size: 11px; max-width: 200px !important;}</style>
    
<link type="text/css" rel="stylesheet" href="css/outlook/combo.css">
<c:set var="sompo" value="${sessionScope['sompo']}" />
<c:if test="${sompo == 'Sompo'}">
	<link rel="stylesheet" href="css/sompo_yasuda.css" type="text/css">
	<script language="javascript" type="text/javascript" src="js/sompo_datetimepicker.js" charset="UTF-8"></script>
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<link rel="stylesheet" href="css/yasuda.css" type="text/css">
	<script language="javascript" type="text/javascript" src="js/datetimepicker.js"></script>
</c:if>
<!-- 	<script type="text/javascript" language="javascript" src="js/ebacombo.js"></script> -->
<script src="chosen/chosen.jquery.js" type="text/javascript"></script>
	
<SCRIPT language="JavaScript" src="js/consistencias.js" charset="UTF-8"></SCRIPT>
<SCRIPT  language="JavaScript" type="text/javascript" src="js/menupesq.js"></SCRIPT>

<script language="JavaScript">

	$().ready(function(){
		$("[data-toggle='tooltip']").tooltip(); 
		var listaCorretor 	= $("#lstCorretorJson").val();
		var listaRamo 		= $("#ramosDivJson").val();
		var listaDepto 		= $("#lstDepartamentoJson").val();
		var listaProdutor 	= $("#lstProdutorJson").val();
	
		loadChosen("cCorretor", listaCorretor, "text", "corretor", "corretor");
		loadChosen("cRamo", listaRamo, "text", "nome", "nome");
		loadChosen("cDepartamento", listaDepto, "text", "codigo", "nome");
		loadChosen("cProdutor", listaProdutor, "text", "codigo", "nome");

		var CodCorrVolta  = $("#CodCorrVolta").val();
		var CodDeparVolta = $("#CodDeparVolta").val();
		var CodRamoVolta  = $("#CodRamoVolta").val();
		var CodProdVolta  = $("#CodProdVolta").val(); 		

		if(CodCorrVolta != null && CodCorrVolta != ""){
			 selectedChosen("cCorretor", CodCorrVolta);
		}
		if(CodDeparVolta != null && CodDeparVolta != "" && CodDeparVolta != "0"){
			 selectedChosen("cDepartamento", CodDeparVolta);
		}
		if(CodRamoVolta != null && CodRamoVolta != ""){
			 selectedChosen("cRamo", CodRamoVolta);
		}
		if(CodProdVolta != null && CodProdVolta != ""){
			 selectedChosen("cProdutor", CodProdVolta);
		}

	});
        
function initialize(){
	try {
// 		InitializeEbaCombos();
	} catch (err){
		//alert("Initializing the combo boxes failed. Is the .js file correctly referenced and being served by your server?");
	}
}

function AlterarProdutor() {
	if (document.frm1.cDepartamento.value != "0" && document.frm1.cDepartamento.value != "") {
		document.frm1.action.value="buscaProdutor";
		document.frm1.submit();
		return true;		
	}
}

function submitPesquisaCotacao() {	
	if (document.frm2.cCotacao.value==""){
		if (document.frm2.nomeProponente.value=="" &&
			document.frm2.numCpfCnpj.value=="" &&
			document.frm2.dataInicioII.value=="" &&
			document.frm2.dataFimII.value==""
			) {
				alert("Ao menos um parâmetro de pesquisa deve ser especificado");
				return false;
		}else if((document.frm2.dataInicioII.value!="" ||
				document.frm2.dataFimII.value!="")&& document.frm2.nomeProponente.value=="" &&
				document.frm2.numCpfCnpj.value==""){
					alert("Para pesquisa somente por data utilize os campos acima.");
					return false;
		}else if (document.frm2.dataInicioII.value=="" ||
				document.frm2.dataFimII.value=="") {
				alert("O 'Período da Solicitação' é obrigatório.");
				return false;

		}else if (comparaDatas(document.frm2.dataInicioII.value,document.frm2.dataFimII.value)<0) {
			alert("A data final deve ser maior do que a data inicial");
			return false;
			
		}else if (!validaPeriodo(document.frm2.dataInicioII.value,document.frm2.dataFimII.value)) {
			alert("O período informado é maior do que o permitido (2 anos).");
			return false;
			
		}else {
			if (!validaData(document.frm2.dataInicioII.value)) {
				alert("O campo 'Data' inicial deve estar no formato DD/MM/AAAA");
				return false;
			}
			if (!validaData(document.frm2.dataFimII.value)) {
				alert("O campo 'Data' final deve estar no formato DD/MM/AAAA");
				return false;
			}
	
		}
	}	
	
	document.frm2.action.value="pesqcotacao";
	document.frm1.dataInicio.value="";
	document.frm1.dataFim.value="";
	document.frm2.submit();
}

function submitPesquisaPeriodo() {
	if (document.frm1.dataInicio.value=="" ||
				document.frm1.dataFim.value=="") {
				alert("O 'Período da Solicitação' é obrigatório.");
				return false;

	} else {
			if (!validaData(document.frm1.dataInicio.value)) {
				alert("O campo 'Data' inicial deve estar no formato DD/MM/AAAA");
				return false;
			}
			if (!validaData(document.frm1.dataFim.value)) {
				alert("O campo 'Data' final deve estar no formato DD/MM/AAAA");
				return false;
			}
	
	}

	if (comparaDatas(document.frm1.dataInicio.value,document.frm1.dataFim.value)<0) {
		alert("A data final deve ser maior do que a data inicial");
		return false;
	}
	if (limitaDatas(document.frm1.dataInicio.value,document.frm1.dataFim.value,3)){
		alert("Pesquisa não permitida, permitido apenas intervalo menor que 3 meses");
		return false;
	}
	
	if(document.frm1.intranet.value == 1 && document.frm1.cCorretor.value == ""  && document.frm1.cDepartamento.value == "" && document.frm1.cProdutor.value == "" && document.frm1.cRamo.value == ""){
		alert("Escolha uma das opções de pesquisa: Corretor, Ramo, Departamento ou Produtor");
		return false;		
	}
	
	document.frm1.action.value="pesqperiodo";
	document.frm2.cCotacao.value="";
	document.frm2.nomeProponente.value="";
	document.frm2.numCpfCnpj.value="";
	document.frm1.submit();
}

function comparaDatas(dtIni, dtFim) {
	var dIni = parseInt(dtIni.substring(6)+dtIni.substring(3,5)+dtIni.substring(0,2));
	var dFim = parseInt(dtFim.substring(6)+dtFim.substring(3,5)+dtFim.substring(0,2));
	var dif = dFim-dIni;
	return dif;
}

function validaPeriodo(dtIni, dtFim){
	var dIni = parseInt(dtIni.substring(6)+dtIni.substring(3,5)+dtIni.substring(0,2));
	var dFim = parseInt(dtFim.substring(6)+dtFim.substring(3,5)+dtFim.substring(0,2));
	var dif = (dFim-dIni) / 20000;
    if(dif > 1)
        return false;
    return true;
}

function limitaDatas(dtIni, dtFim, qtdMeses) {
	var auxQtdMeses = qtdMeses * 100;
	var dif = 0;
	var dIni = parseInt(dtIni.substring(6)+dtIni.substring(3,5)+dtIni.substring(0,2));
	var dFim = parseInt(dtFim.substring(6)+dtFim.substring(3,5)+dtFim.substring(0,2));
	if(dtIni.substring(6) == dtFim.substring(6)){
		dif = dFim-dIni;
	}
	else if(dtIni.substring(6) == dtFim.substring(6) - 1){		
		dif = ((dtIni.substring(6)+"1231")-dIni)+(dFim-(dtFim.substring(6)+"0101"))
		if(dif > 230){
			dif = auxQtdMeses + 1;
		}
	}
	else{
		dif = auxQtdMeses + 1;
	}
	
	if(dif > auxQtdMeses){
		return true;
	}else{
		return false;
	}		
}


function limpaCampos() {
	document.frm2.cCotacao.value="";
	document.frm2.nomeProponente="";
	document.frm2.numCpfCnpj="";
	document.frm1.dataInicio.value="";
	document.frm1.dataFim.value="";
}

</script>
</HEAD>

<BODY OnLoad="initialize();">
<!-- <BODY> -->

<input type="HIDDEN" name="lstCorretorJson" id="lstCorretorJson" value="<c:out value="${requestScope['lstCorretorJson']}"/>" />
<input type="HIDDEN" name="ramosDivJson" id="ramosDivJson" value="<c:out value="${requestScope['ramosDivJson']}"/>" />
<input type="HIDDEN" name="lstDepartamentoJson" id="lstDepartamentoJson" value="<c:out value="${requestScope['lstDepartamentoJson']}"/>" />
<input type="HIDDEN" name="lstProdutorJson" id="lstProdutorJson" value="<c:out value="${requestScope['lstProdutorJson']}"/>" />

<input type="HIDDEN" name="CodCorrVolta" id="CodCorrVolta" value="<c:out value="${requestScope['CodCorrVolta']}"/>" />
<input type="HIDDEN" name="CodDeparVolta" id="CodDeparVolta" value="<c:out value="${requestScope['CodDeparVolta']}"/>" />
<input type="HIDDEN" name="CodRamoVolta" id="CodRamoVolta" value="<c:out value="${requestScope['CodRamoVolta']}"/>" />
<input type="HIDDEN" name="CodProdVolta" id="CodProdVolta" value="<c:out value="${requestScope['CodProdVolta']}"/>" />

<FORM NAME=frm1 ACTION="./Controller" METHOD="POST">
<INPUT TYPE=HIDDEN NAME="action" VALUE="pesqperiodo">
<INPUT TYPE=HIDDEN NAME="intranet" VALUE="<c:out value="${requestScope['intranet']}"/>">
<%
String errMsg=(String)request.getAttribute("errorMessage");
if (errMsg==null)errMsg="";
if (errMsg.length() > 0) {
%>
<font class="legendaVermelha"><%=errMsg%></font>
<%
}
%>

<TABLE border="0" cellpadding="5" cellspacing="0" class="tabelaAction">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="198">Filtro por Período e Situação</TD>
		</TR>
		<TR valign="top">
			<TD height="53" width="198">


			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" width="211" height="14"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">Informe o período da Solicitação :</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">
							<INPUT type="Text" class="boxInputDate" id="dataInicio" name="dataInicio" value="<c:out value="${requestScope['dataInicioVolta']}"/>" maxlength="10" size="08">
							<A href="javascript:NewCal('dataInicio','ddmmyyyy')"><IMG src="images/cal.gif" width="16" height="16" border="0" alt="Selecione a data"></A>  a  
							<INPUT type="Text" class="boxInputDate" id="dataFim" name="dataFim" value="<c:out value="${requestScope['dataFimVolta']}"/>" maxlength="10" size="08">
							<A href="javascript:NewCal('dataFim','ddmmyyyy')"><IMG src="images/cal.gif" width="16" height="16" border="0" alt="Selecione a data"></A>
						</TD>
					</TR>

					<TR>
						<TD class="boxTexto" width="211">dd/mm/aaaa&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dd/mm/aaaa
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211" height="14"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">Situação da cotação :</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">
							<INPUT type="radio" name="sitCotacao" value="1" checked>Todas
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">
							<INPUT type="radio" name="sitCotacao" value="13">Pendentes
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">
							<INPUT type="radio" name="sitCotacao" value="15">Declinadas
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">
							<INPUT type="radio" name="sitCotacao" value="14">Finalizadas
						</TD>
					</TR>
					
					<!-- Hebert -->
					<c:if test="${requestScope['intranet']=='1'}">
						<TR>
							<TD class="boxTexto" height="14" width="211"></TD>
						</TR>
						<TR>
							<TD class="boxTexto" width="211">Corretor :</TD>
						</TR>
						<tr>
<!-- 							<td class="boxTexto" width="256">																							 -->
<!-- 								<eba:Combo id="cCorretor" -->
<!-- 								   Mode="classic">  -->

<!-- 								<eba:ComboTextBox -->
<!-- 						       		type="hidden" -->
<!-- 								   Width="185px" -->
<%-- 								   Value="<c:out value="${requestScope['CodCorrVolta']}"/>" --%>
<!-- 								   DataValueIndex=0 -->
<!-- 								   	DataFieldIndex=2 -->
<!-- 							    	> -->
<!-- 								</eba:ComboTextBox>														 -->
<!-- 								<eba:ComboList -->
<!-- 							   		Width="300x" -->
<!-- 							   		Height="105px" -->
<!-- 							   		DatasourceUrl="ebaCorretor.jsp?op=corretor" -->
<!-- 							   		PageSize="10">					 -->
<!-- 							   		<eba:ComboColumnDefinition Width="50px" HeaderLabel="Código" DataValueIndex=0 DataFieldIndex=0 ></eba:ComboColumnDefinition> -->
<!-- 									<eba:ComboColumnDefinition Width="180px" HeaderLabel="Corretor" DataValueIndex=0 DataFieldIndex=1 ></eba:ComboColumnDefinition> -->
<!-- 								</eba:ComboList> -->
<!-- 							</eba:Combo> -->
<!-- 							</td>													 -->
						
							<TD class="boxTexto" height="26" width="205px" >		
   	   				            <select name="cCorretor" size="1" id="cCorretor" style="width: 205px;">
    						      	 <option value="">Selecione</option>
    							</select>							
							</TD>
						</tr>		
						<TR>
							<TD class="boxTexto" height="14" width="256">Ramo :</TD>
						</TR>
						<TR>
							<TD class="boxTexto" height="26" width="256">		
   	   				            <%--<select class="boxInput" name="codRamo" size="1" id="codRamo" OnChange="javascript: CmbRamoChanged(<%= request.getAttribute("CodModalidadeVolta")%>);" style="width: 205px" >--%>
   	   				            <select class="boxInput" name="cRamo" size="1" id="cRamo" style="width: 205px" >
    						    <c:forEach items="${requestScope['RamosDiv']}" var="ramos" 	varStatus="status"  >
    						      	 <%--<option value="<c:out value="${ramos}" />" <c:if test="${param['codRamo']==ramos || ramos == requestScope['CodRamoVolta']}"><c:out value="selected" /></c:if> ><c:out value="${ramos}" /></option>--%>
    						      	 <option value="<c:out value="${ramos}" />" <c:if test="${requestScope['CodRamoVolta']==ramos}"><c:out value="selected" /></c:if> ><c:out value="${ramos}" /></option>
    						 	</c:forEach>
    							</select>							
							</TD>
						</TR>	
									
						<TR>
							<TD class="boxTexto" height="14" width="256">Departamento :</TD>
						</TR>
						<TR>				
						  <TD class="boxTexto" height="26" width="256">
    					  	<select onchange = "javascript: AlterarProdutor();" class="boxInput" id="cDepartamento" name="cDepartamento" size="1"  style="width: 205px" >
    					  	<option value=""></option>
    						<c:forEach var="departamento" items="${requestScope['lstDepartamento']}">    						
	    						<option value="<c:out value="${departamento.codigo}" />" <c:if test="${requestScope['CodDeparVolta']==departamento.codigo}"><c:out value="selected" /></c:if> ><c:out value="${departamento.codigo}" /> - <c:out value="${departamento.nome}" /></option> 
    					   	</c:forEach>
    					  	</select>	    							
							</TD>
						</TR>		
						
						<TR>
							<TD class="boxTexto" height="14" width="256">Produtor :</TD>
						</TR>													
						<TR>				
						  <TD class="boxTexto" height="26" width="256">
    				  		<select class="boxInput" name="cProdutor" id="cProdutor" size="1"  style="width: 205px" >
    					  	<option value=""></option>
    						<c:forEach var="produtor" items="${requestScope['lstProdutor']}">    						
	    						<option value="<c:out value="${produtor.key}" />" <c:if test="${requestScope['CodProdVolta']==produtor.key}"><c:out value="selected" /></c:if> ><c:out value="${produtor.key}" /> - <c:out value="${produtor.value}" /></option> 
    				   		</c:forEach>
    				  		</select>	    							
							</TD>
						</TR>																			
					</c:if>	
					<!-- Hebert -->
										
					<TR>
						<TD class="boxTexto" align="center" width="211" height="15"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="center" width="211" height="19">
							<a name="validar0" class="cbutton" onclick="javascript:return submitPesquisaPeriodo();">CONSULTAR</a>
							</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211" height="18"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>

</FORM>

<FORM NAME=frm2 ACTION="./Controller" METHOD="POST">
<INPUT TYPE=HIDDEN NAME="action" VALUE="pesqcotacao">


<P><BR>
</P>	
<TABLE border="0" cellpadding="5" cellspacing="0"  class="tabelaAction">

	<TBODY>

		<TR>
			<TD class="boxTitulo" width="198">Filtro para Pesquisa</TD>
		</TR>
		<TR valign="top">
			<TD height="53" width="198">


			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>					
					<TR>
						<TD class="boxTexto" height="14" width="211"></TD>
					</TR>	
					<TR>
						<TD class="boxTexto" width="211">Informe o período da Solicitação :</TD>
					</TR>	
					<TR>
						<TD class="boxTexto" width="211">
							<INPUT type="Text" class="boxInputDate" id="dataInicioII" name="dataInicioII" value="<c:out value="${requestScope['dataInicioVoltaII']}"/>" maxlength="10" size="08">
							<A href="javascript:NewCal('dataInicioII','ddmmyyyy')"><IMG src="images/cal.gif" width="16" height="16" border="0" alt="Selecione a data"></A>  a  
							<INPUT type="Text" class="boxInputDate" id="dataFimII" name="dataFimII" value="<c:out value="${requestScope['dataFimVoltaII']}"/>" maxlength="10" size="08">
							<A href="javascript:NewCal('dataFimII','ddmmyyyy')"><IMG src="images/cal.gif" width="16" height="16" border="0" alt="Selecione a data"></A>
						</TD>
					</TR>

					<TR>
						<TD class="boxTexto" width="211">dd/mm/aaaa&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;dd/mm/aaaa
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211" height="14"></TD>
					</TR>		
					<TR>
						<TD class="boxTexto" width="211">Nº da Cotação :</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">
							<INPUT type="text" class="boxInput" name="cCotacao" size="17" maxlength="12" value="">
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="8" width="211"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">Nome do Segurado :</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211">
							<INPUT type="text" class="boxInput" name="nomeProponente" size="17" value="" maxlength="70">
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="8" width="211"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" width="211">Cnpj/Cpf :</TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="left" width="211">
							<INPUT type="text" class="boxInput" name="numCpfCnpj" size="17" maxlength="14"
								data-toggle="tooltip" data-placement="right" title="Consulta por CPF/CNPJ ou apenas a base do CNPJ">
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" align="center" width="211" height="14"></TD>
					</TR>						
					<TR>
						<TD class="boxTexto" align="center" width="211" height="21">
							<a name="validar" class="cbutton" onclick="javascript:return submitPesquisaCotacao();">CONSULTAR</a>
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="211" height="10"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<SCRIPT language="JavaScript">
//CmbRamoChanged(<%= request.getAttribute("CodModalidadeVolta")%>);
</SCRIPT>
<script>
	data = new Date();		
	dia = data.getDate();
	mes = data.getMonth()+1;
	ano = data.getFullYear();
	//dif = 2;
	
	if(dia < 10){
		dia = "0"+dia;
	}
	if(mes < 10){
		mes = "0"+mes;
	}
	if (document.frm2.dataFimII.value == "" && document.frm2.dataInicioII.value == ""){
		document.frm2.dataFimII.value = dia+"/"+mes+"/"+ano;		
		//document.frm2.dataInicioII.value = dia+"/"+mes+"/"+(ano-dif);
		
		dataII = new Date();
		dataII.setMonth(dataII.getMonth() - 6);
		diaII = dataII.getDate();
		mesII = dataII.getMonth()+1;
		anoII = dataII.getFullYear();

		if(diaII < 10){
			diaII = "0"+diaII;
		}
		if(mesII < 10){
			mesII = "0"+mesII;
		}
		document.frm2.dataInicioII.value = diaII+"/"+mesII+"/"+anoII;
			
	}
	if (document.frm1.dataFim.value == "" && document.frm1.dataInicio.value == ""){
		document.frm1.dataFim.value = dia+"/"+mes+"/"+ano;		
		if(mes == 1){
			ano--;
			mes = 12;
		}else{
			mes--;
			if(mes < 10){
				mes = "0"+mes;
			}
		}		
		document.frm1.dataInicio.value = dia+"/"+mes+"/"+ano;			
	}

</script>
</FORM>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/MenuPesq.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
