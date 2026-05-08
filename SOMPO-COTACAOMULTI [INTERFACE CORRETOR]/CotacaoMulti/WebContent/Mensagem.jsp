<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<%if("Sompo".equalsIgnoreCase((String) request.getSession().getAttribute("sompo"))){ %>
	<link rel="stylesheet" href="css/sompo_yasuda.css" type="text/css">
<%}else{ %>
	<link rel="stylesheet" href="css/yasuda.css" type="text/css">
<%} %>

<script type="text/javascript">
function fnDireciona(){
	document.frm1.submit();
}
</script>
<TITLE></TITLE>
</HEAD>

<BODY>
<FORM NAME=frm1 ACTION="Controller" METHOD="POST">
<INPUT TYPE=HIDDEN NAME="action" VALUE="menucad">
<P><BR>
</P>
<TABLE border="0">
	<TBODY>
		<TR>
			<TD width="711"></TD>
		</TR>
		<TR>
			<TD width="711"></TD>
		</TR>
<%
		String msg1 = (String)request.getAttribute("mensagemConfirmacao_1");
		if (msg1 != null) {
%>
		<TR>
			<TD  class="boxTitulo sompo_color" width="711" align="center" ><%= msg1%></TD>
		</TR>
<%
		}
		String msg2 = (String)request.getAttribute("mensagemConfirmacao_2");
		if (msg2 != null) {
%>
		<TR>
			<TD class="boxTitulo sompo_color" width="711" align="center"><%= msg2%></TD>
		</TR>
<%
		}
%>
		<TR>
			<TD width="711" align="center" height="9"></TD>
		</TR>
<%
	String btn=(String)request.getAttribute("mostraBotoes");
	if (btn==null) btn="";
%>
		<TR>
			<TD width="711" align="center" height="27">
				<DIV <%if ("false".equalsIgnoreCase(btn)){%> style="display:none" <%}%>>
					<a name="btnValida" class="cbutton cbuttonMensage" onclick="javascript: fnDireciona();">OK</a>
				</DIV>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Mensagem.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
