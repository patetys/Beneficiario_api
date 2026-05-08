/*
 * Criado em 15/06/2010
 *
 */
package br.yasuda.control;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @author Ricardo Miyahira
 *
 */
public class ResolveAction {
	
	private static final Logger LOGGER = Logger.getLogger(ResolveAction.class);

	public Action getInstance(HttpServletRequest req) {
		String action = req.getParameter("action");
		if (action == null) {
			action = "";
		}
		return getInstance(action);
	}

	public Action getInstance(String nome) {
		Action ret = null;
		Class<?> classe;
		
		if(nome == null || nome.trim().equals("")){
			LOGGER.info("CotacaoAuto: Ação não informada ou não localizada.");
			try {
				//Caso apresente algum erro retorna a classe padr�o.
				classe = Class.forName("br.yasuda.control.AcessoNegadoAction");
				ret =  (Action)classe.newInstance();
			} catch (Exception e1) {
				LOGGER.error(e1.getMessage(), e1);
			}
		}
		try {
			//Sobe a classe para a mem�ria. 
			classe = Class.forName("br.yasuda.control." + nome + "Action");
			//Cria uma nova instancia da classe.
			ret = (Action) classe.newInstance();
		} catch (Exception e){
			LOGGER.info("CotacaoAuto: Ação não informada ou não localizada.");
			try {
				//Caso apresente algum erro retorna a classe padr�o.
				classe = Class.forName("br.yasuda.control.AcessoNegadoAction");
				ret = (Action) classe.newInstance();
			} catch (Exception e1) {
				LOGGER.error("CotacaoMulti - " + e.getMessage(), e);
			
			}
		}
		return ret;
	}
}
