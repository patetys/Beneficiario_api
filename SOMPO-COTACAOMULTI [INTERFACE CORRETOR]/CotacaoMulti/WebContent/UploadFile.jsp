<%@page import="bean.CotacaoBean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<c:set var="sompo" value="${sessionScope['sompo']}" />
<c:if test="${sompo == 'Sompo'}">
	<LINK rel="stylesheet" type="text/css" href="css/sompo_yasuda.css" id="cssDinamico" title="Style">
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<link rel="stylesheet" href="css/yasuda.css" type="text/css">
</c:if>
<TITLE>UploadFile.jsp</TITLE>



<script language="JavaScript">

var processando = false;
var teste = document.getElementsByName('classificacao');

function uploadFile() {
	if (processando) {
		alert("Aguarde, já está sendo processado!");
		return;
	}

	const fileInput = document.querySelector('input[name="arqAnexo"]');
	if (!fileInput.files.length) return;

	const formData = new FormData();
	formData.append('action', 'uploadEmailFile');
	formData.append('arqAnexo', fileInput.files[0]);

	processando = true;

	fetch('/CotacaoMulti/Controller', {
		method: 'POST',
		body: formData
	})
	.then(response => response.text())
	.then(result => {
		console.log('Arquivo enviado com sucesso');
		location.reload();
	})
	.catch(error => {
		console.error('Erro ao enviar:', error);
		alert('Erro ao enviar arquivo.');
	})
	.finally(() => {
		processando = false;
	});
}

function downloadFile(file){
	if (processando){
		alert ("Aguarde, já está sendo processado!");
	}		
	window.open('/CotacaoMulti/VisualizaDoc.jsp?file='+file,'Documento','fullscreen=no,border=yes,toolbar=no,location=no,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes');
	
}

function delFile(file){

	if (processando){
		alert ("Aguarde, já está sendo processado!");
	}

	document.frmDel.nameTempFile.value=file;		
	document.frmDel.action.value="delEmailFile";
	processando = true;
	document.frmDel.submit();
}

function classificaFile(arquivo,c){

	if (processando){
		alert ("Aguarde, já está sendo processado!");
	}

	document.frmClassifica.nome.value=arquivo;		
	document.frmClassifica.classifica.value=c;
	document.frmClassifica.action.value="ArquivoClassifica";
	processando = true;
	document.frmClassifica.submit();
}

function VerificaBPM(){
	if (parent.document.getElementById("BPM").value == "true"){
		document.getElementById('cssDinamico').setAttribute('href', 'css/sompo_yasuda_bpm.css');
		if (parent.document.getElementById("acesso").value != "interno"){
			for (i = 0; i < document.getElementsByName('classificacao').length; i++) {
				document.getElementsByName('classificacao')[i].style.display = "none";
				if (document.getElementsByName('classificacao')[i].value == "off"){
					document.getElementById('doc'+ (i-1)).style.display = "none";
				}
			}
		}
	}
}

</script>

</HEAD>
<BODY onLoad="VerificaBPM()">

<FORM NAME=frm1  target="Documento" ACTION="Controller" METHOD="POST" >
<INPUT TYPE=HIDDEN NAME="action" VALUE="uploadEmailFile">
<INPUT TYPE=HIDDEN NAME="nameTempFile" VALUE="">
</FORM>

<FORM NAME=frmDel  ACTION="Controller"  METHOD="POST" >
<INPUT TYPE=HIDDEN NAME="action" VALUE="">
<INPUT TYPE=HIDDEN NAME="nameTempFile" VALUE="">
</FORM>

<FORM NAME=frmClassifica  ACTION="Controller"  METHOD="POST" >
<INPUT TYPE=HIDDEN NAME="action" VALUE="">
<INPUT TYPE=HIDDEN NAME="classifica" VALUE="">
<INPUT TYPE=HIDDEN NAME="nome" VALUE="">
</FORM>

<FORM NAME=frm ACTION="Controller" METHOD="POST" enctype="multipart/form-data">
<INPUT TYPE=HIDDEN NAME="action" VALUE="">
<INPUT TYPE=HIDDEN NAME="nameTempFile" VALUE="">

<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" width="364">
	<TBODY>
		<TR>
			<TD class="boxTexto" colspan="2" height="15"><INPUT name="arqAnexo"
				class="boxFileUpload" type="file" width="100"
				onchange="javascript: uploadFile();"></TD>
			<TD class="boxTexto" width="12" height="15"></TD>
		</TR>
		<c:set var="erro" value="${requestScope['erro']}"/>
		<c:if test="${erro!=null}">
			<TR>
				<TD class="boxTexto" width="700" height="25"><FONT color="#FF0000">
				Erro: <c:out value='${erro}'/></FONT></TD>
			</TR>		
		</c:if>
		<TR>
			<TD class="boxTexto" width="183" height="25">Arquivos:</TD>
			<TD class="boxTexto" width="111" height="25"></TD>
			<TD class="boxTexto" width="12" height="25"></TD>
		</TR>
		<TR>
			<TD class="boxTexto" colspan="3" height="26">
			<TABLE border="0" cellpadding="0" cellspacing="0">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="15" width="223">Nome</TD>
						<TD class="boxTexto" height="15" width="46">Tamanho</TD>
						<c:if test="${numSusep==999999 || num_susep_completo==999999 || sessionScope['numSusep1']==999999}">
							<td class="boxTexto" height="15" name="classificacao" width="46" align="center">Interno</td>
						</c:if>							
					</TR>
					<c:forEach var="file" varStatus="num" items="${sessionScope['fileList']}">
						<c:if test="${file.value.fileName!='' && file.value.fileName!=null}">
							<c:if test="${file.value.classifica=='off' || numSusep==999999 || num_susep_completo==999999 || sessionScope['numSusep1']==999999}">
								<TR id="doc<c:out value='${num.index}'></c:out>">
									<TD class="boxTextoWrap" height="15" width="250"><A class="linkMenu" 
										href="javascript: downloadFile('<c:out value='${file.value.tempFile}'></c:out>')"><c:out
										value="${file.value.fileName}"></c:out></A></TD>
									<TD class="boxTexto" height="15" width="46"><c:out
										value="${file.value.fileSize}"></c:out></TD>
									
									   <c:if test="${numSusep==999999 || num_susep_completo==999999 || sessionScope['numSusep1']==999999}">
										<td class="boxTexto" height="15" width="27" align="center" >
									
									   <input
									   <c:if test="${ sessionScope['intranet']==1 && sessionScope['codUnidade']!=0 }">
									      checked disabled
									     </c:if>  
									      <c:if test="${file.value.classifica=='on'}">
									      checked value="off"  
									      </c:if>  
										type="checkbox" name="classificacao"  onClick="classificaFile('<c:out value="${file.value.fileName}" />',this.value)" />
										</td>
									</c:if>	
									<TD class="boxTexto" height="15" width="27"></TD>
									<TD class="boxTexto" height="15" width="71"><A class="linkMenu" 
										href="javascript: delFile('<c:out value='${file.value.tempFile}'></c:out>')">Excluir</A>
									</TD>
							
								
								</TR>
							</c:if>
						</c:if>
					</c:forEach>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/UploadFile.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
