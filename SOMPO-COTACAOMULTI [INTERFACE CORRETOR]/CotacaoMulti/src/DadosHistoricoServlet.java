import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.DbAccess;

import org.apache.log4j.Logger;

import br.com.sompo.cotacaomulti.dto.HistoricoInternoDTO;

public class DadosHistoricoServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 4336596189540501252L;
	private static final Logger LOGGER = Logger.getLogger(Controller.class);
	
	@SuppressWarnings({ "unchecked", "finally", "rawtypes" })
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.info("PORTAL_CORRETOR - Entrou DadosHistoricoServlet.dadosHistoricoInterno");
		try {
			List<HistoricoInternoDTO> listRetornoHistorico = DbAccess.recuperaListaHistoricoInterno(req.getParameter("cotacao"));
			req.setAttribute("listRetornoHistorico", listRetornoHistorico);
			req.getRequestDispatcher("HistoricoInterno.jsp").forward(req, resp);
		}
		catch(Exception e) {
			LOGGER.error("Erro genérico ao devolver os dados históricos -> " + e.getMessage(), e);
		}
	}
}