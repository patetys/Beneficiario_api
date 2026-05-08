package br.yasuda.control;
/*
 * Criado em 25/06/2010
 *
 * Para alterar o gabarito para este arquivo gerado v� para
 * Janela&gt;Prefer�ncias&gt;Java&gt;Gera��o de C�digos&gt;C�digo e Coment�rios
 */


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;

import bean.Produtor;
import db.DbAccess;
import util.EmailUtil;

/*
 * @author Diego
 *
 * Para alterar o gabarito para este coment�rio do tipo gerado v� para
 * Janela&gt;Prefer�ncias&gt;Java&gt;Gera��o de C�digos&gt;C�digo e Coment�rios
 */
public class ContasInternacionaisAction implements Action {

	/* (n�o-Javadoc)
	 * @see br.yasuda.control.action.Action#doAction(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
				
		resp.setContentType("application/xml"); 

		String codCorr 					= req.getParameter("codCorr")==null?"":req.getParameter("codCorr");
		String cnpjCpf 					= req.getParameter("cnpjCpf")==null?"":req.getParameter("cnpjCpf");
		String tipPessoa 				= req.getParameter("tipPessoa")==null?"":req.getParameter("tipPessoa");
		String codUnidadeCorp 			= req.getParameter("codUnidadeCorp")==null?"":req.getParameter("codUnidadeCorp");	
		DbAccess dbAccess 				= new DbAccess();
		PrintWriter out 				= resp.getWriter();
		Boolean resultadoCnpj 			= false;
		
		if(tipPessoa.equals("1") && !codUnidadeCorp.equals("7207")) {//7207 - Negócios Internacionais
			
			String login 						= (String)req.getSession().getAttribute("login");
			resultadoCnpj 						= dbAccess.verificarExistenciaCnpjBlackListContasInternacionais(cnpjCpf);
			Produtor recuperaProdutor 			= dbAccess.recuperaProdutor(codCorr, codUnidadeCorp);
			Produtor recuperaUsuarioLogin 		= dbAccess.recuperaUsuarioLogin(login);
			
			StringBuilder corpo 				= new StringBuilder()
			.append("Login de quem acessou o sistema: ")
			.append(login)
			.append(" | ")
			.append("Nome do usuário do login: ")
			.append(recuperaUsuarioLogin.getNome())
			.append(" | ")
			.append("E-mail do usuário do login: ")
			.append(recuperaUsuarioLogin.getEmail())
			.append(" | ")
			.append("Código do Corretor: ")
			.append(codCorr)
			.append(" | ")
			.append("Unidade Corporativa: ")
			.append(codUnidadeCorp)
			.append(" | ")
			.append("Código do Executivo: ")
			.append(recuperaProdutor.getCodigo())
			.append(" | ")
			.append("Executivo: ")
			.append(recuperaProdutor.getNome());
			
			//Envia e-mail a operação relativo ao processo que fora bloqueado a sua continuidade
			if(resultadoCnpj) {
				new EmailUtil().envioEmail(cnpjCpf, corpo.toString());
			}
		}

		XStream xstream = new XStream();
		xstream.alias("liberarTelaContasInternacionais", Boolean.class);
		String xml = xstream.toXML(resultadoCnpj);
		
		out.print(xml);
		
	}

}
