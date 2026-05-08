/*
 * Criado em 27/10/2006
 *
 * Para alterar o gabarito para este arquivo gerado v� para
 * Janela&gt;Prefer�ncias&gt;Java&gt;Gera��o de C�digos&gt;C�digo e Coment�rios
 */
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import bean.Dominio;


/**
 * @author erialdo
 *
 * Para alterar o gabarito para este coment�rio do tipo gerado v� para
 * Janela&gt;Prefer�ncias&gt;Java&gt;Gera��o de C�digos&gt;C�digo e Coment�rios
 */
public class Util implements Serializable {
	
	private static final Logger LOGGER = Logger.getLogger(Util.class);

	private static final long serialVersionUID = 7407683386234793499L;
	private static final String CONFIG_MULTI = "/u/dadosapp/config/CotacaoMulti/config.properties";

	/**
	 * Converte data do formato DD/MM/AAAA para AAAAMMDD
	 * @param data
	 * @return
	 */
	public static String convertDataToSql(String data) {
		String dia = data.substring(0, 2);
		String mes = data.substring(3, 5);
		String ano = data.substring(6);
		return ano + mes + dia;
	}

	/**
	 * Converte data do formato DD/MM/AAAA para AAAAMMDD
	 * @param data
	 * @return
	 */
	public static int convertDataToSqlInteiro(String data) {
		return Integer.parseInt(convertDataToSql(data));
	}


	
	public static String zerosEsq(String num, int tam) {
		String ret = num;
		if (num.length() < tam) {
			while (ret.length() < tam) {
				ret = "0" + ret;
			}
		}
		return ret;
	}
	public static String formatMoeda(float v) {
		DecimalFormat df = new DecimalFormat("###,##0.00");
		String stmp = df.format(v);
		return df.format(v);
	}

	public static String formataMoeda(double v) {
		DecimalFormat df =
			(DecimalFormat) NumberFormat.getInstance(new Locale("pt", "BR"));
		df.applyPattern("###,##0.00");
		return df.format(v);
	}

	public static String formataMoeda(String v) {
		return formataMoeda(Double.parseDouble(v));
	}

	public static double formataMoedaToDB(String val) {
		DecimalFormat df =
			(DecimalFormat) NumberFormat.getInstance(new Locale("pt", "BR"));
		try {
			return df.parse(val).doubleValue();
		} catch (ParseException e) {
		}
		return 0;
	}
	/**
	 * Retorna a data atual no formato AAMMDD
	 * @return String
	 */
	public static String dataAtual() {
		Calendar data = Calendar.getInstance(new Locale("pt", "br"));
		int dia = data.get(Calendar.DAY_OF_MONTH);
		int mes = data.get(Calendar.MONTH) + 1;
		int ano = data.get(Calendar.YEAR);
		String strDia = new String((new Integer(dia)).toString());
		if (strDia.length() < 2)
			strDia = "0" + strDia;
		String strMes = new String((new Integer(mes)).toString());
		if (strMes.length() < 2)
			strMes = "0" + strMes;
		String strAno = new String((new Integer(ano)).toString());
		return strAno + strMes + strDia;
	}

	/**
	 * Retorna a hora atual no formato hhmmss
	 * @return String
	 */
	public static String horaAtual() {
		Calendar data = Calendar.getInstance(new Locale("pt", "br"));
		int hora = data.get(Calendar.HOUR_OF_DAY);
		int min = data.get(Calendar.MINUTE);
		int seg = data.get(Calendar.SECOND);
		String strHora = new String((new Integer(hora)).toString());
		if (strHora.length() < 2)
			strHora = "0" + strHora;
		String strMin = new String((new Integer(min)).toString());
		if (strMin.length() < 2)
			strMin = "0" + strMin;
		String strSeg = new String((new Integer(seg)).toString());
		if (strSeg.length() < 2)
			strSeg = "0" + strSeg;
		return strHora + strMin + strSeg;
	}

