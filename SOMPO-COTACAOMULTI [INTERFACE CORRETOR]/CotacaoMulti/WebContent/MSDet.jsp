<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<%@ page import="bean.*" %>


<HTML>
<HEAD>
<%@ page  
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>

<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<TITLE></TITLE>

<c:set var="sompo" value="${sessionScope['sompo']}" />

<c:if test="${sompo == 'Sompo'}">
	<LINK href="theme/sompo_Master.css" rel="stylesheet" type="text/css">
	<LINK rel="stylesheet" type="text/css" href="css/sompo_yasuda.css" id="cssDinamico" title="Style">	
	
</c:if>

<c:if test="${sompo != 'Sompo'}">
	<LINK href="theme/Master.css" rel="stylesheet" type="text/css">
	<LINK rel="stylesheet" type="text/css" href="css/yasuda.css" title="Style">
</c:if>

<script language="JavaScript">

function ValidaCSS(){
	var FILE = "css/sompo_yasuda.css";
	if (document.getElementById("canal").value == "BPM"){
		FILE = "css/sompo_yasuda_bpm.css"
		//document.getElementById('tabelaPrincipal').style.width = '420px';
		document.getElementById('tabelaPrincipal').style.width = '100%';
		document.getElementById('bodyPrincipal').style.margin = "0px 0px 0px 0px";
		document.getElementById('observacaoInterna').style.display = "none";
		document.getElementById('observacaoCorretor').style.display = "none";
	}
	document.getElementById('cssDinamico').setAttribute('href', FILE);
}
</script>
</HEAD>

<BODY onLoad="ValidaCSS()" id="bodyPrincipal">
<script language="JavaScript" type="text/javascript" src="js/consistencias.js" charset="UTF-8"></script>
<script language="JavaScript">
 
 var processando = false;
 
function ValidarCamposEnviar(Form) {
	
	if (processando){
		alert ("Aguarde, sua consulta já está sendo processada!");
		return false;
	}
    
    processando = true;
    document.frm1.submit();
	return(true);
}

function pesquisaCotacaoPeriodo(periodo){
	
 	document.frmConsulta.dataInicio.value=regredirPeriodo(periodo);
	document.frmConsulta.dataFim.value=dataAtualDDMMAAAA();
	document.frmConsulta.sitCotacao.value="";
	document.frmConsulta.submit(); 
}

function regredirPeriodo(dias){
	var today = new Date();
	var diasRegredirMilli = (24*dias)*60*60*1000;
	var diferenca = today.getTime() - diasRegredirMilli;
	today.setTime(diferenca);
	
    var dd = today.getDate();
    var mm = today.getMonth()+1; //Janeiro é 0!
    var yyyy = today.getFullYear();
    
    if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 
    var retorno = dd+'/'+mm+'/'+yyyy;
    return retorno;
}

function dataAtualDDMMAAAA(){
	var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //Janeiro é 0!

    var yyyy = today.getFullYear();
    if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 
    today = dd+'/'+mm+'/'+yyyy;
    return today;
}

function downloadFile(file,fileorig){
	window.open('/CotacaoMulti/VisualizaDoc.jsp?file='+file+'&fileorig='+fileorig,'Documento','fullscreen=no,border=yes,toolbar=no,location=no,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes');
}

function exibe(cotacao)
{	
	//popup = window.open ('http://wpd10600fslu397/webworkflow/content/custom_siscota_multi/form_historico.asp?cotacao=' + cotacao,"","width=850,location=0,height=350,top=100,left=0,resizable=1,maximinize=1,directories=0,menubar=0,scrollbars=1",true)	
	//popup = window.open ('http://sp310114psluged/webworkflow/content/custom_siscota_multi/form_historico.asp?cotacao=' + cotacao,"","width=850,location=0,height=350,top=100,left=0,resizable=1,maximinize=1,directories=0,menubar=0,scrollbars=1",true) 
	popup = window.open ('/CotacaoMulti/DadosHistoricoExternoServlet?cotacao=' + cotacao,"","width=1000,location=0,height=600,top=100,left=100,resizable=1,maximinize=0,directories=0,menubar=0,scrollbars=1",true)
	//?resposta=' + document.formulario.respostaok.value + '&cotacao=' + document.formulario.cotacao.value + '&item=' + document.formulario.item.value + '&folder=' + document.formulario.folder.value,"","width=850,location=0,height=350,top=100,left=0,resizable=1,maximinize=1,directories=0,menubar=0,scrollbars=1",true)
}

