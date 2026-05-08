<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
	<link href='css/Styles.css' rel="styleSheet" type="text/css">
<!-- ======================================================= INICIO HTML ======================================== -->
<LINK href="css/default.css" type=text/css rel=stylesheet> 
<LINK href="css/yasuda.css" type=text/css rel=stylesheet>
<body>
<form name="formulario" action="./Controller">
<input type=hidden name="action" value="">
<!-- Tabela Principal -->
<script language="JavaScript">
	function Pesquisa(){
		alert("pesquisa");
		document.formulario.action.value="menupesq";
		document.formulario.submit();
	}
	function Menu(){
		alert("menu");
		document.formulario.action.value="menucad";
		document.formulario.submit();
	}
</script>
<% String u=  request.getParameter("Usuario"); %>
<table border="0" width="198" cellpadding="0" cellspacing="0">
	<tr valign="top">
		<td><img src="images/menuTopo.gif"></td>
	</tr>								
<tr valign="top">
	<td align="center" background="images/menuBackground.gif">
<table border="0" width="164" cellpadding="0" cellspacing="0">
<!--  Cotacao -->
		<tr>
			<td height="10"><img src="images/pixel.gif"></td>
		</tr>
		<tr>
			<td><img src="images/MenuCotacoes.gif"></td>
		</tr>
		<tr>
			<td class="menu">
				<a target="principal" href="./Controller?intranet=1&action=menucad&login=<%=u%>&numSusep=999999" class="linkMenu" >Solicita&ccedil;&atilde;o Ramo Diversos</a>
			</td>
		</tr>
		<tr>
			<td height="1"><img src="images/pixel.gif"></td>
		</tr>
		<tr>
			<td class="menu">
				<a target="principal" href="./Controller?intranet=1&action=menupesq&login=<%=u%>&numSusep=999999" class="linkMenu" >Consulta Ramo Diversos</a>
			</td>
			</tr>
		<tr>
			<td height="1"><img src="images/pixel.gif">
			</td>

		</tr>		<tr>
			<td height="300"><img src="images/pixel.gif">
			</td>
		</tr>	
</table>
<!-- Fim da Tabela Principal -->
	</td>
	</tr>
	<tr valign="top">
	<td><img src="images/menuRodape.gif"></td>
	</tr>	
</table>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/mnuItens.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</body>
</html>