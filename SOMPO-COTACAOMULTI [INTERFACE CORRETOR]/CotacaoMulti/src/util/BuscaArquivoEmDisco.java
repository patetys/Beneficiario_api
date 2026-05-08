package util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class BuscaArquivoEmDisco implements Serializable{

	private static final long serialVersionUID = 1872700768018796696L;
	
	private static final Logger LOGGER = Logger.getLogger(BuscaArquivoEmDisco.class);

	private String caminhoCompletoArquivo;

	public BuscaArquivoEmDisco(String String) {
		this.caminhoCompletoArquivo = String;
	}

	public String verificaCaminhoValidoArquivo(boolean exibeErroLog) {
		LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivo) : Iniciando o método");
		List<String> diretorios = retornaDiretoriosPesquisaArquivos();
		File arquivoAux = new File("");
		
		for (String diretorio : diretorios) {
			String diretorioConcatenado = diretorio.concat(caminhoCompletoArquivo.substring(8));
			LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivo) : Buscando o arquivo: ".concat(diretorioConcatenado));
			arquivoAux = new File(diretorioConcatenado);		
			if (arquivoAux.exists()) {
				LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivo) : Encontrou o arquivo: ".concat(arquivoAux.getPath()));
				LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivo) : Finalizando o método com sucesso.");
				return arquivoAux.getPath();
			}
		}	
		
		if (exibeErroLog) {
			// Caso n�o encontre o arquivo
			LOGGER.error("CotacaoMulti - Arquivo n�o localizado no disco de Backup: " + caminhoCompletoArquivo);
		}
		LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivo) : Finalizando o método");
		return null;

	}

	/*
	 * M�todo criado para listar todos os diretorios que seja possivel que
	 * arquivos gravados no /dados estejam
	 */
	public List<String> retornaDiretoriosPesquisaArquivos() {
		LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivos) : Iniciando o método");
		File dir = new File("/");
		LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivos) : Iniciando o preenchimento da lista");
		String[] children = dir.list();
		List<String> diretorios = new ArrayList<String>();

		/*
		 * Os diretorios criados na raiz do servidor que se inciarem com dados_
		 * ser�o destinados para backup de arquivos antigos do barra dados
		 */
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivos) : Incluindo o filtro dados_ ");
				return name.startsWith("dados_");
			}
		};
		LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivos) : Incluindo no array children os diretorios da lista");
		children = dir.list(filter);

		if (children == null) {
			LOGGER.error("N�o foi poss�vel localizar nenhum diret�rio de arquivos v�lido.");
		} else {
			LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivos) : Incluindo o diretório /u/dados ");
			diretorios.add("/u/dados");
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				String diretorio = children[i];
				LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivos) : Incluindo o diretório: ".concat(diretorio));
				diretorios.add("/" + diretorio);
			}
			// Diretorio de backup antigo
			LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivos) : Incluindo o diretório antigo /mnt/cob_dados/u/dados ");
			diretorios.add("/mnt/cob_dados/u/dados");
		}
		LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivos) : Finalizando o método");
		return diretorios;
	}
	
	public int deletaArquivos(){
		LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Iniciando o método. ");
		List<String> diretorios = retornaDiretoriosPesquisaArquivos();
		File arquivoAux = new File("");
		int contador = 0;

		for (String diretorio : diretorios) {
			String diretorioConcatenado = diretorio.concat(caminhoCompletoArquivo.substring(6));
			LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Buscando o arquivo: ".concat(diretorioConcatenado));
			arquivoAux = new File(diretorioConcatenado);			
			if (arquivoAux.exists()) {
				LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Excluindo o arquivo: ".concat(arquivoAux.getPath()));
				arquivoAux.delete();
				LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Excluiu o arquivo: ".concat(arquivoAux.getPath()));
				contador++;
			}
		}
		LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Finalizando o método deletando o total de arquivos: ".concat(String.valueOf(contador)));
		return contador;
	}
	
	public String verificaCaminhoValidoArquivoNovo(boolean exibeErroLog) {
		LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivoNovo) : Iniciando o método");
		List<String> diretorios = retornaDiretoriosPesquisaArquivosNovo();
		File arquivoAux = new File("");
		
		for (String diretorio : diretorios) {
			String diretorioConcatenado = diretorio.concat(caminhoCompletoArquivo.substring(8));
			LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivoNovo) : Buscando o arquivo: ".concat(diretorioConcatenado));
			arquivoAux = new File(diretorioConcatenado);		
			if (arquivoAux.exists()) {
				LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivoNovo) : Encontrou o arquivo: ".concat(arquivoAux.getPath()));
				LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivoNovo) : Finalizando o método com sucesso.");
				return arquivoAux.getPath();
			}
		}	
		
		if (exibeErroLog) {
			// Caso não encontre o arquivo
			LOGGER.error("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivoNovo) : CotacaoMulti - Arquivo nao localizado no disco de Backup: " + caminhoCompletoArquivo);
		}
		LOGGER.info("[BuscaArquivoEmDisco] - (verificaCaminhoValidoArquivoNovo) : Finalizando o método");
		return null;

	}

	/*
	 * M�todo criado para listar todos os diretorios que seja possivel que
	 * arquivos gravados no /dados estejam
	 */
	public List<String> retornaDiretoriosPesquisaArquivosNovo() {
		LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivosNovo) : Iniciando o método");
		File dir = new File("/");
		LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivosNovo) : Iniciando o preenchimento da lista");
		String[] children = dir.list();
		List<String> diretorios = new ArrayList<String>();

		/*
		 * Os diretorios criados na raiz do servidor que se inciarem com dados_
		 * ser�o destinados para backup de arquivos antigos do barra dados
		 */
		FilenameFilter filter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivosNovo) : Incluindo o filtro dados_ ");
				return name.startsWith("dados_");
			}
		};
		LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivosNovo) : Incluindo no array children os diretorios da lista");
		children = dir.list(filter);

		if (children == null) {
			LOGGER.error("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivosNovo) : Nao foi poss�vel localizar nenhum diretorio de arquivos valido.");
		} else {
			LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivosNovo) : Incluindo o diretório /u/dadosapp/cotacao ");
			diretorios.add("/u/dadosapp/cotacao");
			for (int i = 0; i < children.length; i++) {
				// Get filename of file or directory
				String diretorio = children[i];
				LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivosNovo) : Incluindo o diretório: ".concat(diretorio));
				diretorios.add("/" + diretorio);
			}
		}
		LOGGER.info("[BuscaArquivoEmDisco] - (retornaDiretoriosPesquisaArquivosNovo) : Finalizando o método");
		return diretorios;
	}
	
	public int deletaArquivosNovo(){
		LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Iniciando o método. ");
		List<String> diretorios = retornaDiretoriosPesquisaArquivosNovo();
		File arquivoAux = new File("");
		int contador = 0;

		for (String diretorio : diretorios) {
			String diretorioConcatenado = diretorio.concat(caminhoCompletoArquivo.substring(8));
			LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Buscando o arquivo: ".concat(diretorioConcatenado));
			arquivoAux = new File(diretorioConcatenado);			
			if (arquivoAux.exists()) {
				LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Excluindo o arquivo: ".concat(arquivoAux.getPath()));
				arquivoAux.delete();
				LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Excluiu o arquivo: ".concat(arquivoAux.getPath()));
				contador++;
			}
		}
		LOGGER.info("[BuscaArquivoEmDisco] - (deletaArquivos) : Finalizando o método deletando o total de arquivos: ".concat(String.valueOf(contador)));
		return contador;
	}
}