	/**
	 * Faz o download do arquivo selecionado 
	 * @param arqsCotacao
	 * @param req
	 * @param resp
	 * @return 
	 */
	public static int ListForm(
		HashMap arqsCotacao,
		HttpServletRequest req,
		HttpServletResponse resp)
		throws IOException {
		
		int ret = -1;
		HashMap tmp = null;
		
		if (arqsCotacao != null) {
			tmp =
				(HashMap) arqsCotacao.get(
					new String(
						Base64.decode(req.getParameter("nameTempFile"))));
		}
		

		String nameFile = "";
		String tempFile = "";
		String tempPath = "";
		String arqOrig="";
		String arqAux	="";
		//arqOrig = nameFile = (String) req.getParameter("nameOrigFile");		
		if (tmp == null) {
			tempFile = nameFile = (String) req.getParameter("nameTempFile");
			tempPath = "/u/dadosapp/cotacao";
		} else {
			tempFile = new String(Base64.decode((String) tmp.get("tempFile")));
			tempPath = (String) tmp.get("tempPath");
			arqOrig = nameFile = (String) tmp.get("fileName");
		}
				
		if (tempFile.indexOf(".") == -1)
			arqAux = arqOrig;
		else
			arqAux = tempFile;			
		
		/*nameFile = new ArqExists().arqExists(
				tempPath.replace('\\', '/') + "/" + tempFile);*/
		
		BuscaArquivoEmDisco buscaArquivoEmDisco = new BuscaArquivoEmDisco(tempPath.replace('\\', '/') + "/" + tempFile);
		String path = buscaArquivoEmDisco.verificaCaminhoValidoArquivo(true);		
				
		if (path == null)
			return -999;

		//n�o alterei o algoritimo original, pois por motivos do WAS utilizar byteArray espec�fico.
		
		
		
		File file = new File(path);
		
		if (file.exists()) {					
			if (arqOrig.endsWith(".pdf")) {
				resp.setContentType("application/pdf"); // Para arquivos Pdf
			} else {
				if (arqOrig.endsWith(".txt")) {
					resp.setContentType("text/html"); //para arquivos TXT
				} else {
					if (arqOrig.endsWith(".eml")) {
						resp.setContentType("application/octet-stream");
					} else {
						if (arqOrig.endsWith(".doc")) {
							resp.setContentType("application/msword");
						} else {
							if (arqOrig.endsWith(".xls")) {
								resp.setContentType("application/vnd.ms-excel");
							} else {
								resp.setContentType("application/octet-stream");
								//para qualquer documento (Binario)

							}
						}
					}
				}
			}

			//resp.setHeader("Content-Disposition","attachment; filename=" + String.valueOf('"') + nameFile + String.valueOf('"') + ";");
			resp.setHeader(
				"Content-Disposition",
				"filename="
					+ String.valueOf('"')
					+ arqOrig
					+ String.valueOf('"')
					+ ";");

			resp.setBufferSize(1024);

			InputStream in = null;

			try {
				in = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				while (in.read(buffer) > 0) {
					resp.getOutputStream().write(buffer);
				}
				resp.getOutputStream().flush();
				//resp.getOutputStream().close();
				in.close();
			} catch (IOException e) {
				++ret;
				LOGGER.error(e.getMessage(), e);
				return ret;
			}
			ret++;

		}
		return ret;
	} //fim do listForm

	/**
	 * Retorna a data e a hora atual
	 * @return String
	 */
	public static String getCurrentDateTime() {
		Calendar now = Calendar.getInstance(new Locale("pt", "br"));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		return df.format(now.getTime());
	}

	/**
	 * Gera nova string para o campo key_ext da tabela tab_cotacao_transp
	 * 
	 * posicao  conteudo
	 * 0        Letra random
	 * 1..4     DDMM
	 * 5        Letra random
	 * 6..9     YYYY
	 * 10..11   Letras random
	 * 12..17   HHMMSS
	 * @return 
	 */
	public static String geraNumeroLote() {
		Calendar now = Calendar.getInstance(new Locale("pt", "br"));
		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
		String strNow = df.format(now.getTime());
		return getRandom()
			+ strNow.substring(0, 4)
			+ getRandom()
			+ strNow.substring(4, 8)
			+ getRandom()
			+ getRandom()
			+ strNow.substring(8);
	}

	private static String getRandom() {
		char c = (char) ((int) ((Math.random() * 25) + 65));
		return String.valueOf(c);
	}

	public static String trataTexto(String texto) {
		String s = "'";
		return texto.replace(s.toCharArray()[0], ' ');
	}

	public static String limitaTexto(String texto, int max) {
		String t = texto;
		t = trataTexto(t);
		if (t.length() > max) {
			t = t.substring(0, max - 1);
		}
		return t;
	}
	
