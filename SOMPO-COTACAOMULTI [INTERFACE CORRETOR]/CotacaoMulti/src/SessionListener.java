import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import bean.CotacaoBean;
import db.DbAccess;

public class SessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		@SuppressWarnings("unchecked")
		CotacaoBean	cot = (CotacaoBean) arg0.getSession().getAttribute("CotacaoBean");
		DbAccess.apagaTemporarios(cot);	
	}

}
