/*
 * Criado em 06/10/2006
 *
 */
package db;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import bean.Corretor;
import bean.CorretorBean;
import bean.CotacaoBean;
import bean.DepartamentoBean;
import bean.Dominio;
import bean.Emissao;
import bean.Modalidade;
import bean.Produtor;
import bean.Profissao;
import bean.Ramo;
import bean.RamoAtividade;
import bean.UnidadeBean;
import br.com.sompo.cotacaomulti.dto.ExcecaoCorretorDTO;
import br.com.sompo.cotacaomulti.dto.HistoricoExternoDTO;
import br.com.sompo.cotacaomulti.dto.HistoricoInternoDTO;
import br.com.sompo.cotacaomulti.dto.RamoExcecaoDTO;
import br.com.sompo.cotacaomulti.dto.RestricaoDTO;
import br.com.sompo.cotacaomulti.exception.SusepHistoricoException;
import br.com.sompo.cotacaomulti.service.SusepHistoricoService;
import util.*;

/**
 * @author erialdo
 * 
 */
public class DbAccess implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(DbAccess.class);

	private static String codCorretor = "";
	private static final long serialVersionUID = 4851657423640467571L;
	private static final String SQL_PROD = "java:comp/env/jdbc/sqlProducao";
	private static final String YASUDANET_AWS = "jdbc/siscotaMultiAws";

