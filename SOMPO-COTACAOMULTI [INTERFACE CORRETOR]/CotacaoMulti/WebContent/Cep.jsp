<%@ page import="java.util.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Studio">
<%
	HashMap dadosCep = new  HashMap();
	dadosCep = (HashMap)request.getAttribute("cep");
%>
<script>

<% 	if (dadosCep!=null){
		if (dadosCep.size()==1){ %>
			parent.document.frm1.End.value='';	
			parent.document.frm1.Complemento.value='';	
			parent.document.frm1.Bairro.value='';	
			parent.document.frm1.Cid.value='';	
			parent.document.frm1.cobUF.value='';	
			parent.document.frm1.msgCEP.value='';	
			alert('CEP inexistente');
			parent.document.frm1.Cep.focus();
<% 		}
		else { %>	
				
			parent.document.frm1.End.value=' <%=(String)dadosCep.get("TipLogr")%>' + ' ' + '<%=(String)dadosCep.get("NomLogr")%>';			
			parent.document.frm1.Complemento.value='';
			parent.document.frm1.Numero.value='';
			parent.document.frm1.Bairro.value='<%=(String)dadosCep.get("NomBairro")%>';	
			parent.document.frm1.Cid.value='<%=(String)dadosCep.get("NomCid")%>';	
			parent.document.frm1.cobUF.value='<%=(String)dadosCep.get("SigUF")%>';	
			parent.document.frm1.msgCEP.value='';
			parent.document.frm1.VlRisco.value='';

<% 			if(((String)dadosCep.get("NomLogr")).trim().equals("")) { %>
				parent.document.frm1.End.readOnly='';	
				parent.document.frm1.Bairro.readOnly='';
				parent.document.frm1.End.focus();

<%			} 
			else { %>		
				parent.document.frm1.End.readOnly="readOnly";
				parent.document.frm1.Bairro.readOnly="readOnly";
				parent.document.frm1.Numero.focus();					

<%			}
		}
	} %>
</script>

</HEAD>
<BODY>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30723727-1']);
  _gaq.push(['_trackPageview','/APP/CotacaoMulti/Cep.jsp']);



  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</BODY>
</HTML>

