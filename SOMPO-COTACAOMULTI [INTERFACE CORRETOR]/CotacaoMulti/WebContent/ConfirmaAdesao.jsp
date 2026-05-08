<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page import="java.util.*" %>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<link rel="stylesheet" href="css/yasuda.css" type="text/css">
<TITLE></TITLE>
<SCRIPT>
	var processando = false;
	
	function submeteForm(){
		if (processando){
			alert ("Aguarde, sua solicitação está sendo executada!");
			return false;
		}
	
	 	processando = true;
	  	document.frm1.submit();
	  	return true;
	}
</SCRIPT>

</HEAD>
<%
Calendar data = Calendar.getInstance(new Locale("pt", "br"));
int ano = data.get(Calendar.YEAR);
int tipoAdesao = Integer.parseInt((String)request.getSession().getAttribute("tipoAdesao"));
String tipoDesc="";
if(tipoAdesao == 1)tipoDesc="Automóvel";
if(tipoAdesao == 2)tipoDesc="Ramos Diversos";

%>
<BODY>
<FORM NAME=frm1 ACTION="Controller" METHOD="POST">
<INPUT TYPE=HIDDEN NAME="action" VALUE="protocolo">
<TABLE border="0">
	<TBODY>
		<TR>
			<TD width="711" align="center" height="15"></TD>
		</TR>
		<TR>
			<TD width="711" align="center" height="15"></TD>
		</TR>
		<TR>
			<TD  width="711" class="LegendaProtocolo" align="left">Você realmente deseja aderir ao Programa de Pagamento Adicional de Comissão <%=ano%> - <%=tipoDesc%> da Sompo Seguros?
			</TD>
		</TR>
		<TR>
			<TD width="711" height="15"></TD>
		</TR>
		<TR>
			<TD width="711" align="center" height="27">
					<IMG border="0"	style="cursor: pointer" src="images/botaoConfirmar.gif" onclick="javascript:submeteForm();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href='javascript:history.go(-1);'><IMG border="0" style="cursor: pointer" src="images/botaoCancelar.gif"></a>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/ConfirmaAdesao.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
