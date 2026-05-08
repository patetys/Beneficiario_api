<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<%@ page import="util.CotacaoMultiProperties"%>
<%
String email = (String) CotacaoMultiProperties.getInstance().getProperty("garantia.endereco");
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">

	<style type="text/css">
		p{
			color: #666;
			font-family:Arial;
			font-size:12px;
			margin: 0;
			padding: 0;
		}
	</style>
	
</head>
<body>
	<div class="msg">
		<center><p><b>Atenção:</b> Prezado corretor, para solicitar cotação do ramo <b>Garantia</b>, por favor, envie e-mail </p></center>
		<center><p>para: <a href="#"><%=email%></a></p></center>
	</div>
</body>
</html>