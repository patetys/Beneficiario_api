<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
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

<BODY>
<TABLE border="0">
	<TBODY>
		<TR>
			<TD width="711" align="center" height="15"></TD>
		</TR>
		<TR>
			<TD width="711" align="center" height="15"></TD>
		</TR>
		<TR>
<%
		String msg = (String)request.getAttribute("msg");
		
		if (msg==null) {
			msg="";
		}
%>
				<TD  width="711" class="LegendaProtocolo" align="left"><%=msg%>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/MensagemAdesao.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
