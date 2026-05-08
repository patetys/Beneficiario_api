<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="bean.*"%>


<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="css/w3.css">


<style>
label{
    display: block; /* add this */
    padding-top: 5px;
}

.button {
	display: inline-block;
	padding: 15px 25px;
	font-size: 24px;
	cursor: pointer;
	text-align: center;
	text-decoration: none;
	outline: none;
	color: #fff;
	background-color: #d61515;
	border: none;
	border-radius: 15px;
}

.button:active {
	background-color: #540808;
	box-shadow: 0 5px #666;
	transform: translateY(4px);
}

#excecoes {
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

#excecoes td, #excecoes th {
	border: 1px solid #ddd;
	padding: 8px;
}

#excecoes tr:nth-child(even) {
	background-color: #f2f2f2;
}

#excecoes tr:hover {
	background-color: #ddd;
}

#excecoes th {
	padding-top: 12px;
	padding-bottom: 12px;
	text-align: center;
	background-color: #d61515;
	color: white;
}
</style>


<TITLE></TITLE>
<script language="javascript" type="text/javascript"
	src="js/jquery-3.3.1.js"></script>
	
<script language="javascript" src="js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" href="css/jquery.dataTables.min.css">
<%
String regra =request.getParameter("v");
if (regra==null) regra="";
if (regra.length()==0) regra=(String)session.getAttribute("regra");
session.setAttribute("regra",regra);
%>
	

<script language="JavaScript">
$(document).ready(function() {
    $('#excecoes').DataTable( {
        "pagingType": "simple_numbers"
    } );

    validaLogin();
   
} );


	function validaLogin() {
		$.ajax({
			type : "POST",
			url : "ExcecaoController?action=validaLogin",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("erro: " + textStatus);				
				window.location.replace("/CotacaoMulti/login.jsp");
	        }
		});
	}

	function pesquisar() {
		$("#excecoes").show();
		$("#corretor").show();
		$("#frmExcecao").submit();
	}



	function excluir(v) {
		if(window.confirm('Confirma exclusão do registro ' + v + ' ? ')) {
			$.ajax({
				type : "POST",
				url : "ExcecaoController?action=restricaocorrExcluir",
				data : {
					"v" : v,
					"regra" : "<%=regra%>"
				},
				success : function(json) {
					$("#frmExcecao").submit();
				}
			});
		}
	}
	

</script>
</HEAD>

<BODY>

	<FORM NAME="frmExcecao" id="frmExcecao" ACTION="ExcecaoController"
		METHOD="POST">
        <input type="hidden" name="action" value="restricoescorr"/>
        <input type="hidden" name="regra" value="<%=regra%>"/>
    </FORM>    
		<div id="corretor">
			<h2 align="center">Corretores da Restri&ccedil;&otilde;es Ramo x Acao= <%= regra%></h2>
			
		</div>

		<br><br>
		<table id="excecoes">
		 	<thead>
				<tr>
					<th></th>
					<th>Corretores</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${not empty requestScope['corretores']}">
				<c:forEach items="${requestScope['corretores']}" var="corretor">
					<tr>
						<td></td>
						<td><c:out value="${corretor}" /></td>
						<td>  
					<input type="button" class="button" name="btnExcluir"
					value="Excluir" onclick="excluir('<c:out value="${corretor}" />');" />
						</td>
					</tr>
				</c:forEach>
			</c:if>
			</tbody>
		</table>
		</br> </br>
		<div id="botoes" align="center">
			<tr>
				<td><input type="button" class="button" name="btnPesquisar"
					value="Pesquisar" onclick="pesquisar();" /></td>
				<td></td>

				<br><br><br>
			</tr>
		</div>
</BODY>
</HTML>

