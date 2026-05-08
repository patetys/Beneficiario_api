package util;
import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/*
 * Criado em 18/02/2010
 *
 */

/**
 * @author DCMaia
 *
 */
public class ArqExists implements Serializable{
	
	private static final Logger LOGGER = Logger.getLogger(ArqExists.class);

	private static final long serialVersionUID = 787898709025385424L;
	
	private static final String SQL_PROD = "java:comp/env/jdbc/sqlProducao";
	private static final String YASUDANET_AWS = "jdbc/siscotaMultiAws";
	
//	private static final String SQL_PROD = "java:jboss/datasources/sqlProducao";
//	private static final String YASUDANET_AWS = "java:jboss/jdbc/siscotaMultiAws";

	public String arqExists(String fname) {
		Connection conn = null;
		String ret = null;
		try {
			if ((new File(fname)).exists()) {
				ret = fname;
			} else {

				Context ctx = new InitialContext();
				//DataSource ds = (DataSource) ctx.lookup(YASUDANET_AWS);
				DataSource ds = (DataSource) ctx.lookup(SQL_PROD);
				conn = ds.getConnection();
				String query =
					"SELECT [conteudo_parm] FROM [yasudanet].[dbo].[Tab_Parametros] WHERE [nome_parm] = 'RENOVNET-DIR-BACKUP'";
				ResultSet rs = conn.createStatement().executeQuery(query);
				if (rs.next()) {
					if ((new File(rs.getString("conteudo_parm") + fname)).exists()) {
						ret = rs.getString("conteudo_parm") + fname;

					} else {
						ret = null;
					}
				}

				try {
					rs.close();
					conn.close();
					conn = null;
				} catch (SQLException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					LOGGER.error(e1.getMessage(), e1);
				}
			}
		}
		return ret;
	}
}
