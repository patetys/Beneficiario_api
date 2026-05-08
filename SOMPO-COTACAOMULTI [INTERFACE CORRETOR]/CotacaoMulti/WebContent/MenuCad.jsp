<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns:eba>
<HEAD>
<%@ page 
language="java"

contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
%>
<%@ page import="java.util.*" %>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META name="GENERATOR" content="IBM WebSphere Studio">

<%
Random random = new Random(); 
String valorRan = "" + random.nextInt();
valorRan = valorRan.trim();
%>

<TITLE>Cotação</TITLE>
<SCRIPT language="JavaScript" src="js/consistencias.js" charset="UTF-8"></SCRIPT>
<script type="text/javascript" language="javascript" src="js/ebacombo.js"></script>
<SCRIPT  language="JavaScript" type="text/javascript" src="js/menupesq.js?param=<%=valorRan%>" charset="UTF-8"></SCRIPT>
<script language="javascript" type="text/javascript" src="js/jquery-1.4.2.js"></script>
<script language="javascript" type="text/javascript" src="js/jquery.flexbox.min.js"></script>
<script language="javascript" type="text/javascript" src="js/jquery.bgiframe-2.1.1.js"></script>
<c:set var="sompo" value="${sessionScope['sompo']}" />
<c:if test="${sompo == 'Sompo'}">
	<LINK rel="stylesheet" type="text/css" href="css/sompo_yasuda.css" title="Style">
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<LINK rel="stylesheet" type="text/css" href="css/yasuda.css" title="Style">
</c:if>
<link type="text/css" rel="stylesheet" href="css/outlook/combo.css">
<link rel="stylesheet" type="text/css" href="css/jquery.flexbox.css" />
</HEAD>

<BODY OnLoad="initialize();">
<!--
<BODY onload="javascript: return limpaVar();">
-->
<script language="JavaScript" charset="UTF-8">
function initialize(){
	
}

$(document).ready(function() {  	
	
	$('#corretor').flexbox('Controller?action=buscacorretor', {  
		 autoCompleteFirstMatch: false,  
		 //watermark: '<c:out value="${corretor.codCorretor}" /> <c:out value="${corretor.corretor}" /> / <c:out value="${corretor.cidCorretor}" />',
		 noResultsText: 'Não localizado...',
		 minChars: 3,
		 width: 205, 
		 onSelect: function() {  
	          $.getJSON('Controller?action=buscacorretor', {q:this.getAttribute('hiddenValue'),p:1,s:1,select:'s'}, 
	          	function(data){
		          $.each(data.results, function(i,item){		          
		         
		          	$('#corretor_input').val(item.name);
		          	$('#cCorretor').val(item.name);
		          	document.frm1.action.value="buscaUnidade";
					document.frm1.submit();
					return true;
															      
		          });
		      });
		 }  
	 });

	
	
	$('.ffb').bgIframe();
    $('.watermark').val('<c:out value="${corretor_input}" />'.replace(/&amp;/g,'&'));
   
 });