function exibeHistInterno(cotacao)
{	
	//popup = window.open ('http://wpd10600fslu397/webworkflow/content/custom_siscota_multi/form_historico_interno.asp?cotacao=' + cotacao,"","width=850,location=0,height=350,top=100,left=0,resizable=1,maximinize=1,directories=0,menubar=0,scrollbars=1",true)	
	//popup = window.open ('http://sp310114psluged/webworkflow/content/custom_siscota_multi/form_historico_interno.asp?cotacao=' + cotacao,"","width=850,location=0,height=350,top=100,left=0,resizable=1,maximinize=1,directories=0,menubar=0,scrollbars=1",true)
	popup = window.open ('/CotacaoMulti/DadosHistoricoServlet?cotacao=' + cotacao,"","width=1000,location=0,height=600,top=100,left=100,resizable=1,maximinize=0,directories=0,menubar=0,scrollbars=1",true)

}

function exibeinspecao(cotacao)
{	

//	popup = window.open ('http://spx10422psluynl:9080/SolicitaInspecao/Controller?action=Cotacao&cotacao=' + cotacao,"","width=600,location=0,height=600,top=100,left=100,resizable=1,maximinize=0,directories=0,menubar=0,scrollbars=1",true)
	popup = window.open ('http://www.yasuda.com.br/SolicitaInspecao/Controller?action=Cotacao&cotacao=' + cotacao,"","width=600,location=0,height=600,top=100,left=100,resizable=1,maximinize=0,directories=0,menubar=0,scrollbars=1",true)
	

}

function alteracomissao()
{	
	document.frm1.action.value="alteracomissao";
	document.frm1.submit();
	return true;			
}
</script>

<c:set var="bean" value="${sessionScope['CotacaoBean']}" />
<c:set var="datePattern" value="dd/MM/yyyy" />
<fmt:setLocale value="pt-BR"/> 

<FORM NAME="frm1" ACTION="Controller" METHOD="POST" >

	<INPUT TYPE="HIDDEN" NAME="action" VALUE="enviacotacao">
	<INPUT TYPE="HIDDEN" NAME="canal" id="canal" value="<%=request.getAttribute("canal")%>"/>
		
