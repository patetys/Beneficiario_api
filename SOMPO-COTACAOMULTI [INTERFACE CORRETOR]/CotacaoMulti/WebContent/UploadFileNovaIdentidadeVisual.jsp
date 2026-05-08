<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="bean.CotacaoBean"%>
<%@ page import="bean.Project"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<meta content="IE=9" http-equiv="X-UA-Compatible" />
<%@ page import="java.util.*" %>
<%
Random random = new Random(); 
String valorRan = "" + random.nextInt();
valorRan = valorRan.trim();
%>

<!-- CSS -->

<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
<link rel="stylesheet" href="css/yasuda.css" type="text/css">
<link rel="stylesheet" type="text/css" href="css/jquery.selectbox.css" />
<link rel="stylesheet" type="text/css" href="css/tooltipster.css" />
<link rel="stylesheet" type="text/css" href="css/calendario.css" />
<link rel="stylesheet" type="text/css" href="css/chosen.css" />
<link rel="stylesheet" type="text/css" href="css/ymModal.css?<%=Project.version%>" />


<c:set var="sompo" value="${sessionScope['sompo']}" />
<c:if test="${sompo == 'Sompo'}">
	<link rel="stylesheet" type="text/css" href="css/sompo_menuCadRC.css?<%=Project.version%>" />
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<link rel="stylesheet" type="text/css" href="css/menuCadRC.css?<%=Project.version%>" />
</c:if>

<style>
.col1 {width: 420px;}
.col2 {width: 320px;}
.col3 {width: 600px;}
.margin-col1 {margin-left: 10px;}
.caixa_select_custom .txtComp {width: 1055px;}
.style-select select 
{
overflow: hidden;
	background: url(./images/botoes/seta-select.png) no-repeat right #fafafa;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
}

.style-select select::-ms-expand {
 display: none !important; 
}  
</style>
<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="js/jquery.maskedinput-1.2.2-co.min.js"></script>
<script type="text/javascript" src="js/jquery.selectbox-0.6.1.js"></script>
<script type="text/javascript" src="js/jquery.tooltipster.js"></script>
<script type="text/javascript" src="js/highlight.js"></script>
<script type="text/javascript" src="js/ymModal.js?param=<%=valorRan%>"></script>
<SCRIPT type="text/javascript" src="js/consistencias.js?param=<%=valorRan%>" charset="UTF-8"></SCRIPT>
<SCRIPT type="text/javascript" src="js/consistenciasMSCad.js?param=<%=valorRan%>" charset="UTF-8"></SCRIPT>
<SCRIPT type="text/javascript" src="js/chosen.jquery.js" charset="UTF-8"></SCRIPT>
<SCRIPT type="text/javascript" src="js/MenuCadRC.js" charset="UTF-8"></SCRIPT>
<!-- <script>
		setInterval(function(){ hideLoading(); }, 10000);
		$(function(){
    	      $("#includedContent").load("/YMPopup/popup.html");
	    });
</script> -->
<TITLE>UploadFile.jsp</TITLE>
</HEAD>
<BODY>

<FORM NAME=frm1  target="Documento" ACTION="Controller" METHOD="POST" >
<INPUT TYPE=HIDDEN NAME="action" VALUE="uploadEmailFile">
<INPUT TYPE=HIDDEN NAME="nameTempFile" VALUE="">
</FORM>

<FORM NAME=frmDel  ACTION="Controller"  METHOD="POST" >
<INPUT TYPE=HIDDEN NAME="action" VALUE="">
<INPUT TYPE=HIDDEN NAME="nameTempFile" VALUE="">
<INPUT TYPE="HIDDEN" NAME="outfile" id="outfile" value=""/>
</FORM>

<FORM NAME=frmClassifica  ACTION="Controller"  METHOD="POST" >
<INPUT TYPE=HIDDEN NAME="action" VALUE="">
<INPUT TYPE=HIDDEN NAME="classifica" VALUE="">
<INPUT TYPE=HIDDEN NAME="nome" VALUE="">
</FORM>

<FORM NAME=frm ACTION="Controller" METHOD="POST" enctype="multipart/form-data" >
<INPUT TYPE="HIDDEN" NAME="action" id="action" VALUE=""/>
<INPUT TYPE="HIDDEN" NAME="nameTempFile" id="nameTempFile" VALUE=""/>
<INPUT TYPE="HIDDEN" NAME="outfile" id="outfile" value=""/>
<%request.getSession().setAttribute("outfile", "1"); %>

	<div class="fLeft col1  margin-top9">

			<label class="w120">Anexar Arquivos:</label>
		  		<div class="caixa_select_custom" style="width: 255px">
		  			<INPUT name="arqAnexo" id="arqAnexo" class="removeComp"  type="file" width="100" onchange="javascript:uploadFile();"/> 
		  			<INPUT name="arqAnexoFake" id="arqAnexoFake" class="fLeft w368" type="text" />
				</div>
	
	</div>		
							
	<div class="fLeft margin-col1">

				<label>&nbsp;</label>
				<div class="caixa_select_custom" style="margin-top: 7px; width: 178px;">
					<abbr title="Procurar">
						<label class="click botao-azul fRight" for="arqAnexo" id="uploadFile">Procurar</label>
					</abbr>
				</div>
	</div>
			
<TABLE border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" width="364">
	<TBODY>
		<c:set var="erro" value="${requestScope['erro']}"/>
		<c:if test="${erro!=null}">
			<TR>
				<TD class="boxTexto" width="700" height="25"><FONT color="#FF0000">
				Erro: <c:out value='${erro}'/></FONT></TD>
			</TR>		
		</c:if>
		<TR>
			<TD class="w120" width="183" height="25">Arquivos:</TD>
			<TD class="w120" width="111" height="25"></TD>
			<TD class="w120" width="12" height="25"></TD>
		</TR>
		<TR>
			<TD class="boxTexto" colspan="3" height="26">
			<TABLE border="0" cellpadding="0" cellspacing="0">
				<TBODY>
					<TR>
						<TD class="w120" height="15" width="223">Nome</TD>
						<TD class="w120" height="15" width="46">Tamanho</TD>
						<c:if test="${numSusep==999999 || num_susep_completo=='999999' || sessionScope['numSusep1']=='999999'}">
							<td class="boxTexto" height="15" width="46" align="center">Interno</td>
						</c:if>							
					</TR>
					<c:forEach var="file" varStatus="num" items="${sessionScope['fileList']}">
						<c:if test="${file.value.fileName!='' && file.value.fileName!=null}">
							<c:if test="${file.value.classifica=='off' || numSusep==999999 ||  num_susep_completo=='999999' || sessionScope['numSusep1']=='999999'}">
								<TR>
									<TD class="boxTextoWrap" height="15" style="width: 360px;">
										<A href="javascript: downloadFile('<c:out value='${file.value.tempFile}'></c:out>')">
											<c:out value="${file.value.fileName}"/>
										</A>
									</TD>
									<TD class="w120" height="15" width="46">
										<label><c:out value="${file.value.fileSize}"/></label>
									</TD>
									
									   <c:if test="${numSusep==999999 || num_susep_completo=='999999' || sessionScope['numSusep1']=='999999'}">
										<td class="w120" height="15" width="27" align="center" >
									
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
									<TD class="w120" height="15" width="71"><A
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
<!-- <div id="includedContent"></div> -->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/UploadFileNovaIdentidadeVisual.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>
