/*
 * Criado em 18/10/2006
 *
 */
package bean;

import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import db.DbAccess;

/**
 * @author erialdo
 *
 *         Riscos Diversos - Exposicao
 * 
 */
public class Cto7101Bean extends CotacaoBean {

	private static final long serialVersionUID = 7044955081217331676L;

	private String dscObjeto; // Descricao por equipamento com ano/modelo, nacionalidade,ultilizacao e valor
								// atual
	private String dscFeira; // Descricao completa da feira com local e periodo
	private String comTransp; // com transporte? 01 - Sim / 02 - Nao
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div7101Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setComTransp((String) d.get("dados01"));
		setNumSin5anos((String) d.get("dados02"));

		setDscObjeto((String) d.get("dados14"));
		setDscFeira((String) d.get("dados15"));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getComTransp());
		list.put("dados02", getNumSin5anos());
		list.put("dados03", " ");
		list.put("dados04", " ");
		list.put("dados05", " ");
		list.put("dados06", " ");
		list.put("dados07", " ");
		list.put("dados08", " ");
		list.put("dados09", " ");
		list.put("dados10", " ");
		list.put("dados11", " ");
		list.put("dados12", " ");
		list.put("dados13", " ");
		list.put("dados14", getDscObjeto());
		list.put("dados15", getDscFeira());

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;

	}

	public void setDadosHtml() {

		dados_html = new String[8]; // sempre mais um

		dados_html[0] = "Descrição por equipamento";
		dados_html[1] = getDscObjeto();

		dados_html[2] = "Descrição da Feira";
		dados_html[3] = getDscFeira();

		dados_html[4] = "Com Transporte";
		if (getComTransp().equals("01")) {
			dados_html[5] = "SIM";
		} else {
			dados_html[5] = "NÃO";
		}

		dados_html[6] = "Sinistros dos últimos 5 Anos";
		dados_html[7] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("DscObjeto");
		if (tmp == null)
			tmp = "";
		setDscObjeto(tmp);

		tmp = req.getParameter("DscFeira");
		if (tmp == null)
			tmp = "";
		setDscFeira(tmp);

		tmp = req.getParameter("ComTransp");
		if (tmp == null)
			tmp = "01";
		setComTransp(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}
//set

	public void setDscObjeto(String i) {
		if (i.length() > 500) {
			i = i.substring(0, 500);
		}
		this.dscObjeto = i;
	}

	public void setDscFeira(String i) {
		if (i.length() > 500) {
			i = i.substring(0, 500);
		}
		this.dscFeira = i;
	}

	public void setComTransp(String i) {
		this.comTransp = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}

//get  

	public String getDscObjeto() {
		return dscObjeto;
	}

	public String getDscFeira() {
		return dscFeira;
	}

	public String getComTransp() {
		return comTransp;
	}

	public String getNumSin5anos() {
		return numSin5anos;
	}

	public String[] getDadosHtml() {
		return dados_html;
	}

	public String getPaginaJSP() {
		return DIVCADJSP;
	}
}
