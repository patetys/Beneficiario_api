<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="css/yasuda.css" type="text/css">
<script language="JavaScript" src="js/jquery-1.4.2.js"></script>
<script language="JavaScript">

function AjaxExec(action,arquivo,c){
	$('#status').text('');
	$.post("Controller",{nome: arquivo, action:action, classifica:c}, function(data){
		data = data.replace(/<\/?[^>]+(>|$)/g, "");
		if(data=="success"){
			location='UploadFile.jsp';		
		} else{
			alert(data);
		}
	}); 
	
}
var processando = false;
function downloadFile(file){
	if (processando){
		alert ("Aguarde, já está sendo processado!");
	}
	window.open('VisualizaDoc.jsp?file='+file,'Documento','fullscreen=no,toolbar=no,location=no,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes');
}

function upload(){
	if (processando){
		alert ("Aguarde, já está sendo processado!");
	}
	processando = true;
	document.form.submit();
}

</script>
<form name="form" method="post" action="Controller?action=ArquivoUpload"  enctype="multipart/form-data">
<table border="0" cellpadding="0" cellspacing="0" background="#fff" width="364">
	<TBODY>
		<tr>
			<td class="boxTexto" colspan="2" height="15">
			<input name="uploadfile" class="boxFileUpload" type="file" width="100" onchange="javascript: upload();">
			</td>
			<td class="boxTexto" width="12" height="15"></td>
		</tr>
		<tr>
			<td class="boxTexto" width="183" height="25">Arquivos:</td>
			<td class="boxTexto" width="111" height="25"></td>
			<td class="boxTexto" width="12" height="25"></td>
		</tr>
		<tr id="showStatus" style="display: none">
			<td class="boxTexto" colspan="3" height="26">
				<div id="status"></div>
			</td>
		</tr>
		<tr>
			<td class="boxTexto" colspan="3" height="26">
				<table border="0" cellpadding="0" cellspacing="0"  width="100%">
					<TBODY>
						<tr>
							<td class="boxTexto" height="15" width="223">Nome</td>
							<td class="boxTexto" height="15" width="60" align="center">Tamanho</td>
							<c:if test="${numSusep==999999 || num_susep_completo==999999}">
								<td class="boxTexto" height="15" width="46" align="center">Interno</td>
							</c:if>							
							<td class="boxTexto" height="15" width="46" align="center">&nbsp;</td>
						</tr>
						
						<c:forEach var="arquivo" items="${arquivos}" >
							<c:if test="${arquivo.nome!='' && arquivo.nome!=null}">
								<tr>
									<td class="boxTextoWrap" height="15" width="250">
									<a href="javascript: downloadFile('<c:out value='${arquivo.nomeTemp}'></c:out>')" >
									<c:out value="${arquivo.nome}" /></a>
									</td>
									<td class="boxTexto" height="15" width="46" align="center"><c:out value="${arquivo.size}"></c:out></td>
									<c:if test="${arquivo.status==0}">
										<c:if test="${numSusep==999999 || num_susep_completo==999999}">
											<td class="boxTexto" height="15" width="27" align="center">
												<input <c:if test="${arquivo.classificacao=='on'}">checked="checked" value="off"</c:if>
												type="checkbox" name="classificacao" onClick="AjaxExec('ArquivoClassifica','<c:out value="${arquivo.nomeTemp}" />',this.value)" />
											</td>
										</c:if>
										<td class="boxTexto" height="15" width="71" align="center">
											<a href="#" onClick="AjaxExec('ArquivoDeleta','<c:out value="${arquivo.nomeTemp}" />',this.value)" >Excluir</a>
										</td>									
									</c:if>
								</tr>
							</c:if>
						</c:forEach>
						
					</TBODY>
				</table>
				<br/><br/>
				<c:if test="${numSusep==999999 || num_susep_completo==999999}">
					*Caso deseje que este arquivo seja de uso exclusivo interno, basta apenas selecionar o arquivo correspondente.
				</c:if>
			</td>
		</tr>
	</TBODY>
</table>
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/UploadFileNew.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