//	private static final String SQL_PROD = "java:jboss/datasources/sqlProducao";
//	private static final String YASUDANET_AWS = "java:jboss/jdbc/siscotaMultiAws";

	

	public static final String COTACAO_LIMITE_INFERIOR = "00000000";
	public static final String COTACAO_VALOR_INICIAL = "00000001";
	public static final String COTACAO_LIMITE_SUPERIOR = "99999999";

	public static final String COTACAO = "1";
	public static final String RECOTACAO = "2";
	public static final int PESSOA_FISICA = 0;
	public static final int PESSOA_JURIDICA = 1;

	protected Connection connACID = null;
	protected ArrayList<Statement> stACIDList = null;

	/*
	 * public static final String[] situacoesCotacaoList = {" "," "," "," "," ",
	 * " "," "," "," "," "," "," "," ", "PENDENTE","FINALIZADA"
	 * ,"DECLINADA","FINALIZADA","DECLINADA","SOLIC. INFO."}; // [13] = "PENDENTE";
	 * // [14] = "FINALIZADA"; // [15] = "DECLINADA"; // [16] = "FINALIZADA"; //
	 * [17] = "CANCELADA"; // [18] = "SOLIC. INFO.";
	 */
	// Deifinicoe antigas /////////////////
	// [13] = "PENDENTE";
	// [14] = "ACEITA";
	// [15] = "RECUSADA";
	// [16] = "ACEITA";
	// [17] = "CANCELADO";
	// [18] = "SOLIC. INFO.";

	public static Hashtable<String, Object> situacoesCotacaoList() {
		Hashtable<String, Object> list = new Hashtable<String, Object>();
		list.put("0", "");
		list.put("1", "");
		list.put("2", "");
		list.put("3", "");
		list.put("4", "");
		list.put("5", "");
		list.put("6", "");
		list.put("7", "");
		list.put("8", "");
		list.put("9", "");
		list.put("10", "");
		list.put("11", "");
		list.put("12", "");
		list.put("13", "PENDENTE");
		list.put("14", "FINALIZADA");
		list.put("15", "DECLINADA");
		list.put("16", "FINALIZADA");
		list.put("17", "DECLINADA");
		list.put("18", "SOLIC. INFO.");
		list.put("99", "");
		return list;
	}

	public static void apagaTemporarios(CotacaoBean cot) {
		HashMap<String, Object> anexos = cot.getArqsEmailCotacao();

		if (anexos != null) {
			Iterator it = anexos.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				HashMap<String, Object> tmp = (HashMap) anexos.get(key);
				String tempFile = (String) tmp.get("tempFile");
				cot.delArqsEmailCotacao(tempFile, false);
			}
		}
	}

	//JIRA-168467
	public static final String[] RAMO_DIVERSOS = { " ",
			"110 - Riscos Nomeados e Operacionais", "112 - Empresarial",
			"710 - Riscos Diversos", "340 - Riscos de Petróleo", "150 - Roubo", "670 - Riscos de Engenharia",
			"750 - Garantia Obrigações Contratuais",
			"460 - Fiança Locatícia", "620 - Penhor Rural", "300 - Benfeitoria Rural", "410 - Lucros cessantes" };


	public static final String[] MOTIVO = { "00 - Desconto acima do permitido", "01 - Classe 3", "02 - Classe 4",
			"03 - Endosso", "04 - Existe sinistro (Renovação=Não)", "05 - Outros" };

	public static Hashtable<String, String> recuperaListaTpEmissao() {
		Hashtable<String, String> list = new Hashtable<String, String>();
		list.put("99", " ");
		list.put("00", "Seguro Novo");
		list.put("01", "Renov Congênere");
		list.put("02", "Renov Sompo");
		list.put("03", "Endosso");
		return list;

	}

	public static List<Emissao> recuperaListaTpEmissaoBean() {
		List<Emissao> list = new ArrayList<Emissao>();
		Hashtable<String, String> hash = recuperaListaTpEmissao();
		hash.remove("99");
		Emissao emissao;

		Iterator keyHash = hash.keySet().iterator();
		String element;
		String key;
		while (keyHash.hasNext()) {
			emissao = new Emissao();
			key = (String) keyHash.next();
			emissao.setCodigo(key);
			emissao.setNome((String) hash.get(key));
			list.add(emissao);
		}
		return list;
	}

	public static Hashtable<String, Object> recuperaListaRamos() {
		Hashtable<String, Object> list = new Hashtable<String, Object>();
		list.put("0", " ");
		list.put("100", "D&O (Directors and Officers)");//Atendimento JIRA-76451
		list.put("110", "Riscos Nomeados e Operacionais");
		list.put("112", "Empresarial");
		list.put("113", "Residencial");
		list.put("114", "Condomínio");
		list.put("710", "Riscos Diversos");
		list.put("340", "Riscos de Petróleo");
		list.put("150", "Roubo");
		list.put("670", "Riscos de Engenharia");
		list.put("510", "Responsabilidade Civil");
		list.put("750", "Garantia Obrigações Contratuais");
		list.put("460", "Fiança Locatícia");
		list.put("620", "Penhor Rural");
		list.put("300", "Benfeitoria Rural");
		list.put("410", "Lucros Cessantes");
		list.put("780", "RC Profissional");
		return list;
	}

	public static Hashtable<String, Object> recuperaListaMotivo() {
		Hashtable<String, Object> list = new Hashtable<String, Object>();
		// list.put(" ","Nenhum");
		list.put("0", "Desconto acima do permitido");
		list.put("1", "Classe 3");
		list.put("2", "Classe 4");
		list.put("3", "Endosso");
		list.put("4", "Existe sinistro (Renovação=Não)");
		list.put("5", "Outros");
		return list;
	}

	public static Hashtable<String, Object> recuperaListaTpModalidade(int ramo) {

		Hashtable<String, Object> list = new Hashtable<String, Object>();
		list.put("00", "");

		if (ramo == 150) {
			list.put("01", "All Risk's");
			list.put("02", "Comercial/Industrial");
			list.put("03", "Residencial");
		} else if (ramo == 112) {
			list.put("01", "Empresarial");
			list.put("02", "Imobiliário Empresarial");
		} else if (ramo == 113) {
			list.put("01", "Residencial");
			list.put("02", "Imobiliário Residencial");
		} else if (ramo == 510) {
			// Modalidades anteriores a data 04/02/2016
			/*
			 * list.put("01", "Anuncios Luminosos/Antenas"); list.put("02", "Operacoes");
			 * list.put("03", "Prestacao de Servicos"); list.put("04", "Clubes");
			 * list.put("05", "Condominios, Proprietarios e Locatarios"); list.put("06",
			 * "Guarda de Veiculos"); list.put("07", "Estabelecimento de Ensino" );
			 * list.put("08", "Obras Civis"); list.put("09",
			 * "Exposisao e Feira de Amostra"); list.put("10", "Farmacias e Drogarias");
			 * list.put("11", "Promocoes e Eventos"); list.put("12",
			 * "Hospedagem/Restaurantes/Bares/Sim."); list.put("13",
			 * "Operacoes de Vigilancia"); list.put("14", "Familiar"); list.put("15",
			 * "Auditorio"); list.put("16", "Carga e Descarga"); list.put("17",
			 * "Armazens Gerais");
			 */
			// Novas modalidades definidas nas CRQ278579
			list.put("18", "RC Anúncios e/ou Antenas");
			list.put("19", "RC Armazéns Gerais e Similares");
			list.put("20", "RC Condomínios Comerciais (''Shopping Centers'')");
			list.put("21", "RC Condomínios, Proprietários e Locatários de Imóveis");
			list.put("22", "RC Familiar");
			list.put("23", "RC Guarda de Embarcações de Terceiros");
			list.put("24", "RC Guarda de Veículos de Terceiros ");
			list.put("25",
					"RC Obras Civis e/ou Prestação de Serviços de Montagem, Instalação e/ou Assistência Técnica e manutenção, de Máquinas, Equipamentos e Aparelhos em Geral");
			list.put("26", "RC Operações - Estabelecimentos Comerciais e/ou Industriais");
			list.put("27", "RC Participação em Exposições ou em Feiras de Amostras");
			list.put("28", "RC Prestação de Serviços de Movimentação de Cargas");
			list.put("29", "RC Prestação de Serviços em Locais de Terceiros, de Limpeza e Manutenção Geral de Imóveis");
			list.put("30", "RC Promoção de Eventos Artísticos, Esportivos e Similares");
			list.put("31", "RC Promoção de Exposições e Feiras de Amostras");
			list.put("32", "RC Transporte de Passageiros em Embarcações");
		} else if (ramo == 670) {
			// Modalidades anteriores a data 04/02/2016
			/*
			 * list.put("01", "Yasuda Engenharia"); list.put("02", "QM/EE/EBV");
			 */
			// Novas modalidades definidas nas CRQ278579
			list.put("03", "Instalação e montagem");
			list.put("04", "Obras civis em construção");
			list.put("05", "Obras civis em construção e Instalação e montagem");
		} else if (ramo == 710) {
			list.put("01", "Exposição");
			list.put("02", "Portáteis");
			list.put("03", "Móveis");
			list.put("04", "Instrumentos Musicais");
			list.put("05", "Arrendados");
			list.put("06", "Estacionários");
			list.put("07", "Valores");
			list.put("08", "Anúncios Luminosos");
			list.put("09", "Cinematográficos");
			list.put("10", "Operações sobre água");
			list.put("11", "Sompo equipamentos");
			list.put("12", "Compreensivo de Veículos");
			list.put("13", "Tarifa (demais modalidades)");
			list.put("15", "Roubo All Risk's");
			list.put("16", "Roubo Comercial/Industrial");
			list.put("17", "Roubo Residencial");
		} else if (ramo == 300) {
			list.put("01", "Móveis");
			list.put("02", "Estacionários");
		} else if (ramo == 620) {
			list.put("01", "Móveis");
			list.put("02", "Estacionários");
		} else if (ramo == 780) {
			list.put("01", "Profissional Contabilista");
//			JIRA-117284
//			list.put("02", "Profissional Corretores de Seguros");			
			list.put("02", "");
			list.put("03", "Profissional Engenheiros e Arquitetos");
			list.put("04", "Profissional Notórios e Registradores");
			list.put("05", "Profissional de Agência de Turismo");
			list.put("06", "Miscellaneous");
		}

		return list;

	}

	public List<Modalidade> recuperaListaModalidadeBean(int ramo) {

		List<Modalidade> modalidades = new ArrayList<Modalidade>();
		Map<String, Object> hashModalidade = recuperaListaTpModalidade(ramo);
		hashModalidade.remove("00");
		Iterator itHash = hashModalidade.entrySet().iterator();
		Modalidade modalidade;

		while (itHash.hasNext()) {
			Map.Entry map = (Map.Entry) itHash.next();
			modalidade = new Modalidade();
			modalidade.setId((String) map.getKey());
			modalidade.setNome((String) map.getValue());
			modalidades.add(modalidade);
		}

		return modalidades;
	}

	public static String getCodCorrUnico() {
		return codCorretor;
	}

	private static void fechaConexao(ResultSet rs, Statement select, Connection conn) {
		LOGGER.info("[DbAccess] - (fechaConexao) Statement : Iniciando o metodo");
		try {

			if (rs != null && !rs.isClosed())
				rs.close();

		} catch (Exception eint) {
			LOGGER.error("[DbAccess] - (fechaConexao) Statement - Try 1 - YasForms-DbAccess: " + eint.getMessage(), eint);
		}

		try {

			if (select != null && !select.isClosed())
				select.close();

		} catch (Exception eint) {
			LOGGER.error("[DbAccess] - (fechaConexao) Statement - Try 2 - YasForms-DbAccess: " + eint.getMessage(), eint);
		}

		try {

			if (conn != null && !conn.isClosed())
				conn.close();

		} catch (Exception eint) {
			LOGGER.error("[DbAccess] - (fechaConexao) Statement - Try 3 - YasForms-DbAccess: " + eint.getMessage(), eint);
		}
		LOGGER.info("[DbAccess] - (fechaConexao) Statement : Finalizando o metodo");
	}
	
	private static void fechaConexao(ResultSet rs, PreparedStatement select, Connection conn) {
		LOGGER.info("[DbAccess] - (fechaConexao) PreparedStatement : Iniciando o metodo");
		try {

			if (rs != null && !rs.isClosed())
				rs.close();

		} catch (Exception eint) {
			LOGGER.error("[DbAccess] - (fechaConexao) PreparedStatement - Try 1 - YasForms-DbAccess: " + eint.getMessage(), eint);
		}

		try {

			if (select != null && !select.isClosed())
				select.close();

		} catch (Exception eint) {
			LOGGER.error("[DbAccess] - (fechaConexao) PreparedStatement - Try 2 - YasForms-DbAccess: " + eint.getMessage(), eint);
		}

		try {

			if (conn != null && !conn.isClosed())
				conn.close();

		} catch (Exception eint) {
			LOGGER.error("[DbAccess] - (fechaConexao) PreparedStatement - Try 3 - YasForms-DbAccess: " + eint.getMessage(), eint);
		}
		LOGGER.info("[DbAccess] - (fechaConexao) PreparedStatement : Finalizando o metodo");
	}

	public List<UnidadeBean> recuperaListaUnidadeBean(String corretor, long codUnidade) {

		UnidadeBean unidade = null;
		List<UnidadeBean> list = new ArrayList<UnidadeBean>();
		HashMap<String, Object> map = DbAccess.recuperaListaUnidades(corretor, codUnidade);
		Iterator itUnidades = map.keySet().iterator();

		while (itUnidades.hasNext()) {
			String key = (String) itUnidades.next();
			String codUnidadeMap = key.split("-")[0].trim();
			String codProdutorMap = key.split("-")[1].trim();
			String nomeDeptoMap = ((String) map.get(key)).split("-")[1].trim();
			unidade = new UnidadeBean();
			unidade.setUnidNegocio(codUnidadeMap);
			unidade.setCodProdutor(codProdutorMap);
			unidade.setNomDepto(nomeDeptoMap);
			list.add(unidade);
		}

		return list;
	}

	public static HashMap<String, Object> recuperaListaUnidades(String corretor, long codUnidade) {

		LOGGER.info("> INICIO DBACCESS.recuperaListaUnidades para corretor: " + corretor + " e unidade: " + codUnidade);
		
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		HashMap<String, Object> list = new LinkedHashMap<String, Object>();
		String query = "select cod_corretor, unid_negocio, nom_depto,cod_produtor "
				     + " from ged.dbo.TBS_Corr_Prod_Unid as u "
				     + " inner join ged.dbo.tbs_depto as d on u.unid_negocio = d.cod_depto "
				     + " where cod_corretor= '" + corretor + "'"
				     + " and d.COMP_CODE='2000'"
				     + " order by cod_corretor, nom_depto ";

		LOGGER.info("recuperaListaUnidades query: " + query);
		try {
			conn = getConn();
			LOGGER.info("recuperaListaUnidades conn: " + conn);
			select = conn.createStatement();
			LOGGER.info("createStatement select: " + select);
			rs = select.executeQuery(query);

			while (rs.next()) {
				list.put(rs.getString("unid_negocio") + "-" + rs.getString("cod_produtor"),
						Util.zerosEsq(rs.getString("unid_negocio"), 4) + " - " + rs.getString("nom_depto"));
				LOGGER.info("list: " + list.size());
			}
		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		LOGGER.info("< FIM DBACCESS.recuperaListaUnidades para corretor: " + corretor + " e unidade: " + codUnidade);
		return list;
	}

	/**
	 * @param string
	 * @param string2
	 * @param string3
	 * @param codUnidade
	 * @return
	 */
	public static List<Corretor> ListaCorretores(String corretorCode, String corretorName, String numSusep,
			long codUnidade, boolean onSelect) throws Exception {

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		List<Corretor> list = new ArrayList<Corretor>();
		try {

			String query = "select c.cod_corretor, c.corretor, c.cid_corretor" + " from GED.DBO.TBS_CORRETOR as c ";

			if (codUnidade != 0) {
				query += "inner join GED.DBO.TBS_CORR_PROD_UNID as u " + " on c.cod_corretor = u.cod_corretor ";
			}
			query += " where c.cod_ativo = 1 ";
			//PORTAL_CORRETOR
			if (numSusep.equalsIgnoreCase("999999") == false) {
				//NOVA_SUSEP
//				query += " AND (NUM_SUSEP_COMPLETO = '" + numSusep + "'";
//				query += " OR NUM_SUSEP = '" + numSusep + "') ";
				String suseps = new SusepHistoricoService().obterSusepsPorSusepToQueryIN(numSusep);
				query += " AND (NUM_SUSEP_COMPLETO IN (" + suseps + ")";
				query += " OR NUM_SUSEP IN (" + suseps + ") ";
			}

			if (codUnidade != 0) {
				query += " and u.unid_negocio = " + codUnidade;
			}
			if (corretorCode != "") {
				if (corretorCode.length() > 7) {
					corretorCode = corretorCode.substring(0, 7);
				}
				if (corretorCode.trim().length() == 0) {
					corretorCode = "0";
				}
				if (onSelect) {
					query += " and c.cod_corretor = '" + Long.parseLong(corretorCode) + "' ";
				} else {
					query += " and c.cod_corretor like '" + Long.parseLong(corretorCode) + "%' ";
				}
			}
			if (corretorName != "") {
				query += " and c.corretor like '%" + corretorName + "%' ";
			}
			query += " order by corretor ";

			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);

			while (rs.next()) {

				Corretor corretor = new Corretor();
				// limpa problemas de string gerados pelo nome do corretor
				String corr = rs.getString("corretor");
				if (corr != null && !corr.equals("")) {
					corr = Util.trataTexto(corr);
					corr = Util.replaceSubString(corr, ":", "");
					corr = Util.replaceSubString(corr, "'", "");
					corr = Util.replaceSubString(corr, "\"", "");
				}
				corretor.setCodCorretor(Util.zerosEsq(rs.getString("cod_corretor"), 07));
				corretor.setCorretor(Util.zerosEsq(rs.getString("cod_corretor"), 07) + " " + corr + "/"
						+ rs.getString("cid_corretor"));
				// corretor.setCorretor(util.zerosEsq(rs.getString("cod_corretor"),
				// 07)+ " / "+ rs.getString("cid_corretor"));
				list.add(corretor);

			}
			rs.close();

		} finally {
			fechaConexao(rs, select, conn);
		}

		return list;
	}

	// Hebert
	public static ArrayList<DepartamentoBean> recuperaListaDepartamento(String unidade) {
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		ArrayList<DepartamentoBean> list = new ArrayList<DepartamentoBean>();

		String query = "select cod_depto, nom_depto  from ged.dbo.TBS_Depto ";
		// Caso o usuario pertenca ao grupo Servico Operacional ou Produto
		// (unidade igual a zero), entao
		// todos dos departamentos devem ser exibidos.
		if ("0".equalsIgnoreCase(unidade)) {
			// query += "where (cod_depto > 1000 or cod_depto in(211, 231, 251,
			// 271)) ";
			query += "where (cod_depto > 1000 or cod_depto < 900) ";
			query += "and depto_ativo =  'S' ";
		} /*
			 * else if (unidade.equals("251") || unidade.equals("271")) { query +=
			 * "where cod_depto in(251,271) and depto_ativo =  'S' "; }
			 */ else {
			query += "where cod_depto = " + unidade + " and depto_ativo =  'S' ";
		}

		query += "order by cod_depto";

		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);

			while (rs.next()) {
				DepartamentoBean bean = new DepartamentoBean();
				bean.setCodigo(rs.getString("cod_depto"));
				bean.setNome(rs.getString("nom_depto"));
				list.add(bean);
			}

		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return list;
	}
	
	
	public Produtor recuperaProdutor(String codCorr, String codUnidade) {

		Produtor produtor 						= new Produtor();
		PreparedStatement prepareStatement 		= null;
		Connection conn 						= null;
		ResultSet rs 							= null;
		
		try {
			
			StringBuilder sql = new StringBuilder()
			.append(" SELECT CORR_UNIDADES.COD_PRODUTOR, PRODUTOR.PRODUTOR, PRODUTOR.email_produtor ")
			.append("FROM GED.DBO.TBS_CORR_PROD_UNID AS CORR_UNIDADES ")
			.append("LEFT JOIN GED.DBO.TBS_PRODUTOR AS PRODUTOR ")
			.append("ON CORR_UNIDADES.COD_PRODUTOR = PRODUTOR.COD_PRODUTOR WHERE CORR_UNIDADES.UNID_NEGOCIO = ? ")
			.append("AND CORR_UNIDADES.COD_CORRETOR = ? ");
			
			conn = execSQLSelect();
			prepareStatement = conn.prepareStatement(sql.toString());
			
			prepareStatement.setObject(1, codUnidade);
			prepareStatement.setObject(2, codCorr);
			
			rs = prepareStatement.executeQuery();
			
			if (rs.next()) {
				produtor.setCodigo(String.valueOf(rs.getInt("COD_PRODUTOR")));
				produtor.setNome(rs.getString("PRODUTOR") != null ? rs.getString("PRODUTOR") : "  ");
				produtor.setEmail(rs.getString("email_produtor") != null ? rs.getString("email_produtor") : "-");
			}
			rs.close();

		} catch (Exception e) {
			LOGGER.error("CotacaoAuto - " + e.getMessage(), e);
		} finally {		
			
			fechaConexao(rs, prepareStatement, conn);
		}

		return produtor;
	}
	
	public Produtor recuperaUsuarioLogin(String login) {

		Produtor produtor 						= new Produtor();
		PreparedStatement prepareStatement 		= null;
		Connection conn 						= null;
		ResultSet rs 							= null;
		
		try {
			
			StringBuilder sql = new StringBuilder()
			.append(" SELECT [nom_usuario],[end_email] FROM [Ged].[dbo].[TBS_Usuario] ")
			.append("where [Ged].[dbo].[TBS_Usuario].[end_email] like ? ");
			
			conn = execSQLSelect();
			prepareStatement = conn.prepareStatement(sql.toString());
			
			prepareStatement.setObject(1, login.concat("%"));
			
			rs = prepareStatement.executeQuery();
			
			if (rs.next()) {
				produtor.setNome(rs.getString("nom_usuario") != null ? rs.getString("nom_usuario") : "  ");
				produtor.setEmail(rs.getString("end_email") != null ? rs.getString("end_email") : "-");
			}
			rs.close();

		} catch (Exception e) {
			LOGGER.error("CotacaoAuto - " + e.getMessage(), e);
		} finally {			

			fechaConexao(rs, prepareStatement, conn);
		}

		return produtor;
	}

	/**
	 * @param unidade
	 *            : Define a unidade que sera consultada
	 * @param flag
	 *            : Se 2, consulta apenas a unidade enviada. Se 1, caso unidade seja
	 *            251 considera 251 and 271.
	 **/
	public static HashMap<String, Object> recuperaListaProdutor(String unidade, int flag) {
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		HashMap<String, Object> list = new HashMap<String, Object>();
		String query = "select distinct p.cod_produtor, p.produtor from " + "ged.dbo.TBS_Produtor as p "
				+ "inner join ged.dbo.TBS_Corr_Prod_Unid as u " + "on p.cod_produtor = u.cod_produtor ";
		if (!"0".equalsIgnoreCase(unidade)) {
			query = query + "where u.unid_negocio = " + unidade;
		}

		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);

			while (rs.next()) {
				list.put(rs.getString("cod_produtor"), rs.getString("produtor"));
			}
		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return list;
	}

	public static List<Produtor> recuperaListProdutor(String unidade, int flag) {

		List<Produtor> listProdutor = new ArrayList<Produtor>();

		HashMap<String, Object> map = recuperaListaProdutor(unidade, flag);
		Iterator it = map.keySet().iterator();
		Produtor produtor = null;

		while (it.hasNext()) {
			produtor = new Produtor();
			String key = (String) it.next();
			produtor.setCodigo(key);
			produtor.setNome(key + " - " + (String) map.get(key));
			listProdutor.add(produtor);
		}
		return listProdutor;
	}

	public static String recuperaDataTerminoVigenciaApolice(String numApolice) {

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;
		String datVigencia = "";

		//HashMap<String, Object> list = new HashMap<String, Object>();
		String query = "select DAT_TER_VIG from RamosDiversos.dbo.tab_ped where NUM_APOL = '" + numApolice
				+ "' and NUM_ENDO = 0";

		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);

			if (rs.next()) {
				datVigencia = rs.getString("DAT_TER_VIG");
			}

		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return datVigencia;
	}

	public static long[] verificaUnidade(String pLogin) {
		long[] vUnidade = new long[2];
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();

			String query = "Select Cod_Depto from ged.dbo.tab_user where login_user_rede = '" + pLogin + "'";

			rs = select.executeQuery(query);

			if (rs.next()) {
				vUnidade[1] = rs.getLong("Cod_Depto");
				/*
				 * if (rs.getLong("Cod_Depto") == 211 || rs.getLong("Cod_Depto") == 231 ||
				 * rs.getLong("Cod_Depto") == 251 || rs.getLong("Cod_Depto") == 271 ||
				 * rs.getLong("Cod_Depto") == 1571 || rs.getLong("Cod_Depto") == 1691 ||
				 * rs.getLong("Cod_Depto") == 1751 || rs.getLong("Cod_Depto") == 1811 ||
				 * rs.getLong("Cod_Depto") == 2531 || rs.getLong("Cod_Depto") == 2671 ||
				 * rs.getLong("Cod_Depto") == 3731 || rs.getLong("Cod_Depto") == 3732 ||
				 * rs.getLong("Cod_Depto") == 3791 || rs.getLong("Cod_Depto") == 4591 ||
				 * rs.getLong("Cod_Depto") == 4611 || rs.getLong("Cod_Depto") == 5551 ||
				 * rs.getLong("Cod_Depto") == 5771 || rs.getLong("Cod_Depto") == 5772 ||
				 * rs.getLong("Cod_Depto") == 6631 || rs.getLong("Cod_Depto") == 6651 ||
				 * rs.getLong("Cod_Depto") == 6711)
				 */
				if (rs.getLong("Cod_Depto") > 1000 || rs.getLong("Cod_Depto") < 900) {
					vUnidade[0] = rs.getLong("Cod_Depto");
				} else {
					vUnidade[0] = 0;
				}
			} else {
				vUnidade[0] = -1;
			}

		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

		return vUnidade;
	}

	public static HashMap<String, Object> verificaCep(int cep) {

		boolean ok = false;
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;
		HashMap<String, Object> dadosCep = new HashMap<String, Object>();

		String query = "select top 1 Tip_Logr, Nom_Logr, Nom_Bairro, Nom_Cid, Sig_UF, Nom_Compl "
				+ " from P0044800.dbo.T0044805 where num_CEP = " + cep;
		try {
			conn = getConn();

			select = conn.createStatement();
			rs = select.executeQuery(query);

			while (rs.next()) {
				ok = true;

				if (rs.getString("Tip_Logr") == null) {
					dadosCep.put("TipLogr", "");
				} else {
					dadosCep.put("TipLogr", rs.getString("Tip_Logr").trim());
				}

				if (rs.getString("Nom_Logr") == null) {
					dadosCep.put("NomLogr", "");
				} else {
					dadosCep.put("NomLogr", rs.getString("Nom_Logr").trim());
				}

				if (rs.getString("Nom_Bairro") == null) {
					dadosCep.put("NomBairro", "");
				} else {
					dadosCep.put("NomBairro", rs.getString("Nom_Bairro").trim());
				}

				if (rs.getString("Nom_Cid") == null) {
					dadosCep.put("NomCid", "");
				} else {
					dadosCep.put("NomCid", rs.getString("Nom_Cid").trim());
				}

				if (rs.getString("Sig_UF") == null) {
					dadosCep.put("SigUF", "");
				} else {
					dadosCep.put("SigUF", rs.getString("Sig_UF").trim());
				}

				if (rs.getString("Nom_Compl") == null) {
					dadosCep.put("NomCompl", "");
				} else {
					dadosCep.put("NomCompl", rs.getString("Nom_Compl").trim());
				}

			}
		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

		if (ok)
			dadosCep.put("status", "0"); // Localizou
		else
			dadosCep.put("status", "1"); // Nao Localizou

		return dadosCep;
	}

	public static HashMap<String, Object> recuperaListaCorretores(String numSusep, long codUnidade, String codCorr) throws SusepHistoricoException {
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		HashMap<String, Object> list = new LinkedHashMap<String, Object>();
		String query = "select c.cod_corretor, c.corretor, c.cid_corretor" + " from Ged.dbo.TBS_Corretor as c ";

		if (codUnidade != 0) {
			query = query + "inner join ged.dbo.TBS_Corr_Prod_Unid as u " + " on c.cod_corretor = u.cod_corretor ";
		}
		query = query + " where c.cod_ativo = 1 ";
		//PORTAL_CORRETOR
		if (StringUtils.isNotEmpty(numSusep) && "999999".equalsIgnoreCase(numSusep) == false) {
			//NOVA_SUSEP
//			query += " AND (NUM_SUSEP_COMPLETO = '" + numSusep + "'";
//			query += " OR NUM_SUSEP = '" + numSusep + "') ";

			String suseps = new SusepHistoricoService().obterSusepsPorSusepToQueryIN(numSusep);
			query += " AND (NUM_SUSEP_COMPLETO IN (" + suseps + ")";
			query += " OR NUM_SUSEP IN (" + suseps + ")) ";
		}

		if (codCorr != null && !"".equalsIgnoreCase(codCorr) && !"0".equalsIgnoreCase(codCorr)) {
			query += " and c.cod_corretor = " + codCorr + " ";
		}

		if (codUnidade != 0) {

			query = query + " and unid_negocio = " + codUnidade;

			// Ficha - 1300263
			query = query
					+ " or (c.cod_corretor in (select cod_corretor from GED.DBO.TBS_Corr_Prod_Unid_Excecao where unid_negocio = "
					+ codUnidade + "))";
		}
		query = query + " order by corretor ";

		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			while (rs.next()) {
				codCorretor = Util.zerosEsq(rs.getString("cod_corretor"), 07);
				list.put(Util.zerosEsq(rs.getString("cod_corretor"), 07),
						Util.zerosEsq(rs.getString("cod_corretor"), 07) + " " + rs.getString("corretor") + "/"
								+ rs.getString("cid_corretor"));
			}
		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return list;
	}

	public List<Corretor> recuperaListaCorretoresOrdenado(String numSusep, long codUnidade, String codCorr,
			String intranet) throws Exception {
		List<Corretor> corretores = new ArrayList<Corretor>();
		HashMap<String, Object> map = null;
		if ("1".equalsIgnoreCase(intranet)) {
			List<Corretor> tmp = DbAccess.ListaCorretores("", "", "999999", codUnidade, false);

			for (Corretor cor : tmp) {
				Corretor corretor = new Corretor();

				corretor.setCodCorretor(corretor.removeZerosCorretor(cor.getCodCorretor()));
				/*
				 * String [] nomeDividido = cor.getCorretor().split(" "); StringBuilder
				 * corretorFormatado = new StringBuilder(""); for(int i = 1;
				 * i<nomeDividido.length;i++){ corretorFormatado.append(nomeDividido[i] + " ");
				 * }
				 */
				corretor.setCorretor(cor.getCorretor());
				corretores.add(corretor);
			}
		} else {
			map = DbAccess.recuperaListaCorretores(numSusep, codUnidade, codCorr);

			Corretor corretor;
			String key;

			Iterator iMap = map.keySet().iterator();
			while (iMap.hasNext()) {
				corretor = new Corretor();
				key = (String) iMap.next();
				corretor.setCodCorretor(corretor.removeZerosCorretor(key));
				String corMap = (String) map.get(key);
				/*
				 * String [] arrayCorr = corMap.split(" "); StringBuilder corretorFormatado =
				 * new StringBuilder(""); for(int i = 1; i<arrayCorr.length;i++){
				 * corretorFormatado.append(arrayCorr[i] + " "); }
				 */
				corretor.setCorretor(corMap.trim());
				corretores.add(corretor);
			}

		}

		return corretores;
	}
	
	//PORTAL_CORRETOR
	//NOVA_SUSEP
//	public static String obterSegundaSusep(String susep, String codigoCorretor) {
//
//		String segundaSusep = "";
//
//		LOGGER.info("> INICIO obterSegundaSusep susep|codigoCorretor : " + susep + "|" + codigoCorretor);
//
//		Connection conn = null;
//		Statement select = null;
//		ResultSet rs = null;
//
//		try {
//			String query = "SELECT  NUM_SUSEP, NUM_SUSEP_COMPLETO " + " FROM GED.DBO.TBS_CORRETOR "
//					+ " WHERE (NUM_SUSEP ='" + susep + "' OR NUM_SUSEP_COMPLETO = '" + susep + "') "
//					+ " AND COD_ATIVO = 1 ";
//
//			if (StringUtils.isNotEmpty(codigoCorretor)) {
//				query += " AND COD_CORRETOR = " + codigoCorretor;
//			}
//
//			LOGGER.info("query: " + query);
//
//			conn = getConn();
//
//			select = conn.createStatement();
//			rs = select.executeQuery(query);
//
//			if (rs.next()) {
//				String numSusep = rs.getString("NUM_SUSEP");
//				String numSusepCompleto = rs.getString("NUM_SUSEP_COMPLETO");
//
//				LOGGER.info("numSusep|numSusepCompleto: " + numSusep + "|" + numSusepCompleto);
//
//				segundaSusep = StringUtils.equals(StringUtils.trim(susep), StringUtils.trim(numSusep))
//						? numSusepCompleto
//						: numSusep;
//			}
//
//		} catch (Exception e) {
//			LOGGER.error("erro ao realizar consulta: " + e.getMessage(), e);
//		} finally {
//			fechaConexao(rs, select, conn);
//		}
//
//		LOGGER.info("< FIM obterSegundaSusep segundaSusep: " + segundaSusep);
//		return segundaSusep;
//	}

	public static String[] verificarExiste(String numProtocolo, String numSusep, String codigoCorretor) {
		LOGGER.info("[DbAccess] - (verificarExiste) : Iniciando o metodo");
		String[] l = new String[4];

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			String query = "SELECT  A.CRAMO, B.modalidade,B.key_ext, A.DSYS "
					+ " FROM SISCOTA.SISCOTA.TAB_COTACAO A INNER JOIN SISCOTA.dbo.tab_cotacao_div B ON A.CCOTACAO = B.ccotacao "
					+ " WHERE A.CCOTACAO='" + numProtocolo + "'";
			//PORTAL_CORRETOR
			if (numSusep != null && numSusep.equalsIgnoreCase("999999") == false) {
				//NOVA_SUSEP
//				String segundaSusep = obterSegundaSusep(numSusep, codigoCorretor);
//				query += " AND (B.num_susep = '" + numSusep + "'";
//				query += " OR B.num_susep = '" + segundaSusep + "') ";

				String suseps = new SusepHistoricoService().obterSusepsPorSusepToQueryIN(numSusep);
				query += " AND (B.num_susep IN (" + suseps + ")";
				query += " OR B.num_susep IN (" + suseps + ")) ";
			}
			
			conn = getConn();

			select = conn.createStatement();
			LOGGER.info("[DbAccess] - (verificarExiste) : Executando a query: ".concat(query));
			rs = select.executeQuery(query);
			LOGGER.info("[DbAccess] - (verificarExiste) : Retornando da execucao da query:".concat(query));
			if (rs.next()) {
				LOGGER.info("[DbAccess] - (verificarExiste) : Retornando da execucao da query:".concat(query));
				l[0] = String.valueOf(rs.getInt("CRAMO"));
				if (rs.getString("modalidade") == null || rs.getString("modalidade").equals(""))
					l[1] = "01";
				else
					l[1] = rs.getString("modalidade").trim();

				l[2] = rs.getString("key_ext");
				l[3] = rs.getString("DSYS");
			}

		} catch (Exception e) {
			LOGGER.error("[DbAccess] - (verificarExiste) - cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		
		LOGGER.info("[DbAccess] - (verificarExiste) : Finalizando o metodo");
		return l;
	}

	//NOVA_SUSEP (throws)
	public static void recuperaDadosCotacao(CotacaoBean bean, long codUnidade) throws SusepHistoricoException {
		Hashtable<String, Object> d = new Hashtable<String, Object>();
		int sCotacao;
        bean.setCnpjCpf(CpfCnpjValidator.formataCpfCnpj(bean.getCnpjCpf()));

		String query = "SELECT A.DVIGINIC, B.LMI_UNICO, A.DVIGFINAL, D.PRODUTOR, C.PRAZO_UNIDADE, E.NOM_DEPTO, A.CPRODUTOR, A.CDEPARTAMENTO, A.CRAMO, A.CSEGURADO, A.ASEGURADO, "
				+ " A.CCORRETOR, A.ACORRETOR, A.RENOVACAO, A.SCOTACAO, C.STATUS_FLUXO, C.Status_andamento, A.OBSERVACAO, A.CUSER, A.TCOTACAO, "
				+ " A.CCOTACAOANT, A.VRISCO, A.APOLICE, A.NUM_CEP, A.NOM_LOGR, A.NOM_BAIRRO, B.NOM_PROFISSAO, B.RAMO_ATIV_EMPR,"
				+ " A.NOM_CID, A.SIG_UF, A.NOM_COMPL, A.OBSERVACAOCOT, A.VARIOS, A.NUM_END, A.CCOTACAOORIG, "
				+ " isnull(B.modalidade,'') as modalidade ,isnull(B.nom_contat,'') as nom_contat , isnull(B.email_contat,'') as email_contat , isnull(B.tel_contat,'') as tel_contat , isnull(B.key_ext,'') as key_ext, "
				+ " isnull(B.dados01,'') as dados01 ,isnull(B.dados02,'') as dados02 ,isnull(B.dados03,'') as dados03, isnull(B.dados04, '') as dados04, isnull(B.dados05,'') as dados05 , isnull(B.dados06,'') as dados06, "
				+ " isnull(B.dados07,'') as dados07 ,isnull(B.dados08,'') as dados08 ,isnull(B.dados09,'') as dados09, isnull(B.dados10,'') as dados10, isnull(B.dados11,'') as dados11, isnull(B.dados12,'') as dados12, "
				+ " isnull(B.dados13,'') as dados13, isnull(B.dados14,'') as dados14 ,isnull(B.dados15,'') as dados15, isnull(B.NOMSEGURADO ,'') as NOMSEGURADO,"
				+ " a.algoritmo, A.dcontrato, A.dsys, a.cMotivo,a.tipomotivo , a.tecnico , isnull(b.user_anuencia,'') as user_anuencia  , a.vdesconto,"
				+ " A.PMargem,A.PComissao,A.PTaxa,A.Vpremio_Total, A.ALTOPOTRISCO,A.DECLINADO,A.RECUSADOIRB, "
				+ " A.condinspec, a.lmi,a.facultativo,a.especiais,a.aprovadoirb,a.inspecao,a.cobertura,"
				+ " a.clausulabenef,a.beneficiario,a.periodoind,a.meses,a.especificacao, a.info_tecnica, a.NUMDOCTOSEG, B.REGRA_ALCADA, PARAM_B2B.COD_HIERARQUICO, PARAM_B2B.COD_FILIAL "
				+ " FROM SISCOTA.SISCOTA.TAB_COTACAO A (nolock)  left JOIN SISCOTA.dbo.tab_cotacao_div B (nolock)  ON A.CCOTACAO = B.ccotacao "
				+ " left JOIN GED.DBO.TAB_CTRL_SISCOTA AS C (nolock)  ON A.CCOTACAO = c.ccotacao "
				+ " left JOIN GED.dbo.TBS_Produtor D (nolock)  ON A.CPRODUTOR = D.COD_PRODUTOR "
				+ " left JOIN SISCOTA.dbo.TAB_PARAM_B2B PARAM_B2B (nolock) ON A.CCOTACAO = PARAM_B2B.CCOTACAO "
				+ " left JOIN GED.dbo.tbs_depto E (nolock) ON A.CDEPARTAMENTO = E.COD_DEPTO " + " WHERE A.CCOTACAO='"
				+ Util.trataTexto(bean.getNumProtocolo()) + "'";
		
		//PORTAL_CORRETOR
		if (bean != null && bean.getNumSusep() != null && "999999".equalsIgnoreCase(bean.getNumSusep()) == false) {
			//NOVA_SUSEP
//			String segundaSusep = obterSegundaSusep(bean.getNumSusep(), bean.getCodCorr());
//			if (StringUtils.isEmpty(segundaSusep)) {
//				query += " AND B.num_susep = '" + bean.getNumSusep() + "' ";
//			} else {
//				query += " AND (B.num_susep = '" + bean.getNumSusep() + "'";
//				query += " OR B.num_susep = '" + segundaSusep + "') ";
//			}
			
			String suseps = new SusepHistoricoService().obterSusepsPorSusepToQueryIN(bean.getNumSusep());
			query += " AND (B.num_susep IN (" + suseps + ")";
			query += " OR B.num_susep IN (" + suseps + ")) ";
		}

		// Hebert
		if (codUnidade != 0) {
			query = query + " and a.cdepartamento = " + codUnidade;
		}
		// Hebert
		
		//CARVOUT
		query += " and C.tipo_fluxo = " + TipoFluxo.VALOR.getValor();

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;
		try {
			conn = getConn();

			select = conn.createStatement();
			rs = select.executeQuery(query);
			if (rs.next()) {
				// A.DVIGINIC
				bean.setDataVigenciaInicio(rs.getString("DVIGINIC").trim());
				// A.DVIGFINAL
				bean.setDataVigenciaFim(rs.getString("DVIGFINAL").trim());
				// D.PRODUTOR
				bean.setNomProdutor(rs.getString("PRODUTOR").trim());
				//Date prazoUnidade = null;
				//prazoUnidade = rs.getDate("prazo_unidade");
				// E.NOM_DEPTO
				bean.setNomDepto(rs.getString("NOM_DEPTO").trim());
				// B.LMI_UNICO
				bean.setLmiUnico(rs.getInt("LMI_UNICO"));
				// A.CPRODUTOR
				bean.setCodProdutor(rs.getString("CPRODUTOR").trim());
				// ,A.CDEPARTAMENTO
				bean.setCodDepto(rs.getString("CDEPARTAMENTO").trim());
				// A.CRAMO
				bean.setCodRamo(rs.getInt("CRAMO"));
				// A.CSEGURADO
				bean.setCodSegurado(rs.getString("CSEGURADO").trim());
				// ,A.ASEGURADO
				String asegurado = "";
				asegurado = rs.getString("ASegurado").trim().toUpperCase();
				bean.setNomeProp(asegurado);

				// A.CCORRETOR
				bean.setCodCorr(rs.getString("CCORRETOR").trim());
				// A.ACORRETOR
				String aCorretor = rs.getString("ACORRETOR");
				bean.setNomCorr(aCorretor != null ? aCorretor.trim() : "");
				// ,A.RENOVACAO
				bean.setTipEmissao(rs.getString("RENOVACAO").trim());
				// A.SCOTACAO
				sCotacao = rs.getInt("SCOTACAO");

				bean.setCodClasseAceitacao(sCotacao);
				//int statusFluxo = 0;
				//int statusAndamento = 0;

				//statusFluxo = rs.getInt("STATUS_FLUXO");
				//statusAndamento = rs.getInt("Status_andamento");

				// A.OBSERVACAO
				bean.setComentario(rs.getString("OBSERVACAO").trim());
				// A.CUSER
				bean.setCodUser(rs.getString("CUSER").trim());
				// A.TCOTACAO,"
				bean.setTpCotacao(rs.getString("TCOTACAO").trim());
				// A.CCOTACAOANT
				bean.setNumCotacaoAnt(rs.getString("CCOTACAOANT").trim());
				// A.VRISCO
				bean.setVlRisco(rs.getDouble("VRISCO"));
				// A.APOLICE
				bean.setNumApol(rs.getString("APOLICE").trim());
				// A.NUM_CEP
				bean.setCep(rs.getString("NUM_CEP").trim());
				// A.NOM_LOGR
				bean.setEnd(rs.getString("NOM_LOGR").trim());
				// A.NOM_BAIRRO," +
				bean.setBairro(rs.getString("NOM_BAIRRO").trim());
				// A.NOM_CID
				bean.setCid(rs.getString("NOM_CID").trim());
				// A.SIG_UF
				bean.setUf(rs.getString("SIG_UF").trim());
				// A.NOM_COMPL
				bean.setComplemento(rs.getString("NOM_COMPL").trim());
				// A.OBSERVACAOCOT
				bean.setObs(rs.getString("OBSERVACAOCOT").trim());
				// A.VARIOS
				bean.setQtdLocRisc(rs.getString("VARIOS").trim());
				// A.NUM_END
				bean.setNumero(rs.getString("NUM_END").trim());
				// A.CCOTACAOORIG, " +
				bean.setCotacaoOrigem(rs.getString("CCOTACAOORIG"));

				// B.modalidade
				bean.setCodModalidade(rs.getString("modalidade"));

				// B.nom_contat
				bean.setNomeContato(rs.getString("nom_contat"));

				// B.email_contat
				bean.setEmailContato(rs.getString("email_contat"));

				// ,B.tel_contat,B.key_ext, " +
				bean.setTelefContato(rs.getString("tel_contat"));

				bean.setKeyext(rs.getString("key_ext"));
				// B.dados01,B.dados02,B.dados03,B.dados04,B.dados05,B.dados06,
				// B.dados07,B.dados08,B.dados09,B.dados10,B.dados11,B.dados12,
				// B.dados13,B.dados14,B.dados15
				d.put("dados01", rs.getString("dados01"));

				d.put("dados02", rs.getString("dados02"));

				d.put("dados03", rs.getString("dados03"));

				d.put("dados04", rs.getString("dados04"));

				d.put("dados05", rs.getString("dados05"));

				d.put("dados06", rs.getString("dados06"));

				d.put("dados07", rs.getString("dados07"));
				d.put("dados08", rs.getString("dados08"));

				d.put("dados09", rs.getString("dados09"));

				d.put("dados10", rs.getString("dados10"));

				d.put("dados11", rs.getString("dados11"));
				d.put("dados12", rs.getString("dados12"));

				d.put("dados13", rs.getString("dados13"));
				d.put("dados14", rs.getString("dados14"));

				d.put("dados15", rs.getString("dados15"));

				bean.setDBtoBean(d); // grava no bean do diversos

				// B.NOMSEGURADO ," +
				String NomAux = rs.getString("NOMSEGURADO").trim();
				if (!"".equalsIgnoreCase(NomAux)) {
					bean.setNomeProp(NomAux);
				}
				bean.setNomAbrSegurado(NomAux);

				// a.algoritmo
				String algoritmo = rs.getString("algoritmo");
				// , A.dcontrato , A.dsys
				// bean.setData_Cotacao((new
				// SimpleDateFormat("yyyyMMdd")).parse(rs.getString("dcontrato")));
				bean.setData_Cotacao((new SimpleDateFormat("yyyyMMdd")).parse(rs.getString("dsys")));

				// a.cMotivo
				bean.setCodMotivo(rs.getInt("cMotivo"));
				if (bean.getCodMotivo() == 5) {
					bean.setNomMotivo(rs.getString("tipomotivo"));
				} else {
					Hashtable<String, Object> hash = recuperaListaMotivo();
					bean.setNomMotivo((String) hash.get(Integer.toString(bean.getCodMotivo())));
				}
				// a.tecnico
				bean.setTecnico(rs.getString("tecnico"));
				// b.user_anuencia
				bean.setUserAnuencia(rs.getString("user_anuencia"));
				// a.vdesconto " +
				bean.setDesconto(rs.getDouble("vdesconto"));
				bean.setPmargem(rs.getDouble("PMargem"));
				bean.setPcomissao(rs.getDouble("PComissao"));
				bean.setPtaxa(rs.getDouble("PTaxa"));
				bean.setVpremiototal(rs.getDouble("Vpremio_Total"));
				bean.setAltopotrisco(rs.getInt("ALTOPOTRISCO"));
				bean.setDeclinado(rs.getInt("DECLINADO"));
				bean.setRecusadoirb(rs.getInt("RECUSADOIRB"));

				bean.setCondinspec(rs.getInt("condinspec"));

				bean.setLmi(rs.getInt("lmi"));
				bean.setFacultativo(rs.getInt("facultativo"));
				bean.setEspeciais(rs.getInt("especiais"));
				bean.setAprovairb(rs.getInt("aprovadoirb"));
				bean.setInspecao(rs.getInt("inspecao"));
				bean.setCobertura(rs.getInt("cobertura"));
				bean.setClausulabenef(rs.getInt("clausulabenef"));
				bean.setBeneficiario(rs.getString("beneficiario"));
				bean.setPeriodo(rs.getInt("periodoind"));
				bean.setMeses(rs.getInt("meses"));
				bean.setEspecificacao(rs.getInt("especificacao"));

				bean.setInfoTecnica(rs.getString("info_tecnica"));

				bean.setRefSyas(algoritmo);
				bean.setTipPessoa("");
				bean.setCnpjCpf("");
				bean.setCnpjCpf(rs.getString("NUMDOCTOSEG"));
				if (bean.getCnpjCpf().length() <= 11) {
					if (bean.getCnpjCpf().length() > 1) {
						bean.setTipPessoa(String.valueOf(PESSOA_FISICA));
					}
				} else
					bean.setTipPessoa(String.valueOf(PESSOA_JURIDICA));
				// B.REGRA_ALCADA
				String regraAlcadaAux = rs.getString("REGRA_ALCADA");
				if (regraAlcadaAux != "" && regraAlcadaAux != null) {
					bean.setRegraAlcada(regraAlcadaAux);
				}
				bean.setRamoAtividadeProfissaoBanco(rs.getString("NOM_PROFISSAO"), rs.getString("RAMO_ATIV_EMPR"));

				// PARAMETROS B2B
				if( !StringUtils.equalsIgnoreCase( rs.getString("COD_HIERARQUICO"), "null" ) ) {
					bean.setCodigoHierarquico( StringUtils.trimToNull( rs.getString("COD_HIERARQUICO") ) );
				}
				if( !StringUtils.equalsIgnoreCase( rs.getString("COD_FILIAL"), "null" ) ) {
					bean.setCodigoFilial( StringUtils.trimToNull( rs.getString("COD_FILIAL") ) );
				}
			}
		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

		/* TODO - Ricardo ajustar consulta */
		String retorno = "";
		retorno = recuperaSituacaoAtual(bean.getNumProtocolo());
		bean.setCodSituacao("");
		bean.setcotFinalData(retorno);

	}

	public static String recuperaNovoCSegurado() {
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		String csegurado = null;
		try {
			conn = getConn();

			select = conn.createStatement();
			String query = " select max(CSEGURADO) from SISCOTA.SISCOTA.TAB_SEGURADO ";

			rs = select.executeQuery(query);
			if (rs.next()) {
				csegurado = rs.getString(1);
			}

		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

		DecimalFormat df = new DecimalFormat("10000000");
		if (csegurado != null) {
			int val = Integer.parseInt(csegurado.substring(1)) + 1;
			return df.format(val);
		}
		return df.format(1);
	}

	public static String recuperaUnidadeCotacao(String cCotacao) {
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		String depto = null;
		try {
			conn = getConn();

			select = conn.createStatement();
			String query = "select CDEPARTAMENTO from SISCOTA.SISCOTA.TAB_COTACAO where CCOTACAO = " + cCotacao;

			rs = select.executeQuery(query);
			if (rs.next()) {
				depto = rs.getString("CDEPARTAMENTO");
				depto = depto.trim();
			}

		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

		return depto;
	}

	public static void recuperaSegurado(CotacaoBean bean) {

		int tipoPessoa = Integer.parseInt(bean.getTipPessoa());
		String cpfCnpj = CpfCnpjValidator.formataCpfCnpj(bean.getCnpjCpf());

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();

			select = conn.createStatement();
			String query = "SELECT CSEGURADO, ASEGURADO, SEGURADO FROM SISCOTA.SISCOTA.TAB_SEGURADO";
			if (tipoPessoa == PESSOA_FISICA)
				query += " WHERE CPF='" + cpfCnpj + "'";
			else
				query += " WHERE CGC='" + cpfCnpj + "'";

			rs = select.executeQuery(query);
			if (rs.next()) {
				bean.setCodSegurado(rs.getString("CSEGURADO"));
				bean.setNomAbrSegurado(rs.getString("ASEGURADO").toUpperCase());
			}
		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

	}

	public static void recuperaDadosSegurado(CotacaoBean bean) {

		String query = "SELECT SEGURADO, XPESSOA, CGC, CPF" + " FROM SISCOTA.SISCOTA.TAB_SEGURADO WHERE CSEGURADO = "
				+ bean.getCodSegurado();

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();

			select = conn.createStatement();
			rs = select.executeQuery(query);
			if (rs.next()) {
				if ("".equalsIgnoreCase(bean.getNomeProp())) {
					bean.setNomeProp(rs.getString("SEGURADO").toUpperCase());
				}
				String tmp = rs.getString("XPESSOA").trim();
				if ("F".equalsIgnoreCase(tmp)) {
					bean.setTipPessoa(String.valueOf(PESSOA_FISICA));
					bean.setCnpjCpf(rs.getString("CPF").trim());
				} else if ("J".equalsIgnoreCase(tmp)) {
					bean.setTipPessoa(String.valueOf(PESSOA_JURIDICA));
					bean.setCnpjCpf(rs.getString("CGC").trim());
				}
			}

		} catch (Exception e) {
			LOGGER.error("cotacaoMulti codSegurado -> " + bean.getCodSegurado() + " - erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
	}

	private static String geraASegurado(String nome) {
		StringTokenizer tk = new StringTokenizer(nome);
		nome = "";
		while (tk.hasMoreTokens())
			nome += tk.nextToken();
		nome = Util.trataTexto(nome);
		if (nome.length() > 15)
			nome = nome.substring(0, 15);
		String query = "SELECT count(ASEGURADO) FROM SISCOTA.SISCOTA.TAB_SEGURADO";
		String where = " where ASEGURADO='" + nome + "'";
		/*
		 * int i = 1;
		 * 
		 * while (execSQLCount(query + where, SISCOTA_USER_PROD, SISCOTA_PASSWORD_PROD)
		 * > 0) { if (i < 10) { if (nome.length() < 15) nome = nome.concat("" + i); else
		 * if (nome.length() == 15) nome = nome.substring(0, 14) + i; } else { if
		 * (nome.length() < 14) nome = nome.concat("" + i); else if (nome.length() ==
		 * 15) nome = nome.substring(0, 13) + i; } where = " where ASEGURADO='" + nome +
		 * "'"; i++; }
		 */
		try {
			if (execSQLCount(query + where) > 0) {
				for (int i = 1; i < 1000; i++) {
					if (i < 10) {
						if (nome.length() < 15)
							nome = nome.concat("" + i);
						else if (nome.length() == 15)
							nome = nome.substring(0, 14) + i;
					} else if (i < 100) {
						if (nome.length() < 14)
							nome = nome.concat("" + i);
						else if (nome.length() == 15)
							nome = nome.substring(0, 13) + i;
					} else {
						if (nome.length() < 13)
							nome = nome.concat("" + i);
						else if (nome.length() <= 15)
							nome = nome.substring(0, 12) + i;
					}
					where = " where ASEGURADO='" + nome + "'";
					int count = execSQLCount(query + where);
					if (count == 0)
						i = 1000;
				}
			}
		} catch (Exception e) {
			LOGGER.error("cotacaoMulti erro: " + e.getMessage(), e);
		}

		return nome;
	}

	public void salvaTabSegurado(CotacaoBean bean) throws SQLException {
		int tipoPessoa = Integer.parseInt(bean.getTipPessoa());
		String cpfCnpj = CpfCnpjValidator.formataCpfCnpj(bean.getCnpjCpf());
		String query = "SELECT count(ASEGURADO) FROM SISCOTA.SISCOTA.TAB_SEGURADO";
		if (tipoPessoa == PESSOA_FISICA) {
			query += " where CPF='" + cpfCnpj + "'";
		} else {
			query += " where CGC='" + cpfCnpj + "'";
		}
		if (execSQLCountACID(query) == 0) {
			String aSegurado = geraASegurado(bean.getNomeProp().toUpperCase());
			bean.setNomAbrSegurado(aSegurado.toUpperCase());
			// String aSegurado=bean.getNomAbrSegurado();
			String cSegurado = recuperaNovoCSegurado();
			bean.setCodSegurado(cSegurado);
			String insertQuery = "INSERT INTO SISCOTA.SISCOTA.TAB_SEGURADO"
					+ " (CSEGURADO, SEGURADO, ASEGURADO, XPESSOA, CGC, CPF, CUSER,"
					+ " DSYS, DATUAL, SREG, ABREV_SISCOTA, HSYS)" + " VALUES(" + cSegurado + "," + "'"
					+ Util.trataTexto(bean.getNomeProp().toUpperCase()) + "'," + "'"
					+ Util.trataTexto(aSegurado.toUpperCase()) + "'," + "'"
					+ ((tipoPessoa == PESSOA_JURIDICA) ? "J" : "F") + "'," + "'"
					+ ((tipoPessoa == PESSOA_JURIDICA) ? cpfCnpj : "") + "'," + "'"
					+ ((tipoPessoa == PESSOA_FISICA) ? cpfCnpj : "") + "'," + "'"
					+ (bean.getCodUser().length() > 10 ? bean.getCodUser().substring(0, 10) : bean.getCodUser()) + "',"
					+ Util.dataAtual() + "," + "0," + "'A'," + "'M'," + Util.horaAtual() + ")";
			execSQLInsertACID(insertQuery);
		} else {
			recuperaSegurado(bean);
		}
	}
	

	/**
	 * Retorna o proximo numero de cotacao Esta funcao usa procedure com output
	 * 
	 * @return
	 */
	private static String recuperaProximaCotacao() {

		String ccotacao = null;
		Connection conn = null;
		CallableStatement cs = null;

		String query = "EXEC SISCOTA.dbo.proc_GetNextCotacao ? ";
		try {
			conn = getConn();
			cs = conn.prepareCall(query);
			cs.registerOutParameter(1, Types.VARCHAR);
			cs.execute();
			ccotacao = cs.getString(1);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				cs.close();
			} catch (Exception ein) {
				LOGGER.error("CotacaoMulti erro (recuperaProximaCotacao): " + query + " " + ein.getMessage(), ein);
			}
			fechaConexao(null, null, conn);
		}

		return (ccotacao);

	}

	
	
	
	public String salvaTabCotacao(CotacaoBean bean) throws SQLException {
		// recuperaProdutorUnidade(bean);

		String cCotacao = recuperaProximaCotacao();
		PreparedStatement prepareStatement = null;
		
		StringBuilder sql = new StringBuilder()
		.append("INSERT INTO SISCOTA.SISCOTA.TAB_COTACAO ")
		.append(" (CCOTACAO, CPRODUTOR, CDEPARTAMENTO, CRAMO, CSEGURADO, ASEGURADO, CCORRETOR,")
		.append(" ACORRETOR, RENOVACAO, DCONTRATO, DRECEPCAO, DRETORNO_COMERCIAL, DRETORNO,")
		.append(" SCOTACAO, SFUXO, DASSINATURA, DREJEICAO, DPERDA, DCANCEL,RESPONSAVEL,OBSERVACAO,")
		.append(" VPREMIO_TOTAL, VPREMIO_COMERCIAL, PCOMISSAO, PMARGEM, PTAXA, VDESCONTO,")
		.append(" CMOEDA, CUSER, DSYS, HSYS, DATUAL, SREG, TCOTACAO, CCOTACAOANT,")
		.append(" CMOTIVO,DVIGINIC,DVIGFINAL,VRISCO,APOLICE,CONDINSPEC,COBERTURA,ALTOPOTRISCO,")
		.append(" DECLINADO,RECUSADOIRB,LMI,FACULTATIVO,APROVADOIRB,INSPECAO,CLAUSULABENEF, ")
		.append(" BENEFICIARIO,PERIODOIND,MESES,NUM_CEP,NOM_LOGR,NOM_BAIRRO,NOM_CID,SIG_UF, ")
		.append(" TIP_LOGR,NOM_COMPL,OBSERVACAOCOT,ALGORITMO,TIPOMOTIVO,VARIOS,NUM_END,NUMDOCTOSEG, ")
		.append(" DCONCLUSAO,ESPECIAIS,PMARGEM_RENEG,APOL_EMIT,NUM_PI,ESPECIFICACAO,CCOTACAOORIG,COD_CLIENTE)")
		.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ")
		.append(" ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		
		stACIDList.add(connACID.prepareStatement(sql.toString()));
		prepareStatement 					= (PreparedStatement) stACIDList.get(stACIDList.size()-1);
		
		prepareStatement.setObject(1, cCotacao);
		prepareStatement.setObject(2, bean.getCodProdutor());
		prepareStatement.setObject(3, bean.getCodDepto());
		prepareStatement.setObject(4, bean.getCodRamo());
		prepareStatement.setObject(5, bean.getCodSegurado());
		prepareStatement.setObject(6, Util.trataTexto((bean.getNomeProp() + "               ").substring(0, 15).toUpperCase()));
		LOGGER.error("salvaTabCotacao - SISCOTA.SISCOTA.TAB_COTACAO ASEGURADO "+Util.trataTexto((bean.getNomeProp() + "               ").substring(0, 15).toUpperCase()));
		prepareStatement.setObject(7, Util.zerosEsq(bean.getCodCorr(), 10));
		prepareStatement.setObject(8, Util.limitaTexto(bean.getNomCorr(), 15));
		LOGGER.error("salvaTabCotacao - SISCOTA.SISCOTA.TAB_COTACAO ACORRETOR "+Util.limitaTexto(bean.getNomCorr(), 15));
		prepareStatement.setObject(9, bean.getTipEmissao());
		prepareStatement.setObject(10, Util.dataAtual());
		prepareStatement.setObject(11, 0);
		prepareStatement.setObject(12, 0);
		prepareStatement.setObject(13, 0);
		prepareStatement.setObject(14, 13);
		prepareStatement.setObject(15, "S");
		prepareStatement.setObject(16, 0);
		prepareStatement.setObject(17, 0);
		prepareStatement.setObject(18, 0);
		prepareStatement.setObject(19, 0);
		prepareStatement.setObject(20, "YASUDANET");
		prepareStatement.setObject(21, Util.trataTexto(bean.getComentario()));
		prepareStatement.setObject(22, 0);
		prepareStatement.setObject(23, 0);
		prepareStatement.setObject(24, 0);
		prepareStatement.setObject(25, 0);
		prepareStatement.setObject(26, 0);
		prepareStatement.setObject(27, 0);
		prepareStatement.setObject(28, "00");
		prepareStatement.setObject(29, (bean.getCodUser().length() > 10 ? bean.getCodUser().substring(0, 10) : bean.getCodUser()));
		prepareStatement.setObject(30, Util.dataAtual());
		prepareStatement.setObject(31, Util.horaAtual());
		prepareStatement.setObject(32, 0);
		prepareStatement.setObject(33, "A");
		prepareStatement.setObject(34, (bean.getTpCotacao().equals(COTACAO) ? "1" : "2"));
		prepareStatement.setObject(35, (bean.getTpCotacao().equals(RECOTACAO) ? bean.getNumProtocolo() : ""));
		prepareStatement.setObject(36, bean.getCodMotivo());
		prepareStatement.setObject(37, bean.getDataVigenciaInicio());
		prepareStatement.setObject(38, bean.getDataVigenciaFim());
		prepareStatement.setObject(39, bean.getVlRisco());
		prepareStatement.setObject(40, (bean.getNumApol() == null || bean.getNumApol().equalsIgnoreCase("") ? "0" : bean.getNumApol()));
		prepareStatement.setObject(41, 0);
		prepareStatement.setObject(42, 0);
		prepareStatement.setObject(43, 0);
		prepareStatement.setObject(44, 0);
		prepareStatement.setObject(45, 0);
		prepareStatement.setObject(46, 0);
		prepareStatement.setObject(47, 0);
		prepareStatement.setObject(48, 0);
		prepareStatement.setObject(49, 0);
		prepareStatement.setObject(50, 0);
		prepareStatement.setObject(51, " ");
		prepareStatement.setObject(52, 0);
		prepareStatement.setObject(53, 0);
		prepareStatement.setObject(54, Util.limitaTexto(bean.getCep(), 8));
		prepareStatement.setObject(55, Util.limitaTexto(Util.trataTexto(bean.getEnd()), 50));
		prepareStatement.setObject(56, Util.limitaTexto(bean.getBairro(), 30));
		prepareStatement.setObject(57, Util.limitaTexto(bean.getCid(), 30));
		prepareStatement.setObject(58, Util.limitaTexto(bean.getUf(), 2));
		prepareStatement.setObject(59, " ");
		prepareStatement.setObject(60, Util.limitaTexto(Util.trataTexto((String) bean.getComplemento()), 20));
		prepareStatement.setObject(61, " ");
		prepareStatement.setObject(62, " ");
		prepareStatement.setObject(63, bean.getNomMotivo());
		prepareStatement.setObject(64, bean.getQtdLocRisc());
		prepareStatement.setObject(65, Util.limitaTexto(Util.trataTexto((String) bean.getNumero()), 10));
		prepareStatement.setObject(66, CpfCnpjValidator.formataCpfCnpj(bean.getCnpjCpf()));
		prepareStatement.setObject(67, 0);
		prepareStatement.setObject(68, 0);
		prepareStatement.setObject(69, 0);
		prepareStatement.setObject(70, 0);
		prepareStatement.setObject(71, 0);
		prepareStatement.setObject(72, 0);
		prepareStatement.setObject(73, (bean.getTpCotacao().equals(COTACAO) ? cCotacao : bean.getCotacaoOrigem()));
		prepareStatement.setObject(74, bean.getCodCliente());


		if (prepareStatement.executeUpdate() == 1) {
			return cCotacao;
		}
		return null;
	}

	public boolean isPermissaoCorretor(CotacaoBean cot, String aplicativo) throws SusepHistoricoException {

		boolean ret = false;

		//PORTAL_CORRETOR
		String query = "select cod_corretor from yasudanet.dbo.Tab_Ctrl_Acesso_Aplicativos_Portal ";
		query += " where cod_corretor = " + cot.getCodCorr();
		query += " AND aplicativo = '" + aplicativo + "'";
		
		//NOVA_SUSEP
//		String segundaSusep = obterSegundaSusep(cot.getNumSusep(), cot.getCodCorr());
//		if (StringUtils.isEmpty(segundaSusep)) {
//			query += " AND (num_susep = '" + Long.valueOf(cot.getNumSusep()) + "'";
//			query += " OR num_susep = '" + cot.getNumSusep() + "') ";
//		} else {
//			query += " AND (num_susep = '" + Long.valueOf(cot.getNumSusep()) + "'";
//			query += " OR num_susep = '" + cot.getNumSusep() + "'";
//			query += " OR num_susep = '" + segundaSusep + "'";
//			query += " OR num_susep = '" + Long.valueOf(segundaSusep) + "') ";
//		}

		SusepHistoricoService susepService = new SusepHistoricoService();
		String suseps = susepService.obterSusepsPorSusepToQueryIN(cot.getNumSusep());
		String susepsAsLong = susepService.getSusepsFormatAsLong(suseps);
		LOGGER.info("isPermissaoCorretor -> suseps: " + suseps);
		if(StringUtils.isEmpty(suseps)) {
			query += " AND (num_susep = '" + Long.valueOf(cot.getNumSusep()) + "'";
			query += " OR num_susep = '" + cot.getNumSusep() + "') ";
		} else {
			query += " AND (num_susep IN (" + suseps + ") ";
			query += " OR num_susep IN (" + susepsAsLong + ")) ";
		}

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConnYasudanetAWS();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			if (rs.next()) {
				ret = true;
			}

		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

		return ret;
	}

	public int salvaTabCtrlSiscota(CotacaoBean bean) throws SQLException {
		int ret = 0;

		String insertQuery = "INSERT INTO Ged.dbo.tab_ctrl_siscota"
				+ " (CCOTACAO, status_fluxo, status_andamento, cod_ramo, membro_unid,tipo_fluxo)" + " VALUES(" + "'"
				+ bean.getNumProtocolo() + "', " + "0, " + "0, " + bean.getCodRamo() + ", " + "'" + bean.getCodUser()
				+ "'," + TipoFluxo.VALOR.getValor() +
				// Para o Siscota Diversos
				")";

		if (execSQLInsertACID(insertQuery) == 1)
			return ret;
		else
			return 1;

	}

	public int salvaTabCotacaoDiv(Hashtable<String, Object> d) throws SQLException {

		int ret = 0;

		String insertQuery = "INSERT INTO SISCOTA.dbo.tab_cotacao_div "
				+ " (CCOTACAO,num_susep,cod_corretor,cod_ramo, modalidade,key_ext, nom_contat,email_contat,tel_contat, user_alt, dat_alt,"
				+ "  dados01,dados02,dados03,dados04,dados05,dados06,dados07,dados08,dados09,dados10,"
				+ "  dados11,dados12,dados13,dados14,dados15,NOMSEGURADO, LMI_UNICO, NOM_PROFISSAO, RAMO_ATIV_EMPR) "
				+ " VALUES(" + "'" + d.get("numProtocolo") + "', " + "'" + d.get("numSusep") + "', " + "'"
				+ d.get("codCorretor") + "', " + "'" + d.get("codRamo") + "', " + "'" + d.get("modalidade") + "', "
				+ "'" + d.get("keyExt") + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("nomContat")), 30) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("emailContat")), 50) + "', " + "'"
				+ Util.trataTexto((String) d.get("telContat")) + "', " + "'"
				+ (((String) d.get("userAlt")).length() > 10 ? ((String) d.get("userAlt")).substring(0, 10)
						: d.get("userAlt"))
				+ "', " + "'" + d.get("datAlt") + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados01")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados02")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados03")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados04")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados05")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados06")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados07")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados08")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados09")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados10")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados11")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados12")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados13")), 60) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados14")), 500) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("dados15")), 500) + "', " + "'"
				+ Util.limitaTexto(Util.trataTexto((String) d.get("NOMSEGURADO")), 60) + "'," + d.get("LMIUNICO") + ","
				+ d.get("profissao") + "," + d.get("ramoAtividade") + ")";

		if (execSQLInsertACID(insertQuery) == 1) {
			String updateSql = "";
			//PORTAL_CORRETOR
			//NOVA_SUSEP
//			if(d.get("numSusep").toString().length() <= 8) {
//				updateSql = " update siscota.dbo.tab_cotacao_div set num_susep = c.num_susep "
//						+ " from siscota.dbo.tab_cotacao_div as a inner join ged.dbo.tbs_corretor as c "
//						+ " on cast(a.cod_corretor AS int) = cast(c.cod_corretor AS int) where a.num_susep = '999999' ";
//			} else {
				updateSql = " update siscota.dbo.tab_cotacao_div set num_susep = c.num_susep_completo "
						+ " from siscota.dbo.tab_cotacao_div as a inner join ged.dbo.tbs_corretor as c "
						+ " on cast(a.cod_corretor AS int) = cast(c.cod_corretor AS int) where a.num_susep = '999999' ";
//			}
			execSQLInsertACID(updateSql);
			return ret;
		} else {
			return 1;
		}
	}
	
		
	/**
	 * Executa um statement SQL count(*)
	 * 
	 * @param query
	 *            Query com statement count.
	 * @return resultado do count(*)
	 */
	private static int execSQLCount(String query) {
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		int ret = -1;
		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			if (rs.next()) {
				ret = rs.getInt(1);
			}

		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return ret;
	}
	
	private int execSQLCountACID(String query) throws SQLException {
		ResultSet rs = null;

		int ret = -1;
		stACIDList.add(connACID.createStatement());
		rs = stACIDList.get(stACIDList.size()-1).executeQuery(query);		
		if (rs.next()) {
			ret = rs.getInt(1);
		}

		return ret;
	}	


	private int execSQLInsertACID(String query) throws SQLException {
		int ret = -1;

		stACIDList.add(connACID.createStatement());
		ret = stACIDList.get(stACIDList.size()-1).executeUpdate(query);	
		return ret;
	}
	
	/**
	 * Executa um statement SQL INSERT/UPDADE
	 * 
	 * @param query
	 *            Query com statement INSERT/UPDADE.
	 * @return quantidade de linhas inseridas/atualizadas
	 */
	private static int execSQLInsert(String query) {
		Connection conn = null;
		Statement select = null;

		int ret = -1;

		try {
			conn = getConn();
			select = conn.createStatement();
			ret = select.executeUpdate(query);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			fechaConexao(null, select, conn);
		}

		return ret;
	}
	
	private static Connection execSQLInsert() {
		Connection conn = null;

		try {
			conn = getConn();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return conn;
	}
	
	private static Connection execSQLSelect() {
		Connection conn = null;

		try {
			conn = getConn();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		return conn;
	}
	

	public static final int TAB_COTACAO = 0;
	public static final int TAB_CTRL_SISCOTA = 1;
	public static final int TAB_COTACAO_DIV = 2;
	public static final int TAB_CTRL_PASSO_SISCOTA = 3;

	public static int updateTabela(CotacaoBean bean, int table, String field, String val, boolean isChar) {
		String updtQuery = "UPDATE";
		HashMap<String, Object> tmp = bean.getDadosEmailCotacao();
		String numCotacao = (String) tmp.get("cCotacao");
		switch (table) {
		case TAB_COTACAO:
			updtQuery += " SISCOTA.SISCOTA.TAB_COTACAO";
			break;
		case TAB_CTRL_SISCOTA:
			updtQuery += " Ged.dbo.tab_ctrl_siscota";
			break;
		case TAB_COTACAO_DIV:
			updtQuery += " SISCOTA.dbo.tab_cotacao_DIV";
			break;
		case TAB_CTRL_PASSO_SISCOTA:
			updtQuery += " Ged.dbo.tab_ctrl_passo_siscota";
			break;
		}

		if (table == TAB_CTRL_PASSO_SISCOTA) {
			updtQuery += " SET " + field + "=" + (isChar ? "'" : "") + val + (isChar ? "'" : "") + " WHERE ccotacao='"
					+ numCotacao + "'" + " and data_final is null";
		} else {
			updtQuery += " SET " + field + "=" + (isChar ? "'" : "") + val + (isChar ? "'" : "") + " WHERE ccotacao='"
					+ numCotacao + "'";
		}

		return execSQLInsert(updtQuery);
		// return 1;
	}

	/**
	 * @author Ricardo
	 * 
	 *         Recupera o ultimo numero da sequencia dos arquivos anexos
	 * @param bean
	 *            Dados da Cotacao.
	 * @return resultado do max(sequencia)
	 */
	public static int recuperaProximaSeqArquivo(CotacaoBean bean) {
		LOGGER.info("[DbAccess] - (recuperaProximaSeqArquivo) : Iniciando o metodo ");
		String query = "SELECT max(sequencia) FROM SISCOTA.dbo.Tab_Anexos" + " WHERE Ccotacao='"
				+ ((String) bean.getDadosEmailCotacao().get("cCotacao")).trim() + "'";
		LOGGER.info("[DbAccess] - (recuperaProximaSeqArquivo) : Iniciando a chamada da query: ".concat(query));
		int retorno = execSQLCount(query);
		LOGGER.info("[DbAccess] - (recuperaProximaSeqArquivo) : Finalizando o metodo com o retorno: ".concat(String.valueOf(retorno)));
		return retorno;
	}

	public int recuperaProximaSeqArquivoACID(CotacaoBean bean) throws SQLException {
		String query = "SELECT max(sequencia) FROM SISCOTA.dbo.Tab_Anexos" + " WHERE Ccotacao='"
				+ ((String) bean.getDadosEmailCotacao().get("cCotacao")).trim() + "'";
		return execSQLCountACID(query);
	}

	/**
	 * @author Ricardo
	 * 
	 *         Salva os dados da resposta do email de Cotacao
	 * @param bean
	 *            Dados da Cotacao.
	 * @return resultado do Update 1 ou 0
	 * 
	 */
	public static int salvaTabCotacaoEmailHist(CotacaoBean bean) {
		LOGGER.info("[DbAccess] - (salvaTabCotacaoEmailHist) : Iniciando o metodo");
		HashMap<String, Object> dadosEmail = bean.getDadosEmailCotacao();
		String respEmail = (String) dadosEmail.get("respEmail");
		if (respEmail == null || respEmail.toUpperCase().equals("null")) {
			respEmail = "";
		}
		String updtQuery = "UPDATE SISCOTA.dbo.tab_cotacao_email_hist" + " SET" + " resp_email='"
				+ Util.trataTexto((String) respEmail) + "'," + " dat_resp='" + Util.getCurrentDateTime() + "'"
				+ " WHERE ccotacao=" + (String) dadosEmail.get("cCotacao") + " AND sequencia="
				+ "(SELECT MAX(sequencia) FROM SISCOTA.dbo.tab_cotacao_email_hist" + " WHERE ccotacao="
				+ (String) dadosEmail.get("cCotacao") + ")";
		LOGGER.info("[DbAccess] - (salvaTabCotacaoEmailHist) : Executando a query: ".concat(updtQuery));
		int retorno = execSQLInsert(updtQuery);
		LOGGER.info("[DbAccess] - (salvaTabCotacaoEmailHist) : Finalizando o metodo com o retorno: ".concat(String.valueOf(retorno)));
		return retorno;
	}

	/**
	 * @author Ricardo
	 * 
	 *         Salva os dados dos arquivos anexos na tabela "SISCOTA.dbo.Tab_Anexos"
	 *         e trata os arquivos
	 * @param bean
	 *            Dados da Cotacao.
	 * @throws SQLException
	 * 
	 */
	public int salvaTabAnexosComTransacao(CotacaoBean bean, String intranet, String numSusep) throws SQLException {

		int ret = 0;
		if (bean.getQtdeEmailArquivos() <= 0)
			return 1; // Verifica se ha arquivos para gravar
		HashMap<String, Object> arqs = bean.getArqsEmailCotacao();
		// Recupera do bean os dados dos arquivos
		Iterator it = arqs.keySet().iterator();

		String numCotacao = "";
		String tpCotacao = "";
		tpCotacao = bean.getTpCotacao(); // 1 - cotacao / 2 - Recotacao
		String Dir_Dest = TransportUtil.DIR_DESTINO;
		String Dir_Temp = TransportUtil.DIR_TEMPORARIO;

		numCotacao = (String) bean.getDadosEmailCotacao().get("cCotacao");
		// Recupera o numero da cotacao
		if (numCotacao == null || numCotacao.equals("") || tpCotacao.equals("2")) {
			numCotacao = bean.getNumProtocolo();
			bean.setDadosEmailCotacao("cCotacao", numCotacao);
		}

		while (it.hasNext()) {
			String key = (String) it.next();
			HashMap<String, Object> tmp = (HashMap) arqs.get(key);

			// Efetua consulta na tabela para saber se o arquivo ja existe
			String query = "SELECT count(nome_arq) FROM SISCOTA.dbo.Tab_Anexos" + " WHERE Ccotacao='"
					+ numCotacao.trim() + "'" + " and novo_arq = '"
					+ new String(Base64.decode((String) tmp.get("tempFile"))) + "' " + " and sequencia = "
					+ (String) tmp.get("sequencia");

			int retTab = execSQLCountACID(query);

			if (tmp.get("fileName").equals("") || tmp.get("fileName") == null) {
				// Verifica se o arquivo foi excluido pelo usuario(Hash)
				// Se existir o arquivo na tabela ele e excluido

				if (retTab > 0) {
					String queryDel = "DELETE FROM SISCOTA.dbo.Tab_Anexos" + " where Ccotacao ='" + numCotacao.trim()
							+ "'" + " and sequencia =" + (String) tmp.get("sequencia") + " and novo_arq ='"
							+ new String(Base64.decode((String) tmp.get("tempFile"))) + "'";

					execSQLACID(queryDel);
				}
				bean.delArqsEmailCotacao((String) tmp.get("tempFile"), true);
			} else {

				// Se insere os dados do arquivo na tabela
				if (retTab <= 0) {
					int seq = recuperaProximaSeqArquivoACID(bean) + 1;
					// Recupera a sequencia de arquivos da tabela

					String extension = String.valueOf(tmp.get("fileName")).substring(
							String.valueOf(tmp.get("fileName")).lastIndexOf("."),
							String.valueOf(tmp.get("fileName")).length());
					String nomeTemp = new String(Base64.decode((String) tmp.get("tempFile")));

					if (!nomeTemp.endsWith(".tmp")) {
						Dir_Temp = Dir_Dest;

					}

					String classificacao = "P"; // publico
					if (tmp.get("classifica").equals("on")
							|| ("1".equalsIgnoreCase(intranet) && "999999".equalsIgnoreCase(numSusep))) {
						classificacao = "R"; // restrito
					}

					File temp = new File(Dir_Temp + "/" + nomeTemp);

					// String arqName = Util.zerosEsq(bean.getNumProtocolo(),
					// 10) + tmp.get("sequencia") + extension; Hebert
					String arqName = Util.zerosEsq(bean.getNumProtocolo(), 10) + seq + extension;

					File destino1 = new File(Dir_Dest + "/" + classificacao + arqName);

					/*
					 * Verifica se existe o arquivo fisico no servidor Foi necessario criar a classe
					 * BuscaArquivoEmDisco para que valide o caminho fazendo a busca em todos os
					 * diretorios que seja possivel existir arquivos gravados no / dados ja que com
					 * o passar do tempo eles sao migrados para pasta de backup que sao todas as
					 * pastas que iniciam com /dados_
					 */

					BuscaArquivoEmDisco buscaArquivoEmDisco = new BuscaArquivoEmDisco(destino1.getPath());

					String path = buscaArquivoEmDisco.verificaCaminhoValidoArquivo(true);

					// if (!destino1.exists()) {
					if (path == null) {
						try {
							TransportUtil.copy(temp, destino1);
						} catch (IOException e1) {
							LOGGER.error("CotacaoMulti erro: " + e1.getMessage(), e1);
						}
						if (nomeTemp.endsWith(".tmp")) {
							if (temp.delete()) {
								// System.out.println( "Excluido:" +
								// temp.getPath());
							}
						}
					}

					tmp.put("tempFile", classificacao + arqName);

					String newPath = "/u/dadosapp/cotacao";
					// Endereco de repositorio dos arquivos
					// File tempFile = new File(tmp.get("tempPath") +"/"+ new
					// String(Base64.decode((String) tmp.get("tempFile"))));
					File tempFile = new File(tmp.get("tempPath") + "/" + (String) tmp.get("tempFile"));

					String ext = (String) tmp.get("fileName");
					// Pega a extenssao do arquivo enviado

					File destino = new File(newPath + "/" + numCotacao.trim() + seq
							+ ext.substring(ext.lastIndexOf("."), ext.length()));

					/*
					 * FileInputStream in = new FileInputStream(tempFile); FileOutputStream out =
					 * new FileOutputStream(destino); int c; while ((c = in.read()) !=
					 * -1)out.write(c); in.close(); out.close();
					 */
					String nome = (String) tmp.get("tempFile");

					if (nome.endsWith(".tmp") && nome.startsWith("multPart")) {
						if (tempFile.delete()) {
							// System.out.println("CotacaoMulti erro: " +
							// "Arquivo excluido!!! - " + tempFile.getPath());
						}
					}

					// Insere os dados dos arquivos na tabela
					String insertQuery = "INSERT INTO SISCOTA.dbo.Tab_Anexos"
							+ "(Ccotacao, sequencia, Nome_Arq, Novo_Arq, Dat_Inclusao, Dir_Arq, tam_arq, arquivoCorrExec) "
							+ "VALUES(" + "'" + numCotacao.trim() + "'," + seq + ",'"
							+ Util.trataTexto((String) tmp.get("fileName")) + "','"
							+ Util.trataTexto((String) tmp.get("tempFile")) + "'," + Util.dataAtual() + ",'"
							+ destino.getParent() + "','" + tmp.get("fileSize") + "'," + bean.getArquivoCorrExec()
							+ ")";

					ret = ret + execSQLInsertACID(insertQuery);

				} else {

					String seq = (String) tmp.get("sequencia");
					String extension = String.valueOf(tmp.get("fileName")).substring(
							String.valueOf(tmp.get("fileName")).lastIndexOf("."),
							String.valueOf(tmp.get("fileName")).length());
					String arqName = Util.zerosEsq(bean.getNumProtocolo(), 10) + seq + extension;

					String classificacao = "P"; // publico
					if (tmp.get("classifica").equals("on")) {
						classificacao = "R"; // restrito
					}

					String updateQuery = "UPDATE SISCOTA.DBO.TAB_ANEXOS SET NOVO_ARQ = '" + classificacao + arqName
							+ "'" + " WHERE CCOTACAO = '" + bean.getNumProtocolo() + "' AND SEQUENCIA = " + seq;
					execSQLInsertACID(updateQuery);
					bean.altArqsCotacao((String) tmp.get("tempFile"),
							new File("/u/dadosapp/cotacao/" + classificacao + arqName));
					ret = retTab;
				}

			}
		} // Fim do While(it.hasNext())
		bean.clearArqsEmailCotacao();
		return ret;
	} // Fim da salvaTabAnexos
	
	/**
	 * @author Ricardo
	 * 
	 *         Salva os dados dos arquivos anexos na tabela "SISCOTA.dbo.Tab_Anexos"
	 *         e trata os arquivos
	 * @param bean
	 *            Dados da Cotacao.
	 * @throws SQLException 
	 * 
	 */
	public static int salvaTabAnexos(CotacaoBean bean, String intranet, String numSusep){
		LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando o metodo");
		int ret = 0;
		if (bean.getQtdeEmailArquivos() <= 0)
			return 1; // Verifica se ha arquivos para gravar
		HashMap<String, Object> arqs = bean.getArqsEmailCotacao();
		// Recupera do bean os dados dos arquivos
		LOGGER.info("[DbAccess] - (salvaTabAnexos) : Recuperando os dados do arquivo no bean");
		Iterator it = arqs.keySet().iterator();
		LOGGER.info("[DbAccess] - (salvaTabAnexos) : Recuperou os dados do arquivo no bean");
		
		String numCotacao = "";
		String tpCotacao = "";
		tpCotacao = bean.getTpCotacao(); // 1 - Cotacao / 2 - ReCotacao
		String Dir_Dest = TransportUtil.DIR_DESTINO;
		String Dir_Temp = TransportUtil.DIR_TEMPORARIO;

		LOGGER.info("[DbAccess] - (salvaTabAnexos) : Recuperando o numero da cotacao no bean");
		numCotacao = (String) bean.getDadosEmailCotacao().get("cCotacao");
		LOGGER.info("[DbAccess] - (salvaTabAnexos) : Recuperou a cotacao no bean com o numero: ".concat(numCotacao));
		// Recupera o numero da Cotacao
		if (numCotacao == null || numCotacao.equals("") || tpCotacao.equals("2")) {
			numCotacao = bean.getNumProtocolo();
			bean.setDadosEmailCotacao("cCotacao", numCotacao);
		}
		LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando o while");
		while (it.hasNext()) {
			String key = (String) it.next();
			HashMap<String, Object> tmp = (HashMap) arqs.get(key);

			// Efetua consulta na tabela para saber se o arquivo ja existe
			String query = "SELECT count(nome_arq) FROM SISCOTA.dbo.Tab_Anexos" + " WHERE Ccotacao='"
					+ numCotacao.trim() + "'" + " and novo_arq = '"
					+ new String(Base64.decode((String) tmp.get("tempFile"))) + "' " + " and sequencia = "
					+ (String) tmp.get("sequencia");

			LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a query: ".concat(query));
			int retTab = execSQLCount(query);
			LOGGER.info("[DbAccess] - (salvaTabAnexos) : Executou a query: ".concat(query));
			LOGGER.info("[DbAccess] - (salvaTabAnexos) : Resultado da query foi: ".concat(String.valueOf(retTab)));
			
			if (tmp.get("fileName").equals("") || tmp.get("fileName") == null) {
				// Verifica se o arquivo foi excluido pelo usuario(Hash)
				// Se existir o arquivo na tabela ele e excluido
				LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a exclusao do arquivo");
				if (retTab > 0) {
					String queryDel = "DELETE FROM SISCOTA.dbo.Tab_Anexos" + " where Ccotacao ='" + numCotacao.trim()
							+ "'" + " and sequencia =" + (String) tmp.get("sequencia") + " and novo_arq ='"
							+ new String(Base64.decode((String) tmp.get("tempFile"))) + "'";
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a query: ".concat(queryDel));
					execSQL(queryDel);
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Executou a query: ".concat(queryDel));
				}
				LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a exclusao do arquivo na bean ");
				bean.delArqsEmailCotacao((String) tmp.get("tempFile"), true);
				LOGGER.info("[DbAccess] - (salvaTabAnexos) : Executou a exclusao do arquivo");
			} else {

				// Se insere os dados do arquivo na tabela
				if (retTab <= 0) {
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a inclusao dos dados do arquivo na tabela ");
					int seq = recuperaProximaSeqArquivo(bean) + 1;
					// Recupera a sequencia de arquivos da tabela

					String extension = String.valueOf(tmp.get("fileName")).substring(
							String.valueOf(tmp.get("fileName")).lastIndexOf("."),
							String.valueOf(tmp.get("fileName")).length());
					String nomeTemp = new String(Base64.decode((String) tmp.get("tempFile")));

					if (!nomeTemp.endsWith(".tmp")) {
						Dir_Temp = Dir_Dest;

					}

					String classificacao = "P"; // publico
					if (tmp.get("classifica").equals("on")
							|| ("1".equalsIgnoreCase(intranet) && "999999".equalsIgnoreCase(numSusep))) {
						classificacao = "R"; // restrito
					}

					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a geração do arquivo com o nome: ".concat(nomeTemp));
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a geração do arquivo no diretorio: ".concat(Dir_Temp));
					File temp = new File(Dir_Temp + "/" + nomeTemp);
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Gerou o arquivo com o nome: ".concat(nomeTemp));

					// String arqName = Util.zerosEsq(bean.getNumProtocolo(),
					// 10) + tmp.get("sequencia") + extension; Hebert
					String arqName = Util.zerosEsq(bean.getNumProtocolo(), 10) + seq + extension;

					
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a geração do arquivo destino1 com o nome: ".concat(arqName));
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a geração do arquivo destino1 com a classificacao: ".concat(classificacao));
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a geração do arquivo destino 1 no diretorio: ".concat(Dir_Dest));
					File destino1 = new File(Dir_Dest + "/" + classificacao + arqName);
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Gerou o arquivo destino1 com o nome: ".concat(arqName));
					/*
					 * Verifica se existe o arquivo fisico no servidor Foi necessario criar a classe
					 * BuscaArquivoEmDisco para que valide o caminho fazendo a busca em todos os
					 * diretorios que seja possivel existir arquivos gravados no / dados ja que com
					 * o passar do tempo eles sao migrados para pasta de backup que sao todas as
					 * pastas que iniciam com /dados_
					 */

					
					BuscaArquivoEmDisco buscaArquivoEmDisco = new BuscaArquivoEmDisco(destino1.getPath());
					String path = buscaArquivoEmDisco.verificaCaminhoValidoArquivo(true);

					// if (!destino1.exists()) {
					if (path == null) {
						try {
							LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a copia do arquivo com path null");
							TransportUtil.copy(temp, destino1);
							LOGGER.info("[DbAccess] - (salvaTabAnexos) : Finalizando a copia do arquivo com path null");
						} catch (IOException e1) {
							LOGGER.error("[DbAccess] - (salvaTabAnexos) - CotacaoMulti erro: " + e1.getMessage(), e1);
						}
						if (nomeTemp.endsWith(".tmp")) {
							LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a exclusao do arquivo temporario com path null");
							if (temp.delete()) {
								LOGGER.info("[DbAccess] - (salvaTabAnexos) : Excluiu  o arquivo temporario com path null");
								// System.out.println( "Excluido:" +
								// temp.getPath());
							}
						}
					}
					
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Incluindo o tempFile com do arquivo: ".concat(arqName));
					tmp.put("tempFile", classificacao + arqName);

					String newPath = "/u/dadosapp/cotacao";
					// Endereco de repositorio dos arquivos
					// File tempFile = new File(tmp.get("tempPath") +"/"+ new
					// String(Base64.decode((String) tmp.get("tempFile"))));
					
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Gerando o arquivo tempFile com o arquivo: ".concat(arqName));
					File tempFile = new File(tmp.get("tempPath") + "/" + (String) tmp.get("tempFile"));
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Gerou o arquivo tempFile com o arquivo: ".concat(arqName));
					String ext = (String) tmp.get("fileName");
					// Pega a extensao do arquivo enviado
					
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Gerando o arquivo destino no novo path com o numero da cotacao ".concat(numCotacao.trim()));
					File destino = new File(newPath + "/" + numCotacao.trim() + seq
							+ ext.substring(ext.lastIndexOf("."), ext.length()));
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Gerou o arquivo destino com o numero da cotacao ".concat(numCotacao.trim()));
					/*
					 * FileInputStream in = new FileInputStream(tempFile); FileOutputStream out =
					 * new FileOutputStream(destino); int c; while ((c = in.read()) !=
					 * -1)out.write(c); in.close(); out.close();
					 */
					String nome = (String) tmp.get("tempFile");

					if (nome.endsWith(".tmp") && nome.startsWith("multPart")) {
						LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a exclusao do arquivo temporario multPart");
						if (tempFile.delete()) {
							LOGGER.info("[DbAccess] - (salvaTabAnexos) : Excluiu o arquivo temporario multPart");
							// System.out.println("CotacaoMulti erro: " +
							// "Arquivo excluido!!! - " + tempFile.getPath());
						}
					}

					// Insere os dados dos arquivos na tabela
					String insertQuery = "INSERT INTO SISCOTA.dbo.Tab_Anexos"
							+ "(Ccotacao, sequencia, Nome_Arq, Novo_Arq, Dat_Inclusao, Dir_Arq, tam_arq, arquivoCorrExec) "
							+ "VALUES(" + "'" + numCotacao.trim() + "'," + seq + ",'"
							+ Util.trataTexto((String) tmp.get("fileName")) + "','"
							+ Util.trataTexto((String) tmp.get("tempFile")) + "'," + Util.dataAtual() + ",'"
							+ destino.getParent() + "','" + tmp.get("fileSize") + "'," + bean.getArquivoCorrExec()
							+ ")";
					
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a inclusao dos dados do arquivo na tabela do siscota com a query: ".concat(insertQuery));
					ret = ret + execSQLInsert(insertQuery);
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Incluiu os dados do arquivo na tabela do siscota com a query: ".concat(insertQuery));
				} else {
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a alteracao dos dados do arquivo na tabela ");
					String seq = (String) tmp.get("sequencia");
					String extension = String.valueOf(tmp.get("fileName")).substring(
							String.valueOf(tmp.get("fileName")).lastIndexOf("."),
							String.valueOf(tmp.get("fileName")).length());
					String arqName = Util.zerosEsq(bean.getNumProtocolo(), 10) + seq + extension;

					String classificacao = "P"; // publico
					if (tmp.get("classifica").equals("on")) {
						classificacao = "R"; // restrito
					}
					
					String updateQuery = "UPDATE SISCOTA.DBO.TAB_ANEXOS SET NOVO_ARQ = '" + classificacao + arqName
							+ "'" + " WHERE CCOTACAO = '" + bean.getNumProtocolo() + "' AND SEQUENCIA = " + seq;
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Iniciando a alteracao dos dados do arquivo na tabela do siscota com a query: ".concat(updateQuery));
					execSQLInsert(updateQuery);
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Executou a alteracao dos dados do arquivo na tabela do siscota com a query: ".concat(updateQuery));
					bean.altArqsCotacao((String) tmp.get("tempFile"),
							new File("/u/dadosapp/cotacao/" + classificacao + arqName));
					LOGGER.info("[DbAccess] - (salvaTabAnexos) : Finalizou a alteracao na bean dos dados do arquivo");
					ret = retTab;
				}

			}
		} // Fim do While(it.hasNext())
		LOGGER.info("[DbAccess] - (salvaTabAnexos) : Finalizando o while");
		bean.clearArqsEmailCotacao();
		LOGGER.info("[DbAccess] - (salvaTabAnexos) : Finalizando o metodo");
		return ret;
	} // Fim da salvaTabAnexos

	
	private void execSQLACID(String query) throws SQLException {
		stACIDList.add(connACID.createStatement());
		stACIDList.get(stACIDList.size()-1).execute(query);
		
	}
	
	/**
	 * @author Ricardo
	 * 
	 *         Recupera os dados dos aquivos anexo a Cotacao e guarda no Bean
	 * @param bean
	 *            Dados da Cotacao.
	 * @param numCotacao
	 *            Numero da Cotacao.
	 * 
	 */
	public static int recuperaArqAnexos(CotacaoBean bean, String numCotacao, int tipo) {
		LOGGER.info("[DbAccess] - (recuperaArqAnexos) : Iniciando o metodo");
		int i = 1;
		// Recupera os dados da tabela
		String query = " Select an.*, status_fluxo , status_andamento " + " from siscota.dbo.tab_anexos  as an "
				+ " inner join ged.dbo.tab_ctrl_siscota as ctr " + " on an.ccotacao=ctr.ccotacao "
				+ " WHERE an.ccotacao='" + numCotacao + "'";

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();
			LOGGER.info("[DbAccess] - (recuperaArqAnexos) : Executando a query: ".concat(query));
			rs = select.executeQuery(query);
			LOGGER.info("[DbAccess] - (recuperaArqAnexos) : Executou a query: ".concat(query));
			// Insere os dados no bean
			while (rs.next()) {

				// Hebert
				String classificacao = "off";

				if (rs.getString("novo_arq") != null) {
					if (rs.getString("novo_arq").startsWith("R")) {
						classificacao = "on";
					}
				}

				if ((rs.getInt("status_fluxo") == 9 && rs.getInt("status_andamento") != 5) || tipo == 1) {
					bean.setArqsEmailCotacao(rs.getInt("sequencia"), rs.getString("Dir_arq"), rs.getString("Dir_arq"),
							rs.getString("nome_arq"), rs.getString("novo_arq"), rs.getString("tam_arq"), classificacao,
							1, rs.getInt("arquivoCorrExec"));
					i++;
				} else {
					if (rs.getString("nome_arq").toUpperCase().indexOf("COTDIV") == -1) {

						bean.setArqsEmailCotacao(rs.getInt("sequencia"), rs.getString("Dir_arq"),
								rs.getString("Dir_arq"), rs.getString("nome_arq"), rs.getString("novo_arq"),
								rs.getString("tam_arq"), classificacao, 1, rs.getInt("arquivoCorrExec"));
					}
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("[DbAccess] - (recuperaArqAnexos) - CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		LOGGER.info("[DbAccess] - (recuperaArqAnexos) : Finalizando o metodo");
		return i;
	}

	/**
	 * @author Ricardo
	 * 
	 *         Recupera os dados do email de Cotacao e guarda no Bean
	 * @param bean
	 *            Dados da Cotacao.
	 * @param numCotacao
	 *            Numero da Cotacao.
	 * 
	 */

	public static int recuperaEmailHist(CotacaoBean bean, String numCotacao) {
		LOGGER.info("[DbAccess] - (recuperaEmailHist) : Iniciando o metodo");
		int i = 0;
		// Recupera os dados da tabela
		String query = "SELECT nom_membro, end_email, corpo_email, resp_email, sequencia"
				+ " FROM SISCOTA.dbo.tab_cotacao_email_hist" + " where ccotacao = '" + numCotacao + "'"
				+ " and sequencia = (select max(sequencia)" + " FROM SISCOTA.dbo.tab_cotacao_email_hist"
				+ " where ccotacao = '" + numCotacao + "')";

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();
			LOGGER.info("[DbAccess] - (recuperaEmailHist) : Executando a query: ".concat(query));
			rs = select.executeQuery(query);
			LOGGER.info("[DbAccess] - (recuperaEmailHist) : Executou a query: ".concat(query));
			// Insere os dados no bean
			if (rs.next()) {
				bean.setDadosEmailCotacao("cCotacao", numCotacao);
				bean.setDadosEmailCotacao("nomeCorretor", rs.getString("nom_membro"));
				bean.setDadosEmailCotacao("email", rs.getString("end_email"));
				bean.setDadosEmailCotacao("corpoEmail", rs.getString("corpo_email"));
				bean.setDadosEmailCotacao("respEmail", rs.getString("resp_email"));
				bean.setDadosEmailCotacao("sequencia", rs.getString("sequencia"));
				i++;
			}
		} catch (Exception e) {
			LOGGER.error("[DbAccess] - (recuperaEmailHist) - CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		LOGGER.info("[DbAccess] - (recuperaEmailHist) : Finalizando o metodo ");
		return i;
	}

	public static CorretorBean ListaCorretores(String numSusep, long codUnidade, String codCorretor) throws SusepHistoricoException {

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		CorretorBean list = new CorretorBean();
		String query = "select c.cod_corretor, c.corretor, c.cid_corretor" + " from Ged.dbo.TBS_Corretor as c ";

		if (codUnidade != 0) {
			query = query + "inner join ged.dbo.TBS_Corr_Prod_Unid as u " + " on c.cod_corretor = u.cod_corretor ";
		}
		query = query + " where c.cod_ativo = 1 ";
		
		//PORTAL_CORRETOR
		if (numSusep.equalsIgnoreCase("999999") == false) {
			//NOVA_SUSEP
//			query += " and (NUM_SUSEP_COMPLETO = '" + numSusep + "'";
//			query += " OR NUM_SUSEP = '" + numSusep + "') ";

			String suseps = new SusepHistoricoService().obterSusepsPorSusepToQueryIN(numSusep);
			query += " and (NUM_SUSEP_COMPLETO IN (" + suseps + ")";
			query += " OR NUM_SUSEP IN (" + suseps + ")) ";
		}

		if (codCorretor != null && !"".equals(codCorretor) && !"0".equals(codCorretor)) {
			query += " and c.cod_corretor = " + codCorretor + " ";
		}

		if (codUnidade != 0) {
			/*
			 * if (codUnidade == 251) { query = query +
			 * " and unid_negocio in ('251','271') "; } else { query = query +
			 * " and unid_negocio = " + codUnidade; }
			 */
			query = query + " and unid_negocio = " + codUnidade;

			// Ficha - 1300263
			query += " or (c.cod_corretor in (select cod_corretor from GED.DBO.TBS_Corr_Prod_Unid_Excecao where unid_negocio = "
					+ codUnidade + "))";
		}
		query = query + " order by corretor ";
		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			String descricao = "";
			while (rs.next()) {
				codCorretor = Util.zerosEsq(rs.getString("cod_corretor"), 07);
				list.addCodigo(codCorretor);
				descricao = rs.getString("corretor") + "/" + rs.getString("cid_corretor");
				list.addDescricao(descricao);
			}
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return list;
	}

	// todo: aqui Eri
	/**
	 * @param unidade
	 *            : Define a unidade que sera consultada
	 * @param flag
	 *            : Se 2, consulta apenas a unidade enviada. Se 1, caso unidade seja
	 *            251 considera 251 and 271.
	 * @throws SusepHistoricoException 
	 **/
	public static Vector<Hashtable<String, Object>> recuperaListaSegurado(CotacaoBean bean, long codUnidade, int flag) throws SusepHistoricoException {
		Vector<Hashtable<String, Object>> ret = new Vector<Hashtable<String, Object>>();
		Hashtable<String, Object> lista = recuperaListaRamos(); // Recupera nome do ramo
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;
		//CachedRowSetImpl rs = null;
		CotacaoBean cot;
        bean.setCnpjCpf(CpfCnpjValidator.formataCpfCnpj(bean.getCnpjCpf()));

		boolean usaUnionAll = false;
		String queryUnionAll = "";

		String query = " SELECT F.PRAZO_UNIDADE,F.STATUS_FLUXO,F.STATUS_ANDAMENTO,B.ACORRETOR, B.CDEPARTAMENTO, B.CPRODUTOR, B.CCOTACAO, B.CRAMO, B.VRISCO, B.VPREMIO_TOTAL, B.SCOTACAO, B.ASEGURADO, "
				+ " A.SEGURADO, C.NOMSEGURADO ,D.PRODUTOR, B.DSYS " + " from SISCOTA.SISCOTA.TAB_COTACAO as B (nolock) "
				+ " right JOIN GED.DBO.TAB_CTRL_SISCOTA AS F (nolock)  " + " ON B.CCOTACAO = F.CCOTACAO "
				+ " left JOIN SISCOTA.SISCOTA.TAB_SEGURADO as A (nolock) " + " ON A.CSEGURADO = B.CSEGURADO "
				+ " left JOIN SISCOTA.dbo.tab_cotacao_div as C (nolock) " + " ON B.CCOTACAO = C.CCOTACAO "
				+ " left JOIN GED.dbo.TBS_Produtor as D (nolock) " + " ON B.CPRODUTOR = D.COD_PRODUTOR "
				+ " left JOIN ged.dbo.tbs_corretor as E (nolock) "
				+ " ON E.COD_CORRETOR = (case C.COD_CORRETOR when '' then 0 else C.COD_CORRETOR end) ";

		//PORTAL_CORRETOR
		if ("999999".equalsIgnoreCase(bean.getNumSusep()) == false) {
			//NOVA_SUSEP
//			query += " WHERE (E.NUM_SUSEP_COMPLETO = '" + bean.getNumSusep() + "'";
//			query += " OR E.NUM_SUSEP = '" + bean.getNumSusep() + "') AND ";

			String suseps = new SusepHistoricoService().obterSusepsPorSusepToQueryIN(bean.getNumSusep());
			query += " WHERE (E.NUM_SUSEP_COMPLETO IN (" + suseps + ")";
			query += " OR E.NUM_SUSEP IN (" + suseps + ")) AND ";
		} else {
			if (codUnidade != 0)
				query += " WHERE cdepartamento in ('" + codUnidade + "','"
						+ Util.zerosEsq(String.valueOf(codUnidade), 4) + "') and ";
			else
				query += " WHERE ";
		}

		if (bean.getCodCorr() != null && !"".equalsIgnoreCase(bean.getCodCorr())
				&& !"0".equalsIgnoreCase(bean.getCodCorr())) {
			query += " e.cod_corretor = " + bean.getCodCorr() + " AND ";
		}

		if (bean.getCodCliente() != null && !"".equalsIgnoreCase(bean.getCodCliente())
				&& !"0".equalsIgnoreCase(bean.getCodCliente())) {
			query += " B.cod_cliente = " + bean.getCodCliente() + " AND ";
		}

		if (bean.getNomeProp().length() > 0)
			query += " C.NOMSEGURADO LIKE '%" + Util.trataTexto(bean.getNomeProp().toUpperCase()) + "%'";
		else if (bean.getCnpjCpf().length() > 0) {
			queryUnionAll = " UNION ALL " + query;
			usaUnionAll = true;
			query += " (A.CPF='" + Util.trataTexto(bean.getCnpjCpf()) + "')";
			queryUnionAll += " (A.CGC='" + Util.trataTexto(bean.getCnpjCpf()) + "' ";
			if (Util.trataTexto(bean.getCnpjCpf()).length() > 7 && Util.trataTexto(bean.getCnpjCpf()).length() < 14) {
				queryUnionAll += " OR A.CGC like '" + Util.trataTexto(bean.getCnpjCpf()).substring(0, 8) + "%'";
			}
			queryUnionAll += ")";
		}

		String fimQuery = "";

		if (bean.getDataInicio().length() > 0 && bean.getDataFim().length() > 0) {
			if (bean.getCodSituacao().length() == 0) {
				fimQuery += " AND ";
			}
			fimQuery += " (B.DSYS BETWEEN " + Util.convertDataToSql(bean.getDataInicio()) + " AND "
					+ Util.convertDataToSql(bean.getDataFim()) + ")";
			if (!"1".equalsIgnoreCase(bean.getCodSituacao()) && bean.getCodSituacao().length() > 0) {
				if ("14".equalsIgnoreCase(bean.getCodSituacao())) {
					fimQuery += " AND B.SCOTACAO in('14','16')";
				} else {
					fimQuery += " AND B.SCOTACAO='" + Util.zerosEsq(bean.getCodSituacao(), 2) + "'";
				}
			}

		}

		if (!"".equalsIgnoreCase(bean.getCodCorr()) && !"0".equalsIgnoreCase(bean.getCodCorr())) {
			fimQuery = fimQuery + " and B.ccorretor = " + bean.getCodCorr();
		}
		if (!"".equalsIgnoreCase(bean.getCodProdutor())) {
			fimQuery = fimQuery + " and B.cprodutor = " + bean.getCodProdutor();
		}

		if (bean.getCodRamo() != 0) {
			fimQuery = fimQuery + " and B.cramo = " + bean.getCodRamo();
		} else {
			fimQuery += " and B.cramo in ('100', '340','110','112','113','114','710','150','670','510','750','460','620','300','410', '780') ";

		}
		
		//CARVOUT
		fimQuery += " and F.tipo_fluxo = " + TipoFluxo.VALOR.getValor();

		query = query + fimQuery;
		if (usaUnionAll) {
			queryUnionAll = queryUnionAll + fimQuery;
			query = query + queryUnionAll;
		}

		query += " order by B.CCOTACAO desc";

		LOGGER.info("recuperaListaSegurado.query: " + query);
		
		try {
			conn = getConn();
			select = conn.createStatement();

//			rs = new CachedRowSetImpl();
			rs = select.executeQuery(query);
//			rs.populate(resultset);

//			resultset.close();

			while (rs.next()) {
				Hashtable<String, Object> item = new Hashtable<String, Object>();
				//Date prazoUnidade = null;
				//prazoUnidade = rs.getDate("PRAZO_UNIDADE");
				// Hebert
				String aCorretor = rs.getString("ACORRETOR").trim();
				item.put("aCorretor", aCorretor);
				String cDepartamento = rs.getString("CDEPARTAMENTO").trim();
				item.put("cDepartamento", cDepartamento);
				String produtor = "";
				if (rs.getString("PRODUTOR") != null && !"".equalsIgnoreCase(rs.getString("PRODUTOR"))) {
					produtor = rs.getString("PRODUTOR").trim();
				}
				item.put("cProdutor", produtor);
				// Hebert
				String cCotacao = rs.getString("CCOTACAO").trim();
				item.put("cCotacao", cCotacao);
				String cRamo = rs.getString("CRAMO").trim();
				item.put("cRamo", cRamo);
				item.put("nomRamo", (String) lista.get(String.valueOf(cRamo)));
				String nomeSeg = "";
				if (rs.getString("NOMSEGURADO") != null && !"".equalsIgnoreCase(rs.getString("NOMSEGURADO"))) {
					nomeSeg = rs.getString("NOMSEGURADO").trim().toUpperCase();
				}
				if (nomeSeg.length() > 40) {
					nomeSeg = nomeSeg.substring(0, 40);
				}
				item.put("nomeProponente", nomeSeg);
				item.put("valorRisco", Util.formataMoeda(rs.getDouble("VRISCO")));
				item.put("valorPremio", Util.formataMoeda(rs.getDouble("VPREMIO_TOTAL")));

				item.put("cotFinalizacao", recuperaCtrlSiscotaFinalizado(cCotacao));

				//int sCotacao = rs.getInt("SCOTACAO");
				//int statusFluxo = 0;
				//int statusAndamento = 0;
				//statusFluxo = rs.getInt("STATUS_FLUXO");
				//statusAndamento = rs.getInt("Status_andamento");

				String retorno = "";
				retorno = recuperaSituacaoAtual(cCotacao);
				item.put("cotFinalData", retorno);
				item.put("cotSituacao", "");
				item.put("dataCotacao", getFormataData(rs.getString("DSYS")));

				if ("1".equalsIgnoreCase(bean.getNovaConsultaAnexos())) {
					cot = new CotacaoBean();
					DbAccess.recuperaArqAnexos(cot, cCotacao, 1);
					item.put("beanAnexos", cot);
				}
				ret.add(item);
				bean.setNumProtocolo(cCotacao);

			}
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e2) {
				LOGGER.error("CotacaoMulti erro: " + e2.getMessage(), e2);
			}
//			try {
//				if (resultset != null) {
//					resultset.close();
//				}
//			} catch (Exception e2) {
//				LOGGER.error("CotacaoMulti erro: " + e2.getMessage(), e2);
//			}
			fechaConexao(null, select, conn);
		}

		return ret;
	}

	public static String recuperaCtrlSiscotaFinalizado(String cCotacao) {
		String query = "SELECT data_hora_fim FROM Ged.dbo.TAB_CTRL_SISCOTA" + " WHERE CCOTACAO='" + cCotacao + "'";
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		String ret = "NÃO";
		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			if (rs.next()) {
				String str = rs.getString("data_hora_fim");
				if (str != null)
					ret = "SIM";
			}
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return ret;
	}

	public static String recuperaCtrlSiscotaDataSituacao(String cCotacao, boolean dtInicio, boolean retStatus) {
		String query = "SELECT data_hora_fim , data_hora_inicio  , status_andamento, prazo_unidade, "
				+ " (select dat_geracao from SISCOTA.dbo.tab_cotacao_email_hist "
				+ " where sequencia = (select max(sequencia) " + " FROM SISCOTA.dbo.tab_cotacao_email_hist where "
				+ "	ccotacao = '" + cCotacao + "')" + "	and ccotacao= '" + cCotacao + "') as data_email "
				+ " FROM Ged.dbo.TAB_CTRL_SISCOTA" + " WHERE CCOTACAO='" + cCotacao + "'";

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		String ret = "";
		int status = 0;
		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			if (rs.next()) {
				Date dt = null;
				String s = "";
				status = rs.getInt("status_andamento");
				dt = rs.getDate("prazo_unidade");
				if (status == 2 || (status == 10 && dt != null)) {
					dt = rs.getDate("data_email");
					if (dt != null)
						s = "Aguardando informações do corretor desde "
								+ (new SimpleDateFormat("dd/MM/yyyy")).format(dt);
					else
						s = "Aguardando informações do corretor";
					ret = s;
				} else {
					if (status == 10)
						dtInicio = true;
					if (dtInicio) {
						dt = rs.getDate("data_hora_inicio");
						if (dt == null) {
							s = " EM ANÁLISE ";
						} else {
							s = " EM ANÁLISE DESDE ";
						}
					} else {
						dt = rs.getDate("data_hora_fim");
						if (dt != null) {
							s = " EM ";
						} else {
							s = "  ";
						}

					}
					if (dt != null) {
						ret = s + (new SimpleDateFormat("dd/MM/yyyy")).format(dt);
					} else {
						ret = s;
					}
				}
			} else {
				ret = " EM ANÁLISE ";
			}
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

		if (retStatus == true) {
			// ret = situacoesCotacaoList[status] + " " + ret; Hebert
			Hashtable<String, Object> sit = situacoesCotacaoList();
			ret = (String) sit.get(String.valueOf(status)) + " " + ret;
		}
		return ret;
	}

	public static String recuperaCtrlPassoSiscota(String cCotacao, String passo) {
		// cCotacao = "200601013755";
		String query = " SELECT passo , data_inicial, data_final" + " FROM GED.dbo.tab_ctrl_passo_siscota"
				+ " where ccotacao = '" + cCotacao + "'" + " and passo = " + passo + " and data_final is null"
				+ " and cod_seq = (select max(cod_seq) FROM GED.dbo.tab_ctrl_passo_siscota" + " where ccotacao = '"
				+ cCotacao + "' )";
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		String ret = "";
		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			if (rs.next()) {
				// if(rs.getInt("passo")==1 && rs.getDate("data_final")==null){
				if ((rs.getInt("passo") == 1 || rs.getInt("passo") == 3 || rs.getInt("passo") == 4)
						&& rs.getDate("data_final") == null) {

					Date dt = rs.getDate("data_inicial");
					if (dt != null) {
						ret = " EM ANÁLISE DESDE " + (new SimpleDateFormat("dd/MM/yyyy")).format(dt);
					}
				} else {
					if (rs.getInt("passo") == 2 && rs.getDate("data_final") == null) {
						Date dt = rs.getDate("data_inicial");
						if (dt != null) {
							ret = " PENDENTE CORRETOR DESDE " + (new SimpleDateFormat("dd/MM/yyyy")).format(dt);
						}
					} else {
						if (rs.getInt("passo") == 3 && rs.getDate("data_final") == null) {
							Date dt = rs.getDate("data_inicial");
							if (dt != null) {
								ret = " EM ANÁLISE DESDE " + (new SimpleDateFormat("dd/MM/yyyy")).format(dt);
							}
						}
					}
				}
			} else {
				// if(passo.equals("1") || passo.equals("3")){
				if (passo.equals("1") || passo.equals("3") || passo.equals("4")) {
					ret = " EM ANÁLISE";
				} else {
					ret = " PENDENTE CORRETOR";
				}
			}
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

		return ret;
	}

	public static String recuperaSituacaoAtual(String cCotacao) {
		String query = " Exec siscota.dbo.proc_RetornaStatusSiscota '" + cCotacao + "'";
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		String ret = "";
		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			if (rs.next()) {
				ret = rs.getString("sit");
			}
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		if (ret == null)
			ret = "";
		return ret;
	}

	/**
	 * @author Erialdo Metodo recupera o local de risco da tab_ped,tab_ped_loc
	 * 
	 * @param numSusep
	 * @param numApol
	 * @return
	 * @throws SusepHistoricoException 
	 */
	public static int recuperaLocalRisco(CotacaoBean cot, String numSusep) throws SusepHistoricoException {

		int ret = 1;
		String end = "0";
		if (cot != null && cot.getNumApol() != null && Long.parseLong(cot.getNumApol()) != 0) {
			String query = "select MAX(DAT_EMIS)as vDataEmis, A.NUM_ENDO ";
			query = query + "FROM [Ged].[dbo].[Tab_Ped_Corr] A  inner join [Ged].[dbo].[Tab_Ped] B  on ";
			query = query + "A.NUM_APOL = B.NUM_APOL AND A.NUM_ENDO = B.NUM_ENDO ";
			query = query + "left join [Ged].[dbo].[TBS_Corretor] C on A.COD_CORR = C.cod_corretor AND cod_ativo=1 ";
			query = query + " WHERE a.num_apol = " + cot.getNumApol();
			
			//PORTAL_CORRETOR
			if (numSusep.equalsIgnoreCase("999999") == false) {
				//NOVA_SUSEP
//				query += " and (c.NUM_SUSEP_COMPLETO = '" + cot.getNumSusep() + "'";
//				query += " or c.NUM_SUSEP = '" + cot.getNumSusep() + "') ";

				String suseps = new SusepHistoricoService().obterSusepsPorSusepToQueryIN(numSusep);
				query += " and (c.NUM_SUSEP_COMPLETO IN (" + suseps + ")";
				query += " or c.NUM_SUSEP IN (" + suseps + ")) ";
			}
			query = query + " GROUP BY  A.NUM_ENDO";

			Connection conn = null;
			Statement select = null;
			ResultSet rs = null;

			try {
				conn = getConn();
				select = conn.createStatement();
				rs = select.executeQuery(query);

				if (rs.next()) {
					end = rs.getString("NUM_ENDO");
					ret = 0;
				}
				rs.close();
				select.close();

				if (ret == 0) {
					query = "SELECT  count(NUM_APOL) FROM [Ged].[dbo].[Tab_Ped_Loc]";
					query = query + "WHERE NUM_APOL = " + cot.getNumApol() + " and NUM_ENDO = " + end;

					int totLocRisc = execSQLCount(query);

					if (totLocRisc > 0) {

						query = "SELECT B.NOM_SEGURADO,  B.NUM_CGC_CPF, A.COD_TIP_LOGR_RISCO, A.NOM_LOGR_RISCO, A.DSC_COMPL_RISCO, A.NOM_BAIRRO_RISCO, ";
						query = query + "A.NOM_CID_RISCO, A.SIG_UF_RISCO, A.NUM_CEP_RISCO ";
						query = query
								+ " FROM [Ged].[dbo].[Tab_Ped_Loc] A inner join [Ged].[dbo].[Tab_Ped] B ON   A.NUM_APOL = B.NUM_APOL AND A.NUM_ENDO = B.NUM_ENDO ";
						query = query + "WHERE A.NUM_APOL =" + cot.getNumApol() + " and A.NUM_ENDO =" + end
								+ " and  A.NUM_ITEM =1 ";
						conn = null;

						conn = getConn();
						select = conn.createStatement();
						rs = select.executeQuery(query);
						if (rs.next()) {

							cot.setNomeProp(rs.getString("NOM_SEGURADO"));
							cot.setCnpjCpf(rs.getString("NUM_CGC_CPF"));

							if (rs.getString("NUM_CGC_CPF").length() == 14) {
								cot.setTipPessoa("1");
							} else {
								cot.setTipPessoa("0");
								// um local de risco
							}

							if (totLocRisc == 1) {
								cot.setQtdLocRisc("0");
								cot.setCep(rs.getString("NUM_CEP_RISCO"));
								cot.setEnd(rs.getString("COD_TIP_LOGR_RISCO") + " " + rs.getString("NOM_LOGR_RISCO"));
								cot.setComplemento(rs.getString("DSC_COMPL_RISCO"));
								cot.setBairro(rs.getString("NOM_BAIRRO_RISCO"));
								cot.setCid(rs.getString("NOM_CID_RISCO"));
								cot.setUf(rs.getString("SIG_UF_RISCO"));
							} else {
								cot.setQtdLocRisc("1");
								// mais de um local de risco
							}
							ret = 0;
						}
					} else {
						cot.setQtdLocRisc("0"); // nao encontrou local de risco
					}
				}
			} catch (Exception e) {
				LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
			} finally {
				fechaConexao(rs, select, conn);
			}
		} else {
			if (cot != null)
				cot.setQtdLocRisc("0"); // nao encontrou local de risco

		}

		return ret;

	}

	/**
	 * Executa um statement SQL
	 * 
	 * @param query
	 *            Query com statement count.
	 * @return resultado do count(*)
	 */
	private static void execSQL(String query) {
		Connection conn = null;
		Statement select = null;
		try {
			conn = getConn();
			select = conn.createStatement();
			select.execute(query);
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(null, select, conn);
		}
	}

	/**
	 * Aloca uma conexao com o banco de dados.
	 * 
	 * @return Connection
	 * @throws NamingException
	 * @throws SQLException
	 */
	private static Connection getConn() throws NamingException, SQLException {
		LOGGER.info("[DbAccess] - (getConn) : Iniciando o metodo");
		Connection conexaoConn = ((DataSource) new InitialContext().lookup(SQL_PROD)).getConnection();
		LOGGER.info("[DbAccess] - (getConn) : Finalizando o metodo");
		return conexaoConn;
	}

	private static Connection getConnYasudanetAWS() throws NamingException, SQLException {
		LOGGER.info("[DbAccess] - (getConnYasudanetAWS) : Iniciando o metodo");
		//return ((DataSource) new InitialContext().lookup(YASUDANET_AWS)).getConnection();
		Connection conexaoConnYasudaNetAws = getConn();
		LOGGER.info("[DbAccess] - (getConnYasudanetAWS) : Finalizando o metodo");
		return conexaoConnYasudaNetAws;
	}

	public static String verificaAdesao(String numSusep, int tipoAdesao) {
		String msg = "";
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		Calendar data = Calendar.getInstance(new Locale("pt", "br"));
		// int dia = data.get(Calendar.DAY_OF_MONTH);
		// int mes = data.get(Calendar.MONTH) + 1;
		int ano = data.get(Calendar.YEAR);

		try {
			conn = getConnYasudanetAWS();
			select = conn.createStatement();
			String query = "Select dat_adesao from yasudanet.dbo.Tab_Ctrl_Adesao " + "where num_susep = '" + numSusep
					+ "' " + "and dat_adesao > '" + (ano - 1) + "-12-31'";

			// Adesao Automovel
			if (tipoAdesao == 1) {
				query += " and num_protocolo >= 9" + String.valueOf(ano).substring(3, 4) + "00000 ";
				query += " and num_protocolo <= 9" + String.valueOf(ano).substring(3, 4) + "99999 ";
			}
			// Adesao ramos Diversos
			if (tipoAdesao == 2) {
				query += " and num_protocolo >= 8" + String.valueOf(ano).substring(3, 4) + "00000 ";
				query += " and num_protocolo <= 8" + String.valueOf(ano).substring(3, 4) + "99999 ";
			}

			rs = select.executeQuery(query);
			df = null;
			if (rs.next()) {
				df = new SimpleDateFormat("dd/MM/yyyy");
				msg = "Sua corretora já enviou a opção de adesão em " + df.format(rs.getDate("dat_adesao"))
						+ ".<br><br>Sompo Seguros";
			}

		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

		return msg;
	}

	public static String gravaAdesao(String numSusep, String codUser, long numProtocolo, int tipoAdesao) {
		String msg = "";
		Connection conn = null;
		Statement select = null;

		try {
			conn = getConnYasudanetAWS();
			select = conn.createStatement();
			String query = "INSERT INTO yasudanet.dbo.Tab_Ctrl_Adesao([num_susep], [cod_user], [dat_adesao], [num_protocolo]) VALUES('"
					+ numSusep + "','" + (codUser.length() > 10 ? codUser.substring(0, 10) : codUser) + "','"
					+ Util.getCurrentDateTime() + "'," + numProtocolo + ")";

			try {
				select.executeUpdate(query);
			} catch (SQLException e) {
				if (e.getErrorCode() == 2627) {
					msg = verificaAdesao(numSusep, tipoAdesao);
				} else {
					msg = "Ocorreu um problema na gravação.<br><br>Query: " + query;
				}
			}

			// select.close();
			// conn.close();
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(null, select, conn);
		}

		return msg;
	}

	public static long getNextProtocolo(int tipo) {
		long numProtocolo = 0;
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConnYasudanetAWS();
			select = conn.createStatement();
			// Obs.: Sempre conferir a procedure.
			// opcoes de protocolo da Comissao Adicional
			// 1 - Auto (A numeracao comeca com 9, o segundo digito e o ano -
			// Ex: 9900001)
			// 2 - Diversos (A numeracao comeca com 8, o segundo digito e o ano
			// - Ex: 8900001)
			String query = "EXEC yasudanet.dbo.prcGetNextProtocolo " + tipo;
			rs = select.executeQuery(query);

			if (rs.next()) {
				numProtocolo = rs.getLong(1);
			}

		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return (numProtocolo);
	}

	/**
	 * @author Hebert
	 * 
	 *         Salva os dados alterados da comissao e desconto na tabela
	 *         Siscota.Tab_Cotacao
	 * @param String
	 *            comissao
	 * @param String
	 *            desconto
	 * @param String
	 *            protocolo
	 */
	public static int atualizaDadosComissaoTabCotacao(String comissao, String desconto, String protocolo) {
		if (comissao == null || comissao.equals("")) {
			comissao = "0";
		}
		if (desconto == null || desconto.equals("")) {
			desconto = "0";
		}

		String updtQuery = "UPDATE SISCOTA.SISCOTA.TAB_COTACAO" + " SET" + " PCOMISSAO ="
				+ Util.formataMoedaToDB(comissao) + " , VDESCONTO =" + Util.formataMoedaToDB(desconto)
				+ " WHERE CCOTACAO='" + protocolo + "'";

		return execSQLInsert(updtQuery);
	}

	public static String liberaVisualizacaoTodosAnexos(String pLogin) {
		LOGGER.info("[DbAccess] - (liberaVisualizacaoTodosAnexos) : Iniciando o metodo");
		String visualizaTodosAnexos = "N";
		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();
			String query = "Select Cod_Depto, Gestor from ged.dbo.tab_user where login_user_rede = '" + pLogin + "'";

			rs = select.executeQuery(query);

			if (rs.next()) {
				if (rs.getLong("Cod_Depto") == 935 || rs.getLong("Cod_Depto") == 938 || rs.getInt("Gestor") == 1) {
					visualizaTodosAnexos = "S";
				}
			}

		} catch (Exception e) {
			LOGGER.error("[DbAccess] - (liberaVisualizacaoTodosAnexos) - CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		LOGGER.info("[DbAccess] - (liberaVisualizacaoTodosAnexos) : Finalizando o metodo");
		return visualizaTodosAnexos;
	}

	public UnidadeBean recuperaUnidadeSegurado(String cnpjSegurado) {
		UnidadeBean unidade = new UnidadeBean();
		Connection conn = null;
		String query = null;
		Statement select = null;
		ResultSet rs = null;
        cnpjSegurado = CpfCnpjValidator.formataCpfCnpj(cnpjSegurado);

		try {
			query = (new StringBuilder(
					"select unid_negocio, cod_produtor from [ged].[dbo].[TBS_Seg_Prod_Unid] where left(num_cgc_cpf,7) = "))
							.append(cnpjSegurado.substring(0, 8)).toString();
			conn = getConn();
			select = conn.createStatement();

			rs = select.executeQuery(query);

			if (rs.next()) {
				unidade.setUnidNegocio(Util.zerosEsq(rs.getString("unid_negocio"), 4));
				unidade.setCodProdutor(Util.zerosEsq(rs.getString("cod_produtor"), 4));
			}
			rs.close();
			select.close();
			conn.close();
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return unidade;
	}

	public List<Dominio> recuperaListaProfissoes() throws Exception {
		Statement select = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Dominio> profissoes = new ArrayList<Dominio>();

		try {
			String query = "select COD_OCUPACAO, SUB_CODIGO_OCUP, OCUPACAO from P0044800.dbo.T0044811 order by ocupacao";
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			Profissao profissao;
			while (rs.next()) {
				profissao = new Profissao();
				profissao.setId(rs.getString("COD_OCUPACAO"));
				profissao.setSubId(rs.getString("SUB_CODIGO_OCUP"));
				profissao.setNome(rs.getString("OCUPACAO"));
				profissoes.add(profissao);
			}
			rs.close();
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return profissoes;
	}

	public List<Dominio> recuperaListaRamosAtividades() throws Exception {
		Statement select = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Dominio> ramosAtividades = new ArrayList<Dominio>();

		try {
			String query = "select COD_ATIV, DSC_ATIV, COD_SYAS from P0044800.dbo.T0044801 order by DSC_ATIV";
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query);
			RamoAtividade ramoAtividade;
			while (rs.next()) {
				ramoAtividade = new RamoAtividade();
				ramoAtividade.setId(rs.getString("COD_ATIV"));
				ramoAtividade.setNome(rs.getString("DSC_ATIV"));
				ramoAtividade.setCodSyas(rs.getString("COD_SYAS"));
				ramosAtividades.add(ramoAtividade);
			}
			rs.close();
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return ramosAtividades;
	}

	public static String getFormataData(String databanco) {
		StringBuilder data = new StringBuilder("");
		if (!"".equalsIgnoreCase(databanco) && databanco != null && !"0".equalsIgnoreCase(databanco)) {
			data.append(databanco.substring(6, 8));
			data.append("/");
			data.append(databanco.substring(4, 6));
			data.append("/");
			data.append(databanco.substring(0, 4));
		}

		return data.toString();
	}

	public List<Ramo> recuperaListaRamos(List<Integer> ramos) throws Exception {
		Statement select = null;
		ResultSet rs = null;
		Connection conn = null;
		List<Ramo> lista = new ArrayList<Ramo>();

		try {
			StringBuilder query = new StringBuilder(
					"select COD_RAMO, COD_DEPT, RAMO from ged.dbo.tbs_ramo where cod_ramo in (");
			for (int i = 0; i < ramos.size(); i++) {
				if (i == ramos.size() - 1) {
					query.append(ramos.get(i));
				} else {
					query.append(ramos.get(i) + ",");
				}
			}
			query.append(") order by ramo");
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery(query.toString());
			Ramo ramo;
			while (rs.next()) {
				ramo = new Ramo();
				ramo.setCodigo(rs.getString("COD_RAMO"));
				ramo.setDepto(rs.getString("COD_DEPT"));
				ramo.setNome(rs.getString("RAMO"));
				lista.add(ramo);
			}
			rs.close();
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}
		return lista;
	}

	public static void recuperaCorretorMaritima(CotacaoBean bean) {

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();

			select = conn.createStatement();
			String query = "SELECT COD_CORR_MAR from GED.DBO.TBS_CORRETOR " + " WHERE cod_corretor = "
					+ bean.getCodCorr();

			rs = select.executeQuery(query);
			if (rs.next()) {
				bean.setSucursal(rs.getString("COD_CORR_MAR").substring(0, 2));
				bean.setCorrMarit(rs.getString("COD_CORR_MAR").substring(2, 7));
			}
		} catch (Exception e) {
			LOGGER.error("CotacaoMulti getCodCorr: " + bean.getCodCorr() + " - erro: " + e.getMessage(), e);
		} finally {
			fechaConexao(rs, select, conn);
		}

	}
	
	
	public static String updatesSusepCotacao() throws Exception {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			
			
			String queryMulti = "update [SISCOTA].[dbo].[tab_cotacao_div] set num_susep = a.num_susep_completo " + 
					" from (select cod_corretor, num_susep_completo from ged.dbo.tbs_corretor) as a " + 
					" , ged..tab_ctrl_siscota g " + 
					" where CAST(a.cod_corretor AS int) = CAST(SISCOTA.dbo.tab_cotacao_div.cod_corretor AS int) " + 
					" and [SISCOTA].[dbo].[tab_cotacao_div].CCOTACAO = g.CCOTACAO " + 
					" and g.data_hora_inicio >= '2020/01/01'";			
			
			
			String queryAuto = "update [SISCOTA].[dbo].[TAB_COTACAO_AUTO] set num_susep = a.num_susep_completo " + 
					" from (select cod_corretor, num_susep_completo from ged.dbo.tbs_corretor) as a " + 
					"  , ged..tab_ctrl_siscota g " + 
					" where CAST(a.cod_corretor AS int) = CAST(SISCOTA.dbo.TAB_COTACAO_AUTO.cod_corretor AS int) " + 
					" and [SISCOTA].[dbo].[TAB_COTACAO_AUTO].CCOTACAO = g.CCOTACAO " + 
					" and g.data_hora_inicio >= '2020/01/01'";
			
			
			preparedStatement = conn.prepareStatement(queryMulti);
			int totalMulti = preparedStatement.executeUpdate();
			preparedStatement.close();
			
			preparedStatement = conn.prepareStatement(queryAuto);
			int totalAuto = preparedStatement.executeUpdate();
			
			return "Total Multi: " + totalMulti + " | Total Auto: " + totalAuto;  
 

		} catch (Exception e) {
			LOGGER.error("Erro ao realizar o update da susep do corretor", e);
			throw e;
		} finally {
			fechaConexao(rs, preparedStatement, conn);
		}
	}

	
	public Boolean verificarExistenciaCnpjBlackListContasInternacionais(String cnpj) {
		
		Boolean resultado 						= Boolean.FALSE;
		Connection conn 						= null;
		PreparedStatement prepareStatement 		= null;
		ResultSet resultSet 					= null;
        cnpj = CpfCnpjValidator.formataCpfCnpj(cnpj);
		
		try {
			StringBuilder query = new StringBuilder("SELECT CASE WHEN EXISTS (SELECT cpf_cnpj FROM [SISCOTA].[dbo].[tab_Lista_Contas_Internacionais] ")
											.append("WHERE cpf_cnpj = ?) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END as temCnpj");
					
			conn = getConn();
			prepareStatement = conn.prepareStatement(query.toString());
			prepareStatement.setString(1, cnpj);
			resultSet = prepareStatement.executeQuery();
			while(resultSet.next()) {
				resultado = resultSet.getBoolean("temCnpj");
			}

		} catch (Exception e) {
			LOGGER.error("CotacaoAuto - Erro ao buscar CNPJ na blacklist de contas internacionais..." + e.getMessage(), e);
		} finally {
			fechaConexao(resultSet, prepareStatement, conn);
		}
		return resultado;
	}
	
	private Connection getConnACID() throws NamingException, SQLException {
		LOGGER.info("[DbAccess] - (getConnACID) : Iniciando o metodo");
		//connACID = ((DataSource) new InitialContext().lookup(SQL_PROD)).getConnection();
		//return connACID;
		Connection conexaoACID = getConn();
		LOGGER.info("[DbAccess] - (getConnACID) : Finalizando o metodo");
		return conexaoACID;
	}
	
	public Connection openConnectionACID() throws NamingException, SQLException {
		LOGGER.info("[DbAccess] - (openConnectionACID) : Iniciando o metodo");
		connACID = getConnACID();
		connACID.setAutoCommit(false);
		stACIDList = new ArrayList<Statement>();
		LOGGER.info("[DbAccess] - (openConnectionACID) : Finalizando o metodo");
		return connACID;
	}

	public void rollback() throws SQLException {
		if(connACID!=null)
			connACID.rollback();
	}
	
	public void commit() throws SQLException {
		if(connACID!=null)
			connACID.commit();
	}

	public void endACID() {
		try {
			for(Statement st : stACIDList){
				if(st!=null)st.close();
			}
			connACID.close();
		} catch (SQLException e) {
			LOGGER.error("CotacaoMulti - " + e.getMessage(), e);
		}
	}

	public void salvarParametrosB2B(String codigoCotacao, String codigoHierarquico, String codigoFilial) throws Exception {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			conn = getConn();
		
			StringBuilder sql = new StringBuilder()
				.append("INSERT INTO SISCOTA.dbo.TAB_PARAM_B2B ( CCOTACAO, COD_HIERARQUICO, COD_FILIAL, DT_CADASTRO ) ")
				.append("VALUES( '").append( codigoCotacao ).append("', '").append( codigoHierarquico ).append("', '")
				.append( codigoFilial ).append("', '").append( Timestamp.valueOf( LocalDateTime.now() ) ).append("' )");
	
			preparedStatement = conn.prepareStatement( sql.toString() );
		
			preparedStatement.execute();
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar parametros B2B", e);
			throw e;
		} finally {
			fechaConexao(null, preparedStatement, conn);
		}
	}


	// JIRA-302950
	public static ArrayList<Integer> recuperaRestricoes(Long codigoCorretor,Integer codigoRamo) throws Exception {

		ArrayList<Integer> ret = new ArrayList<Integer>();

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();
			rs = select.executeQuery("select ID_ACAO from siscota.dbo.TAB_CORRETOR_RESTRICAO_RAMO_ACAO where cod_ramo = " + codigoRamo +" AND cod_corretor = " + codigoCorretor);
			while (rs.next()) {
				ret.add(rs.getInt("ID_ACAO"));
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao listar Restricoes", e);
			throw e;
		} finally {
			fechaConexao(rs, select, conn);
		}
		return ret;
	}

	public static List<RestricaoDTO> recuperaListaRestricoes() throws Exception {

		List<RestricaoDTO> retorno = new ArrayList<RestricaoDTO>();

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();
			
				rs = select.executeQuery(
						"select DISTINCT id_acao, cod_ramo from siscota.dbo.TAB_CORRETOR_RESTRICAO_RAMO_ACAO ");

			while (rs.next()) {
				RestricaoDTO item=new RestricaoDTO();
				item.setIdAcao(rs.getInt("id_acao"));
				if (item.getIdAcao().intValue()==1) item.setDscAcao("1-Seguro Novo");
				if (item.getIdAcao().intValue()==2) item.setDscAcao("2-Renov Congênere");
				if (item.getIdAcao().intValue()==3) item.setDscAcao("3-Renov Sompo");
				if (item.getIdAcao().intValue()==4) item.setDscAcao("4-Endosso");
				item.setCodigoRamo(rs.getInt("cod_ramo"));
				retorno.add(item);
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao listar restricoes do corretor", e);
			throw e;
		} finally {
			fechaConexao(rs, select, conn);
		}
		return retorno;
	}

	private static long recuperaMaxRestricoes() throws Exception {

		long retorno =0;

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();
			
				rs = select.executeQuery(
						"select MAX(ID) AS MAX from siscota.dbo.TAB_CORRETOR_RESTRICAO_RAMO_ACAO ");

			while (rs.next()) {
				retorno=rs.getLong("MAX");
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao listar restricoes do corretor", e);
			throw e;
		} finally {
			fechaConexao(rs, select, conn);
		}
		return retorno;
	}
	
	public static  void insereRestricoes(Integer codigoRamo, Integer idAcao,String[] corretores) throws Exception {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			conn = getConn();
		    long max=recuperaMaxRestricoes();
			for (int i=0;i<corretores.length;i++) {
				max++;
				StringBuilder sql = new StringBuilder()
					.append("INSERT INTO SISCOTA.dbo.TAB_CORRETOR_RESTRICAO_RAMO_ACAO ( ID,COD_CORRETOR,COD_RAMO,ID_ACAO,DATA_ATUALIZACAO ) ")
					.append("VALUES( ").append( max ).append(", ").append( corretores[i] ).append(", ")
					.append( codigoRamo ).append(", ").append( idAcao ).append(", '").append( Timestamp.valueOf( LocalDateTime.now() ) ).append("' )");
		
				preparedStatement = conn.prepareStatement( sql.toString() );
			
				preparedStatement.execute();
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar parametros B2B", e);
			throw e;
		} finally {
			fechaConexao(null, preparedStatement, conn);
		}
		
	}
	
	public static  ArrayList<String> restricaoCorretores(Integer codigoRamo, Integer idAcao) throws Exception {
		ArrayList<String> retorno = new ArrayList<String>();

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();
			
				rs = select.executeQuery(
						"select DISTINCT COD_CORRETOR from siscota.dbo.TAB_CORRETOR_RESTRICAO_RAMO_ACAO WHERE COD_RAMO="+codigoRamo+" AND ID_ACAO="+idAcao);

			while (rs.next()) {
				retorno.add(rs.getString("COD_CORRETOR"));
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao listar restricoes do corretor", e);
			throw e;
		} finally {
			fechaConexao(rs, select, conn);
		}
		return retorno;
	}
		
	public static  void restricaoExcluir(Integer codigoRamo, Integer idAcao) throws Exception {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			conn = getConn();
				StringBuilder sql = new StringBuilder()
					.append("DELETE from siscota.dbo.TAB_CORRETOR_RESTRICAO_RAMO_ACAO WHERE COD_RAMO="+codigoRamo+" AND ID_ACAO="+idAcao);
		
				preparedStatement = conn.prepareStatement( sql.toString() );
			
				preparedStatement.execute();
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar parametros B2B", e);
			throw e;
		} finally {
			fechaConexao(null, preparedStatement, conn);
		}
		
	}
	
	public static  void restricaocorrExcluir(Integer codigoRamo, Integer idAcao, String ccorr) throws Exception {
		Connection conn = null;
		PreparedStatement preparedStatement = null;

		try {
			conn = getConn();
				StringBuilder sql = new StringBuilder()
					.append("DELETE from siscota.dbo.TAB_CORRETOR_RESTRICAO_RAMO_ACAO WHERE COD_RAMO="+codigoRamo+" AND ID_ACAO="+idAcao +" AND COD_CORRETOR="+ccorr);
		
				preparedStatement = conn.prepareStatement( sql.toString() );
			
				preparedStatement.execute();
		} catch (Exception e) {
			LOGGER.error("Erro ao salvar parametros B2B", e);
			throw e;
		} finally {
			fechaConexao(null, preparedStatement, conn);
		}
		
	}
	
	public static List<HistoricoInternoDTO> recuperaListaHistoricoInterno(String cotacao) throws Exception {

		List<HistoricoInternoDTO> retorno = new ArrayList<HistoricoInternoDTO>();

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();
			
				rs = select.executeQuery(
						"select sequencia, nom_membro, observacao, format(dat_geracao, 'dd/MM/yyyy hh:mm:ss') dat_geracao from siscota.dbo.tab_cotacao_hist_observacao where ccotacao = " + cotacao + " order by sequencia desc");

			while (rs.next()) {
				
				HistoricoInternoDTO item = new HistoricoInternoDTO();
				
				item.setSequencia(rs.getString("sequencia"));
				item.setNom_membro(rs.getString("nom_membro"));
				item.setObservacao(rs.getString("observacao"));
				item.setDat_geracao(rs.getString("dat_geracao"));
				
				retorno.add(item);
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao listar histórico interno da cotação", e);
			throw e;
		} finally {
			fechaConexao(rs, select, conn);
		}
		return retorno;
	}
	
	public static List<HistoricoExternoDTO> recuperaListaHistoricoExterno(String cotacao) throws Exception {

		List<HistoricoExternoDTO> retorno = new ArrayList<HistoricoExternoDTO>();

		Connection conn = null;
		Statement select = null;
		ResultSet rs = null;

		try {
			conn = getConn();
			select = conn.createStatement();
			
				rs = select.executeQuery(
						"select sequencia, nom_membro, end_email, corpo_email, resp_email, format(dat_resp, 'dd/MM/yyyy hh:mm:ss') dat_resp, format(dat_geracao, 'dd/MM/yyyy hh:mm:ss') dat_geracao from siscota.dbo.tab_cotacao_email_hist where ccotacao = " + cotacao + " order by sequencia desc");

			while (rs.next()) {
				
				HistoricoExternoDTO item = new HistoricoExternoDTO();
				
				item.setSequencia(rs.getString("sequencia"));
				item.setNom_membro(rs.getString("nom_membro"));
				item.setEnd_email(rs.getString("end_email"));
				item.setCorpo_email(rs.getString("corpo_email"));
				item.setResp_email(rs.getString("resp_email"));
				item.setDat_resp(rs.getString("dat_resp"));
				item.setDat_geracao(rs.getString("dat_geracao"));
				
				retorno.add(item);
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao listar histórico externo da cotação", e);
			throw e;
		} finally {
			fechaConexao(rs, select, conn);
		}
		return retorno;
	}

}