function AlterarCorretor(Form) {
	if (document.frm1.cCorretor.value != "0" && document.frm1.cCorretor.value != "") {
		document.frm1.action.value="buscaUnidade";
		document.frm1.submit();
		return true;		
	}
}
function ValidarCamposEnviar(Form) {

//Retirado chamada para nova tela 
//	if(document.frm1.codRamo.value == "510 - Responsabilidade Civil" || document.frm1.codRamo.value == "670 - Riscos de Engenharia"){
//		document.frmNew.submit();
//		return false;
//	}

	 if (document.frm1.cCorretor.value == "0" || document.frm1.cCorretor.value == "") {
		alert("Selecione o corretor.");
		return false;
	}
	if (!validaNumero( document.frm1.cCorretor.value.substring(1,7))) {
		alert("Selecione o corretor.");
		return false;
	}		
	if (document.frm1.cUnidade.value == "0" || document.frm1.cUnidade.value == "") {
		alert("Selecione a unidade.");
		return false;
	}			
	if (document.frm1.tpCotacao[0].checked) {
		document.frm1.cCotacao.value="";
	}
	if (document.frm1.tpCotacao[1].checked &&
		document.frm1.cCotacao.value=="") {
		alert("Favor preencher o número da recotação!");
		return false;
	} else if (document.frm1.tpCotacao[1].checked &&
		document.frm1.cCotacao.value!="") {
		if (document.frm1.cCotacao.value.length < 12 ||
			!validaNumero(document.frm1.cCotacao.value)) {
			alert("O número de recotação deve ter 12 dígitos!");
			return false;
		}
		document.frm1.action.value="cadrecotacao";
		document.frm1.submit();
		return true;
	}
	
		
	if (document.frm1.codRamo.selectedIndex < 1) {
   	   alert("O campo 'Informe Ramo' é obrigatório");	
   	   return false; 
	}   

	if ((document.frm1.codMod.disabled==false)) {
	    if (document.frm1.codMod.selectedIndex < 1 || document.getElementById("codMod").options.item(document.frm1.codMod.selectedIndex).text == ""){
         	alert("Para este ramo você deve selecionar uma modalidade");
		   	return false;	
		}					   	
		
		//Caso sejam os ramos 510 ou 670, obtem o codigo da modalidade pela descricao da mesma
		
		
		if (document.frm1.codRamo.value.indexOf('510', 0) === 0
				||document.frm1.codRamo.value.indexOf('670', 0) === 0) {
	   		var descModalidade = document.frm1.codMod.options.item(document.frm1.codMod.selectedIndex).text;
	   		var codModalidade = descModalidade.split("-")[0].trim();
	   		document.frm1.codigoModalidade.value = codModalidade;
		}
		
		if (document.frm1.codRamo.value.indexOf('780', 0) === 0) {
	   		var descModalidade = document.frm1.codMod.options.item(document.frm1.codMod.selectedIndex).text;
	   		var codModalidade = descModalidade.split("-")[0].trim();
	   		document.frm1.codMod.value = codModalidade;
		}
	}   
		
	
	var indTipEmissao = 0;
	var textoTipEmissao = "";	
	
	indTipEmissao = document.frm1.TipEmissao.selectedIndex;   		        	
    textoTipEmissao = document.getElementById("TipEmissao").options.item(indTipEmissao).text;    
  		
	if ((textoTipEmissao=="")) {
   	   	  alert("O campo 'Tipo de Emissão' é obrigatório");	
   	      return false;
	}   				
				
	if (textoTipEmissao == "Seguro Novo" || textoTipEmissao == "Renov Congênere") {
		document.frm1.numApol.value="";		
	}
				
	if ((textoTipEmissao == "Endosso" || textoTipEmissao == "Renov Yasuda / Marítima" || textoTipEmissao == "Renov Sompo") && document.frm1.numApol.value == "") {
		alert("Favor preencher o número da Apólice!");		
		return false;
	} else if ((textoTipEmissao == "Endosso" || textoTipEmissao == "Renov Yasuda / Marítima" || textoTipEmissao == "Renov Sompo") && document.frm1.numApol.value != "") {
		if (document.frm1.numApol.value.length < 10 || !validaNumero(document.frm1.numApol.value)) {
			alert("O número da apólice deve ter 10 dígitos!\n"
					+ "Para as renovações Marítima, complementar o número da apólice com zeros a esquerda.");
			
			return false;
		} else if (document.frm1.numApol.value + 0 == 0) {
				alert("O número de apólice inválido.");
				return false;
		}		
	}
			
	document.frm1.action.value="cadcotacao";
	document.frm1.submit();
	
}



</script>


<FORM NAME=frm1 ACTION="Controller" METHOD="POST" >
<INPUT TYPE="HIDDEN" NAME="codigoModalidade" VALUE="">
<INPUT TYPE="HIDDEN" NAME="action" VALUE="cadcotacao">
<input type="HIDDEN" name="CodCorrVoltaJs" value="<c:out value="${requestScope['CodCorrVolta']}" />" />

<input type="hidden" name="cod_hierarquico" value="<c:out value="${param.cod_hierarquico}" />" />
<input type="hidden" name="cod_filial" value="<c:out value="${param.cod_filial}" />" />

