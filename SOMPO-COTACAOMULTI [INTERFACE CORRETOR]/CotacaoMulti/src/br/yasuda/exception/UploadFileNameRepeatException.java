package br.yasuda.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UploadFileNameRepeatException extends MessageScreenException {

	private static final long serialVersionUID = -7625334098137138409L;

	public UploadFileNameRepeatException(HttpServletRequest req, HttpServletResponse resp, int number, String message) {
		super(req, resp, number, message);
	}

}
