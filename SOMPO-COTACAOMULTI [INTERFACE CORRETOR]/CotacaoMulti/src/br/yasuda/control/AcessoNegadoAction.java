/*
 * Criado em 15/06/2010
 *
 */
package br.yasuda.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ricardo Miyahira
 *
 */
public class AcessoNegadoAction implements Action {

	/* (não-Javadoc)
	 * @see br.yasuda.control.action.Action#doAction(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	public void doAction(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setAttribute("msg", "Acesso não autorizado.");
		req.getRequestDispatcher("mensagem.jsp").forward(req, resp);
        
	}

}
