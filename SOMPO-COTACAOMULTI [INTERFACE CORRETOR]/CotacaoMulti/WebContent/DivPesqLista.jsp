<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%@ page import="java.util.*" %>
<%@ page import="bean.CotacaoBean"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<c:set var="sompo" value="${sessionScope['sompo']}" />
<c:if test="${sompo == 'Sompo'}">
	<link rel="stylesheet" href="css/sompo_yasuda.css" id="cssDinamico" type="text/css">
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<link rel="stylesheet" href="css/yasuda.css" id="cssDinamico" type="text/css">
</c:if>
<TITLE>Sompo - Lista de Segurados</TITLE>

<script language="JavaScript">

function ValidaCSS(){
	var FILE = "css/sompo_yasuda.css";
	if (document.getElementById("canal").value == "BPM"){
		FILE = "css/sompo_yasuda_bpm.css"
	}
	document.getElementById('cssDinamico').setAttribute('href', FILE);
}
</script>

</HEAD>

<BODY onLoad="ValidaCSS()" leftmargin="0" topmargin="0" marginheight="0" marginwidth="0">

<SCRIPT language="JavaScript">
var processando = false;

function SubmeteForm(ct){
	if (processando){
		alert ("Aguarde, sua consulta já está sendo processada!");
		return false;
	}
	//processando = true;
	document.frm1.cCotacao.value=ct;
	document.frm1.action.value="msdetalhe2";
	document.frm1.submit();	

}

function downloadFile(file,fileorig,flag, cotacao){
	window.open('/CotacaoMulti/VisualizaDoc.jsp?cCotacao='+ cotacao +'&flag='+ flag +'&file='+file+'&fileorig='+fileorig,'Documento','fullscreen=no,border=yes,toolbar=no,location=no,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes');
}

</SCRIPT>

<FORM NAME=frm1 ACTION="Controller" METHOD="POST">
<INPUT TYPE=HIDDEN NAME="action" VALUE="msdetalhe2">	
<INPUT TYPE=HIDDEN NAME="cCotacao" VALUE="">	
<INPUT TYPE="HIDDEN" name="entWorkflow" value="<%=request.getAttribute("entWorkflow")%>"/>
<INPUT TYPE="HIDDEN" NAME="canal" id="canal" value="<%=request.getAttribute("canal")%>"/>
</FORM>

<c:choose>
	<c:when test="${requestScope['intranet']=='1'}"><c:set var="wid" value="670"/></c:when>
	<c:otherwise><c:set var="wid" value="495"/></c:otherwise>
</c:choose>

<table border="0" width=<c:out value="${wid}"/> cellpadding="5" cellspacing="1">

	<tr>
		<c:if test="${requestScope['novaConsulta']=='1'}">
			<td class="tabelaHeader" width="130">Data da cotação</td>
		</c:if>
			<td class="tabelaHeader" width="80">Cotação</td>
		<c:if test="${requestScope['novaConsulta']!='1'}">
			<td class="tabelaHeader" width="200">Segurado</td>
			<td class="tabelaHeader" width="25">Ramo</td>
		</c:if>
		<c:if test="${requestScope['intranet']=='1' && requestScope['novaConsulta']!='1'}">
			<td class="tabelaHeader" width="100">Prêmio (R$)</td>
		</c:if>
		<td class="tabelaHeader" width="140">Situação</td>
		<!--Hebert-->
		<c:if test="${requestScope['intranet']=='1' || requestScope['novaConsulta']=='1'}">
			<td class="tabelaHeader" width="140">Corretor</td>
			<c:if test="${requestScope['novaConsulta']!='1'}">
				<td class="tabelaHeader" width="50">Unidade</td>
				<td class="tabelaHeader" width="200">Produtor</td>
			</c:if>
			<c:if test="${requestScope['novaConsulta']=='1'}">
				<td class="tabelaHeader" width="160">Anexos</td>
			</c:if>
		</c:if>
		<!--Hebert-->		
	</tr>

<%
	HashMap<String,CotacaoBean> mapArquivosBeansVisualiza = new HashMap<String, CotacaoBean>();
	Vector list = (Vector)request.getAttribute("listaCotacoes");
	for (int i=0; i<list.size(); i++) {
		Hashtable item = (Hashtable)list.get(i);
%>	
	
  	
	<tr class="tabelaLinha1">	
	
	    <c:if test="${requestScope['novaConsulta']=='1'}">
			<td width="130"><%=item.get("dataCotacao") %></td>
		</c:if>
		
		<td width="80">
			<a href="#" onclick="javascript:return SubmeteForm(<%= item.get("cCotacao")%>);" class="linkMenu"><%= item.get("cCotacao")%></a>				
		</td>
		<c:if test="${requestScope['novaConsulta']!='1'}">
			<td width="200"><%= item.get("nomeProponente")%></td>
			<td width="25" align="center"><%= item.get("cRamo")%></td>
		</c:if>
		<c:if test="${requestScope['intranet']=='1' && requestScope['novaConsulta']!='1'}">
			<td width="100" align="right"><%= item.get("valorPremio")%></td>
		</c:if>
		<td width="140"><%= item.get("cotSituacao")%><%= item.get("cotFinalData")%></td>
		<c:if test="${requestScope['intranet']=='1' || requestScope['novaConsulta']=='1'}">
			<td width="140"><%= item.get("aCorretor")%></td>
			<c:if test="${requestScope['novaConsulta']!='1'}">
				<td width="50" align="center"><%= item.get("cDepartamento")%></td>
				<td width="200" align="center"><%= item.get("cProdutor")%></td>
			</c:if>
			<c:if test="${requestScope['novaConsulta']=='1'}">
				<td  width="300">
					<table>
						
							<%
								
								CotacaoBean cotBean = (CotacaoBean) item.get("beanAnexos");
								HashMap anexos = cotBean.getArqsEmailCotacao();
								Iterator it = anexos.keySet().iterator();
								int quebra = 1;
								int multiplo = 3;
								int res = 0;
								int cont = 0;
								mapArquivosBeansVisualiza.put((String) item.get("cCotacao"), cotBean);
									while(it.hasNext()){
										res = quebra%multiplo;
										String key = (String) it.next();
										HashMap map = (HashMap) anexos.get(key);
										
										
						if(res == 0){
							
							if(quebra > 1){ 
							%>
							</TR>
							<%} %>
						<TR>	
							<td width="200">
								<a href="#" onclick="javascript:downloadFile('<%=map.get("tempFile")%>','<%=map.get("fileName")%>', '1', '<%= item.get("cCotacao")%>');" class="linkMenu"><%=map.get("fileName")%></a>
							</td>
						
						<%}else{ %>
							<td width="200">
								<a href="#" onclick="javascript:downloadFile('<%=map.get("tempFile")%>','<%=map.get("fileName")%>', '1','<%= item.get("cCotacao")%>');" class="linkMenu"><%=map.get("fileName")%></a>
							</td>
							
						<%} %>
	
						<%
						quebra++;
						} %>
							</TR>	
							
								    
					</table>	
				</td>
			</c:if>
		</c:if>
	</tr>
	
<%
	}
	
	request.getSession().setAttribute("mapArquivosBeansVisualiza", mapArquivosBeansVisualiza);
%>	
		
</TABLE>
<BR>
<table border="0" width=<c:out value="${wid}"/> cellpadding="0" cellspacing="0">
<tr>
		<td width="100%" align="center"><a href='javascript:history.go(-1);' class="cbutton">VOLTAR</a></td>
</tr>
</table>

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/DivPesqLista.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</BODY>
</html>