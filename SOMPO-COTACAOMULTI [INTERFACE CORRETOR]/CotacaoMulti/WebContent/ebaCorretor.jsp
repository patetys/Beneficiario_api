<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="eba.gethandler.*" %>
<%@ page import="java.util.*" %>
<%@ page import="bean.*" %> 
<%@ page import="util.*" %> 
<%@ page import="db.*" %>

<%		
	String pageSizeParam=request.getParameter("PageSize");
	if (pageSizeParam==null) pageSizeParam="10";
	int pageSize = Integer.parseInt(pageSizeParam);	
	String searchSubStringParam=request.getParameter("SearchSubstring");
	if (searchSubStringParam==null) searchSubStringParam="";
	String searchSubString = searchSubStringParam.toUpperCase();	

	String lastStringParam=request.getParameter("LastString");
	if (lastStringParam==null) lastStringParam="";
	String lastString = lastStringParam;	
	
	CorretorBean sb = new  CorretorBean();
	long codUnidade = Long.parseLong((String)session.getAttribute("codUnidade"));
	if (session.getAttribute("CorretorBean")==null){
		sb =  DbAccess.ListaCorretores("999999", codUnidade,"0");
		session.setAttribute("CorretorBean",sb);
	}
	else {
		sb = (CorretorBean)session.getAttribute("CorretorBean");
	}

   	int pini = sb.proxCorretor(searchSubString);
   	if (pini<0) pini=0;

   	int next = (null==request.getParameter("StartingRecordIndex")) ? 0 : Integer.parseInt(request.getParameter("StartingRecordIndex"));   
   	if (next>=pini) pini=next;

   	int pfim=pini+pageSize;
   	if (pfim>sb.size()) pfim=sb.size();   
	
	GetHandler myGetHandler = new GetHandler(response,out);	
	Record curRecord; 
	
	myGetHandler.defineField("codigo");
	myGetHandler.defineField("descricao");
	myGetHandler.defineField("codigo/descricao");
	
	for (int i=pini;i<pfim;i++){	
		curRecord=myGetHandler.createNewRecord(sb.getCodigo(i));		
		curRecord.setField("codigo",sb.getCodigo(i));
		curRecord.setField("descricao",sb.getDescricao(i));
		curRecord.setField("codigo/descricao",sb.getCodigo(i) + " " +  sb.getDescricao(i));
		myGetHandler.addRecord(curRecord);
	}
	myGetHandler.writeToClient();
%>