<TABLE border="0" cellpadding="5" cellspacing="0" class="tabelaAction">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="210">Cotação Diversos</TD>
		</TR>
		<TR valign="top">
			<TD height="53" width="205">


			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="14" width="256">Informe Corretor:</TD>
					</TR>

					<c:if test="${requestScope['intranet']!='1' || requestScope['codUnidade']=='2531' }">
						<TR>				
						  <TD class="boxTexto" height="26" width="256">  					    
	    				  	<select onchange = "javascript: AlterarCorretor();" class="boxInput" name="cCorretor" size="1"  style="width: 205px" >
	    				  	<c:if test="${requestScope['CodCorrVolta']!=''}">
		    				  	<option value = "0"></option>
		    				 </c:if>
	    					<c:forEach var="corretor" items="${requestScope['lstCorretor']}">
	    						<option value="<c:out value="${corretor.value}" />" <c:if test="${requestScope['CodCorrVolta']==corretor.key}"><c:out value="selected" /></c:if> ><c:out value="${corretor.value}" /></option> 
	    				   	</c:forEach>
	    				  	</select>	    							
							</TD>
						</TR>
					</c:if>
					<c:if test="${requestScope['intranet']=='1' && requestScope['codUnidade']!='2531' }">
									<tr>
										<td class="boxTexto" width="256" style="height: 25px">
											<%-- jquery aplicado --%>
											<div id="corretor" style="margin-top: -8px"></div> 
											<input	type="hidden" name="cCorretor" id="cCorretor" value="<c:out value="${cCorretor}" />" />
											<INPUT TYPE=HIDDEN NAME="corretor_input" id="corretor_input" VALUE="N" />
										    <%-- jquery aplicado --%>
										</td>
									</tr>
								</c:if>
					<TR>
						<TD class="boxTexto" height="14" width="256">Informe a unidade:</TD>
					</TR>
					<TR>				
					  <TD class="boxTexto" height="26" width="256">
    				  	<select class="boxInput" name="cUnidade" size="1"  style="width: 205px">
    					<c:forEach var="unidade" items="${sessionScope['lstUnidade']}">    						
	    						<option value="<c:out value="${unidade.key}" />"
	    						 	<c:if test="${requestScope['CodUnidVolta']==unidade.key}"><c:out value="selected" /></c:if> ><c:out value="${unidade.value}" />
	    						 </option> 
    				   	</c:forEach>
    				  	</select>	    							
						</TD>
					</TR>	
					<TR>
						<TD class="boxTexto" height="14" width="256">Informe Ramo: </TD>
					</TR>					
					<TR>
						<TD class="boxTexto" height="26" width="256">							
   	   			            <select class="boxInput" name="codRamo" size="1" id="codRamo" OnChange="javascript: CmbRamoChanged(<%= request.getAttribute("CodModalidadeVolta")%>,<%=request.getSession().getAttribute("intranet") %>);" style="width: 205px" >    		   	   			            			 	
    					    <c:forEach items="${requestScope['RamosDiv']}" var="ramos" 	varStatus="status"  >
				      	 		<option value="<c:out value="${ramos}" />" <c:if test="${param['codRamo']==ramos || ramos == requestScope['CodRamoVolta']}"><c:out value="selected" /></c:if> ><c:out value="${ramos}" /></option>
    					 	</c:forEach>
    						</select>								
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="14" width="256">Modalidade :</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="26" width="256">
						
   	   			            <select class="boxInput"  name="codMod" id="codMod" size="1" OnChange="javascript: CmbModalidadeChanged(<%=request.getSession().getAttribute("intranet") %>);" <c:if test="${requestScope['CodModalidadeVolta']==0 ||requestScope['CodModalidadeVolta']==null}"><c:out value="disabled" /></c:if> style="width: 205px" >
     					       <option value="00"></option>  
     					       <option value="01"></option>  
       					       <option value="02"></option>       					                           					              					              
							   <option value="03"></option>  
       					       <option value="04"></option>  
 							   <option value="05"></option>  
       					       <option value="06"></option>       					                           					              					              
							   <option value="07"></option>  
       					       <option value="08"></option>  
 							   <option value="09"></option>
 							   <option value="10"></option>  							          	
 							   <option value="11"></option>
 							   <option value="12"></option>
 							   <option value="13"></option>				    
 							   <option value="14"></option>
 							   <option value="15"></option>
 							   <option value="16"></option>
 							   <option value="17"></option>        					            					            					       
    						</select>	
    												
						</TD>
					</TR>					
					<TR>
						<TD class="boxTexto" height="16" width="256">Tipo de Emissão:</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="23" width="256">
    			            <select class="boxInput" id="TipEmissao" name="TipEmissao" size="1"  onChange="javascript: CmbTpEmissaoChanged();" style="width: 205px">    					    
    					    <c:forEach items="${requestScope['TpEmissao']}" var="tpems" varStatus="status"  >
    					      	 <option value="<c:out value="${tpems}" />" <c:if test="${param['TipEmissao']==tpems || status.index == requestScope['TipEmissaoVolta'] }"><c:out value="selected" /></c:if> ><c:out value="${tpems}" /></option>       					 	
    					 	</c:forEach>
    						</select>
    					</TD>
    					
					</TR>
					<TR>
						<TD class="boxTexto" height="15" width="256">Apolice:</TD>
					</TR>
					<TR>
						<c:set var="apolVolta" value="${requestScope['NumApolVolta']}" />
						<TD class="boxTexto" height="23" width="256"><INPUT type="text"
							class="boxInput" name="numApol" size="14" maxlength="10"
							value="<c:if test="${apolVolta!=null && apolVolta!=0}"><c:out value="${apolVolta}" /></c:if>" style="width: 205px"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="22" width="256">Tipo de cotação :</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="26" width="256">
							<INPUT type="radio" name="tpCotacao" value="1" onClick="javascript: tipoCotacaoChanged();" <c:if test="${requestScope['TpCotacaoVolta']==1 || requestScope['TpCotacaoVolta']==null}"><c:out value="checked" /></c:if> >Cotação 
							<INPUT type="radio" name="tpCotacao" value="2" onClick="javascript: tipoCotacaoChanged();" <c:if test="${requestScope['TpCotacaoVolta']==2}"><c:out value="checked" /></c:if> >Recotação
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="17" width="256">N. da Recotação:</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="20" width="256">
							<c:set var="protocoloVolta" value="${requestScope['NumProtocoloVolta']}" />
							<INPUT type="text" class="boxInput" name="cCotacao" size="14" maxlength="12" readonly="readonly" value="<c:if test="${protocoloVolta!=null && protocoloVolta!=0}"><c:out value="${protocoloVolta}" /></c:if>" style="width: 205px">
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="20" width="256"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="14" align="center" width="256">
							<a name="validar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">CONTINUAR</a>
						</TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="14" width="256"></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>

