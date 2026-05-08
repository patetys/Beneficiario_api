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
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet"
	type="text/css">
<TITLE>Cotacao - Visualiza Documentos</TITLE>
<LINK rel="stylesheet" type="text/css" href="css/yasuda.css" title="Style">
</HEAD>

<BODY>
<FORM NAME=frm ACTION="Controller" METHOD="POST" >
<INPUT TYPE=HIDDEN NAME="action" VALUE="downloadEmailFile">
<INPUT TYPE=HIDDEN NAME="nameTempFile" VALUE="<%=request.getParameter("file")%>">
<INPUT TYPE=HIDDEN NAME="nameOrigFile" VALUE="<%=request.getParameter("fileorig")%>">
<INPUT TYPE=HIDDEN NAME="novaConsulta" VALUE="<%=request.getParameter("flag")%>">
<INPUT TYPE=HIDDEN NAME="cCotacao" VALUE="<%=request.getParameter("cCotacao")%>">
</FORM>

<SCRIPT>
	  	document.frm.submit();
</SCRIPT>


<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/VisualizaDoc.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