	/**
	 * Efetua a substitui��o de textos.
	 * 
	 * @param str
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replaceSubString(String str, String pattern, String replace) {
		int slen = str.length();
		int plen = pattern.length();
		int s = 0, e = 0;
		StringBuffer result = new StringBuffer(slen * 2);
		char[] chars = new char[slen];

		while ((e = str.indexOf(pattern, s)) >= 0) {
			str.getChars(s, e, chars, 0);
			result.append(chars, 0, e - s).append(replace);
			s = e + plen;
		}
		str.getChars(s, slen, chars, 0);
		result.append(chars, 0, slen - s);
		return result.toString();
	}

	/**  
	 * @param e
	 * @param msg
	 * Fun��o prepara mensagem erro para enviar via e-mail * 
	 * 
	 */
//	public static void trataErro(Exception e, String msg) {
//		try {
//
//			InternetAddress to[] =
//				{ new InternetAddress("tjgomes@yasuda.com.br")};
//			InternetAddress cc[] =
//				{ new InternetAddress("tjgomes@yasuda.com.br")};
//			//if (req.getServerName().equals("spx10422psluynl.yasuda.com.br") || req.getServerName().equals("localhost")) {
//
//			//InternetAddress to[] = { new InternetAddress("fukashi@yasuda.com.br")};
//			//InternetAddress cc[] =
//			//	{ new InternetAddress("erialdo@yasuda.com.br"), new InternetAddress("ricardo@yasuda.com.br")};
//
//			String subject = "Siscota Multi : Erro Cota��o Diversos";
//			String message =
//				"Erro na cota��o Web favor verificar mensagem \n Mensagem de Erro = "
//					+ e.getMessage()
//					+ "\n Comando Sql="
//					+ msg
//					+ " \n ";
//			sendMail(to, cc, subject, message);
//
//		} catch (AddressException e1) {
//			e1.printStackTrace();
//		}
//	}
	
	/*** 
	 * **Fun��o envia e-mail**
	 * @param mailTo
	 * @param mailCC
	 * @param subject
	 * @param message
	 * @param arqboleto 
	 * 
	 */
	private static void sendMail(
		InternetAddress[] mailTo,
		InternetAddress[] mailCC,
		String subject,
		String message) {
		java.util.Properties props = new java.util.Properties();
		props.put("mail.smtp.host", "sp320104psluexc");
		props.put("mail.smtp.port", "" + 25);
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress("sistemas@yasuda.com.br"));
			InternetAddress to[] = mailTo;
			InternetAddress cc[] = mailCC;
			msg.setRecipients(Message.RecipientType.TO, to);
			msg.setRecipients(Message.RecipientType.CC, cc);
			msg.setSubject(subject);
			MimeMultipart mcontent = new MimeMultipart();
			MimeBodyPart mtext = new MimeBodyPart();
			mtext.setContent(message + "\n\n\n\n\n", "text/plain");
			mcontent.addBodyPart(mtext, 0);

			msg.setContent(mcontent);
			Transport.send(msg);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * metodo criado para cuidar de problemas do java 1.3
	 * @author dcmaia
	 * 
	 * @param string
	 * @param split
	 * @return
	 */
	
	public static String[] split(String input, String str) {
		int strLength = str.length();
		int tokenCount = 0;
		int strIndex = -strLength;
		do {
			strIndex = input.indexOf(str, strIndex + strLength);
			tokenCount++;
		} while (strIndex >= 0);
		String[] tokens = new String[tokenCount];
		int tokenIndex = 0;
		strIndex = -strLength;
		do {
			int index = input.indexOf(str, strIndex + strLength);
			if (index < 0) {
				tokens[tokenIndex] = input.substring(strIndex + strLength);
			} else {
				tokens[tokenIndex] =
					input.substring(strIndex + strLength, index);
			}
			strIndex = index;
			tokenIndex++;
		} while (strIndex >= 0);
		return tokens;
	}
	