<!-- <p><span class="boxTexto" style="color:#A20A29">Para os ramos 100 D&O, 780 E&O e 510 RC Geral a solicitação de cotação deverá ser efetuado pelos emails: </span></p>
<p><span class="boxTexto" style="color:#A20A29">- RC GERAL - pedidodecotacaoRCG@sompo.com.br </span></p>
<p><span class="boxTexto" style="color:#A20A29">- D&O - pedidodecotacaoD&O@sompo.com.br </span></p>
<p><span class="boxTexto" style="color:#A20A29">- E&O - pedidodecotacaoE&O@sompo.com.br </span></p>
<p><span class="boxTexto" style="color:#A20A29">- Pedidos de Emissão - proposta@sompo.com.br </span></p> -->

<SCRIPT language="JavaScript">
CmbRamoChanged(<%= request.getAttribute("CodModalidadeVolta")%>);
</SCRIPT>
</FORM>
<FORM name="frmNew" action="Controller" METHOD="POST">
	<INPUT TYPE="HIDDEN" NAME="action" VALUE="menucadrc"/>
	<%-- <INPUT TYPE="HIDDEN" NAME="intranet" VALUE="<c:out value="${intranet}"/>"/>
	<INPUT TYPE="HIDDEN" NAME="numSusep" VALUE="<c:out value="${numSusep}"/>"/>
	<INPUT TYPE="HIDDEN" NAME="login" VALUE="<c:out value="${login}"/>"/> --%>
</FORM>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/MenuCad.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
