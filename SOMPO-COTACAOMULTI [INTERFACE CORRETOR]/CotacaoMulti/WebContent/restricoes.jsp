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
		$("#divCadastro").hide();
		$("#excecoes").show();
		$("#corretor").show();
		$("#frmExcecao").submit();
	}


	
	function iniciaCadastro() {
		$("#divCadastro").show();
		$("#corretor").show();
		$("#excecoes").hide();
		$("#excecoes_wrapper").hide();		
		$("#botoes").hide();

	}

	
	
	
	function cancelar() {
		$("#divCadastro").hide();
		$("#corretor").show();
		$("#excecoes").show();
		$("#botoes").show();
	}
	
	function corretores(v) {
		document.frmCorretores.v.value=v;
		document.frmCorretores.submit();
	}
	function corretoresGrid(v) {
		document.frmCorretores2.v.value=v;
		document.frmCorretores2.submit();
	}
	
	function realizarCadastro() {
		$.ajax({
			type : "POST",
			url : "ExcecaoController?action=restricaoCadastrar",
			data : {
				"codCorretor" : $("#codCorretor").val(),
				"codRamo" : $("#codRamo").val(),
				"idAcao" : $("#idAcao").val()
			},
			success : function(json) {
				console.log('sucesso')
				$("#frmExcecao").submit();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("erro: " + textStatus);
	        }
		});
	}


	function excluir(v) {
		if(window.confirm('Confirma exclusão do registro ' + v + ' ? Além da acao/ramo, todos os corretores com essa restrição também serão removidos')) {
			$.ajax({
				type : "POST",
				url : "ExcecaoController?action=restricaoExcluir",
				data : {
					"v" : v
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
	<FORM NAME="frmCorretores" id="frmCorretores" ACTION="ExcecaoController"
		METHOD="POST" target="corretores">
        <input type="hidden" name="action" value="restricaoCorretores"/>
        <input type="hidden" name="v" value=""/>
    </FORM>    
	<FORM NAME="frmCorretores2" id="frmCorretores2" ACTION="restricoescorr.jsp"
		METHOD="POST" target="corretores2">
        <input type="hidden" name="v" value=""/>
    </FORM>    

	<FORM NAME="frmExcecao" id="frmExcecao" ACTION="ExcecaoController"
		METHOD="POST">
        <input type="hidden" name="action" value="restricoes"/>
    </FORM>    
		<div id="corretor">
			<h2 align="center">Restri&ccedil;&otilde;es Ramo x Acao</h2>
			<!--  			<p>
				<label><b>C&oacute;digo(s) do Corretor: </b></label>
				<textarea id="codCorretor" name="codCorretor" rows="1" cols="100"
					placeholder="Poderá adicionar mais de um código separado por ';' . Ex: 32201;9854"></textarea>
			</p>
			-->
			
		</div>

		<br><br>
		<table id="excecoes">
		 	<thead>
				<tr>
					<th></th>
					<th>Acao</th>
					<th>Ramo</th>
					<th>Corretores</th>
				</tr>
			</thead>
			<tbody>
			<c:if test="${not empty requestScope['restricoes']}">
				<c:forEach items="${requestScope['restricoes']}" var="restricao">
					<tr>
						<td></td>
						<td><c:out value="${restricao.dscAcao}" /></td>
						<td><c:out value="${restricao.codigoRamo}" /></td>
						<td>  <input type="button" class="button" name="btnCorretores"
					value="Corretores TXT" onclick="corretores('<c:out value="${restricao.idAcao}${restricao.codigoRamo}" />');" /> &nbsp;&nbsp;
 <input type="button" class="button" name="btnCorretores2"
					value="Corretores GRID" onclick="corretoresGrid('<c:out value="${restricao.idAcao}${restricao.codigoRamo}" />');" /> &nbsp;&nbsp;					<input type="button" class="button" name="btnExcluir"
					value="Excluir" onclick="excluir('<c:out value="${restricao.idAcao}${restricao.codigoRamo}" />');" />
						</td>
					</tr>
				</c:forEach>
			</c:if>
			</tbody>
		</table>
		<div id="divCadastro" style="display: none">
			<tr>
				<td>
					<p>
						<label class="w3-text-black"><b>Acao: </b></label> 
						<!-- 
						<input type="text" id="idAcao">
						-->
<select class="boxInput" id="idAcao" name="idAcao" size="1"   style="width: 205px">    					    
    					    
 	
    					 	
    					      	 <option value="1"  >1-Seguro Novo</option>       	

				 	
    					 	
    					      	 <option value="2"  >2-Renov Congênere</option>    

   					 	
    					 	
    					      	 <option value="3"  >3-Renov Sompo</option>       	

				 	
    					 	
    					      	 <option value="4"  >4-Endosso</option>       		

			 	
    					 	
    						</select>
						
						</input>
					</p>
					<p>
						<label class="w3-text-black"><b>Ramo: </b></label> 
						<input type="text" id="codRamo">
						</input>
					</p>
					<p>
						<label><b>C&oacute;digo(s) do Corretor: </b></label>
						<textarea id="codCorretor" name="codCorretor" rows="10" cols="100"
							placeholder="Deverá adicionar mais de um código (um por linha)"></textarea>
					</p>
				</td>
				<td><input type="button" class="button" name="btnEnvioCadastro"
					value="Realizar Cadastro" onclick="realizarCadastro();" /></td>
				<td><input type="button" class="button" name="btnCancelar"
					value="Cancelar" onclick="cancelar();" /></td>

			</tr>
		</div>
		</br> </br>
		<div id="botoes" align="center">
			<tr>
				<td><input type="button" class="button" name="btnPesquisar"
					value="Pesquisar" onclick="pesquisar();" /></td>
				<td><input type="button" class="button" name="btnCadastrar"
					value="Cadastrar" onclick="iniciaCadastro();" /></td>

				<br><br><br>
			</tr>
		</div>
</BODY>
</HTML>