<TABLE border="0" width="500" id="tabelaPrincipal" cellpadding="0" cellspacing="0" >
	<TBODY>				
		<c:if test="${consulta=='S'}">
			<TR>
				<TD class="tabelaTitulo" colspan="2" height="24">Cotação</td>
			</TR>
			<tr>
				<td class="tabelaColunaEsquerda" width="180">Nº da Cotação</td>
				<td class="tabelaColunaDireita" width="320"><c:out
					value="${bean.numProtocolo}" /></td>
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
				<td class="tabelaColunaEsquerda" width="180">Situação</td>
				<td class="tabelaColunaDireita" width="320" style="color: red">				
				<c:out value="${bean.codSituacao}" />
					<c:out value="${bean.cotFinalData}" />
					</td>
			</tr>
									
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
				<td class="tabelaColunaEsquerda" width="180">Observação ao Corretor</td>
				<td class="tabelaColunaDireita" width="320" style="color: red"><c:out
					value="${bean.obs}" /></td>
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
		</c:if>


		<TR>
			<TD class="tabelaTitulo" colspan="2" height="24">Dados Gerais</td>
		</TR>


		<tr>
			<td class="tabelaColunaEsquerda" width="180">Ramo</td>
			<td class="tabelaColunaDireita" width="320"><c:out
				value="${bean.codRamo}" /> - <c:out value="${bean.nomRamo}" /></td>
		</tr>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>

		<c:if test="${bean.codModalidade>0}">
			<tr>
				<td class="tabelaColunaEsquerda" width="180">Modalidade</td>
				<td class="tabelaColunaDireita" width="320"><c:out
					value="${bean.nomModalidade}" /></td>
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
		</c:if>


		<tr>
			<td class="tabelaColunaEsquerda" width="180">Corretor</td>
			<td class="tabelaColunaDireita" width="320"><c:out
				value="${bean.codCorr}" /> - <c:out value="${CotacaoBean.nomCorr}" /></td>
		</tr>		
		
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>

			<c:if test="${intranet=='1'}">
					<tr>
						<td class="tabelaColunaEsquerda" width="180">Sucursal</td>
						<td class="tabelaColunaDireita" width="320">
							<c:out value="${bean.sucursal}" /></td>
					</tr>

					<tr>
						<td colspan="2" class="tabelaDivisoria"></td>
					</tr>
					
					<tr>
						<td class="tabelaColunaEsquerda" width="180">Código Corretor Marítima</td>
						<td class="tabelaColunaDireita" width="320">
						<c:out value="${bean.corrMarit}" /></td>
					</tr>

					<tr>
						<td colspan="2" class="tabelaDivisoria"></td>
					</tr>
			</c:if>
		<tr>
				<td class="tabelaColunaEsquerda" width="180">LMI único:</td>
				<td class="tabelaColunaDireita" width="320">				
				<c:out value="${bean.lmiUnicoTela}" />
				</td>
			</tr>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">Unidade Produtora </td>
			<td class="tabelaColunaDireita" width="320"><c:out value="${CotacaoBean.nomDepto}" /></td>
		</tr>
		<c:if test="${CotacaoBean.nomProdutor != '' && CotacaoBean.nomProdutor != null}">
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">Produtor </td>
			<td class="tabelaColunaDireita" width="320"><c:out value="${CotacaoBean.nomProdutor}" /></td>
		</tr>
		</c:if>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
		  <td class="tabelaColunaEsquerda">Usu&aacute;rio</td>
		  <td class="tabelaColunaDireita"><c:out value="${CotacaoBean.codUser}" /></td>
	  </tr>
	  <tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">Data da Cotação</td>
			<td class="tabelaColunaDireita" width="320"><fmt:formatDate value="${bean.data_Cotacao}" pattern="dd/MM/yyyy" /></td>
		</tr>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<TR>
			<TD class="tabelaColunaEsquerda" width="180">Tipo de Cotação</td>
			<TD class="tabelaColunaDireita" width="320"><c:if
				test="${CotacaoBean.tpCotacao=='1'}"> COTAÇÃO  </c:if> <c:if
				test="${CotacaoBean.tpCotacao=='2'}"> RECOTAÇÃO - <c:out
				value="${bean.numCotacaoAnt}" /> 
				
				</c:if></td>
		</TR>


		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<TR>
			<TD class="tabelaColunaEsquerda" width="180">Tipo de Emissão</td>
			<TD class="tabelaColunaDireita" width="320"><c:out
				value="${bean.tipEmissao}" /> - <c:out
				value="${CotacaoBean.nomEmissao}" /></td>
		</TR>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<TR>
			<TD class="tabelaColunaEsquerda" width="180">Apólice</td>
			<TD class="tabelaColunaDireita" width="320"><c:out
				value="${CotacaoBean.numApolFormatted}" /></td>
		</TR>
		<c:if test="${CotacaoBean.codigoHierarquico != null && CotacaoBean.codigoHierarquico != '' && CotacaoBean.codigoHierarquico != 'null'}">
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<TR>
				<TD class="tabelaColunaEsquerda" width="180">Código Hierárquico</td>
				<TD class="tabelaColunaDireita" width="320"><c:out
					value="${CotacaoBean.codigoHierarquico}" /></td>
			</TR>
		</c:if>
		<c:if test="${CotacaoBean.codigoFilial != null && CotacaoBean.codigoFilial != '' && CotacaoBean.codigoFilial != 'null'}">
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<TR>
				<TD class="tabelaColunaEsquerda" width="180">Código Filial</td>
				<TD class="tabelaColunaDireita" width="320"><c:out
					value="${CotacaoBean.codigoFilial}" /></td>
			</TR>
		</c:if>
		<c:if test="${CotacaoBean.classeAceitacao!='' && intranet=='1'}">
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<TR>
			<TD class="tabelaColunaEsquerda" width="180">Classe de Aceitação</td>
			<TD class="tabelaColunaDireita" width="320"><c:out
				value="${CotacaoBean.classeAceitacao}" /></td>
		</TR>
		</c:if>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<c:if test="${requestScope['intranet']=='1'}">
			<tr>
			<TD class="tabelaColunaEsquerda" width="180">Motivo</td>
			<TD class="tabelaColunaDireita" width="320">
				<c:choose>
					<c:when test="${CotacaoBean.codMotivo==5}">Outros - <c:out value="${CotacaoBean.nomMotivo}" /></c:when>
					<c:otherwise><c:out value="${CotacaoBean.nomMotivo}" /></c:otherwise>
				</c:choose>
			</td>			
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>		
			<TD class="tabelaColunaEsquerda" width="180">Técnico</td>
			<TD class="tabelaColunaDireita" width="320"><c:out
				value="${CotacaoBean.tecnico}" /></td>		
			</tr>		
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>		
			<TD class="tabelaColunaEsquerda" width="180">Anuência</td>
			<TD class="tabelaColunaDireita" width="320"><c:out
				value="${CotacaoBean.userAnuencia}" /></td>		
			</tr>
			<c:if test="${CotacaoBean.regraAlcada != ''}">			
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				<tr>		
				<TD class="tabelaColunaEsquerda" width="180">Critério</td>
				<TD class="tabelaColunaDireita" width="320"><c:out
					value="${CotacaoBean.regraAlcada}" /></td>		
				</tr>	
			</c:if>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>		
			<TD class="tabelaColunaEsquerda" width="180">Ref Syas</td>
			<TD class="tabelaColunaDireita" width="320"><c:out value="${CotacaoBean.refSyas}" /></td>		
			</tr>			
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>		
			<TD class="tabelaColunaEsquerda" width="180">Desconto máx.</td>
			<!--Hebertt-->
			<c:choose>
				<c:when test="${intranet=='1' &&  (codUnidadeReal=='935' || codUnidadeReal=='939')  && consulta=='S'}">
				<TD class="tabelaColunaDireita" width="320">
				<INPUT style="height: 16px; font-size: 9px;" type="text" name="desconto" size="14" value="<fmt:formatNumber value="${CotacaoBean.desconto}" minFractionDigits="2" maxFractionDigits="2"/>" maxlength="8">
				</td>
				</c:when>
				<c:otherwise><TD class="tabelaColunaDireita" width="320"><fmt:formatNumber value="${CotacaoBean.desconto}" minFractionDigits="2" maxFractionDigits="2" /></td></c:otherwise>
			</c:choose>
			</tr>	
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>		

			<c:if test="${consulta=='S'}">
			<tr>
			  <td class="tabelaColunaEsquerda">Marg. Comercial</td>
			  <td class="tabelaColunaDireita">
			  	<fmt:formatNumber value="${CotacaoBean.pmargem}" minFractionDigits="2" maxFractionDigits="2" />
			  </td>
		  	</tr>
		  	<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
			  <td class="tabelaColunaEsquerda">Comiss&atilde;o Corretagem</td>
			  <!--Hebertt-->
			  <c:choose>
				<c:when test="${intranet=='1' &&  (codUnidadeReal=='935' || codUnidadeReal=='939')  && consulta=='S'}">
				<TD class="tabelaColunaDireita" width="320">
				<INPUT style="height: 16px; font-size: 9px;" type="text" name="comissao" size="14" value="<fmt:formatNumber value="${CotacaoBean.pcomissao}" minFractionDigits="2" maxFractionDigits="2"/>" maxlength="8">
				</td>
				</c:when>
				<c:otherwise><td class="tabelaColunaDireita"><fmt:formatNumber value="${CotacaoBean.pcomissao}" minFractionDigits="2" maxFractionDigits="2" /></td></c:otherwise>
			  </c:choose>			  
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
			  <td class="tabelaColunaEsquerda">Taxa</td>
			  <td class="tabelaColunaDireita"><fmt:formatNumber value="${CotacaoBean.ptaxa}" minFractionDigits="2" maxFractionDigits="2" /></td>
		  	</tr>
		  	<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
			  <td class="tabelaColunaEsquerda">Pr&ecirc;mio</td>
			  <td class="tabelaColunaDireita"><fmt:formatNumber value="${CotacaoBean.vpremiototal}" minFractionDigits="2" maxFractionDigits="2" /></td>
		  	</tr>
		  	<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>			
			</c:if>
			
			<c:if test="${intranet==1}">			
			<tr id="observacaoInterna">
				<td class="tabelaColunaEsquerda" width="180">Observação interna</td>
				<!--<td class="tabelaColunaDireita" width="320" style="color: red;"><c:out value="${bean.infoTecnica}" /></TD>-->
				<td class="tabelaColunaDireita" width="320"><a href="javascript:exibeHistInterno('<c:out	value="${bean.numProtocolo}" />')"> Histórico interno</a></TD>
			</tr>
			
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>	
			</c:if>
			
			<tr id="observacaoCorretor">
				<td class="tabelaColunaEsquerda" width="180">Observação Corretor</td>
				<td class="tabelaColunaDireita" width="320"><a href="javascript:exibe('<c:out	value="${bean.numProtocolo}" />')"> Histórico do fluxo</a></TD>
			</tr>	
			
			<c:if test="${consulta=='S'}"> 
			
				<TR>
					<TD class="tabelaTitulo" colspan="2" height="24">Informações Adicionais</td>
				</TR>
		
		
				<TR>
					<TD class="tabelaColunaEsquerda" width="180">Alto Potencial de Risco</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.altopotrisco==1}">Sim</c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</TR>
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				<TR>
					<TD class="tabelaColunaEsquerda" width="180">Declinado na Inspeção</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.declinado==1}">Sim</c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</TR>
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				<TR>
					<TD class="tabelaColunaEsquerda" width="180">Recusado pelo IRB</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.recusadoirb==1}">Sim</c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</TR>
			</c:if>
				<TR>
					<TD class="tabelaTitulo" colspan="2" height="24">Condições de Inspeção</td>
				</TR>
				<TR>
					<TD class="tabelaColunaEsquerda" colspan="2">
					<c:if test="${bean.condinspec==0}">Não informado</c:if>
					<c:if test="${bean.condinspec==1}">Obrigatória</c:if>
					<c:if test="${bean.condinspec==2}">Dispensada</c:if>
					<c:if test="${bean.condinspec==3}">Conforme norma de aceitação</c:if>
					</td>
				</TR>
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				<%--< aqui c:if test="${requestScope['intranet']=='1'}"> --%>
				<c:if test="${intranet=='1' && (codUnidadeReal=='939' || codUnidadeReal=='935' || codUnidadeReal=='943' || codUnidadeReal=='942')}">
					<tr>
						<TD class="tabelaColunaEsquerda" width="180">Cadastro de inspeção:</td>
						<TD class="tabelaColunaDireita" width="320"><a href="javascript:exibeinspecao(<c:out value="${bean.numProtocolo}" />)"> clique aqui</a></td>
					</tr>
				</c:if>
			<c:if test="${consulta=='S'}">
				<TR>
					<TD class="tabelaTitulo" colspan="2" height="24">Condições especiais</td>
				</TR>
				<TR>
					<TD class="tabelaColunaEsquerda" width="180">LMI</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.lmi==1}">Sim</c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</TR>
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				<TR>
					<TD class="tabelaColunaEsquerda" width="180">Facultativo</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.facultativo==1}">Sim 
							<c:if test="${codUnidadeReal == 935 || codUnidadeReal == 938  || codUnidadeReal == 939 || codUnidadeReal == 922 }">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="\\SP320116PSLUNAS\soft2\Resseguro\anexos\Siscota\<c:out value="${bean.numProtocolo}" />" target="_blank">Negociação da Cotação</a>	
									<a href="\\SP320116PSLUNAS\soft2\Resseguro\anexos\OrdemFirme\<c:out value="${bean.numProtocolo}" />" target="_blank"> Ordem Firme </a>
							</c:if>
						</c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</TR>
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				<TR>
					<TD class="tabelaColunaEsquerda" width="180">Aceitação Especial</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.especiais==1}">Sim                     
							<c:if test="${bean.facultativo!=1 && (codUnidadeReal == 935 || codUnidadeReal == 938 || codUnidadeReal == 939 || codUnidadeReal == 922)}">	
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="\\SP320116PSLUNAS\soft2\Resseguro\anexos\Siscota\<c:out value="${bean.numProtocolo}" />" target="_blank">Negociação da Cotação</a>								
								<a href="\\SP320116PSLUNAS\soft2\Resseguro\anexos\OrdemFirme\<c:out value="${bean.numProtocolo}" />" target="_blank"> Ordem Firme </a>            
							</c:if>	
						</c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</TR>
				
				<TR>
					<TD class="tabelaTitulo" colspan="2" height="24">Pendências</td>
				</TR>
				<TR>
					<TD class="tabelaColunaEsquerda" width="180">Aprovação IRB </td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.aprovairb==1}">Sim</c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</TR>
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				<TR>
					<TD class="tabelaColunaEsquerda" width="180">Inspeção</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.inspecao==1}">Sim</c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</TR>
				
				<TR>
					<TD class="tabelaTitulo" colspan="2" height="24">Cobertura</td>
				</TR>
				<TR>
					<TD class="tabelaColunaEsquerda" colspan="2">
					<c:if test="${bean.cobertura==0}">Não informado</c:if>
					<c:if test="${bean.cobertura==1}">Exclusiva para Prédio </c:if>
					<c:if test="${bean.cobertura==2}">Exclusiva para Conteúdo</c:if>
					<c:if test="${bean.cobertura==3}">Para Prédio e Conteúdo</c:if>
					</td>
				</TR>
				
				<TR>
					<TD class="tabelaTitulo" colspan="2" height="24">Observações Gerais</td>
				</TR>
				
				
				<tr>
					<TD class="tabelaColunaEsquerda" width="180">Beneficiário</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.clausulabenef ==1}"><c:out value="${bean.beneficiario}"/></c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				
				<tr>
					<TD class="tabelaColunaEsquerda" width="180">Período Indenitário</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.periodo==1}"><c:out value="${bean.meses}"/></c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				<tr>
					<TD class="tabelaColunaEsquerda" width="180">Especificação</td>
					<TD class="tabelaColunaDireita" width="320">
					<c:choose>
						<c:when test="${bean.especificacao ==1}">Sim</c:when>
						<c:otherwise>Não</c:otherwise>
					</c:choose>
					</td>
				</tr>				
			</c:if>
		</c:if>
		<TR>
			<TD class="tabelaTitulo" colspan="2" height="24">Dados do Proponente</td>
		</TR>

		<TR>
			<TD class="tabelaColunaEsquerda" width="180">Nome</td>
			<TD class="tabelaColunaDireita" width="320"><c:out value="${bean.nomeProp}" /></td>
		</TR>



		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">Tipo Pessoa:</td>
			<td class="tabelaColunaDireita" width="320"><c:if
				test="${bean.tipPessoa=='0'}">FÍSICA</c:if><c:if
				test="${bean.tipPessoa=='1'}">JURÍDICA</c:if></td>
		</tr>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">CNPJ/CPF:</td>
			<td class="tabelaColunaDireita" width="320"><c:out
				value="${bean.formattedCnpjCpf}" /></td>
		</tr>
		<c:if test="${bean.codRamo == 510 || bean.codRamo == 670 || bean.codRamo == 750 || bean.codRamo == 760}">
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">
				<c:if test="${bean.tipPessoa == '1'}">Atividade Principal:</c:if>
				<c:if test="${bean.tipPessoa == '0'}">Profissão:</c:if>
			</td>
			<td class="tabelaColunaDireita" width="320">
				<c:forEach var="profRamAtividade" items="${profissoesRamosAtividade}">
					<c:if test="${bean.tipPessoa == '0'}"><c:set var="idSubIdConcat" value="${profRamAtividade.idSubId}"/></c:if>
					<c:if test="${bean.tipPessoa == '1'}"><c:set var="idSubIdConcat" value="${profRamAtividade.id}"/></c:if>
					<c:if test="${idSubIdConcat == bean.profissaoRamoAtividade}">
						<c:out value="${profRamAtividade.nome}"/>
					</c:if>
				</c:forEach>
			</td>
		</tr>
		</c:if>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">Vigência:</td>
			<td class="tabelaColunaDireita" width="320">
			<c:if test="${bean.dataVigenciaInicio != '0'}">
				<c:out value="${bean.dataVigenciaInicioFormatada}" />
				até 
				<c:out value="${bean.dataVigenciaFimFormatada}" />
			</c:if>	
			</td>
		</tr>
		<c:if test="${intranet=='1' && bean.numProtocolo != null && bean.numProtocolo != ''}">
				<tr>
					<td colspan="2" class="tabelaDivisoria"></td>
				</tr>
				<tr>
					<td class="tabelaColunaEsquerda" width="180">Histórico de Cotações</td>
					<td class="tabelaColunaDireita" width="320" >Últimos <a href="javascript:pesquisaCotacaoPeriodo(30);">30</a>, 
			<a href="javascript:pesquisaCotacaoPeriodo(60);">60</a> ou <a href="javascript:pesquisaCotacaoPeriodo(90);">90</a> dias</td>
				</tr>
		</c:if>
		<TR>
			<TD class="tabelaTitulo" colspan="2" height="24">Dados do Risco</td>
		</TR>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>

		<tr>
			<td class="tabelaColunaEsquerda" width="180">Locais de Risco</td>
		<td class="tabelaColunaDireita" width="320"><c:if
				test="${bean.qtdLocRisc=='0'}">1</c:if> <c:if
				test="${bean.qtdLocRisc=='1'}">Varios</c:if>		</tr>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>

		<c:if test="${bean.qtdLocRisc=='0'}">

			<tr>
				<td class="tabelaColunaEsquerda" width="180">Endereço :</td>
				<td class="tabelaColunaDireita" width="320"><c:out
					value="${bean.end}" /></td>
			</tr>

			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
				<td class="tabelaColunaEsquerda" width="180">Numero:</td>
				<td class="tabelaColunaDireita" width="320"><c:out
					value="${bean.numero}" /></td>
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>

			<tr>
				<td class="tabelaColunaEsquerda" width="180">Complemento:</td>
				<td class="tabelaColunaDireita" width="320"><c:out
					value="${bean.complemento}" /></td>
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
				<td class="tabelaColunaEsquerda" width="180">Bairro:</td>
				<td class="tabelaColunaDireita" width="320"><c:out
					value="${bean.bairro}" /></td>
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
				<td class="tabelaColunaEsquerda" width="180">CEP:</td>
				<td class="tabelaColunaDireita" width="320"><c:out
					value="${bean.cep}" /></td>
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
				<td class="tabelaColunaEsquerda" width="180">Cidade:</td>
				<td class="tabelaColunaDireita" width="320"><c:out
					value="${bean.cid}" /></td>
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
			<tr>
				<td class="tabelaColunaEsquerda" width="180">Estado:</td>
				<td class="tabelaColunaDireita" width="320"><c:out
					value="${bean.uf}" /></td>
			</tr>
			<tr>
				<td colspan="2" class="tabelaDivisoria"></td>
			</tr>
		</c:if>

		<c:if test="${CotacaoBean.vlRisco!=0}">
			<TR>
				<c:choose>
					<c:when test="${bean.qtdLocRisc=='1'}">
						<TD class="tabelaColunaEsquerda" width="180">Maior LMG
						R$:</td>
					</c:when>
					<c:otherwise>
						<TD class="tabelaColunaEsquerda" width="180">LMG R$:</td>
					</c:otherwise>
				</c:choose>
				<TD class="tabelaColunaDireita" width="320"><fmt:formatNumber value="${CotacaoBean.vlRisco}" groupingUsed="true" minFractionDigits="2" /></td>
			</TR>
			<tr>
				<td colspan="2" class="tabelaDivisoria" height="0"></td>
			</tr>
		</c:if>

		<TR>
			<TD class="tabelaTitulo" colspan="2" height="25">Dados do Solicitante</td>
		</TR>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">Nome</td>
			<td class="tabelaColunaDireita" width="320"><c:out
				value="${bean.nomeContato}" /></td>
		</tr>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">Email</td>
			<td class="tabelaColunaDireita" width="320"><c:out
				value="${bean.emailContato}" /></td>
		</tr>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">Telefone</td>
			<td class="tabelaColunaDireita" width="320"><c:out
				value="${bean.telefContato}" /></td>
		</tr>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>


		<TR>
			<TD class="tabelaTitulo" colspan="2" height="25">Observações</td>
		</TR>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<tr>
			<td class="tabelaColunaEsquerda" width="180">Demais dados necessários
			para a cotação (cobertura, LMG, bens a segurar, etc.):</td>
			<td class="tabelaColunaDireita" width="320"><c:out
				value="${bean.comentario}" /></td>
		</tr>



		<TR>
			<%CotacaoBean bean = (CotacaoBean) session.getAttribute("CotacaoBean");
