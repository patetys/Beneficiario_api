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
 * 
 *         Riscos Diversos - Estacionários
 */
public class Cto7106Bean extends CotacaoBean {

	private static final long serialVersionUID = 4663485766990907054L;

	private String dscEquip; // Descricao por equipamento ano/modelo ,ultilizacao e valor atual
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div7106Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {
		setNumSin5anos((String) d.get("dados01"));
		setDscEquip((String) d.get("dados14"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getNumSin5anos());
		list.put("dados02", " ");
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
		list.put("dados14", getDscEquip());
		list.put("dados15", " ");

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;

	}

	public void setDadosHtml() {

		dados_html = new String[4]; // sempre mais 1

		dados_html[0] = "Descrição por Equipamento";
		dados_html[1] = getDscEquip();

		dados_html[2] = "Sinistros dos últimos 5 Anos";
		dados_html[3] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		setDscEquip(req.getParameter("DscEquip"));
		setNumSin5anos(req.getParameter("NumSin5anos"));

	}

//set	
	public void setDscEquip(String i) {
		this.dscEquip = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}

//get
	public String getDscEquip() {
		return dscEquip;
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
