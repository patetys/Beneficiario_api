import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.sompo.cotacaomulti.dto.HistoricoExternoDTO;
import db.DbAccess;

public class DadosHistoricoExternoServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 4336596189540501252L;
	private static final Logger LOGGER = Logger.getLogger(Controller.class);
	
	@SuppressWarnings({ "unchecked", "finally", "rawtypes" })
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.info("PORTAL_CORRETOR - Entrou DadosHistoricoServlet.dadosHistoricoExterno");
		try {
			List<HistoricoExternoDTO> listRetornoHistExterno = DbAccess.recuperaListaHistoricoExterno(req.getParameter("cotacao"));
			req.setAttribute("listRetornoHistExterno", listRetornoHistExterno);
			req.getRequestDispatcher("HistoricoExterno.jsp").forward(req, resp);
		}
		catch(Exception e) {
			LOGGER.error("Erro genérico ao devolver os dados históricos Externos -> " + e.getMessage(), e);
		}
	}
}