String[] d = bean.getDadosHtml();
int i = 0;
%>
			<%if ((d.length > 0)) {%>
		<TR>
			<TD class="tabelaTitulo" colspan="2" height="25"><c:out
				value="${bean.nomRamo}" /> <c:out value="${bean.nomModalidade}" /></td>
		</TR>

		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>

		<%while (i < d.length) {
	if (d[i] != null && d[i] != "") {%>

		<tr>
			<td class="tabelaColunaEsquerda" width="180"><%=d[i]%></td>
			<td class="tabelaColunaDireita" width="319" colspan="1"><%=d[i + 1]%></td>
			<td class="tabelaColunaDireita" width="1"></td>
		</tr>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>

		<%}
i = i + 2;
}%>
		<TR>
			<%}%>			
		
			<TD class="tabelaTitulo" colspan="2" height="24">Anexos</td>
		</TR>
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
		<c:forEach var="file" varStatus="num" items="${sessionScope['fileList']}">		
		
		
			<c:if test="${bean.codSituacao == 'FINALIZADA' || bean.cotFinalData == 'FINALIZADA' || visualizaTodosAnexos == 'S' || file.value.arquivoCorrExec == 0 || file.value.arquivoCorrExec == null}">					
				<c:if test="${file.value.fileName!='' && file.value.fileName!=null}">
					<c:choose>
						<c:when test="${file.value.classifica == 'on'}">						
							<c:if test="${intranet=='1'}">
								<tr>
									<td class="tabelaColunaEsquerda" width="180">Arquivo:</td>
									<TD class="boxTexto" height="15" width="250"><A href="#" class="linkMenu"
									onclick="javascript:return downloadFile('<c:out value='${file.value.tempFile}'></c:out>','<c:out value='${file.value.fileName}'></c:out>')">
									<c:out value="${file.value.fileName}"></c:out></A></td>
								</tr>
							</c:if>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="tabelaColunaEsquerda" width="180">Arquivo:</td>
								<TD class="boxTexto" height="15" width="250"><A href="#" class="linkMenu"
								onclick="javascript:return downloadFile('<c:out value='${file.value.tempFile}'></c:out>','<c:out value='${file.value.fileName}'></c:out>')">
								<c:out value="${file.value.fileName}"></c:out></A></td>					
							</tr>
						</c:otherwise>
					</c:choose>
				</c:if>
