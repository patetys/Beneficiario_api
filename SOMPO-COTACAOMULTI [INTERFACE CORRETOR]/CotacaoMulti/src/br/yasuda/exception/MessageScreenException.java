package br.yasuda.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MessageScreenException extends Exception {

	private static final long serialVersionUID = -8455553799694825910L;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private int number;

	public MessageScreenException(HttpServletRequest req, HttpServletResponse resp, int number, String message) {
		super(message);
		this.resp = resp;
		this.req = req;
		this.number = number;
	}

	public HttpServletRequest getReq() {
		return req;
	}

	public int getNumber() {
		return number;
	}

	public HttpServletResponse getResp() {
		return resp;
	}

	public void throwMessage() throws ServletException, IOException {
		req.setAttribute("erro", getNumber() + " - " + getMessage());

		if ("1".equalsIgnoreCase((String) req.getSession().getAttribute("outfile"))) {
			req.getRequestDispatcher("UploadFileNovaIdentidadeVisual.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("UploadFile.jsp").forward(req, resp);
		}
	}

}
