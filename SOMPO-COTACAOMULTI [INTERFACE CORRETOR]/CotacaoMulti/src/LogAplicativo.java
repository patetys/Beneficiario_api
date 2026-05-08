import java.util.*;
import java.sql.*;
import javax.sql.*;

import org.apache.log4j.Logger;

import javax.naming.*;

import java.text.*;
import java.io.*;
import util.Util;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;



/*
 * Created on Jul 12, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author felipe
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LogAplicativo {
	
  private static final Logger LOGGER = Logger.getLogger(LogAplicativo.class);
	
  public static final String datasource="jdbc/siscotaMultiAws";
//  public static final String datasource="java:jboss/datasources/sqlProducao";
  
  public static final String username="yasnet";
  public static final String password="ys5net";	

  public static String formataDataAtualSql()
  {
	Calendar data = Calendar.getInstance(new Locale("pt","br"));

	int dia;
	int mes;
	int ano;
	int hora;
	int minuto;

	String strDia = "";
	String strMes = "";
	String strHora = "";
	String strMinuto = "";
	
	dia = data.get(Calendar.DAY_OF_MONTH);
	mes = data.get(Calendar.MONTH) + 1;
	ano = data.get(Calendar.YEAR);

	hora = data.get(Calendar.HOUR_OF_DAY);
	minuto = data.get(Calendar.MINUTE);

	strDia=""+dia;
	if (strDia.length()<2) strDia="0"+strDia;
	strMes=""+mes;
	if (strMes.length()<2) strMes="0"+strMes;

	strHora=""+hora;
	if (strHora.length()<2) strHora="0"+strHora;
	strMinuto=""+minuto;
	if (strMinuto.length()<2) strMinuto="0"+strMinuto;

	String ret="'"+ano+"-"+strMes+"-"+strDia+" "+strHora+":"+strMinuto+"'";
	return (ret);	
  }

  public  static void registraAction(String user,String apl,String action,HttpServletRequest req)
  {
	String datual=formataDataAtualSql();
	String params="";
    Enumeration enumeration=req.getParameterNames();
    while (enumeration.hasMoreElements())
    {
      String p=(String)enumeration.nextElement();	
      params+=p+"="+Util.trataTexto(req.getParameter(p))+";";		
    }


	if (params==null) params="";
	if (user==null) user="";
	if (apl==null) apl="";
	if (action==null) action="";


    if (params.length()>1500) params=params.substring(1500);
	if (user.length()>10) user=user.substring(10);
	if (apl.length()>40) apl=apl.substring(40);
	if (action.length()>40) action=action.substring(40);
	
	String q="INSERT INTO Yasudanet.dbo.Tab_Log_Aplic(Cod_User,Nom_Aplic,Acao,Dsc_Param,Dat_aces) VALUES(  "
		+"'"+ user+"',"
		+"'"+ apl+"',"
		+"'"+ action+"',"
		+"'"+ params+"',"
		+datual+")";
	Connection conn =null;
	try{	
		
		
		Context ctx = new InitialContext(); 
		
		DataSource ds = (DataSource)ctx.lookup(datasource); 
		conn = ds.getConnection(username, password);
		
		//Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver"); 
		//conn = DriverManager.getConnection ("jdbc:microsoft:sqlserver://SPX20112CSLUMIX:1433;User=yasnet;Password=ys5net;Database=yasudanet"); 

		Statement st1 = conn.createStatement();
		st1.executeUpdate(q);
		st1.close();
		conn.close();
	}
	catch (Exception e){
		try {
			conn.close();
		} catch (Exception eint){
			LOGGER.error(eint.getMessage(), eint);
		}
		LOGGER.error(e.getMessage(), e);
	}


  }


}