<!--HB -->		
			</c:if>	
		</c:forEach>
		
		<tr>
			<td colspan="2" class="tabelaDivisoria"></td>
		</tr>
	</TBODY>
</TABLE>

</form>
<table border="0" width="500" cellpadding="0" cellspacing="0"
	bgcolor="#FFFFFF">
	<tr>
		<c:if test="${consulta=='S'}">
			<td align="center" height="40">
						
			<a href="javascript: history.go(-1);" class="cbutton">VOLTAR</a>
			</td>

			<c:if test="${intranet=='1' &&  (codUnidadeReal=='935' || codUnidadeReal=='939') }">
			<td align="center" height="40">
			<a name="validarComissao" class="cbutton" onclick="javascript:return alteracomissao();">ENVIAR</a></td>			
			</c:if>
		</c:if>

		<c:if test="${consulta=='N'}">

			<td align="center" height="40">
			<a href="javascript: voltaEspecifico(document.frm1);" class="cbutton">VOLTAR</a>
			
			</td>
			<td align="center" height="40">
			<a name="validar" class="cbutton" onclick="javascript:return ValidarCamposEnviar(this);">ENVIAR</a></td>
		</c:if>
	</tr>
</table>

<FORM NAME="frmConsulta" ACTION="Controller" METHOD="POST">
	<INPUT TYPE="HIDDEN" NAME="action" VALUE="pesqperiodo"/>
	<INPUT TYPE="HIDDEN" NAME="dataInicio" VALUE=""/>
	<INPUT TYPE="HIDDEN" NAME="dataFim" VALUE=""/>
	<INPUT TYPE="HIDDEN" NAME="sitCotacao" VALUE=""/>
	<INPUT TYPE="HIDDEN" NAME="novaConsulta" value="<%=request.getSession().getAttribute("intranet")%>"/>
	<INPUT TYPE="HIDDEN" NAME="cpfCnpjProp" value="<c:out value='${bean.cnpjCpf}'/>" />
	<INPUT TYPE="HIDDEN" NAME="cRamo" value="<c:out value='${bean.codRamo}'/>"/>
	<INPUT TYPE="HIDDEN" NAME="entWorkflow" VALUE="<%=request.getAttribute("entWorkflow")%>"/>
	<INPUT TYPE="HIDDEN" NAME="BPM" VALUE="<%=request.getAttribute("canal")%>"/>
</FORM>

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/MSDet.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>

