<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML xmlns:eba>
<HEAD>
<%@ page import="java.util.*" %>
<%@ page import="br.com.sompo.cotacaomulti.dto.HistoricoInternoDTO" %>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<link rel="stylesheet" href="css/yasuda.css" type="text/css">
<TITLE></TITLE>
<style>
	table {
	  font-family: arial, sans-serif;
	  border-collapse: collapse;
	  width: 100%;
	}
	
	td, th {
	  border: 1px solid;
	  text-align: left;
	  padding: 8px;
	}
	
	tr:nth-child(even) {
	  background-color: #dddddd;
	}
</style>
</head>
</HEAD>
<%
%>
<BODY>
<FORM NAME=frm1 ACTION="DadosHistoricoServlet" METHOD="POST">
<INPUT TYPE=HIDDEN NAME="action" VALUE="HistoricoInterno">
<INPUT TYPE=HIDDEN NAME="cotacao" VALUE="<%=request.getParameter("cotacao")%>">


	<h2>Histórico Interno</h2>

	<table>
	  <tr>
	    <th>Nome</th>
	    <th>Observação</th>
	    <th>Data</th>
	  </tr>
	  <c:forEach var="registroHistoricoInterno" items="${listRetornoHistorico}">
	  	<tr>
	  	  <td><c:out value="${registroHistoricoInterno['nom_membro']}" /></td>
	  	  <td><c:out value="${registroHistoricoInterno['observacao']}" /></td>
	  	  <td><c:out value="${registroHistoricoInterno['dat_geracao']}" /></td>
	  	</tr>
	  </c:forEach>
	</table>

</FORM>

<script type="text/javascript">

</script>

</BODY>
</HTML>