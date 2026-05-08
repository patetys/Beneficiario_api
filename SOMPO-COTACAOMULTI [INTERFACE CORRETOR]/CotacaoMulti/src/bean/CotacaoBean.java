package bean;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import db.DbAccess;
import util.Util;

/*
 * Criado em 03/10/2006
 * 
 */
/**
 * @author erialdo
 *
 */
public class CotacaoBean implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(CotacaoBean.class);

	private static final long serialVersionUID = 1282674425416422890L;

	// Cotacao
	private String numProtocolo = "";
	private String numSusep;
	private int lmiUnico = 0;
	private String profissaoRamoAtividade = "";
	private String novaConsultaAnexos = "";

	// Dados Gerais
	private int codRamo = 0;
	private String nomRamo = "";
	private String codCorr = "";
	private String tpCotacao = "1"; // 1 - cotacao / 2 - Recotacao
	private String tipEmissao = "0";
	private String codCliente = "";

	private String sucursal = "";
	private String corrMarit = "";

	// 0 - Seguro Novo / 1 - Renovacao Congenere/ 2 - Renovacao Yasuda / 2 - Endosso
	private String nomEmissao = "0";
	private String numApol = "0";
	private String numApolFormatted = "0000000000";
	private String codModalidade = "";
	private String nomModalidade = "";

	// Dados do Proponente
	private String nomeProp = "";
	private String tipPessoa = ""; // 0 - fisica / 1 - Juridica
	private String cnpjCpf = "";
	private String formattedCnpjCpf = "";

	// Local de Risco
	private String qtdLocRisc = "0";
	// 0 - tem local de risco / 1 -varios locais de risoc
	private String end = "";
	private String numero = "";
	private String complemento = "";
	private String bairro = "";
	private String cid = "";
	private String cep = "";
	private String uf = "";
	private String telefone = "";
	private double vlRisco = 0;
	private String comentario = "";

	// Dados para Correspondencia
	private String emailContato = "";
	private String nomeContato = "";
	private String telefContato = "";

	// Dados Complementares
	private String numCotacaoAnt = "";
	private int codClasseAceitacao = 0;
	private String classeAceitacao = "";
	private String codProdutor = "";
	private String nomProdutor = "";
	private String codDepto = "";
	private String nomDepto = "";
	private String codSegurado = "";
	private String nomAbrSegurado = "";
	private String nomCorr = "";
	private String codUser = "";
	private String unidadeNegocioCodigoProdutor;

	private String dataVigenciaInicio = "0";
	private String dataVigenciaFim = "0";
	private String dataInicio = "";
	private String dataFim = "";
	private String codSituacao = "";
	private String cotFinalData = "";
	private String keyext = "";
	private String obs = "";
	private String senha = "";
	private String cotacaoOrigem = "";
	private Date data_Cotacao;
	private int codMotivo = 0;
	private String nomMotivo = "";
	private String tecnico = "";
	private String refSyas = "";
	private String userAnuencia = "";
	private double desconto = 0;

	// novos campos conforme solicitacao

	private double pmargem = 0;
	private double pcomissao = 0;
	private double ptaxa = 0;
	private double vpremiototal = 0;
	private String infoTecnica = "";

	private int altopotrisco = 0;
	private int declinado = 0;
	private int recusadoirb = 0;

	private int condinspec = 0;

	private int lmi = 0;
	private int facultativo = 0;
	private int especiais = 0;

	private int aprovairb = 0;
	private int inspecao = 0;
	private int cobertura = 0;
	private int clausulabenef = 0;
	private String beneficiario = "";
	private int periodo = 0;
	private int meses = 0;
	private int especificacao = 0;
	private String regraAlcada = "";

	private final String DIVDETJSP = "MSDet.jsp"; // Pagina jsp
	private String[] dados_html;

	// Dados dos arquivos de Cotacao
	private HashMap<String, Object> dadosCotacao = null;
	private HashMap<String, Object> arqsCotacao = null;
	private int arquivoCorrExec = 0;
	
	// Parametros B2B
	private String codigoHierarquico;
	private String codigoFilial;

	public CotacaoBean() {
		dadosCotacao = new HashMap<String, Object>();
		arqsCotacao = new HashMap<String, Object>();
	}

	/**
	 * acessada quando nao tem dados especificos
	 * 
	 * @param d
	 */
	public void setArquivoCorrExec(int i) {
		this.arquivoCorrExec = i;
	}

	public void setRegraAlcada(String i) {
		this.regraAlcada = i;
	}

	public void setCodClasseAceitacao(int i) {
		this.codClasseAceitacao = i;
	}

	public void setClasseAceitacao(String i) {
		this.classeAceitacao = i;
	}

	public void setNomProdutor(String i) {
		this.nomProdutor = i;
	}

	public void setNomDepto(String i) {
		this.nomDepto = i;
	}

	public void setCotacaoOrigem(String i) {
		this.cotacaoOrigem = i;
	}

	public void setKeyext(String i) {
		this.keyext = i;
	}

	public void setDataInicio(String i) {
		this.dataInicio = i;
	}

	public void setDataFim(String i) {
		this.dataFim = i;
	}

	public void setCodSituacao(String i) {
		this.codSituacao = i;
	}

	public int getArquivoCorrExec() {
		return arquivoCorrExec;
	}

	public String getRegraAlcada() {
		return regraAlcada;
	}

	public int getCodClasseAceitacao() {
		return codClasseAceitacao;
	}

	public String getClasseAceitacao() {
		switch (getCodClasseAceitacao()) {
		case 14:
			classeAceitacao = "Classe 1 (Aceitação Normal)";
			break;
		case 15:
			classeAceitacao = "Classe 3 (Sem Aceitação)";
			break;
		case 16:
			classeAceitacao = "Classe 4 (Aceitação Restrita)";
			break;
		default:
			classeAceitacao = "";
			break;
		}
		return classeAceitacao;
	}

	public String getCotacaoOrigem() {
		return cotacaoOrigem;
	}

	public String getKeyext() {
		return keyext;
	}

	public String getDataInicio() {
		return dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public String getCodSituacao() {
		return codSituacao;
	}

	public String getNomProdutor() {
		return nomProdutor;
	}

	public String getNomDepto() {
		return nomDepto;
	}

	public void setDBtoBean(Hashtable<String, Object> d) {

	}

	public void setDadosHtml() {
		dados_html = new String[0];
	}

	// Set
	public void setDados(HttpServletRequest req) {

	}

	public String setGravaTabela() {
		numero = getNumProtocolo();
		return numero; // retornar protocolo
	}

	public void setNumProtocolo(String i) {
		this.numProtocolo = i;
	}

	public void setNumSusep(String i) {
		this.numSusep = i;
	}

	@SuppressWarnings("rawtypes")
	public void setCodRamo(int i) {
		this.codRamo = i;

		Hashtable l = DbAccess.recuperaListaRamos(); // Recupera nome do ramo
		setNomRamo((String) l.get(String.valueOf(i)));

	}

	public void setNomRamo(String i) {
		this.nomRamo = i;
	}

	public void setCodCorr(String i) {
		this.codCorr = i;
	}

	public void setTpCotacao(String i) {
		this.tpCotacao = i;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getCorrMarit() {
		return corrMarit;
	}

	public void setCorrMarit(String corrMarit) {
		this.corrMarit = corrMarit;
	}

	/**
	 * TipEmissao 0 - Seguro Novo 1 - Renovacao Congenere 2 - Renovacao Yasuda 3 -
	 * Endosso
	 */
	@SuppressWarnings("rawtypes")
	public void setTipEmissao(String i) {
		this.tipEmissao = i;

		if (i.length() == 1)
			i = "0" + i;

		Hashtable l = DbAccess.recuperaListaTpEmissao();
		// Recupera nome do ramo
		setNomEmissao((String) l.get(String.valueOf(i)));
	}

	public void setNomEmissao(String i) {
		this.nomEmissao = i;
	}

	public void setNumApol(String i) {
		this.numApol = i;
		setNumApolFormatted(i);
	}

	public void setCodModalidade(String i) {
		this.codModalidade = i;

		Hashtable<String, Object> l = DbAccess.recuperaListaTpModalidade(getCodRamo());
		// Recupera nome do ramo
		setNomModalidade((String) l.get(String.valueOf(i)));

	}

	public void setNomModalidade(String i) {
		this.nomModalidade = i;
	}

	public void setNomeProp(String i) {
		this.nomeProp = i.toUpperCase();
	}

	public void setTipPessoa(String i) {
		this.tipPessoa = i;
	}

	public void setCnpjCpf(String i) {
		this.cnpjCpf = i;
		setFormattedCnpjCpf(i);
	}

	/**
	 * @param string
	 */
	public void setFormattedCnpjCpf(String i) {
		if (!i.equalsIgnoreCase(" ") && i.length() > 8) {
			if (i.length() <= 11) {
				i = Util.zerosEsq(i, 11);
				this.formattedCnpjCpf = i.substring(0, 3) + "." + i.substring(3, 6) + "." + i.substring(6, 9) + "-"
						+ i.substring(9);
			} else {
				if (i.length() < 14) {
					i = Util.zerosEsq(i, 14);
					// i = ("0000" + i);
					i = i.substring(i.length() - 14);
				}
				this.formattedCnpjCpf = i.substring(0, 2) + "." + i.substring(2, 5) + "." + i.substring(5, 8) + "/"
						+ i.substring(8, 12) + "-" + i.substring(12, 14);
			}
		}
	}

	/**
	 * QtdLocRisc 0 - Tem endereco 1 - nao tem endereco (+ de um local de risco)
	 * 
	 * @param i
	 */

	public void setQtdLocRisc(String i) {
		this.qtdLocRisc = i;
	}

	public void setEnd(String i) {
		this.end = i;
	}

	public void setNumero(String i) {
		this.numero = i;
	}

	public void setComplemento(String i) {
		this.complemento = i;
	}

	public void setBairro(String i) {
		this.bairro = i;
	}

	public void setCid(String i) {
		this.cid = i;
	}

	public void setUf(String i) {
		this.uf = i;
	}

	public void setCep(String i) {
		this.cep = i;
	}

	public void setTelefone(String i) {
		this.telefone = i;
	}

	public void setVlRisco(double i) {
		this.vlRisco = i;
	}

	public void setComentario(String i) {
		this.comentario = i;
	}

	public void setEmailContato(String i) {
		this.emailContato = i;
	}

	public void setNomeContato(String i) {
		this.nomeContato = i;
	}

	public void setTelefContato(String i) {
		this.telefContato = i;
	}

	public void setCodProdutor(String i) {
		this.codProdutor = i;
	}

	public void setCodDepto(String i) {
		this.codDepto = i;
	}

	public void setCodSegurado(String i) {
		this.codSegurado = i;
	}

	public void setNomAbrSegurado(String i) {
		this.nomAbrSegurado = i;
	}

	public void setNomCorr(String i) {
		this.nomCorr = i;
	}

	public void setCodUser(String i) {
		this.codUser = i;
	}

	public void setNumCotacaoAnt(String i) {
		this.numCotacaoAnt = i;
	}

	/**
	 * ramos que nao tem dados especificos utiliza este metodo
	 * 
	 * @return
	 */
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable<String, Object> list = new Hashtable<String, Object>();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", " ");
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
		list.put("dados14", " ");
		list.put("dados15", " ");

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;
	}

	public Hashtable<String, Object> setDadosDiv(Hashtable<String, Object> d) {

		// Dados principais

		d.put("numProtocolo", getNumProtocolo());
		d.put("numSusep", getNumSusep());
		d.put("codCorretor", getCodCorr());
		d.put("codRamo", String.valueOf(getCodRamo()));
		d.put("modalidade", getCodModalidade());
		d.put("keyExt", (String) " ");
		d.put("nomContat", getNomeContato());
		d.put("emailContat", getEmailContato());
		d.put("telContat", getTelefContato());
		d.put("userAlt", getCodUser());
		d.put("datAlt", Util.dataAtual());
		d.put("NOMSEGURADO", getNomeProp());
		d.put("LMIUNICO", this.getLmiUnico());
		d.put("profissao", this.getProfissaoBanco());
		d.put("ramoAtividade", this.getRamoAtividadeBanco());

		return d;

	}

	// Get
	public String getNumProtocolo() {
		return numProtocolo;
	}

	public int getCodRamo() {
		return codRamo;
	}

	public String getNumSusep() {
		return numSusep;
	}

	public String getNomRamo() {
		return nomRamo;
	}

	public String getCodCorr() {
		return codCorr;
	}

	public String getTpCotacao() {
		return tpCotacao;
	}

	public String getTipEmissao() {
		return tipEmissao;
	}

	public String getNomEmissao() {
		return nomEmissao;
	}

	public String getNumApol() {
		return numApol;
	}

	public String getCodModalidade() {
		return codModalidade;
	}

	public String getNomModalidade() {
		return nomModalidade;
	}

	public String getNomeProp() {
		return nomeProp;
	}

	public String getTipPessoa() {
		return tipPessoa;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public String getQtdLocRisc() {
		return qtdLocRisc;
	}

	public String getEnd() {
		return end;
	}

	public String getNumero() {
		return numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCid() {
		return cid;
	}

	public String getUf() {
		return uf;
	}

	public String getCep() {
		return cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public double getVlRisco() {
		return vlRisco;
	}

	public String getComentario() {
		return comentario;
	}

	public String getEmailContato() {
		return emailContato;
	}

	public String getNomeContato() {
		return nomeContato;
	}

	public String getTelefContato() {
		return telefContato;
	}

	public String getCodProdutor() {
		return codProdutor;
	}

	public String getCodDepto() {
		return codDepto;
	}

	public String getCodSegurado() {
		return codSegurado;
	}

	public String getNomAbrSegurado() {
		return nomAbrSegurado;
	}

	public String getNomCorr() {
		return nomCorr;
	}

	public String getCodUser() {
		return codUser;
	}

	public String getNumCotacaoAnt() {
		return numCotacaoAnt;
	}

	public String getPaginaJSP() {
		return DIVDETJSP;
	}

	public String[] getDadosHtml() {
		if (dados_html == null)
			setDadosHtml();

		return dados_html;
	}

	public int getDadosToDB(String numProtoloco) {

		return 0;
	}

	public String getcotFinalData() {
		return cotFinalData;
	}

	/**
	 * Dados dos arquivos anexo do email de Cotacao
	 * 
	 * @param tempPath
	 * @param tempFile
	 * @param nameFile
	 * @param size
	 * @return
	 */
	public void setArqsEmailCotacao(int sequencia, String tempPath, String filePath, String nameFile, String tempFile,
			String size, String classifica, // Hebert
			int status, int arquivoCorrExec) {

		HashMap<String, Object> par = new HashMap<String, Object>();
		par.put("sequencia", String.valueOf(sequencia));
		par.put("tempPath", tempPath);
		par.put("filePath", filePath);
		par.put("fileName", nameFile);
		par.put("tempFile", util.Base64.encodeBytes(tempFile.getBytes()));
		par.put("fileSize", "" + size);
		par.put("classifica", classifica);
		par.put("status", String.valueOf(status));

		par.put("arquivoCorrExec", String.valueOf(arquivoCorrExec));
		arqsCotacao.put(tempFile, par);
	}

	/**
	 * Dados do email de Cotacao
	 * 
	 * @param campo
	 * @param value
	 * @return
	 */
	public void setDadosEmailCotacao(String campo, String value) {
		if (value != null) {
			dadosCotacao.put(campo, value);
		}
	}

	/**
	 * Limpa os dados do email de Cotacao
	 * 
	 * @return
	 */
	public void clearDadosEmailCotacao() {
		dadosCotacao = new HashMap<String, Object>();
	}

	/**
	 * Limpa os dados dos arquivos anexo de Cotacao
	 * 
	 * @return
	 */
	public void clearArqsEmailCotacao() {
		arqsCotacao = new HashMap<String, Object>();
	}

	/**
	 * Retorna os dados dos arquivos anexo ao email
	 * 
	 * @return
	 */
	public HashMap<String, Object> getArqsEmailCotacao() {
		return arqsCotacao;
	}

	/**
	 * Retorna os dados do email
	 * 
	 * @return
	 */
	public HashMap<String, Object> getDadosEmailCotacao() {
		return dadosCotacao;
	}

	/**
	 * Retorna a quantidade de arquivos anexos
	 * 
	 * @return
	 */
	public int getQtdeEmailArquivos() {
		return arqsCotacao.size();
	}

	/**
	 * Exclui o arquivo selecionado do bean Obs.: Limpa somento o nome e o tamanho
	 * do arquivo do bean, pois a exclusao do arquivo fisico so ocorrera quando for
	 * enviar o email e gravar os dados na tabela
	 * 
	 * @param tempFile
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void delArqsEmailCotacao(String tempFile, boolean del) {
		tempFile = new String(util.Base64.decode(tempFile));
		if (arqsCotacao.get(tempFile) != null) {
			HashMap tmp = (HashMap) arqsCotacao.get(tempFile);
			String nameFile = (String) tmp.get("tempFile");
			nameFile = new String(util.Base64.decode(nameFile));
			String tempPath = (String) tmp.get("tempPath");
			tmp.put("fileName", "");
			tmp.put("fileSize", "");
			if (del == true || (nameFile.endsWith(".tmp") && nameFile.startsWith("multPart"))) { // quando del=true
																									// arquivo fisico
																									// sera excluido
				File f = new File(tempPath + "/" + nameFile);
				if (!f.delete()) { // limpa o
					// System.out.println("CotacaoMulti erro: Erro ao deletar o arquivo!!!!");
				}
			}

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void altArqsCotacao(String tempFile, File newName) {
		tempFile = new String(util.Base64.decode(tempFile));
		if (arqsCotacao.get(tempFile) != null) {
			HashMap tmp = (HashMap) arqsCotacao.get(tempFile);
			String nameFile = (String) tmp.get("tempFile");
			nameFile = new String(util.Base64.decode(nameFile));
			String tempPath = (String) tmp.get("tempPath");
			tmp.put("fileName", "");
			tmp.put("fileSize", "");
			if ((!nameFile.endsWith(".tmp") && !nameFile.startsWith("multPart"))) { // quando del=true arquivo fisico
																					// sera excluido
				File f = new File(tempPath + "/" + nameFile);
				if (!f.renameTo(newName)) { // limpa o
					LOGGER.error("CotacaoMulti erro: Erro ao alterar o arquivo!!!!");
				}
			}

		}
	}

	/**
	 * Retorna o valor do campo solicitado.
	 * 
	 * @param arqKey
	 * @param fieldKey
	 * @return ret
	 */
	@SuppressWarnings("rawtypes")
	public String getArqsEmailCotacao(String arqKey, String fieldKey) {
		if (arqsCotacao.get(arqKey) == null)
			return "";
		HashMap tmp = (HashMap) arqsCotacao.get(arqKey);
		String ret = (String) tmp.get(fieldKey);
		if (ret == null)
			return "";
		return ret;
	}

	public void setcotFinalData(String string) {
		cotFinalData = string;
	}

	/**
	 * @return
	 */
	public String getFormattedCnpjCpf() {
		return formattedCnpjCpf;
	}

	/**
	 * @return
	 */
	public String getNumApolFormatted() {
		return numApolFormatted;
	}

	/**
	 * @param string
	 */
	public void setNumApolFormatted(String string) {
		if (string != null && !string.equals("")) {
			DecimalFormat df = new DecimalFormat("0000000000");
			numApolFormatted = df.format(Long.parseLong(string));
		}
	}

	/**
	 * @return
	 */
	public String getObs() {
		return obs;
	}

	/**
	 * @param string
	 */
	public void setObs(String string) {
		obs = string;
	}

	/**
	 * @return
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param string
	 */
	public void setSenha(String string) {
		senha = string;
	}

	/**
	 * @return
	 */
	public Date getData_Cotacao() {
		return data_Cotacao;
	}

	/**
	 * @param date
	 */
	public void setData_Cotacao(Date date) {
		data_Cotacao = date;
	}

	public void setNomMotivo(String i) {
		this.nomMotivo = i;
	}

	/**
	 * @return
	 */
	public String getNomMotivo() {
		return nomMotivo;
	}

	/**
	 * @return
	 */
	public String getRefSyas() {
		return refSyas;
	}

	/**
	 * @return
	 */
	public String getTecnico() {
		return tecnico;
	}

	/**
	 * @return
	 */
	public String getUserAnuencia() {
		return userAnuencia;
	}

	/**
	 * @param string
	 */
	public void setRefSyas(String string) {
		refSyas = string;
	}

	/**
	 * @param string
	 */
	public void setTecnico(String string) {
		tecnico = string;
	}

	/**
	 * @param string
	 */
	public void setUserAnuencia(String string) {
		userAnuencia = string;
	}

	/**
	 * @return
	 */
	public double getDesconto() {
		return desconto;
	}

	/**
	 * @param d
	 */
	public void setDesconto(double d) {
		desconto = d;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public HashMap getArqsCotacao() {
		return arqsCotacao;
	}

	/**
	 * @return
	 */
	public String getCotFinalData() {
		return cotFinalData;
	}

	/**
	 * @return
	 */
	public String[] getDados_html() {
		return dados_html;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public HashMap getDadosCotacao() {
		return dadosCotacao;
	}

	/**
	 * @param map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setArqsCotacao(HashMap map) {
		arqsCotacao = map;
	}

	/**
	 * @param string
	 */
	public void setCotFinalData(String string) {
		cotFinalData = string;
	}

	/**
	 * @param strings
	 */
	public void setDados_html(String[] strings) {
		dados_html = strings;
	}

	/**
	 * @param map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setDadosCotacao(HashMap map) {
		dadosCotacao = map;
	}

	/**
	 * @return
	 */
	public double getPcomissao() {
		return pcomissao;
	}

	/**
	 * @return
	 */
	public double getPmargem() {
		return pmargem;
	}

	/**
	 * @return
	 */
	public double getPtaxa() {
		return ptaxa;
	}

	/**
	 * @return
	 */
	public double getVpremiototal() {
		return vpremiototal;
	}

	/**
	 * @param d
	 */
	public void setPcomissao(double d) {
		pcomissao = d;
	}

	/**
	 * @param d
	 */
	public void setPmargem(double d) {
		pmargem = d;
	}

	/**
	 * @param d
	 */
	public void setPtaxa(double d) {
		ptaxa = d;
	}

	/**
	 * @param d
	 */
	public void setVpremiototal(double d) {
		vpremiototal = d;
	}

	/**
	 * @return
	 */
	public String getInfoTecnica() {
		return infoTecnica;
	}

	/**
	 * @param string
	 */
	public void setInfoTecnica(String string) {
		infoTecnica = string;
	}

	/**
	 * @return
	 */
	public int getAltopotrisco() {
		return altopotrisco;
	}

	/**
	 * @return
	 */
	public int getDeclinado() {
		return declinado;
	}

	/**
	 * @return
	 */
	public int getRecusadoirb() {
		return recusadoirb;
	}

	/**
	 * @param i
	 */
	public void setAltopotrisco(int i) {
		altopotrisco = i;
	}

	/**
	 * @param i
	 */
	public void setDeclinado(int i) {
		declinado = i;
	}

	/**
	 * @param i
	 */
	public void setRecusadoirb(int i) {
		recusadoirb = i;
	}

	/**
	 * @return
	 */
	public int getCondinspec() {
		return condinspec;
	}

	/**
	 * @param i
	 */
	public void setCondinspec(int i) {
		condinspec = i;
	}

	/**
	 * @return
	 */
	public int getAprovairb() {
		return aprovairb;
	}

	/**
	 * @return
	 */
	public String getBeneficiario() {
		return beneficiario;
	}

	/**
	 * @return
	 */
	public int getClausulabenef() {
		return clausulabenef;
	}

	/**
	 * @return
	 */
	public int getCobertura() {
		return cobertura;
	}

	/**
	 * @return
	 */
	public int getEspeciais() {
		return especiais;
	}

	/**
	 * @return
	 */
	public int getEspecificacao() {
		return especificacao;
	}

	/**
	 * @return
	 */
	public int getFacultativo() {
		return facultativo;
	}

	/**
	 * @return
	 */
	public int getInspecao() {
		return inspecao;
	}

	/**
	 * @return
	 */
	public int getLmi() {
		return lmi;
	}

	/**
	 * @return
	 */
	public int getMeses() {
		return meses;
	}

	/**
	 * @return
	 */
	public int getPeriodo() {
		return periodo;
	}

	/**
	 * @param i
	 */
	public void setAprovairb(int i) {
		aprovairb = i;
	}

	/**
	 * @param string
	 */
	public void setBeneficiario(String string) {
		beneficiario = string;
	}

	/**
	 * @param i
	 */
	public void setClausulabenef(int i) {
		clausulabenef = i;
	}

	/**
	 * @param i
	 */
	public void setCobertura(int i) {
		cobertura = i;
	}

	/**
	 * @param i
	 */
	public void setEspeciais(int i) {
		especiais = i;
	}

	/**
	 * @param i
	 */
	public void setEspecificacao(int i) {
		especificacao = i;
	}

	/**
	 * @param i
	 */
	public void setFacultativo(int i) {
		facultativo = i;
	}

	/**
	 * @param i
	 */
	public void setInspecao(int i) {
		inspecao = i;
	}

	/**
	 * @param i
	 */
	public void setLmi(int i) {
		lmi = i;
	}

	/**
	 * @param i
	 */
	public void setMeses(int i) {
		meses = i;
	}

	/**
	 * @param i
	 */
	public void setPeriodo(int i) {
		periodo = i;
	}

	/*
	 * public void setTpMotivo(String string) { if (string != null &&
	 * !string.equals("")) { tpMotivo = string; Hashtable l =
	 * DbAccess.recuperaListaMotivo(); //Recupera nome do ramo setNomMotivo((String)
	 * l.get(String.valueOf(string))); }else{ this.tpMotivo = string; } }
	 */

	/**
	 * @return
	 */
	public int getCodMotivo() {
		return codMotivo;
	}

	/**
	 * @param string
	 */
	public void setCodMotivo(int i) {
		codMotivo = i;
	}

	public String getCodCliente() {
		if (codCliente == null || "".equals(codCliente)) {
			return "0";
		}
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getDataVigenciaInicio() {
		return dataVigenciaInicio;
	}

	public String getDataVigenciaInicioFormatada() {
		StringBuilder formatada = new StringBuilder("");
		if (!"0".equalsIgnoreCase(this.dataVigenciaInicio) && !"".equalsIgnoreCase(this.dataVigenciaInicio)) {
			formatada.append(this.dataVigenciaInicio.substring(6, 8));
			formatada.append("/");
			formatada.append(this.dataVigenciaInicio.substring(4, 6));
			formatada.append("/");
			formatada.append(this.dataVigenciaInicio.substring(0, 4));
		}
		return formatada.toString();
	}

	public void setDataVigenciaInicio(String dataVigenciaInicio) {
		this.dataVigenciaInicio = dataVigenciaInicio;
	}

	public String getDataVigenciaFim() {
		return dataVigenciaFim;
	}

	public String getDataVigenciaFimFormatada() {
		StringBuilder formatada = new StringBuilder("");
		if (!"0".equalsIgnoreCase(this.dataVigenciaFim) && !"".equalsIgnoreCase(this.dataVigenciaFim)) {
			formatada.append(this.dataVigenciaFim.substring(6, 8));
			formatada.append("/");
			formatada.append(this.dataVigenciaFim.substring(4, 6));
			formatada.append("/");
			formatada.append(this.dataVigenciaFim.substring(0, 4));
		}
		return formatada.toString();
	}

	public void setDataVigenciaFim(String dataVigenciaFim) {
		this.dataVigenciaFim = dataVigenciaFim;
	}

	public int getLmiUnico() {
		return lmiUnico;
	}

	public void setLmiUnico(int lmiUnico) {
		this.lmiUnico = lmiUnico;
	}

	public void setLmiUnico(String param) {
		if ("on".equalsIgnoreCase(param)) {
			this.setLmiUnico(1);
		} else {
			this.setLmiUnico(0);
		}
	}

	public String getLmiUnicoTela() {
		StringBuilder ret = new StringBuilder("");

		if (this.getLmiUnico() == 1) {
			ret.append("Sim");
		} else {
			ret.append("Não");
		}

		return ret.toString();
	}

	public String getProfissaoRamoAtividade() {
		return profissaoRamoAtividade;
	}

	public void setProfissaoRamoAtividade(String profissaoRamoAtividade) {
		this.profissaoRamoAtividade = profissaoRamoAtividade;
	}

	public String limpaExcessoNumerico(String numero) {
		return Integer.toString(Integer.parseInt(numero));
	}

	public String getNovaConsultaAnexos() {
		return novaConsultaAnexos;
	}

	public void setNovaConsultaAnexos(String novaConsultaAnexos) {
		this.novaConsultaAnexos = novaConsultaAnexos;
	}

	public String getProfissaoBanco() {
		String string = "null";
		if ("0".equalsIgnoreCase(this.getTipPessoa())) {
			string = "'" + this.getProfissaoRamoAtividade() + "'";
		}
		return string;
	}

	public String getRamoAtividadeBanco() {
		String string = "null";
		if ("1".equalsIgnoreCase(this.getTipPessoa())) {
			string = "'" + this.getProfissaoRamoAtividade() + "'";
		}
		return string;
	}

	public void setRamoAtividadeProfissaoBanco(String profissao, String ramoAtividade) {
		if ("1".equalsIgnoreCase(this.getTipPessoa().toString())) {
			this.setProfissaoRamoAtividade(ramoAtividade);
		} else {
			this.setProfissaoRamoAtividade(profissao);
		}
	}

	public String getUnidadeNegocioCodigoProdutor() {
		return unidadeNegocioCodigoProdutor;
	}

	public void setUnidadeNegocioCodigoProdutor(String unidadeNegocioCodigoProdutor) {
		this.unidadeNegocioCodigoProdutor = unidadeNegocioCodigoProdutor;
	}

	public String getCodigoHierarquico() {
		return codigoHierarquico;
	}

	public void setCodigoHierarquico(String codigoHierarquico) {
		this.codigoHierarquico = codigoHierarquico;
	}

	public String getCodigoFilial() {
		return codigoFilial;
	}

	public void setCodigoFilial(String codigoFilial) {
		this.codigoFilial = codigoFilial;
	}

}
