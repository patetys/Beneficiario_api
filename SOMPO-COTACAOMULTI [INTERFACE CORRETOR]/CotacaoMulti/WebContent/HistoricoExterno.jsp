<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML xmlns:eba>
<HEAD>
<%@ page import="java.util.*" %>
<%@ page import="br.com.sompo.cotacaomulti.dto.HistoricoExternoDTO" %>
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
<FORM NAME=frm1 ACTION="DadosHistoricoExternoServlet" METHOD="POST">
<INPUT TYPE=HIDDEN NAME="action" VALUE="HistoricoExterno">
<INPUT TYPE=HIDDEN NAME="cotacao" VALUE="<%=request.getParameter("cotacao")%>">


	<h2>Histórico Externo</h2>

	<table>
	  <tr>
	    <th>Nome</th>
	    <th>E-mail</th>
	    <th>Corpo E-mail</th>
	    <th>Resposta E-mail</th>
	    <th>Data Resposta</th>
	    <th>Data Geração</th>
	  </tr>
	  <c:forEach var="registroHistoricoExterno" items="${listRetornoHistExterno}">
	  	<tr>
	  	  <td><c:out value="${registroHistoricoExterno['nom_membro']}" /></td>
	  	  <td><c:out value="${registroHistoricoExterno['end_email']}" /></td>
	  	  <td><c:out value="${registroHistoricoExterno['corpo_email']}" /></td>
	  	  <td><c:out value="${registroHistoricoExterno['resp_email']}" /></td>
	  	  <td><c:out value="${registroHistoricoExterno['dat_resp']}" /></td>
	  	  <td><c:out value="${registroHistoricoExterno['dat_geracao']}" /></td>
	  	</tr>
	  </c:forEach>
	</table>

</FORM>

<script type="text/javascript">

</script>

</BODY>
</HTML>