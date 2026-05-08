import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.lumina.cripto.DataEncryption;

import bean.Cep;
import bean.Corretor;
import bean.CotacaoBean;
import bean.Cto112Bean;
import bean.Cto113Bean;
import bean.Cto114Bean;
import bean.Cto1501Bean;
import bean.Cto1502Bean;
import bean.Cto1503Bean;
import bean.Cto5101Bean;
import bean.Cto5102Bean;
import bean.Cto5103Bean;
import bean.Cto5104Bean;
import bean.Cto5105Bean;
import bean.Cto5106Bean;
import bean.Cto5107Bean;
import bean.Cto5108Bean;
import bean.Cto5109Bean;
import bean.Cto7101Bean;
import bean.Cto7102Bean;
import bean.Cto7103Bean;
import bean.Cto7104Bean;
import bean.Cto7105Bean;
import bean.Cto7106Bean;
import bean.Cto7107Bean;
import bean.Cto7108Bean;
import bean.Cto7109Bean;
import bean.DepartamentoBean;
import bean.Dominio;
import bean.Mensagem;
import bean.Modalidade;
import bean.Produtor;
import bean.Ramo;
import bean.UnidadeBean;
import br.com.sompo.cotacaomulti.dto.ExcecaoCorretorDTO;
import br.com.sompo.cotacaomulti.dto.RamoExcecaoDTO;
import br.com.sompo.cotacaomulti.exception.SusepHistoricoException;
import br.yasuda.control.Action;
import br.yasuda.control.ResolveAction;
import br.yasuda.exception.UploadFileNameRepeatException;
import br.yasuda.exception.UploadFileTypeException;
import db.DbAccess;
import http.utils.multipartrequest.MultipartRequest;
import http.utils.multipartrequest.ServletMultipartRequest;
import util.*;