	public List<? extends Dominio> removeItensListDominio(String [] itens, List<? extends Dominio> lista) {

		int unidPass;
		if (itens != null) {
			Iterator<Dominio> it = (Iterator<Dominio>) lista.iterator();
			while (it.hasNext()) {
				Dominio dom = it.next();
				if (dom.getId() != null && !"".equalsIgnoreCase(dom.getId())) {
					unidPass = Integer.parseInt(dom.getId());
					for (int i = 0; i < itens.length; i++) {
						if (Integer.parseInt(itens[i].trim()) == unidPass) {
							it.remove();
						}
					}
				}
			}
		}
		return lista;
	}
	
	
	public HashMap removeItensMap(String[] itens, HashMap map) {

		int unidPass;
		if (itens != null) {
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				key = (key.split("-")[0]).trim();
				if (key != null && !"".equalsIgnoreCase(key)) {
					unidPass = Integer.parseInt(key);
					for (int i = 0; i < itens.length; i++) {
						if (Integer.parseInt(itens[i].trim()) == unidPass) {
							it.remove();
						}
					}
				}
			}
		}
		return map;
	}
	
	public static String [] getUnidadesRestritasSiscotaMulti(String codCorretor){
		String keyComp = CotacaoMultiProperties.getInstance().getProperty(codCorretor.trim());
 		String [] key = null;
		if(keyComp != null){
			key = CotacaoMultiProperties.getInstance().getProperty(keyComp).split(","); 
		}
			
		return key;
	}
	
	
	public String getAttributeValueProperties(String attribute, File file) throws IOException{
		Properties prop = Util.getProperties(file);
		return prop.getProperty(attribute);
	}
	
	private static Properties getProperties(File file) throws IOException{
		InputStream fis = new FileInputStream(file);
		Properties prop = new Properties();
		prop.load(fis);
		return prop;
	}
	
	private static void writeProperties(Properties prop, File file, String comments) throws IOException{
		FileOutputStream fout = new FileOutputStream(file);
		prop.store(fout, comments);
		fout.close();
	}
	
	public static void getRequestDispatcherMesagem(HttpServletRequest req, HttpServletResponse resp, String attrButton, 
			String attrMsg, String bt, String msg) throws ServletException, IOException{
		req.setAttribute(attrMsg, msg);
		req.setAttribute(attrButton, bt);
		req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
		return;
	}
	
	public static void readProperties(HttpServletRequest req, HttpServletResponse resp,
			String properties) throws ServletException, IOException{
		
		try{
			File file = new File(properties);
			Properties prop = getProperties(file);
			Enumeration en = prop.propertyNames();
			
			StringBuilder msg = new StringBuilder("");
			
			while(en.hasMoreElements()){
				String key = (String) en.nextElement();
				String value = (String) prop.get(key);
				msg.append("Chave: " + key + " Value: " + value+ "\n");
			}
			
			getRequestDispatcherMesagem(req, resp, "mostraBotoes", "mensagemConfirmacao_1", "false", msg.toString());
			return;

		}catch(Exception e){
			getRequestDispatcherMesagem(req, resp, "mostraBotoes", "mensagemConfirmacao_2", "false", "Falha ao ler o properties\n"+e.getMessage());
			return;
		}
	}
	
	
	public static void removeProperties(HttpServletRequest req, HttpServletResponse resp,String properties, 
			String attribute, String comments) throws ServletException, IOException{
		
		try{
			File file = new File(properties);
			Properties prop = getProperties(file);
			prop.remove(attribute);
			writeProperties(prop, file, comments);
			getRequestDispatcherMesagem(req, resp, "mostraBotoes", "mensagemConfirmacao_1", "false", "Properties Alterado com Sucesso! (Remoção do atributo)");
			return;

		}catch(Exception e){
			getRequestDispatcherMesagem(req, resp, "mostraBotoes", "mensagemConfirmacao_2", "false", "Falha ao alterar o properties\n"+e.getMessage());
			return;
		}
	}	
	
	public static void writeProperties(HttpServletRequest req, HttpServletResponse resp,String properties, 
			String attribute, String value, String comments) throws ServletException, IOException{
		
		try{
			File file = new File(properties);
			Properties prop = getProperties(file);
			prop.setProperty(attribute, value);
			writeProperties(prop, file, comments);
			getRequestDispatcherMesagem(req, resp, "mostraBotoes", "mensagemConfirmacao_1", "false", "Properties Alterado com Sucesso!");
			return;

		}catch(Exception e){
			getRequestDispatcherMesagem(req, resp, "mostraBotoes", "mensagemConfirmacao_2", "false", "Falha ao alterar o properties\n"+e.getMessage());
			return;
		}
	}

	public static String getConfigMulti() {
		return CONFIG_MULTI;
	}
	
}

