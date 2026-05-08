package br.yasuda.properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.yasuda.properties.ExternalProperties;
import com.yasuda.properties.Properties;

public class CotacaoMultiProperties extends Properties {
	
	private static final Logger LOGGER = Logger.getLogger(CotacaoMultiProperties.class);

	private final static String resourceBundleName = "/config.properties";  

	private static CotacaoMultiProperties instance = null;
	private static ExternalProperties instanceExt = null;
	public static String sompo = null;

	private CotacaoMultiProperties(String resourceBundleName) {
		super(resourceBundleName);
	}

	public static CotacaoMultiProperties getInstance() {
		if (instance == null)
			instance = new CotacaoMultiProperties(resourceBundleName);
		return instance;
	}
	
	public static ExternalProperties getInstanceExt(String chave) {
		if (instanceExt == null) {
			instanceExt = new ExternalProperties(getInstance().getProperty(chave));
		}
		return instanceExt;

	}
	
	public static void VerificaEstiloSompo(HttpServletRequest req) {
		if (sompo == null) {
			sompo = getInstanceExt("compartilhado").getProperty("cores.empresa");
			if (sompo == null || !sompo.equalsIgnoreCase("sompo")  ) {
				sompo = "yasuda";
			}
		}
		//req.getSession().setAttribute("sompo", sompo);
		
		if (req.getSession().getAttribute("sompo") == null) {
			req.getSession().setAttribute("sompo", sompo);
		}
		/*
		if ("sompo".equalsIgnoreCase(sompo)) {
			req.setAttribute("urlSompo", "css/sompo_CotacaoAuto.css");
		} else {
			req.setAttribute("urlSompo", "css/yasuda.css");
		}
		*/
	}

}
