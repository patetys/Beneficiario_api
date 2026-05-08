<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<% String u=  request.getParameter("Usuario"); %>
</head>
		<frameset framespacing="0" border="0" frameborder="0" rows="10,*">
			<FRAME SRC="white.jsp" name="top" marginwidth="0" marginheight="0" scrolling="auto" noresize>
			<frameset framespacing="0" border="0" frameborder="0" cols="10,220,*"><frame src="white.jsp" name="lateral" marginwidth="0" marginheight="0" scrolling="auto" noresize>
				<frame src="mnuItens.jsp?Usuario=<%=u%>" name="menu" marginwidth="0" marginheight="0" scrolling="auto" noresize>
				<frame src="white.jsp" name="principal" marginwidth="0" marginheight="0"  noresize> 
			<noframes> 
			</noframes>
			</frameset>
		</frameset>
</html>