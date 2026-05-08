package util;

import javax.servlet.http.HttpServletRequest;

import com.yasuda.properties.ExternalProperties;
import com.yasuda.properties.Properties;

public class CotacaoMultiProperties extends Properties {

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
/*		if (sompo == null) {
			sompo = getInstanceExt("compartilhado").getProperty("cores.empresa");
			if (sompo == null || !sompo.equalsIgnoreCase("sompo")) {
				sompo = "yasuda";
			}
		}

		if (req.getSession().getAttribute("sompo") == null) {
			req.getSession().setAttribute("sompo", sompo);
		}*/
		if (req.getSession().getAttribute("sompo") == null) {
			req.getSession().setAttribute("sompo", "Sompo");
		}
	}

	public static void main(String[] args) {
		String valor = getInstance().getProperty("menucad");
		System.out.println(valor);

		String valor1 = getInstanceExt("compartilhado").getProperty("cores.empresa");
		System.out.println(valor1);
	}

}
