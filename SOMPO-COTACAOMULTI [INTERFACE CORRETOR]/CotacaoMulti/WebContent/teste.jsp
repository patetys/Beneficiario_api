<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE>CotacaoMulti</TITLE>
<%String action = (String) request.getAttribute("action");
if (action == null)
	action = "Teste";
String u = (String) request.getAttribute("u");
if (u == null){
	//u = "00410012";
	//u = "00059412";          
	//u = "12345678";
	//u = "00226165";
	//u = "999999";
	u = "100150355"; 
	//u = "00065498";
}
String codCorretor = (String) request.getAttribute("codCorr");
if (codCorretor == null){
	codCorretor = "0"; 
	//codCorretor = "917372";        
}
String codCliente = (String) request.getAttribute("codCliente");
if (codCliente == null){
	codCliente = "0";          
}
%>

</HEAD>

<BODY>
<SCRIPT language="javascript">
function setActionPesquisa(ac) {
	document.form1.proxaction.value=ac;
	return true;
}


</SCRIPT>
<form name="form1" action="Controller" method="POST">
<input type=hidden name="action" value="<%=action%>"> 
<input type=hidden name="proxaction" value="">
<%if("1".equalsIgnoreCase(request.getParameter("simula"))){ %>
	<input type="hidden" name="intranet" value="<%=request.getAttribute("intranet")%>">
<%} else{%>
	<input type="hidden" name="intranet" value="0"/>
<%} %>

<table border="0" width="100" cellpadding="5" cellspacing="0">

	<TR>
		<TD class="boxTitulo">Simulação:</TD>
		<TD class="boxTitulo">
			<select name="simula">
				<option >PORTAL</option>
				<option value="1">INTRANET</option>
			</select>
		</TD>
	</TR>
	<TR>
		<TD>Login: </TD>	
		<TD><input type="input" name="login" value="AFSilva"/></TD>
	</TR>
	<TR>
		<TD class="boxTitulo">NumSusep:</TD>
	</TR>
	<TR>
		<TD class="boxTexto" width="100"><input type=text name="u" value="<%=u%>"></TD>
	</TR>
	<TR>
		<TD class="boxTexto" width="100"><input type=text name="codCorr" value="<%=codCorretor%>"></TD>
	</TR>
	<TR>
		<TD class="boxTexto" width="100"><input type=text name="codCliente" value="<%=codCliente%>"></TD>
	</TR>

<%
if (action.equalsIgnoreCase("menucad")||action.equalsIgnoreCase("Teste")){%>
	<TR>
		<TD class="boxTexto" align="center"><input type=submit name=btnPesq value="Cadastro"
			onclick="javascript: setActionPesquisa('menucad');"></TD>
	
<%}%>

<%if(action.equalsIgnoreCase("menucadrc")||action.equalsIgnoreCase("Teste")){ %>
	<TD class="boxTexto" align="center"><input type=submit name=btnPesqRC value="Cadastro RC"
			onclick="javascript: setActionPesquisa('menucadrc');"></TD>
<%} %>
	</TR>
<%if(action.equalsIgnoreCase("menupesq")||action.equalsIgnoreCase("Teste")){%>
	<TR>
		<TD class="boxTexto" align="center"><input type=submit name=btnLista value="Consulta"
			onclick="javascript: setActionPesquisa('menupesq');"></TD>
	</TR>
<%}%>

</table>
</form>

</BODY>

</HTML>
