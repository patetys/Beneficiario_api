<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="beanDados" value="${requestScope['dadosCotacao']}" />
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

<TITLE>E-mail</TITLE>
</HEAD>

<BODY>
<c:set var="sompo" value="${sessionScope['sompo']}" />
<c:if test="${sompo == 'Sompo'}">
	<link rel="stylesheet" href="css/sompo_yasuda.css" type="text/css">
	<script language="javascript" type="text/javascript" src="js/sompo_datetimepicker.js" charset="UTF-8"></script>
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<link rel="stylesheet" href="css/yasuda.css" type="text/css">
	<script language="javascript" type="text/javascript" src="js/datetimepicker.js"></script>
</c:if>


<script language="JavaScript">

function calcHeight()
{
  //find the height of the internal page
  var the_height=
    document.getElementById('frameFile').contentWindow.
      document.body.scrollHeight;

  //change the height of the iframe
  document.getElementById('frameFile').height=
      the_height;
}


function enviarEmail(Form) {

		if (document.frm1.respEmail.value=="") {
			alert("A resposta deve ser preenchida.");
			return false;
		}

		document.frm1.action.value="enviaEmail";
		document.frm1.submit();
}

function confirmaCancelamento(ccotacao) {

		if(confirm("Confirma o cancelamento da cotação nº "+ccotacao+" ?")){
			document.frm1.action.value="cancelaCotacao";
			document.frm1.submit();
			return true;
		}else{
			return false;
		}
}

</script>

<FORM NAME=frm1 ACTION="Controller" METHOD="POST">
<INPUT TYPE=HIDDEN NAME="action" VALUE="">
<INPUT TYPE=HIDDEN NAME="sequencia" VALUE="<c:if test='${beanDados.sequencia!=null}'><c:out value='${beanDados.sequencia}'/></c:if>">
<P></P>
<TABLE border="0" cellpadding="5" cellspacing="0" class="tabelaAction" height="325">
	<TBODY>
		<TR>
			<TD class="boxTitulo" width="238" height="9">E-mail</TD>
		</TR>
		<TR valign="top">
			<TD width="238" height="324">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"
				width="306">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="15" width="183">Cotação Nº</TD>
						<TD class="boxTexto" height="15" width="111">Corretor :</TD>
						<TD class="boxTexto" height="15" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="15" width="183"><INPUT type="text"
							class="boxInput" name="cCotacao" size="14" readonly="readonly"
							value="<c:if test='${beanDados.cCotacao!=null}'><c:out value='${beanDados.cCotacao}'/></c:if>"></TD>
						<TD class="boxTexto" height="15" width="111"><INPUT type="text"
							class="boxInput" name="nomeCorretor" size="14"
							readonly="readonly"
							value="<c:if test='${beanDados.nomeCorretor!=null}'><c:out value='${beanDados.nomeCorretor}'/></c:if>"></TD>
						<TD class="boxTexto" height="15" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="20" width="183">Pergunta: </TD>
						<TD class="boxTexto" height="20" width="111"></TD>
						<TD class="boxTexto" height="20" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" colspan="2"><TEXTAREA class="boxTextoArea" readonly="readonly"
							name="corpoEmail" rows="4" cols="47"><c:if test="${beanDados.corpoEmail!=null && beanDados.corpoEmail!='null'}"><c:out value='${beanDados.corpoEmail}' /></c:if></TEXTAREA></TD>
						<TD class="boxTexto" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="20" width="183">Resposta :</TD>
						<TD class="boxTexto" height="20" width="111"></TD>
						<TD class="boxTexto" height="20" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" colspan="2"><TEXTAREA class="boxTextoArea"
							name="respEmail" rows="9" cols="47"><c:if test="${beanDados.respEmail!=null && beanDados.respEmail!='null'}"><c:out value='${beanDados.respEmail}' /></c:if></TEXTAREA></TD>
						<TD class="boxTexto" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="20" width="183"></TD>
						<TD class="boxTexto" height="20" width="111"></TD>
						<TD class="boxTexto" height="20" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="20" colspan="2" align="center">
							<a class="cbutton" name="btnEnviar"  onclick="javascript:return enviarEmail(this);">ENVIAR</a>
						</TD>
						<TD class="boxTexto" height="20" align="center" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="183" height="11"></TD>
						<TD class="boxTexto" width="111" height="11"></TD>
						<TD class="boxTexto" width="12" height="11"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="183" height="20"><A
							href="#"
							target="_self"
							onclick="javascript:return confirmaCancelamento(<c:if test='${beanDados.cCotacao!=null}'><c:out value='${beanDados.cCotacao}'/></c:if>);">Quero
						cancelar essa Cotação</A></TD>
						<TD class="boxTexto" width="111" height="20"></TD>
						<TD class="boxTexto" width="12" height="20"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="183" height="13"></TD>
						<TD class="boxTexto" width="111" height="13"></TD>
						<TD class="boxTexto" width="12" height="13"></TD>
					</TR>
					<TR>
						<TD class="boxTitulo tabelaAction" colspan="2"  height="21">Anexos</TD>
						<TD class="boxTitulo tabelaAction" bgcolor="#8FBCE4" width="12" height="21"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="183" height="15" colspan="3"><IFRAME id="frameFile"
							frameborder="0" name="uploadFile" src="UploadFile.jsp" width="416" onLoad="javascript:calcHeight();" 
							height="1" marginheight="10" marginwidth="0" scrolling="no">
						</IFRAME></TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>		
</form>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/DivRespEmail.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
