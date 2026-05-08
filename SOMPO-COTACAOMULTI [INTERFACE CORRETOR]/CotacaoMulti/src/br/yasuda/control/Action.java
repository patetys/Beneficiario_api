package br.yasuda.control;
/*
 * Criado em 15/06/2010
 *
 */


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ricardo Miyahira
 *
 */
public interface Action {

	public void doAction(HttpServletRequest req, HttpServletResponse resp) throws Exception;

}
