package util;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import br.com.maritima.www.MSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy;
import br.yasuda.properties.CotacaoMultiProperties;

/**
 * 
 * @author aasousa
 *
 */

public class EmailUtil {

	private static final Logger LOGGER 						= Logger.getLogger(EmailUtil.class);
	
	/**
	 * Envio de email usando o serviço do Broker
	 * 
	 * @param email Email
	 * @throws RemoteException
	 * 
	 * @author aasousa
	 * @throws MalformedURLException 
	 */
	public void envioEmail(String assuntoEmail, String corpoEmail) throws RemoteException, MalformedURLException {

		LOGGER.info("Envio de e-mail a operação relativo ao processo.");

		MSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy proxy = new MSE_ServicosCorporativos_EMAIL_MSPPortTypeProxy();
		proxy.setEndpoint(CotacaoMultiProperties.getInstance().getProperty("email.url.servico.envio"));		
		
		String assunto 		= CotacaoMultiProperties.getInstance().getProperty("email.operacao.siscota.assunto");
		String corpo 		= CotacaoMultiProperties.getInstance().getProperty("email.operacao.siscota.mensagem");
		
		proxy.sendEmail(CotacaoMultiProperties.getInstance().getProperty("email.sompo.origem"), 
						CotacaoMultiProperties.getInstance().getProperty("email.operacao.siscota"), 
						null, 
						null, 
						null, 
						assunto.replaceAll("NUM_CNPJ", assuntoEmail), 
						corpo.replaceAll("CORPO_MENSAGEM", corpoEmail).replaceAll("NUM_CNPJ", assuntoEmail), 
						null, 
						null, 
						null, null, null, 
						null, null, null, 
						null, null, null, 
						null, null, null, 
						null, null, null, 
						null, null, null);

	}
}