/**
 * @version 1.0
 * @author
 */

    public class Controller extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 4336596189540501252L;
	
	private static final Logger LOGGER = Logger.getLogger(Controller.class);

	private final String DIVDET = "MSDet.jsp";
	private final String DIVCAD = "MSCad.jsp";
	private final String MENU_PESQ_JSP = "MenuPesq.jsp";
	private final String PESQ_LISTA_JSP = "DivPesqLista.jsp";

	// private final int TIPO_AUTO = 1;
	// private final int TIPO_DIVERSOS = 2;

	@SuppressWarnings({ "unchecked", "finally", "rawtypes" })
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.info("PORTAL_CORRETOR - Entrou controller.service");
		int valPosicao = 0;
		try {
			CotacaoBean cot = (CotacaoBean) req.getSession().getAttribute("CotacaoBean");
			
			valPosicao = 1;
			
			String action = req.getParameter("action");
			
			valPosicao = 2;
			
			if (action == null)
				action = "";
			
			valPosicao = 3;
			
			//PORTAL_CORRETOR
			String numSusep = obterSusepRequest(req);
			
			valPosicao = 4;
			
			numSusep = StringUtils.isEmpty(StringUtils.trim(numSusep)) ? obterSusepSession(req)
					: StringUtils.trim(numSusep);
			LOGGER.info("PORTAL_CORRETOR - num_susep_completo >>> " + numSusep);
			LOGGER.info("action: " + action);
					
			if(StringUtils.isEmpty(numSusep)) {
				numSusep = (String) req.getParameter("u");
			}
					
			CotacaoMultiProperties.VerificaEstiloSompo(req);

			String codCorretor = (String) req.getParameter("codCorr");
			if (codCorretor == null) {
				if (req.getSession().getAttribute("codCorr") == null)
					codCorretor = "0";
				else
					codCorretor = (String) req.getSession().getAttribute("codCorr");
			} else {
				req.getSession().setAttribute("codCorr", codCorretor);
			}

			String codCliente = (String) req.getParameter("codCliente");
			if (codCliente == null) {
				codCliente = (String) req.getSession().getAttribute("codCliente");
			} else {
				req.getSession().setAttribute("codCliente", codCliente);
			}

			String login = (String) req.getParameter("login");
			if (login == null)
				login = "";
			String intranet = "0";
			intranet = req.getParameter("intranet");

			if (intranet == null)
				intranet = (String) req.getSession().getAttribute("intranet");
			else
				req.getSession().setAttribute("intranet", intranet);

			if (action.equalsIgnoreCase("consulta") == true) {
				login = "consulta";
				numSusep = "999999";
				intranet = "";
			}
			long codUnidade = 0;
			long codUnidadeReal = 0;
			if (intranet == null)
				intranet = "";
			if (intranet.equalsIgnoreCase("1") == true) {
				codUnidade = 0;
				
				//PORTAL_CORRETOR
				numSusep = obterSusepRequest(req);
				if (login.equals("")) {
					//PORTAL_CORRETOR
					numSusep = obterSusepSession(req);
					
					login = (String) req.getSession().getAttribute("login");
					codCorretor = (String) req.getSession().getAttribute("codCorretor");
					if (login == (null)) {
						req.setAttribute("mensagemConfirmacao_1", "Efetuar login novamente.");
						req.setAttribute("mostraBotoes", "false");
						req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
						return;
					}
					codUnidade = Long.parseLong((String) req.getSession().getAttribute("codUnidade"));
					codUnidadeReal = Long.parseLong((String) req.getSession().getAttribute("codUnidadeReal"));
					// Hebert

				} else {
					//PORTAL_CORRETOR
					setSusepSession(req, numSusep);
					
					req.getSession().setAttribute("login", login);
					req.getSession().setAttribute("codCorretor", codCorretor);
					// Verifico a qual unidade o usuario pertence.
					// Se for filial entao permitir listar somente da mesma filial
					// Caso contrario listar todos os casos
					// ********Acesso somente interno**********
					// codUnidade = DbAccess.verificaUnidade(login); Hebert
					long vCodUnidade[] = DbAccess.verificaUnidade(login);
					codUnidade = vCodUnidade[0];
					if (codUnidade == -1) {
						req.setAttribute("mensagemConfirmacao_1", "Usuário sem permissão de acesso");
						req.setAttribute("mostraBotoes", "false");
						req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
						return;
					}
					req.getSession().setAttribute("codUnidade", Long.toString(vCodUnidade[0]));
					req.getSession().setAttribute("arquivoCorrExec", "1");
					req.getSession().setAttribute("codUnidadeReal", Long.toString(vCodUnidade[1]));
				}
			} else {
				if ("Teste".equalsIgnoreCase(action)) {

					String c = req.getParameter("u");
					DataEncryption d = new DataEncryption();
					c = d.encrypt(c);

					req.setAttribute("action", req.getParameter("proxaction"));
					req.setAttribute("u", c);
					if ("1".equalsIgnoreCase(req.getParameter("simula")))
						req.setAttribute("intranet", req.getParameter("simula"));
					req.setAttribute("codCorr", codCorretor);
					req.setAttribute("codCliente", codCliente);
					req.getRequestDispatcher("teste.jsp").forward(req, resp);
					return;
				} else {

					if (numSusep == null) {
						//PORTAL_CORRETOR
						numSusep = obterSusepSession(req);
						
						login = (String) req.getSession().getAttribute("login");
						codCorretor = (String) req.getSession().getAttribute("codCorretor");
					} else {
						//PORTAL_CORRETOR
						setSusepSession(req, numSusep);
						
						req.getSession().setAttribute("login", login);
						req.getSession().setAttribute("codCorretor", codCorretor);
						DataEncryption d = new DataEncryption();

						try {
							//PORTAL_CORRETOR
							LOGGER.info("num_susep_completo: " + numSusep);
							numSusep = d.decrypt(numSusep).trim();
							setSusepSession(req, numSusep);
						} catch (Exception e) {
							//LOGGER.error(e.getMessage(), e);						
							//PORTAL_CORRETOR
							numSusep = obterSusepSession(req);
						}
					}
				}
			}

			// grava log
			if (login == null)
				login = "undefined";
			if (action.equalsIgnoreCase("consulta") == true) {
				intranet = "1";
			}
			//LogAplicativo.registraAction(login, "CotacaoMulti", action, req);
			req.setAttribute("intranet", intranet);
			if (action == "" && req.getContentType() != null) {
				if (req.getContentType().startsWith("multipart/form-data")) {
					LOGGER.info("[Controller] - (serivce) - [[action vazia]]: Iniciando a action contendo multipart/form-data  ");
					MultipartRequest parser = null;
					// Cria um MultiPart para efetuar o upload do arquivo
					try {
						parser = new ServletMultipartRequest(req, "/u/dadosapp/cotacao/tmp", // Diretorio
																						// de
																						// repositorio
																						// do
																						// arquivo
																						// temporario
								10 * 1024 * 1024, // 10MB 10*1024*1024
								MultipartRequest.ABORT_IF_MAX_BYES_EXCEEDED, null);

						LOGGER.info("[Controller] - (serivce) - [[action vazia]]: Montou o parser na action contendo multipart/form-data  ");
						action = parser.getURLParameter("action");
						LOGGER.info("[Controller] - (serivce) - [[action vazia]]: Encontrou a action no parser: ".concat(action));
						// Captura o valor da action
						if (!action.equals("delEmailFile")) {
							LOGGER.info("[Controller] - (serivce) - [[action vazia]]: Iniciando a acao encontrada no parser da action contendo multipart/form-data  ");
							// Verifica extensao do arquivo
							String fileName = parser.getBaseFilename("arqAnexo");
							LOGGER.info(
									"[Controller] - (serivce) - [[action vazia]]: Resgatando o nome do arquivo anexo na acao encontrada no parser da action contendo multipart/form-data: "
									.concat(fileName));
							fileName = fileName.toLowerCase();
							if (!fileName.endsWith(".pdf") && !fileName.endsWith(".doc") && !fileName.endsWith(".docx")
									&& !fileName.endsWith(".xlsx") && !fileName.endsWith(".xls")) {
								throw new UploadFileTypeException(req, resp, 1,
										"Permitidos para upload arquivos com as extensões: .doc ; .docx; .pdf; .xlsx; .xls.");
							}

							LOGGER.info("[Controller] - (serivce) - [[action vazia]]: Gerando o HashMap na action contendo multipart/form-data  ");
							HashMap arqs = cot.getArqsEmailCotacao();
							LOGGER.info("[Controller] - (serivce) - [[action vazia]]: Gerando o Iterator na action contendo multipart/form-data  ");
							Iterator it = arqs.keySet().iterator();

							LOGGER.info("[Controller] - (serivce) - [[action vazia]]: Iniciando o while com o Iterator na action contendo multipart/form-data  ");
							while (it.hasNext()) {
								String key = (String) it.next();
								HashMap tmp = (HashMap) arqs.get(key);

								if (fileName.equalsIgnoreCase((String) tmp.get("fileName"))) {
									throw new UploadFileNameRepeatException(req, resp, 1,
											"Não é permitido realizar upload de arquivos com o mesmo nome.");
								}
							}
							LOGGER.info("[Controller] - (serivce) - [[action vazia]]: Finalizando o while com o Iterator na action contendo multipart/form-data  ");

							// Armazena os dados dos arquivos anexados No Bean
							cot.setArqsEmailCotacao(0, parser.getFile("arqAnexo").getParent(), "",
									parser.getBaseFilename("arqAnexo"), parser.getFile("arqAnexo").getName(),
									String.valueOf(parser.getFileSize("arqAnexo")), "off", 0, cot.getArquivoCorrExec());

						}
					} catch (UploadFileTypeException e) {
						LOGGER.error(e.getMessage(), e);
						return;
					} catch (UploadFileNameRepeatException e) {
						LOGGER.error(e.getMessage(), e);
						return;
					} catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						if (e.getMessage().startsWith("Content length exceeded")) {
							req.setAttribute("erro", "O arquivo excedeu o limite (10 MB), não pode ser carregado!!");
						}

						if ("1".equalsIgnoreCase((String) req.getSession().getAttribute("outfile"))) {
							req.getRequestDispatcher("UploadFileNovaIdentidadeVisual.jsp").forward(req, resp);
						} else {
							req.getRequestDispatcher("UploadFile.jsp").forward(req, resp);
						}
						return;
					}
				}

			}

			// Hebert
			if (action.equals("ArquivoClassifica")) {
				LOGGER.info("[Controller] - (serivce) - [[ArquivoClassifica]]: Iniciando a action");
				PrintWriter out = resp.getWriter();
				String nome = req.getParameter("nome");
				String classifica = req.getParameter("classifica");
				if (nome != null && !nome.equals("")) {
					// List list = (List) req.getSession().getAttribute("arquivos");
					LOGGER.info("[Controller] - (serivce) - [[ArquivoClassifica]]: Gerando o HashMap");
					HashMap arqs = cot.getArqsEmailCotacao();
					LOGGER.info("[Controller] - (serivce) - [[ArquivoClassifica]]: Gerando o Iterator");
					Iterator it = arqs.keySet().iterator();
					LOGGER.info("[Controller] - (serivce) - [[ArquivoClassifica]]: Iniciando o while");
					while (it.hasNext()) {
						String key = (String) it.next();
						HashMap tmp = (HashMap) arqs.get(key);

						if (nome.equals(tmp.get("fileName"))) {
							tmp.put("classifica", classifica);
							break;
						}
					}
					// out.print("success");
					LOGGER.info("[Controller] - (serivce) - [[ArquivoClassifica]]: Finalizou o while");
					req.getRequestDispatcher("UploadFile.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[ArquivoClassifica]]: Finalizando a action completa");
					return;
				} else {
					out.print("Arquivo não pode ser classificado");
					LOGGER.info("[Controller] - (serivce) - [[ArquivoClassifica]]: Finalizando a action sem classificar");
					return;
				}
			}
			// Hebert

			// Inicio Comissao Adicional
			/*
			 * int tipo_Adesao = 0; String tipoDesc = ""; if
			 * (action.equals("adesaoAuto")||action.equals("adesao")) { action =
			 * "adesao"; tipo_Adesao = TIPO_AUTO; } if (action.equals("adesaoDiv"))
			 * { action = "adesao"; tipo_Adesao = TIPO_DIVERSOS; }
			 * 
			 * if (action.equals("adesao")) { String msg =
			 * DbAccess.verificaAdesao(numSusep,tipo_Adesao); if (msg.equals("")) {
			 * req.getSession().setAttribute("tipoAdesao",
			 * String.valueOf(tipo_Adesao));
			 * req.getRequestDispatcher("Adesao.jsp").forward(req, resp); return; }
			 * else { req.setAttribute("msg", msg);
			 * req.getRequestDispatcher("MensagemAdesao.jsp").forward(req, resp);
			 * return; } } else { if (action.equals("confirmacao")) {
			 * req.getRequestDispatcher("ConfirmaAdesao.jsp").forward(req, resp);
			 * return; } else { if (action.equals("protocolo")) { tipo_Adesao =
			 * Integer
			 * .parseInt((String)req.getSession().getAttribute("tipoAdesao"));
			 * if(tipo_Adesao == 1)tipoDesc="Automovel"; if(tipo_Adesao ==
			 * 2)tipoDesc="Ramos Diversos"; long numProtocolo =
			 * DbAccess.getNextProtocolo(tipo_Adesao); String msg =
			 * DbAccess.gravaAdesao(numSusep, login, numProtocolo,tipo_Adesao); if
			 * (msg.equals("")) { Calendar c = Calendar.getInstance(new Locale("pt",
			 * "br")); int ano = c.get(Calendar.YEAR); SimpleDateFormat df = new
			 * SimpleDateFormat("dd/MM/yyyy"); req.setAttribute( "confirm",
			 * "Esta e uma confirmacao que sua opcao de adesao ao Programa de <br> Comissao Adicional de "
			 * + tipoDesc + " de " + ano + " foi enviada atraves do usuario <br>" +
			 * login + " e recebida em " + df.format(c.getTime()) +
			 * ", sob o protocolo  no. " + numProtocolo + ".");
			 * req.getRequestDispatcher("ProtocoloAdesao.jsp").forward(req, resp);
			 * return; } else { req.setAttribute("msg", msg);
			 * req.getRequestDispatcher("MensagemAdesao.jsp").forward(req, resp);
			 * return; } } } }
			 */
			// Fim Comissao Adicional

			if (action.equals("buscacorretor")) {

				PrintWriter out = resp.getWriter();

				String paramSelect = req.getParameter("select");

				boolean onSelect = "s".equals(paramSelect);

				String prefix = req.getParameter("q").equals("") ? " " : req.getParameter("q");

				List<?> returnData = new ArrayList<Object>();

				try {
					if (Character.isDigit(prefix.charAt(0))) {
						// busca pelo codigo ou parte
						returnData = DbAccess.ListaCorretores(prefix, "", "999999", codUnidade, onSelect);
					} else {
						// busca pelo nome ou parte
						returnData = DbAccess.ListaCorretores("", prefix, "999999", codUnidade, onSelect);
					}

				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					req.setAttribute("mensagemConfirmacao_1", "Corretores não encontrados");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					return;
				}

				int pagina = Integer.parseInt(req.getParameter("p") == null ? "" : req.getParameter("p"));
				int size = Integer.parseInt(req.getParameter("s") == null ? "" : req.getParameter("s"));

				int total = 0;
				List<String> results = new ArrayList<String>();

				// StringBuilder sb = new StringBuilder();
				String sb = "{\"results\":";
				// sb.append("{\"results\":");

				for (Iterator<?> iterator = returnData.iterator(); iterator.hasNext();) {
					Corretor object = (Corretor) iterator.next();
					String corretor = object.getCorretor();
					codCorretor = object.getCodCorretor();
					// definicoes de int ou string para efetuar a pesquisa
					// conforme a digitacao

					if (corretor.trim().length() > 41) {
						corretor = corretor.trim().substring(0, 40) + "...";
					}

					String json = "{" + "\"id\":\"" + codCorretor.trim() + "\"," + "\"name\":\"" + corretor.trim() + "\" " +

							"}";
					results.add(json);

				}
				total = results.size();
				Object[] resultsArray = results.toArray();
				List<Object> pagedResults = new ArrayList<Object>();

				if (size > 0) {
					int start = (pagina - 1) * size + 1;
					int end = (start > (total - size)) ? total : start + size - 1;
					for (int i = start - 1; i < end; i++) {
						pagedResults.add(resultsArray[i]);
					}
				}

				if (pagedResults.size() > 0) {
					// sb.append(pagedResults);
					sb += pagedResults;
				} else if (resultsArray.length > 0) {
					// sb.append(resultsArray);
					sb += resultsArray;
				}

				// sb.append(",\"total\":\"" + total + "\"}");
				sb += ",\"total\":\"" + total + "\"}";
				// System.out.print(sb.toString());

				out.print(sb);

				results.clear();
				pagedResults.clear();
				resultsArray = null;

				out.flush();
				out.close();

			} else if (action.equalsIgnoreCase("menucad") || action.equalsIgnoreCase("menucadrc")) {

				req.getSession().removeAttribute("outfile");
				
				LOGGER.info("action menucad -> susep: " + numSusep);

				//NOVA_SUSEP
				HashMap listaCorretor = null; 
				try {
					listaCorretor = DbAccess.recuperaListaCorretores(numSusep, codUnidade, codCorretor);
				} catch(SusepHistoricoException e) {
					LOGGER.error("Erro ao consultar suseps", e);
					throw new ServletException("Erro ao consultar suseps: " + e.getCause());
				}

				
				req.setAttribute("lstCorretor", listaCorretor);

				//JIRA-168467
				req.setAttribute("TpEmissao", TipoEmissao.getTiposEmissao()); 
				
				//JIRA-110924
				req.setAttribute("RamosDiv", DbAccess.RAMO_DIVERSOS);
				
				req.setAttribute("codUnidade", Long.toString(codUnidade));
				String codCorr = "0";
				if (listaCorretor.size() == 1) {
					codCorr = DbAccess.getCodCorrUnico();
					req.setAttribute("CodCorrVolta", codCorr);
				}
				
				LOGGER.info(">>> INICIO CHAMADA LISTA UNIDADES PONTO 1");
				HashMap unidades = DbAccess.recuperaListaUnidades(codCorr, codUnidade);
				if (!"1".equalsIgnoreCase((String) req.getSession().getAttribute("intranet"))) {
					LOGGER.info("IF INTRANET");
					String corrLimpo = Integer.toString(Integer.parseInt(codCorr.trim()));
					LOGGER.info("corrLimpo: " + corrLimpo);
					unidades = new Util().removeItensMap(Util.getUnidadesRestritasSiscotaMulti(corrLimpo), unidades);
					LOGGER.info("unidades: " + unidades.size());
				}
				LOGGER.info("<<< FIM CHAMADA LISTA UNIDADES PONTO 1");
				req.getSession().setAttribute("lstUnidade", unidades);
				req.setAttribute("lstUnidade", unidades);
				req.getSession().setAttribute("parameterTeste", "testado");
				
				
				
				if (req.getParameter("vMenuCad") == null) {
					cot = null;
				}
				if (cot != null) {
					req.setAttribute("CodCorrVolta", cot.getCodCorr());
					String ramos[] = DbAccess.RAMO_DIVERSOS;
					for (int i = 1; i < ramos.length; i++) {
						if (String.valueOf(cot.getCodRamo()).equals(ramos[i].substring(0, 3).trim())) {
							req.setAttribute("CodRamoVolta", ramos[i]);
							break;
						}
					}
					req.setAttribute("CodModalidadeVolta", cot.getCodModalidade());
					req.setAttribute("TipEmissaoVolta", String.valueOf(cot.getTipEmissao()));
					req.setAttribute("NumApolVolta", cot.getNumApol());
					req.setAttribute("TpCotacaoVolta", cot.getTpCotacao());
					req.setAttribute("NumProtocoloVolta", cot.getNumProtocolo());
					cot = null;
				}
				
				req.getRequestDispatcher("MenuCad.jsp").forward(req, resp);
				return;

			} else if (action.equalsIgnoreCase("acessprop")) {
				//CotacaoMulti/Controller?action=acessprop&file=_dados_config_CotacaoMulti_config.properties&atr=trava.portal.rcprof&val=true&com=alterado_20170817&mod=write
				//CotacaoMulti/Controller?action=acessprop&file=_dados_config_CotacaoMulti_config.properties&atr=trava.portal.rcprof&com=alterado_20170817&mod=remove
				//CotacaoMulti/Controller?action=acessprop&file=_dados_config_CotacaoMulti_config.properties&mod=read
							
				String properties = req.getParameter("file");
				String attribute = req.getParameter("atr"); 
				String value = req.getParameter("val");
				String comments = req.getParameter("com");
				String mod = req.getParameter("mod");
				properties = properties.replace("_", "/");
						
				if("read".equals(mod)){
					Util.readProperties(req, resp, properties);
				}
				else if("write".equals(mod)){
					Util.writeProperties(req, resp, properties, attribute, value, comments);
				}
				else if("remove".equals(mod)){
					Util.removeProperties(req, resp, properties, attribute, comments);
				}
				else{
					Util.getRequestDispatcherMesagem(req, resp, "mostraBotoes", "mensagemConfirmacao_1", "false", "Parametros Incorretos!");
				}
				return;
				
			} else if (action.equalsIgnoreCase("recotacaoRcAjax")) {

				String json = null;
				Mensagem msg = new Mensagem();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				PrintWriter out = resp.getWriter();
				Gson gson = new Gson();
				cot = new CotacaoBean();

				String codRamo = "0";
				String codMod = "00";

				String numProt = req.getParameter("cCotacao");
				if (numProt == null)
					numProt = "";

				String[] ex = DbAccess.verificarExiste(numProt, numSusep, codCorretor);
				// verifica a existencia da recotacao
				if (ex.length > 0 && ex[0] != null) {
					if (Integer.parseInt(ex[3]) < Integer.parseInt(adicionaDiasDataAtual(-30))) {
						msg.setDescricao(
								"A recotação ultrapassa 30 dias da última cotação. Favor solicitar uma nova cotação.");
						msg.setFlag("mensagemConfirmacao_1");
						json = gson.toJson(msg);
						out.print(json);
						out.flush();
						return;
					}

					codRamo = ex[0];
					codMod = ex[1];
				} else {
					msg.setDescricao("Nº de recotação inexistente " + numProt);
					msg.setFlag("mensagemConfirmacao_1");
					json = gson.toJson(msg);
					out.print(json);
					out.flush();
					return;
				}

				cot = selectClass(codRamo, codMod, cot);
				// Instancia da Class correta(Bean)

				cot.setNumProtocolo(numProt);
				cot.setNumSusep(numSusep);
				cot.setCodUser(login);

				// /Recupera Dados

				//NOVA_SUSEP
				try {
					DbAccess.recuperaDadosCotacao(cot, codUnidade);
				} catch(SusepHistoricoException e) {
					LOGGER.error("Erro ao consultar suseps", e);
					throw new ServletException(e.getMessage());
				}

				// Valida cotacao (caso codprodutor seja null entao cotacao original e invalida)
				if (cot.getCodProdutor() == null || cot.getCodProdutor() == "") {
					msg.setDescricao("Cotação não localizada");
					msg.setFlag("mensagemConfirmacao_1");
					json = gson.toJson(msg);
					out.print(json);
					out.flush();
					return;
				}
				DbAccess.recuperaDadosSegurado(cot);
				cot.setCodCorr(cot.limpaExcessoNumerico(cot.getCodCorr()));
				cot.setTipEmissao("0" + cot.getTipEmissao());

				json = gson.toJson(cot);
				out.print(json);
				out.flush();
				return;

			} else if (action.equalsIgnoreCase("buscaUnidadeAjax")) {

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				String codCorr = req.getParameter("cCorretor");
				PrintWriter out = resp.getWriter();
				Gson gson = new Gson();

				List<UnidadeBean> unidades = new DbAccess().recuperaListaUnidadeBean(codCorr, codUnidade);
				if (!"1".equalsIgnoreCase((String) req.getSession().getAttribute("intranet"))) {
					String corrLimpo = Integer.toString(Integer.parseInt(codCorr.trim()));
					unidades = (List<UnidadeBean>) new Util()
							.removeItensListDominio(Util.getUnidadesRestritasSiscotaMulti(corrLimpo), unidades);
				}

				String json = gson.toJson(unidades);
				out.print(json);
				out.flush();
				return;

			} else if (action.equalsIgnoreCase("buscaProfissaoRamoAtividadeAjax")) {

				try {

					DbAccess dbQuery = new DbAccess();
					List<Dominio> profissoesRamosAtividade;
					String tpPessoa = req.getParameter("tipoPessoa");
					resp.setContentType("application/json");
					resp.setCharacterEncoding("UTF-8");
					PrintWriter out = resp.getWriter();
					Gson gson = new Gson();

					if ("1".equalsIgnoreCase(tpPessoa)) {
						profissoesRamosAtividade = dbQuery.recuperaListaRamosAtividades(); // Lista
																							// de
																							// ramos
																							// de
																							// atividades
																							// para
																							// bean
					} else {
						profissoesRamosAtividade = dbQuery.recuperaListaProfissoes(); // Lista
																						// de
																						// profissoes
																						// para
																						// bean
					}

					String json = gson.toJson(profissoesRamosAtividade);
					out.print(json);
					out.flush();

				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}

				return;

			} else if (action.equalsIgnoreCase("buscaModalidadeAjax")) {

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				String ramo = req.getParameter("ramo");
				PrintWriter out = resp.getWriter();
				Gson gson = new Gson();

				List<Modalidade> modalidades = new DbAccess().recuperaListaModalidadeBean(Integer.parseInt(ramo));
				Collections.sort(modalidades);
				String json = gson.toJson(modalidades);
				out.print(json);
				out.flush();
				return;

			} else if (action.equalsIgnoreCase("buscaTpEmissaoBloqueadasAjax")) {

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				PrintWriter out = resp.getWriter();
				Gson gson = new Gson();
				String corretor = req.getParameter("corretor");
				
				if (corretor == null)
					corretor = "";
				String ramo = req.getParameter("ramo");
				if (ramo == null)
					ramo = "";
				
	            ArrayList<Integer> restricoes=new ArrayList<Integer>();
	            
	            if ((corretor.length()>0)&& (ramo.length()>1)) {
	            	Long codC=Long.parseLong(corretor.trim().substring(0,7));
	            	Integer codR=Integer.parseInt(ramo.trim().substring(0,3));
	            	
	            	if(!codR.equals(112)) {
	            		LOGGER.info("buscaTpEmissaoBloqueadasAjax: Não é um ramo 112");
	            		try {
	    	            	restricoes= DbAccess.recuperaRestricoes(codC, codR);
	    					} catch (Exception e) {
	    						LOGGER.error(e.getMessage(), e);
	    					}
	            	}
	            	else {
	            		restricoes = null;
	            	}

	            }
				List<String> listaem= new ArrayList<String>();
				if(restricoes != null) {
					for (int i=0;i<restricoes.size();i++){
						if (restricoes.get(i).intValue()==1) listaem.add("Seguro Novo");
						if (restricoes.get(i).intValue()==2) listaem.add("Renov Congênere");
						if (restricoes.get(i).intValue()==3) listaem.add("Renov Sompo");
						if (restricoes.get(i).intValue()==4) listaem.add("Endosso");
					}	
				}
				
				//Collections.sort(listaem);
				String json = gson.toJson(listaem);
				out.print(json);
				out.flush();
				return;

				
			} else if (action.equalsIgnoreCase("cepAjax")) {

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				String cep = req.getParameter("numCEP");
				PrintWriter out = resp.getWriter();
				Gson gson = new Gson();

				HashMap dadosCep = new HashMap();
				dadosCep = DbAccess.verificaCep(Integer.parseInt(cep));
				Cep cepBean = new Cep();
				cepBean.toBean(dadosCep);
				String json = gson.toJson(cepBean);
				out.print(json);
				out.flush();
				return;

			} else if (action.equalsIgnoreCase("buscaUnidade")) {
				String codCorr = req.getParameter("cCorretor");
				req.setAttribute("corretor_input", codCorr);
				req.setAttribute("cCorretor", codCorr);
				if (codCorr == null || codCorr.equals("")) {
					codCorr = "";
					req.setAttribute("mensagemConfirmacao_1", "Corretor não localizado");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					return;
				} else {
					codCorr = codCorr.substring(0, 7).trim();
					req.setAttribute("CodCorrVolta", codCorr);
					req.setAttribute("CodUnidVolta", Long.toString(codUnidade));

					//JIRA-168467
					req.setAttribute("TpEmissao", TipoEmissao.getTiposEmissao()); 
					
					//JIRA-110924
					req.setAttribute("RamosDiv", DbAccess.RAMO_DIVERSOS);
					
					//NOVA_SUSEP
					try {
						req.setAttribute("lstCorretor", DbAccess.recuperaListaCorretores(numSusep, codUnidade, codCorretor));
					} catch(SusepHistoricoException e) {
						LOGGER.error("Erro ao consultar suseps", e);
						throw new ServletException(e.getMessage());
					}
					
					LOGGER.info(">>> INICIO CHAMADA LISTA UNIDADES PONTO 2");
					HashMap unidades = DbAccess.recuperaListaUnidades(codCorr, codUnidade);
					if (!"1".equalsIgnoreCase((String) req.getSession().getAttribute("intranet"))) {
						LOGGER.info("IF INTRANET");
						String corrLimpo = Integer.toString(Integer.parseInt(codCorr.trim()));
						LOGGER.info("corrLimpo: " + corrLimpo);
						unidades = new Util().removeItensMap(Util.getUnidadesRestritasSiscotaMulti(corrLimpo), unidades);
						LOGGER.info("unidades: " + unidades.size());
					}
					LOGGER.info("<<< FIM CHAMADA LISTA UNIDADES PONTO 2");
					req.getSession().setAttribute("lstUnidade", unidades);
					req.setAttribute("lstUnidade", unidades);
					req.getSession().setAttribute("parameterTeste", "testado");
					
					
					req.setAttribute("num_susep_completo", numSusep);
					if (req.getParameter("vMenuCad") == null) {
						cot = null;
					}
					if (cot != null) {
						req.setAttribute("CodModalidadeVolta", cot.getCodModalidade());
						req.setAttribute("TipEmissaoVolta", String.valueOf(cot.getTipEmissao()));
						req.setAttribute("NumApolVolta", cot.getNumApol());
						req.setAttribute("TpCotacaoVolta", cot.getTpCotacao());
						req.setAttribute("NumProtocoloVolta", cot.getNumProtocolo());
						cot = null;
					}
					req.setAttribute("intranet", intranet);
					req.setAttribute("codUnidade", Long.toString(codUnidade));
					if (intranet.equalsIgnoreCase("1") == true && codUnidade != 2531) {
						req.setAttribute("CodCorrVolta", req.getParameter("cCorretor"));
					} else {
						req.setAttribute("CodCorrVolta", codCorr);
					}

					req.getRequestDispatcher("MenuCad.jsp").forward(req, resp);
					return;
				}

				// hebert
			} else if (action.equals("buscaProdutor")) {
				String dataInicio = req.getParameter("dataInicio");
				String dataFim = req.getParameter("dataFim");
				String departamento = req.getParameter("cDepartamento");
				String corretor = req.getParameter("cCorretor");
				if (corretor != null && !corretor.equals("")) {
					corretor = corretor.substring(0, 7);
				}
				String ramo = req.getParameter("cRamo");
				String produtor = req.getParameter("cProdutor");

				req.setAttribute("intranet", intranet);
				
				//NOVA_SUSEP
				HashMap listaCorretor = null;
				try {
					listaCorretor = DbAccess.recuperaListaCorretores(numSusep, codUnidade, codCorretor);
				} catch(SusepHistoricoException e) {
					LOGGER.error("Erro ao consultar suseps", e);
					throw new ServletException(e.getMessage());
				}

				req.setAttribute("lstCorretor", listaCorretor);
				req.setAttribute("lstDepartamento", DbAccess.recuperaListaDepartamento(Long.toString(codUnidade)));
				// hebert
				req.setAttribute("lstProdutor", DbAccess.recuperaListaProdutor(departamento, 2));
				
				
				//JIRA-168467
				req.setAttribute("TpEmissao", TipoEmissao.getTiposEmissao()); 
				
				//JIRA-110924
				req.setAttribute("RamosDiv", DbAccess.RAMO_DIVERSOS);
				
				req.setAttribute("codUnidade", Long.toString(codUnidade));
				String codCorr = "0";
				if (listaCorretor.size() == 1) {
					codCorr = DbAccess.getCodCorrUnico();
					req.setAttribute("CodCorrVolta", codCorr);
				}

				req.setAttribute("CodCorrVolta", corretor);
				req.setAttribute("CodDeparVolta", departamento);
				req.setAttribute("CodRamoVolta", ramo);
				req.setAttribute("CodProdVolta", produtor);
				req.setAttribute("dataFimVolta", dataFim);
				req.setAttribute("dataInicioVolta", dataInicio);
				
				setMenuPesqCorretor(req,numSusep, codUnidade, codCorr,  intranet, action);
				setMenuPesqRamo(req,numSusep, codUnidade, codCorr,  intranet, action);
				setMenuPesqDepto(req,numSusep, codUnidade, codCorr,  intranet, action);
				setMenuPesqProdutor(req,numSusep, codUnidade, codCorr,  intranet, action);

				/*
				 * if (req.getParameter("vMenuCad") == null) { cot = null; }
				 * 
				 * if (cot != null) { req.setAttribute("CodCorrVolta",
				 * cot.getCodCorr()); String ramos[] = DbAccess.RAMO_DIVERSOS; for
				 * (int i = 1; i < ramos.length; i++) { if
				 * (String.valueOf(cot.getCodRamo()).equals(ramos[i].substring(0,
				 * 3).trim())) { req.setAttribute("CodRamoVolta", ramos[i]); break;
				 * } } req.setAttribute("CodModalidadeVolta",
				 * cot.getCodModalidade()); req.setAttribute("TipEmissaoVolta",
				 * String.valueOf(cot.getTipEmissao()));
				 * req.setAttribute("NumApolVolta", cot.getNumApol());
				 * req.setAttribute("TpCotacaoVolta", cot.getTpCotacao());
				 * req.setAttribute("NumProtocoloVolta", cot.getNumProtocolo()); cot
				 * = null; }
				 */
				
				req.getRequestDispatcher(MENU_PESQ_JSP).forward(req, resp);
				return;
			} else if (action.equals("cadcotacao")) {

				LOGGER.info("> action: cadcotacao ");
				req.getSession().setAttribute("dataLimteVigenciaEndosso", false);

				String numProt = req.getParameter("cCotacao");
				if (numProt == null)
					numProt = "";

				String codRamo = req.getParameter("codRamo");
				if (codRamo == null)
					codRamo = "0";

				String codMod = req.getParameter("codMod");
				if (codMod == null)
					codMod = "00";
				
				LOGGER.info("> numProt: " + numProt);
				LOGGER.info("> codRamo: " + codRamo);
				

				//Caso sejam Ramos de Responsabilidade Civil ou Ricos de Engenharia,
				//obtem o codigo da modalidade que esta na descricao da mesma ao inves
				//do index do combo
				if(codRamo.startsWith("510") || codRamo.startsWith("670")) {
					codMod = req.getParameter("codigoModalidade");
				}
				LOGGER.info("> codMod: " + codMod);

				String tipEmissao = req.getParameter("TipEmissao");
				if (tipEmissao == null)
					tipEmissao = "0";
				LOGGER.info("> tipEmissao: " + tipEmissao);
				
				
				
				String corretor = req.getParameter("cCorretor");
				if (corretor == null)
					corretor = "";
				
				LOGGER.info("> corretor: " + corretor);

				cot = selectClass(codRamo, codMod, cot);
				LOGGER.info("> cot: " + cot);
				// Instancia da Class correta(Bean)

				String unidProd = req.getParameter("cUnidade");
				LOGGER.info("> unidProd: " + unidProd);
				
				String keyUnidadeProd = req.getParameter("cUnidade");
				LOGGER.info("> keyUnidadeProd: " + keyUnidadeProd);
				if (unidProd == null) {
					unidProd = "";
				} else {

					// esta implementacao tem que vir depois da atribuicao do objeto
					// "cot"

					String[] split = Util.split(unidProd, "-");
					LOGGER.info("> split: " + split);
					cot.setCodDepto(split[0]);
					cot.setCodProdutor(split[1]);				
					
					// devolvendo o formato original que eu modifiquei da rotina da
					// Thais
					unidProd = Util.zerosEsq(split[0], 4) + Util.zerosEsq(split[1], 4);
					LOGGER.info("> unidProd: " + unidProd);

					
					HashMap lstUnidadeMap = obterListaUnidades(req);
					LOGGER.info("> lstUnidadeMap: " + lstUnidadeMap);
					LOGGER.info("> parameterTeste: " + req.getSession().getAttribute("parameterTeste"));
					
					String nomeUnidade = (String) lstUnidadeMap.get(keyUnidadeProd);
					LOGGER.info("> nomeUnidade: " + nomeUnidade);
					
					String[] arrayNomUnidade = nomeUnidade.split("-");
					LOGGER.info("> arrayNomUnidade: " + arrayNomUnidade);
					cot.setNomDepto((arrayNomUnidade[1]).trim());

				}
				cot.setArquivoCorrExec(0);
				cot.setNumProtocolo(numProt);
				cot.setNumSusep(numSusep);
				cot.setCodUser(login);
				cot.setCodCorr(corretor.substring(0, 7).trim());
				cot.setNomCorr(corretor.substring(8, 20).trim());
				cot.setCodRamo(Integer.parseInt(codRamo.substring(0, 3).trim()));

				// cot.setTipEmissao(tipEmissao.substring(0, 2).trim());
				cot.setTipEmissao(this.getCodigoTipEmissao(tipEmissao == null ? "" : tipEmissao.trim()));
				cot.setCodModalidade(codMod);
				String ap = req.getParameter("numApol");
				if (ap == null)
					ap = "";
				cot.setNumApol(ap);
				cot.setTpCotacao(req.getParameter("tpCotacao"));
				cot.setCodCliente(codCliente);
				LOGGER.info("> cot: " + cot);

				if ((cot.getNumApol().equals("") == false) && (cot.getTpCotacao().trim().equals("1"))) {
					//NOVA_SUSEP
					try {
						LOGGER.info("> Inicio DbAccess.recuperaLocalRisco");
						DbAccess.recuperaLocalRisco(cot, numSusep);
						LOGGER.info("< Fim DbAccess.recuperaLocalRisco");
					} catch(SusepHistoricoException e) {
						LOGGER.error("Erro ao consultar suseps", e);
						throw new ServletException(e.getMessage());
					}
				}

				if ("03".equalsIgnoreCase(cot.getTipEmissao())) {
					LOGGER.info("> tipoEmissao: " + cot.getTipEmissao());
					LOGGER.info("> Inicio DbAccess.recuperaDataTerminoVigenciaApolice");
					String dataTerminoVigencia = DbAccess.recuperaDataTerminoVigenciaApolice(cot.getNumApol());
					LOGGER.info("> dataTerminoVigencia: " + dataTerminoVigencia);
					LOGGER.info("< Fim DbAccess.recuperaDataTerminoVigenciaApolice");
					if (!"".equalsIgnoreCase(dataTerminoVigencia) && !"0".equalsIgnoreCase(dataTerminoVigencia)) {
						req.getSession().setAttribute("dataLimteVigenciaEndosso", true);
						cot.setDataVigenciaFim(dataTerminoVigencia);
					}
				}

				try {
					cot.setData_Cotacao((new SimpleDateFormat("yyyyMMdd")).parse(Util.dataAtual()));
				} catch (ParseException e) {
					LOGGER.error(e.getMessage(), e);
				}

				//NOVA_SUSEP
				boolean flagCorretorEmpresarial = false;			
				try {
					LOGGER.info("> Inicio DbAccess().isPermissaoCorretor");
					flagCorretorEmpresarial = new DbAccess().isPermissaoCorretor(cot,
							AplicativoPermissao.CORRETOR_EMPRESARIAL.getAplicativo());
					LOGGER.info("< Fim DbAccess().isPermissaoCorretor");
				} catch(SusepHistoricoException e) {
					LOGGER.error("Erro ao consultar suseps", e);
					throw new ServletException(e.getMessage());
				}

				if ("1".equalsIgnoreCase(intranet)) {
					flagCorretorEmpresarial = true;
				}

				LOGGER.info("> B2B");
				// Parametros B2B
				if( !StringUtils.equalsIgnoreCase( req.getParameter("cod_hierarquico"), "null" ) ) {
					cot.setCodigoHierarquico( req.getParameter("cod_hierarquico") );
				}
				if( !StringUtils.equalsIgnoreCase( req.getParameter("cod_filial"), "null" ) ) {
					cot.setCodigoFilial( req.getParameter("cod_filial") );
				}
				LOGGER.info("< B2B");

				req.getSession().removeAttribute("fileList");
				req.getSession().removeAttribute("dadosCotacao");
				req.getSession().setAttribute("CotacaoBean", cot);
				req.getSession().setAttribute("TpMotivo", DbAccess.MOTIVO);
				req.getSession().setAttribute("flagCorretorEmpresarial", flagCorretorEmpresarial);
				req.getRequestDispatcher(DIVCAD).forward(req, resp);
				return;

			} else if (action.equals("cadrecotacao")) {

				req.getSession().setAttribute("dataLimteVigenciaEndosso", false);

				String codRamo = "0";
				String codMod = "00";

				String numProt = req.getParameter("cCotacao");
				if (numProt == null)
					numProt = "";

				String[] ex = DbAccess.verificarExiste(numProt, numSusep, codCorretor);
				// verifica a existencia da recotacao
				if (ex.length > 0 && ex[0] != null) {
					if (Integer.parseInt(ex[3]) < Integer.parseInt(adicionaDiasDataAtual(-30))) {
						req.setAttribute("mensagemConfirmacao_1",
								"A recotação ultrapassa 30 dias da última cotação.<BR>Favor solicitar uma nova cotação.");
						req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
						return;
					}

					codRamo = ex[0];
					codMod = ex[1];
				} else {
					req.setAttribute("mensagemConfirmacao_1", "Nº de recotação inexistente " + numProt);
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					return;
				}

				cot = selectClass(codRamo, codMod, cot);
				// Instancia da Class correta(Bean)

				cot.setNumProtocolo(numProt);
				cot.setNumSusep(numSusep);
				cot.setCodUser(login);

				// /Recupera Dados
				//NOVA_SUSEP
				try {
					DbAccess.recuperaDadosCotacao(cot, codUnidade);
				} catch(SusepHistoricoException e) {
					LOGGER.error("Erro ao consultar suseps", e);
					throw new ServletException(e.getMessage());
				}

				// Valida cotacao (caso codprodutor seja null entao cotacao original e invalida)
				if (cot.getCodProdutor() == null || cot.getCodProdutor() == "") {
					req.setAttribute("mensagemConfirmacao_1", "Cotação não localizada");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					return;
				}
				DbAccess.recuperaDadosSegurado(cot);

				cot.setTpCotacao(req.getParameter("tpCotacao"));

				// DbAccess.recuperaArqAnexos(cot, numProt);
				// req.getSession().setAttribute("fileList",
				// cot.getArqsEmailCotacao());
				req.getSession().setAttribute("visualizaTodosAnexos", DbAccess.liberaVisualizacaoTodosAnexos(login));
				req.getSession().setAttribute("CotacaoBean", cot);
				req.getRequestDispatcher(DIVCAD).forward(req, resp);
				return;

			} else if (action.equals("cadramo")) {

				recebeCampos(req, cot); // classe principal recebe dados

				try {
					AtualizaUnidadeProdutor(cot);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}

				req.setAttribute("consulta", "N");
				req.getRequestDispatcher(cot.getPaginaJSP()).forward(req, resp);
				return;
			} else if (action.equals("msdetalhe")) {

				cot.setDados(req); // set dados na classe especifica
				cot.setDadosHtml(); // dados da pagina de detalhe

				req.getSession().setAttribute("CotacaoBean", cot);
				req.setAttribute("consulta", "N");
				req.getRequestDispatcher(DIVDET).forward(req, resp);
				return;

			} else if (action.equals("consulta")) { // Consulta externa workflow
				// http://localhost:9080/CotacaoMulti/Controller?action=consulta&cCotacao=200603000010&numSusep=00059412

				req.setAttribute("entWorkflow", "1");
				
				List<Dominio> profissoesRamosAtividade = null;

				String numProt = req.getParameter("cCotacao");
				if (numProt == null)
					numProt = "";

				// sub escritor visualiza cotacoes das unidades
				/*
				 * String unidade = DbAccess.recuperaUnidadeCotacao(numProt);
				 * if(unidade == null){ unidade = "0"; }
				 */

				//PORTAL_CORRETOR
				if (intranet.equals("1")) {
					recuperaDetalhe(numProt, "999999", req, resp, cot, codUnidade);
				} else {
					recuperaDetalhe(numProt, StringUtils.trimToEmpty(req.getParameter("numSusep")), req, resp, cot,
							codUnidade);
				}
				
				req.getSession().setAttribute("visualizaTodosAnexos", DbAccess.liberaVisualizacaoTodosAnexos(login));
				cot = (CotacaoBean) req.getSession().getAttribute("CotacaoBean");

				try {

					if ("1".equalsIgnoreCase(cot.getTipPessoa())) {
						profissoesRamosAtividade = new DbAccess().recuperaListaRamosAtividades(); // Lista
																									// de
																									// ramos
																									// de
																									// atividade
																									// para
																									// bean

					} else {
						profissoesRamosAtividade = new DbAccess().recuperaListaProfissoes(); // Lista
																								// de
																								// profissoes
																								// para
																								// bean
					}

				} catch (Exception e) {
					LOGGER.error("CotacaoMulti action: " + action + " " + e.getMessage(), e);
				}

				if(req.getParameter("canal") != null && req.getParameter("canal").equals("BPM")){
					req.setAttribute("canal", "BPM");	
				}
				
				req.setAttribute("consulta", "");
				req.setAttribute("profissoesRamosAtividade", profissoesRamosAtividade);
				req.getSession().setAttribute("intranet", intranet);
				req.getSession().setAttribute("codUnidade", Long.toString(codUnidade));
				req.getSession().setAttribute("codUnidadeReal", Long.toString(codUnidadeReal));
				req.getRequestDispatcher(DIVDET).forward(req, resp);
				return;

			} else if (action.equals("msdetalhe2")) {

				List<Dominio> profissoesRamosAtividade = null;

				String numProt = req.getParameter("cCotacao");
				if (numProt == null)
					numProt = "";

				recuperaDetalhe(numProt, numSusep, req, resp, cot, codUnidade);
				req.getSession().setAttribute("visualizaTodosAnexos", DbAccess.liberaVisualizacaoTodosAnexos(login));
				cot = (CotacaoBean) req.getSession().getAttribute("CotacaoBean");

				try {

					if ("1".equalsIgnoreCase(cot.getTipPessoa())) {
						profissoesRamosAtividade = new DbAccess().recuperaListaRamosAtividades(); // Lista
																									// de
																									// ramos
																									// de
																									// atividade
																									// para
																									// bean

					} else {
						profissoesRamosAtividade = new DbAccess().recuperaListaProfissoes(); // Lista
																								// de
																								// profissoes
																								// para
																								// bean
					}

				} catch (Exception e) {
					LOGGER.error("CotacaoMulti action: " + action + " " + e.getMessage(), e);
				}

				if ("1".equalsIgnoreCase(req.getParameter("entWorkflow"))) {
					req.setAttribute("consulta", "");
					req.setAttribute("entWorkflow", req.getParameter("entWorkflow"));
				}
				
				if(req.getParameter("canal") != null && req.getParameter("canal").equals("BPM")){
					req.setAttribute("canal", "BPM");	
				}

				req.setAttribute("profissoesRamosAtividade", profissoesRamosAtividade);
				req.getRequestDispatcher(DIVDET).forward(req, resp);
				return;

			} else if (action.equals("alteracomissao")) { // Hebert

				String comissao = req.getParameter("comissao");
				String desconto = req.getParameter("desconto");
				String protocolo = cot.getNumProtocolo();

				int ret = DbAccess.atualizaDadosComissaoTabCotacao(comissao, desconto, protocolo);
				if (ret != -1) {
					cot.setPcomissao(Util.formataMoedaToDB(comissao));
					cot.setDesconto(Util.formataMoedaToDB(desconto));
				}
				
				req.setAttribute("consulta", "S");
				req.getRequestDispatcher(DIVDET).forward(req, resp);
				return;
			} else if (action.equals("menupesq")) {

				// hebert
				String dataInicio = req.getParameter("dataInicio");
				String dataFim = req.getParameter("dataFim");
				String departamento = req.getParameter("cDepartamento");
				if (departamento == null || departamento.equals("")) {
					departamento = "0";
				}
				String corretor = req.getParameter("cCorretor");
				if (corretor != null && !corretor.equals("")) {
					corretor = corretor.substring(0, 7);
				}
				String ramo = req.getParameter("cRamo");
				String produtor = req.getParameter("cProdutor");

				req.setAttribute("intranet", intranet);
				
				//NOVA_SUSEP
				HashMap listaCorretor = null;
				try {
					listaCorretor = DbAccess.recuperaListaCorretores(numSusep, codUnidade, codCorretor);
				} catch(SusepHistoricoException e) {
					LOGGER.error("Erro ao consultar suseps", e);
					throw new ServletException(e.getMessage());
				}
				
				req.setAttribute("lstCorretor", listaCorretor);
				req.setAttribute("lstDepartamento", DbAccess.recuperaListaDepartamento(Long.toString(codUnidade)));
				// hebert
				// req.setAttribute("lstProdutor",DbAccess.recuperaListaProdutor(departamento));
				if (codUnidade != 0) {
					req.setAttribute("lstProdutor", DbAccess.recuperaListaProdutor(Long.toString(codUnidade), 1));
				}
				
				//JIRA-168467
				req.setAttribute("TpEmissao", TipoEmissao.getTiposEmissao()); 
				
				//JIRA-110924
				req.setAttribute("RamosDiv", DbAccess.RAMO_DIVERSOS);
				
				req.setAttribute("codUnidade", Long.toString(codUnidade));
				String codCorr = "0";
				if (listaCorretor.size() == 1) {
					codCorr = DbAccess.getCodCorrUnico();
					req.setAttribute("CodCorrVolta", codCorr);
				}

				req.setAttribute("CodCorrVolta", corretor);
				req.setAttribute("CodDeparVolta", departamento);
				req.setAttribute("CodRamoVolta", ramo);
				req.setAttribute("CodProdVolta", produtor);
				req.setAttribute("dataFimVolta", dataFim);
				req.setAttribute("dataInicioVolta", dataInicio);
				// hebert

				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				resp.getWriter();
				
				setMenuPesqCorretor(req,numSusep, codUnidade, codCorr,  intranet, action);
				setMenuPesqRamo(req,numSusep, codUnidade, codCorr,  intranet, action);
				setMenuPesqDepto(req,numSusep, codUnidade, codCorr,  intranet, action);
				setMenuPesqProdutor(req,numSusep, codUnidade, codCorr,  intranet, action);
				
				
				req.getRequestDispatcher(MENU_PESQ_JSP).forward(req, resp);
				return;

			} else if (action.equalsIgnoreCase("enviacotacao")) {
				
				LOGGER.info("[Controller] - (serivce) - [[enviacotacao]]: Iniciando a acao");

				int ret = 0;
				DbAccess dbAccess = new DbAccess();
				String msg = null;
				try {
					dbAccess.openConnectionACID();
					LOGGER.info(">>> abriu conexao");

					
					LOGGER.info(">>> 1 - salvaTabSegurado");
					dbAccess.salvaTabSegurado(cot);
					LOGGER.info("<<< 1 - salvaTabSegurado: " + cot.getCodSegurado());
					
					LOGGER.info(">>> 2 - salvaTabCotacao");
					String numProtolo = dbAccess.salvaTabCotacao(cot);
					LOGGER.info("<<< 2 - salvaTabCotacao: " + numProtolo);
					
					if (numProtolo == null) {
						msg = "Erro na geração do protocolo";
						throw new Exception(msg);
					}

					// Gravacao dos parametros B2B
					String codigoHierarquico = cot.getCodigoHierarquico();
					String codigoFilial = cot.getCodigoFilial();
					if( StringUtils.isNoneBlank( codigoHierarquico, codigoFilial ) ) {
						dbAccess.salvarParametrosB2B( numProtolo, codigoHierarquico, codigoFilial );
					}
					
					cot.setNumProtocolo(numProtolo);
					
					
					LOGGER.info(">>> 3 - salvaTabCotacaoDiv");
					ret = cot.setDadosToDB(dbAccess); // Salva na tab_cotacao_div
					LOGGER.info("<<< 3 - salvaTabCotacaoDiv: " + ret);
					
					if (ret != 0) {
						msg = "Erro na gravação dos Dados da Cotação nº " + cot.getNumProtocolo();
						throw new Exception(msg);
					}
					
					LOGGER.info(">>> 4 - salvaTabCtrlSiscota");
					ret = dbAccess.salvaTabCtrlSiscota(cot);
					LOGGER.info("<<< 4 - salvaTabCtrlSiscota: " + ret);
					
					if (ret != 0) {
						msg = "Erro na gravação da Cotação nº " + cot.getNumProtocolo();
						throw new Exception(msg);
					}

					// Salva tabelas anexos
					LOGGER.info(">>> 5 - salvaTabAnexosComTransacao");
					dbAccess.salvaTabAnexosComTransacao(cot, intranet, numSusep); // Incluido
					LOGGER.info("<<< 5 - salvaTabAnexosComTransacao");
					msg = "Cotação Diversos Protocolo nº  " + cot.getNumProtocolo();
					req.getSession().removeAttribute("fileList");
					
					dbAccess.commit();
					LOGGER.info("[Controller] - (serivce) - [[enviacotacao]]: Finalizando a acao antes do finally. ");
				} catch (Exception e) {
					LOGGER.error("CotacaoMulti - Erro na gravação da cotacao..." + e.getMessage(), e);
					req.setAttribute("msg", msg != null ? msg : "Erro na gravação da Cotação");
					try {
						dbAccess.rollback();
					} catch (SQLException e1) {
						LOGGER.error("CotacaoMulti - Erro ao realizar rollback da gravacao da cotacao..." + e.getMessage(),
								e);
					}
				} finally {
					dbAccess.endACID();
					req.setAttribute("mensagemConfirmacao_1", msg);
					req.getSession().removeAttribute("CotacaoBean");
					LOGGER.error("Controller enviacotacao - verifica sessao: "
							+ (CotacaoBean) req.getSession().getAttribute("CotacaoBean"));
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					return;

				}
			} else if (action.equals("cep")) {
				String cep = req.getParameter("numCEP");
				HashMap dadosCep = new HashMap();
				dadosCep = DbAccess.verificaCep(Integer.parseInt(cep));
				req.setAttribute("cep", dadosCep);
				req.getRequestDispatcher("Cep.jsp").forward(req, resp);
				return;

			} else if (action.equals("anexaArquivoCotacao")) {
				// http://localhost:9080/CotacaoMulti/Controller?action=anexaArquivoCotacao&cCotacao=200602000032

				LOGGER.info("[Controller] - (serivce) - [[anexaArquivoCotacao]]: Iniciando a acao");
				cot = new CotacaoBean();
				int ret = 0;
				ret = DbAccess.recuperaArqAnexos(cot, req.getParameter("cCotacao"), 1);
				req.getSession().setAttribute("visualizaTodosAnexos", DbAccess.liberaVisualizacaoTodosAnexos(login));
				cot.setDadosEmailCotacao("cCotacao", req.getParameter("cCotacao"));
				cot.setNumProtocolo(req.getParameter("cCotacao")); // Hebert
				cot.setArquivoCorrExec(1);
				// ret = DbAccess.recuperaEmailHist(cot,
				// req.getParameter("cCotacao"));
				// ret=0;
				if (ret > 0) {
					req.setAttribute("dadosCotacao", cot.getDadosEmailCotacao());
					req.getSession().setAttribute("numSusep1", "999999");
					req.getSession().setAttribute("fileList", cot.getArqsEmailCotacao());
					req.getSession().setAttribute("CotacaoBean", cot);
					req.setAttribute("anexoSubscricao", "1");
					req.getRequestDispatcher("ArquivoAnexo.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[anexaArquivoCotacao]]: Finalizando a acao com sucesso");
					return;
				} else {
					req.setAttribute("mensagemConfirmacao_1", "Erro no anexo");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[anexaArquivoCotacao]]: Finalizando a acao com erro");
					return;
				}

			} else if(action.equals("anexaArquivoCotacaoBpm")){
				LOGGER.info("[Controller] - (serivce) - [[anexaArquivoCotacaoBpm]]: Iniciando a acao");
				cot = new CotacaoBean();
				int ret = 0;
				ret = DbAccess.recuperaArqAnexos(cot, req.getParameter("cCotacao"), 1);
				req.getSession().setAttribute("visualizaTodosAnexos", DbAccess.liberaVisualizacaoTodosAnexos(login));
				cot.setDadosEmailCotacao("cCotacao", req.getParameter("cCotacao"));
				cot.setNumProtocolo(req.getParameter("cCotacao")); // Hebert
				cot.setArquivoCorrExec(1);
				req.setAttribute("BPM", "true");
							
				// ret = DbAccess.recuperaEmailHist(cot,
				// req.getParameter("cCotacao"));
				// ret=0;
				  
				if (ret > 0) {
					req.setAttribute("dadosCotacao", cot.getDadosEmailCotacao());
					req.getSession().setAttribute("numSusep1", "999999");
					req.getSession().setAttribute("fileList", cot.getArqsEmailCotacao());
					req.getSession().setAttribute("CotacaoBean", cot);
					req.setAttribute("anexoSubscricao", "1");
					req.getRequestDispatcher("ArquivoAnexoBpm.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[anexaArquivoCotacaoBpm]]: Finalizando a acao completa");
					return;
				} else {
					req.setAttribute("mensagemConfirmacao_1", "Erro no anexo");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[anexaArquivoCotacaoBpm]]: Finalizando a acao com erro");
					return;
				}
				
			} else if (action.equals("enviaArquivo")) {
				LOGGER.info("[Controller] - (serivce) - [[enviaArquivo]]: Iniciando a acao");
				int ret = 0;
				String flagSub = req.getParameter("anexaSubscricao");
				if ("1".equalsIgnoreCase(flagSub)) {
					numSusep = "";
				}
				//gburato - 29/06/2018 - Ajuste para permitir visualizacao do anexo na tela de consulta
				if (req.getParameter("BPM") != null && (req.getParameter("acesso") == null || (req.getParameter("acesso") != null && req.getParameter("acesso").equals("")))) {
					cot.setArquivoCorrExec(0);
				}
				ret = DbAccess.salvaTabAnexos(cot, intranet, numSusep);
				if (ret > 0) {
					cot.clearArqsEmailCotacao();
					cot.clearDadosEmailCotacao();
					req.getSession().removeAttribute("fileList");
					req.getSession().removeAttribute("dadosCotacao");
					req.setAttribute("mensagemConfirmacao_1", "Arquivo(s) anexado(s) com sucesso!!!");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[enviaArquivo]]: Finalizando a acao completa");
					return;
				} else {
					req.setAttribute("mensagemConfirmacao_1", "Não foi possível anexar o(s) arquivo(s)!!!");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[enviaArquivo]]: Finalizando a acao com erro");
					return;
				}

			} else if (action.equals("emailCotacao")) {
				LOGGER.info("[Controller] - (serivce) - [[emailCotacao]]: Iniciando a acao");
				// http://localhost:9080/CotacaoMulti/Controller?action=emailCotacao&p1=200601015197&p2=00059412&key=N1212R2006WI09335

				// localhost:9080/CotacaoMulti/Controller?action=emailCotacao&p1=201400000057&p2=00195995&key=N1212R2006WI09335

				String keyext = "";

//				String[] ex = DbAccess.verificarExiste(req.getParameter("p1"), req.getParameter("p2"), codCorretor);
				String[] ex = DbAccess.verificarExiste(req.getParameter("p1"), "999999", codCorretor);
				// verifica a existencia de Cotacao
				if (ex.length > 0) {
					keyext = ex[2];
				} else {
					req.setAttribute("mensagemConfirmacao_1", "Cotação não Autorizada");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[emailCotacao]]: Iniciando a acao");
					return;
				}

				String keyParm = req.getParameter("key");
				if (keyParm == null)
					keyParm = "";

				if ((keyParm.trim().equals(keyext.trim()) == false)) {
					req.setAttribute("mensagemConfirmacao_1", "Cotação não Autorizada");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					return;
				}

				int ret = 0;
				cot = new CotacaoBean();
				DbAccess.recuperaArqAnexos(cot, req.getParameter("p1"), 2);
				req.getSession().setAttribute("visualizaTodosAnexos", DbAccess.liberaVisualizacaoTodosAnexos(login));
				ret = DbAccess.recuperaEmailHist(cot, req.getParameter("p1"));
				if (ret > 0) {
					cot.setKeyext(keyParm.trim());
					cot.setNumSusep(req.getParameter("p2"));
					cot.setNumProtocolo(req.getParameter("p1"));
					req.setAttribute("dadosCotacao", cot.getDadosEmailCotacao());
					req.getSession().setAttribute("fileList", cot.getArqsEmailCotacao());
					req.getSession().setAttribute("CotacaoBean", cot);
					req.getRequestDispatcher("DivRespEmail.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[emailCotacao]]: Finalizando a acao completa");
					return;
				} else {
					req.setAttribute("mensagemConfirmacao_1",
							"Não foi possível carregar a cotação nº " + req.getParameter("p1"));
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.error("[Controller] - (serivce) - [[emailCotacao]]: Finalizando a acao com erro");
					return;
				}

				// Envia email
			} else if (action.equals("enviaEmail")) {
				LOGGER.info("[Controller] - (serivce) - [[enviaEmail]]: Iniciando a acao");
				String keyext = "";

				cot = (CotacaoBean) req.getSession().getAttribute("CotacaoBean");

				//String[] ex = DbAccess.verificarExiste(cot.getNumProtocolo(), cot.getNumSusep(), cot.getCodCorr());
				String[] ex = DbAccess.verificarExiste(cot.getNumProtocolo(), "999999", cot.getCodCorr());
				
				// verifica a existencia de Cotacao
				if (ex.length > 0) {
					keyext = ex[2];
				} else {
					req.setAttribute("mensagemConfirmacao_1", "Cotação não autorizada");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.error("[Controller] - (serivce) - [[enviaEmail]]: Finalizando a acao com o erro Cotacao nao autorizada");
					return;
				}

				if ((cot.getKeyext().trim().equals(keyext.trim()) == false)) {
					req.setAttribute("mensagemConfirmacao_1", "Cotação já confirmada");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.error("[Controller] - (serivce) - [[enviaEmail]]: Finalizando a acao com o erro Cotacao ja confirmada");
					return;
				}

				int ret = 0;
				carregaDadosEmailCotacao(req, cot);
				ret = DbAccess.salvaTabCotacaoEmailHist(cot);
				ret = ret + DbAccess.salvaTabAnexos(cot, intranet, numSusep);

				if (ret >= 2) {
					DbAccess.updateTabela(cot, DbAccess.TAB_COTACAO_DIV, "key_ext", Util.geraNumeroLote(), true);
					DbAccess.updateTabela(cot, DbAccess.TAB_CTRL_SISCOTA, "status_andamento", "1", false);
					DbAccess.updateTabela(cot, DbAccess.TAB_CTRL_PASSO_SISCOTA, "data_final", Util.getCurrentDateTime(),
							true);
					DbAccess.updateTabela(cot, DbAccess.TAB_COTACAO, "scotacao", "13", false);
					cot.clearArqsEmailCotacao();
					cot.clearDadosEmailCotacao();
					req.getSession().removeAttribute("fileList");
					req.getSession().removeAttribute("dadosCotacao");
					req.setAttribute("mensagemConfirmacao_1", "Email Enviado com Sucesso!!!");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[enviaEmail]]: Finalizando a acao com sucesso");
					return;
				} else {
					req.setAttribute("mensagemConfirmacao_1",
							"Não foi possível enviar a cotação nº " + req.getParameter("cCotacao"));
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.error("[Controller] - (serivce) - [[enviaEmail]]: Finalizando a acao com o erro nao foi possivel enviar a cotacao");
					return;

				}

			} else if (action.equals("uploadEmailFile")) {
				// Efetua o upload de arquivos
				LOGGER.info("[Controller] - (serivce) - [[uploadEmailFile]]: Iniciando a acao");
				req.setAttribute("dadosCotacao", cot.getDadosEmailCotacao());
				req.getSession().setAttribute("fileList", cot.getArqsEmailCotacao());
				req.getRequestDispatcher("UploadFile.jsp").forward(req, resp);
				LOGGER.info("[Controller] - (serivce) - [[uploadEmailFile]]: Finalizando a acao");
				return;

			} else if (action.equals("uploadEmailFileNewVisual")) {
				// Efetua o upload de arquivos
				LOGGER.info("[Controller] - (serivce) - [[uploadEmailFileNewVisual]]: Iniciando a acao");
				req.setAttribute("dadosCotacao", cot.getDadosEmailCotacao());
				req.getSession().setAttribute("fileList", cot.getArqsEmailCotacao());
				req.getRequestDispatcher("UploadFileNovaIdentidadeVisual.jsp").forward(req, resp);
				LOGGER.info("[Controller] - (serivce) - [[uploadEmailFileNewVisual]]: Finalizando a acao");
				return;

			} else if (action.equals("delEmailFileNewVisual")) {
				// Exclusao de arquivos
				LOGGER.info("[Controller] - (serivce) - [[delEmailFileNewVisual]]: Iniciando a acao");
				cot.delArqsEmailCotacao((String) req.getParameter("nameTempFile"), false);
				req.getSession().setAttribute("fileList", cot.getArqsEmailCotacao());
				req.getRequestDispatcher("UploadFileNovaIdentidadeVisual.jsp").forward(req, resp);
				LOGGER.info("[Controller] - (serivce) - [[delEmailFileNewVisual]]: Finalizando a acao");
				return;

				// Downloads de arquivos
			} else if (action.equals("delEmailFile")) {
				// Exclusao de arquivos
				LOGGER.info("[Controller] - (serivce) - [[delEmailFile]]: Iniciando a acao");
				cot.delArqsEmailCotacao((String) req.getParameter("nameTempFile"), false);
				req.getSession().setAttribute("fileList", cot.getArqsEmailCotacao());
				req.getRequestDispatcher("UploadFile.jsp").forward(req, resp);
				LOGGER.info("[Controller] - (serivce) - [[delEmailFile]]: Finalizando a acao");
				return;

				// Downloads de arquivos
			} else if (action.equals("downloadEmailFile")) {
				// http://localhost:9080/CotacaoMulti/VisualizaDoc.jsp?file=2006030000652.doc
				// http://spx10422psluynl.yasuda.com.br/CotacaoMulti/VisualizaDoc.jsp?file=2006010152171.txt
				// http://www.yasuda.com.br/CotacaoMulti/VisualizaDoc.jsp?file=2006010171381.doc
				LOGGER.info("[Controller] - (serivce) - [[downloadEmailFile]]: Iniciando a acao");
				if ("1".equalsIgnoreCase(req.getParameter("novaConsulta"))) {
					HashMap<String, CotacaoBean> mapArquivosBeansVisualiza = (HashMap<String, CotacaoBean>) req.getSession()
							.getAttribute("mapArquivosBeansVisualiza");
					String cotacao = req.getParameter("cCotacao");
					cotacao = cotacao.trim();

					Iterator itMap = mapArquivosBeansVisualiza.keySet().iterator();
					LOGGER.info("[Controller] - (serivce) - [[downloadEmailFile]]: Iniciando o while");
					while (itMap.hasNext()) {
						String key = (String) itMap.next();
						if (cotacao.equalsIgnoreCase(key)) {
							cot = mapArquivosBeansVisualiza.get(key);
						}
					}
					LOGGER.info("[Controller] - (serivce) - [[downloadEmailFile]]: Finalizando o while");
				}

				int i = 0;
				if (cot == null) {
					i = Util.ListForm(null, req, resp);
				} else {
					i = Util.ListForm(cot.getArqsEmailCotacao(), req, resp);
					if (i == -999) {
						req.setAttribute("mensagemConfirmacao_1", "Arquivo não existe ou não está disponível!");
						req.setAttribute("mostraBotoes", "false");
						req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
						LOGGER.error("[Controller] - (serivce) - [[downloadEmailFile]]: Finalizando a acao com o arquivo nao existe");
						return;
					}
				}
				if (i < 0) {
					req.setAttribute("mensagemConfirmacao_1", "Erro na abertura do arquivo!!!");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.error("[Controller] - (serivce) - [[downloadEmailFile]]: Finalizando a acao com erro na abertura do arquivo");
					return;
				}
				if (cot != null) {
					req.getSession().setAttribute("fileList", cot.getArqsEmailCotacao());
				}
				LOGGER.info("[Controller] - (serivce) - [[downloadEmailFile]]: Finalizando a acao");
				return;

			} else if (action.equals("cancelaCotacao")) {
				LOGGER.info("[Controller] - (serivce) - [[cancelaCotacao]]: Iniciando a acao");
				// System.out.println("CotacaoMulti Cancelada!!!! - "
				// + req.getParameter("cCotacao") + " / " + numSusep);

				String keyext = "";

				cot = (CotacaoBean) req.getSession().getAttribute("CotacaoBean");

				String[] ex = DbAccess.verificarExiste(cot.getNumProtocolo(), cot.getNumSusep(), cot.getCodCorr());
				// verifica a existencia de Cotacao
				if (ex.length > 0) {
					keyext = ex[2];
				} else {
					req.setAttribute("mensagemConfirmacao_1", "Cotação não autorizada");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.error("[Controller] - (serivce) - [[cancelaCotacao]]: Finalizando a acao com o erro Cotacao nao autorizada");
					return;
				}

				if ((cot.getKeyext().trim().equals(keyext.trim()) == false)) {
					req.setAttribute("mensagemConfirmacao_1", "Cotação já cancelada");
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.error("[Controller] - (serivce) - [[cancelaCotacao]]: Finalizando a acao com o erro Cotacao ja cancelada");
					return;
				}

				req.setAttribute("mostraBotoes", "false");
				if (cancelaCotacao(cot) == 4) {
					req.setAttribute("mensagemConfirmacao_1", "Cotacao nº " + req.getParameter("cCotacao"));
					req.setAttribute("mensagemConfirmacao_2", "cancelada com sucesso");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.info("[Controller] - (serivce) - [[cancelaCotacao]]: Finaliznado a acao com sucesso");
					return;
				} else {
					req.setAttribute("mensagemConfirmacao_1",
							"Ocorreu um problema ao cancelar a cotacao nº " + req.getParameter("cCotacao"));
					req.setAttribute("mostraBotoes", "false");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					LOGGER.error("[Controller] - (serivce) - [[cancelaCotacao]]: Finalizando a acao com problema ao cancelar a cotacao");
					return;
				}

			} else if (action.equals("pesqcotacao")) {

				List<Dominio> profissoesRamosAtividade = null;

				String cCotacao = req.getParameter("cCotacao");
				if (cCotacao == null)
					cCotacao = "";

				String nomeProponente = req.getParameter("nomeProponente");
				if (nomeProponente == null)
					nomeProponente = "";

				String numCpfCnpj = CpfCnpjValidator.formataCpfCnpj(req.getParameter("numCpfCnpj"));
				if (numCpfCnpj == null)
					numCpfCnpj = "";

				String dataInicio = req.getParameter("dataInicioII");
				if (dataInicio == null)
					dataInicio = "";

				String dataFim = req.getParameter("dataFimII");
				if (dataFim == null)
					dataFim = "";

				String sitCotacao = req.getParameter("sitCotacao");
				if (sitCotacao == null)
					sitCotacao = "";

				cot = new CotacaoBean(); // set no bean

				cot.setNumSusep(numSusep);
				cot.setNumProtocolo(cCotacao);
				cot.setNomeProp(nomeProponente);
				cot.setCnpjCpf(numCpfCnpj);

				cot.setCodCorr(codCorretor);
				cot.setCodCliente(codCliente);

				cot.setDataInicio(dataInicio);
				cot.setDataFim(dataFim);
				cot.setCodSituacao(sitCotacao);
				String page = pesquisaCotacao(cot, codUnidade, req, resp, 1, intranet);
				req.getSession().setAttribute("visualizaTodosAnexos", DbAccess.liberaVisualizacaoTodosAnexos(login));
				req.setAttribute("consulta", "S");

				if (DIVDET.equalsIgnoreCase(page)) {

					cot = (CotacaoBean) req.getSession().getAttribute("CotacaoBean");
					try {

						if ("1".equalsIgnoreCase(cot.getTipPessoa())) {
							profissoesRamosAtividade = new DbAccess().recuperaListaRamosAtividades(); // Lista
																										// de
																										// ramos
																										// de
																										// atividade
																										// para
																										// bean

						} else {
							profissoesRamosAtividade = new DbAccess().recuperaListaProfissoes(); // Lista
																									// de
																									// profissoes
																									// para
																									// bean
						}

					} catch (Exception e) {
						LOGGER.error("CotacaoMulti action: " + action + " " + e.getMessage(), e);
					}
				}

				req.setAttribute("profissoesRamosAtividade", profissoesRamosAtividade);
				req.getRequestDispatcher(page).forward(req, resp);
				return;

			} else if ("pesqperiodo".equalsIgnoreCase(action)) {

				String novaConsulta = req.getParameter("novaConsulta");
				List<Dominio> profissoesRamosAtividade = null;

				String dataInicio = req.getParameter("dataInicio");
				if (dataInicio == null)
					dataInicio = "";

				String dataFim = req.getParameter("dataFim");
				if (dataFim == null)
					dataFim = "";

				String sitCotacao = req.getParameter("sitCotacao");
				if (sitCotacao == null)
					sitCotacao = "";

				// Hebert
				String cCorretor = req.getParameter("cCorretor");
				if (cCorretor == null)
					cCorretor = "";

				String cRamo = req.getParameter("cRamo");
				if (cRamo == null || " ".equalsIgnoreCase(cRamo) || "".equalsIgnoreCase(cRamo))
					cRamo = "000";

				String cDepartamento = req.getParameter("cDepartamento");
				if (cDepartamento == null || cDepartamento.equals(""))
					cDepartamento = "0";

				String cProdutor = req.getParameter("cProdutor");
				if (cProdutor == null)
					cProdutor = "";
				// Hebert

				cot = new CotacaoBean(); // set no bean
				cot.setNovaConsultaAnexos(novaConsulta);

				if ("1".equalsIgnoreCase(novaConsulta)) {
					cot.setCnpjCpf(CpfCnpjValidator.formataCpfCnpj(req.getParameter("cpfCnpjProp")));
					cot.setCodDepto((String) req.getSession().getAttribute("codUnidade"));
				}

				cot.setNumSusep(numSusep);
				cot.setCodCorr(codCorretor);
				cot.setCodCliente(codCliente);

				cot.setDataInicio(dataInicio);
				cot.setDataFim(dataFim);
				cot.setCodSituacao(sitCotacao);

				// Hebert
				if (!"".equalsIgnoreCase(cCorretor)) {
					cot.setCodCorr(cCorretor.substring(0, 7));
				}
				cot.setCodRamo(Integer.parseInt(cRamo.substring(0, 3)));
				cot.setCodDepto(cDepartamento);
				cot.setCodProdutor(cProdutor);
				// Hebert

				String page;
				if ("1".equalsIgnoreCase(intranet) && cot.getCodDepto() != null
						&& !"0".equalsIgnoreCase(cot.getCodDepto())) { // Hebert
					page = pesquisaPeriodo(cot, Long.parseLong(cot.getCodDepto()), req, resp, 2, intranet);
				} else {
					page = pesquisaPeriodo(cot, codUnidade, req, resp, 1, intranet);
				}

				if ("1".equalsIgnoreCase(req.getParameter("entWorkflow"))) {
					req.setAttribute("consulta", "");
					req.setAttribute("entWorkflow", req.getParameter("entWorkflow"));
				}
				
				if(req.getParameter("BPM") != null && req.getParameter("BPM").equals("BPM")) {
					req.setAttribute("canal", req.getParameter("BPM"));
				}

				if (DIVDET.equalsIgnoreCase(page)) {

					cot = (CotacaoBean) req.getSession().getAttribute("CotacaoBean");
					try {

						if ("1".equalsIgnoreCase(cot.getTipPessoa())) {
							profissoesRamosAtividade = new DbAccess().recuperaListaRamosAtividades(); // Lista
																										// de
																										// ramos
																										// de
																										// atividade
																										// para
																										// bean

						} else {
							profissoesRamosAtividade = new DbAccess().recuperaListaProfissoes(); // Lista
																									// de
																									// profissoes
																									// para
																									// bean
						}

					} catch (Exception e) {
						LOGGER.error("CotacaoMulti action: " + action + " " + e.getMessage(), e);
					}
				}

				// hebert
				req.setAttribute("intranet", intranet);
				req.getSession().setAttribute("visualizaTodosAnexos", DbAccess.liberaVisualizacaoTodosAnexos(login));
				
				//NOVA_SUSEP
				HashMap listaCorretor = null;
				try {
					listaCorretor = DbAccess.recuperaListaCorretores(numSusep, codUnidade, codCorretor);
				} catch(SusepHistoricoException e) {
					LOGGER.error("Erro ao consultar suseps", e);
					throw new ServletException(e.getMessage());
				}

				req.setAttribute("lstCorretor", listaCorretor);
				req.setAttribute("lstDepartamento", DbAccess.recuperaListaDepartamento(Long.toString(codUnidade)));
				// hebert
				if ("0".equalsIgnoreCase(cDepartamento) && codUnidade != 0) {
					req.setAttribute("lstProdutor", DbAccess.recuperaListaProdutor(Long.toString(codUnidade), 2));
				} else if (!"0".equalsIgnoreCase(cDepartamento)) {
					req.setAttribute("lstProdutor", DbAccess.recuperaListaProdutor(cDepartamento, 2));
					// hebert
				}
				
				//JIRA-168467
				req.setAttribute("TpEmissao", TipoEmissao.getTiposEmissao()); 

				req.setAttribute("RamosDiv", DbAccess.RAMO_DIVERSOS);
				req.setAttribute("codUnidade", Long.toString(codUnidade));
				String codCorr = "0";
				if (listaCorretor.size() == 1) {
					codCorr = DbAccess.getCodCorrUnico();
					req.setAttribute("CodCorrVolta", codCorr);
				}

				req.setAttribute("CodCorrVolta", cCorretor);
				req.setAttribute("CodDeparVolta", cDepartamento);
				req.setAttribute("CodRamoVolta", cRamo);
				req.setAttribute("CodProdVolta", cProdutor);
				req.setAttribute("dataFimVolta", dataFim);
				req.setAttribute("dataInicioVolta", dataInicio);
				// hebert

				req.setAttribute("novaConsulta", novaConsulta); // Gustavo
				req.setAttribute("profissoesRamosAtividade", profissoesRamosAtividade);
				
				setMenuPesqCorretor(req,numSusep, codUnidade, codCorr,  intranet, action);
				setMenuPesqRamo(req,numSusep, codUnidade, codCorr,  intranet, action);
				setMenuPesqDepto(req,numSusep, codUnidade, codCorr,  intranet, action);
				setMenuPesqProdutor(req,numSusep, codUnidade, codCorr,  intranet, action);

				
				req.getRequestDispatcher(page).forward(req, resp);
				return;

			} else if (action.equals("voltaCadPrincipal")) {
				req.getRequestDispatcher(DIVCAD).forward(req, resp);
				return;

			} else if (action.equals("voltaCadEspecifico")) {
				if (cot.getDadosHtml().length <= 0) {
					req.getRequestDispatcher(DIVCAD).forward(req, resp);
					return;
				} else {
					req.getRequestDispatcher(cot.getPaginaJSP()).forward(req, resp);
					return;
				}
			} else if (action.equals("ContasInternacionais")) {
					
				try {	// final - retirar
					Action acao = new ResolveAction().getInstance(action);
					acao.doAction(req, resp);
					
				} catch (Exception e) {
					LOGGER.error("CotacaoMulti - " + e.getMessage(), e);
					req.setAttribute("msg",
							"Não foi possivel concluir a ação, tente novamente.");
					req.getRequestDispatcher("mensagem.jsp").forward(req, resp);

				}
			}
			else {
				req.setAttribute("mostraBotoes", "false");
				if (req.getContentType() != null) {
					req.setAttribute("mensagemConfirmacao_1", "Acesso negado.");
					req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
					return;
				} else {
					if (action == "") {
						req.setAttribute("mensagemConfirmacao_1", "Acesso negado.");
						req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
						return;
					}
				}
			}
		}
		catch(Exception e) {
			LOGGER.error("PORTAL_CORRETOR - ERRO na controller.service -> " + e.getMessage() + " - Posicao -> " + valPosicao);
		}
		
	}

	private String getCodigoTipEmissao(String string) {

		if (string.equals("Seguro Novo"))
			return "00";
		else if (string.startsWith("Renov Cong"))
			return "01";
		else if (string.startsWith("Renov Yasuda") || "Renov Sompo".equalsIgnoreCase(string))
			return "02";
		else if (string.equals("Endosso"))
			return "03";
		else
			return null;

	}

	/*
	 * De acordo com o CNPJ digitado, verifica se existe uma Unidade e Produtor
	 * especifico para o segurado. Se existe atualiza os dados de Unidade e
	 * Produtor
	 */
	public void AtualizaUnidadeProdutor(CotacaoBean cot) throws Exception {
		String numCnpjCpfSegurado = CpfCnpjValidator.formataCpfCnpj(cot.getCnpjCpf());

		UnidadeBean unidadeSegurado = (new DbAccess()).recuperaUnidadeSegurado(numCnpjCpfSegurado.trim());
		if (unidadeSegurado.getCodProdutor() != null) {
			cot.setCodProdutor(unidadeSegurado.getCodProdutor());
			cot.setCodDepto(unidadeSegurado.getUnidNegocio());
		}
	}

	@SuppressWarnings("rawtypes")
	private String pesquisaPeriodo(CotacaoBean bean, long codUnidade, HttpServletRequest req, HttpServletResponse resp,
			int flag, String intranet) throws ServletException, IOException {
		
		//NOVA_SUSEP
		Vector list = null;
		try {
			list = DbAccess.recuperaListaSegurado(bean, codUnidade, flag);
		} catch(SusepHistoricoException e) {
			LOGGER.error("Erro ao consultar suseps", e);
			throw new ServletException(e.getMessage());
		}

		if (list.size() > 1) {
			req.setAttribute("listaCotacoes", list);
			req.setAttribute("intranet", intranet);
			return PESQ_LISTA_JSP;
		}
		if (list.size() == 1) {
			recuperaDetalhe(bean.getNumProtocolo(), bean.getNumSusep(), req, resp, bean, codUnidade);
			return DIVDET;
		}
		req.setAttribute("errorMessage", "Registro não Localizado");
		return MENU_PESQ_JSP;
	}

	@SuppressWarnings("rawtypes")
	private String pesquisaCotacao(CotacaoBean bean, long codUnidade, HttpServletRequest req, HttpServletResponse resp,
			int flag, String intranet) throws ServletException, IOException {

		if (bean.getNumProtocolo().length() > 0) {
			
			//NOVA_SUSEP
			try {
				DbAccess.recuperaDadosCotacao(bean, codUnidade);
			} catch(SusepHistoricoException e) {
				LOGGER.error("Erro ao consultar suseps", e);
				throw new ServletException(e.getMessage());
			}

			if (bean.getCodSegurado().length() > 0) {
				recuperaDetalhe(bean.getNumProtocolo(), bean.getNumSusep(), req, resp, bean, codUnidade);
				return DIVDET;
			}
		} else if (bean.getNomeProp().length() > 0 || bean.getCnpjCpf().length() > 0) {
			
			//NOVA_SUSEP
			Vector list = null;
			try {
				list = DbAccess.recuperaListaSegurado(bean, codUnidade, flag);
			} catch(SusepHistoricoException e) {
				LOGGER.error("Erro ao consultar suseps", e);
				throw new ServletException(e.getMessage());
			}

			if (list.size() > 1) {
				req.setAttribute("listaCotacoes", list);
				req.setAttribute("intranet", intranet);
				return PESQ_LISTA_JSP;
			}
			if (list.size() == 1) {
				recuperaDetalhe(bean.getNumProtocolo(), bean.getNumSusep(), req, resp, bean, codUnidade);
				return DIVDET;
			}
		}

		req.setAttribute("errorMessage", "Registro não Localizado");
		return MENU_PESQ_JSP;
	}

	
	private void recuperaDetalhe(String numProtocolo, String numSusep, HttpServletRequest req, HttpServletResponse resp,
			CotacaoBean cot, long codUnidade) throws ServletException, IOException {

		String codRamo = "0";
		String codMod = "00";
		if (numProtocolo == null)
			numProtocolo = "";

		String[] ex = DbAccess.verificarExiste(numProtocolo, numSusep, (cot == null || cot.getCodCorr() == null ? null : cot.getCodCorr()));
		// verifica a existencia de Cotacao
		
		if(cot != null && cot.getCodRamo() == 310){
			req.setAttribute("mensagemConfirmacao_1", "Cotação '" + numProtocolo + "' não encontrada para a susep '" + numSusep + "'");
			req.setAttribute("mostraBotoes", "false");
			req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
			return;
		}
		
		if (ex.length > 0 && ex[0] != null && ex[1] != null) {
			codRamo = ex[0];
			codMod = ex[1];
		} else {
			req.setAttribute("mensagemConfirmacao_1", "Cotação '" + numProtocolo + "' não encontrada para a susep '" + numSusep + "'");
			req.setAttribute("mostraBotoes", "false");
			req.getRequestDispatcher("Mensagem.jsp").forward(req, resp);
			return;
		}
		cot = selectClass(codRamo, codMod, cot);
		// Instancia da Class correta(Bean)

		cot.setNumProtocolo(numProtocolo);
		cot.setNumSusep(numSusep);

		// /Recupera Dados
		// DbAccess.recuperaDadosCotacao(cot);//Hebert
		
		//NOVA_SUSEP
		try {
			DbAccess.recuperaDadosCotacao(cot, codUnidade);
		} catch(SusepHistoricoException e) {
			LOGGER.error("Erro ao consultar suseps", e);
			throw new ServletException(e.getMessage());
		}

		DbAccess.recuperaCorretorMaritima(cot);
		DbAccess.recuperaDadosSegurado(cot);
		cot.setDadosHtml(); // dados da pagina de detalhe
		DbAccess.recuperaArqAnexos(cot, numProtocolo, 2);
		req.getSession().setAttribute("fileList", cot.getArqsEmailCotacao());

		req.setAttribute("consulta", "S");
		req.getSession().setAttribute("CotacaoBean", cot);

	}


	/**
	 * Class para indentificar qual ramo e modalidade sera utilizada no projeto.
	 * 
	 * @param codRamo
	 * @param codMod
	 */
	private CotacaoBean selectClass(String codRamo, String codMod, CotacaoBean cot) {

		codMod = (codMod.length() > 2) ? codMod.substring(0, 2).trim() : codMod;
		switch (Integer.parseInt(codRamo.substring(0, 3).trim())) {
		case 112:
			cot = new Cto112Bean();
			break;
		case 113:
			cot = new Cto113Bean();
			break;
		case 114:
			cot = new Cto114Bean();
			break;
		case 150:
			if (codMod == null || codMod.trim().equals("") || codMod.trim().equals("0")) { // Simony
				cot = new CotacaoBean();
			} else {
				switch (Integer.parseInt(codMod.substring(0, 2).trim())) {
				case 1:
					cot = new Cto1501Bean();
					break;
				case 2:
					cot = new Cto1502Bean();
					break;
				case 3:
					cot = new Cto1503Bean();
					break;
				default: // Hebert
					cot = new CotacaoBean();
					break;
				}
			}
			break;
		case 510:
			if (codMod == null || codMod.trim().equals("") || codMod.trim().equals("0")) { // Simony
				cot = new CotacaoBean();
			} else {
				switch (Integer.parseInt(codMod.substring(0, 2).trim())) {
				case 1:
					cot = new Cto5101Bean();
					break;
				case 2:
					cot = new Cto5102Bean();
					break;
				case 3:
					cot = new Cto5103Bean();
					break;
				case 4:
					cot = new Cto5104Bean();
					break;
				case 5:
					cot = new Cto5105Bean();
					break;
				case 6:
					cot = new Cto5106Bean();
					break;
				case 7:
					cot = new Cto5107Bean();
					break;
				case 8:
					cot = new Cto5108Bean();
					break;
				case 9:
					cot = new Cto5109Bean();
					break;
				default: // Hebert
					cot = new CotacaoBean();
					break;
				}
			}
			break;
		case 710:

			if (codMod == null || codMod.trim().equals("") || codMod.trim().equals("0")) { // Simony
				cot = new CotacaoBean();
			} else {
				switch (Integer.parseInt(codMod)) {
				case 1:
					cot = new Cto7101Bean();
					break;
				case 2:
					cot = new Cto7102Bean();
					break;
				case 3:
					cot = new Cto7103Bean();
					break;
				case 4:
					cot = new Cto7104Bean();
					break;
				case 5:
					cot = new Cto7105Bean();
					break;
				case 6:
					cot = new Cto7106Bean();
					break;
				case 7:
					cot = new Cto7107Bean();
					break;
				case 8:
					cot = new Cto7108Bean();
					break;
				case 9:
					cot = new Cto7109Bean();
					break;
				case 15:
					cot = new Cto1501Bean();
					break;
				case 16:
					cot = new Cto1502Bean();
					break;
				case 17:
					cot = new Cto1503Bean();
					break;
				default: // Hebert
					cot = new CotacaoBean();
					break;
				}
			}
			break;
		default:
			cot = new CotacaoBean();
			break;
		}
		return cot;
	}

	@SuppressWarnings("rawtypes")
	private int recebeCampos(HttpServletRequest req, CotacaoBean cot) {

		com.yasuda.util.Util util = new com.yasuda.util.Util();

		int ok = 0;

		cot.setNomeProp(req.getParameter("nomeProp"));
		cot.setTipPessoa(req.getParameter("tipoPessoa"));
		cot.setCnpjCpf(CpfCnpjValidator.formataCpfCnpj(req.getParameter("cnpjCpf")));
		cot.setDataVigenciaInicio(
				String.valueOf(util.formatarDataStringToInteiro(req.getParameter("dataVigenciaInicio"))));
		cot.setDataVigenciaFim(String.valueOf(util.formatarDataStringToInteiro(req.getParameter("dataVigenciaFim"))));
		String tmp = req.getParameter("QtdLocRisc");
		cot.setLmiUnico(req.getParameter("lmiUnico"));
		if (tmp == null)
			tmp = "0";
		else
			tmp = "1";
		cot.setQtdLocRisc(tmp);

		if (tmp.equals("0")) {
			cot.setEnd(req.getParameter("End"));
			cot.setNumero(req.getParameter("Numero"));
			cot.setComplemento(req.getParameter("Complemento"));
			cot.setBairro(req.getParameter("Bairro"));
			cot.setCid(req.getParameter("Cid"));
			cot.setUf(req.getParameter("cobUF"));
			cot.setCep(req.getParameter("Cep"));
			cot.setVlRisco(Util.formataMoedaToDB(req.getParameter("VlRisco")));
		} else {
			cot.setEnd("");
			cot.setNumero("");
			cot.setComplemento("");
			cot.setBairro("");
			cot.setCid("");
			cot.setUf("");
			cot.setCep("");
			cot.setTelefone("");
			if ((cot.getCodRamo() == 112) || (cot.getCodRamo() == 113) || (cot.getCodRamo() == 114)) {
				cot.setVlRisco(Util.formataMoedaToDB(req.getParameter("VlRisco")));
			} else {
				cot.setVlRisco(0);
			}
		}

		// o codigo abaixo ja estava implementado no bean, apenas o transferi
		// para este
		// ponto para gravar fielmente sempre o motivo atual, mas se caso for
		// necessario
		// alterar para algo mais dinamico, porem nao usual e so retornar o
		// mesmo para o
		// bean ou isolar o mesmo em uma classe especifica.
		//
		// Diego

		String tpmotivo = req.getParameter("TpMotivo");
		// tpmotivo ja estava definido com este nome
		int codMotivo = 0;
		if (tpmotivo != null) {
			codMotivo = Integer.parseInt(tpmotivo);
		}
		cot.setCodMotivo(codMotivo);
		if (codMotivo != 5 && tpmotivo != null) {
			Hashtable hash = DbAccess.recuperaListaMotivo();
			// Recupera nome do ramo
			cot.setNomMotivo((String) hash.get(tpmotivo));
		} else {
			cot.setNomMotivo(req.getParameter("nomMotivo"));
		}

		// System.out.println("cod1"+codMotivo);
		// System.out.println("cod2"+tpmotivo);
		// System.out.println("cod3"+req.getParameter("nomMotivo"));

		cot.setEmailContato(req.getParameter("EmailContato"));
		cot.setNomeContato(req.getParameter("NomeContato"));
		cot.setTelefContato(req.getParameter("TelefContato"));
		cot.setComentario(req.getParameter("observ"));
		
		// Parametros B2B
		if( !StringUtils.equalsIgnoreCase( req.getParameter("cod_hierarquico"), "null" ) ) {
			cot.setCodigoHierarquico( req.getParameter("cod_hierarquico") );
		}
		if( !StringUtils.equalsIgnoreCase( req.getParameter("cod_filial"), "null" ) ) {
			cot.setCodigoFilial( req.getParameter("cod_filial") );
		}

		req.getSession().setAttribute("CotacaoBean", cot);
		return ok;

	}

	/**
	 * @param CotacaoBean
	 * @return int
	 */
	private int cancelaCotacao(CotacaoBean bean) {
		int ret = 0;
		ret += DbAccess.salvaTabCotacaoEmailHist(bean);
		ret += DbAccess.updateTabela(bean, DbAccess.TAB_COTACAO, "scotacao", "17", true);
		ret += DbAccess.updateTabela(bean, DbAccess.TAB_CTRL_SISCOTA, "status_andamento", "03", false);
		ret += DbAccess.updateTabela(bean, DbAccess.TAB_COTACAO_DIV, "key_ext", Util.geraNumeroLote(), true);
		return ret;
	}

	// CARREGA DADOS DO EMAIL COTACAO
	/**
	 * @param CotacaoBean
	 *            , HttpServletRequest
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private void carregaDadosEmailCotacao(HttpServletRequest request, CotacaoBean bean) {
		LOGGER.info("[Controller] - (carregaDadosEmailCotacao) : Iniciando o metodo");
		Enumeration e = request.getParameterNames();
		LOGGER.info("[Controller] - (carregaDadosEmailCotacao) : Buscou os elementos");
		while (e.hasMoreElements()) {
			String p = (String) e.nextElement();
			LOGGER.info("[Controller] - (carregaDadosEmailCotacao) : Editando a bean com o elemento: ".concat(p));
			bean.setDadosEmailCotacao(p, request.getParameter(p));
			LOGGER.info("[Controller] - (carregaDadosEmailCotacao) : Editou a bean com o elemento: ".concat(p));
		}
		LOGGER.info("[Controller] - (carregaDadosEmailCotacao) : Finalizando o metodo");
	}

	public static String adicionaDiasDataAtual(int dias) {
		Calendar data = Calendar.getInstance(new Locale("pt", "br"));

		data.add(Calendar.DATE, dias);

		int dia;
		int mes;
		int ano;

		String strDia = "";
		String strMes = "";
		String strAno = "";

		dia = data.get(Calendar.DAY_OF_MONTH);
		mes = data.get(Calendar.MONTH) + 1;
		ano = data.get(Calendar.YEAR);

		strDia = new String((new Integer(dia)).toString());
		if (strDia.length() < 2)
			strDia = "0" + strDia;

		strMes = new String((new Integer(mes)).toString());
		if (strMes.length() < 2)
			strMes = "0" + strMes;

		strAno = new String((new Integer(ano)).toString());

		return strAno + strMes + strDia;
	}
	
	private void setMenuPesqCorretor(HttpServletRequest req, String numSusep, long codUnidade, String codCorr, String intranet, String action){
		Gson gson = new Gson();
		
		if ("1".equalsIgnoreCase(intranet)) {
			List<Corretor> listCorretores = null;
			try {
				listCorretores = new DbAccess().recuperaListaCorretoresOrdenado(numSusep, codUnidade, codCorr, "1");
			} catch (Exception e) {
				LOGGER.error("CotacaoMulti Intranet: Consulta Corretores - action: " + action + " " + e.getMessage(), e);
			}
			String jsonCorretores = gson.toJson(listCorretores);

			req.setAttribute("lstCorretorJson", jsonCorretores);
		}
	}
	
	
	private void setMenuPesqRamo(HttpServletRequest req, String numSusep, long codUnidade, String codCorr, String intranet, String action){
		Gson gson = new Gson();
		
		if ("1".equalsIgnoreCase(intranet)) {
			List<Ramo> listRamos = new ArrayList<Ramo>();
			try {
				//JIRA-167098
				List<String> ramos = new ArrayList<String>();
				ramos.addAll(Arrays.asList(DbAccess.RAMO_DIVERSOS));
				listRamos = ramos.stream().map(r -> new Ramo(r, r, null)).collect(Collectors.toList());				
				
			} catch (Exception e) {
				LOGGER.error("CotacaoMulti Intranet: Consulta Ramo - action: " + action + " " + e.getMessage(), e);
			}
			String jsonRamos = gson.toJson(listRamos);
			req.setAttribute("ramosDivJson", jsonRamos);
		}
	}
	
	private void setMenuPesqDepto(HttpServletRequest req, String numSusep, long codUnidade, String codCorr, String intranet, String action){
		Gson gson = new Gson();
		
		if ("1".equalsIgnoreCase(intranet)) {
			List<DepartamentoBean> listDepto = null;
			try {
				listDepto = DbAccess.recuperaListaDepartamento(Long.toString(codUnidade));
				for(DepartamentoBean bean : listDepto){
					bean.setNome(bean.getCodigo() + " - " + bean.getNome());
				}
			} catch (Exception e) {
				LOGGER.error("CotacaoMulti Intranet: Consulta Depto - action: " + action + " " + e.getMessage(), e);
			}
			String jsonDepto = gson.toJson(listDepto);
			req.setAttribute("lstDepartamentoJson", jsonDepto);
		}
	}
	
	
	private void setMenuPesqProdutor(HttpServletRequest req, String numSusep, long codUnidade, String codCorr, String intranet, String action){
		Gson gson = new Gson();
		
		if ("1".equalsIgnoreCase(intranet)) {
			List<Produtor> listProdutor = null;
			try {
				listProdutor = DbAccess.recuperaListProdutor(Long.toString(codUnidade), 1);
			} catch (Exception e) {
				LOGGER.error("CotacaoMulti Intranet: Consulta Depto - action: " + action + " " + e.getMessage(), e);
			}
			String jsonProdutor = gson.toJson(listProdutor);
			req.setAttribute("lstProdutorJson", jsonProdutor);
		}
	}
	
	

	private HashMap obterListaUnidades(HttpServletRequest req) {
		LOGGER.info("> INICIO obterListaUnidades");
		HashMap unidades = new HashMap<>();
		if(req.getAttribute("lstUnidade") == null) {
			LOGGER.info("NAO EXISTE NA REQUEST");
			LOGGER.info("VAI PEGAR DA SESSION");
			
			if(req.getSession().getAttribute("lstUnidade") == null) {
				LOGGER.info("NAO EXISTE NA SESSION");
			} else {
				LOGGER.info("EXISTE NA SESSION");
				unidades = (HashMap) req.getSession().getAttribute("lstUnidade");
				LOGGER.info("session unidades.size() : " + unidades.size());
			}
		} else {
			LOGGER.info("EXISTE NA REQUEST");
			unidades = (HashMap) req.getAttribute("lstUnidade");
			LOGGER.info("request unidades.size() : " + unidades.size());
		}
		LOGGER.info("< FIM obterListaUnidades");
		return unidades;
	}

	
	private String obterSusepRequest(HttpServletRequest req) {
		String susep = StringUtils.EMPTY;
		if(StringUtils.isNotEmpty(StringUtils.trim(req.getParameter("num_susep_completo")))) { 
			susep = (String) req.getParameter("num_susep_completo");
		} else {
			susep = (String) req.getParameter("numSusep");
		}
		return susep;
	}

	private String obterSusepSession(HttpServletRequest req) {
		String susep = StringUtils.EMPTY;
		if (req.getSession().getAttribute("num_susep_completo") != null) {
			susep = (String) req.getSession().getAttribute("num_susep_completo");
		} else if (req.getSession().getAttribute("numSusep") != null) {
			susep = (String) req.getSession().getAttribute("numSusep");
		}
		return susep;
	}
	
	private Boolean isSusepCompleta(String susep) {
		return StringUtils.isNotEmpty(susep) && susep.length() > 8;
	}
	
	private void setSusepSession(HttpServletRequest req, String susep) {
		if(isSusepCompleta(susep)) {
			req.getSession().setAttribute("num_susep_completo", susep);
		} else {
			req.getSession().setAttribute("numSusep", susep);
		}
	}
	
}
