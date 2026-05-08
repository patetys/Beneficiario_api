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
<INPUT TYPE=HIDDEN NAME="action" VALUE="confirmacao">
<TABLE border="0">
	<TBODY>
		<TR>
			<TD width="711" align="center" height="15"></TD>
		</TR>
		<TR>
			<TD width="711" class="legendaGrande"><b>Termo de Adesão ao Programa de Comissão Adicional de <br><%=tipoDesc%> de <%=ano%></b><br></TD>
		</TR>
		<TR>
			<TD width="711" align="center" height="15"></TD>
		</TR>
		<TR>
			<TD  class="legendaProtocolo" width="711">Estou ciente e de acordo com os termos do Regulamento do Programa <br> de Comissão Adicional <%=ano%> - <%=tipoDesc%> da Sompo Seguros.<BR>
			<BR></TD>
		</TR>
		<TR>
			<TD width="711" align="center" height="10"></TD>
		</TR>
		<TR>
			<TD width="711" align="center" height="27">
					<INPUT type="image" name="btnAderir" src="images/botaoAderir.gif">
			</TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Adesao.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
