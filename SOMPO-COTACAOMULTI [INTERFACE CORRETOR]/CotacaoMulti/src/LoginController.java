import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		req.getSession().removeAttribute("basic_cotacao");

		// Get the user's name and password
		String name = req.getParameter("name");
		String passwd = req.getParameter("passwd");

		// Check the name and password for validity
		if (!allowUser(name, passwd)) {
			out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
			out.println("<BODY>Your login and password are invalid.<BR>");
			out.println("You may want to <A HREF=\"/CotacaoMulti/login.jsp\">try again</A>");
			out.println("</BODY></HTML>");
			
		} else {
			out.println("<HTML><HEAD><TITLE>Logado com sucesso</TITLE></HEAD>");
			out.println("<BODY>Login realizado com sucesso.");
			out.println("</BODY></HTML>");
			req.getSession().setAttribute("basic_cotacao", Base64.getEncoder().encodeToString(("cotacao_" + name).getBytes()).toString());
		}
	}

	protected boolean allowUser(String user, String passwd) {
		return user.equals("admin") && passwd.equals("CotacaoMulti");
	}

}
