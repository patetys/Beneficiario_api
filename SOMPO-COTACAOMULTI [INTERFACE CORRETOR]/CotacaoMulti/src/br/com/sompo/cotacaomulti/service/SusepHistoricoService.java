package br.com.sompo.cotacaomulti.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import br.com.sompo.cotacaomulti.dto.SusepHistoricoDTO;
import br.com.sompo.cotacaomulti.exception.SusepHistoricoException;
import br.yasuda.properties.CotacaoMultiProperties;

public class SusepHistoricoService {

	private static final Logger LOGGER = Logger.getLogger(SusepHistoricoService.class);

	public String getSusepsFormatAsLong(String suseps) {
		String retorno = "";
		if(StringUtils.isNotEmpty(suseps)) {			
			String[] susepsAsLong = Arrays.stream(suseps.split(","))
					  .map(s -> String.format("'%s'", Long.valueOf(s.trim().replaceAll("'", "")).toString()))
					  .toArray(String[]::new);
			
			retorno = String.join(",", susepsAsLong);
		}
		return retorno;
	}

	
	public String obterSusepsPorSusepToQueryIN(String numeroSusep) throws SusepHistoricoException {
		LOGGER.info("[SusepHistoricoService] - (obterSusepsPorSusepToQueryIN) : Iniciando o metodo");
		String retorno = "";
		try {
			List<String> suseps = this.obterSusepsPorSusep(numeroSusep);
			
			if(suseps != null && !suseps.isEmpty()) {
				for(String susep : suseps) { 
					retorno += String.format("'%s',", susep.trim());
				}
				retorno = retorno.substring(0,retorno.length() - 1);
			}
		} catch (Exception e) {
			LOGGER.error("[SusepHistoricoService] - (obterSusepsPorSusepToQueryIN): Erro ao obter suseps", e);
			throw new SusepHistoricoException(e.getMessage(), e);
		}
		LOGGER.info("[SusepHistoricoService] - (obterSusepsPorSusepToQueryIN) : Finalizando o metodo");
		return retorno;
	}

	
	public List<String> obterSusepsPorSusep(String numeroSusep) throws Exception {
		List<String> retorno = null;
		LOGGER.info("> Inicio obterSusepsPorSusep da susep: " + numeroSusep);
		
		List<SusepHistoricoDTO> susepsHist = realizarConsultaSuseps(numeroSusep);
		if(susepsHist != null && !susepsHist.isEmpty()) {
			retorno = susepsHist.stream().map(SusepHistoricoDTO::getNumeroSusep).collect(Collectors.toList());
		}
		
		LOGGER.info("suseps: " + retorno);
		LOGGER.info("< Fim obterSusepsPorSusep");
		return retorno;
	}

	private List<SusepHistoricoDTO> realizarConsultaSuseps(String numeroSusep) throws Exception {
		LOGGER.info(String.format("> Inicio realizarConsultaSuseps para susep: %s", numeroSusep));
		List<SusepHistoricoDTO> susepsHist = null;
		try {
			String strURL = String.format("%s%s%s",
					CotacaoMultiProperties.getInstance().getProperty("susep.hist.api.url"),
					CotacaoMultiProperties.getInstance().getProperty("susep.hist.api.recurso"), numeroSusep);
			
			LOGGER.info(":::URL SusepHist: " + strURL);
			
			URL url = new URL(strURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() == 200) {
				String suseps = new BufferedReader(new InputStreamReader(conn.getInputStream())).lines()
						.collect(Collectors.joining("\n"));

				Gson gson = new Gson();
				susepsHist = Arrays.asList(gson.fromJson(suseps, SusepHistoricoDTO[].class));
			} else if (conn.getResponseCode() == 204) {
				LOGGER.info("Nenhuma informacao encontrada para a susep: " + numeroSusep);
			} else {
				throw new RuntimeException("Erro ao realizar consulta de suseps. HTTP error code : "
						+ conn.getResponseCode() + ". Message: " + conn.getResponseMessage());
			}
			conn.disconnect();

		} catch (Exception e) {
			LOGGER.error("Erro: ", e);
			throw e;
		}
		LOGGER.info("< Fim realizarConsultaSuseps");
		return susepsHist;
	}
}