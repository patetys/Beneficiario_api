<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>INDEX</TITLE>
</HEAD>

<BODY>
<SCRIPT language="javascript">
function setActionCadastro() {
	document.form1.action.value="menucad";
}
function setActionPesquisa() {
	document.form1.action.value="menupesq";
}
function setActionComissaoAuto() {
	document.form1.action.value="adesaoAuto";
}
function setActionComissaoDiv() {
	document.form1.action.value="adesaoDiv";
}

</SCRIPT>
	<form name="form1" method="POST" action="./Controller">
		<input type=hidden name="action" value="">
		<input type=hidden name="login" value="ysnt15">
		<table border="0"  width="100" cellpadding="5" cellspacing="0">
			<TR>
				<TD class="boxTitulo">NumSusep: </TD>
			</TR>
			<TR>
				<TD class="boxTexto" width="100">
					<input type=text name="u" value="00001015">
				</TD>
			</TR>
			<TR>
				<TD class="boxTexto" align="center">
					<input type=submit name=btnCad value="Cadastro" onclick="javascript: setActionCadastro();">
				</TD>
				<TD class="boxTexto" align="center">
					<input type=submit name=btnPesq value="Pesquisa" onclick="javascript: setActionPesquisa();">
				</TD>
				<TD class="boxTexto" align="center">
					<input type=submit name=btnComissAuto value="ComissaoAuto" onclick="javascript: setActionComissaoAuto();">
				</TD>
				<TD class="boxTexto" align="center">
					<input type=submit name=btnComissDiv value="ComissaoDiv" onclick="javascript: setActionComissaoDiv();">
				</TD>
			</TR>
		</table>
	</form>
	
	

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/index.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>

</HTML>
