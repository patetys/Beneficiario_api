package br.yasuda.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadFileTypeException extends MessageScreenException {

	private static final long serialVersionUID = -2542867863199268657L;

	public UploadFileTypeException(HttpServletRequest req, HttpServletResponse resp, int number, String message) {
		super(req, resp, number, message);
	}

}
