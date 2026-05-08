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
<link rel="stylesheet" href="css/yasuda.css" type="text/css">
<TITLE>E-mail</TITLE>
</HEAD>

<BODY style="margin: 0px 0px 0px 0px">

<div id="includedContent"></div>
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


function enviarArquivo(Form) {

		document.frm1.action.value="enviaArquivo";
		return true;
}

</script>

<FORM NAME=frm1 ACTION="Controller" METHOD="POST">
<INPUT TYPE="HIDDEN" NAME="action" VALUE="">
<INPUT TYPE="HIDDEN" NAME="anexaSubscricao" value="<c:out value='${anexoSubscricao}'/>"/>
<INPUT TYPE="HIDDEN" NAME="BPM" id="BPM" value="<c:out value='${BPM}'/>"/>
<INPUT TYPE="HIDDEN" NAME="acesso" id="acesso" value="<c:out value="${param.acesso}"/>"/>
<INPUT TYPE=HIDDEN NAME="sequencia" VALUE="<c:if test='${beanDados.sequencia!=null}'><c:out value='${beanDados.sequencia}'/></c:if>">
<P></P>
<TABLE border="0" cellpadding="5" cellspacing="0" bgcolor="#A20A29"	height="145">
	<TBODY>
		<TR>
			<TD class="boxTitulo" style="color:white; font-weight: bold; font-size: 12" height="9" width="392">Cotação Nº</TD>
		</TR>
		<TR valign="top">
			<TD height="130" style="background-color:white;" width="392">
			<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"
				width="306">
				<TBODY>
					<TR>
						<TD class="boxTexto" height="15" width="183"></TD>
						<TD class="boxTexto" height="15" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" height="15" width="183"><INPUT type="text" style="border-style: none;color:  black; font-size: 12"
							class="boxInput" name="cCotacao" size="14" readonly="readonly"
							value="<c:if test='${beanDados.cCotacao!=null}'><c:out value='${beanDados.cCotacao}'/></c:if>"></TD>

						<TD class="boxTexto" height="15" width="111"><%--Ricardo
						<INPUT type="text"
							class="boxInput" name="nomeCorretor" size="14"
							readonly="readonly"
							value="<c:if test='${beanDados.nomeCorretor!=null}'><c:out value='${beanDados.nomeCorretor}'/></c:if>"></TD>
						--%>
						<TD class="boxTexto" height="15" width="12"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="183" height="13"></TD>
						<TD class="boxTexto" width="111" height="13"></TD>
						<TD class="boxTexto" width="12" height="13"></TD>
					</TR>
					<TR>
						<TD class="boxTitulo" colspan="2" bgcolor="#A20A29" style="color:white; font-weight: bold; font-size: 12" height="21">Anexos</TD>
						<TD class="boxTitulo" bgcolor="#A20A29" width="12" height="21"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" width="183" height="15" colspan="3"><IFRAME
							id="frameFile" frameborder="0" name="uploadFile"
							src="UploadFile.jsp" onLoad="javascript:calcHeight();"
							width="416" height="1" marginheight="10" marginwidth="0"
							scrolling="no"> </IFRAME></TD>
					</TR>
					<TR>
						<TD class="boxTitulo" colspan="2" height="6"></TD>
						<TD class="boxTitulo" width="12" height="6"></TD>
					</TR>
					<TR>
						<TD class="boxTexto" colspan="3" align="center" height="35"><INPUT
							type="image" name="btnEnviar" src="images/botaoEnviar.png"
							onclick="javascript:return enviarArquivo(this);"></TD>
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
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/ArquivoAnexoBpm.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
